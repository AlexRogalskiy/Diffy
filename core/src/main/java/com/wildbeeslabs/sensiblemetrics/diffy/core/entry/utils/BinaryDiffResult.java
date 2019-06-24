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
package com.wildbeeslabs.sensiblemetrics.diffy.core.entry.utils;

public class BinaryDiffResult {
    /**
     * Default EOF marker
     */
    private static final int EOF = -1;

    /**
     * Default offset
     */
    public final int offset;
    /**
     * Default expected value
     */
    public final String expected;
    /**
     * Default actual value
     */
    public final String actual;

    /**
     * Builds a new instance.
     *
     * @param offset   the offset at which the difference occurred.
     * @param expected the expected byte as an int in the range 0 to 255, or -1 for EOF.
     * @param actual   the actual byte in the same format.
     */
    public BinaryDiffResult(int offset, int expected, int actual) {
        this.offset = offset;
        this.expected = describe(expected);
        this.actual = describe(actual);
    }

    public boolean hasNoDiff() {
        return this.offset == EOF;
    }

    public static BinaryDiffResult noDiff() {
        return new BinaryDiffResult(EOF, 0, 0);
    }

    private String describe(int b) {
        return (b == EOF) ? "EOF" : "0x" + Integer.toHexString(b).toUpperCase();
    }
}
