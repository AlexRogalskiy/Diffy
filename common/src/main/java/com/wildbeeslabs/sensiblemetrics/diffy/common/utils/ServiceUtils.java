/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.io.Closeable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.*;
import java.util.stream.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils.formatMessage;
import static java.util.Arrays.asList;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.Collectors.*;
import static java.util.stream.StreamSupport.stream;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Service utilities implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class ServiceUtils {

    /**
     * Default {@link Function} mappings
     */
    public static final Function<Optional<?>, List<?>> toList = option -> collect(option, Collectors.toList());
    public static final Function<Optional<?>, Set<?>> toSet = option -> collect(option, Collectors.toSet());
    public static final Function<Optional<?>, LinkedList<?>> toLinkedList = option -> collect(option, toLinkedList());
    public static final Function<Optional<?>, List<?>> toUnmodifiableList = option -> collect(option, toUnmodifiableList());
    public static final Function<Optional<?>, Set<?>> toUnmodifiableSet = option -> collect(option, toUnmodifiableSet());

    /**
     * Default {@link Function} increment / decrement counters
     */
    public static final Function<Integer, IntUnaryOperator> INCREMENT_BY = value -> i -> i + value;
    public static final Function<Integer, IntUnaryOperator> DECREMENT_BY = value -> i -> i - value;

    /**
     * Default counter increment by one
     */
    public static final IntUnaryOperator INCREMENT = i -> i + 1;
    /**
     * Default counter decrement by one
     */
    public static final IntUnaryOperator DECREMENT = i -> i - 1;

    /**
     * Returns {@link BiConsumer} by input parameters
     *
     * @param size - initial input range size
     * @return {@link BiConsumer}
     */
    @NonNull
    public static BiConsumer<Map<Integer, Integer>, Double> accumulator(final int size) {
        return (map, val) -> map.merge((int) (val / size), 1, (a, b) -> a + 1);
    }

    /**
     * Returns {@link BinaryOperator}
     *
     * @param <K> type of key item
     * @param <V> type of value item
     * @return {@link BinaryOperator}
     */
    @NonNull
    public static <K, V> BinaryOperator<Map<K, V>> combinerFirst() {
        return (map1, map2) -> {
            map2.forEach((k, v) -> map1.merge(k, v, (v1, v2) -> v1));
            return map1;
        };
    }

    /**
     * Returns {@link BinaryOperator}
     *
     * @param <K> type of key item
     * @param <V> type of value item
     * @return {@link BinaryOperator}
     */
    @NotNull
    public static <K, V> BinaryOperator<Map<K, V>> combinerLast() {
        return (map1, map2) -> {
            map2.forEach((k, v) -> map1.merge(k, v, (v1, v2) -> v2));
            return map1;
        };
    }

    /**
     * Returns {@link HashMap} {@link Supplier}
     *
     * @param <K> type of key item
     * @param <V> type of value item
     * @return {@link Supplier}
     */
    public static <K, V> Supplier<Map<K, V>> supplier() {
        return HashMap::new;
    }

    /**
     * Default completable {@link BiConsumer} action
     */
    private static final BiConsumer<? super Object, ? super Throwable> DEFAULT_COMPLETABLE_ACTION = (response, error) -> {
        try {
            if (Objects.isNull(error)) {
                log.debug("Received completable future response [from={}]", response);
            } else {
                log.debug("Canceled completable future request [response={}, error={}]", response, error);
            }
        } catch (RuntimeException | Error e) {
            log.error("ERROR: cannot process completable future request callback", e);
        }
    };

    /**
     * Returns {@link LinkedList} representation of {@link Collector}
     *
     * @param <T> type of collecting item
     * @return {@link LinkedList} representation of {@link Collector}
     */
    public static <T> Collector<T, ?, LinkedList<T>> toLinkedList() {
        return Collector.of(LinkedList::new, LinkedList::add, (first, second) -> {
            first.addAll(second);
            return first;
        });
    }

    public static <T> Collector<T, ?, List<T>> toListWithLast(int n) {
        ValidationUtils.isTrue(n > 0, "Number should be positive");
        return Collector.<T, Deque<T>, List<T>>of(ArrayDeque::new, (acc, t) -> {
            if (acc.size() == n) {
                acc.pollFirst();
            }
            acc.add(t);
        }, (acc1, acc2) -> {
            while (acc2.size() < n && !acc1.isEmpty()) {
                acc2.addFirst(acc1.pollLast());
            }
            return acc2;
        }, ArrayList::new);
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link List}
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link Set}
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return collectingAndThen(toSet(), Collections::unmodifiableSet);
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link List}
     *
     * @return will never be {@literal null}.
     */
    public static <T, A extends List<T>> Collector<T, A, List<T>> toImmutableList(final Supplier<A> supplier) {
        return Collector.of(
            supplier,
            List::add, (left, right) -> {
                left.addAll(right);
                return left;
            }, Collections::unmodifiableList);
    }

    /**
     * Returns {@link Enum} by input parameters
     *
     * @param <E>       type of enumeration item
     * @param propValue - initial input property {@link String}
     * @param enumType  - initial input enumeration type {@link Class}
     * @return {@link Enum}
     * @throws IllegalArgumentException
     */
    public static <E extends Enum<E>> E toEnum(final String propValue, final Class<E> enumType) throws IllegalArgumentException {
        try {
            return Enum.valueOf(enumType, propValue);
        } catch (IllegalArgumentException e) {
            BadOperationException.throwError(String.format("ERROR: cannot process enum type: {%s} by class: {%s}", enumType, propValue), e);
        }
        return null;
    }

    public static <K, V> Map<K, V> toCheckedMap(final Map rawMap, final Class<? extends K> keyType, final Class<? extends V> valueType, boolean strict) throws ClassCastException {
        ValidationUtils.notNull(rawMap, "Raw map should not be null");
        final Map<K, V> m2 = new HashMap<>(rawMap.size() * 4 / 3 + 1);
        final Iterator it = rawMap.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry e = (Map.Entry) it.next();
            try {
                m2.put(keyType.cast(e.getKey()), valueType.cast(e.getValue()));
            } catch (ClassCastException ex) {
                log.error(String.format("ERROR: cannot convert map key=%s, value=%s to key type=%s, key value=%s, message=%s", e.getKey(), e.getValue(), keyType, valueType, ex.getMessage()));
                if (strict) {
                    throw ex;
                }
            }
        }
        return m2;
    }

    /**
     * @param <E>
     * @param rawList
     * @param type
     * @param strict
     * @return
     * @throws ClassCastException
     */
    public static <E> List<E> toCheckedList(final List rawList, final Class<? extends E> type, boolean strict) throws ClassCastException {
        ValidationUtils.notNull(rawList, "Raw list should not be null");
        final List<E> l = (rawList instanceof RandomAccess) ? new ArrayList<>(rawList.size()) : new LinkedList<>();
        final Iterator it = rawList.iterator();
        while (it.hasNext()) {
            final Object e = it.next();
            try {
                l.add(type.cast(e));
            } catch (ClassCastException ex) {
                log.error(String.format("ERROR: cannot convert list value=%s to type=%s, message=%s", e, type, ex.getMessage()));
                if (strict) {
                    throw ex;
                }
            }
        }
        return l;
    }

    public static <T, E extends T> void append(final Collection<T> collection1, final Collection<E> collection2, int count) {
        ValidationUtils.notNull(collection1, "First collection should not be null");
        ValidationUtils.notNull(collection2, "Last collection should not be null");
        ValidationUtils.isTrue(count > 0, "Count should be positive");

        final Iterator<E> it2 = collection2.iterator();
        for (int i = 0; i < count && it2.hasNext(); i++) {
            collection1.add(it2.next());
        }
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input collection of {@link CompletableFuture} and {@link Executor} instance
     *
     * @param <T>      type of {@link CompletableFuture} result
     * @param executor - initial input {@link Executor} instance
     * @param futures  - initial input collection of {@link CompletableFuture}
     * @throws NullPointerException if futures is {@code null}
     */
    public static <T> void getResultAsync(final Executor executor, final CompletableFuture<T>... futures) {
        ValidationUtils.notNull(futures, "Array of futures should not be null");
        CompletableFuture.allOf(futures).whenCompleteAsync(DEFAULT_COMPLETABLE_ACTION, executor).join();
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input {@link CompletableFuture} and {@link Executor} instance
     *
     * @param <T>      type of {@link CompletableFuture} result
     * @param executor - initial input {@link Executor} instance
     * @param future   - initial input {@link CompletableFuture} instance
     * @return result {@code T} of {@link CompletableFuture}
     * @throws NullPointerException if future is {@code null}
     */
    public static <T> T getResultAsync(final Executor executor, final CompletableFuture<T> future) {
        ValidationUtils.notNull(future, "Future should not be null");
        return future.whenCompleteAsync(DEFAULT_COMPLETABLE_ACTION, executor).join();
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input {@link CompletableFuture}
     *
     * @param <T>    type of {@link CompletableFuture} result
     * @param future - initial input {@link CompletableFuture} instance
     * @return result {@code T} of {@link CompletableFuture}
     * @throws NullPointerException if future is {@code null}
     */
    public static <T> T getResultAsync(final CompletableFuture<T> future) {
        ValidationUtils.notNull(future, "Future should not be null");
        return getResultAsync(Executors.newSingleThreadExecutor(), future);
    }

    /**
     * Checks if {@link Collection} matches {@link Predicate}
     *
     * @param <T>
     * @param list      - initial input {@link Collection}
     * @param predicate - initial input {@link Predicate}
     * @return true - if {@link Collection} matches, false - otherwise
     * @throws NullPointerException if list is {@code null}
     * @throws NullPointerException if predicate is {@code null}
     */
    public static <T> boolean contains(final Collection<T> list, final Predicate<? super T> predicate) {
        ValidationUtils.notNull(list, "List should not be null");
        ValidationUtils.notNull(predicate, "Predicate should not be null");

        return listOf(list).stream().filter(predicate).findFirst().isPresent();
    }

    /**
     * Returns converted from string value to type {@link Class} by method name {@link String}
     *
     * @param value        - initial argument value to be converted {@link String}
     * @param toType       - initial type to be converted to {@link Class}
     * @param parserMethod - initial method name to process the conversion {@link String}
     * @return converted value {@link Object}
     * @throws NullPointerException if toType is {@code null}
     */
    @Nullable
    public static Object convert(final String value, final Class<?> toType, final String parserMethod) {
        ValidationUtils.notNull(toType, "Destination type should not be null");
        try {
            final Method method = toType.getMethod(parserMethod, String.class);
            return method.invoke(toType, value);
        } catch (NoSuchMethodException e) {
            log.error(formatMessage("ERROR: cannot find method={%s} in type={%s},", parserMethod, toType));
        } catch (IllegalAccessException e) {
            log.error(formatMessage("ERROR: cannot access method={%s} in type={%s},", parserMethod, toType));
        } catch (InvocationTargetException e) {
            log.error(formatMessage("ERROR: cannot convert value={%s} to type={%s},", value, toType));
        }
        return null;
    }

    /**
     * Returns {@link Optional} of {@code T} by input parameters
     *
     * @param <T>       type of input element to be converted from by operation
     * @param predicate - initial input {@link Predicate}
     * @param reducer   - initial input {@link BinaryOperator}
     * @param values    - initial input collection of {@code T}
     * @return {@link Optional} of {@code T}
     * @throws NullPointerException if predicate is {@code null}
     * @throws NullPointerException if reducer is {@code null}
     */
    @NonNull
    public static <T> Optional<T> reduce(final T[] values, final Predicate<T> predicate, final BinaryOperator<T> reducer) {
        ValidationUtils.notNull(predicate, "Predicate should not be null");
        ValidationUtils.notNull(reducer, "Reducer should not be null");

        return streamOf(values).filter(predicate).reduce(reducer);
    }

    /**
     * Returns {@link Optional} of {@code T} by input parameters
     *
     * @param <T>       type of input element to be converted from by operation
     * @param predicate - initial input {@link Predicate}
     * @param reducer   - initial input {@link BinaryOperator}
     * @param values    - initial input collection of {@code T}
     * @return {@link Optional} of {@code T}
     * @throws NullPointerException if predicate is {@code null}
     * @throws NullPointerException if reducer is {@code null}
     * @throws NullPointerException if supplier is {@code null}
     */
    @NonNull
    public static <T, K extends Throwable> T reduceOrThrow(final T[] values, final Predicate<T> predicate, final BinaryOperator<T> reducer, final Supplier<? extends K> supplier) {
        ValidationUtils.notNull(predicate, "Predicate should not be null");
        ValidationUtils.notNull(reducer, "Reducer should not be null");
        ValidationUtils.notNull(supplier, "Supplier should not be null");

        try {
            return reduce(values, predicate, reducer).orElseThrow(supplier);
        } catch (Throwable k) {
            throw new InvalidParameterException(String.format("ERROR: cannot operate reducer on values = {%s}", join(values, "|")), k);
        }
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input collection of {@code T} values
     *
     * @param <T>    type of input element to be converted from by operation
     * @param values - initial input collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final T... values) {
        return Arrays.stream(Optional.ofNullable(values).orElseGet(() -> (T[]) new Objects[]{}));
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterable} collection of {@code T} values
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterable<T> iterable) {
        ValidationUtils.notNull(iterable, "Iterable should not be null");
        return (iterable instanceof Collection) ? ((Collection<T>) iterable).stream() : StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Collection<T> collectionOf(final Iterable<T> iterable) {
        return (iterable instanceof Collection) ? (Collection<T>) iterable : newArrayList(iterable);
    }

    public static <T> T[] arrayOf(final Iterable<? extends T> iterable, final Class<T> type) {
        if (Objects.isNull(iterable)) {
            return null;
        }
        final Collection<? extends T> collection = collectionOf(iterable);
        final T[] array = newArray3(type, collection.size());
        return collection.toArray(array);
    }

    public static <T> T[] arrayOf(final Iterable<? extends T> iterable) {
        if (Objects.isNull(iterable)) {
            return null;
        }
        return (T[]) newArrayList(iterable).toArray();
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @param parallel - initial input parallel flag
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final Iterator<T> iterator, final boolean parallel) {
        ValidationUtils.notNull(iterator, "Source iteratorOf should not be null");
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), parallel);
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @return non-nullable {@link Stream} of {@code T}
     */
    public static <T> Stream<T> asStream(final Iterator<T> iterator) {
        return streamOf(iterator, false);
    }

    /**
     * Returns non-nullable {@link Iterable} collection from input {@link Iterable} collection of values {@code T}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Iterable} collection
     */
    @NonNull
    public static <T> List<T> listOf(final Iterable<T> iterable) {
        return streamOf(iterable).collect(toList());
    }

    /**
     * Returns {@link Iterable} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @return {@link Iterable}
     */
    @NonNull
    public static <T> Iterable<T> iterableOf(final Iterator<T> iterator) {
        return () -> iterator;
    }

    @NonNull
    public static <T> Iterable<T> iterableOf(final T... elements) {
        return streamOf(elements).collect(toList());
    }

    @NonNull
    public static <T> Iterator<T> iteratorOf(final T... elements) {
        return iterableOf(elements).iterator();
    }

    /**
     * Returns {@link Set} by input array of {@code T} items
     *
     * @param <T>   type of input element to be converted from by operation
     * @param value - initial input array of {@code T}s
     * @return {@link Set} of {@link T}s
     */
    @NonNull
    public static <T> Set<T> setOf(final T... value) {
        return new HashSet<>(asList(value));
    }

    @NonNull
    public static <T> List<T> copyOf(final List<T> source, final int fromIndex, final int toIndex) {
        ValidationUtils.notNull(source, "Source list should not be null");
        return new ArrayList<>(source.subList(fromIndex, toIndex));
    }

    public static void closeQuietly(final Closeable... closeables) {
        streamOf(closeables).forEach(ServiceUtils::closeCloseable);
    }

    public static void closeCloseable(final Closeable closeable) {
        if (Objects.isNull(closeable)) {
            return;
        }
        try {
            closeable.close();
        } catch (Throwable t) {
            log.warn(String.format("Error occurred while closing source = {%s}", closeable), t);
        }
    }

    /**
     * Returns concatenated {@link List} of {@code T} items by input array of {@link List}s
     *
     * @param <T>         type of list item
     * @param collections - initial input array of {@link List}s
     * @return concatenated {@link List} of {@code T} items
     */
    @SuppressWarnings("varargs")
    public static <T> List<T> concatOf(final List<T>... collections) {
        return streamOf(collections).flatMap(Collection::stream).collect(toList());
    }

    /**
     * Returns {@link Map} of {@code K,V} items by input arguments
     *
     * @param <K>    type of key item
     * @param <V>    type of value item
     * @param keys   - initial input {@link List} of {@code K} keys
     * @param values - initial input {@link List} of {@code V} values
     * @return {@link Map} of {@code K,V}
     */
    @NonNull
    public static <K, V> Map<K, V> zipOf(final List<K> keys, final List<V> values) {
        ValidationUtils.notNull(keys, "Keys should not be null");
        ValidationUtils.notNull(values, "Values should not be null");

        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
    }

    /**
     * Returns {@link Stream} of {@code O} items by input arguments
     *
     * @param <K>   type of first item
     * @param <V>   type of last item
     * @param first - initial input {@link Stream} of {@code K} keys
     * @param last  - initial input {@link Stream} of {@code V} values
     * @return {@link Stream} of {@code O}
     */
    @NonNull
    static <K, V, O> Stream<O> zipOf(final Stream<K> first, final Stream<V> last, final BiFunction<K, V, O> combiner) {
        ValidationUtils.notNull(first, "First stream should not be null!");
        ValidationUtils.notNull(last, "Last stream should not be null!");
        ValidationUtils.notNull(combiner, "Combiner should not be null!");

        return StreamUtils.zip(first, last, combiner);
    }

    /**
     * Zips the given {@link Stream}s using the given {@link BiFunction}. The resulting {@link Stream} will have the
     * length of the shorter of the two, abbreviating the zipping when the shorter of the two {@link Stream}s is
     * exhausted.
     *
     * @param first    must not be {@literal null}.
     * @param last     must not be {@literal null}.
     * @param combiner must not be {@literal null}.
     * @return {@link Stream} of {@code T}
     * @throws NullPointerException if first is {@code null}
     * @throws NullPointerException if last is {@code null}
     * @throws NullPointerException if combiner is {@code null}
     * @since 2.1
     */
    @NonNull
    static <K, V, T> Stream<T> zip2(final Stream<K> first, final Stream<V> last, final BiFunction<K, V, T> combiner) {
        ValidationUtils.notNull(first, "Key stream should not be null!");
        ValidationUtils.notNull(last, "Value stream should not be null!");
        ValidationUtils.notNull(combiner, "Combiner should not be null!");

        final Spliterator<K> firsts = first.spliterator();
        final Spliterator<V> lasts = last.spliterator();

        long size = Long.min(firsts.estimateSize(), lasts.estimateSize());
        int characteristics = firsts.characteristics() & lasts.characteristics();
        boolean parallel = first.isParallel() || last.isParallel();

        return StreamSupport.stream(new Spliterators.AbstractSpliterator<>(size, characteristics) {
            @Override
            public boolean tryAdvance(final Consumer<? super T> action) {
                return firsts.tryAdvance(f -> lasts.tryAdvance(l -> action.accept(combiner.apply(f, l))));
            }
        }, parallel);
    }

    /**
     * Returns {@link List} of {@link Indexed} items by input {@link Stream}
     *
     * @param <T>    type of stream item
     * @param stream - initial input {@link Stream}
     * @return {@link List} of {@link Indexed} items
     */
    @NonNull
    public static <T> List<Indexed<T>> zipOf(final Stream<T> stream) {
        ValidationUtils.notNull(stream, "Stream should not be null!");
        return StreamUtils.zipWithIndex(stream).collect(toList());
    }

    /**
     * Returns {@link Map} by input {@link Stream} partitioned by {@link Predicate}
     *
     * @param <T>       type of stream item
     * @param <M>       type of collector result
     * @param stream    - initial input {@link Stream}
     * @param predicate - initial input {@link Predicate}
     * @param collector - initial input {@link Collector}
     * @return {@link Map} by input {@link Stream} partitioned by {@link Predicate}
     */
    public static <T, A, M extends Collection<T>> Map<Boolean, M> partitionBy(final Stream<T> stream, final Predicate<? super T> predicate, final Collector<T, A, M> collector) {
        ValidationUtils.notNull(stream, "Stream should not be null!");
        ValidationUtils.notNull(predicate, "Predicate should not be null!");
        ValidationUtils.notNull(collector, "Collector should not be null!");

        return stream.collect(Collectors.partitioningBy(predicate, collector));
    }

    /**
     * Returns {@link Stream} by input {@code T} item
     *
     * @param <T>    type of stream item
     * @param object - initial input {@code T}
     * @return {@link Stream}
     */
    public static <T> Stream<T> toStream(final T object) {
        ValidationUtils.notNull(object, "Object must not be null");

        if (object instanceof Stream) {
            return (Stream<T>) object;
        }
        if (object instanceof DoubleStream) {
            return (Stream<T>) ((DoubleStream) object).boxed();
        }
        if (object instanceof IntStream) {
            return (Stream<T>) ((IntStream) object).boxed();
        }
        if (object instanceof LongStream) {
            return (Stream<T>) ((LongStream) object).boxed();
        }
        if (object instanceof Collection) {
            return (Stream<T>) ((Collection<T>) object).stream();
        }
        if (object instanceof Iterable) {
            return (Stream<T>) stream(((Iterable<T>) object).spliterator(), false);
        }
        if (object instanceof Iterator) {
            return stream(spliteratorUnknownSize((Iterator<T>) object, ORDERED), false);
        }
        if (object instanceof Object[]) {
            return java.util.Arrays.stream((T[]) object);
        }
        if (object instanceof double[]) {
            return (Stream<T>) DoubleStream.of((double[]) object).boxed();
        }
        if (object instanceof int[]) {
            return (Stream<T>) IntStream.of((int[]) object).boxed();
        }
        if (object instanceof long[]) {
            return (Stream<T>) LongStream.of((long[]) object).boxed();
        }
        if (object.getClass().isArray() && object.getClass().getComponentType().isPrimitive()) {
            return IntStream.range(0, Array.getLength(object)).mapToObj(i -> (T) Array.get(object, i));
        }
        throw new IllegalArgumentException("Cannot convert instance of " + object.getClass().getName() + " into a Stream: " + object);
    }

    /**
     * Rethrow input {@link Throwable}
     *
     * @param <E>       type of throwable item
     * @param throwable - initial input {@link Throwable}
     * @throws E
     */
    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void doThrow(final Throwable throwable) throws E {
        ValidationUtils.notNull(throwable, "Throwable should not be null");
        throw (E) throwable;
    }

    /**
     * Rethrow {@link Exception} as unchecked
     *
     * @param <E>       type of exception item
     * @param exception - initial input {@link Exception}
     * @throws E
     */
    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void throwAsUnchecked(final Exception exception) throws E {
        ValidationUtils.notNull(exception, "Exception should not be null");
        throw (E) exception;
    }

    public static <T> Stream<T> enumerationAsStream3(final Enumeration<T> enumeration) {
        ValidationUtils.notNull(enumeration, "Enumeration should not be null");
        return StreamSupport.stream(
            new Spliterators.AbstractSpliterator<T>(Long.MAX_VALUE, Spliterator.ORDERED) {
                public boolean tryAdvance(final Consumer<? super T> action) {
                    if (enumeration.hasMoreElements()) {
                        action.accept(enumeration.nextElement());
                        return true;
                    }
                    return false;
                }

                public void forEachRemaining(final Consumer<? super T> action) {
                    while (enumeration.hasMoreElements()) action.accept(enumeration.nextElement());
                }
            }, false);
    }

    public static <T> Iterable<T> concat(final Iterable<? extends Iterable<T>> foo) {
        return () -> StreamSupport.stream(foo.spliterator(), false)
            .flatMap(i -> StreamSupport.stream(i.spliterator(), false))
            .iterator();
    }

    public static <T> T[] newArray(final Class<? extends T[]> type, int size) {
        ValidationUtils.notNull(type, "Class type should not be null");
        ValidationUtils.isTrue(size >= 0, "Size should be greater than or equal zero");

        return type.cast(Array.newInstance(type.getComponentType(), size));
    }

    public static <T> T[] newArray2(final Class<? extends T> type, int size) {
        ValidationUtils.notNull(type, "Class type should not be null");
        ValidationUtils.isTrue(size >= 0, "Size should be greater than or equal zero");

        return (T[]) Array.newInstance(type, size);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray3(final Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
    }

    public static <T> T[][] newMatrix(final Class<? extends T> type, int rowCount, int colCount) {
        ValidationUtils.notNull(type, "Class type should not be null");
        ValidationUtils.isTrue(rowCount >= 0, "Row count should be greater than or equal zero");
        ValidationUtils.isTrue(colCount >= 0, "Column count should be greater than or equal zero");

        return (T[][]) Array.newInstance(type, rowCount, colCount);
    }

    public static <T> T getInstance(final Class<? extends T> type) {
        ValidationUtils.notNull(type, "Class type should not be null");
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            log.error("ERROR: cannot initialize class instance={}, message={}", type, ex.getMessage());
        } catch (NoSuchMethodException ex) {
            log.error("ERROR: cannot execute method of class instance={}, message={}", type, ex.getMessage());
        } catch (InvocationTargetException ex) {
            log.error("ERROR: cannot get class instance={}, message={}", type, ex.getMessage());
        }
        return null;
    }

    public static <T, U> List<U> map(final Iterable<T> itemList, final Function<T, U> mapper) {
        return streamOf(itemList)
            .filter(Objects::nonNull)
            .map(mapper)
            .collect(toList());
    }

    public static <T, U> Optional<U> flatMap(final Optional<T> value, final Function<T, Optional<U>> mapper) {
        ValidationUtils.notNull(mapper, "Mapper should not be null");
        if (Objects.isNull(value) || !value.isPresent()) {
            return Optional.empty();
        }
        return mapper.apply(value.get());
    }

    public static <T> List<T> convertToList(final Optional<T> value) {
        return Optional.ofNullable(value).map(Optional::get).map(Collections::singletonList).orElse(Collections.emptyList());
    }

    //List<String> list = collect(opt, toList());
    //Set<String>  set  = collect(opt, toSet());
    public static <R, A, T> R collect(final Optional<T> value, final Collector<? super T, A, R> collector) {
        ValidationUtils.notNull(collector, "Collector should not be null");

        final A container = collector.supplier().get();
        Optional.ofNullable(value).ifPresent(v -> collector.accumulator().accept(container, v.get()));
        return collector.finisher().apply(container);
    }

    public static <T> Stream<T> streamOf(final Collection<T> collection) {
        return Optional.ofNullable(collection).map(Collection::stream).orElseGet(Stream::empty);
    }

    public static <T> List<Stream<T>> convertFlat(final List<Optional<T>> itemList) {
        return (List<Stream<T>>) streamOf(itemList)
            .flatMap(o -> o.map(Stream::of).orElseGet(Stream::empty))
            .collect(toList());
    }

    public static <T> List<T> convert(final List<Optional<T>> itemList) {
        return streamOf(itemList)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(toList());
    }

    public static <T, M> M convertTo(final T[] array, final IntPredicate indexPredicate, final Collector<T, ?, M> collector) {
        ValidationUtils.notNull(array, "Array should not be null");
        return IntStream
            .range(0, array.length)
            .filter(indexPredicate)
            .mapToObj(i -> array[i])
            .collect(collector);
    }

    public static <T> T[] arrayOf(final T[] first, final T[] second) {
        return (T[]) Stream.concat(streamOf(first), streamOf(second)).toArray();
    }

    public static <A, B, C> Function<A, C> composeBefore(final Function<A, B> f1, final Function<B, C> f2) {
        ValidationUtils.notNull(f1, "First function should not be null");
        ValidationUtils.notNull(f2, "Last function should not be null");
        return f1.andThen(f2);
    }

    public static <A, B, C> Function<A, C> composeAfter(final Function<B, C> f1, final Function<A, B> f2) {
        ValidationUtils.notNull(f1, "First function should not be null");
        ValidationUtils.notNull(f2, "Last function should not be null");
        return f1.compose(f2);
    }

    public static <T, K> Map<K, Integer> getMapSumBy(final Stream<T> stream, final Function<T, K> keys, final Function<T, Integer> values) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(keys, "Keys function should not be null");
        ValidationUtils.notNull(values, "Values function should not be null");
        return stream.collect(Collectors.toMap(keys, values, Integer::sum));
    }

    public static <E, K, U> Map<K, List<U>> toMapList(final Stream<E> stream, final Function<E, K> groupingBy, final Function<E, U> mapper) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(groupingBy, "GroupingBy function should not be null");
        ValidationUtils.notNull(mapper, "Mapper function should not be null");
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.mapping(mapper, Collectors.toList())));
    }

    public static <E> Map<Integer, Long> countOf(final Stream<E> stream, final Function<E, Integer> groupingBy) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(groupingBy, "GroupingBy function should not be null");
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.counting()));
    }

    public static <E, K, U> Map<K, Set<U>> toMapSet(final Stream<E> stream, final Function<E, K> groupingBy, final Function<E, U> mapper) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(groupingBy, "GroupingBy function should not be null");
        ValidationUtils.notNull(mapper, "Mapper function should not be null");
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.mapping(mapper, Collectors.toSet())));
    }

    public static <T, K> Map<K, Optional<T>> getMapMaxBy(final Stream<T> stream, final Function<T, K> groupingBy, final Comparator<? super T> comparator) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(groupingBy, "GroupingBy function should not be null");
        ValidationUtils.notNull(comparator, "Comparator should not be null");
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.maxBy(comparator)));
    }

    public static <T, K> Map<K, Optional<T>> getMapMinBy(final Stream<T> stream, final Function<T, K> groupingBy, final Comparator<? super T> comparator) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(groupingBy, "GroupingBy function should not be null");
        ValidationUtils.notNull(comparator, "Comparator should not be null");
        return stream.collect(Collectors.groupingBy(groupingBy, Collectors.minBy(comparator)));
    }

    public static <T> Optional<T> getMaxBy(final Stream<T> stream, final Comparator<? super T> comparator) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(comparator, "Comparator should not be null");
        return stream.collect(Collectors.maxBy(comparator));
    }

    public static <T> Optional<T> getMinBy(final Stream<T> stream, final Comparator<? super T> comparator) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        ValidationUtils.notNull(comparator, "Comparator should not be null");
        return stream.collect(Collectors.minBy(comparator));
    }

    public static <K, T> Map<K, List<T>> getSortedMapByKey(final Map<K, List<T>> map, final Comparator<? super K> comparator) {
        ValidationUtils.notNull(comparator, "Comparator should not be null");
        return Optional.ofNullable(map).orElseGet(Collections::emptyMap)
            .entrySet().stream()
            .sorted(Map.Entry.comparingByKey(comparator))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public static <T> List<T> subList(final List<T> list, int skip, int size) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        ValidationUtils.isTrue(skip > 0, "Skip value should not be negative");
        ValidationUtils.isTrue(size > 0, "Size value should not be negative");

        int total = list.size();
        int from = skip;
        from = Math.max(0, from);
        int to = from + size;
        if (from >= total || to <= 0 || from >= to) {
            return Collections.emptyList();
        }
        to = Math.min(total, to);
        return list.subList(from, to);
    }
}
