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
 * Produces values of type {@code short} or {@link Short}.
 */
public class ShortGenerator extends IntegralGenerator<Short> {
    private short min = (Short) defaultValueOf(InRange.class, "minShort");
    private short max = (Short) defaultValueOf(InRange.class, "maxShort");

    @SuppressWarnings("unchecked")
    public ShortGenerator() {
        super(asList(Short.class, short.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum and/or
     * maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minShort()} and {@link InRange#maxShort()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minShort() : Short.parseShort(range.min());
        max = range.max().isEmpty() ? range.maxShort() : Short.parseShort(range.max());
    }

    @Override
    public Short generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextShort(min, max);
    }

    @Override
    protected Function<BigInteger, Short> narrow() {
        return BigInteger::shortValue;
    }

    @Override
    protected Predicate<Short> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Short leastMagnitude() {
        return Comparables.leastMagnitude(min, max, (short) 0);
    }

    @Override
    protected boolean negative(Short target) {
        return target < 0;
    }

    @Override
    protected Short negate(Short target) {
        return (short) -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
