package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link LocalTime}.
 */
public class LocalTimeGenerator extends Generator<LocalTime> {
    private LocalTime min = LocalTime.MIN;
    private LocalTime max = LocalTime.MAX;

    public LocalTimeGenerator() {
        super(LocalTime.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * nanosecond.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * times with values of either {@link LocalTime#MIN} or
     * {@link LocalTime#MAX} as appropriate.</p>
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
            min = LocalTime.parse(range.min(), formatter);
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = LocalTime.parse(range.max(), formatter);

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public LocalTime generate(SourceOfRandomness random, GenerationStatus status) {
        return LocalTime.ofNanoOfDay(
            random.nextLong(
                min.toNanoOfDay(),
                max.toNanoOfDay()));
    }
}
