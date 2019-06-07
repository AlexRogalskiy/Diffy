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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.wildbeeslabs.sensiblemetrics.diffy.executor.impl.TaskExecutorService.execute;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.listOf;

/**
 * Default {@link MatcherHandler} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultMatcherHandler<T, S> implements MatcherHandler<T, S> {

    /**
     * Default {@link Object} mutex
     */
    private final Object mutex = new Object();

    /**
     * Default {@link Duration} timeout (5000 in millis)
     */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(5000);

    /**
     * Default {@link MatcherHandler}
     */
    public static final MatcherHandler INSTANCE = new DefaultMatcherHandler<>();

    /**
     * Default task {@link ExecutorService}
     */
    private final ExecutorService executor;

    /**
     * Default matcher handler constructor
     */
    public DefaultMatcherHandler() {
        this(null);
    }

    /**
     * Default matcher handler constructor with input task {@link ExecutorService}
     *
     * @param executor - initial input task {@link ExecutorService}
     */
    public DefaultMatcherHandler(final ExecutorService executor) {
        this.executor = Optional.ofNullable(executor).orElseGet(Executors::newSingleThreadExecutor);
    }

    /**
     * {@link BaseMatcherEvent} handler by input event {@code E}
     *
     * @param <E>   type of processing event
     * @param event - initial input event {@link E} to handle
     */
    @Override
    public <E extends BaseMatcherEvent<T, S>> void handleEvent(final E event) {
        if (this.isEnableMode(event)) {
            listOf(event.getMatcher().getListeners())
                .stream()
                .filter(Objects::nonNull)
                .forEach(listener -> execute(DEFAULT_TIMEOUT, () -> this.invokeEventListener(event, listener), this.getExecutor()));
        }
    }

    /**
     * Returns binary flag by input {@link BaseMatcherEvent}
     *
     * @param <E>   type of processing event
     * @param event - initial input {@link BaseMatcherEvent}
     * @return true - if event handler is enabled, false - otherwise
     */
    private <E extends BaseMatcherEvent<T, S>> boolean isEnableMode(final E event) {
        return Objects.nonNull(event) && event.getMatcher().isEnable();
    }

    /**
     * Invokes {@link MatcherEventListener} by {@link BaseMatcherEvent} type
     *
     * @param <E>      type of processing event
     * @param event    - initial input {@link BaseMatcherEvent}
     * @param listener - initial input {@link MatcherEventListener}
     */
    private <E extends BaseMatcherEvent<T, S>> void invokeEventListener(final E event, final MatcherEventListener<T, S> listener) {
        //synchronized (this.mutex) {
        switch (event.getType()) {
            case MATCH_SUCCESS:
                listener.onSuccess(event);
                break;
            case MATCH_FAILURE:
                listener.onFailure(event);
                break;
            case MATCH_SKIP:
                listener.onSkip(event);
                break;
            case MATCH_ERROR:
                listener.onError(event);
                break;
            case MATCH_START:
                listener.onStart(event);
                break;
            case MATCH_COMPLETE:
                listener.onComplete(event);
                break;
            case MATCH_BEFORE:
                listener.onBefore(event);
                break;
            case MATCH_AFTER:
                listener.onAfter(event);
                break;
            default:
                break;
        }
        // }
    }
}
