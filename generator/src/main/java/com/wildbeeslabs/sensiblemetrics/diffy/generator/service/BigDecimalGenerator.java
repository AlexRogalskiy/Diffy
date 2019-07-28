package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.annotation.Precision;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Comparables;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges.checkRange;
import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;
import static java.lang.Math.max;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.util.function.Function.identity;

/**
 * <p>Produces values of type {@link BigDecimal}.</p>
 *
 * <p>With no additional configuration, the generated values are chosen from
 * a range with a magnitude decided by</p>
 */
public class BigDecimalGenerator extends DecimalGenerator<BigDecimal> {
    private BigDecimal min;
    private BigDecimal max;
    private Precision precision;

    public BigDecimalGenerator() {
        super(BigDecimal.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} (inclusive) and/or
     * {@linkplain InRange#max() maximum} (exclusive), with uniform
     * distribution.</p>
     *
     * <p>If an endpoint of the range is not specified, its value takes on
     * a magnitude influenced by</p>
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = new BigDecimal(range.min());
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = new BigDecimal(range.max());

        if (min != null && max != null)
            checkRange(Ranges.Type.FLOAT, min, max);
    }

    /**
     * <p>Tells this generator to produce values that have a specified
     * {@linkplain Precision#scale() scale}.</p>
     *
     * <p>With no precision constraint and no {@linkplain #configure(InRange)
     * min/max constraint}, the scale of the generated values is unspecified.</p>
     *
     * <p>Otherwise, the scale of the generated values is set as
     * {@code max(0, precision.scale, range.min.scale, range.max.scale)}.</p>
     *
     * @param configuration annotation that gives the desired scale of the
     *                      generated values
     */
    public void configure(Precision configuration) {
        precision = configuration;
    }

    @Override
    public BigDecimal generate(SourceOfRandomness random, GenerationStatus status) {
        BigDecimal minToUse = min;
        BigDecimal maxToUse = max;
        int power = status.size() + 1;

        if (minToUse == null && maxToUse == null) {
            maxToUse = TEN.pow(power);
            minToUse = maxToUse.negate();
        }

        if (minToUse == null)
            minToUse = maxToUse.subtract(TEN.pow(power));
        else if (maxToUse == null)
            maxToUse = minToUse.add(TEN.pow(power));

        int scale = decideScale();

        BigDecimal minShifted = minToUse.movePointRight(scale);
        BigDecimal maxShifted = maxToUse.movePointRight(scale);
        BigInteger range = maxShifted.toBigInteger().subtract(minShifted.toBigInteger());

        BigInteger generated;
        do {
            generated = random.nextBigInteger(range.bitLength());
        } while (generated.compareTo(range) >= 0);

        return minShifted.add(new BigDecimal(generated)).movePointLeft(scale);
    }

    private int decideScale() {
        int scale = Integer.MIN_VALUE;

        if (min != null)
            scale = max(scale, min.scale());
        if (max != null)
            scale = max(scale, max.scale());
        if (precision != null)
            scale = max(scale, precision.scale());

        return max(scale, 0);
    }

    @Override
    protected Function<BigDecimal, BigDecimal> widen() {
        return identity();
    }

    @Override
    protected Function<BigDecimal, BigDecimal> narrow() {
        return identity();
    }

    @Override
    protected Predicate<BigDecimal> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected BigDecimal leastMagnitude() {
        return Comparables.leastMagnitude(
            min,
            max,
            ZERO);
    }

    @Override
    protected boolean negative(BigDecimal target) {
        return target.signum() < 0;
    }

    @Override
    protected BigDecimal negate(BigDecimal target) {
        return target.negate();
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return narrow(value);
    }
}
