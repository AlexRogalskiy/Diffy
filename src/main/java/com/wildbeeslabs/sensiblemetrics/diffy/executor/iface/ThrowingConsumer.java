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
package com.wildbeeslabs.sensiblemetrics.diffy.executor.iface;

import java.util.function.Consumer;

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
     * @param <T>   type of consumed value
     * @param value - initial input argument {@code T} to be consumed
     */
    @Override
    default void accept(final T value) {
        try {
            acceptOrThrow(value);
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Consumes the supplied argument {@code T}, potentially throwing an exception
     *
     * @param <T>   type of consumed value
     * @param <E>   type of throwable value
     * @param value - initial input argument {@code T} to be consumed
     * @throw {@link Throwable} if consumer produces exception
     */
    void acceptOrThrow(final T value) throws E;

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
        try {
            consumer.accept(value);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on consumer = {%s}, message = {%s}", consumer, t.getMessage()), t);
        }
    }
}
