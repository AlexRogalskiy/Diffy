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

import com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.iface.Ranger;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * {@link Ranger} implementation
 *
 * @param <T> type of range bound element
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
@SuppressWarnings("unchecked")
public final class DefaultRanger<T> implements Ranger<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -3956107567150562347L;

    /**
     * The ordering scheme used in this range.
     */
    private final Comparator<? super T> comparator;
    /**
     * Range lower bound {@code T1}
     */
    private final T lowerBound;
    /**
     * Range upper bound {@code T}
     */
    private final T upperBound;

    /**
     * Creates default ranger instance
     *
     * @param lowerBound the first element, not null
     * @param upperBound the second element, not null
     */
    private DefaultRanger(final T lowerBound, final T upperBound) {
        this(lowerBound, upperBound, null);
    }

    /**
     * Creates default ranger instance
     *
     * @param lowerBound the first element, not null
     * @param upperBound the second element, not null
     * @param comparator the comparator to be used, null for natural ordering
     */
    private DefaultRanger(final T lowerBound, final T upperBound, final Comparator<? super T> comparator) {
        ValidationUtils.notNull(lowerBound, "Lower bound should not be null");
        ValidationUtils.notNull(upperBound, "Upper bound should not be null");

        this.comparator = Optional.ofNullable(comparator).orElseGet(ComparableComparator::getInstance);
        if (this.getComparator().compare(lowerBound, upperBound) < 1) {
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        } else {
            this.lowerBound = upperBound;
            this.upperBound = lowerBound;
        }
    }

    /**
     * Returns {@link Ranger} by input parameters
     *
     * @param <T>        type of range bound element
     * @param lowerBound - initial input range lower bound {@code T}
     * @param upperBound - initial input range upper bound {@code T}
     * @return {@link Ranger}
     */
    public static <T> Ranger<T> of(final T lowerBound, final T upperBound) {
        return new DefaultRanger<>(lowerBound, upperBound);
    }

    /**
     * Returns {@link Ranger} by input parameters
     *
     * @param <T>   type of range bound element
     * @param value - initial input range lower/upper bound {@code T}
     * @return {@link Ranger}
     */
    public static <T> Ranger<T> point(final T value) {
        return new DefaultRanger<>(value, value);
    }

    /**
     * Returns {@link Ranger} if both {@link Optional} instances have values or {@link Optional#empty()} if one or both
     * are missing.
     *
     * @param <T>        type of range bound element
     * @param lowerBound - initial input range lower bound {@link Optional}
     * @param upperBound - initial input range upper bound {@link Optional}
     * @return {@link Optional} of {@link Ranger}
     */
    public static <T> Optional<Ranger<T>> with(final Optional<T> lowerBound, final Optional<T> upperBound) {
        return lowerBound.flatMap(l -> upperBound.map(u -> DefaultRanger.of(l, u)));
    }

    /**
     * <p>Obtains a range using the specified element as both the minimum
     * and maximum in this range.</p>
     *
     * <p>The range uses the natural ordering of the elements to determine where
     * values lie in the range.</p>
     *
     * @param <T>     the type of the elements in this range
     * @param element the value to use for this range, not null
     * @return the range object, not null
     * @throws IllegalArgumentException if the element is null
     * @throws ClassCastException       if the element is not {@code Comparable}
     */
    public static <T extends Comparable<T>> Ranger<T> is(final T element) {
        return between(element, element, null);
    }

    /**
     * <p>Obtains a range using the specified element as both the minimum
     * and maximum in this range.</p>
     *
     * <p>The range uses the specified {@code Comparator} to determine where
     * values lie in the range.</p>
     *
     * @param <T>        the type of the elements in this range
     * @param element    the value to use for this range, must not be {@code null}
     * @param comparator the comparator to be used, null for natural ordering
     * @return the range object, not null
     * @throws IllegalArgumentException if the element is null
     * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
     */
    public static <T> Ranger<T> is(final T element, final Comparator<T> comparator) {
        return between(element, element, comparator);
    }

    /**
     * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
     *
     * <p>The range uses the natural ordering of the elements to determine where
     * values lie in the range.</p>
     *
     * <p>The arguments may be passed in the order (min,max) or (max,min).
     * The getMinimum and getMaximum methods will return the correct values.</p>
     *
     * @param <T>           the type of the elements in this range
     * @param fromInclusive the first value that defines the edge of the range, inclusive
     * @param toInclusive   the second value that defines the edge of the range, inclusive
     * @return the range object, not null
     * @throws IllegalArgumentException if either element is null
     * @throws ClassCastException       if the elements are not {@code Comparable}
     */
    public static <T extends Comparable<T>> Ranger<T> between(final T fromInclusive, final T toInclusive) {
        return between(fromInclusive, toInclusive);
    }

    /**
     * <p>Obtains a range with the specified minimum and maximum values (both inclusive).</p>
     *
     * <p>The range uses the specified {@code Comparator} to determine where
     * values lie in the range.</p>
     *
     * <p>The arguments may be passed in the order (min,max) or (max,min).
     * The getMinimum and getMaximum methods will return the correct values.</p>
     *
     * @param <T>           the type of the elements in this range
     * @param fromInclusive the first value that defines the edge of the range, inclusive
     * @param toInclusive   the second value that defines the edge of the range, inclusive
     * @param comparator    the comparator to be used, null for natural ordering
     * @return the range object, not null
     * @throws IllegalArgumentException if either element is null
     * @throws ClassCastException       if using natural ordering and the elements are not {@code Comparable}
     */
    public static <T> Ranger<T> between(final T fromInclusive, final T toInclusive, final Comparator<? super T> comparator) {
        return new DefaultRanger<>(fromInclusive, toInclusive, comparator);
    }

    /**
     * <p>Whether or not the Range is using the natural ordering of the elements.</p>
     *
     * <p>Natural ordering uses an internal comparator implementation, thus this
     * method is the only way to check if a null comparator was specified.</p>
     *
     * @return true if using natural ordering
     */
    public boolean isNaturalOrdering() {
        return Objects.equals(this.getComparator(), ComparableComparator.getInstance());
    }

    /**
     * <p>Checks whether this range is after the specified element.</p>
     *
     * @param element the element to check for, null returns false
     * @return true if this range is entirely after the specified element
     */
    public boolean isAfter(final T element) {
        if (Objects.isNull(element)) {
            return false;
        }
        return this.getComparator().compare(element, this.getLowerBound()) < 0;
    }

    /**
     * <p>Checks whether this range starts with the specified element.</p>
     *
     * @param element the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    public boolean isStartedBy(final T element) {
        if (Objects.isNull(element)) {
            return false;
        }
        return this.getComparator().compare(element, this.getLowerBound()) == 0;
    }

    /**
     * <p>Checks whether this range ends with the specified element.</p>
     *
     * @param element the element to check for, null returns false
     * @return true if the specified element occurs within this range
     */
    public boolean isEndedBy(final T element) {
        if (Objects.isNull(element)) {
            return false;
        }
        return this.getComparator().compare(element, this.getUpperBound()) == 0;
    }

    /**
     * <p>Checks whether this range is before the specified element.</p>
     *
     * @param element the element to check for, null returns false
     * @return true if this range is entirely before the specified element
     */
    public boolean isBefore(final T element) {
        if (Objects.isNull(element)) {
            return false;
        }
        return this.getComparator().compare(element, this.getUpperBound()) > 0;
    }

    /**
     * <p>Checks where the specified element occurs relative to this range.</p>
     *
     * <p>The API is reminiscent of the Comparable interface returning {@code -1} if
     * the element is before the range, {@code 0} if contained within the range and
     * {@code 1} if the element is after the range. </p>
     *
     * @param element the element to check for, not null
     * @return -1, 0 or +1 depending on the element's location relative to the range
     */
    public int compareTo(final T element) {
        ValidationUtils.notNull(element, "Element should not be null");
        if (this.isAfter(element)) {
            return -1;
        } else if (this.isBefore(element)) {
            return 1;
        }
        return 0;
    }

    /**
     * <p>Checks whether this range contains all the elements of the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange the range to check, null returns false
     * @return true if this range contains the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    public boolean containsRange(final Ranger<T> otherRange) {
        if (Objects.isNull(otherRange)) {
            return false;
        }
        return this.contains(otherRange.getLowerBound()) && this.contains(otherRange.getUpperBound());
    }

    /**
     * <p>Checks whether this range is completely after the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange the range to check, null returns false
     * @return true if this range is completely after the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    public boolean isAfter(final Ranger<T> otherRange) {
        if (Objects.isNull(otherRange)) {
            return false;
        }
        return this.isAfter(otherRange.getUpperBound());
    }

    /**
     * <p>Checks whether this range is overlapped by the specified range.</p>
     *
     * <p>Two ranges overlap if there is at least one element in common.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange the range to test, null returns false
     * @return true if the specified range overlaps with this
     * range; otherwise, {@code false}
     * @throws RuntimeException if ranges cannot be compared
     */
    public boolean isOverlap(final Ranger<T> otherRange) {
        if (Objects.isNull(otherRange)) {
            return false;
        }
        return otherRange.contains(this.getLowerBound()) || otherRange.contains(this.getUpperBound()) || this.contains(otherRange.getLowerBound());
    }

    /**
     * <p>Checks whether this range is completely before the specified range.</p>
     *
     * <p>This method may fail if the ranges have two different comparators or element types.</p>
     *
     * @param otherRange the range to check, null returns false
     * @return true if this range is completely before the specified range
     * @throws RuntimeException if ranges cannot be compared
     */
    public boolean isBefore(final Ranger<T> otherRange) {
        if (Objects.isNull(otherRange)) {
            return false;
        }
        return this.isBefore(otherRange.getLowerBound());
    }

    /**
     * Returns binary flag by input parameters
     *
     * @param otherRange - initial input {@link Ranger}
     * @return true - if current {@link Ranger} equals to input {@link Ranger}, false - otherwise
     */
    public boolean isEqual(final Ranger<T> otherRange) {
        ValidationUtils.notNull(otherRange, "Range should not be null");
        return Objects.equals(this.getLowerBound(), otherRange.getLowerBound()) && Objects.equals(this.getUpperBound(), otherRange.getUpperBound());
    }

    /**
     * Calculate the intersection of {@code this} and an overlapping Range.
     *
     * @param other overlapping Range
     * @return range representing the intersection of {@code this} and {@code other} ({@code this} if equal)
     * @throws IllegalArgumentException if {@code other} does not overlap {@code this}
     * @since 3.0.1
     */
    public Ranger<T> intersectionWith(final Ranger<T> other) {
        if (!this.isOverlap(other)) {
            throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", other));
        }
        if (this.equals(other)) {
            return this;
        }
        final T min = getComparator().compare(this.getLowerBound(), other.getLowerBound()) < 0 ? other.getLowerBound() : this.getLowerBound();
        final T max = getComparator().compare(this.getUpperBound(), other.getUpperBound()) < 0 ? this.getUpperBound() : other.getUpperBound();
        return between(min, max, this.getComparator());
    }
}
