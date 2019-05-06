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
package com.wildbeeslabs.sensiblemetrics.diffy.entry.iface;

import java.io.Serializable;

/**
 * Difference entry declaration
 *
 * @param <T> type of element to be stored by entry
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface DiffEntry<T> extends Serializable {

    /**
     * Returns property name {@link String} to compare by
     *
     * @return property name {@link String}
     */
    String getPropertyName();

    /**
     * Returns property value {@code T} of first argument
     *
     * @return property value {@code T}
     */
    T getFirst();

    /**
     * Returns property value {@code T} of last argument
     *
     * @return property value {@code T}
     */
    T getLast();
}
