/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface;

import java.io.Serializable;
import java.util.List;

/**
 * Delta interface declaration
 *
 * @param <T> type of chunk item
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface Chunk<T> extends Serializable {

    /**
     * Returns {@link Chunk} position
     *
     * @return {@link Chunk} position
     */
    int getPosition();

    /**
     * Returns {@link List} of chunk {@code T} items
     *
     * @return {@link List} of chunk {@code T} items
     */
    List<T> getLines();

    /**
     * Return the chunk size
     *
     * @return the chunk size
     */
    int size();

    /**
     * Returns the index of the last line of the chunk.
     *
     * @return the index of the last line of the chunk.
     */
    int last();

    /**
     * Verifies that this chunk's saved text matches the corresponding text in
     * the given sequence.
     *
     * @param target the sequence to verify against.
     */
    void verify(final List<T> target);
}
