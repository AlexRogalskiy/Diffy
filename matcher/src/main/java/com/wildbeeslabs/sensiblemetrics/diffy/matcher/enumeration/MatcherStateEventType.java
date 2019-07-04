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

import com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration.LevelType;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.EventState;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception.UnsupportedMatcherStateEventTypeException.throwUnsupportedStateEventType;

/**
 * Matcher {@link EventState} type {@link Enum}
 */
@Getter
@RequiredArgsConstructor
public enum MatcherStateEventType implements EventState {
    MATCH_START(LevelType.TRACE, "MATCH_START", "%s START context"),
    MATCH_COMPLETE(LevelType.TRACE, "MATCH_COMPLETE", "%s COMPLETE context"),
    MATCH_SKIP(LevelType.TRACE, "MATCH_SKIP", "%s SKIP context"),
    MATCH_IGNORE(LevelType.TRACE, "MATCH_IGNORE", "%s IGNORE context"),
    MATCH_ERROR(LevelType.TRACE, "MATCH_ERROR", "%s ERROR context"),
    MATCH_BEFORE(LevelType.TRACE, "MATCH_BEFORE", "%s BEFORE context"),
    MATCH_AFTER(LevelType.TRACE, "MATCH_AFTER", "%s AFTER context"),
    MATCH_SUCCESS(LevelType.TRACE, "MATCH_SUCCESS", "%s SUCCESS context"),
    MATCH_FAILURE(LevelType.TRACE, "MATCH_FAILURE", "%s FAILURE context");

    /**
     * Default {@link LevelType}
     */
    private final LevelType level;
    /**
     * Default {@link String} name
     */
    private final String name;
    /**
     * Default {@link String} message format
     */
    private final String messageFormat;

    /**
     * Returns {@link MatcherStateEventType} by input binary success flag
     *
     * @param value - initial input binary success flag
     * @return {@link MatcherStateEventType}
     */
    @NonNull
    public static MatcherStateEventType fromSuccess(final boolean value) {
        return value ? MATCH_SUCCESS : MATCH_FAILURE;
    }

    /**
     * Returns {@link MatcherStateEventType} by input binary start flag
     *
     * @param value - initial input binary start flag
     * @return {@link MatcherStateEventType}
     */
    @NonNull
    public static MatcherStateEventType fromStart(final boolean value) {
        return value ? MATCH_START : MATCH_COMPLETE;
    }

    /**
     * Returns {@link MatcherStateEventType} by input binary before flag
     *
     * @param value - initial input binary before flag
     * @return {@link MatcherStateEventType}
     */
    @NonNull
    public static MatcherStateEventType fromBefore(final boolean value) {
        return value ? MATCH_BEFORE : MATCH_AFTER;
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
     * Returns {@link MatcherStateEventType} by input event type {@link String}
     *
     * @param name - initial input event type {@link String}
     * @return {@link MatcherStateEventType}
     */
    @NonNull
    public static MatcherStateEventType fromName(final String name) {
        return Arrays.stream(values())
            .filter(type -> type.name().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> throwUnsupportedStateEventType(name));
    }

    /**
     * Returns binary flag based on input {@link MatcherStateEventType}es comparison
     *
     * @param s1 - initial input {@link MatcherStateEventType} to compare with
     * @param s2 - initial input {@link MatcherStateEventType} to compare by
     * @return true - if {@link MatcherStateEventType} are equal, false - otherwise
     */
    public static boolean equals(final MatcherStateEventType s1, final MatcherStateEventType s2) {
        return Objects.equals(s1, s2);
    }
}
