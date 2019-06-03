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

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Entry declaration
 *
 * @param <K> type of entry first value
 * @param <V> type of entry last value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
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
        return Objects.equals(getFirst(), getLast());
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
        Objects.requireNonNull(first, "Optional entry first value should not be null!");
        Objects.requireNonNull(last, "Optional entry last value should not be null!");
        Objects.requireNonNull(consumer, "Binary consumer operator should not be null!");

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
        Objects.requireNonNull(first, "Optional entry first value should not be null!");
        Objects.requireNonNull(last, "Optional entry last value should not be null!");
        Objects.requireNonNull(function, "Binary function operator should not be null!");

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
     * @throws NullPointerException if first is {@code null}
     * @throws NullPointerException if last is {@code null}
     * @throws NullPointerException if combiner is {@code null}
     * @return {@link Stream} of {@code T}
     * @since 2.1
     */
    static <K, V, T> Stream<T> zip(final Stream<K> first, final Stream<V> last, final BiFunction<K, V, T> combiner) {
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
}
