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

import java.util.function.Supplier;

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
}
