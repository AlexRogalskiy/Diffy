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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.util.Objects;

/**
 * Field name {@link Validator} implementation
 *
 * @param <T> type of validated value
 */
public class FieldValidator<T> implements Validator<T> {

    /**
     * Default {@code T} value
     */
    private final T value;

    public FieldValidator(final T value) {
        this.value = value;
    }

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@code T}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    @Override
    public boolean validate(final T value) {
        return Objects.equals(value, this.value);
    }

    /**
     * Returns {@link FieldValidator} by input value {@code T}
     *
     * @param <T>   type of validated value
     * @param value - initial input value {@code T}
     * @return {@link FieldValidator}
     */
    @Factory
    public static <T> FieldValidator<T> of(final T value) {
        return new FieldValidator<>(value);
    }
}
