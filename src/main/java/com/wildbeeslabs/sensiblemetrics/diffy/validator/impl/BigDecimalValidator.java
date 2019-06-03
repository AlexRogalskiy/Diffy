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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Big decimal {@link Validator} implementation
 */
public class BigDecimalValidator implements Validator<BigDecimal> {

    /**
     * Default {@link BigDecimal} delta
     */
    private final BigDecimal delta;
    /**
     * Default {@link BigDecimal} value
     */
    private final BigDecimal value;

    /**
     * Big decimal validator constructor by input parameters
     *
     * @param value - initial input {@link BigDecimal} value
     * @param error - initial input {@link BigDecimal} error
     */
    public BigDecimalValidator(final BigDecimal value, final BigDecimal error) {
        this.delta = error;
        this.value = value;
    }

    /**
     * Returns true if input value {@link BigDecimal} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@link BigDecimal}
     * @return true - if input value {@link BigDecimal} is valid, false - otherwise
     */
    @Override
    public boolean validate(final BigDecimal value) {
        return this.actualDelta(value).compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * Returns {@link BigDecimal} delta by input value {@link BigDecimal}
     *
     * @param item - initial input value {@link BigDecimal}
     * @return {@link BigDecimal} delta
     */
    private BigDecimal actualDelta(@NotNull final BigDecimal item) {
        return item.subtract(this.value, MathContext.DECIMAL128).abs().subtract(this.delta, MathContext.DECIMAL128).stripTrailingZeros();
    }

    /**
     * Returns new big decimal {@link Validator} by input parameters
     *
     * @param value - initial input {@link BigDecimal} value
     * @param error - initial input {@link BigDecimal} error
     * @return big decimal {@link Validator}
     */
    @Contract("_, _ -> new")
    @Factory
    public static Validator<BigDecimal> of(final BigDecimal value, final BigDecimal error) {
        return new BigDecimalValidator(value, error);
    }
}
