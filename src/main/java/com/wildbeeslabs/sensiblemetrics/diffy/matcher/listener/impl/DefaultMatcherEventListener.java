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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.EventListener;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BaseMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.iface.MatcherEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;

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
     * Default {@link MatcherEventListener} instance
     */
    public static final MatcherEventListener INSTANCE = new DefaultMatcherEventListener<>();

    /**
     * Default {@link Collection} collection of failed {@link BaseMatcher}
     */
    private final Collection<? super BaseMatcher<T>> failedMatchers;
    /**
     * Default {@link Collection} collection of success {@link BaseMatcher}
     */
    private final Collection<? super BaseMatcher<T>> successMatchers;

    /**
     * Default matcher event listener constructor
     */
    public DefaultMatcherEventListener() {
        this.failedMatchers = new ArrayList<>();
        this.successMatchers = new ArrayList<>();
    }

    /**
     * {@link MatcherEventListener} on success {@link BaseMatcherEvent}
     *
     * @param event - initial input {@link BaseMatcherEvent}
     */
    @Override
    public void onSuccess(final BaseMatcherEvent<T> event) {
        if (this.isEnableMode(event)) {
            this.getSuccessMatchers().add(event.getMatcher());
        }
    }

    /**
     * {@link MatcherEventListener} on failure {@link BaseMatcherEvent}
     *
     * @param event - initial input {@link BaseMatcherEvent}
     */
    @Override
    public void onFailure(final BaseMatcherEvent<T> event) {
        if (this.isEnableMode(event)) {
            this.getFailedMatchers().add(event.getMatcher());
        }
    }

    /**
     * Returns binary flag based on matcher mode by input {@link BaseMatcherEvent}
     *
     * @param event - initial input {@link BaseMatcherEvent}
     * @return true - if current matcher mode is enabled, false - otherwise
     */
    private boolean isEnableMode(final BaseMatcherEvent<T> event) {
        return (Objects.nonNull(event.getMatcher()) && event.getMatcher().getMode().isEnable());
    }

    /**
     * Returns {@link List} of supported {@link EventListener}s
     *
     * @return {@link List} of supported {@link EventListener}s
     */
    @Override
    public Collection<? extends EventListener<T, BaseMatcherEvent<T>>> getSupportedListeners() {
        return asList((EventListener<T, BaseMatcherEvent<T>>) INSTANCE);
    }
}
