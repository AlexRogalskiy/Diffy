package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

/**
 * Custom time format provider declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
public interface TimeFormatProvider {

    /**
     * Return the appropriate {@link TimeFormat} for the given
     * {@link TimeMeasure}
     *
     * @param timeUnit
     * @return
     */
    TimeFormat getFormat(final TimeMeasure timeUnit);
}
