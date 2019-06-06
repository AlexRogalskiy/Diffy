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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Matcher event type {@link Enum}
 */
public enum MatcherEventType {
    MATCH_START,
    MATCH_COMPLETE,
    MATCH_SKIP,
    MATCH_ERROR,
    MATCH_BEFORE,
    MATCH_AFTER,
    MATCH_SUCCESS,
    MATCH_FAILURE;

    /**
     * Returns {@link MatcherEventType} by input binary value
     *
     * @param value - initial input binary value
     * @return {@link MatcherEventType}
     */
    @NonNull
    public static MatcherEventType fromBoolean(final boolean value) {
        return value ? MATCH_SUCCESS : MATCH_FAILURE;
    }

    /**
     * Returns binary flag based on current event type {@code MATCH_SUCCESS}
     *
     * @return true - if current event type is {@code MATCH_SUCCESS}, false - otherwise
     */
    public boolean isSuccess() {
        return this.equals(MATCH_SUCCESS);
    }

    /**
     * Returns binary flag based on current event type {@code MATCH_START}
     *
     * @return true - if current event type is {@code MATCH_START}, false - otherwise
     */
    public boolean isStart() {
        return this.equals(MATCH_START);
    }

    /**
     * Returns binary flag based on current event type {@code MATCH_COMPLETE}
     *
     * @return true - if current event type is {@code MATCH_COMPLETE}, false - otherwise
     */
    public boolean isComplete() {
        return this.equals(MATCH_COMPLETE);
    }

    /**
     * Returns binary flag based on current event type {@code MATCH_ERROR}
     *
     * @return true - if current event type is {@code MATCH_ERROR}, false - otherwise
     */
    public boolean isError() {
        return this.equals(MATCH_ERROR);
    }

    /**
     * Returns {@link MatcherEventType} by input event type {@link String}
     *
     * @param name - initial input event type {@link String}
     * @return {@link MatcherEventType}
     */
    @Nullable
    public static MatcherEventType fromName(final String name) {
        return Arrays.stream(values())
            .filter(type -> type.name().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    /**
     * Returns binary flag based on input {@link MatcherEventType}es comparison
     *
     * @param s1 - initial input {@link MatcherEventType} to compare with
     * @param s2 - initial input {@link MatcherEventType} to compare by
     * @return true - if {@link MatcherEventType} are equal, false - otherwise
     */
    public static boolean equals(final MatcherEventType s1, final MatcherEventType s2) {
        return Objects.equals(s1, s2);
    }
}
