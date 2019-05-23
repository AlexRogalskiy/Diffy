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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;

/**
 * Matcher mode {@link Enum} to process malformed and unexpected data
 * <p>
 * 2 basic implementations are provided:
 * <ul>
 * <li>{@link #STRICT} return "true" on any occurrence</li>
 * <li>{@link #SILENT} ignores any problem</li>
 * </ul>
 */
public abstract class MatcherMode {

    public static final MatcherMode STRICT = new MatcherMode() {
        @Override
        public <T> void onSuccess(final Matcher<T> matcher) {
            if (this.isEnabled()) {
                matcher.getDescription().append(true);
            }
        }

        @Override
        public <T> void onError(final Matcher<T> matcher) {
            if (this.isEnabled()) {
                matcher.getDescription().append(matcher);
            }
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    };

    public static final MatcherMode SILENT = new MatcherMode() {
        @Override
        public <T> void onSuccess(final Matcher<T> matcher) {
        }

        @Override
        public <T> void onError(final Matcher<T> matcher) {
        }
    };

    /**
     * On success {@link Matcher} handler
     *
     * @param matcher - initial input {@link Matcher}
     */
    public abstract <T> void onSuccess(final Matcher<T> matcher);

    /**
     * On error {@link Matcher} handler
     *
     * @param matcher - initial input {@link Matcher}
     */
    public abstract <T> void onError(final Matcher<T> matcher);

    /**
     * Returns binary flag based on mode status
     *
     * @return true - if matcher mode is enabled, false - otherwise
     */
    protected boolean isEnabled() {
        return false;
    }
}
