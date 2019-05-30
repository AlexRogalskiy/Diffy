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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.impl.DefaultMatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.DiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;

/**
 * Abstract matcher implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractDiffMatcher<T> implements DiffMatcher<T>, MatcherHandler<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

    /**
     * Default {@link List} collection of {@link Matcher}s
     */
    private final List<Matcher<? super T>> matchers = new ArrayList<>();
    /**
     * Default {@link MatcherHandler} implementation
     */
    private final MatcherHandler<T> handler;

    /**
     * Default abstract matcher constructor
     */
    public AbstractDiffMatcher() {
        this(null);
    }

    /**
     * Default abstract matcher constructor with input {@link MatcherHandler}
     *
     * @param handler - initial input {@link MatcherHandler}
     */
    public AbstractDiffMatcher(final MatcherHandler<T> handler) {
        this.handler = Optional.ofNullable(handler).orElse(DefaultMatcherHandler.INSTANCE);
    }

    /**
     * Removes input {@link Iterable} collection of {@link Matcher}s fromName current {@link List} collection of {@link Matcher}s
     *
     * @param matchers - initial input {@link Iterable} collection of {@link Matcher}s
     */
    public AbstractDiffMatcher<T> exclude(final Iterable<Matcher<? super T>> matchers) {
        Optional.ofNullable(matchers)
            .orElseGet(Collections::emptyList)
            .forEach(this::exclude);
        return this;
    }

    /**
     * Removes input {@link Matcher} fromName current {@link List} collection of {@link Matcher}s
     *
     * @param matcher - initial input {@link Matcher} to remove
     */
    public AbstractDiffMatcher<T> exclude(final Matcher<? super T> matcher) {
        if (Objects.nonNull(matcher)) {
            this.getMatchers().remove(matcher);
        }
        return this;
    }

    /**
     * Adds input {@link Iterable} collection of {@link Matcher}s to current {@link List} collection
     *
     * @param matchers - initial input {@link Iterable} collection of {@link Matcher}s
     */
    public AbstractDiffMatcher<T> include(final Iterable<Matcher<? super T>> matchers) {
        this.getMatchers().clear();
        Optional.ofNullable(matchers)
            .orElseGet(Collections::emptyList)
            .forEach(this::include);
        return this;
    }

    /**
     * Adds input {@link Matcher} to current {@link List} collection of {@link Matcher}s
     *
     * @param matcher - initial input matcher {@link Matcher} to add
     */
    public AbstractDiffMatcher<T> include(final Matcher<? super T> matcher) {
        if (Objects.nonNull(matcher)) {
            this.getMatchers().add(matcher);
        }
        return this;
    }

    /**
     * {@link MatcherEvent} handler operator
     *
     * @param event - initial input {@link MatcherEvent} to handle
     */
    @Override
    public <E extends BaseMatcherEvent<T>> void handleEvent(final E event) {
        this.getHandler().handleEvent(event);
    }
}
