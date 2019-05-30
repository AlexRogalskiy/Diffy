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
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Binary match operation {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BiMatchOperationException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -6770900397605904118L;

    /**
     * Match operation exception constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public BiMatchOperationException(final String message) {
        super(message);
    }

    /**
     * Match operation exception constructor with initial input {@link Throwable}
     *
     * @param cause - initial input {@link Throwable}
     */
    public BiMatchOperationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Match operation exception constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input {@link Throwable}
     */
    public BiMatchOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link BiMatchOperationException} instance by input parameters
     *
     * @param value1    - initial input first source target {@link Object}
     * @param value2    - initial input last source target {@link Object}
     * @param throwable - initial input cause instance {@link Throwable}
     * @return {@link BiMatchOperationException}
     */
    public static final BiMatchOperationException throwIncorrectMatch(final Object value1, final Object value2, final Throwable throwable) {
        throw new BiMatchOperationException(formatMessage("ERROR: cannot process match operation on first target = {%s}, last target = {%s}", value1, value2), throwable);
    }
}
