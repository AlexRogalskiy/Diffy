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
 * Time measure interface declaration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
public interface TimeMeasure extends Comparable<TimeMeasure> {

    /**
     * Returns the number of milliseconds represented by the current time unit
     * {@link TimeMeasure} instance (positive number greater than zero)
     *
     * @return number of milliseconds represented by the current time unit
     * {@link TimeMeasure} instance
     */
    long getMillisPerUnit();

    /**
     * Returns the maximum quantity of the current time unit {@link TimeMeasure}
     * to be used as a threshold for the next largest time unit (e.g. if one
     * <code>Second</code> represents 1000ms, and <code>Second</code> has a
     * maxQuantity of 5, then if the difference between compared timestamps is
     * larger than 5000ms, PrettyTime will move on to the next smallest TimeUnitType
     * for calculation; <code>Minute</code>, by default)
     * <p>
     * millisPerUnit * maxQuantity = maxAllowedMs
     * <p>
     * If maxQuantity is zero, it will be equal to the next highest      <code>TimeUnitType.getMillisPerUnit() /
     * this.getMillisPerUnit()</code> or infinity if there are no greater
     * TimeUnits
     *
     * @return maximum quantity of the current time unit {@link TimeMeasure}
     * instance to be used as a threshold for the next largest time unit
     */
    long getMaxQuantity();

    /**
     * Whether or not this {@link TimeMeasure} represents a price measurement of
     * time, or a general concept of time. E.g: "minute" as opposed to "moment".
     *
     * @return true - if current time unit represents a price / general concept
     * of time, false - otherwise
     */
    boolean isPrecise();

    /**
     * Default TimeMeasure comparator
     *
     * @param obj
     * @return
     */
    @Override
    default int compareTo(final TimeMeasure obj) {
        if (this.getMillisPerUnit() < obj.getMillisPerUnit()) {
            return -1;
        } else if (this.getMillisPerUnit() > obj.getMillisPerUnit()) {
            return 1;
        }
        return 0;
    }
}
