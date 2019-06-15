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

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiConsumer;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.doThrow;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.throwAsUnchecked;

/**
 * Throwing {@link BiConsumer} interface declaration
 *
 * @param <T> type of first consumed value
 * @param <U> type of last consumed value
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingBinaryConsumer<T, U, E extends Throwable> extends BiConsumer<T, U> {

    /**
     * Consumes the supplied arguments {@code T} and {@code U}
     *
     * @param first - initial input first argument {@code T} to be consumed
     * @param last  - initial input last argument {@code U} to be consumed
     */
    @Override
    default void accept(@Nullable final T first, @Nullable final U last) {
        try {
            this.acceptOrThrow(first, last);
        } catch (final Throwable t) {
            doThrow(t);
        }
    }

    /**
     * Consumes the supplied arguments {@code T} and {@code U}, potentially throwing an exception
     *
     * @param first - initial input first argument {@code T} to be consumed
     * @param last  - initial input last argument {@code U} to be consumed
     */
    void acceptOrThrow(@Nullable final T first, @Nullable final U last) throws E;

    /**
     * Consumes the supplied arguments {@code T} and {@code U} by input {@link ThrowingBinaryConsumer}
     *
     * @param <T>      type of consumed value
     * @param <E>      type of throwable value
     * @param consumer - initial input {@link ThrowingBinaryConsumer} operator
     * @param first    - initial input first argument {@code T} to be consumed
     * @param last     - initial input last argument {@code U} to be consumed
     * @throws IllegalArgumentException if consumer produces exception
     */
    static <T, U, E extends Throwable> void acceptOrThrow(final ThrowingBinaryConsumer<T, U, E> consumer, final T first, final U last) {
        Objects.requireNonNull(consumer, "Binary consumer should not be null");
        try {
            consumer.acceptOrThrow(first, last);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on binary consumer = {%s}, message = {%s}", consumer, t.getMessage()), t);
        }
    }

    /**
     * Wraps input {@link ThrowingBinaryConsumer} by input exception {@link Class}
     *
     * @param <T>      type of first consumed value
     * @param <U>      type of last consumed value
     * @param <E>      type of exception value
     * @param consumer - initial input {@link ThrowingBinaryConsumer}
     * @param clazz    - initial input {@link Class}
     * @return {@link ThrowingBinaryConsumer}
     */
    @NonNull
    static <T, U, E extends Exception> ThrowingBinaryConsumer<T, U, E> wrapConsumer(final ThrowingBinaryConsumer<T, U, E> consumer, final Class<E> clazz) {
        Objects.requireNonNull(consumer, "Consumer should not be null");
        Objects.requireNonNull(clazz, "Class should not be null");

        return (first, last) -> {
            try {
                consumer.acceptOrThrow(first, last);
            } catch (Throwable ex) {
                try {
                    throwAsUnchecked(clazz.cast(ex));
                } catch (ClassCastException eex) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
