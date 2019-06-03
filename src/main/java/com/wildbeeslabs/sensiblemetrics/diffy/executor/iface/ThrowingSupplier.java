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
 * Throwing supplier interface declaration
 *
 * @param <T> type of supplier value
 */
@FunctionalInterface
public interface ThrowingSupplier<T> {

    /**
     * Returns supplier result {@code T}, potentially throwing an exception
     *
     * @return supplier result {@code T}
     * @throw {@link Throwable}
     */
    T get() throws Throwable;

    /**
     * Returns value {@code T} by input {@link ThrowingSupplier}
     *
     * @param <T>      type of supplier value
     * @param supplier - initial input {@link ThrowingSupplier} to retrieve value from
     * @return supplier value {@code T}
     * @throws IllegalArgumentException if supplier produces exception
     */
    static <T> T getOrThrow(final ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on supplier = {%s}, message = {%s}", supplier, t.getMessage()), t);
        }
    }
}
