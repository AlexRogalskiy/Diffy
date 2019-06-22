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
package com.wildbeeslabs.sensiblemetrics.diffy.core.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces.Processor;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.NonNull;

import javax.annotation.Nullable;
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
    @Nullable
    default T get() {
        try {
            return this.getOrThrow();
        } catch (Throwable t) {
            ServiceUtils.doThrow(t);
        }
        return null;
    }

    /**
     * Returns supplier result {@code T}, potentially throwing an exception
     *
     * @return supplier result {@code T}
     * @throw {@link Throwable}
     */
    @Nullable
    T getOrThrow() throws E;

    /**
     * Returns {@link ThrowingSupplier} by input parameters
     *
     * @param <R>       type of supplied value
     * @param converter - initial input {@link Processor} operator
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingSupplier<R, E> map(final Processor<T, R> converter) {
        ValidationUtils.notNull(converter, "Converter should not be null");
        return () -> converter.process(this.get());
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
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingSupplier<R, E> accept(final Processor<T, R> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return () -> processor.process(this.get());
    }

    /**
     * Returns {@link ThrowingSupplier} operator
     *
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default ThrowingSupplier<Stream<T>, E> stream() {
        return () -> generate(this::get);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingSupplier<Stream<R>, E> stream(final Processor<T, R> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return () -> generate(this::get).map(processor::process);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingSupplier<Stream<R>, E> streamBefore(final Processor<T, R> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return () -> generate(() -> processor.process(this.get()));
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingSupplier<Stream<R>, E> streamAfter(final Processor<Stream<T>, Stream<R>> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return () -> processor.process(generate(this::get));
    }

    /**
     * Returns long {@link ThrowingSupplier}
     *
     * @return long {@link ThrowingSupplier}
     */
    @NonNull
    default ThrowingSupplier<LongStream, E> longStream() {
        return () -> LongStream.generate(() -> (Long) this.get());
    }

    /**
     * Returns integer {@link ThrowingSupplier}
     *
     * @return integer {@link ThrowingSupplier}
     */
    @NonNull
    default ThrowingSupplier<IntStream, E> intStream() {
        return () -> IntStream.generate(() -> (Integer) this.get());
    }

    /**
     * Returns double {@link ThrowingSupplier}
     *
     * @return double {@link ThrowingSupplier}
     */
    @NonNull
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
    @NonNull
    static <T, E extends Throwable> ThrowingSupplier<T, E> get(final ThrowingSupplier<T, E> supplier) {
        Objects.requireNonNull(supplier, "Supplier should not be null");
        return supplier::get;
    }

    /**
     * Returns value {@code T} by input {@link ThrowingSupplier}
     *
     * @param <T>      type of supplier value
     * @param <E>      type of throwable value
     * @param supplier - initial input {@link ThrowingSupplier} operator
     * @return supplier value {@code T}
     * @throws IllegalArgumentException if supplier produces exception
     */
    @Nullable
    static <T, E extends Throwable> T getOrThrow(final ThrowingSupplier<T, E> supplier) {
        Objects.requireNonNull(supplier, "Supplier should not be null");
        try {
            return supplier.getOrThrow();
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on supplier = {%s}, message = {%s}", supplier, t.getMessage()), t);
        }
    }

    /**
     * Wraps input {@link ThrowingSupplier} by input exception {@link Class}
     *
     * @param <T>      type of supplied value
     * @param <E>      type of exception value
     * @param supplier - initial input {@link ThrowingSupplier}
     * @param clazz    - initial input {@link Class}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    static <T, E extends Exception> ThrowingSupplier<T, E> wrapConsumer(final ThrowingSupplier<T, E> supplier, final Class<E> clazz) {
        Objects.requireNonNull(supplier, "Supplier should not be null");
        Objects.requireNonNull(clazz, "Class should not be null");

        return () -> {
            try {
                return supplier.getOrThrow();
            } catch (Throwable ex) {
                try {
                    ServiceUtils.throwAsUnchecked(clazz.cast(ex));
                } catch (ClassCastException eex) {
                    throw new RuntimeException(ex);
                }
            }
            return null;
        };
    }
}
