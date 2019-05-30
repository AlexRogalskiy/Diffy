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
package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Value matcher {@link RuntimeException} implementation
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ValueMatcherException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4545255095576355819L;

    /**
     * Default expected value
     */
    private final String expected;
    /**
     * Default actual value
     */
    private final String actual;

    /**
     * Create new ValueMatcherException
     *
     * @param message  description of exception
     * @param expected value expected by BiMatcher
     * @param actual   value being tested by BiMatcher
     */
    public ValueMatcherException(final String message, final String expected, final String actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * Create new ValueMatcherException
     *
     * @param message  description of exception
     * @param cause    cause of ValueMatcherException
     * @param expected value expected by BiMatcher
     * @param actual   value being tested by BiMatcher
     */
    public ValueMatcherException(final String message, final Throwable cause, final String expected, final String actual) {
        super(message, cause);
        this.expected = expected;
        this.actual = actual;
    }
}
