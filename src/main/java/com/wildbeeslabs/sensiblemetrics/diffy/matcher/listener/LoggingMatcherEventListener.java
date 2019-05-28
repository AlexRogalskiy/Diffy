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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherEventListener;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Logging {@link MatcherEventListener} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Slf4j
@EqualsAndHashCode
@ToString
public class LoggingMatcherEventListener<T> implements MatcherEventListener<T> {
    /**
     * Logging {@link MatcherEventListener}
     */
    public static final MatcherEventListener LOGGING_MATCHER_HANDLER = new LoggingMatcherEventListener<>();

    /**
     * {@link MatcherEventListener} on success {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onSuccess(final MatcherEvent<T> event) {
        log.info("{}, on success event: {}, description: {}", this.getClass().getName(), event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onError(final MatcherEvent<T> event) {
        log.info("{}, on error event: {}, description: {}", this.getClass().getName(), event, event.getMatcher().getDescription());
    }
}