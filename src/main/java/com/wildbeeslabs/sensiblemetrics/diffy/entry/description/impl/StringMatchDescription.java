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
package com.wildbeeslabs.sensiblemetrics.diffy.entry.description.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.entry.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BadOperationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * String {@link BaseMatchDescription} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StringMatchDescription extends BaseMatchDescription {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1115429688276595763L;

    /**
     * Default appendable output stream {@link Appendable}
     */
    private final transient Appendable out;

    /**
     * Default empty string match description constructor
     */
    public StringMatchDescription() {
        this(new StringBuilder());
    }

    /**
     * Default string match description constructor with input appendable output source {@link Appendable}
     *
     * @param out - initial input {@link Appendable} output source instance
     */
    public StringMatchDescription(final Appendable out) {
        this.out = out;
    }

    /**
     * Appends input string value to curernt description {@link MatchDescription}
     *
     * @param value - initial input value to be appended {@link String}
     * @return current {@link MatchDescription}
     */
    @Override
    public MatchDescription append(final String value) {
        try {
            this.getOut().append(value);
        } catch (IOException e) {
            BadOperationException.throwBadOperation(formatMessage("ERROR: cannot append value: {%s}", value), e);
        }
        return this;
    }
}
