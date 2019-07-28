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
 * Produces values of type {@code long} or {@link Long}.
 */
public class LongGenerator extends IntegralGenerator<Long> {
    private long min = (Long) defaultValueOf(InRange.class, "minLong");
    private long max = (Long) defaultValueOf(InRange.class, "maxLong");

    @SuppressWarnings("unchecked")
    public LongGenerator() {
        super(asList(Long.class, long.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum and/or
     * maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minLong()} and {@link InRange#maxLong()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minLong() : Long.parseLong(range.min());
        max = range.max().isEmpty() ? range.maxLong() : Long.parseLong(range.max());
    }

    @Override
    public Long generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextLong(min, max);
    }

    @Override
    protected Function<BigInteger, Long> narrow() {
        return BigInteger::longValue;
    }

    @Override
    protected Predicate<Long> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Long leastMagnitude() {
        return Comparables.leastMagnitude(min, max, 0L);
    }

    @Override
    protected boolean negative(Long target) {
        return target < 0;
    }

    @Override
    protected Long negate(Long target) {
        return -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
