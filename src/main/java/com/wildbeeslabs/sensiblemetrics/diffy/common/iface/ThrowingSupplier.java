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
package com.wildbeeslabs.sensiblemetrics.diffy.common.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Stream.generate;

/**
 * Throwing {@link Supplier} interface declaration
 *
 * @param <T> type of supplied value
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingSupplier<T, E extends Throwable> extends Supplier<T> {

    /**
     * Returns supplier result {@code T}
     *
     * @return supplier result {@code T}
     */
    @Override
    default T get() {
        try {
            return this.getOrThrow();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Returns supplier result {@code T}, potentially throwing an exception
     *
     * @return supplier result {@code T}
     * @throw {@link Throwable}
     */
    T getOrThrow() throws E;

    /**
     * Returns value {@code T} by input {@link ThrowingSupplier}
     *
     * @param <T>      type of supplier value
     * @param <E>      type of throwable value
     * @param supplier - initial input {@link ThrowingSupplier} operator
     * @return supplier value {@code T}
     * @throws IllegalArgumentException if supplier produces exception
     */
    static <T, E extends Throwable> T getOrThrow(final ThrowingSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on supplier = {%s}, message = {%s}", supplier, t.getMessage()), t);
        }
    }

    /**
     * Returns {@link ThrowingSupplier} by input parameters
     *
     * @param <R>       type of supplied value
     * @param converter - initial input {@link Converter} operator
     * @return {@link ThrowingSupplier}
     */
    default <R, E extends Throwable> ThrowingSupplier<R, E> map(final Converter<T, R> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return () -> converter.convert(this.get());
    }

    /**
     * Accepts current {@link ThrowingSupplier} by input {@link ThrowingConsumer}
     *
     * @param consumer - initial input {@link ThrowingConsumer}
     */
    default void accept(final ThrowingConsumer<T, E> consumer) {
        Objects.requireNonNull(consumer, "Consumer should not be null");
        consumer.accept(this.get());
    }

    /**
     * Returns {@link ThrowingSupplier} by input parameters
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    default <R, E extends Throwable> ThrowingSupplier<R, E> accept(final ThrowingProcessor<T, R, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return () -> processor.process(this.get());
    }

    /**
     * Returns {@link ThrowingSupplier} operator
     *
     * @return {@link ThrowingSupplier}
     */
    default ThrowingSupplier<Stream<T>, E> stream() {
        return () -> generate(this::get);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    default <R> ThrowingSupplier<Stream<R>, E> stream(final ThrowingProcessor<T, R, E> processor) {
        return () -> generate(this::get).map(processor::process);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    default <R> ThrowingSupplier<Stream<R>, E> streamBefore(final ThrowingProcessor<T, R, E> processor) {
        return () -> generate(() -> processor.process(this.get()));
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    default <R> ThrowingSupplier<Stream<R>, E> streamAfter(final ThrowingProcessor<Stream<T>, Stream<R>, E> processor) {
        return () -> processor.process(generate(this::get));
    }

    /**
     * Returns long {@link ThrowingSupplier}
     *
     * @return long {@link ThrowingSupplier}
     */
    default ThrowingSupplier<LongStream, E> longStream(final long maxSize) {
        return () -> LongStream.generate(() -> (Long) this.get());
    }

    /**
     * Returns integer {@link ThrowingSupplier}
     *
     * @return integer {@link ThrowingSupplier}
     */
    default ThrowingSupplier<IntStream, E> intStream() {
        return () -> IntStream.generate(() -> (Integer) this.get());
    }

    /**
     * Returns double {@link ThrowingSupplier}
     *
     * @return double {@link ThrowingSupplier}
     */
    default ThrowingSupplier<DoubleStream, E> doubleStream() {
        return () -> DoubleStream.generate(() -> (Double) this.get());
    }

    /**
     * Returns {@link ThrowingSupplier} by input parameters
     *
     * @param <T>      type of supplied value
     * @param <E>      type of throwable value
     * @param supplier - initial input {@link ThrowingSupplier} operator
     * @return {@link ThrowingSupplier}
     */
    static <T, E extends Throwable> ThrowingSupplier<T, E> get(final ThrowingSupplier<T, E> supplier) {
        Objects.requireNonNull(supplier, "Supplier should not be null");
        return supplier::get;
    }
}
