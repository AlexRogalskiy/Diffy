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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.EventListener;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;

/**
 * Match {@link EventListener} interface declaration
 *
 * @param <T> type of input element to be matched by operation
 */
public interface MatcherEventListener<T> extends EventListener<T, BaseMatcherEvent<T>> {

    /**
     * {@link MatcherEventListener} on success {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onSuccess(final BaseMatcherEvent<T> event) {
    }

    /**
     * {@link MatcherEventListener} on failure {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onFailure(final BaseMatcherEvent<T> event) {
    }

    /**
     * {@link MatcherEventListener} on skip {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onSkip(final BaseMatcherEvent<T> event) {
    }

    /**
     * {@link MatcherEventListener} on error {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onError(final BaseMatcherEvent<T> event) {
    }
}
