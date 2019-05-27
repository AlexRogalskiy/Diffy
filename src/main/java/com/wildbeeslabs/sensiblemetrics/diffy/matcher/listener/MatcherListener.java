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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.MatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Matcher listener implementation to process {@link Matcher}s by defined rules
 *
 * @param <T> type of input element to be matched by operation
 */
@Slf4j
@EqualsAndHashCode
@ToString
public abstract class MatcherListener<T> {

    /**
     * Logging {@link MatcherListener}
     */
    public static final MatcherListener LOGGING_MATCHER_HANDLER = new LoggingMatcherListener<>();
    /**
     * Default {@link MatcherListener}
     */
    public static final MatcherListener DEFAULT_MATCHER_HANDLER = new DefaultMatcherListener<>();

    /**
     * Default {@link MatcherListener} implementation
     *
     * @param <T> type of matcher item
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultMatcherListener<T> extends MatcherListener<T> {
        /**
         * Default {@link List} collection of failed {@link Matcher}
         */
        private final List<Matcher<? super T>> failedMatchers = new ArrayList<>();
        /**
         * Default {@link List} collection of success {@link Matcher}
         */
        private final List<Matcher<? super T>> successMatchers = new ArrayList<>();

        @Override
        public void onSuccess(final MatcherEvent<T> event) {
            if (this.isEnableMode(event)) {
                this.getFailedMatchers().add(event.getMatcher());
            }
        }

        @Override
        public void onError(final MatcherEvent<T> event) {
            if (this.isEnableMode(event)) {
                this.getSuccessMatchers().add(event.getMatcher());
            }
        }

        private boolean isEnableMode(final MatcherEvent<? super T> event) {
            return Objects.nonNull(event.getMatcher()) && event.getMatcher().getMode().isEnable();
        }
    }

    /**
     * Logging {@link MatcherListener} implementation
     *
     * @param <T> type of matcher item
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LoggingMatcherListener<T> extends MatcherListener<T> {
        @Override
        public void onSuccess(final MatcherEvent<T> event) {
            log.info("{}, on success event: {}, description: {}", this.getClass().getName(), event, event.getMatcher().getDescription());
        }

        @Override
        public void onError(final MatcherEvent<T> event) {
            log.info("{}, on error event: {}, description: {}", this.getClass().getName(), event, event.getMatcher().getDescription());
        }
    }

    /**
     * {@link MatcherListener} on success {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    public abstract void onSuccess(final MatcherEvent<T> event);

    /**
     * {@link MatcherListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    public abstract void onError(final MatcherEvent<T> event);
}
