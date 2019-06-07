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
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.EventDispatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Abstract {@link Matcher} implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractMatcher<T> extends AbstractBaseMatcher<T, T> implements Matcher<T>, EventDispatcher<MatcherEvent<T>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4127438327874076332L;

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
    public AbstractMatcher(final MatcherHandler<T, T> handler) {
        super(handler);
    }

    /**
     * Dispatches {@link MatcherEvent} by {@link ApplicationContext}
     *
     * @param event   - initial input {@link MatcherEvent}
     * @param context - initial input {@link ApplicationContext}
     */
    @Override
    public void dispatch(final MatcherEvent<T> event, final ApplicationContext context) {
    }
}
