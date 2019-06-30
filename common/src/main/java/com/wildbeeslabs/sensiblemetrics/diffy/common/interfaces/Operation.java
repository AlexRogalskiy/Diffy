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
package com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidOperationException;

import java.util.function.BiFunction;

/**
 * Operation {@link BiFunction} interface declaration
 *
 * @param <T> type of operand value
 * @param <R> type of operation result
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2018-11-30
 */
@FunctionalInterface
public interface Operation<T, R> extends BiFunction<T, T, R> {

    /**
     * Returns {@code R} by input arguments
     *
     * @param x - initial input first {@code T} argument
     * @param y - initial input last {@code T} argument
     * @return {@code R} result of operation
     */
    default R safeApply(final T x, final T y) {
        try {
            return this.apply(x, y);
        } catch (Exception e) {
            throw new InvalidOperationException(e);
        }
    }
}

