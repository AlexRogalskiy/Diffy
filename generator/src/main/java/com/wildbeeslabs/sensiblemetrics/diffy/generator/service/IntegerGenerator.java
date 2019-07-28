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
 * Produces values of type {@code int} or {@link Integer}.
 */
public class IntegerGenerator extends IntegralGenerator<Integer> {
    private int min = (Integer) defaultValueOf(InRange.class, "minInt");
    private int max = (Integer) defaultValueOf(InRange.class, "maxInt");

    @SuppressWarnings("unchecked")
    public IntegerGenerator() {
        super(asList(Integer.class, int.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum and/or
     * maximum, inclusive, with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minInt()} and {@link InRange#maxInt()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minInt() : Integer.parseInt(range.min());
        max = range.max().isEmpty() ? range.maxInt() : Integer.parseInt(range.max());
    }

    @Override
    public Integer generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextInt(min, max);
    }

    @Override
    protected Function<BigInteger, Integer> narrow() {
        return BigInteger::intValue;
    }

    @Override
    protected Predicate<Integer> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Integer leastMagnitude() {
        return Comparables.leastMagnitude(min, max, 0);
    }

    @Override
    protected boolean negative(Integer target) {
        return target < 0;
    }

    @Override
    protected Integer negate(Integer target) {
        return -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
