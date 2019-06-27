/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces.Clock;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple {@link Clock} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class SimpleClock implements Clock {
    /**
     * Default {@link AtomicLong} time
     */
    private final AtomicLong time;

    /**
     * Construct a new clock setting the current time to {@code init}.
     *
     * @param init Number of milliseconds to use as the initial time.
     */
    public SimpleClock(final long init) {
        this.time = new AtomicLong(init);
    }

    /**
     * Update the current time to {@code t}.
     *
     * @param t Number of milliseconds to use for the current time.
     */
    public void set(final long t) {
        this.time.set(t);
    }

    /**
     * {@inheritDoc}
     */
    public long now() {
        return this.time.get();
    }
}
