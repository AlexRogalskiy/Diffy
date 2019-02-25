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
package com.wildbeeslabs.sensiblemetrics.diffy.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.interfaces.impl.LazyStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Custom streamable interface declaration based on {@link Iterable}
 */
@FunctionalInterface
public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>> {

    /**
     * Returns an empty {@link Streamable}.
     *
     * @return empty streamable instance {@link Streamable}
     */
    default <T> Streamable<T> empty() {
        return Collections::emptyIterator;
    }

    /**
     * Returns a {@link Streamable} with the given elements.
     *
     * @param t the elements to return.
     * @return streamable instance {@link Streamable}
     */
    default <T> Streamable<T> of(final T... t) {
        return () -> Arrays.asList(t).iterator();
    }

    /**
     * Returns a {@link Streamable} for the given {@link Iterable}.
     *
     * @param iterable must not be {@literal null}.
     * @return streamable instance {@link Streamable}
     */
    default <T> Streamable<T> of(final Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "Iterable must not be null!");
        return iterable::iterator;
    }

    /**
     * Returns streamable instance {@link Streamable} from input supplier {@link Supplier}
     *
     * @param <T>
     * @param supplier - initial input supplier {@link Supplier}
     * @return streamable instance {@link Streamable}
     */
    default <T> Streamable<T> of(final Supplier<? extends Stream<T>> supplier) {
        return LazyStream.from(supplier);
    }

    /**
     * Creates a non-parallel {@link Stream} of the underlying {@link Iterable}.
     *
     * @return will never be {@literal null}.
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one.
     *
     * @param mapper must not be {@literal null}.
     * @return streamable instance {@link Streamable}
     * @see Stream#map(Function)
     */
    default <R> Streamable<R> map(final Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "Mapping interfaces must not be null!");
        return of(() -> stream().map(mapper));
    }

    /**
     * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one.
     *
     * @param mapper must not be {@literal null}.
     * @return streamable instance {@link Streamable}
     * @see Stream#flatMap(Function)
     */
    default <R> Streamable<R> flatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "Mapping interfaces must not be null!");
        return of(() -> stream().flatMap(mapper));
    }

    /**
     * Returns a new {@link Streamable} that will apply the given filter {@link Predicate} to the current one.
     *
     * @param predicate must not be {@literal null}.
     * @return streamable instance {@link Streamable}
     * @see Stream#filter(Predicate)
     */
    default Streamable<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "Filter predicate must not be null!");
        return of(() -> stream().filter(predicate));
    }

    /**
     * Returns whether the current {@link Streamable} is empty.
     *
     * @return true - if iterator is exhausted, false - otherwise
     */
    default boolean isEmpty() {
        return !iterator().hasNext();
    }

    /**
     * Creates a new {@link Streamable} from the current one and the given {@link Stream} concatenated.
     *
     * @param stream must not be {@literal null}.
     * @return streamable instance {@link Streamable}
     */
    default Streamable<T> and(final Supplier<? extends Stream<? extends T>> stream) {
        Objects.requireNonNull(stream, "Stream must not be null!");
        return of(() -> Stream.concat(this.stream(), stream.get()));
    }

    /**
     * Returns default stream instance {@link Stream}
     *
     * @return default stream instance {@link Stream}
     */
    default Stream<T> get() {
        return stream();
    }
}
