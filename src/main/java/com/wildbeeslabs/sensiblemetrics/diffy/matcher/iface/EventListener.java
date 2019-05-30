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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import java.io.Serializable;
import java.util.List;

/**
 * Event listener declaration
 *
 * @param <T> type of event element
 */
public interface EventListener<T> extends Serializable {
    /**
     * Returns {@link Class} of {@link EventListener} by input {@link EventListener}
     *
     * @param listener - initial input {@link EventListener}
     * @return {@link Class} of {@link EventListener}
     */
    default Class<? extends EventListener<T>> getListenerType(final EventListener<T> listener) {
        for (final EventListener<T> listenerType : this.getSupportedListeners()) {
            if (listenerType.getClass().isAssignableFrom(listener.getClass())) {
                return (Class<? extends EventListener<T>>) listenerType.getClass();
            }
        }
        return null;
    }

    /**
     * Returns {@link List} of supported {@link EventListener}s
     *
     * @return {@link List} of supported {@link EventListener}s
     */
    List<? extends EventListener<T>> getSupportedListeners();
}
