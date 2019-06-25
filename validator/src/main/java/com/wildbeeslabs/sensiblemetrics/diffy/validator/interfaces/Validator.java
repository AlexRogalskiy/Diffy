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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.exception.ValidationException;
import lombok.NonNull;

import java.util.function.Function;

/**
 * Validator interface declaration
 *
 * @param <T> type of validated item
 */
@FunctionalInterface
@SuppressWarnings("unchecked")
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
     * Default throwable {@link Validator}
     */
    Validator<?> DEFAULT_THROWABLE_INSTANCE = (value) -> {
        throw new ValidationException();
    };

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@code T}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    boolean validate(final T value) throws Throwable;

    /**
     * Gets new {@link Validator} to use for the value of the field with the given name.
     *
     * @param value - initial input value {@code T}
     * @return {@link Validator}
     */
    @NonNull
    default Validator<T> getValidatorFor(final T value) {
        return (Validator<T>) DEFAULT_TRUE_INSTANCE;
    }

    /**
     * Returns true if input value {@code T} is valid by {@link Validator}, false - otherwise
     *
     * @param <T>       type of validated value
     * @param value     - initial input value {@code T} to validate
     * @param validator - initial input {@link Validator}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    @NonNull
    static <T> boolean validate(final T value, final Validator<T> validator) {
        ValidationUtils.notNull(validator, "Validator should not be null");
        try {
            return validator.validate(value);
        } catch (Throwable throwable) {
            return false;
        }
    }

    /**
     * Returns {@link Validator} that first applies the specified {@link Function} and then
     * validates the specified {@link Validator} against the result of the {@link Function}.
     *
     * @param converter - initial input {@link Function}
     * @param validator - initial input {@link Validator}
     */
    @NonNull
    static <T, V> Validator<T> where(final Function<T, V> converter, final Validator<? super V> validator) {
        ValidationUtils.notNull(converter, "Converter should not be null");
        ValidationUtils.notNull(validator, "Validator should not be null");

        return (input) -> validator.validate(converter.apply(input));
    }
}
