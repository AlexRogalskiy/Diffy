/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.event;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Event listener interface declaration
 *
 * @param <T> type of event item
 */
public interface EventListener<T, E extends Event<T>> {

    /**
     * {@link MatcherEventListener} on start {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onStart(final E event) {
    }

    /**
     * {@link MatcherEventListener} on before {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onBefore(final E event) {
    }

    /**
     * {@link MatcherEventListener} on after {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onAfter(final E event) {
    }

    /**
     * {@link MatcherEventListener} on complete {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@code E}
     */
    default void onComplete(final E event) {
    }

    /**
     * Returns {@link Class} of {@link EventListener} by input {@link EventListener}
     *
     * @param listener - initial input {@link EventListener}
     * @return {@link Class} of {@link EventListener}
     * @throws NullPointerException if listener is {@code null}
     */
    default Class<? extends EventListener<T, E>> getListenerType(final EventListener<T, E> listener) {
        Objects.requireNonNull(listener, "Listener should not be null");
        for (final EventListener<T, E> listenerType : this.getSupportedListeners()) {
            if (listenerType.getClass().isAssignableFrom(listener.getClass())) {
                return (Class<? extends EventListener<T, E>>) listenerType.getClass();
            }
        }
        return null;
    }

    /**
     * Returns {@link List} of supported {@link EventListener}s
     *
     * @return {@link List} of supported {@link EventListener}s
     */
    Collection<? extends EventListener<T, E>> getSupportedListeners();
}
