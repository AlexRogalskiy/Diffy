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
package com.wildbeeslabs.sensiblemetrics.diffy.utility;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
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
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.formatMessage;
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

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link List}.
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link Set}.
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return collectingAndThen(toSet(), Collections::unmodifiableSet);
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

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input collection of {@link CompletableFuture} and {@link Executor} instance
     *
     * @param <T>      type of {@link CompletableFuture} result
     * @param executor - initial input {@link Executor} instance
     * @param futures  - initial input collection of {@link CompletableFuture}
     * @throws NullPointerException if futures is {@code null}
     */
    public static <T> void getResultAsync(final Executor executor, final CompletableFuture<T>... futures) {
        Objects.requireNonNull(futures, "Array of futures should not be null");
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
        Objects.requireNonNull(future, "Future should not be null");
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
        Objects.requireNonNull(future, "Future should not be null");
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
        Objects.requireNonNull(list, "List should not be null");
        Objects.requireNonNull(predicate, "Predicate should not be null");

        return listOf(list).stream().filter(predicate).findFirst().isPresent();
    }

    /**
     * Returns converted value by converter instance {@link Converter}
     *
     * @param <T>       type of input element to be converted from by operation
     * @param <R>       type of input element to be converted to by operation
     * @param value     - initial argument value to be converted
     * @param converter - initial converter to process on {@link Converter}
     * @return converted value
     * @throws NullPointerException if converter is {@code null}
     */
    @Nullable
    public static <T, R> R convert(final T value, final Converter<T, R> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return converter.convert(value);
    }

    /**
     * Returns converted from string value to type {@link Class} by method name {@link String}
     *
     * @param value        - initial argument value to be converted {@link String}
     * @param toType       - initial type to be converted to {@link Converter}
     * @param parserMethod - initial method name to process the conversion {@link String}
     * @return converted value {@link Object}
     * @throws NullPointerException if toType is {@code null}
     */
    @Nullable
    public static Object convert(final String value, final Class<?> toType, final String parserMethod) {
        Objects.requireNonNull(toType, "Destination type should not be null");
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
        Objects.requireNonNull(predicate, "Predicate should not be null");
        Objects.requireNonNull(reducer, "Reducer should not be null");

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
        Objects.requireNonNull(predicate, "Predicate should not be null");
        Objects.requireNonNull(reducer, "Reducer should not be null");
        Objects.requireNonNull(supplier, "Supplier should not be null");

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
        Objects.requireNonNull(iterable, "Iterable should not be null");
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
        final T[] array = newArray(type, collection.size());
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
        Objects.requireNonNull(iterator, "Source iteratorOf should not be null");
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
        return streamOf(iterable).collect(Collectors.toList());
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
        return streamOf(elements).collect(Collectors.toList());
    }

    @NonNull
    public static <T> Iterator<T> iteratorOf(final T... elements) {
        return iterableOf(elements).iterator();
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newArray(final Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
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
        Objects.requireNonNull(source, "Source list should not be null");
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
        return streamOf(collections).flatMap(Collection::stream).collect(Collectors.toList());
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
        Objects.requireNonNull(keys, "Keys should not be null");
        Objects.requireNonNull(values, "Values should not be null");
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
        Objects.requireNonNull(first, "First stream should not be null!");
        Objects.requireNonNull(last, "Last stream should not be null!");
        Objects.requireNonNull(combiner, "Combiner should not be null!");

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
        Objects.requireNonNull(first, "Key stream should not be null!");
        Objects.requireNonNull(last, "Value stream should not be null!");
        Objects.requireNonNull(combiner, "Combiner should not be null!");

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
        Objects.requireNonNull(stream, "Stream should not be null!");
        return StreamUtils.zipWithIndex(stream).collect(Collectors.toList());
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
    public static <T, A, M extends Collection<T>> Map<Boolean, M> partitionBy(@NonNull final Stream<T> stream, final Predicate<? super T> predicate, final Collector<T, A, M> collector) {
        Objects.requireNonNull(stream, "Stream should not be null!");
        Objects.requireNonNull(predicate, "Predicate should not be null!");
        Objects.requireNonNull(collector, "Collector should not be null!");

        return stream.collect(Collectors.partitioningBy(predicate, collector));
    }

    /**
     * Returns {@link Supplier} by input parameters
     *
     * @param <T>       type of value to convert from
     * @param <R>       type of value to convert to
     * @param supplier  - initial input {@link Supplier}
     * @param converter - initial input {@link Converter}
     * @return {@link Supplier}
     */
    public static <T, R> Supplier<R> ifSupplierNotNullDo(final Supplier<T> supplier, final Converter<T, R> converter) {
        return () -> {
            final T val = supplier.get();
            if (Objects.isNull(val)) {
                return null;
            }
            return converter.convert(val);
        };
    }

    /**
     * Returns {@link Stream} by input {@code T} item
     *
     * @param <T>    type of stream item
     * @param object - initial input {@code T}
     * @return {@link Stream}
     */
    public static <T> Stream<T> toStream(final T object) {
        Objects.requireNonNull(object, "Object must not be null");
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
        Objects.requireNonNull(throwable, "Throwable should not be null");
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
        Objects.requireNonNull(exception, "Exception should not be null");
        throw (E) exception;
    }

    public static <T> Stream<T> enumerationAsStream3(final Enumeration<T> enumeration) {
        Objects.requireNonNull(enumeration, "Enumeration should not be null");
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
}
