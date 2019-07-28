package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Ranges;

import java.math.BigInteger;
import java.time.Period;
import java.time.Year;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link Period}.
 */
public class PeriodGenerator extends Generator<Period> {
    private static final BigInteger TWELVE = BigInteger.valueOf(12);
    private static final BigInteger THIRTY_ONE = BigInteger.valueOf(31);

    private Period min = Period.of(Year.MIN_VALUE, -12, -31);
    private Period max = Period.of(Year.MAX_VALUE, 12, 31);

    public PeriodGenerator() {
        super(Period.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * Periods with values of either {@code Period(Year#MIN_VALUE, -12, -31)}
     * or {@code Period(Year#MAX_VALUE, 12, 31)} as appropriate.</p>
     *
     * <p>{@linkplain InRange#format()} is ignored.  Periods are always parsed
     * using formats based on the ISO-8601 period formats {@code PnYnMnD} and
     * {@code PnW}.</p>
     *
     * @param range annotation that gives the range's constraints
     * @see Period#parse(CharSequence)
     */
    public void configure(InRange range) {
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = Period.parse(range.min());
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = Period.parse(range.max());

        if (toBigInteger(min).compareTo(toBigInteger(max)) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public Period generate(SourceOfRandomness random, GenerationStatus status) {
        return fromBigInteger(
            Ranges.choose(random, toBigInteger(min), toBigInteger(max)));
    }

    private BigInteger toBigInteger(Period period) {
        return BigInteger.valueOf(period.getYears())
            .multiply(TWELVE)
            .add(BigInteger.valueOf(period.getMonths()))
            .multiply(THIRTY_ONE)
            .add(BigInteger.valueOf(period.getDays()));
    }

    private Period fromBigInteger(BigInteger period) {
        BigInteger[] monthsAndDays = period.divideAndRemainder(THIRTY_ONE);
        BigInteger[] yearsAndMonths = monthsAndDays[0].divideAndRemainder(TWELVE);

        return Period.of(
            yearsAndMonths[0].intValueExact(),
            yearsAndMonths[1].intValueExact(),
            monthsAndDays[1].intValueExact());
    }
}
