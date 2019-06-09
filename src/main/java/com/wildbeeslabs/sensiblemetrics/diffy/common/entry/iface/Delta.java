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

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;

/**
 * Delta interface declaration
 *
 * @param <T> type of delta value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface Delta<T> extends Serializable {

    /**
     * Specifies the type of the delta
     */
    enum TYPE {
        /**
         * A change in the original.
         */
        CHANGE,
        /**
         * A delete from the original.
         */
        DELETE,
        /**
         * An insert into the original.
         */
        INSERT
    }

    /**
     * Returns entry first value {@code T}
     *
     * @return entry first value {@code T}
     */
    @Nullable
    Chunk<T> getOriginal();

    /**
     * Returns entry last value {@code T}
     *
     * @return entry last value {@code T}
     */
    @Nullable
    Chunk<T> getRevised();

    /**
     * Verifies that this delta can be used to patch the given text.
     *
     * @param target the text to patch.
     * @throws IllegalStateException if the patch cannot be applied.
     */
    void verify(final List<T> target);

    /**
     * Applies this delta as the patch for a given target
     *
     * @param target the given target
     * @throws IllegalStateException if {@link #verify(List)} fails
     */
    void applyTo(final List<T> target);

    /**
     * Returns delta {@link TYPE}
     *
     * @return delta {@link TYPE}
     */
    Delta.TYPE getType();
}
