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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Default {@link BigDecimal} {@link NumericConverter} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BigDecimalConverter extends NumericConverter<BigDecimal> {

    /**
     * Default significant decimal places
     */
    private final int significantDecimalPlaces;

    /**
     * Default big decimal constructor
     */
    public BigDecimalConverter() {
        this(0);
    }

    /**
     * Default big decimal constructor with initial significant decimal places
     *
     * @param significantDecimalPlaces - initial input significant decimal places argument
     */
    public BigDecimalConverter(int significantDecimalPlaces) {
        this.significantDecimalPlaces = significantDecimalPlaces;
    }

    /**
     * Returns big decimal value {@link BigDecimal} by input argument {@link Long}
     *
     * @param value - initial input argument value {@link Long}
     * @return {@link BigDecimal} converted value
     */
    protected BigDecimal valueOf(final long value) {
        return BigDecimal.valueOf(value, this.getSignificantDecimalPlaces());
    }

    /**
     * Returns big decimal value {@link BigDecimal} by input argument {@link String}
     *
     * @param value - initial input argument value {@link String}
     * @return {@link BigDecimal} converted value
     */
    @Override
    protected BigDecimal valueOf(final String value) {
        return new BigDecimal(value);
    }
}
