package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link OffsetTime}.
 */
public class OffsetTimeGenerator extends Generator<OffsetTime> {
    private OffsetTime min = OffsetTime.MIN;
    private OffsetTime max = OffsetTime.MAX;

    public OffsetTimeGenerator() {
        super(OffsetTime.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * nanosecond.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * times with values of either {@link OffsetTime#MIN} or
     * {@link OffsetTime#MAX} as appropriate.</p>
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
            min = OffsetTime.parse(range.min(), formatter);
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = OffsetTime.parse(range.max(), formatter);

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public OffsetTime generate(SourceOfRandomness random, GenerationStatus status) {
        LocalTime time = LocalTime.ofNanoOfDay(
            random.nextLong(
                min.withOffsetSameInstant(ZoneOffset.UTC).toLocalTime().toNanoOfDay(),
                max.withOffsetSameInstant(ZoneOffset.UTC).toLocalTime().toNanoOfDay()));

        return OffsetTime.of(time, ZoneOffset.UTC);
    }
}
