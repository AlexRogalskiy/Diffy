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
package com.wildbeeslabs.sensiblemetrics.diffy.stream.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.stream.impl.LazyStreamable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * {@link Streamable} interface declaration {@link Iterable}, {@link Supplier}
 */
@FunctionalInterface
public interface Streamable<T> extends Iterable<T>, Supplier<Stream<T>> {

    /**
     * Returns an empty {@link Streamable} instance
     *
     * @return empty {@link Streamable} of values {@code T}
     */
    static <T> Streamable<T> empty() {
        return Collections::emptyIterator;
    }

    /**
     * Returns a {@link Streamable} with the given elements {@code T}
     *
     * @param values the elements to be returned {@code T}
     * @return {@link Streamable} of values {@code T}
     */
    @SafeVarargs
    static <T> Streamable<T> of(final T... values) {
        return () -> Arrays.asList(values).iterator();
    }

    /**
     * Returns a {@link Streamable} for the given {@link Iterable} instance
     *
     * @param iterable - initial input iterable collection {@link Iterable}
     * @return {@link Streamable} of values {@code T}
     * @throws NullPointerException if iterable is {@code null}
     */
    static <T> Streamable<T> of(final Iterable<T> iterable) {
        Objects.requireNonNull(iterable, "Iterable should not be null!");
        return iterable::iterator;
    }

    /**
     * Returns streamable instance {@link Streamable} from input supplier {@link Supplier}
     *
     * @param <T>
     * @param supplier - initial input supplier {@link Supplier} of elements {@code T}
     * @return {@link Streamable} of values {@code T}
     */
    static <T> Streamable<T> of(final Supplier<? extends Stream<T>> supplier) {
        return LazyStreamable.from(supplier);
    }

    /**
     * Creates a non-parallel {@link Stream} of the underlying {@link Iterable} collection
     *
     * @return streamable instance {@link Streamable} of values {@code T}
     */
    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    /**
     * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one.
     *
     * @param mapper - initial input mapper instance {@link Function}
     * @return {@link Streamable} of values {@code T}
     * @throws NullPointerException if mapper is {@code null}
     * @see Stream#map(Function)
     */
    default <R> Streamable<R> map(final Function<? super T, ? extends R> mapper) {
        Objects.requireNonNull(mapper, "Mapper function should not be null!");
        return Streamable.of(() -> stream().map(mapper));
    }

    /**
     * Returns a new {@link Streamable} that will apply the given {@link Function} to the current one
     *
     * @param mapper - initial input mapper instance {@link Function}
     * @return streamable instance {@link Streamable} of values {@code T}
     * @throws NullPointerException if mapper is {@code null}
     * @see Stream#flatMap(Function)
     */
    default <R> Streamable<R> flatMap(final Function<? super T, ? extends Stream<? extends R>> mapper) {
        Objects.requireNonNull(mapper, "Mapper function should not be null!");
        return Streamable.of(() -> stream().flatMap(mapper));
    }

    /**
     * Returns a new {@link Streamable} instance that will apply the given filter {@link Predicate} to the current one.
     *
     * @param predicate - initial input predicate instance {@link Predicate}
     * @return {@link Streamable} of values {@code T}
     * @throws NullPointerException if predicate is {@code null}
     * @see Stream#filter(Predicate) of predicate {@code T}
     */
    default Streamable<T> filter(final Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate, "Predicate should not be null!");
        return Streamable.of(() -> stream().filter(predicate));
    }

    /**
     * Returns whether the current {@link Streamable} is empty
     *
     * @return true - if iterator is exhausted, false - otherwise
     */
    default boolean isEmpty() {
        return !iterator().hasNext();
    }

    /**
     * Creates a new {@link Streamable} instance from the current supplier {@link Supplier} and the given {@link Stream} concatenated.
     *
     * @param supplier - initial input supplier {@link Supplier} of elements {@code T}
     * @return {@link Streamable} of values {@code T}
     * @throws NullPointerException if supplier is {@code null}
     */
    default Streamable<T> and(final Supplier<? extends Stream<? extends T>> supplier) {
        Objects.requireNonNull(supplier, "Supplier should not be null!");
        return Streamable.of(() -> Stream.concat(this.stream(), supplier.get()));
    }

    /**
     * Returns stream instance {@link Stream} of values {@code T}
     *
     * @return stream instance {@link Stream} of values {@code T}
     */
    default Stream<T> get() {
        return stream();
    }
}
