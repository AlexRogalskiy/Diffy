package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link YearMonth}.
 */
public class YearMonthGenerator extends Generator<YearMonth> {
    private YearMonth min = YearMonth.of(Year.MIN_VALUE, 1);
    private YearMonth max = YearMonth.of(Year.MAX_VALUE, 12);

    public YearMonthGenerator() {
        super(YearMonth.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * dates with values of either {@code YearMonth(Year#MIN_VALUE, 1)} or
     * {@code YearMonth(Year#MAX_VALUE, 12)} as appropriate.</p>
     *
     * <p>{@link InRange#format()} describes
     * {@linkplain DateTimeFormatter#ofPattern(String) how the generator is to
     * interpret the range's endpoints}.</p>
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(range.format());
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = YearMonth.parse(range.min(), formatter);
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = YearMonth.parse(range.max(), formatter);

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public YearMonth generate(SourceOfRandomness random, GenerationStatus status) {
        long generated = random.nextLong(
            min.getYear() * 12L + min.getMonthValue() - 1,
            max.getYear() * 12L + max.getMonthValue() - 1);

        return YearMonth.of(
            (int) (generated / 12),
            (int) Math.abs(generated % 12) + 1);
    }
}
