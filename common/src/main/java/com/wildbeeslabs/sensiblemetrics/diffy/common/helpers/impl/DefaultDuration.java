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

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Duration;
import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.TimeMeasure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default duration implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultDuration implements Duration {

    private long quantity;
    private long delta;
    private TimeMeasure unit;

    @Override
    public long getQuantityRounded(int tolerance) {
        long value = Math.abs(this.getQuantity());
        if (0 != this.getDelta()) {
            double threshold = Math.abs(((double) this.getDelta() / (double) this.getUnit().getMillisPerUnit()) * 100);
            if (threshold > tolerance) {
                value++;
            }
        }
        return value;
    }

    @Override
    public boolean isInPast() {
        return this.getQuantity() < 0;
    }

    @Override
    public boolean isInFuture() {
        return !this.isInPast();
    }
}
