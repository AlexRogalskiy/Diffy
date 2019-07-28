package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

/**
 * <p>Produces {@link String}s whose characters are in the interval
 * {@code [0x0000, 0xD7FF]}.</p>
 */
public class StringGenerator extends AbstractStringGenerator {
    @Override
    protected int nextCodePoint(final SourceOfRandomness random) {
        return random.nextInt(0, Character.MIN_SURROGATE - 1);
    }

    @Override
    protected boolean codePointInRange(final int codePoint) {
        return codePoint >= 0 && codePoint < Character.MIN_SURROGATE;
    }
}
