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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import lombok.*;

/**
 * String {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StringMatcher extends AbstractMatcher<String> {
    private final String value;
    private final StringMatcher.MatchModeType modeType;

    public StringMatcher(final String value, final StringMatcher.MatchModeType modeType) {
        this.value = value;
        this.modeType = modeType;
    }

    @Override
    public boolean matches(final String target) {
        return this.getModeType().matches(this.value, target);
    }

    /**
     * String type {@link Enum}
     */
    @Getter
    @RequiredArgsConstructor
    public enum MatchModeType {
        EQUALS_FULLY("equals") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.equals(expected);
            }
        },
        EQUALS_FULLY_IGNORE_CASE("equalsIgnoreCase") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.equalsIgnoreCase(expected);
            }
        },
        STARTS_WITH("startsWith") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.startsWith(expected);
            }
        },
        STARTS_WITH_IGNORE_CASE("startsWithIgnoreCase") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.toLowerCase().startsWith(expected.toLowerCase());
            }
        },
        ENDS_WITH("endsWith") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.endsWith(expected);
            }
        },
        ENDS_WITH_IGNORE_CASE("endsWithIgnoreCase") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.toLowerCase().endsWith(expected.toLowerCase());
            }
        },
        CONTAINS("contains") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.contains(expected);
            }
        },
        CONTAINS_IGNORE_CASE("containsIgnoreCase") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.toLowerCase().contains(expected.toLowerCase());
            }
        },
        MATCHES("matches") {
            @Override
            protected boolean matches(final String expected, final String actual) {
                return actual.matches(expected);
            }
        };

        private final String description;

        protected abstract boolean matches(final String expected, final String actual);
    }
}
