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
