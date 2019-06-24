/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces;

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Duration;

/**
 * Time format declaration
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
    String format(final Duration duration);

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
