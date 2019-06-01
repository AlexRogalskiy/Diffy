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

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/**
 * Default {@link MatcherHandler} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultMatcherHandler<T> implements MatcherHandler<T> {

    /**
     * Default {@link MatcherHandler}
     */
    public static final MatcherHandler INSTANCE = new DefaultMatcherHandler<>();

    /**
     * {@link BaseMatcherEvent} handler by input event {@code E}
     *
     * @param <E>   type of input event
     * @param event - initial input event {@link E} to handle
     */
    @Override
    public <E extends BaseMatcherEvent<T>> void handleEvent(final E event) {
        if (this.isEnableMode(event)) {
            Optional.ofNullable(event.getMatcher().getListeners())
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .forEach(listener -> this.invokeEventListener(event, listener));
        }
    }

    /**
     * Returns binary flag by input {@link BaseMatcherEvent}
     *
     * @param <E>   type of input event
     * @param event - initial input {@link BaseMatcherEvent}
     * @return true - if event handler is enabled, false - otherwise
     */
    private <E extends BaseMatcherEvent<T>> boolean isEnableMode(final E event) {
        return Objects.nonNull(event) && event.getMatcher().getMode().isEnable();
    }

    /**
     * Invokes {@link MatcherEventListener} by {@link BaseMatcherEvent} type
     *
     * @param <E>      type of input event
     * @param event    - initial input {@link BaseMatcherEvent}
     * @param listener - initial input {@link MatcherEventListener}
     */
    private <E extends BaseMatcherEvent<T>> void invokeEventListener(final E event, final MatcherEventListener<T> listener) {
        switch (event.getType()) {
            case MATCH_SUCCESS:
                listener.onSuccess(event);
                break;
            case MATCH_FAILURE:
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
    }
}
