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

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Binary matcher {@link RuntimeException} implementation
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BiMatcherException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4545255095576355819L;

    /**
     * Default expected value {@link Object}
     */
    private final Object expected;
    /**
     * Default actual value {@link Object}
     */
    private final Object actual;

    /**
     * Binary matcher exception constructor by input parameters
     *
     * @param message  - initial input description of exception {@link String}
     * @param expected - initial input value expected by matcher {@link Object}
     * @param actual   - initial value being tested by matcher {@link Object}
     */
    public BiMatcherException(final String message, final Object expected, final Object actual) {
        super(message);
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * Binary matcher exception constructor by input parameters
     *
     * @param message  - initial input description of exception {@link String}
     * @param cause    - initial input cause of exception {@link Throwable}
     * @param expected - initial input value expected by matcher {@link Object}
     * @param actual   - initial value being tested by matcher {@link Object}
     */
    public BiMatcherException(final String message, final Throwable cause, final Object expected, final Object actual) {
        super(message, cause);
        this.expected = expected;
        this.actual = actual;
    }

    /**
     * Returns {@link BiMatcherException} by input parameters
     *
     * @param message  - initial input description of exception {@link String}
     * @param cause    - initial input cause of exception {@link Throwable}
     * @param expected - initial input value expected by matcher {@link Object}
     * @param actual   - initial value being tested by matcher {@link Object}
     * @return {@link BiMatcherException}
     */
    @Factory
    public static final BiMatcherException throwError(final String message, final Throwable cause, final Object expected, final Object actual) {
        throw new BiMatcherException(message, cause, expected, actual);
    }
}
