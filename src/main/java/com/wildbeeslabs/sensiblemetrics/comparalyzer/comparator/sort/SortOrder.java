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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.sort;

import java.util.Locale;
import java.util.Optional;

/**
 * Default sort order enumeration
 */
public enum SortOrder {
    ASC(1),
    DESC(-1),
    EQ(0);

    /**
     * Default sort order value
     */
    private final int order;

    SortOrder(int order) {
        this.order = order;
    }

    public int getValue() {
        return this.order;
    }

    /**
     * Returns whether the direction is ascending {@code ASC}
     */
    public boolean isAscending() {
        return this.equals(ASC);
    }

    /**
     * Returns whether the direction is descending {@code DESC}
     */
    public boolean isDescending() {
        return this.equals(DESC);
    }

    /**
     * Returns the {@link SortOrder} enum for the given {@link String} value
     *
     * @param value - initial input string value to be converted
     * @return enum for the given {@link SortOrder} string value
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value
     */
    public static SortOrder fromString(final String value) {
        return fromString(value, Locale.getDefault());
    }

    /**
     * Returns the {@link SortOrder} enum for the given {@link String} value and locale {@link Locale}
     *
     * @param value  - initial input string value to be converted
     * @param locale - initial input locale value {@link Locale}
     * @return enum for the given {@link SortOrder} string value
     * @throws IllegalArgumentException in case the given value cannot be parsed into an enum value
     */
    public static SortOrder fromString(final String value, final Locale locale) {
        try {
            return SortOrder.valueOf(value.toUpperCase(locale));
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
        }
    }

    public static SortOrder getSortOrderByCode(int code) {
        for (SortOrder e : SortOrder.values()) {
            if (code == e.getValue()) {
                return e;
            }
        }
        return null;
    }

    /**
     * Returns the {@link SortOrder} enum for the given {@link String} or null if it cannot be parsed into an enum
     * value.
     *
     * @param value - initial input string value to be converted
     * @return wrapped enum {@link SortOrder} for the given string value
     */
    public static Optional<SortOrder> fromOptionalString(final String value) {
        try {
            return Optional.of(fromString(value));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
