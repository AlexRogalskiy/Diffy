package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * {@link TemporalUnitOffset} with less than or equal condition.
 *
 * @since 3.7.0
 */
public class TemporalUnitWithinOffset extends TemporalUnitOffset {

    public TemporalUnitWithinOffset(long value, final TemporalUnit unit) {
        super(value, unit);
    }

    /**
     * Checks if difference between temporal values is less then or equal to offset.
     *
     * @param temporal1 first temporal value to be validated against second temporal value.
     * @param temporal2 second temporal value.
     * @return true if difference between temporal values is more than offset value.
     */
    @Override
    public boolean isBeyondOffset(final Temporal temporal1, final Temporal temporal2) {
        return getDifference(temporal1, temporal2) > value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBeyondOffsetDifferenceDescription(final Temporal temporal1, final Temporal temporal2) {
        return "within " + super.getBeyondOffsetDifferenceDescription(temporal1, temporal2);
    }
}
