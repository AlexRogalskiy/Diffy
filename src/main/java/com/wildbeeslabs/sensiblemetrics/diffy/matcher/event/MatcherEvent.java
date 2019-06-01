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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.event;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Matcher {@link BaseMatcherEvent} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MatcherEvent<T> extends BaseMatcherEvent<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1178474426881849360L;

    /**
     * Default matchable value {@code T}
     */
    private final T value;

    /**
     * Default matcher event constructor by input parameters
     *
     * @param matcher - initial input {BiMatcher}
     * @param value   - initial input matchable {@code T}
     * @param type    - initial input event type {@link MatcherEventType}
     */
    public MatcherEvent(final Matcher<T> matcher, final T value, final MatcherEventType type) {
        super(matcher, type);
        this.value = value;
    }

    /**
     * Creates new {@link MatcherEvent} by input parameters
     *
     * @param matcher - initial input {@link Matcher}
     * @param value   - initial input matchable {@code T}
     * @param status  - initial input match status
     * @return {@link MatcherEvent}
     */
    public static <T> MatcherEvent<T> of(final Matcher<T> matcher, final T value, final boolean status) {
        return of(matcher, value, MatcherEventType.fromBoolean(status));
    }

    /**
     * Creates new {@link MatcherEvent} by input parameters
     *
     * @param matcher - initial input {@link Matcher}
     * @param value   - initial input matchable {@code T}
     * @param type    - initial input event type {@link MatcherEventType}
     * @return {@link MatcherEvent}
     */
    public static <T> MatcherEvent<T> of(final Matcher<T> matcher, final T value, final MatcherEventType type) {
        return new MatcherEvent(matcher, value, type);
    }
}
