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
package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.context.ApplicationContext;

/**
 * Event dispatcher interface declaration
 *
 * @param <T> type of event item
 */
public interface EventDispatcher<T> {

    /**
     * Dispatches {@link Event} by {@link ApplicationContext}
     *
     * @param event   - initial input {@link Event}
     * @param context - initial input {@link ApplicationContext}
     */
    void dispatch(final T event, final ApplicationContext context);

//    /**
//     * Publish a collection of events on this bus (one, or multiple). The events will be dispatched to all subscribed
//     * listeners.
//     * <p>
//     * Implementations may treat the given {@code events} as a single batch and distribute the events as such to
//     * all subscribed EventListeners.
//     *
//     * @param events The collection of events to dispatch
//     */
//    default void publish(final T... events) {
//        dispatch(asList(events));
//    }
//
//    /**
//     * Publish a collection of events on this bus (one, or multiple). The events will be dispatched to all subscribed
//     * listeners.
//     * <p>
//     * Implementations may treat the given {@code events} as a single batch and distribute the events as such to
//     * all subscribed EventListeners.
//     *
//     * @param events The collection of events to dispatch
//     */
//    void dispatch(final List<? extends T> events);
}
