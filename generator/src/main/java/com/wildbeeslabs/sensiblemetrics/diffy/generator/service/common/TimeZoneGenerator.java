package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.common;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.util.TimeZone;

import static java.util.TimeZone.getAvailableIDs;
import static java.util.TimeZone.getTimeZone;

/**
 * Produces values of type {@link TimeZone}.
 */
public class TimeZoneGenerator extends Generator<TimeZone> {
    public TimeZoneGenerator() {
        super(TimeZone.class);
    }

    @Override
    public TimeZone generate(SourceOfRandomness random, GenerationStatus status) {
        return getTimeZone(random.choose(getAvailableIDs()));
    }
}
