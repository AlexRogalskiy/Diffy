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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.description.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.description.MatchDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Base match description declaration
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public class BaseMatchDescription implements MatchDescription {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8697254240651372713L;

    /**
     * Appends input string value to current description {@link MatchDescription}
     *
     * @param value - initial input string value to be appended {@link String}
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public MatchDescription append(final String value) {
        append(value);
        return this;
    }

    /**
     * Appends input object value to current description {@link MatchDescription}
     *
     * @param value - initial input object value to be appended {@link Object}
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public MatchDescription append(final Object value) {
        append(value);
        return this;
    }

    /**
     * Appends input collection of values to current description {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     - initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input collection of values to be appended
     * @param <T>
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public <T> MatchDescription append(final String start, final String delimiter, final String end, final T... values) {
        return append(start, delimiter, end, Arrays.asList(values));
    }

    /**
     * Appends input iterable collection of values to current description {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     -  initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input iterable collection of values to be appended {@link Iterable}
     * @param <T>
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public <T> MatchDescription append(final String start, final String delimiter, final String end, final Iterable<T> values) {
        boolean separate = false;
        append(start);
        final Iterator<T> it = values.iterator();
        while (it.hasNext()) {
            if (separate) append(delimiter);
            append(it.next());
            separate = true;
        }
        append(end);
        return this;
    }
}
