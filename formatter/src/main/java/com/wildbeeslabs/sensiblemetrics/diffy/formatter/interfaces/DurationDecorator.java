/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
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
 * Custom duration format declaration (represents string-based formatted
 * duration {@link IDuration} instances)
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
public interface DurationDecorator {

    /**
     * Returns string-formatted (with rounding) duration {@link IDuration}
     * instance
     *
     * @param duration - the current duration {@link IDuration} instance from
     *                 which the time string should be decorated
     * @return string-formatted (with rounding) duration {@link IDuration}
     * output
     */
    String format(final Duration duration);

    /**
     * Returns string-formatted (without rounding) duration {@link IDuration}
     * instance
     *
     * @param duration - the current duration {@link IDuration} instance from
     *                 which the time string should be decorated
     * @return string-formatted (without rounding) duration {@link IDuration}
     * output
     */
    String formatUnrounded(final Duration duration);

    /**
     * Returns string-formatted (with rounding) duration {@link IDuration}
     * instance with past/future prefix/suffix decoration
     *
     * @param duration - the current duration {@link IDuration} instance from
     *                 which the time string should be decorated
     * @param time     - formatted time string
     * @return string-formatted (with rounding) duration {@link IDuration}
     * output
     */
    String decorate(final Duration duration, final String time);

    /**
     * Returns string-formatted (without rounding) duration {@link IDuration}
     * instance with past/future prefix/suffix decoration
     *
     * @param duration - the current duration {@link IDuration} instance from
     *                 which the time string should be decorated
     * @param time     - formatted time string
     * @return string-formatted (without rounding) duration {@link IDuration}
     * output
     */
    String decorateUnrounded(final Duration duration, final String time);
}
