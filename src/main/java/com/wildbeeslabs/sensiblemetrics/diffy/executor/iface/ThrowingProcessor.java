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

/**
 * Throwing {@link Processor} interface declaration
 *
 * @param <T> type of consumed value
 * @param <R> type of produced value
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingProcessor<F, T, E extends Throwable> extends Processor<F, T> {

    /**
     * Processes input value {@code F} to {@code T}
     *
     * @param <T>   type of consumed value
     * @param <R>   type of produced value
     * @param <E>   type of throwable value
     * @param value - initial input {@code F} value to be processed
     * @return processed value {@code T}
     */
    @Override
    default T process(final F input) {
        try {
            return this.processOrThrow(input);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Processes input value {@code F} to {@code T}, potentially throwing an exception
     *
     * @param <T>   type of consumed value
     * @param <R>   type of produced value
     * @param <E>   type of throwable value
     * @param value - initial input {@code F} value to be processed
     * @return processed value {@code T}
     * @throws Throwable if processor produces exception
     */
    T processOrThrow(final F input) throws E;

    /**
     * Processes input value {@code F} by input {@link ThrowingProcessor}
     *
     * @param <T>       type of consumed value
     * @param <R>       type of produced value
     * @param <E>       type of throwable value
     * @param processor - initial input {@link ThrowingProcessor} operator
     * @param value     - initial input {@code F} value to be processed
     * @return processed value {@code T}
     * @throws IllegalArgumentException if processor produces exception
     */
    static <F, T, E extends Throwable> T processOrThrow(final ThrowingProcessor<F, T, E> processor, final F value) {
        try {
            return processor.process(value);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on processor = {%s}, message = {%s}", processor, t.getMessage()), t);
        }
    }
}
