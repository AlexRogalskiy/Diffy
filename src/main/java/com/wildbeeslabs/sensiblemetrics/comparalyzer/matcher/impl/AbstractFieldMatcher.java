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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.Matcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract field matcher implementation for instance {@link T} and field value of {@link E}
 *
 * @param <T>
 * @param <E>
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractFieldMatcher<T, E> extends AbstractTypeSafeMatcher<T> implements TypeSafeMatcher<T> {

    /**
     * Default method type instance
     */
    private static final ReflectionUtils.ReflectionMethodType DEFAULT_TYPE = ReflectionUtils.getMethodType("valueOf", 1, 0);
    /**
     * Default field instance matcher {@link Matcher}
     */
    private final Matcher<? super E> matcher;

    /**
     * Default abstract type safe matcher constructor with input field matcher {@link Matcher}
     */
    public AbstractFieldMatcher(final Matcher<? super E> matcher) {
        super(DEFAULT_TYPE);
        this.matcher = matcher;
    }

    /**
     * Returns binary flag depending on initial argument value by field type safe comparison {@link T}
     *
     * @param value - initial input value {@link T}
     * @return true - if field value matches, false - otherwise
     */
    public boolean matchesSafe(final T value) {
        return getMatcher().matches(this.valueOf(value));
    }

    /**
     * Returns field value {@link E} by input argument instance {@link T}
     *
     * @param value - initial input instance {@link T}
     * @return field value {@link E}
     */
    protected abstract E valueOf(final T value);
}
