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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Matcher handler implementation to process {@link Matcher}s by defined rules
 */
@Slf4j
@EqualsAndHashCode
@ToString
public abstract class MatcherHandler<T> {

    /**
     * Logging {@link MatcherHandler}
     */
    public static final MatcherHandler LOGGING_HANDLER = new LoggingMatcherHandler<>();

    /**
     * Returns {@link MatcherHandler}
     *
     * @return {@link MatcherHandler}
     */
    public MatcherHandler getDefaultHandler() {
        return new DefaultMatcherHandler<>();
    }

    /**
     * Default {@link MatcherHandler} implementation
     *
     * @param <T> type of matcher item
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultMatcherHandler<T> extends MatcherHandler<T> {
        /**
         * Default {@link List} collection of failed {@link Matcher}
         */
        private final List<Matcher<? extends T>> failedMatchers = new ArrayList<>();
        /**
         * Default {@link List} collection of success {@link Matcher}
         */
        private final List<Matcher<? extends T>> successMatchers = new ArrayList<>();

        @Override
        public void onSuccess(final Matcher<? extends T> matcher) {
            if (matcher.getMode().isEnable()) {
                this.failedMatchers.add(matcher);
            }
        }

        @Override
        public void onError(final Matcher<? extends T> matcher) {
            if (matcher.getMode().isEnable()) {
                this.successMatchers.add(matcher);
            }
        }
    }

    /**
     * Logging {@link MatcherHandler} implementation
     *
     * @param <T> type of matcher item
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LoggingMatcherHandler<T> extends MatcherHandler<T> {
        @Override
        public void onSuccess(final Matcher<? extends T> matcher) {
            log.info("{}, on success matcher: {}, description: {}", this.getClass().getName(), matcher, matcher.getDescription());
        }

        @Override
        public void onError(final Matcher<? extends T> matcher) {
            log.info("{}, on error matcher: {}, description: {}", this.getClass().getName(), matcher, matcher.getDescription());
        }
    }

    /**
     * On success {@link MatcherHandler}
     *
     * @param matcher - initial input {@link Matcher}
     */
    public abstract void onSuccess(final Matcher<? extends T> matcher);

    /**
     * On error {@link MatcherHandler}
     *
     * @param matcher - initial input {@link Matcher}
     */
    public abstract void onError(final Matcher<? extends T> matcher);
}
