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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.EventListener;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Logging {@link MatcherEventListener} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Slf4j
@EqualsAndHashCode
@ToString
public class LoggingMatcherEventListener<T, S> implements MatcherEventListener<T, S> {

    /**
     * Logging {@link MatcherEventListener} instance
     */
    public static final MatcherEventListener INSTANCE = new LoggingMatcherEventListener<>();

    /**
     * {@link MatcherEventListener} on start {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onStart(final BaseMatcherEvent<T, S> event) {
        log.info("On start event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on before {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onBefore(final BaseMatcherEvent<T, S> event) {
        log.info("On before event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on after {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onAfter(final BaseMatcherEvent<T, S> event) {
        log.info("On after event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on success {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onSuccess(final BaseMatcherEvent<T, S> event) {
        log.info("On success event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onFailure(final BaseMatcherEvent<T, S> event) {
        log.info("On error event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on complete {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onComplete(final BaseMatcherEvent<T, S> event) {
        log.info("On complete event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on skip {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onSkip(final BaseMatcherEvent<T, S> event) {
        log.info("On skip event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * {@link MatcherEventListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    @Override
    public void onError(final BaseMatcherEvent<T, S> event) {
        log.info("On error event: {}, description: {}", event, event.getMatcher().getDescription());
    }

    /**
     * Returns {@link List} of supported {@link EventListener}s
     *
     * @return {@link List} of supported {@link EventListener}s
     */
    @Override
    public Collection<? extends EventListener<T, S, BaseMatcherEvent<T, S>>> getSupportedListeners() {
        return asList((EventListener<T, S, BaseMatcherEvent<T, S>>) INSTANCE);
    }
}
