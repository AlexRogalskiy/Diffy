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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface;

/**
 * DurationHelper time declaration (measure quantity of time unit
 * {@link TimeMeasure} instances)
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
public interface Duration {

    /**
     * Returns the calculated quantity of time unit {@link TimeMeasure} instances
     *
     * @return quantity of time unit {@link TimeMeasure}
     */
    long getQuantity();

    /**
     * Returns the calculated quantity of time unit {@link TimeMeasure} instances,
     * rounded up if {@link #getDelta()} is greater than or equal to the given
     * tolerance
     *
     * @param tolerance - tolerance precision rate
     * @return calculated quantity of time unit {@link TimeMeasure}
     */
    long getQuantityRounded(int tolerance);

    /**
     * Returns the time unit {@link TimeMeasure} instance represented by the
     * current duration instance {@link Duration}
     *
     * @return quantity of time unit {@link TimeMeasure}
     */
    TimeMeasure getUnit();

    /**
     * Returns the number of milliseconds left over when calculating the number
     * of time unit {@link TimeMeasure} instances that fit into the given time
     * range
     *
     * @return number of milliseconds
     */
    long getDelta();

    /**
     * Checks if the current duration {@link Duration} instance represents a
     * number of time unit {@link TimeMeasure} instances in the past
     *
     * @return true if the current duration {@link Duration} instance
     * represents a number of time unit {@link TimeMeasure} instances in the past,
     * false - otherwise
     */
    boolean isInPast();

    /**
     * Checks if the current duration {@link Duration} instance represents a
     * number of time unit {@link TimeMeasure} instances in the future
     *
     * @return true if the current duration {@link Duration} instance
     * represents a number of time unit {@link TimeMeasure} instances in the
     * future, false - otherwise
     */
    boolean isInFuture();
}
