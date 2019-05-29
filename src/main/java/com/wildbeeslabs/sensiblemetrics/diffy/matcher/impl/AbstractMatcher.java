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
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.impl.DefaultMatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherSelectable;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * Abstract {@link Matcher} implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractMatcher<T> implements Matcher<T>, MatcherSelectable<T>, MatcherHandler<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

    /**
     * Default {@link List} collection of {@link MatcherEventListener}s
     */
    private final List<MatcherEventListener<T>> listeners = new ArrayList<>();
    /**
     * Default {@link MatcherHandler} implementation
     */
    private final MatcherHandler<T> handler;

    /**
     * Default abstract matcher constructor
     */
    public AbstractMatcher() {
        this(null);
    }

    /**
     * Default abstract matcher constructor with input {@link MatcherHandler}
     *
     * @param handler - initial input {@link MatcherHandler}
     */
    public AbstractMatcher(final MatcherHandler<T> handler) {
        this.handler = Optional.ofNullable(handler).orElse(DefaultMatcherHandler.INSTANCE);
    }

    /**
     * Removes {@link MatcherEventListener} from current {@link List} collection of {@link MatcherEventListener}s
     *
     * @param listener - initial input {@link MatcherEventListener} to remove
     */
    @Override
    public <E extends MatcherEventListener<T>> void removeListener(final E listener) {
        if (Objects.nonNull(listener)) {
            this.getListeners().remove(listener);
        }
    }

    /**
     * Adds {@link MatcherEventListener} to current {@link List} collection of {@link MatcherEventListener}s
     *
     * @param listener - initial input {@link MatcherEventListener} to add
     */
    @Override
    public <E extends MatcherEventListener<T>> void addListener(final E listener) {
        if (Objects.nonNull(listener)) {
            this.getListeners().add(listener);
        }
    }

    /**
     * Adds {@link Iterable} collection of {@link MatcherEventListener}s to current {@link List} collection of {@link MatcherEventListener}s
     *
     * @param listeners - initial input {@link MatcherEventListener}s to add
     */
    @Override
    public <E extends MatcherEventListener<T>> void addListeners(final Iterable<E> listeners) {
        Optional.ofNullable(listeners).orElseGet(Collections::emptyList).forEach(this::addListener);
    }

    /**
     * Removes all {@link MatcherEventListener}s from current {@link List} collection of {@link MatcherEventListener}s
     */
    @Override
    public void removeAllListeners() {
        this.getListeners().clear();
    }

    /**
     * {@link MatcherEvent} handler
     *
     * @param event - initial input {@link MatcherEvent} to handle
     */
    @Override
    public void handleEvent(final MatcherEvent<T> event) {
        this.getHandler().handleEvent(event);
    }
}
