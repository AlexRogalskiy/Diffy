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
import java.util.*;
import java.util.function.Predicate;

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
     * Default comparator with false first order {@link Comparator}
     */
    public static final Comparator<? super Boolean> DEFAULT_FALSE_FIRST = (a, b) -> a == b ? 0 : b ? -1 : 1;

    /**
     * Default comparator with true first order {@link Comparator}
     */
    public static final Comparator<? super Boolean> DEFAULT_TRUE_FIRST = (a, b) -> a == b ? 0 : a ? -1 : 1;

    /**
     * Returns boolean comparator with false first order {@link Comparator}
     *
     * @return boolean comparator with false first order {@link Comparator}
     */
    public static Comparator<? super Boolean> lastIf() {
        return DEFAULT_FALSE_FIRST;
    }

    /**
     * Returns boolean comparator with true first order {@link Comparator}
     *
     * @return boolean comparator with true first order {@link Comparator}
     */
    public static Comparator<? super Boolean> firstIf() {
        return DEFAULT_TRUE_FIRST;
    }

    /**
     * Returns comparator with false first order {@link Comparator} with initial predicate value {@link Predicate}
     *
     * @param <T>       type of value to be compared by
     * @param predicate - initial input predicate value {@link Predicate}
     * @return comparator with false first order {@link Comparator}
     */
    public static <T> Comparator<? super T> lastIf(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return (a, b) -> {
            final boolean pa = predicate.test(a), pb = predicate.test(b);
            return pa == pb ? 0 : pb ? -1 : 1;
        };
    }

    /**
     * Returns comparator with true first order {@link Comparator} by initial predicate value {@link Predicate}
     *
     * @param <T>       type of value to be compared by
     * @param predicate - initial input predicate value {@link Predicate}
     * @return comparator with true first order {@link Comparator}
     */
    public static <T> Comparator<? super T> firstIf(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        return (a, b) -> {
            final boolean pa = predicate.test(a), pb = predicate.test(b);
            return pa == pb ? 0 : pa ? -1 : 1;
        };
    }

    /**
     * Returns comparator with true first order {@link Comparator} by initial predicate value {@link Predicate}, first order comparator {@link Comparator}, last order comparator {@link Comparator}
     *
     * @param <T>        type of value to be compared by
     * @param predicate  - initial input predicate value {@link Predicate}
     * @param firstOrder - initial input first order comparator {@link Comparator}
     * @param lastOrder  - initial input last order comparator {@link Comparator}
     * @return comparator with true first order {@link Comparator}
     */
    public static <T> Comparator<? super T> firstIf(final Predicate<T> predicate, final Comparator<T> firstOrder, final Comparator<T> lastOrder) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(firstOrder);
        Objects.requireNonNull(lastOrder);
        return (a, b) -> {
            final boolean pa = predicate.test(a), pb = predicate.test(b);
            if (pa == pb) {
                return (pa ? firstOrder : lastOrder).compare(a, b);
            }
            return pb ? -1 : 1;
        };
    }

    /**
     * Returns comparator with false first order {@link Comparator} by initial predicate value {@link Predicate}, first order comparator {@link Comparator}, last order comparator {@link Comparator}
     *
     * @param <T>        type of value to be compared by
     * @param predicate  - initial input predicate value {@link Predicate}
     * @param firstOrder - initial input first order comparator {@link Comparator}
     * @param lastOrder  - initial input last order comparator {@link Comparator}
     * @return comparator with false first order {@link Comparator}
     */
    public static <T> Comparator<? super T> lastIf(final Predicate<T> predicate, final Comparator<T> firstOrder, final Comparator<T> lastOrder) {
        Objects.requireNonNull(predicate);
        return firstIf(predicate.negate(), firstOrder, lastOrder);
    }

    /**
     * Returns comparator with instances first order {@link Comparator} by initial class instance {@link Class}
     *
     * @param <T>   type of value to be compared by
     * @param clazz - initial input class instance {@link Class}
     * @return comparator with instances first order {@link Comparator}
     */
    public static <T> Comparator<? super T> instancesFirst(final Class<? extends T> clazz) {
        Objects.requireNonNull(clazz);
        return firstIf(clazz::isInstance);
    }

    /**
     * Returns comparator with instances last order {@link Comparator} by initial class instance {@link Class}
     *
     * @param <T>   type of value to be compared by
     * @param clazz - initial input class instance {@link Class}
     * @return comparator with instances last order {@link Comparator}
     */
    public static <T> Comparator<? super T> instancesLast(final Class<? extends T> clazz) {
        Objects.requireNonNull(clazz);
        return lastIf(clazz::isInstance);
    }

    /**
     * Returns comparator with instances first order {@link Comparator} by initial class {@link Class} and comparator instance {@link Comparator}
     *
     * @param <T>        type of value to be compared by
     * @param <U>        type of value to be compared with
     * @param clazz      - initial input class instance {@link Class}
     * @param comparator - initial input comparator {@link Comparator} to compare clazz instances by
     * @return comparator with instances first order {@link Comparator}
     */
    public static <T, U> Comparator<? super T> instancesFirst(final Class<? extends U> clazz, final Comparator<? super U> comparator) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(comparator);
        return (a, b) -> {
            if (clazz.isInstance(a)) {
                if (clazz.isInstance(b)) {
                    return comparator.compare(clazz.cast(a), clazz.cast(b));
                }
                return -1;
            }
            return clazz.isInstance(b) ? 1 : 0;
        };
    }

    /**
     * Returns comparator with instances last order {@link Comparator} by initial class {@link Class} and comparator instance {@link Comparator}
     *
     * @param <T>        type of value to be compared by
     * @param <U>        type of value to be compared with
     * @param clazz      - initial input class instance {@link Class}
     * @param comparator - initial input comparator {@link Comparator} to compare clazz instances by
     * @return comparator with instances last order {@link Comparator}
     */
    public static <T, U> Comparator<? super T> instancesLast(final Class<? extends U> clazz, final Comparator<? super U> comparator) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(comparator);
        return (a, b) -> {
            if (clazz.isInstance(a)) {
                if (clazz.isInstance(b)) {
                    return comparator.compare(clazz.cast(a), clazz.cast(b));
                }
                return 1;
            }
            return clazz.isInstance(b) ? -1 : 0;
        };
    }

    /**
     * Default comparator instance {@link Comparator}
     */
    public static final Comparator<? super Object> DEFAULT_COMPARATOR = getDefaultComparator();

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
     * @param <T> type of input element to be compared by
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
     * @param <T> type of input element to be compared by
     * @return iterable comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super Iterable<T>> getDefaultIterableComparator(final Comparator<? super T> comparator) {
        return new DefaultIterableComparator(comparator);
    }

    /**
     * Returns big decimal {@link BigDecimal} comparator instance {@link Comparator}
     *
     * @param <T> type of input element to be compared by
     * @return big decimal comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super BigDecimal> getDefaultBigDecimalComparator(int significantDecimalPlaces, final Comparator<? super BigDecimal> comparator) {
        return new DefaultBigDecimalComparator(significantDecimalPlaces, comparator);
    }

    /**
     * Returns map value comparator instance {@link Comparator}
     *
     * @param <K> type of key entry element
     * @param <V> type of value entry element to be compared by
     * @return value map comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Comparator<? super V> getDefaultValueMapComparator(final Map<K, V> map, final Comparator<? super V> comparator) {
        return new DefaultMapValueComparator(map, comparator);
    }

    /**
     * Returns map entry comparator instance {@link Comparator}
     *
     * @param <K> type of key entry element
     * @param <V> type of value entry element
     * @return map entry comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Comparator<? super Map.Entry<K, V>> getDefaultMapEntryComparator(final Comparator<? super Map.Entry<K, V>> comparator) {
        return new DefaultMapEntryComparator(comparator);
    }

    /**
     * Returns array comparator instance {@link Comparator}
     *
     * @param <T> type of array element
     * @return array comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T> getDefaultArrayComparator(final Comparator<? super T> comparator) {
        return new DefaultArrayComparator(comparator);
    }

    /**
     * Returns null-safe lexicographical array comparator instance {@link LexicographicalArrayComparator}
     *
     * @param <T> type of array element
     * @return null-safe lexicographical array comparator instance {@link LexicographicalArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T> getLexicographicalArrayComparator(final Comparator<? super T> comparator) {
        return new LexicographicalArrayComparator(comparator);
    }

    /**
     * Returns null-safe number {@link Number} comparator instance {@link DefaultNumberComparator}
     *
     * @return null-safe number {@link Number} comparator instance {@link DefaultNumberComparator}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> Comparator<? super T> getDefaultNumberComparator(final Comparator<? super T> comparator) {
        return new DefaultNumberComparator<T>(comparator);
    }

    /**
     * Returns null-safe lexicographical byte array comparator instance {@link LexicographicalByteArrayComparator}
     *
     * @return null-safe lexicographical byte array comparator instance {@link LexicographicalByteArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<byte[]> getDefaultByteArrayComparator() {
        return new LexicographicalByteArrayComparator();
    }

    /**
     * Returns null-safe lexicographical short array comparator instance {@link LexicographicalShortArrayComparator}
     *
     * @return null-safe lexicographical short array comparator instance {@link LexicographicalShortArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<short[]> getLexicographicalShortArrayComparator() {
        return new LexicographicalShortArrayComparator();
    }

    /**
     * Returns null-safe lexicographical int array comparator instance {@link Comparator}
     *
     * @return null-safe lexicographical int array comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<int[]> getLexicographicalIntArrayComparator() {
        return new LexicographicalIntArrayComparator();
    }

    /**
     * Returns null-safe lexicographical long array comparator instance {@link LexicographicalLongArrayComparator}
     *
     * @return null-safe lexicographical long array comparator instance {@link LexicographicalLongArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<long[]> getLexicographicalLongArrayComparator() {
        return new LexicographicalLongArrayComparator();
    }

    /**
     * Returns null-safe lexicographical double array comparator instance {@link LexicographicalDoubleArrayComparator}
     *
     * @return null-safe lexicographical double array comparator instance {@link LexicographicalDoubleArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<double[]> getLexicographicalDoubleArrayComparator() {
        return new LexicographicalDoubleArrayComparator();
    }

    /**
     * Returns null-safe lexicographical float array comparator instance {@link LexicographicalFloatArrayComparator}
     *
     * @return null-safe lexicographical float array comparator instance {@link LexicographicalFloatArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<float[]> getLexicographicalFloatArrayComparator() {
        return new LexicographicalFloatArrayComparator();
    }

    /**
     * Returns null-safe lexicographical char array comparator instance {@link LexicographicalCharacterArrayComparator}
     *
     * @return null-safe lexicographicalchar array comparator instance {@link LexicographicalCharacterArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<char[]> getLexicographicalCharacterArrayComparator() {
        return new LexicographicalCharacterArrayComparator();
    }

    /**
     * Returns null-safe lexicographical boolean array comparator instance {@link LexicographicalBooleanArrayComparator}
     *
     * @return null-safe lexicographical boolean array comparator instance {@link LexicographicalBooleanArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<boolean[]> getLexicographicalBooleanArrayComparator() {
        return new LexicographicalBooleanArrayComparator();
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

    /**
     * Default abstract null-safe comparator implementation {@link Comparator}
     *
     * @param <T> type of input element to be compared by operation
     */
    @EqualsAndHashCode
    @ToString
    public static abstract class DefaultNullSafeComparator<T> implements Comparator<T> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         * Byte.MAX_VALUE - if arguments are different (and not null either)
         *
         * @param first - initial input first argument
         * @param last  - initial input last argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
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
     * Default comparable comparator implementation {@link Comparator}
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
         * @param first - initial input first argument
         * @param last  - initial input last argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final T first, final T last) {
            return ComparatorUtils.compareTo(first, last);
        }
    }

    /**
     * Default null-safe object {@link Object} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultComparator extends DefaultNullSafeComparator<Object> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first argument {@link Object}
         * @param last  - initial input last argument {@link Object}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
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
     * Default class {@link Class} comparator implementation {@link Comparator}
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
         * @param first - initial input first argument {@link Class}
         * @param last  - initial input last argument {@link Class}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
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
     * Default localeo {@link Locale} comparator implementation {@link Comparator}
     */
    @EqualsAndHashCode
    @ToString
    public static class DefaultLocaleComparator implements Comparator<Locale> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first argument {@link Locale}
         * @param last  - initial input last argument {@link Locale}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final Locale first, final Locale last) {
            if (first.getLanguage().equals(last.getLanguage())) {
                if (first.getCountry().equals(last.getCountry())) {
                    return 0;
                }
                return 1;
            }
            return -1;
        }
    }

    /**
     * Default null-safe array comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of array element to be compared by operation
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultArrayComparator<T> extends DefaultNullSafeComparator<T[]> {

        /**
         * Custom comparator instance {@link Comparator}
         */
        private final Comparator<? super T> comparator;

        /**
         * Default value comparator
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultArrayComparator(final Comparator<? super T> comparator) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final T[] first, final T[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int firstSize = first.length;
                int lastSize = last.length;
                if (firstSize < lastSize) {
                    return -1;
                } else if (firstSize > lastSize) {
                    return 1;
                }
                for (int temp, i = 0; i < firstSize; i++) {
                    temp = Objects.compare(first[i], last[i], getComparator());
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
     * Default null-safe lexicographical array comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of array element to be compared by operation
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalArrayComparator<T> extends DefaultNullSafeComparator<T[]> {

        /**
         * Custom comparator instance {@link Comparator}
         */
        private final Comparator<? super T> comparator;

        /**
         * Default value comparator
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public LexicographicalArrayComparator(final Comparator<? super T> comparator) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final T[] first, final T[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Objects.compare(first[i], last[i], getComparator());
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical short array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalShortArrayComparator extends DefaultNullSafeComparator<short[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final short[] first, final short[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Short.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical int array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalIntArrayComparator extends DefaultNullSafeComparator<int[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final int[] first, final int[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Integer.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical long array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalLongArrayComparator extends DefaultNullSafeComparator<long[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final long[] first, final long[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Long.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical float array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalFloatArrayComparator extends DefaultNullSafeComparator<float[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final float[] first, final float[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Float.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical double array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalDoubleArrayComparator extends DefaultNullSafeComparator<double[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final double[] first, final double[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Double.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical char array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalCharacterArrayComparator extends DefaultNullSafeComparator<char[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final char[] first, final char[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Character.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical boolean array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalBooleanArrayComparator extends DefaultNullSafeComparator<boolean[]> {

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final boolean[] first, final boolean[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Boolean.compare(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return first.length - last.length;
            }
            return comp;
        }
    }

    /**
     * Default null-safe lexicographical byte array comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalByteArrayComparator extends DefaultNullSafeComparator<byte[]> {

        /**
         * Default unsigned mask
         */
        private static final int DEFAULT_UNSIGNED_MASK = 0xFF;

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument
         * @param last  - initial input last array argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final byte[] first, final byte[] last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                int minLength = Math.min(first.length, last.length);
                for (int i = 0; i < minLength; i++) {
                    int result = compareBy(first[i], last[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return 0;
            }
            return comp;
        }

        /**
         * Compares input objects by value
         *
         * @param a - initial input first type argument
         * @param b - initial input last type argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        private int compareBy(byte a, byte b) {
            return toInt(a) - toInt(b);
        }

        /**
         * Converts initial input value to integer formatted value {@link Integer}
         *
         * @param value - initial input value to be converted by {@byte}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        private static int toInt(byte value) {
            return value & DEFAULT_UNSIGNED_MASK;
        }
    }

    /**
     * Default null-safe iterable comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of input element to be compared by operation
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
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
         * @param first - initial input first argument {@link Iterable}
         * @param last  - initial input last argument {@link Iterable}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
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
     * Default null-safe big decimal {@link BigDecimal} comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultBigDecimalComparator extends DefaultNullSafeComparator<BigDecimal> {

        /**
         * Custom comparator instance {@link Comparator}
         */
        private final Comparator<? super BigDecimal> comparator;
        /**
         * Custom significant decimal places
         */
        private int significantDecimalPlaces;

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
         * @param first - initial input first argument {@link BigDecimal}
         * @param last  - initial input last argument {@link BigDecimal}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
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
     * Default map value comparator implementation {@link Comparator}
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DefaultMapValueComparator<K, V> implements Comparator<K> {

        /**
         * Custom value map instance {@link Map}
         */
        final Map<K, V> map;
        /**
         * Custom value comparator instance {@link Comparator}
         */
        private final Comparator<? super V> comparator;

        /**
         * Default map value comparator with initial map collection instance {@link Map}
         *
         * @param map - initial input map collection instance {@link Map}
         */
        public DefaultMapValueComparator(final Map<K, V> map) {
            this(map, null);
        }

        /**
         * Default value map comparator with initial map collection instance {@link Map} and value comparator {@link Comparator}
         *
         * @param map        - initial input map collection instance {@link Map}
         * @param comparator - initial input value map comparator instance {@link Comparator}
         */
        public DefaultMapValueComparator(final Map<K, V> map, final Comparator<? super V> comparator) {
            this.map = Objects.requireNonNull(map);
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first argument
         * @param last  - initial input last argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final K first, final K last) {
            return Objects.compare(getMap().get(first), getMap().get(last), getComparator());
        }
    }

    /**
     * Default map entry comparator implementation {@link Comparator}
     */
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DefaultMapEntryComparator<K, V> implements Comparator<Map.Entry<K, V>> {

        /**
         * Custom map entry comparator instance {@link Comparator}
         */
        private final Comparator<? super Map.Entry<K, V>> comparator;

        /**
         * Default map entry comparator
         */
        public DefaultMapEntryComparator() {
            this(null);
        }

        /**
         * Default map entry comparator with initial comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultMapEntryComparator(final Comparator<? super Map.Entry<K, V>> comparator) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input input first argument
         * @param last  - initial input input last argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final Map.Entry<K, V> first, final Map.Entry<K, V> last) {
            return Objects.compare(first, last, getComparator());
        }
    }

    /**
     * Default number comparator implementation {@link DefaultNullSafeComparator}
     */
    @Data
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNumberComparator<T extends Number> extends DefaultNullSafeComparator<T> {

        /**
         * Custom map entry comparator instance {@link Comparator}
         */
        private final Comparator<? super T> comparator;

        /**
         * Default number comparator
         */
        public DefaultNumberComparator() {
            this(null);
        }

        /**
         * Default number comparator with initial comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultNumberComparator(final Comparator<? super T> comparator) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
        }

        /**
         * Returns numeric result of arguments comparison:
         * "-1" - first argument is greater than the last one
         * "1" - last argument is greater than the first one
         * "0" - arguments are equal
         *
         * @param first - initial input first array argument {@code T}
         * @param last  - initial input last array argument {@code T}
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        @Override
        public int compare(final T first, final T last) {
            int comp = super.compare(first, last);
            if (comp == Byte.MAX_VALUE) {
                return Objects.compare(first, last, getComparator());
            }
            return comp;
        }
    }
}
