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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Abstract field matcher implementation by input object instance
 *
 * @param <T> type of input element to be matched by operation
 * @param <E> type of difference matcher instance
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractFieldMatcher<T, E> extends AbstractTypeSafeMatcher<T> implements TypeSafeMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1150403660115920086L;

    /**
     * Default reflection method type instance
     */
    private static final ReflectionUtils.ReflectionMethodType DEFAULT_TYPE = ReflectionUtils.getMethodType("valueOf", 1, 0);
    /**
     * Default field instance matcher {@link Matcher}
     */
    private final Matcher<? super E> matcher;

    /**
     * Default abstract type safe matcher constructor with input field matcher {@link Matcher}
     *
     * @param matcher - initial input matcher argument {@link Matcher}
     */
    public AbstractFieldMatcher(final Matcher<? super E> matcher) {
        super(DEFAULT_TYPE);
        this.matcher = matcher;
    }

    /**
     * Returns binary flag by field type safe comparison
     *
     * @param value - initial input argument value {@code T}
     * @return if initial value matches input argument, false - otherwise
     */
    @Override
    public boolean matchesSafe(final T value) {
        return Objects.nonNull(this.getMatcher()) && this.getMatcher().matches(this.valueOf(value));
    }

    /**
     * Returns field value by input argument instance
     *
     * @param value - initial input argument value {@code T}
     * @return field value {@code E}
     */
    protected abstract E valueOf(final T value);
}
