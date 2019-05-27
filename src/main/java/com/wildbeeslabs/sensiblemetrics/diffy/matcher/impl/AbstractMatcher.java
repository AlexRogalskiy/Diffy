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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherListener;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.MatcherEventListener;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Abstract {@link Matcher} implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Getter(AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public abstract class AbstractMatcher<T> implements Matcher<T>, MatcherListener<T>, MatcherHandler<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

    /**
     * Default {@link List} collection of {@link MatcherEventListener}s
     */
    private final List<MatcherEventListener> handlers = new ArrayList<>();

    /**
     * Default abstract matcher constructor
     */
    public AbstractMatcher() {
        this(null);
    }

    /**
     * Default abstract matcher constructor with input {@link Iterable} collection of {@link MatcherEventListener}s
     *
     * @param handlers - initial input {@link Iterable} collection of {@link MatcherEventListener}s
     */
    public AbstractMatcher(final Iterable<MatcherEventListener<T>> handlers) {
        this.addListeners(handlers);
    }

    /**
     * Removes {@link MatcherEventListener} from current {@link List} collection of {@link MatcherEventListener}s
     *
     * @param handler - initial input {@link MatcherEventListener} to remove
     */
    @Override
    public void removeListener(final MatcherEventListener<T> handler) {
        if (Objects.nonNull(handler)) {
            this.getHandlers().remove(handler);
        }
    }

    /**
     * Adds {@link MatcherEventListener} to current {@link List} collection of {@link MatcherEventListener}s
     *
     * @param handler - initial input {@link MatcherEventListener} to add
     */
    @Override
    public void addListener(final MatcherEventListener<T> handler) {
        if (Objects.nonNull(handler)) {
            this.getHandlers().add(handler);
        }
    }

    /**
     * Adds {@link Iterable} collection of {@link MatcherEventListener}s to current {@link List} collection of {@link MatcherEventListener}s
     */
    @Override
    public void addListeners(final Iterable<MatcherEventListener<T>> handlers) {
        Optional.ofNullable(handlers).orElseGet(Collections::emptyList).forEach(this::addListener);
    }

    /**
     * Removes all {@link MatcherEventListener}s from current {@link List} collection of {@link MatcherEventListener}s
     */
    @Override
    public void removeAllListeners() {
        this.getHandlers().clear();
    }

    /**
     * Operates on {@link MatcherEvent}s
     *
     * @param event - initial input {@link MatcherEvent} to handle
     */
    @Override
    public void handleMatchEvent(final MatcherEvent<T> event) {
        if (this.getMode().isEnable()) {
            this.getHandlers().forEach(handler -> {
                if (event.isMatch()) {
                    handler.onSuccess(event);
                } else {
                    handler.onError(event);
                }
            });
        }
    }
}
