package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link Clock}.
 */
public class ClockGenerator extends Generator<Clock> {
    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    private Instant min = Instant.MIN;
    private Instant max = Instant.MAX;

    public ClockGenerator() {
        super(Clock.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * nanosecond.</p>
     *
     * <p>Instances of this class are configured using {@link Instant}
     * strings.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * instants with values of either {@link Instant#MIN} or
     * {@link Instant#MAX} as appropriate.</p>
     *
     * <p>{@linkplain InRange#format()} is ignored. Instants are always
     * parsed using {@link java.time.format.DateTimeFormatter#ISO_INSTANT}.</p>
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = Instant.parse(range.min());
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = Instant.parse(range.max());

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public Clock generate(final SourceOfRandomness random, final GenerationStatus status) {
        return Clock.fixed(random.nextInstant(min, max), UTC_ZONE_ID);
    }
}
