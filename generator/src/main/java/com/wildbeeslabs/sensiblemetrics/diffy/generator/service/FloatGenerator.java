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
 * Produces values of type {@code float} or {@link Float}.
 */
public class FloatGenerator extends DecimalGenerator<Float> {
    private float min = (Float) defaultValueOf(InRange.class, "minFloat");
    private float max = (Float) defaultValueOf(InRange.class, "maxFloat");

    @SuppressWarnings("unchecked")
    public FloatGenerator() {
        super(asList(Float.class, float.class));
    }

    /**
     * Tells this generator to produce values within a specified minimum
     * (inclusive) and/or maximum (exclusive) with uniform distribution.
     * <p>
     * {@link InRange#min} and {@link InRange#max} take precedence over
     * {@link InRange#minFloat()} and {@link InRange#maxFloat()}, if non-empty.
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        min = range.min().isEmpty() ? range.minFloat() : Float.parseFloat(range.min());
        max = range.max().isEmpty() ? range.maxFloat() : Float.parseFloat(range.max());
    }

    @Override
    public Float generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextFloat(min, max);
    }

    @Override
    protected Function<Float, BigDecimal> widen() {
        return BigDecimal::valueOf;
    }

    @Override
    protected Function<BigDecimal, Float> narrow() {
        return BigDecimal::floatValue;
    }

    @Override
    protected Predicate<Float> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected Float leastMagnitude() {
        return Comparables.leastMagnitude(min, max, 0F);
    }

    @Override
    protected boolean negative(Float target) {
        return target < 0;
    }

    @Override
    protected Float negate(Float target) {
        return -target;
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return BigDecimal.valueOf(narrow(value));
    }
}
