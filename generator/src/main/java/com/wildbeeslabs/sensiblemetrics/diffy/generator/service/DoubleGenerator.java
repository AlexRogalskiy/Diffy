package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Comparables;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;
import static java.util.Arrays.asList;

/**
 * Produces values for property parameters of type {@code double} or
 * {@link Double}.
 */
public class DoubleGenerator extends DecimalGenerator<Double> {
    private double min = (Double) defaultValueOf(InRange.class, "minDouble");
    private double max = (Double) defaultValueOf(InRange.class, "maxDouble");

    @SuppressWarnings("unchecked")
    public DoubleGenerator() {
        super(asList(Double.class, double.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum
     * (inclusive) and/or maximum (exclusive) with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minDouble()} and {@link InRange#maxDouble()},
     * if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minDouble() : Double.parseDouble(range.min());
        max = range.max().isEmpty() ? range.maxDouble() : Double.parseDouble(range.max());
    }

    @Override
    public Double generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextDouble(min, max);
    }

    @Override
    protected Function<Double, BigDecimal> widen() {
        return BigDecimal::valueOf;
    }

    @Override
    protected Function<BigDecimal, Double> narrow() {
        return BigDecimal::doubleValue;
    }

    @Override
    protected Predicate<Double> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Double leastMagnitude() {
        return Comparables.leastMagnitude(min, max, 0D);
    }

    @Override
    protected boolean negative(Double target) {
        return target < 0;
    }

    @Override
    protected Double negate(Double target) {
        return -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
