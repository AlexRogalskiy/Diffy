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

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.DefaultEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BiMatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BiMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeBiMatcher;
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
 * Abstract type safe binary matcher implementation by input class instance {@link Class}
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
public abstract class AbstractTypeSafeBiMatcher<T, S> extends AbstractBiMatcher<T, S> implements TypeSafeBiMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -5270645820774938396L;

    /**
     * Default method type instance
     */
    private static final ReflectionUtils.ReflectionMethodType DEFAULT_METHOD_TYPE = getMethodType("matchesSafe", 2, 0);

    /**
     * Default input argument class instance
     */
    private final Class<? extends T> clazz;

    /**
     * Default abstract type safe binary matcher constructor
     */
    public AbstractTypeSafeBiMatcher() {
        this(null, DEFAULT_METHOD_TYPE);
    }

    /**
     * Default abstract type safe binary matcher constructor with input {@link MatcherHandler}
     *
     * @param handler - initial input {@link MatcherHandler}
     */
    public AbstractTypeSafeBiMatcher(final MatcherHandler<T, S> handler) {
        this(handler, DEFAULT_METHOD_TYPE);
    }

    /**
     * Default abstract type safe binary matcher constructor with input {@link ReflectionUtils.ReflectionMethodType}
     *
     * @param methodType - initial input {@link ReflectionUtils.ReflectionMethodType}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeBiMatcher(final ReflectionUtils.ReflectionMethodType methodType) {
        this(null, methodType);
    }

    /**
     * Default abstract type safe binary matcher constructor with input {@link MatcherHandler} and {@link ReflectionUtils.ReflectionMethodType}
     *
     * @param handler    - initial input {@link MatcherHandler}
     * @param methodType - initial input {@link ReflectionUtils.ReflectionMethodType}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeBiMatcher(final MatcherHandler<T, S> handler, final ReflectionUtils.ReflectionMethodType methodType) {
        super(handler);
        this.clazz = (Class<? extends T>) (Optional.ofNullable(methodType).orElse(DEFAULT_METHOD_TYPE)).getType(this.getClass());
    }

    /**
     * Default abstract type safe binary matcher constructor with input {@link MatcherHandler} and {@link Class}
     *
     * @param handler - initial input {@link MatcherHandler}
     * @param clazz   - initial input {@link Class}
     */
    @SuppressWarnings("unchecked")
    public AbstractTypeSafeBiMatcher(final MatcherHandler<T, S> handler, final Class<? extends T> clazz) {
        super(handler);
        this.clazz = Objects.isNull(clazz)
            ? (Class<? extends T>) DEFAULT_METHOD_TYPE.getType(this.getClass())
            : clazz;
    }

    /**
     * Returns binary flag depending on initial argument value {@code T} by type safe comparison
     *
     * @param first - initial input first argument value {@code T}
     * @param last  - initial input last argument value {@code T}
     * @return true - if input values {@code T} matches, false - otherwise
     */
    @Override
    public boolean matches(final T first, final T last) {
        this.emit(first, last, MATCH_START);
        boolean result = false;
        try {
            this.emit(first, last, MATCH_BEFORE);
            result = this.matchesInstance(first) && this.matchesInstance(last) && this.matchesSafe(first, last);
            this.emit(first, last, fromBoolean(result));
            this.emit(first, last, MATCH_AFTER);
        } catch (RuntimeException e) {
            this.emit(first, last, MATCH_ERROR);
            BiMatchOperationException.throwIncorrectMatch(first, last, e);
        } finally {
            this.emit(first, last, MATCH_COMPLETE);
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
     * Emits {@link BiMatcherEvent} by input parameters
     *
     * @param first - initial input first matchable value {@code T}
     * @param last  - initial input last matchable value {@code T}
     * @param type  - initial input {@link MatcherEventType}
     */
    protected void emit(final T first, final T last, final MatcherEventType type) {
        log.info("Emitting event with type = {%s}, first value = {%s}, last value = {%s}", type, first, last);
        BiMatcherEvent<T, S> event = null;
        try {
            event = BiMatcherEvent.of(DefaultEntry.of(first, last), this, type);
            this.getHandler().handleEvent(event);
        } catch (Exception ex) {
            log.error("ERROR: cannot handle event = {%s}", event);
        }
    }
}
