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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherHandlerListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Abstract matcher implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractMatcher<T> implements Matcher<T>, MatcherHandlerListener<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

    /**
     * Default {@link List} collection of {@link MatcherHandler}s
     */
    private final List<MatcherHandler<? super T>> handlers = new ArrayList<>();

    /**
     * Default abstract matcher constructor
     */
    public AbstractMatcher() {
        this(null);
    }

    /**
     * Default abstract matcher constructor with input iterable collection of matchers {@link Iterable}
     *
     * @param handlers - initial input iterable collection of matchers {@link Iterable}
     */
    public AbstractMatcher(final Iterable<MatcherHandler<? super T>> handlers) {
        Optional.ofNullable(handlers).orElseGet(Collections::emptyList).forEach(this::addHandler);
    }

    /**
     * Removes {@link MatcherHandler} from current {@link List} collection of {@link MatcherHandler}s
     *
     * @param handler - initial input {@link MatcherHandler} to remove
     */
    @Override
    public void removeHandler(final MatcherHandler<? super T> handler) {
        if (Objects.nonNull(handler)) {
            this.getHandlers().remove(handler);
        }
    }

    /**
     * Adds {@link MatcherHandler} to current {@link List} collection of {@link MatcherHandler}s
     *
     * @param handler - initial input {@link MatcherHandler} to add
     */
    @Override
    public void addHandler(final MatcherHandler<? super T> handler) {
        if (Objects.nonNull(handler)) {
            this.getHandlers().add(handler);
        }
    }

    /**
     * Removes all {@link MatcherHandler}s from current {@link List} collection of {@link MatcherHandler}s
     */
    @Override
    public void removeAllHandlers() {
        this.getHandlers().clear();
    }
}
