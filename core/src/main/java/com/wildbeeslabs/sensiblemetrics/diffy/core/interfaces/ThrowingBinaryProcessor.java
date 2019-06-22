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

/**
 * Processor interface declaration
 *
 * @param <T> type of consumed value
 * @param <R> type of produced value
 */
@FunctionalInterface
public interface ThrowingBinaryProcessor<F, T, R, E extends Throwable> {

    /**
     * Default numeric comparators
     */
    ThrowingBinaryProcessor<Double, Double, Double, Throwable> DOUBLE_MAX = (n1, n2) -> Double.compare(n1.doubleValue(), n2.doubleValue()) > 0 ? n1 : n2;
    ThrowingBinaryProcessor<Integer, Integer, Integer, Throwable> INTEGER_MAX = (n1, n2) -> n1.intValue() > n2.intValue() ? n1 : n2;
    ThrowingBinaryProcessor<Long, Long, Long, Throwable> LONG_MAX = (n1, n2) -> n1.longValue() > n2.longValue() ? n1 : n2;
    ThrowingBinaryProcessor<Float, Float, Float, Throwable> FLOAT_MAX = (n1, n2) -> Float.compare(n1.floatValue(), n2.floatValue()) > 0 ? n1 : n2;
    ThrowingBinaryProcessor<Short, Short, Short, Throwable> SHORT_MAX = (n1, n2) -> n1.shortValue() > n2.shortValue() ? n1 : n2;
    ThrowingBinaryProcessor<Byte, Byte, Byte, Throwable> BYTE_MAX = (n1, n2) -> n1.byteValue() > n2.byteValue() ? n1 : n2;
    ThrowingBinaryProcessor<Character, Character, Character, Throwable> CHAR_MAX = (n1, n2) -> n1 > n2 ? n1 : n2;

    /**
     * Returns result {@code R} by consuming the supplied arguments {@code F} and {@code T}
     *
     * @param first - initial input first argument {@code F} to be consumed
     * @param last  - initial input last argument {@code T} to be consumed
     * @return result {@code R}
     */
    @Nullable
    default R process(@Nullable final F first, @Nullable final T last) {
        try {
            return this.processOrThrow(first, last);
        } catch (final Throwable t) {
            ServiceUtils.doThrow(t);
        }
        return null;
    }

    /**
     * Processes the supplied argument {@code T} to result argument {@code R}, potentially throwing an exception
     *
     * @param first - initial input first argument {@code F} to consume
     * @param last  - initial input last argument {@code T} to consume
     * @return processed result {@code R}
     */
    @Nullable
    R processOrThrow(final F first, final T last) throws E;

    /**
     * Returns {@link ThrowingBinaryProcessor} by input parameters
     *
     * @param <V>   type of converted value
     * @param after - initial input {@link Processor} operator
     * @return {@link ThrowingBinaryProcessor}
     */
    @NonNull
    default <V, E extends Throwable> ThrowingBinaryProcessor<F, T, V, E> after(final Processor<R, V> after) {
        ValidationUtils.notNull(after, "Processor should not be null");
        return (final F first, final T last) -> after.process(this.process(first, last));
    }
}
