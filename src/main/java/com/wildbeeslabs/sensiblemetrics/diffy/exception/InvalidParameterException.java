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

/**
 * Invalid parameter {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidParameterException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5312338835743224799L;

    /**
     * Invalid parameter exception constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidParameterException(final String message) {
        super(message);
    }

    /**
     * Invalid parameter exception constructor with initial input {@link Throwable}
     *
     * @param cause - initial input cause target {@link Throwable}
     */
    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }

    /**
     * Invalid parameter operation exception constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input cause target {@link Throwable}
     */
    public InvalidParameterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidParameterException} instance by input parameters
     *
     * @param message   - initial input raw message {@link String}
     * @param throwable - initial input cause target {@link Throwable}
     * @return {@link InvalidParameterException}
     */
    public static final InvalidParameterException throwInvalidParameter(final String message, final Throwable throwable) {
        throw new InvalidParameterException(message, throwable);
    }

    /**
     * Returns {@link InvalidParameterException} instance by input parameters
     *
     * @param message - initial input raw message {@link String}
     * @return {@link InvalidParameterException}
     */
    public static final InvalidParameterException throwInvalidParameter(final String message) {
        throw new InvalidParameterException(message);
    }
}
