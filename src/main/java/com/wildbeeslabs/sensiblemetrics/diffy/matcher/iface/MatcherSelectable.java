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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;

import java.io.Serializable;

/**
 * {@link MatcherEventListener} listener declaration
 *
 * @param <T> type of input element to be matched by operation
 */
public interface MatcherSelectable<T> extends Serializable {

    /**
     * Removes {@link MatcherEventListener} from current {@link Matcher}
     *
     * @param listener - initial input {@link MatcherEventListener} to remove
     */
    <E extends MatcherEventListener<T>> void removeListener(final E listener);

    /**
     * Adds {@link MatcherEventListener} to current {@link Matcher}
     *
     * @param listener - initial input {@link MatcherEventListener} to add
     */
    <E extends MatcherEventListener<T>> void addListener(final E listener);

    /**
     * Adds {@link Iterable} collection of {@link MatcherEventListener}s to current {@link Matcher}
     *
     * @param listeners - initial input {@link Iterable} collection of {@link MatcherEventListener}s to add
     */
    <E extends MatcherEventListener<T>> void addListeners(final Iterable<E> listeners);

    /**
     * Removes all {@link MatcherEventListener}s from current {@link Matcher}
     */
    void removeAllListeners();
}
