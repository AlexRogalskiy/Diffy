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
package com.wildbeeslabs.sensiblemetrics.diffy.utility;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * Validation utilities implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@UtilityClass
public final class ValidationUtils {

    /**
     * Validates that the object is not null
     *
     * @param obj object to test
     */
    public static void notNull(final Object obj) {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException("Object must not be null");
        }
    }

    /**
     * Validates that the object is not null
     *
     * @param obj object to test
     * @param msg message to output if validation fails
     */
    public static void notNull(final Object obj, final String msg) {
        if (Objects.nonNull(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Validates that the value is true
     *
     * @param val object to test
     */
    public static void isTrue(boolean val) {
        if (!val) {
            throw new IllegalArgumentException("Must be true");
        }
    }

    /**
     * Validates that the value is true
     *
     * @param val object to test
     * @param msg message to output if validation fails
     */
    public static void isTrue(boolean val, final String msg) {
        if (!val) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Validates that the value is false
     *
     * @param val object to test
     */
    public static void isFalse(boolean val) {
        if (val) {
            throw new IllegalArgumentException("Must be false");
        }
    }

    /**
     * Validates that the value is false
     *
     * @param val object to test
     * @param msg message to output if validation fails
     */
    public static void isFalse(boolean val, final String msg) {
        if (val) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Validates that the array contains no null elements
     *
     * @param objects the array to test
     */
    public static void noNullElements(final Object[] objects) {
        noNullElements(objects, "Array must not contain any null objects");
    }

    /**
     * Validates that the array contains no null elements
     *
     * @param objects the array to test
     * @param msg     message to output if validation fails
     */
    public static void noNullElements(final Object[] objects, final String msg) {
        for (final Object obj : objects) {
            if (Objects.isNull(obj)) {
                throw new IllegalArgumentException(msg);
            }
        }
    }

    /**
     * Validates that the string is not empty
     *
     * @param string the string to test
     */
    public static void notEmpty(final String string) {
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException("String must not be empty");
        }
    }

    /**
     * Validates that the string is not empty
     *
     * @param string the string to test
     * @param msg    message to output if validation fails
     */
    public static void notEmpty(final String string, final String msg) {
        if (StringUtils.isBlank(string)) {
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Cause a failure.
     *
     * @param msg message to output.
     */
    public static void fail(final String msg) {
        throw new IllegalArgumentException(msg);
    }
}

