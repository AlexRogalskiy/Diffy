package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

public class ComparisonCompactor {
    private static final String ELLIPSIS = "...";
    private static final String DELTA_END = "]";
    private static final String DELTA_START = "[";

    private int fContextLength;
    private String fExpected;
    private String fActual;
    private int fPrefix;
    private int fSuffix;

    public ComparisonCompactor(final int contextLength, final String expected, final String actual) {
        this.fContextLength = contextLength;
        this.fExpected = expected;
        this.fActual = actual;
    }

    @SuppressWarnings("deprecation")
    public String compact(final String message) {
        if (fExpected == null || fActual == null || this.areStringsEqual()) {
            return this.format(message, fExpected, fActual);
        }
        this.findCommonPrefix();
        this.findCommonSuffix();
        final String expected = compactString(this.fExpected);
        final String actual = compactString(this.fActual);
        return this.format(message, expected, actual);
    }

    private String compactString(final String source) {
        String result = DELTA_START + source.substring(this.fPrefix, source.length() - this.fSuffix + 1) + DELTA_END;
        if (this.fPrefix > 0) {
            result = computeCommonPrefix() + result;
        }
        if (this.fSuffix > 0) {
            result = result + computeCommonSuffix();
        }
        return result;
    }

    private void findCommonPrefix() {
        fPrefix = 0;
        int end = Math.min(this.fExpected.length(), this.fActual.length());
        for (; this.fPrefix < end; this.fPrefix++) {
            if (this.fExpected.charAt(this.fPrefix) != fActual.charAt(this.fPrefix)) {
                break;
            }
        }
    }

    private void findCommonSuffix() {
        int expectedSuffix = fExpected.length() - 1;
        int actualSuffix = this.fActual.length() - 1;
        for (; actualSuffix >= this.fPrefix && expectedSuffix >= this.fPrefix; actualSuffix--, expectedSuffix--) {
            if (this.fExpected.charAt(expectedSuffix) != this.fActual.charAt(actualSuffix)) {
                break;
            }
        }
        fSuffix = this.fExpected.length() - expectedSuffix;
    }

    private String computeCommonPrefix() {
        return (fPrefix > fContextLength ? ELLIPSIS : "") + this.fExpected.substring(Math.max(0, this.fPrefix - this.fContextLength), this.fPrefix);
    }

    private String computeCommonSuffix() {
        int end = Math.min(this.fExpected.length() - this.fSuffix + 1 + this.fContextLength, this.fExpected.length());
        return this.fExpected.substring(this.fExpected.length() - this.fSuffix + 1, end) + (this.fExpected.length() - this.fSuffix + 1 < this.fExpected.length() - this.fContextLength ? ELLIPSIS : "");
    }

    private boolean areStringsEqual() {
        return this.fExpected.equals(this.fActual);
    }

    private String format(final String message, final Object expected, final Object actual) {
        String formatted = "";
        if (message != null && message.length() > 0) {
            formatted = message + " ";
        }
        return formatted + "expected:<" + expected + "> but was:<" + actual + ">";
    }
}
