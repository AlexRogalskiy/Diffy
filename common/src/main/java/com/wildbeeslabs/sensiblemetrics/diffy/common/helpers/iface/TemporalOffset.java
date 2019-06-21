package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

import java.time.temporal.Temporal;

/**
 * {@link Temporal} offset.
 *
 * @param <T> the type of the {@code Temporal} values to be checked against offset.
 * @since 3.7.0
 */
public interface TemporalOffset<T extends Temporal> {

    /**
     * Checks if difference between temporal values is beyond offset.
     *
     * @param temporal1 first temporal value to be validated against second temporal value.
     * @param temporal2 second temporal value.
     * @return true if difference between temporal values is beyond offset.
     */
    boolean isBeyondOffset(final T temporal1, final T temporal2);

    /**
     * Returns description of the difference between temporal values and expected offset details.
     * Is designed for the case when difference is beyond offset.
     *
     * @param temporal1 first temporal value which is being validated against second temporal value.
     * @param temporal2 second temporal value.
     * @return difference description.
     */
    String getBeyondOffsetDifferenceDescription(final T temporal1, final T temporal2);
}
