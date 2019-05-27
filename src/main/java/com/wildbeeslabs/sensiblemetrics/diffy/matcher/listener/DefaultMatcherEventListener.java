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
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.MatcherEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Default {@link MatcherEventListener} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultMatcherEventListener<T> implements MatcherEventListener<T> {
    /**
     * Default {@link List} collection of failed {@link Matcher}
     */
    private final List<Matcher<? super T>> failedMatchers = new ArrayList<>();
    /**
     * Default {@link List} collection of success {@link Matcher}
     */
    private final List<Matcher<? super T>> successMatchers = new ArrayList<>();

    /**
     * {@link MatcherEventListener} on success {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    public void onSuccess(final MatcherEvent<T> event) {
        if (this.isEnableMode(event)) {
            this.getSuccessMatchers().add(event.getMatcher());
        }
    }

    /**
     * {@link MatcherEventListener} on error {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     */
    public void onError(final MatcherEvent<T> event) {
        if (this.isEnableMode(event)) {
            this.getFailedMatchers().add(event.getMatcher());
        }
    }

    /**
     * Returns binary flag based on matcher mode by input {@link MatcherEvent}
     *
     * @param event - initial input {@link MatcherEvent}
     * @return true - if current matcher mode is enabled, false - otherwise
     */
    private boolean isEnableMode(final MatcherEvent<? super T> event) {
        return (Objects.nonNull(event.getMatcher()) && event.getMatcher().getMode().isEnable());
    }
}
