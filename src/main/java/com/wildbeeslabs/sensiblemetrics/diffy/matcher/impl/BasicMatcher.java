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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;

/**
 * Basic {@link Matcher} implementation
 *
 * @param <T> type of input element to be matched by type safe operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public final class BasicMatcher<T> implements Matcher<T> {

    /**
     * Filter that matches all metrics.
     */
    public static final Matcher MATCH_ALL = new BasicMatcher<>(true);

    /**
     * Filter that does not match any metrics.
     */
    public static final Matcher MATCH_NONE = new BasicMatcher<>(false);

    private final boolean match;

    /**
     * Creates a new instance with a boolean indicating whether it should
     * always match or always fail.
     *
     * @param match should this filter match?
     */
    public BasicMatcher(boolean match) {
        this.match = match;
    }

    /**
     * {@inheritDoc}
     */
    public boolean matches(final T value) {
        return match;
    }
}
