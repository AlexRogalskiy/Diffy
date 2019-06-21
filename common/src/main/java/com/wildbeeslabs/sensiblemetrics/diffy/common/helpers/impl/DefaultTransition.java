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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.iface.Transition;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * Transition entity implementation
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Data
@EqualsAndHashCode
public class DefaultTransition<T> implements Transition<T, DefaultState<T>>, Serializable, Cloneable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6984105665088971911L;

    /**
     * Default min value
     */
    private final T min;
    /**
     * Default max value
     */
    private final T max;
    /**
     * {@link DefaultState} end value
     */
    private final DefaultState<T> to;
    /**
     * Default {@link Comparator}
     */
    private final Comparator<? super T> comparator;

    /**
     * Constructs a new singleton interval transition.
     *
     * @param c  transition character
     * @param to destination state
     */
    public DefaultTransition(final T c, final DefaultState<T> to) {
        this(c, c, to, null);
    }

    /**
     * Constructs a new transition.
     * Both end points are included in the interval.
     *
     * @param min        initial input transition {@code T} interval minimum
     * @param max        initial input transition {@code T} interval maximum
     * @param to         initial input destination {@link DefaultState}
     * @param comparator initial input {@link Comparator}
     */
    public DefaultTransition(final T min, final T max, final DefaultState<T> to, final Comparator<? super T> comparator) {
        this.min = min;
        this.max = max;
        this.to = to;
        this.comparator = Optional.ofNullable(comparator).orElseGet(ComparableComparator::getInstance);
    }

    /**
     * Clones this transition.
     *
     * @return clone with same character interval and destination state
     */
    @Override
    public DefaultTransition<T> clone() {
        try {
            return (DefaultTransition<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a string describing this state
     */
    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append(this.min);
        if (this.min != this.max) {
            b.append("-");
            b.append(this.max);
        }
        b.append(" -> ").append(this.to.getNumber());
        return b.toString();
    }

    public void appendDot(final StringBuilder b) {
        b.append(" -> ").append(this.to.getNumber()).append(" [label=\"");
        b.append(this.min);
        if (this.min != this.max) {
            b.append("-");
            b.append(this.max);
        }
        b.append("\"]\n");
    }

    @Override
    public boolean isPossible(final T value) {
        return Objects.compare(this.getMin(), value, this.getComparator()) <= 0 && Objects.compare(value, this.getMax(), this.getComparator()) <= 0;
    }

    @Override
    public DefaultState<T> state() {
        return this.to;
    }
}
