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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

/**
 * Matcher event implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@Builder
@AllArgsConstructor
public class MatcherEvent<T> {

    /**
     * Default event type {@link Enum}
     */
    public enum EventType {
        MATCH_START,
        MATCH_SUCCESS,
        MATCH_FAILURE,
        MATCH_END;

        /**
         * Returns {@link EventType} by input binary value
         *
         * @param value - initial input binary value
         * @return {@link EventType}
         */
        public static EventType fromBoolean(final boolean value) {
            return value ? MATCH_SUCCESS : MATCH_FAILURE;
        }
    }

    /**
     * Default {@link Matcher}
     */
    private final Matcher<T> matcher;
    /**
     * Default match status
     */
    private final EventType type;
    /**
     * Default matchable {@link Object}
     */
    private final Object value;

    /**
     * Creates new {@link MatcherEvent}
     *
     * @param matcher - initial input {@link Matcher}
     * @param value   - initial input matchable {@link Object}
     * @param status  - initial input match status
     * @return {@link MatcherEvent}
     */
    public static <T> MatcherEvent<T> of(final Matcher<T> matcher, final Object value, final boolean status) {
        return MatcherEvent
            .<T>builder()
            .matcher(matcher)
            .value(value)
            .type(EventType.fromBoolean(status))
            .build();
    }

    /**
     * Returns binary flag based on current event type {@code MATCH_SUCCESS}
     *
     * @return true - if current event type is {@code MATCH_SUCCESS}, false - otherwise
     */
    public boolean isSuccess() {
        return Objects.equals(this.getType(), EventType.MATCH_SUCCESS);
    }
}
