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

import java.io.IOException;

/**
 * String match description declaration
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

    private final Appendable out;

    /**
     * Default empty string match description constructor
     */
    public StringMatchDescription() {
        this(new StringBuilder());
    }

    /**
     * Default string match description constructor with input appendable output source {@link Appendable}
     *
     * @param out
     */
    public StringMatchDescription(final Appendable out) {
        this.out = out;
    }

    /**
     * Appends input string value to current description {@link MatchDescription}
     *
     * @param value - initial input string value to be appended {@link String}
     * @return current description instance {@link MatchDescription}
     */
    @Override
    public MatchDescription append(final String value) {
        try {
            out.append(value);
        } catch (IOException e) {
            String errorMessage = String.format("ERROR: cannot append value={%s}, message={%s}", value, e.getMessage());
            log.error(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
        return this;
    }
}
