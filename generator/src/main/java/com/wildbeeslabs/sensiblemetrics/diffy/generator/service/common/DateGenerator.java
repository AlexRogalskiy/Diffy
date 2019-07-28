package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.InRange;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.wildbeeslabs.sensiblemetrics.diffy.generator.utils.Reflection.defaultValueOf;

/**
 * Produces values of type {@link Date}.
 */
public class DateGenerator extends Generator<Date> {
    private Date min = new Date(Integer.MIN_VALUE);
    private Date max = new Date(Long.MAX_VALUE);

    public DateGenerator() {
        super(Date.class);
    }

    /**
     * <p>Tells this generator to produce values within a specified
     * {@linkplain InRange#min() minimum} and/or {@linkplain InRange#max()
     * maximum}, inclusive, with uniform distribution, down to the
     * millisecond.</p>
     *
     * <p>If an endpoint of the range is not specified, the generator will use
     * dates with milliseconds-since-the-epoch values of either
     * {@link Integer#MIN_VALUE} or {@link Long#MAX_VALUE} as appropriate.</p>
     *
     * <p>{@link InRange#format()} describes
     * {@linkplain SimpleDateFormat#parse(String) how the generator is to
     * interpret the range's endpoints}.</p>
     *
     * @param range annotation that gives the range's constraints
     */
    public void configure(InRange range) {
        SimpleDateFormat formatter = new SimpleDateFormat(range.format());
        formatter.setLenient(false);

        try {
            if (!defaultValueOf(InRange.class, "min").equals(range.min()))
                min = formatter.parse(range.min());
            if (!defaultValueOf(InRange.class, "max").equals(range.max()))
                max = formatter.parse(range.max());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

        if (min.getTime() > max.getTime())
            throw new IllegalArgumentException(String.format("bad range, %s > %s", range.min(), range.max()));
    }

    @Override
    public Date generate(SourceOfRandomness random, GenerationStatus status) {
        return new Date(random.nextLong(min.getTime(), max.getTime()));
    }
}
