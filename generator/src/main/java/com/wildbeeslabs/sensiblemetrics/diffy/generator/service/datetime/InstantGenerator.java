package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.Instant;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link Instant}.
 */
public class InstantGenerator extends Generator<Instant> {
    private Instant min = Instant.MIN;
    private Instant max = Instant.MAX;

    public InstantGenerator() {
        super(Instant.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * nanosecond.</p>
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
    public Instant generate(SourceOfRandomness random, GenerationStatus status) {
        return random.nextInstant(min, max);
    }
}
