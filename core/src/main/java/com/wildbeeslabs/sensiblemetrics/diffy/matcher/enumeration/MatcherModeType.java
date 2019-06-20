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

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherStatusType.DISABLE;
import static com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherStatusType.ENABLE;

/**
 * Matcher mode type {@link Enum} to process malformed and unexpected data
 * <p>
 * 2 basic implementations are provided:
 * <ul>
 * <li>{@link #STRICT} return "true" on any occurrence</li>
 * <li>{@link #SILENT} ignores any problem</li>
 * </ul>
 */
@Getter
@RequiredArgsConstructor
public enum MatcherModeType implements BaseMatcherMode {
    STRICT(ENABLE),
    SILENT(DISABLE);

    /**
     * Binary flag based on current status
     */
    private final MatcherStatusType status;

    /**
     * Return binary flag based on current mode {@code STRICT}
     *
     * @return true - if current mode is {@code STRICT}, false - otherwise
     */
    public boolean isStrict() {
        return this.equals(STRICT);
    }

    /**
     * Returns binary flag based on current mode status {@code ENABLE}
     *
     * @return true - if current mode status is {@code ENABLE}, false - otherwise
     */
    @Override
    public boolean isEnable() {
        return this.getStatus().isEnable();
    }

    /**
     * Returns binary flag based on input {@link MatcherModeType}es comparison
     *
     * @param s1 - initial input {@link MatcherModeType} to compare with
     * @param s2 - initial input {@link MatcherModeType} to compare by
     * @return true - if {@link MatcherModeType} are equal, false - otherwise
     */
    public static boolean equals(final MatcherModeType s1, final MatcherModeType s2) {
        return Objects.equals(s1, s2);
    }
}
