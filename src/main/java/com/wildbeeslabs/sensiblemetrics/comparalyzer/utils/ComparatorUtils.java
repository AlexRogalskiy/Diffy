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

import lombok.experimental.UtilityClass;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

/**
 * Custom comparator utilities implementation
 */
@UtilityClass
public class ComparatorUtils {

    /**
     * Default comparable comparator implementation {@link T}
     *
     * @param <T>
     */
    public static class DefaultComparableComparator<T extends Comparable<? super T>> implements Comparator<T>, Serializable {

        /**
         * Default explicit serialVersionUID for interoperability
         */
        private static final long serialVersionUID = 1893076360167804802L;

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument
         * @param last  - initial last argument
         * @return numeric result of two entries comparison
         */
        @Override
        public int compare(final T first, final T last) {
            return ComparatorUtils.compareTo(first, last);
        }
    }

    /**
     * Default comparator implementation {@link Object}
     */
    public static class DefaultComparator implements Comparator<Object>, Serializable {

        /**
         * Default explicit serialVersionUID for interoperability
         */
        private static final long serialVersionUID = 483179982991933496L;

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial first argument {@link Object}
         * @param last  - initial last argument {@link Object}
         * @return numeric result of two entries comparison
         */
        @Override
        public int compare(final Object first, final Object last) {
            if (first == last) {
                return 0;
            }
            if (Objects.isNull(first)) {
                return -1;
            }
            if (Objects.isNull(last)) {
                return 1;
            }
            return first.toString().compareTo(last.toString());
        }
    }

    /**
     * Returns default object comparator instance {@link Comparator}
     *
     * @return default comparator instance {@link Comparator}
     */
    public static Comparator<? super Object> getDefaultComparator() {
        return new DefaultComparator();
    }

    /**
     * Returns default comparable comparator instance {@link Comparator}
     *
     * @param <T>
     * @return default comparator instance {@link Comparator}
     */
    public static <T> Comparator<? super T> getDefaultComparableComparator() {
        return new DefaultComparableComparator();
    }

    /**
     * Returns numeric result of initial arguments {@link T} comparison by {@link Comparator}
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return numeric value of comparison
     */
    public static <T> int compare(final T first, final T last) {
        return compare(first, last, getDefaultComparableComparator());
    }

    /**
     * Returns numeric result of initial arguments {@link T} comparison by {@link Comparator}
     *
     * @param <T>
     * @param first      - initial first argument {@link T}
     * @param last       - initial last argument {@link T}
     * @param comparator - initial comparator instance {@link Comparator}
     * @return numeric value of comparison
     */
    public static <T> int compare(final T first, final T last, final Comparator<? super T> comparator) {
        return Objects.compare(first, last, comparator);
    }

    /**
     * Returns numeric result of null-safe integer arguments {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int intCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.intValue() : null, Objects.nonNull(last) ? last.intValue() : null);
    }

    /**
     * Returns numeric result of null-safe long arguments {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int longCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.longValue() : null, Objects.nonNull(last) ? last.longValue() : null);
    }

    /**
     * Returns numeric result of null-safe float arguments {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int floatCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.floatValue() : null, Objects.nonNull(last) ? last.floatValue() : null);
    }

    /**
     * Returns numeric result of null-safe double arguments {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int doubleCompareTo(final T first, final T last) {
        return compareTo(Objects.nonNull(first) ? first.doubleValue() : null, Objects.nonNull(last) ? last.doubleValue() : null);
    }

    /**
     * Returns numeric result of null-safe numeric arguments as bigDecimal {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int bigDecimalCompareTo(final T first, final T last) {
        return bigDecimalCompareTo(Objects.nonNull(first) ? new BigDecimal(first.toString()) : null, Objects.nonNull(last) ? new BigDecimal(last.toString()) : null);
    }

    /**
     * Returns numeric result of null-safe bigDecimal arguments {@link BigDecimal} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link BigDecimal}
     * @param last  - initial last argument {@link BigDecimal}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Number> int bigDecimalCompareTo(final BigDecimal first, final BigDecimal last) {
        return compareTo(first, last);
    }

    /**
     * Returns numeric result of null-safe object arguments as string {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link BigDecimal}
     * @param last  - initial last argument {@link BigDecimal}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T> int stringCompareTo(final T first, final T last) {
        return stringCompareTo(String.valueOf(first), String.valueOf(last));
    }

    /**
     * Returns numeric result of null-safe string arguments {@link String} comparison
     *
     * @param first - initial first argument {@link String}
     * @param last  - initial last argument {@link String}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static int stringCompareTo(final String first, final String last) {
        final boolean f1, f2;
        return (f1 = Objects.isNull(first)) ^ (f2 = Objects.isNull(last)) ? (f1 ? -1 : 1) : (f1 && f2 ? 0 : first.compareToIgnoreCase(last));
    }

    /**
     * Returns numeric result of null-safe object arguments {@link T} comparison
     *
     * @param <T>
     * @param first - initial first argument {@link T}
     * @param last  - initial last argument {@link T}
     * @return a negative integer, zero, or a positive integer if the first
     * argument is less than, equal to, or greater than the second
     */
    public static <T extends Comparable<? super T>> int compareTo(final T first, final T last) {
        final boolean f1, f2;
        return (f1 = Objects.isNull(first)) ^ (f2 = Objects.isNull(last)) ? f1 ? -1 : 1 : f1 && f2 ? 0 : first.compareTo(last);
    }
}
