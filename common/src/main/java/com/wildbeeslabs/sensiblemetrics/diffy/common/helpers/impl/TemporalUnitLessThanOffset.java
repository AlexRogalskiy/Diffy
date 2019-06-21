package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * {@link TemporalUnitOffset} with strict less than condition.
 *
 * @since 3.7.0
 */
public class TemporalUnitLessThanOffset extends TemporalUnitOffset {

    public TemporalUnitLessThanOffset(long value, final TemporalUnit unit) {
        super(value, unit);
    }

    /**
     * Checks if difference between temporal values is less then offset.
     *
     * @param temporal1 first temporal value to be validated against second temporal value.
     * @param temporal2 second temporal value.
     * @return true if difference between temporal values is more or equal to offset value.
     */
    @Override
    public boolean isBeyondOffset(final Temporal temporal1, final Temporal temporal2) {
        return getDifference(temporal1, temporal2) >= value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBeyondOffsetDifferenceDescription(Temporal temporal1, Temporal temporal2) {
        return "by less than " + super.getBeyondOffsetDifferenceDescription(temporal1, temporal2);
    }
}
