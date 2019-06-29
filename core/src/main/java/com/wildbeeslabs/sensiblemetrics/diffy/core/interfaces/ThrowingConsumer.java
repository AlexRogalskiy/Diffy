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
            ServiceUtils.doThrow(t);
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
     * @param processor - initial input {@link Processor} operator
     * @return {@link ThrowingConsumer}
     * @throws IllegalArgumentException if processor is {@code null}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingConsumer<R, E> map(final Processor<R, T> processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        return (value) -> this.accept(processor.process(value));
    }

    /**
     * Consumes by current {@link ThrowingConsumer} input {@link ThrowingSupplier}
     *
     * @param supplier - initial input {@link ThrowingSupplier}
     * @throws IllegalArgumentException if supplier is {@code null}
     */
    default void accept(final ThrowingSupplier<T, E> supplier) {
        ValidationUtils.notNull(supplier, "Supplier should not be null");
        this.accept(supplier.get());
    }

    /**
     * Returns {@link ThrowingConsumer} by input parameters
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingConsumer}
     * @throws IllegalArgumentException if processor is {@code null}
     */
    @NonNull
    default <R, E extends Throwable> ThrowingConsumer<R, E> accept(final Processor<R, T> processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        return (value) -> this.accept(processor.process(value));
    }

    /**
     * Returns {@link ThrowingConsumer} by input {@code T} item
     *
     * @param value - initial input argument {@code T} to be consumed
     * @return {@link ThrowingConsumer}
     */
    @NonNull
    default ThrowingConsumer<T, E> add(final T value) {
        this.accept(value);
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
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     * @throws IllegalArgumentException if processor is {@code null}
     */
    @NonNull
    default <R> ThrowingConsumer<R, E> collect(final Processor<R, Stream<T>> processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        return (value) -> processor.process(value).forEach(this::accept);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     * @throws IllegalArgumentException if processor is {@code null}
     */
    @NonNull
    default <R> Processor<Stream<T>, Stream<R>> collectAfter(final Processor<T, R> processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        return (value) -> value.peek(this::accept).map(processor::process);
    }

    /**
     * Returns {@link ThrowingSupplier} by input {@link Processor}
     *
     * @param processor - initial input {@link Processor}
     * @return {@link ThrowingSupplier}
     * @throws IllegalArgumentException if processor is {@code null}
     */
    @NonNull
    default <R> ThrowingConsumer<Stream<R>, E> collectBefore(final Processor<R, T> processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        return (value) -> value.map(processor::process).forEach(this::accept);
    }

    /**
     * Returns {@link ThrowingConsumer} by input parameters
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingConsumer} operator
     * @return {@link ThrowingConsumer}
     * @throws IllegalArgumentException if consumer is {@code null}
     */
    @NonNull
    static <T, E extends Throwable> ThrowingConsumer<T, E> accept(final ThrowingConsumer<T, E> consumer) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        return consumer::accept;
    }

    /**
     * Consumes the supplied argument {@code T} by input {@link ThrowingConsumer}
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingConsumer} operator
     * @param value    - initial input {@code T} value to be consumed
     * @throws IllegalArgumentException if consumer is {@code null}
     */
    static <T, E extends Throwable> void acceptOrThrow(final ThrowingConsumer<T, E> consumer, final T value) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        try {
            consumer.acceptOrThrow(value);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on consumer = {%s}, message = {%s}", consumer, t.getMessage()), t);
        }
    }

    /**
     * Wraps input {@link ThrowingConsumer} by input exception {@link Class}
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingConsumer}
     * @param clazz    - initial input {@link Class}
     * @return {@link ThrowingConsumer}
     * @throws IllegalArgumentException if consumer is {@code null}
     * @throws IllegalArgumentException if clazz is {@code null}
     */
    @NonNull
    static <T, E extends Exception> ThrowingConsumer<T, E> wrapConsumer(final ThrowingConsumer<T, E> consumer, final Class<E> clazz) {
        ValidationUtils.notNull(consumer, "Consumer should not be null");
        ValidationUtils.notNull(clazz, "Class should not be null");

        return (value) -> {
            try {
                consumer.acceptOrThrow(value);
            } catch (Throwable ex) {
                try {
                    ServiceUtils.throwAsUnchecked(clazz.cast(ex));
                } catch (ClassCastException eex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
