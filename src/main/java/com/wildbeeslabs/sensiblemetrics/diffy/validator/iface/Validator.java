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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.iface;

/**
 * Validator interface declaration
 *
 * @param <T> type of validated value
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Default true {@link Validator}
     */
    Validator<?> DEFAULT_TRUE_INSTANCE = (value) -> true;
    /**
     * Default false {@link Validator}
     */
    Validator<?> DEFAULT_FALSE_INSTANCE = (value) -> false;

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@code T}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    boolean validate(final T value);

    /**
     * Gets a new validator to use for the value of the field with the given name.
     *
     * @param fieldName the field name
     * @return a non-null validator
     */
    default Validator<T> getValidatorForField(final String fieldName) {
        return (Validator<T>) DEFAULT_TRUE_INSTANCE;
    }
}
