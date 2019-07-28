package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Comparables;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;
import static java.util.Arrays.asList;

/**
 * Produces values of type {@code byte} or {@link Byte}.
 */
public class ByteGenerator extends IntegralGenerator<Byte> {
    private byte min = (Byte) defaultValueOf(InRange.class, "minByte");
    private byte max = (Byte) defaultValueOf(InRange.class, "maxByte");

    @SuppressWarnings("unchecked")
    public ByteGenerator() {
        super(asList(Byte.class, byte.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum and/or
     * maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minByte()} and {@link InRange#maxByte()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minByte() : Byte.parseByte(range.min());
        max = range.max().isEmpty() ? range.maxByte() : Byte.parseByte(range.max());
    }

    @Override
    public Byte generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextByte(min, max);
    }

    @Override
    protected Function<BigInteger, Byte> narrow() {
        return BigInteger::byteValue;
    }

    @Override
    protected Predicate<Byte> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Byte leastMagnitude() {
        return Comparables.leastMagnitude(min, max, (byte) 0);
    }

    @Override
    protected boolean negative(Byte target) {
        return target < 0;
    }

    @Override
    protected Byte negate(Byte target) {
        return (byte) -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
