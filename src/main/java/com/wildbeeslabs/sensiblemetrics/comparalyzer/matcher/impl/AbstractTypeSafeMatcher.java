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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * Abstract type safe matcher implementation for input instance
 *
 * @param <T>
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractTypeSafeMatcher<T> extends AbstractMatcher<T> implements TypeSafeMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4312818637038122609L;

    /**
     * Default method type instance
     */
    private static final ReflectionUtils.ReflectionMethodType DEFAULT_TYPE = ReflectionUtils.getMethodType("matchesSafe", 1, 0);

    /**
     * Default class instance of input value
     */
    private final Class<? extends T> clazz;

    /**
     * Default abstract type safe matcher constructor
     */
    public AbstractTypeSafeMatcher() {
        this(DEFAULT_TYPE);
    }

    /**
     * Default abstract type safe matcher constructor with reflection method type instance
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final ReflectionUtils.ReflectionMethodType methodType) {
        this.clazz = (Class<? extends T>) (Objects.isNull(methodType) ? DEFAULT_TYPE : methodType).getType(this.getClass());
    }

    /**
     * Default abstract type safe matcher constructor with class instance {@link Class}
     *
     * @param clazz
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final Class<? extends T> clazz) {
        this.clazz = Objects.isNull(clazz)
                ? (Class<? extends T>) DEFAULT_TYPE.getType(this.getClass())
                : clazz;
    }

    /**
     * Returns binary flag depending on initial argument value by type safe comparison
     *
     * @param value - initial input value
     * @return true - if input value matches, false - otherwise
     */
    public final boolean matches(final T value) {
        return Objects.nonNull(value) && getClazz().isInstance(value) && this.matchesSafe(value);
    }
}