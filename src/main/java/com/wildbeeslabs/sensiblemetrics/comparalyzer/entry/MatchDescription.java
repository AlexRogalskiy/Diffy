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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.entry;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * Match description declaration
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface MatchDescription extends Serializable {

    /**
     * A description that consumes input but does nothing.
     */
    MatchDescription DEFAULT_EMPTY_MATCH_DESCRIPTION = new EmptyMatchDescription();

    /**
     * Appends some plain text to the description.
     */
    MatchDescription append(final String text);

    /**
     * Appends an arbitary value to the description.
     */
    MatchDescription append(final Object value);

    /**
     * Appends a list of values to the description.
     */
    <T> MatchDescription append(final String start, final String separator, final String end, final T... values);

    /**
     * Appends a list of values to the description.
     */
    <T> MatchDescription append(final String start, final String separator, final String end, final Iterable<T> values);


    @EqualsAndHashCode
    @ToString
    final class EmptyMatchDescription implements MatchDescription {
        @Override
        public MatchDescription append(final String text) {
            return this;
        }

        @Override
        public MatchDescription append(final Object value) {
            return this;
        }

        @Override
        public <T> MatchDescription append(final String start, final String separator, final String end, final T... values) {
            return this;
        }

        @Override
        public <T> MatchDescription append(final String start, final String separator, final String end, final Iterable<T> values) {
            return this;
        }
    }
}
