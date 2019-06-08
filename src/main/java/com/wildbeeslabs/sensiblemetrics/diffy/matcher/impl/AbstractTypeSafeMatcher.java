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

import com.wildbeeslabs.sensiblemetrics.diffy.common.ApplicationContext;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.DispatchEventException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.MatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ReflectionUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType.*;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ReflectionUtils.getMethodType;

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
        this(null, DEFAULT_METHOD_TYPE);
    }

    /**
     * Default abstract type safe matcher constructor with input {@link MatcherHandler}
     *
     * @param handler - initial input {@link MatcherHandler}
     */
    public AbstractTypeSafeMatcher(final MatcherHandler<T, T> handler) {
        this(handler, DEFAULT_METHOD_TYPE);
    }

    /**
     * Default abstract type safe matcher constructor with input {@link ReflectionUtils.ReflectionMethodType}
     *
     * @param methodType - initial input {@link ReflectionUtils.ReflectionMethodType}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final ReflectionUtils.ReflectionMethodType methodType) {
        this(null, methodType);
    }

    /**
     * Default abstract type safe matcher constructor with input {@link MatcherHandler} and {@link ReflectionUtils.ReflectionMethodType}
     *
     * @param handler    - initial input {@link MatcherHandler}
     * @param methodType - initial input {@link ReflectionUtils.ReflectionMethodType}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final MatcherHandler<T, T> handler, final ReflectionUtils.ReflectionMethodType methodType) {
        super(handler);
        this.clazz = (Class<? extends T>) (Optional.ofNullable(methodType).orElse(DEFAULT_METHOD_TYPE)).getType(this.getClass());
    }

    /**
     * Default abstract type safe matcher constructor with input {@link MatcherHandler} and {@link Class}
     *
     * @param handler - initial input {@link MatcherHandler}
     * @param clazz   - initial input {@link Class}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeMatcher(final MatcherHandler<T, T> handler, final Class<? extends T> clazz) {
        super(handler);
        this.clazz = Objects.isNull(clazz)
            ? (Class<? extends T>) DEFAULT_METHOD_TYPE.getType(this.getClass())
            : clazz;
    }

    /**
     * Returns binary flag depending on initial argument value {@code T} type safe comparison
     *
     * @param value - initial input argument value {@code T}
     * @return true - if initial value matches input argument {@code T}, false - otherwise
     */
    @Override
    public final boolean matches(final T value) {
        this.dispatch(value, MATCH_START);
        boolean result = false;
        try {
            this.dispatch(value, MATCH_BEFORE);
            result = this.matchesInstance(value) && this.matchesSafe(value);
            this.dispatch(value, fromSuccess(result));
            this.dispatch(value, MATCH_AFTER);
        } catch (RuntimeException e) {
            this.dispatch(value, MATCH_ERROR);
            MatchOperationException.throwIncorrectMatch(value, e);
        } finally {
            this.dispatch(value, MATCH_COMPLETE);
        }
        return result;
    }

    /**
     * Returns binary flag based on input value {@code T}
     *
     * @param value - initial input value {@code T}
     * @return true - if value {@code T} matches {@link Class}, false - otherwise
     */
    private boolean matchesInstance(final T value) {
        return Objects.isNull(value) || this.clazz.isInstance(value);
    }

    /**
     * Dispatches {@link MatcherEvent} by input parameters
     *
     * @param value - initial input matchable value {@code T}
     * @param type  - initial input {@link MatcherEventType}
     */
    private void dispatch(final T value, final MatcherEventType type) {
        this.dispatch(MatcherEvent.of(value, this, type), new ApplicationContext());
    }

    /**
     * Dispatches {@link MatcherEvent} by input parameters
     *
     * @param event   - initial input {@link MatcherEvent}
     * @param context - initial input {@link ApplicationContext}
     */
    @Override
    public void dispatch(final MatcherEvent<T> event, ApplicationContext context) {
        log.info("Dispatching event={%s} with context={%s}", event, context);
        try {
            this.getHandler().handleEvent(event);
        } catch (Exception e) {
            DispatchEventException.throwDispatchError(String.format("ERROR: cannot dispatch event={%s}", event), e);
        }
    }
}
