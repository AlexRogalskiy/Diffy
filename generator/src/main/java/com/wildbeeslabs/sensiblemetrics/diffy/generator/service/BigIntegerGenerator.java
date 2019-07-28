package com.wildbeeslabs.sensiblemetrics.diffy.generator.service;

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
import static java.math.BigInteger.TEN;
import static java.math.BigInteger.ZERO;
import static java.util.function.Function.identity;

/**
 * <p>Produces values of type {@link BigInteger}.</p>
 *
 * <p>With no additional configuration, the generated values are chosen from
 * a range with a magnitude decided by</p>
 */
public class BigIntegerGenerator extends IntegralGenerator<BigInteger> {
    private BigInteger min;
    private BigInteger max;

    public BigIntegerGenerator() {
        super(BigInteger.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or
     * {@linkplain InRange#max() maximum} inclusive, with uniform
     * distribution.</p>
     *
     * <p>If an endpoint of the range is not specified, its value takes on
     * a magnitude influenced.</p>
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = new BigInteger(range.min());
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = new BigInteger(range.max());
        if (min != null && max != null)
            checkRange(Ranges.Type.INTEGRAL, min, max);
    }

    @Override
    public BigInteger generate(SourceOfRandomness random, GenerationStatus status) {
        int numberOfBits = status.size() + 1;

        if (min == null && max == null)
            return random.nextBigInteger(numberOfBits);

        BigInteger minToUse = min;
        BigInteger maxToUse = max;
        if (minToUse == null)
            minToUse = maxToUse.subtract(TEN.pow(numberOfBits));
        else if (maxToUse == null)
            maxToUse = minToUse.add(TEN.pow(numberOfBits));

        return Ranges.choose(random, minToUse, maxToUse);
    }

    @Override
    protected Function<BigInteger, BigInteger> narrow() {
        return identity();
    }

    @Override
    protected Predicate<BigInteger> inRange() {
        return Comparables.inRange(min, max);
    }

    @Override
    protected BigInteger leastMagnitude() {
        return Comparables.leastMagnitude(min, max, ZERO);
    }

    @Override
    protected boolean negative(BigInteger target) {
        return target.signum() < 0;
    }

    @Override
    protected BigInteger negate(BigInteger target) {
        return target.negate();
    }

    @Override
    public BigDecimal magnitude(Object value) {
        return new BigDecimal(narrow(value));
    }
}
