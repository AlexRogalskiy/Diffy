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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.IOException;
import java.util.Iterator;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.listOf;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.StringUtils.*;
import static java.util.Arrays.asList;

/**
 * Base {@link MatchDescription} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public class BaseMatchDescription implements MatchDescription {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8697254240651372713L;

    /**
     * Default {@link Appendable} output stream
     */
    private final transient Appendable out;

    /**
     * Default base match description constructor with input output source {@link Appendable}
     *
     * @param out - initial input {@link Appendable} output source instance
     */
    public BaseMatchDescription(final Appendable out) {
        this.out = out;
    }

    /**
     * Appends input {@link String} value to current {@link MatchDescription}
     *
     * @param value - initial input string value to be appended {@link String}
     * @return {@link MatchDescription}
     */
    @Override
    public MatchDescription appendText(final String value) {
        return this.appendBy(value);
    }

    /**
     * Appends input {@link Object} value to current {@link MatchDescription}
     *
     * @param value - initial input object value to be appended {@link Object}
     * @return {@link MatchDescription}
     */
    @Override
    public MatchDescription append(final Object value) {
        return this.appendBy(value);
    }

    /**
     * Appends input {@link MatchDescription} to current description {@link MatchDescription}
     *
     * @param description - initial input {@link MatchDescription} to be appended {@link Object}
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public MatchDescription appendDescription(final MatchDescription description) {
        return this.appendBy(description);
    }

    /**
     * Appends input {@link Object} value to current {@link MatchDescription}
     *
     * @param value - initial input object value to be appended {@link Object}
     * @return {@link MatchDescription}
     */
    protected MatchDescription appendBy(final Object value) {
        try {
            this.getOut().append(convertValue(value));
        } catch (IOException e) {
            BadOperationException.throwError(formatMessage("ERROR: cannot append value: {%s}", value), e);
        }
        return this;
    }

    /**
     * Appends input collection of values to current {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     - initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input collection of values to be appended
     * @param <T>       type of input element to be processed by matchable operation
     * @return current {@link MatchDescription}
     */
    @Override
    public <T> MatchDescription append(final String start, final String delimiter, final String end, final T... values) {
        return this.append(start, delimiter, end, asList(values));
    }

    /**
     * Appends input iterableOf collection of values to current {@link MatchDescription} by start/delimiter/end tokens
     *
     * @param start     -  initial input start token {@link String}
     * @param delimiter - initial input delimiter token {@link String}
     * @param end       - initial input end token {@link String}
     * @param values    - initial input iterableOf collection of values to be appended {@link Iterable}
     * @param <T>       type of input element to be processed by matchable operation
     * @return current {@link MatchDescription}
     */
    @Override
    public <T> MatchDescription append(final String start, final String delimiter, final String end, final Iterable<? extends T> values) {
        boolean separate = false;
        this.append(start);
        final Iterator<? extends T> it = listOf(values).iterator();
        while (it.hasNext()) {
            if (separate) {
                this.append(delimiter);
            }
            this.append(wrapInBrackets.apply(it.next()));
            separate = true;
        }
        this.append(end);
        return this;
    }
}
