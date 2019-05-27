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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.MatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ReflectionUtils.getMethodType;

/**
 * Abstract type safe matcher implementation by input class instance {@link Class}
 *
 * @param <T> type of input element to be matched by type safe operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
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
    private static final ReflectionUtils.ReflectionMethodType DEFAULT_METHOD_TYPE = getMethodType("matchesSafe", 1, 0);

    /**
     * Default input argument class instance
     */
    private final Class<? extends T> clazz;

    /**
     * Default abstract type safe matcher constructor
     */
    public AbstractTypeSafeMatcher() {
        this(DEFAULT_METHOD_TYPE);
    }

    /**
     * Default abstract type safe matcher constructor with input reflection method type instance
     *
     * @param methodType - initial input reflection type argument
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final ReflectionUtils.ReflectionMethodType methodType) {
        this.clazz = (Class<? extends T>) (Optional.ofNullable(methodType).orElse(DEFAULT_METHOD_TYPE)).getType(this.getClass());
    }

    /**
     * Default abstract type safe matcher constructor with class instance {@link Class}
     *
     * @param clazz - initial input class instance {@link Class}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final Class<? extends T> clazz) {
        this.clazz = Objects.isNull(clazz)
            ? (Class<? extends T>) DEFAULT_METHOD_TYPE.getType(this.getClass())
            : clazz;
    }

    /**
     * Returns binary flag depending on initial argument value by type safe comparison
     *
     * @param value - initial input argument value
     * @return true - if initial value matches input argument, false - otherwise
     */
    @Override
    public final boolean matches(final T value) {
        boolean result = false;
        try {
            result = this.getClazz().isInstance(value) && this.matchesSafe(value);
            this.handleMatchEvent(MatcherEvent.of(this, value, result));
        } catch (RuntimeException e) {
            MatchOperationException.throwIncorrectMatch(value, e);
        }
        return result;
    }
}
