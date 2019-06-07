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

/**
 * Event listener adapter interface declaration
 *
 * @param <T> type of event item
 */
public interface EventListenerAdapter<T, S, E extends Event<S>, L extends EventListener<T, S, E>> {

    /**
     * Removes {@link EventListener} from current adapter
     *
     * @param listener - initial input {@link EventListener} to remove
     */
    void removeListener(final L listener);

    /**
     * Adds {@link EventListener} to current adapter
     *
     * @param listener - initial input {@link EventListener} to add
     */
    void addListener(final L listener);

    /**
     * Adds {@link Iterable} collection of {@link EventListener}s to current adapter
     *
     * @param listeners - initial input {@link Iterable} collection of {@link EventListener}s to add
     */
    void addListeners(final Iterable<L> listeners);

    /**
     * Removes all {@link EventListener}s from current adapter
     */
    void removeAllListeners();
}
