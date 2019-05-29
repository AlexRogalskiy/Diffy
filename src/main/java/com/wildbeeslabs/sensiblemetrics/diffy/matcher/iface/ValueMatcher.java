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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.MatcherModeType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.ValueMatcherModeType;

/**
 * Represents a value matcher that can compare two objects for equality.
 *
 * @param <T> the object type to compare
 */
public interface ValueMatcher<T> {

    /**
     * Compares the two provided objects whether they are equal.
     *
     * @param value1 - initial input first value {@code T}
     * @param value2 - initial input last value {@code T}
     * @return true - if objects are equal, false - otherwise
     */
    boolean matches(final T value1, final T value2);

    /**
     * Returns {@link MatcherModeType}
     *
     * @return {@link MatcherModeType}
     */
    default ValueMatcherModeType getMode() {
        return ValueMatcherModeType.STRICT;
    }
}
