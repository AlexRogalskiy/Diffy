package com.wildbeeslabs.sensiblemetrics.diffy.generator.service.datetime;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces.GenerationStatus;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;
import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.SourceOfRandomness;

import java.time.ZoneId;

import static java.time.ZoneId.getAvailableZoneIds;

/**
 * Produces values of type {@link ZoneId}.
 */
public class ZoneIdGenerator extends Generator<ZoneId> {
    public ZoneIdGenerator() {
        super(ZoneId.class);
    }

    @Override
    public ZoneId generate(SourceOfRandomness random, GenerationStatus status) {
        return ZoneId.of(random.choose(getAvailableZoneIds()));
    }
}
