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
