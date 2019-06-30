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
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

public enum CharPredicateType {

    /**
     * Tests code points against {@link Character#isLetter(int)}.
     *
     * @since 1.0
     */
    LETTERS {
        @Override
        public boolean test(final int codePoint) {
            return Character.isLetter(codePoint);
        }
    },

    /**
     * Tests code points against {@link Character#isDigit(int)}.
     *
     * @since 1.0
     */
    DIGITS {
        @Override
        public boolean test(final int codePoint) {
            return Character.isDigit(codePoint);
        }
    },

    /**
     * Tests if the code points represents a number between 0 and 9.
     *
     * @since 1.2
     */
    ARABIC_NUMERALS {
        @Override
        public boolean test(final int codePoint) {
            return codePoint >= '0' && codePoint <= '9';
        }
    },

    /**
     * Tests if the code points represents a letter between a and z.
     *
     * @since 1.2
     */
    ASCII_LOWERCASE_LETTERS {
        @Override
        public boolean test(final int codePoint) {
            return codePoint >= 'a' && codePoint <= 'z';
        }
    },

    /**
     * Tests if the code points represents a letter between A and Z.
     *
     * @since 1.2
     */
    ASCII_UPPERCASE_LETTERS {
        @Override
        public boolean test(final int codePoint) {
            return codePoint >= 'A' && codePoint <= 'Z';
        }
    },

    /**
     * Tests if the code points represents a letter between a and Z.
     *
     * @since 1.2
     */
    ASCII_LETTERS {
        @Override
        public boolean test(final int codePoint) {
            return ASCII_LOWERCASE_LETTERS.test(codePoint) || ASCII_UPPERCASE_LETTERS.test(codePoint);
        }
    },

    /**
     * Tests if the code points represents a letter between a and Z or a number between 0 and 9.
     *
     * @since 1.2
     */
    ASCII_ALPHA_NUMERALS {
        @Override
        public boolean test(final int codePoint) {
            return ASCII_LOWERCASE_LETTERS.test(codePoint) || ASCII_UPPERCASE_LETTERS.test(codePoint)
                || ARABIC_NUMERALS.test(codePoint);
        }
    };

    abstract boolean test(int codePoint);
}
