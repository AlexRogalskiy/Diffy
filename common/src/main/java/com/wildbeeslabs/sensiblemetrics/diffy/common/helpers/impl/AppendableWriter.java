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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.io.IOException;
import java.io.Writer;

/**
 * Appendable {@link Writer} implementation
 */
public final class AppendableWriter extends Writer {
    private final Appendable appendable;

    /**
     * Default {@link MutableCharSequence} instance
     */
    private final MutableCharSequence currentWrite = new MutableCharSequence();

    /**
     * Default appendable writer constructor by input arguments
     *
     * @param appendable - initial input {@link Appendable} instance
     */
    public AppendableWriter(final Appendable appendable) {
        this.appendable = appendable;
    }

    @Override
    public void write(final char[] chars, int offset, int length) throws IOException {
        currentWrite.chars = chars;
        appendable.append(currentWrite, offset, offset + length);
    }

    @Override
    public void write(int i) throws IOException {
        appendable.append((char) i);
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    /**
     * Default mutable {@link CharSequence} implementation
     */
    private static class MutableCharSequence implements CharSequence {

        private char[] chars;

        public int length() {
            return this.chars.length;
        }

        public char charAt(int i) {
            return this.chars[i];
        }

        public CharSequence subSequence(int start, int end) {
            return new String(this.chars, start, end - start);
        }
    }
}
