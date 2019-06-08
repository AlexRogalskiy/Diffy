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

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BaseMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Binary {@link BaseMatcherEvent} implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BiMatcherEvent<T, S> extends BaseMatcherEvent<T, S> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5500034904779767930L;

    /**
     * Default binary matcher event constructor by input parameters
     *
     * @param source  - initial input event source {@code T}
     * @param matcher - initial input {@link BiMatcher}
     * @param type    - initial input event type {@link MatcherEventType}
     */
    public BiMatcherEvent(final S source, final BaseMatcher<T, S> matcher, final MatcherEventType type) {
        super(source, matcher, type);
    }

    /**
     * Creates new {@link BiMatcherEvent}
     *
     * @param source  - initial input event source {@code T}
     * @param matcher - initial input {@link BiMatcher}
     * @param status  - initial input match status
     * @return {@link BiMatcherEvent}
     */
    @Factory
    public static <T, S> BiMatcherEvent<T, S> of(final S source, final BaseMatcher<T, S> matcher, final boolean status) {
        return of(source, matcher, MatcherEventType.fromSuccess(status));
    }

    /**
     * Creates new {@link BiMatcherEvent}
     *
     * @param source  - initial input event source {@code T}
     * @param matcher - initial input {@link BiMatcher}
     * @param type    - initial input event type {@link MatcherEventType}
     * @return {@link BiMatcherEvent}
     */
    @Factory
    public static <T, S> BiMatcherEvent<T, S> of(final S source, final BaseMatcher<T, S> matcher, final MatcherEventType type) {
        return new BiMatcherEvent(source, matcher, type);
    }
}
