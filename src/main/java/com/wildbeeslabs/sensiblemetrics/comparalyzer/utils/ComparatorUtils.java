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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.utils;

import com.google.common.collect.Iterables;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

/**
 * Custom comparator utilities implementation {@link Comparator}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ComparatorUtils {

    /**
     * Default comparator
     */
    public static final Comparator<? super Object> DEFAULT_COMPARATOR = getDefaultComparator();

    /**
     * Default null safe comparator implementation
     *
     * @param <T> type of input element to be compared by operation
     */
    @EqualsAndHashCode
    @ToString
    public static class DefaultNullSafeComparator<T> implements Comparator<T> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         * Byte.MAX_VALUE - if arguments are different (and not null either)
         *
         * @param first - initial first argument
         * @param last  - initial last argument
         * @return numeric result of two objects comparison
         */
        @Override
        public int compare(final T first, final T last) {
            if (first == last) {
                return 0;
            }
            if (Objects.isNull(first)) {
                return -1;
            } else if (Objects.isNull(last)) {
                return 1;
            }
            return Byte.MAX_VALUE;
        }
    }

    /**
     * Default comparable comparator implementation
     *
     * @param <T> type of input element to be compared by operation
     */
    @EqualsAndHashCode
    @ToString
    public static class DefaultComparableComparator<T extends Comparable<? super T>> implements Comparator<T> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument
         * @param last  - initial last argument
         * @return numeric result of two objects comparison
         */
        @Override
        public int compare(final T first, final T last) {
            return ComparatorUtils.compareTo(first, last);
        }
    }

    /**
     * Default object comparator implementation {@link Object}
     */
    @EqualsAndHashCode
    @ToString
    public static class DefaultComparator extends DefaultNullSafeComparator<Object> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument {@link Object}
         * @param last  - initial last argument {@link Object}
         * @return numeric result of two objects comparison
         */
        @Override
        public int compare(final Object first, final Object last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                return first.toString().compareTo(last.toString());
            }
            return comp;
        }
    }

    /**
     * Default class comparator implementation {@link Class}
     */
    @EqualsAndHashCode
    @ToString
    public static class DefaultClassComparator implements Comparator<Class<?>> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument {@link Class}
         * @param last  - initial last argument {@link Class}
         * @return numeric result of two objects comparison
         */
        @Override
        public int compare(final Class<?> first, final Class<?> last) {
            if (first.isAssignableFrom(last)) {
                return 1;
            } else if (last.isAssignableFrom(first)) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * Default iterable comparator implementation {@link Iterable}
     *
     * @param <T> type of input element to be compared by operation
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DefaultIterableComparator<T> extends DefaultNullSafeComparator<Iterable<T>> {

        /**
         * Custom comparator instance {@link Comparator}
         */
        private final Comparator<? super T> comparator;

        /**
         * Default iterable comparator constructor with initial comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultIterableComparator(final Comparator<? super T> comparator) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument {@link Iterable}
         * @param last  - initial last argument {@link Iterable}
         * @return numeric result of two iterable objects comparison
         */
        @Override
        public int compare(final Iterable<T> first, final Iterable<T> last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int firstSize = Iterables.size(first);
                int lastSize = Iterables.size(last);
                if (firstSize < lastSize) {
                    return -1;
                } else if (firstSize > lastSize) {
                    return 1;
                }
                final Iterator<T> iteratorFirst = first.iterator();
                final Iterator<T> iteratorLast = last.iterator();
                int temp;
                while (iteratorFirst.hasNext()) {
                    temp = Objects.compare(iteratorFirst.next(), iteratorLast.next(), getComparator());
                    if (0 != temp) {
                        return temp;
                    }
                }
                return 0;
            }
            return comp;
        }
    }

    /**
     * Default big decimal comparator implementation {@link BigDecimal}
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DefaultBigDecimalComparator extends DefaultNullSafeComparator<BigDecimal> {

        /**
         * Default significant decimal places
         */
        private int significantDecimalPlaces;
        /**
         * Custom comparator instance {@link Comparator}
         */
        private final Comparator<? super BigDecimal> comparator;

        /**
         * Default big decimal comparator
         *
         * @param significantDecimalPlaces - initial significant decimal places
         */
        public DefaultBigDecimalComparator(int significantDecimalPlaces) {
            this(0, null);
        }

        /**
         * Default big decimal comparator
         *
         * @param significantDecimalPlaces - initial significant decimal places
         * @param comparator               - initial input comparator instance {@link Comparator}
         */
        public DefaultBigDecimalComparator(int significantDecimalPlaces, final Comparator<? super BigDecimal> comparator) {
            this.significantDecimalPlaces = significantDecimalPlaces;
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument {@link BigDecimal}
         * @param last  - initial last argument {@link BigDecimal}
         * @return numeric result of two iterable objects comparison
         */
        @Override
        public int compare(final BigDecimal first, final BigDecimal last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                final BigDecimal firstRounded = first.setScale(significantDecimalPlaces, BigDecimal.ROUND_HALF_UP);
                final BigDecimal lastRounded = last.setScale(significantDecimalPlaces, BigDecimal.ROUND_HALF_UP);
                return Objects.compare(firstRounded, lastRounded, getComparator());
            }
            return comp;
        }
    }

    /**
     * Returns object {@link Object} comparator instance {@link Comparator}
     *
     * @return object {@link Object} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super Object> getDefaultComparator() {
        return new DefaultComparator();
    }

    /**
     * Returns comparable {@link Comparable} comparator instance {@link Comparator}
     *
     * @param <T> type of input element to be compared by operation
     * @return comparable {@link Comparable} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T> getDefaultComparableComparator() {
        return new DefaultComparableComparator();
    }

    /**
     * Returns class {@link Class} comparator instance {@link Comparator}
     *
     * @return class {@link Class} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super Class<?>> getDefaultClassComparator() {
        return new DefaultClassComparator();
    }

    /**
     * Returns iterable {@link Iterable} comparator instance {@link Comparator}
     *
     * @param <T> type of input element to be compared by operation
     * @return iterable {@link Iterable} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super Iterable<T>> getDefaultIterableComparator(final Comparator<? super T> comparator) {
        return new DefaultIterableComparator(comparator);
    }

    /**
     * Returns big decimal {@link BigDecimal} comparator instance {@link Comparator}
     *
     * @param <T> type of input element to be compared by operation
     * @return iterable {@link BigDecimal} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super BigDecimal> getDefaultBigDecimalComparator(int significantDecimalPlaces, final Comparator<? super BigDecimal> comparator) {
        return new DefaultBigDecimalComparator(significantDecimalPlaces, comparator);
    }

    /**
     * Returns numeric result of initial arguments comparison by {@link Comparator}
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return numeric value of comparison
     */
    public static <T> int compare(final T first, final T last) {
        return compare(first, last, getDefaultComparableComparator());
    }

    /**
     * Returns numeric result by initial comparator instance {@link Comparator}
     *
     * @param <T>        type of input element to be compared by operation
     * @param first      - initial first input argument
     * @param last       - initial last input argument
     * @param comparator - initial comparator instance {@link Comparator}
     * @return numeric value of comparison
     */
    public static <T> int compare(final T first, final T last, final Comparator<? super T> comparator) {
        return Objects.compare(first, last, comparator);
    }

    /**
     * Returns numeric result by null-safe integer arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int intCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.intValue() : null, Objects.nonNull(last) ? last.intValue() : null);
    }

    /**
     * Returns numeric result by null-safe long arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int longCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.longValue() : null, Objects.nonNull(last) ? last.longValue() : null);
    }

    /**
     * Returns numeric result by null-safe float arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int floatCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.floatValue() : null, Objects.nonNull(last) ? last.floatValue() : null);
    }

    /**
     * Returns numeric result by null-safe double arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int doubleCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.doubleValue() : null, Objects.nonNull(last) ? last.doubleValue() : null);
    }

    /**
     * Returns numeric result by null-safe BigDecimal-like arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int bigDecimalCompareTo(final T first, final T last) {
        return bigDecimalCompareTo(Objects.nonNull(first) ? new BigDecimal(first.toString()) : null, Objects.nonNull(last) ? new BigDecimal(last.toString()) : null);
    }

    /**
     * Returns numeric result by null-safe {@link BigDecimal} arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument {@link BigDecimal}
     * @param last  - initial last input argument {@link BigDecimal}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int bigDecimalCompareTo(final BigDecimal first, final BigDecimal last) {
        return compareTo(first, last);
    }

    /**
     * Returns numeric result by null-safe string-like arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument {@link BigDecimal}
     * @param last  - initial last input argument {@link BigDecimal}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T> int stringCompareTo(final T first, final T last) {
        return stringCompareTo(String.valueOf(first), String.valueOf(last));
    }

    /**
     * Returns numeric result by null-safe {@link String} arguments comparison
     *
     * @param first - initial first input argument {@link String}
     * @param last  - initial last input argument {@link String}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static int stringCompareTo(final String first, final String last) {
        final boolean f1, f2;
        return (f1 = Objects.isNull(first)) ^ (f2 = Objects.isNull(last)) ? (f1 ? -1 : 1) : (f1 && f2 ? 0 : first.compareToIgnoreCase(last));
    }

    /**
     * Returns numeric result by null-safe general arguments comparison
     *
     * @param <T>   type of input element to be compared by operation
     * @param first - initial first input argument
     * @param last  - initial last input argument
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Comparable<? super T>> int compareTo(final T first, final T last) {
        final boolean f1, f2;
        return (f1 = Objects.isNull(first)) ^ (f2 = Objects.isNull(last)) ? f1 ? -1 : 1 : f1 && f2 ? 0 : first.compareTo(last);
    }
}
