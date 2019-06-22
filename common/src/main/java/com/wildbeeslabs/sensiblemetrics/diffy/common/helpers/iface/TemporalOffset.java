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
