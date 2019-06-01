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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.RejectMatcherException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BaseMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Reject {@link MatcherHandler} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode
@ToString
public class RejectMatcherHandler<T> implements MatcherHandler<T> {

    /**
     * Default {@link MatcherHandler}
     */
    public static final MatcherHandler INSTANCE = new RejectMatcherHandler<>();

    /**
     * {@link BaseMatcherEvent} handler by input event {@link BaseMatcherEvent}
     *
     * @param event - initial input event {@link BaseMatcherEvent} to handle
     */
    @Override
    public <E extends BaseMatcherEvent<T>> void handleEvent(final E event) {
        RejectMatcherException.throwReject(event);
    }
}
