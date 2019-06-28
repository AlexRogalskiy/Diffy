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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.exception;

import com.wildbeeslabs.sensiblemetrics.diffy.formatter.helpers.ConversionCategory;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.IllegalFormatConversionException;

/**
 * Category {@link IllegalFormatConversionException} implementation
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IllegalFormatConversionCategoryException extends IllegalFormatConversionException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2798100815420030619L;

    /**
     * Default expected {@link ConversionCategory}
     */
    private final ConversionCategory expected;
    /**
     * Default found {@link ConversionCategory}
     */
    private final ConversionCategory found;

    /**
     * Constructs an instance of this class with the mismatched conversion and the expected one.
     */
    public IllegalFormatConversionCategoryException(final ConversionCategory expected, final ConversionCategory found) {
        super(expected.chars.length() == 0 ? '-' : expected.chars.charAt(0), found.types == null ? Object.class : found.types[0]);
        this.expected = expected;
        this.found = found;
    }

    @Override
    public String getMessage() {
        return String.format("Expected category %s but found %s.", expected, found);
    }
}
