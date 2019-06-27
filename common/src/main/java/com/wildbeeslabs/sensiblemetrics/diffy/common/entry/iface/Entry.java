/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Executable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.zipOf;

/**
 * Entry interface declaration
 *
 * @param <K> type of entry first value
 * @param <V> type of entry last value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public interface Entry<K, V> extends Serializable {

    /**
     * Returns entry first value {@code T}
     *
     * @return entry first value {@code T}
     */
    @Nullable
    K getFirst();

    /**
     * Returns entry last value {@code T}
     *
     * @return entry last value {@code T}
     */
    @Nullable
    V getLast();

    /**
     * Returns binary flag based on current first item value {@code V}
     *
     * @return true - if first item is {@code null}, false - otherwise
     */
    @JsonIgnore
    default boolean isFirstNull() {
        return Objects.isNull(this.getFirst());
    }

    /**
     * Returns binary flag based on current last item value {@code V}
     *
     * @return true - if last item is {@code null}, false - otherwise
     */
    @JsonIgnore
    default boolean isLastNull() {
        return Objects.isNull(this.getLast());
    }

    /**
     * Returns binary flag based on first/last values comparison
     *
     * @return true - if first/last values are equal, false - otherwise
     */
    default boolean areEqual() {
        return Objects.equals(this.getFirst(), this.getLast());
    }

    /**
     * A collector to create a {@link Map} from a {@link Stream} of {@link Entry}s.
     *
     * @param <K> type of entry first value
     * @param <V> type of entry last value
     * @return {@link Map} from a {@link Stream} of {@link Entry}s
     */
    static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getFirst, Entry::getLast);
    }

    /**
     * Invokes the given {@link BiConsumer} if all given {@link Optional} are present.
     *
     * @param <K>      type of entry first value
     * @param <V>      type of entry last value
     * @param first    - initial input entry first value {@link Optional}
     * @param last     - initial input entry last value {@link Optional}
     * @param consumer - initial input {@link BiConsumer} operator
     * @throws NullPointerException if first is {@code null}
     * @throws NullPointerException if last is {@code null}
     * @throws NullPointerException if consumer is {@code null}
     */
    static <K, V> void ifAllPresent(final Optional<K> first, final Optional<V> last, final BiConsumer<K, V> consumer) {
        ValidationUtils.notNull(first, "Optional entry first value should not be null!");
        ValidationUtils.notNull(last, "Optional entry last value should not be null!");
        ValidationUtils.notNull(consumer, "Binary consumer operator should not be null!");

        mapIfAllPresent(first, last, (f, l) -> {
            consumer.accept(f, l);
            return null;
        });
    }

    /**
     * Maps the values contained in the given {@link Optional} if both of them are present.
     *
     * @param <K>      type of entry first value
     * @param <V>      type of entry last value
     * @param <R>      type of function result
     * @param first    - initial input entry first value {@link Optional}
     * @param last     - initial input entry last value {@link Optional}
     * @param function - initial input {@link BiFunction} operator
     * @return {@link Optional} of {@link BiFunction} operator result {@code R}
     * @throws NullPointerException if first is {@code null}
     * @throws NullPointerException if last is {@code null}
     * @throws NullPointerException if function is {@code null}
     */
    static <K, V, R> Optional<R> mapIfAllPresent(final Optional<K> first, final Optional<V> last, final BiFunction<K, V, R> function) {
        ValidationUtils.notNull(first, "Optional entry first value should not be null!");
        ValidationUtils.notNull(last, "Optional entry last value should not be null!");
        ValidationUtils.notNull(function, "Binary function operator should not be null!");

        return first.flatMap(f -> last.map(l -> function.apply(f, l)));
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
    static <K, V, T> Stream<T> zip(final Stream<K> first, final Stream<V> last, final BiFunction<K, V, T> combiner) {
        return zip(first, last, combiner);
    }

    /**
     * Returns array of {@code R} items by input parameters
     *
     * @param <K>      type of key item
     * @param <V>      type of value item
     * @param <R>      type of result item
     * @param keys     - initial input array of {@code K} items
     * @param values   - initial input array of {@code V} values
     * @param operator - initial input {@link BiFunction} operator
     * @return array of {@code R} items
     */
    @NonNull
    static <K, V, R extends Entry<K, V>> R[] of(final K[] keys, final V[] values, final BiFunction<K, V, R> operator) {
        final R[] result = (R[]) new Entry[keys.length];
        Arrays.setAll(result, i -> operator.apply(keys[i], values[i]));
        return result;
    }

    /**
     * Returns {@link List} of {@link Entry}s by input parameters
     *
     * @param <K>    type of key item
     * @param <V>    type of value item
     * @param keys   - initial input {@link List} of {@code K} keys
     * @param values - initial input {@link List} of {@code V} values
     * @return {@link List} of {@link Entry}s
     */
    @NonNull
    static <K, V> List<Entry<K, V>> of(final List<K> keys, final List<V> values) {
        return zipOf(keys, values).entrySet().stream().map(entry -> DefaultEntry.of(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    /**
     * Invokes the given {@link Consumer} if the {@link Optional} is present or the {@link Executable} if not
     *
     * @param optional - initial input {@link Optional} value
     * @param consumer - initial input {@link Consumer} operator
     * @param runnable - initial input {@link Executable} operator
     */
    static <T> void ifPresentOrElse(final Optional<T> optional, final Consumer<T> consumer, final Runnable runnable) {
        ValidationUtils.notNull(optional, "Optional should not be null");
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(runnable, "Runnable should not be null");

        if (optional.isPresent()) {
            optional.ifPresent(consumer);
        } else {
            try {
                runnable.run();
            } catch (Throwable t) {
                ServiceUtils.doThrow(t);
            }
        }
    }

    /**
     * Constructs new {@link Entry} from the specified <code>Map.Entry</code>.
     *
     * @param entry - initial input {@link Map.Entry} to copy
     * @throws IllegalArgumentException if the entry is null
     */
    static <K, V> Entry<K, V> entryOf(final Map.Entry<K, V> entry) {
        ValidationUtils.notNull(entry, "Entry should not be null");
        return DefaultEntry.of(entry.getKey(), entry.getValue());
    }

    /**
     * Constructs new {@link Map.Entry} from the specified <code>Entry</code>.
     *
     * @param entry - initial input {@link Entry} to copy
     * @throws IllegalArgumentException if the entry is null
     */
    static <K, V> Map.Entry<K, V> entryOf(final Entry<K, V> entry) {
        ValidationUtils.notNull(entry, "Entry should not be null");
        return new AbstractMap.SimpleImmutableEntry<>(entry.getFirst(), entry.getLast());
    }

    /**
     * Constructs new {@link Function} from the specified <code>Function</code>.
     *
     * @param entry - initial input {@link Function} to constructor from
     * @throws IllegalArgumentException if the function is null
     */
    static <K, V> Function<K, Entry<K, V>> toEntry(final Function<K, V> function) {
        ValidationUtils.notNull(function, "Function should not be null");
        return key -> DefaultEntry.of(key, function.apply(key));
    }
}
