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

import java.util.Objects;

/**
 * Pattern type {@link Enum}
 */
public enum PatternType {
    STATIC,
    DYNAMIC;

    /**
     * Return binary flag based on current mode {@code STATIC}
     *
     * @return true - if current mode is {@code STATIC}, false - otherwise
     */
    public boolean isStatic() {
        return this.equals(STATIC);
    }

    /**
     * Returns binary flag based on input {@link PatternType}es comparison
     *
     * @param p1 - initial input {@link PatternType} to compare with
     * @param p2 - initial input {@link PatternType} to compare by
     * @return true - if {@link PatternType} are equal, false - otherwise
     */
    public static boolean equals(final PatternType p1, final PatternType p2) {
        return Objects.equals(p1, p2);
    }
}
