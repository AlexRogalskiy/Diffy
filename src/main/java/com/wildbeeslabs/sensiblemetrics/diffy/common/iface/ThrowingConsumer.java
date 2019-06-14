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
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Throwing {@link Consumer} interface declaration
 *
 * @param <T> type of consumed value
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> extends Consumer<T> {

    /**
     * Consumes the supplied argument {@code T}
     *
     * @param value - initial input argument {@code T} to be consumed
     */
    @Override
    default void accept(@Nullable final T value) {
        try {
            this.acceptOrThrow(value);
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Consumes the supplied argument {@code T}, potentially throwing an exception
     *
     * @param value - initial input argument {@code T} to be consumed
     * @throw {@link Throwable} if consumer produces exception
     */
    void acceptOrThrow(@Nullable final T value) throws E;

    /**
     * Returns {@link ThrowingConsumer} by input parameters
     *
     * @param <R>       type of consumed value
     * @param converter - initial input {@link Converter} operator
     * @return {@link ThrowingConsumer}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingConsumer<R, E> map(final Converter<R, T> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return (value) -> this.accept(converter.convert(value));
    }

    /**
     * Consumes by current {@link ThrowingConsumer} input {@link ThrowingSupplier}
     *
     * @param supplier - initial input {@link ThrowingSupplier}
     */
    default void accept(final ThrowingSupplier<T, E> supplier) {
        Objects.requireNonNull(supplier, "Supplier should not be null");
        this.accept(supplier.get());
    }

    /**
     * Returns {@link ThrowingConsumer} by input parameters
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingConsumer}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingConsumer<R, E> accept(final ThrowingProcessor<R, T, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return (value) -> this.accept(processor.process(value));
    }

    /**
     * Returns {@link ThrowingConsumer} by input {@code T} item
     *
     * @param t - initial input {@code T} item
     * @return {@link ThrowingConsumer}
     */
    @NonNull
    default ThrowingConsumer<T, E> add(final T t) {
        this.accept(t);
        return this;
    }

    /**
     * Returns {@link ThrowingSupplier} operator
     *
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default ThrowingConsumer<Stream<T>, E> collect() {
        return (value) -> value.forEach(this::accept);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingConsumer<R, E> collect(final ThrowingProcessor<R, Stream<T>, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return (value) -> processor.process(value).forEach(this::accept);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingProcessor<Stream<T>, Stream<R>, E> collectAfter(final ThrowingProcessor<T, R, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return (value) -> value.peek(this::accept).map(processor::process);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link ThrowingProcessor}
     *
     * @param processor - initial input {@link ThrowingProcessor}
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    default <R> ThrowingConsumer<Stream<R>, E> collectBefore(final ThrowingProcessor<R, T, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return (value) -> value.map(processor::process).forEach(this::accept);
    }

    /**
     * Returns {@link ThrowingConsumer} by input parameters
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingConsumer} operator
     * @return {@link ThrowingConsumer}
     */
    @NonNull
    static <T, E extends Throwable> ThrowingConsumer<T, E> accept(final ThrowingConsumer<T, E> consumer) {
        Objects.requireNonNull(consumer, "Consumer should not be null");
        return consumer::accept;
    }

    /**
     * Consumes the supplied argument {@code T} by input {@link ThrowingConsumer}
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingConsumer} operator
     * @param value    - initial input {@code T} value to be consumed
     * @throws IllegalArgumentException if consumer produces exception
     */
    static <T, E extends Throwable> void acceptOrThrow(final ThrowingConsumer<T, E> consumer, final T value) {
        Objects.requireNonNull(consumer, "Consumer should not be null");
        try {
            consumer.accept(value);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on consumer = {%s}, message = {%s}", consumer, t.getMessage()), t);
        }
    }
}
