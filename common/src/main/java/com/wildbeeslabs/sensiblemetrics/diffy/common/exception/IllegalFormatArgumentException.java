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
package com.wildbeeslabs.sensiblemetrics.diffy.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.MissingFormatArgumentException;

/**
 * Excessive or missing argument {@link MissingFormatArgumentException} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IllegalFormatArgumentException extends MissingFormatArgumentException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5587502326682779522L;
    /**
     * Default expected value
     */
    private final int expected;
    /**
     * Default found value
     */
    private final int found;

    /**
     * Constructs an instance of this class with the actual argument length and the expected
     * one.
     */
    public IllegalFormatArgumentException(final String message, int expected, int found) {
        super(message);
        this.expected = expected;
        this.found = found;
    }

    @Override
    public String getMessage() {
        return String.format("Expected %d arguments but found %d", this.expected, this.found);
    }
}
