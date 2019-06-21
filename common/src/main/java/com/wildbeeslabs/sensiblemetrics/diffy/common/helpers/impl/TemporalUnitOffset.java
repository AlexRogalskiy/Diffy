package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.jentle.algorithms.toolset.iface.TemporalOffset;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.Objects;

import static java.lang.Math.abs;
import static java.lang.String.format;

/**
 * Base class for {@link TemporalOffset} on basis of {@link TemporalUnit}.
 *
 * @since 3.7.0
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class TemporalUnitOffset implements TemporalOffset<Temporal> {

    protected final TemporalUnit unit;
    protected final long value;

    /**
     * Creates a new temporal offset for a given temporal unit.
     *
     * @param value the value of the offset.
     * @param unit  temporal unit of the offset.
     * @throws NullPointerException     if the given unit is {@code null}.
     * @throws IllegalArgumentException if the given value is negative.
     */
    public TemporalUnitOffset(long value, TemporalUnit unit) {
        Objects.requireNonNull(unit, "Unit should be not null");
        checkThatValueIsPositive(value);
        this.value = value;
        this.unit = unit;
    }

    private void checkThatValueIsPositive(long value) {
        assert value >= 0 : "The value of the offset should be greater than zero";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBeyondOffsetDifferenceDescription(Temporal temporal1, Temporal temporal2) {
        return format("%s %s but difference was %s %s", value, unit, getDifference(temporal1, temporal2), unit);
    }

    /**
     * Returns absolute value of the difference according to time unit.
     *
     * @param temporal1 the first {@link Temporal}
     * @param temporal2 the second {@link Temporal}
     * @return absolute value of the difference according to time unit.
     */
    protected long getDifference(final Temporal temporal1, final Temporal temporal2) {
        return abs(unit.between(temporal1, temporal2));
    }
}
