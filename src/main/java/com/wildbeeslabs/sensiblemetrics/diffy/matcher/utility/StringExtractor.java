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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

import static org.apache.commons.lang.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * String extractor implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class StringExtractor {

    /**
     * Default context length
     */
    private static final int MAX_CONTEXT_LENGTH = 20;

    private static final String ELLIPSIS = "...";
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";

    /**
     * Default expected {@link String} value
     */
    private final String expected;
    /**
     * Default actual {@link String} value
     */
    private final String actual;

    private int contextLength;
    private int prefix;
    private int suffix;

    /**
     * Default comparator helper constructor by input arguments
     *
     * @param expected - initial input expected {@link String} value
     * @param actual   - initial input actual {@link String} value
     */
    public StringExtractor(final String expected, final String actual) {
        this(MAX_CONTEXT_LENGTH, expected, actual);
    }

    /**
     * Default comparator helper constructor by input arguments
     *
     * @param contextLength - initial input length
     * @param expected      - initial input expected {@link String} value
     * @param actual        - initial input actual {@link String} value
     */
    public StringExtractor(int contextLength, final String expected, final String actual) {
        this.contextLength = contextLength;
        this.expected = expected;
        this.actual = actual;
    }

    public String compact(final String message) {
        if (Objects.isNull(this.expected) || Objects.isNull(this.actual) || this.areStringsEqual()) {
            return String.format(message, this.expected, this.actual);
        }
        this.findCommonPrefix();
        this.findCommonSuffix();
        final String expected = this.compactString(this.expected);
        final String actual = this.compactString(this.actual);
        return String.format(message, expected, actual);
    }

    public String compact2(final String message) {
        if (Objects.isNull(this.expected) || Objects.isNull(this.actual) || this.areStringsEqual()) {
            return String.format(message, this.expected, this.actual);
        }
        final DiffExtractor extractor = new DiffExtractor();
        final String compactedPrefix = extractor.compactPrefix();
        final String compactedSuffix = extractor.compactSuffix();
        return String.format(message, compactedPrefix + extractor.expectedDiff() + compactedSuffix, compactedPrefix + extractor.actualDiff() + compactedSuffix);
    }

    private String compactString(final String source) {
        String result = join(DELTA_START, source.substring(this.prefix, source.length() - this.suffix + 1), DELTA_END);
        if (this.prefix > 0) {
            result = join(this.computeCommonPrefix() + result);
        }
        if (this.suffix > 0) {
            result = join(result + this.computeCommonSuffix());
        }
        return result;
    }

    protected String commonPrefix() {
        int end = Math.min(this.expected.length(), this.actual.length());
        for (int i = 0; i < end; i++) {
            if (this.expected.charAt(i) != actual.charAt(i)) {
                return this.expected.substring(0, i);
            }
        }
        return this.expected.substring(0, end);
    }

    protected String commonSuffix(final String prefix) {
        int suffixLength = 0;
        int maxSuffixLength = Math.min(this.expected.length() - prefix.length(), this.actual.length() - prefix.length()) - 1;
        for (; suffixLength <= maxSuffixLength; suffixLength++) {
            if (this.expected.charAt(this.expected.length() - 1 - suffixLength) != this.actual.charAt(actual.length() - 1 - suffixLength)) {
                break;
            }
        }
        return this.expected.substring(this.expected.length() - suffixLength);
    }

    private void findCommonPrefix() {
        this.prefix = 0;
        int end = Math.min(this.expected.length(), this.actual.length());
        for (; this.prefix < end; this.prefix++) {
            if (this.expected.charAt(this.prefix) != this.actual.charAt(this.prefix)) {
                break;
            }
        }
    }

    private void findCommonSuffix() {
        int expectedSuffix = this.expected.length() - 1;
        int actualSuffix = this.actual.length() - 1;
        for (; actualSuffix >= this.prefix && expectedSuffix >= this.prefix; actualSuffix--, expectedSuffix--) {
            if (this.expected.charAt(expectedSuffix) != this.actual.charAt(actualSuffix)) {
                break;
            }
        }
        this.suffix = this.expected.length() - expectedSuffix;
    }

    private String computeCommonPrefix() {
        return (this.prefix > this.contextLength ? ELLIPSIS : "") + this.expected.substring(Math.max(0, this.prefix - this.contextLength), this.prefix);
    }

    private String computeCommonSuffix() {
        int end = Math.min(this.expected.length() - this.suffix + 1 + this.contextLength, this.expected.length());
        return this.expected.substring(this.expected.length() - this.suffix + 1, end) + (this.expected.length() - this.suffix + 1 < this.expected.length() - this.contextLength ? ELLIPSIS : EMPTY);
    }

    private boolean areStringsEqual() {
        return Objects.equals(this.expected, this.actual);
    }

    private String extractDiff(final String source) {
        return join(DELTA_START, source.substring(this.prefix, source.length() - this.suffix), DELTA_END);
    }

    /**
     * Returns "..." in place of common prefix and "..." in
     * place of common suffix between expected and actual
     */
    public static StringExtractor of(final String fExpected, final String fActual) {
        return new StringExtractor(MAX_CONTEXT_LENGTH, fExpected, fActual);
    }

    /**
     * Returns "..." in place of common prefix and "..." in
     * place of common suffix between expected and actual
     */
    public static StringExtractor of(final int length, final String fExpected, final String fActual) {
        return new StringExtractor(length, fExpected, fActual);
    }

    public class DiffExtractor {
        private final String commonPrefix;
        private final String commonSuffix;

        /**
         * Default diff extractor constructor
         */
        public DiffExtractor() {
            this.commonPrefix = commonPrefix();
            this.commonSuffix = commonSuffix(this.commonPrefix);
        }

        public String expectedDiff() {
            return this.extractDiff(expected);
        }

        public String actualDiff() {
            return this.extractDiff(actual);
        }

        public String compactPrefix() {
            if (this.commonPrefix.length() <= contextLength) {
                return this.commonPrefix;
            }
            return join(ELLIPSIS, this.commonPrefix.substring(this.commonPrefix.length() - contextLength));
        }

        public String compactSuffix() {
            if (this.commonSuffix.length() <= contextLength) {
                return this.commonSuffix;
            }
            return this.commonSuffix.substring(0, contextLength) + ELLIPSIS;
        }

        private String extractDiff(final String source) {
            return join(DELTA_START, source.substring(this.commonPrefix.length(), source.length() - this.commonSuffix.length()), DELTA_END);
        }
    }
}
