package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link MonthDay}.
 */
public class MonthDayGenerator extends Generator<MonthDay> {
    private MonthDay min = MonthDay.of(1, 1);
    private MonthDay max = MonthDay.of(12, 31);

    public MonthDayGenerator() {
        super(MonthDay.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * dates with values of either {@code MonthDay(1, 1)} or
     * {@code MonthDay(12, 31)} as appropriate.</p>
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
            min = MonthDay.parse(range.min(), formatter);
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = MonthDay.parse(range.max(), formatter);

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public MonthDay generate(SourceOfRandomness random, GenerationStatus status) {
        /* Project the MonthDay to a LocalDate for easy long-based generation.
           Any leap year will do here. */
        long minEpochDay = min.atYear(2000).toEpochDay();
        long maxEpochDay = max.atYear(2000).toEpochDay();
        LocalDate date = LocalDate.ofEpochDay(random.nextLong(minEpochDay, maxEpochDay));

        return MonthDay.of(date.getMonthValue(), date.getDayOfMonth());
    }
}
