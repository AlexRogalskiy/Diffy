package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.Duration;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link Duration}.
 */
public class DurationGenerator extends Generator<Duration> {
    private Duration min = Duration.ofSeconds(Long.MIN_VALUE, 0);
    private Duration max = Duration.ofSeconds(Long.MAX_VALUE, 999_999_999);

    public DurationGenerator() {
        super(Duration.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * nanosecond.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * durations with second values of either {@link Long#MIN_VALUE} or
     * {@link Long#MAX_VALUE} (with nanoseconds set to 999,999,999) as
     * appropriate.</p>
     *
     * <p>{@linkplain InRange#format()} is ignored. Durations are always
     * parsed using formats based on the ISO-8601 duration format
     * {@code PnDTnHnMn.nS} with days considered to be exactly 24 hours.
     *
     * @param range annotation that gives the range's constraints
     * @see Duration#parse(CharSequence)
     */
    public void configure(InRange range) {
        if (!defaultValueOf(InRange.class, "min").equals(range.min()))
            min = Duration.parse(range.min());
        if (!defaultValueOf(InRange.class, "max").equals(range.max()))
            max = Duration.parse(range.max());

        if (min.compareTo(max) > 0)
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public Duration generate(final SourceOfRandomness random, final GenerationStatus status) {
        return random.nextDuration(min, max);
    }
}
