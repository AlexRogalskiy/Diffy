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

/**
 * Processor interface declaration
 *
 * @param <T> type of consumed value
 * @param <R> type of produced value
 */
@FunctionalInterface
public interface Processor<T, R> {

    /**
     * Processes the supplied argument {@code T} to result argument {@code R}, potentially throwing an exception
     *
     * @param value - initial input argument {@code T} to consume
     * @return processed result {@code R}
     */
    @Nullable
    R process(@Nullable final T value);

    /**
     * Returns composed {@link Processor} that first applies {@code before} {@link Processor} to the input {@code V} and then applies the result to the current {@link Processor}
     *
     * @param <V>    type of consumed value
     * @param before - initial input {@link Processor} to be composed
     * @return composed {@link Processor}
     */
    @NonNull
    default <V> Processor<V, R> before(final Processor<? super V, ? extends T> before) {
        Objects.requireNonNull(before, "Before processor should not be null");
        return (value) -> this.process(before.process(value));
    }

    /**
     * Returns composed {@link Processor} that first applies current {@link Processor} to
     * its input, and then applies the {@code after} {@link Processor} to the result
     */
    @NonNull
    default <V> Processor<T, V> after(final Processor<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "After processor should not be null");
        return (value) -> after.process(this.process(value));
    }

    /**
     * Returns {@link Processor} that always returns its input argument {@code T}
     *
     * @param <T> type of the input and output item
     * @return {@link Processor} that always returns its input argument {@code T}
     */
    @Nullable
    static <T> Processor<T, T> identity() {
        return value -> value;
    }
}
