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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.entry;

import lombok.Data;

/**
 * Class that represents a credit card range.
 */
@Data
public class CreditCardRange {
    private final String low;
    private final String high;
    private final int minLen;
    private final int maxLen;
    private final int lengths[];

    /**
     * Create a credit card range specifier for use in validation
     * of the number syntax including the IIN range.
     * <p>
     * The low and high parameters may be shorter than the length
     * of an IIN (currently 6 digits) in which case subsequent digits
     * are ignored and may range from 0-9.
     * <br>
     * The low and high parameters may be different lengths.
     * e.g. Discover "644" and "65".
     * </p>
     *
     * @param low    the low digits of the IIN range
     * @param high   the high digits of the IIN range
     * @param minLen the minimum length of the entire number
     * @param maxLen the maximum length of the entire number
     */
    public CreditCardRange(final String low, final String high, int minLen, int maxLen) {
        this.low = low;
        this.high = high;
        this.minLen = minLen;
        this.maxLen = maxLen;
        this.lengths = null;
    }

    /**
     * Create a credit card range specifier for use in validation
     * of the number syntax including the IIN range.
     * <p>
     * The low and high parameters may be shorter than the length
     * of an IIN (currently 6 digits) in which case subsequent digits
     * are ignored and may range from 0-9.
     * <br>
     * The low and high parameters may be different lengths.
     * e.g. Discover "644" and "65".
     * </p>
     *
     * @param low     the low digits of the IIN range
     * @param high    the high digits of the IIN range
     * @param lengths array of valid lengths
     */
    public CreditCardRange(final String low, final String high, final int[] lengths) {
        this.low = low;
        this.high = high;
        this.minLen = -1;
        this.maxLen = -1;
        this.lengths = lengths.clone();
    }
}
