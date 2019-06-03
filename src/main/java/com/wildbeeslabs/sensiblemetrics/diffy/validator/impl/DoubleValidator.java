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
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Double {@link Validator} implementation
 */
public class DoubleValidator implements Validator<Double> {

    /**
     * Default double zero value
     */
    public static final double DOUBLE_ZERO = 0.0;

    /**
     * Default double delta
     */
    private final double delta;
    /**
     * Default double value
     */
    private final double value;

    /**
     * Default double validator constructor by input parameters
     *
     * @param value - initial input double value
     * @param error - initial input error value
     */
    public DoubleValidator(double value, double error) {
        this.delta = error;
        this.value = value;
    }

    /**
     * Returns true if input value {@link Double} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@link Double}
     * @return true - if input value {@link Double} is valid, false - otherwise
     */
    @Override
    public boolean validate(final Double value) {
        return this.actualDelta(value) <= DOUBLE_ZERO;
    }

    /**
     * Returns double delta by input parameters
     *
     * @param item - initial input value {@link Double}
     * @return double delta
     */
    @Contract(pure = true)
    private double actualDelta(@NonNull final Double item) {
        return (Math.abs((item - value)) - delta);
    }

    /**
     * Returns new double {@link Validator} by input parameters
     *
     * @param value - initial input double value
     * @param error - initial input error value
     * @return double {@link Validator}
     */
    @NotNull
    @Contract("_, _ -> new")
    @Factory
    public static Validator<Double> closeTo(double value, double error) {
        return new DoubleValidator(value, error);
    }
}
