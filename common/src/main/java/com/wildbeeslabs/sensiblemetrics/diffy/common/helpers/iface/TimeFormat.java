package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

/**
 * Custom time format declaration
 *
 * @author Alex
 */
public interface TimeFormat {

    /**
     * Given a populated {@link Duration} object. Apply formatting (with
     * rounding) and output the result.
     *
     * @param duration original {@link Duration} instance from which the time string
     *                 should be decorated.
     * @return formatted string
     */
    abstract String format(final Duration duration);

    /**
     * Given a populated {@link Duration} object. Apply formatting (without
     * rounding) and output the result.
     *
     * @param duration original {@link Duration} instance from which the time string
     *                 should be decorated.
     * @return formatted string
     */
    String formatUnrounded(final Duration duration);

    /**
     * Decorate with past or future prefix/suffix (with rounding)
     *
     * @param duration The original {@link Duration} instance from which the
     *                 time string should be decorated.
     * @param time     The formatted time string.
     * @return formatted string
     */
    String decorate(final Duration duration, final String time);

    /**
     * Decorate with past or future prefix/suffix (without rounding)
     *
     * @param duration The original {@link Duration} instance from which the
     *                 time string should be decorated.
     * @param time     The formatted time string.
     * @return formatted string
     */
    String decorateUnrounded(final Duration duration, final String time);
}
