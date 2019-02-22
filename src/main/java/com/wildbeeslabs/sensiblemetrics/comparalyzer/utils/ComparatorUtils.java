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
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import javax.annotation.Nullable;
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
    public static final Comparator<? super Boolean> DEFAULT_FALSE_FIRST_COMPARATOR = (a, b) -> a == b ? 0 : b ? -1 : 1;

    /**
     * Default comparator with true first order {@link Comparator}
     */
    public static final Comparator<? super Boolean> DEFAULT_TRUE_FIRST_COMPARATOR = (a, b) -> a == b ? 0 : a ? -1 : 1;

    /**
     * Returns boolean comparator with false first order {@link Comparator}
     *
     * @return boolean comparator with false first order {@link Comparator}
     */
    public static Comparator<? super Boolean> lastIf() {
        return DEFAULT_FALSE_FIRST_COMPARATOR;
    }

    /**
     * Returns boolean comparator with true first order {@link Comparator}
     *
     * @return boolean comparator with true first order {@link Comparator}
     */
    public static Comparator<? super Boolean> firstIf() {
        return DEFAULT_TRUE_FIRST_COMPARATOR;
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
     * Returns object {@link Object} comparator instance {@link Comparator}
     *
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return object {@link Object} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super Object> getObjectComparator(@Nullable final Comparator<? super Object> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeObjectComparator(comparator, nullsInPriority);
    }

    /**
     * Returns string {@link String} comparator instance {@link Comparator}
     *
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return string {@link String} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super String> getStringComparator(@Nullable final Comparator<? super String> comparator, boolean nullsInPriority) {
        return DefaultNullSafeStringComparator
            .<String>builder()
            .comparator(comparator)
            .nullsInPriority(nullsInPriority)
            .build();
    }

    /**
     * Returns comparable {@link Comparable} comparator instance {@link Comparator}
     *
     * @param <T> type of input element to be compared by
     * @return comparable {@link Comparable} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T> getComparableComparator() {
        return new DefaultComparableComparator();
    }

    /**
     * Returns class {@link Class} comparator instance {@link Comparator}
     *
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return class {@link Class} comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super Class<?>> getClassComparator(final Comparator<? super Class<?>> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeClassComparator(comparator, nullsInPriority);
    }

    /**
     * Returns iterable {@link Iterable} comparator instance {@link Comparator}
     *
     * @param <T>             type of input element to be compared by
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return iterable comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super Iterable<T>> getIterableComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeIterableComparator(comparator, nullsInPriority);
    }

    /**
     * Returns big decimal {@link BigDecimal} comparator instance {@link Comparator}
     *
     * @param significantDecimalPlaces - initial input significant decimal places argument
     * @param comparator               - initial input comparator instance {@link Comparator}
     * @param nullsInPriority          - initial input "null" priority {@link Boolean}
     * @return big decimal comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super BigDecimal> getBigDecimalComparator(int significantDecimalPlaces, @Nullable final Comparator<? super BigDecimal> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeBigDecimalComparator(significantDecimalPlaces, comparator, nullsInPriority);
    }

    /**
     * Returns map value comparator instance {@link Comparator}
     *
     * @param <K>        type of key entry element
     * @param <V>        type of value entry element to be compared by
     * @param comparator - initial input comparator instance {@link Comparator}
     * @return value map comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Comparator<? super K> getValueMapComparator(final Map<K, V> map, @Nullable final Comparator<? super V> comparator) {
        return new DefaultMapValueComparator(map, comparator);
    }

    /**
     * Returns map entry comparator instance {@link Comparator}
     *
     * @param <K>        type of key entry element
     * @param <V>        type of value entry element
     * @param comparator - initial input comparator instance {@link Comparator}
     * @return map entry comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Comparator<? super Map.Entry<K, V>> getMapEntryComparator(@Nullable final Comparator<? super Map.Entry<K, V>> comparator) {
        return DefaultMapEntryComparator
            .<K, V>builder()
            .comparator(comparator)
            .build();
    }

    /**
     * Returns array comparator instance {@link Comparator}
     *
     * @param <T>             type of array element
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return array comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T[]> getArrayComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeArrayComparator(comparator, nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical array comparator instance {@link LexicographicalNullSafeArrayComparator}
     *
     * @param <T>             type of array element
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical array comparator instance {@link LexicographicalNullSafeArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static <T> Comparator<? super T> getLexicographicalArrayComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
        return LexicographicalNullSafeArrayComparator
            .<T>builder()
            .comparator(comparator)
            .nullsInPriority(nullsInPriority)
            .build();
    }

    /**
     * Returns null-safe number {@link Number} comparator instance {@link DefaultNullSafeNumberComparator}
     *
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe number {@link Number} comparator instance {@link DefaultNullSafeNumberComparator}
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> Comparator<? super T> getNumberComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
        return DefaultNullSafeNumberComparator
            .<T>builder()
            .comparator(comparator)
            .nullsInPriority(nullsInPriority)
            .build();
    }

    /**
     * Returns null-safe lexicographical byte array comparator instance {@link LexicographicalNullSafeByteArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical byte array comparator instance {@link LexicographicalNullSafeByteArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super byte[]> getByteArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeByteArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical short array comparator instance {@link LexicographicalNullSafeShortArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical short array comparator instance {@link LexicographicalNullSafeShortArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super short[]> getShortArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeShortArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical int array comparator instance {@link Comparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical int array comparator instance {@link Comparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super int[]> getIntArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeIntArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical long array comparator instance {@link LexicographicalNullSafeLongArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical long array comparator instance {@link LexicographicalNullSafeLongArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super long[]> getLongArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeLongArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical double array comparator instance {@link LexicographicalNullSafeDoubleArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical double array comparator instance {@link LexicographicalNullSafeDoubleArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super double[]> getDoubleArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeDoubleArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical float array comparator instance {@link LexicographicalNullSafeFloatArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical float array comparator instance {@link LexicographicalNullSafeFloatArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super float[]> getFloatArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeFloatArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical char array comparator instance {@link LexicographicalNullSafeCharacterArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographicalchar array comparator instance {@link LexicographicalNullSafeCharacterArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super char[]> getCharacterArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeCharacterArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe lexicographical boolean array comparator instance {@link LexicographicalNullSafeBooleanArrayComparator}
     *
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe lexicographical boolean array comparator instance {@link LexicographicalNullSafeBooleanArrayComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super boolean[]> getBooleanArrayComparator(boolean nullsInPriority) {
        return new LexicographicalNullSafeBooleanArrayComparator(nullsInPriority);
    }

    /**
     * Returns null-safe locale {@link Locale} comparator instance {@link DefaultNullSafeLocaleComparator}
     *
     * @param comparator      - initial input comparator instance {@link Comparator}
     * @param nullsInPriority - initial input "null" priority {@link Boolean}
     * @return null-safe locale {@link Locale} comparator instance {@link DefaultNullSafeLocaleComparator}
     */
    @SuppressWarnings("unchecked")
    public static Comparator<? super Locale> getLocaleComparator(final Comparator<? super Locale> comparator, boolean nullsInPriority) {
        return new DefaultNullSafeLocaleComparator(comparator, nullsInPriority);
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
        return compare(first, last, getComparableComparator());
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
     * Default abstract null-safe {@code T} comparator implementation {@link Comparator}
     *
     * @param <T> type of input element to be compared by operation
     */
    @Builder
    @Data
    @EqualsAndHashCode
    @ToString
    public static class DefaultNullSafeComparator<T> implements Comparator<T> {

        /**
         * Default comparator instance {@link Comparator}
         */
        private final Comparator<? super T> comparator;
        /**
         * Default "null" priority (true - if nulls are first, false - otherwise)
         */
        private final boolean nullsInPriority;

        /**
         * Default null-safe comparator constructor
         */
        public DefaultNullSafeComparator() {
            this(null);
        }

        /**
         * Default null-safe comparator constructor with initial comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultNullSafeComparator(@Nullable final Comparator<? super T> comparator) {
            this(comparator, false);
        }

        /**
         * Default null-safe comparator constructor with initial comparator instance {@link Comparator} and "null" priority {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority {@link Boolean}
         */
        public DefaultNullSafeComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
            this.comparator = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
            this.nullsInPriority = nullsInPriority;
        }

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
            if (first == last) return 0;
            if (Objects.isNull(first)) return (isNullsInPriority() ? 1 : -1);
            if (Objects.isNull(last)) return (isNullsInPriority() ? -1 : 1);
            return Objects.compare(first, last, getComparator());
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
    public static class DefaultNullSafeObjectComparator extends DefaultNullSafeComparator<Object> {

        /**
         * Default null-safe object comparator constructor
         */
        public DefaultNullSafeObjectComparator() {
            this(null, false);
        }

        /**
         * Default null-safe object comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeObjectComparator(@Nullable final Comparator<? super Object> comparator, boolean nullsInPriority) {
            super(Objects.isNull(comparator) ? Comparator.comparing(Object::toString) : comparator, nullsInPriority);
        }
    }

    /**
     * Default null-safe string {@link String} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeStringComparator extends DefaultNullSafeComparator<String> {

        /**
         * Default null-safe string comparator constructor
         */
        public DefaultNullSafeStringComparator() {
            this(null, false);
        }

        /**
         * Default null-safe string comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeStringComparator(@Nullable final Comparator<? super String> comparator, boolean nullsInPriority) {
            super(comparator, nullsInPriority);
        }
    }

    /**
     * Default null-safe class {@link Class} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeClassComparator extends DefaultNullSafeComparator<Class<?>> {

        /**
         * Default null-safe class comparator constructor
         */
        public DefaultNullSafeClassComparator() {
            this(null, false);
        }

        /**
         * Default null-safe class comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeClassComparator(@Nullable final Comparator<? super Class<?>> comparator, boolean nullsInPriority) {
            super(Objects.isNull(comparator) ? (o1, o2) -> {
                if (o1.isAssignableFrom(o2)) {
                    return 1;
                } else if (o2.isAssignableFrom(o1)) {
                    return -1;
                }
                return 0;
            } : comparator, nullsInPriority);
        }
    }

    /**
     * Default null-safe locale {@link Locale} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeLocaleComparator extends DefaultNullSafeComparator<Locale> {

        /**
         * Default null safe locale comparator constructor
         */
        public DefaultNullSafeLocaleComparator() {
            this(null, false);
        }

        /**
         * Default null-safe locale comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeLocaleComparator(@Nullable final Comparator<? super Locale> comparator, boolean nullsInPriority) {
            super(Objects.isNull(comparator) ? (o1, o2) -> {
                if (o1.getLanguage().equals(o2.getLanguage())) {
                    return (o1.getCountry().equals(o2.getCountry())) ? 0 : 1;
                }
                return -1;
            } : comparator, nullsInPriority);
        }
    }

    /**
     * Default null-safe {@code T[]} array comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of array element to be compared by operation
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeArrayComparator<T> extends DefaultNullSafeComparator<T[]> {

        /**
         * Default null-safe array comparator constructor
         */
        public DefaultNullSafeArrayComparator() {
            this(null, false);
        }

        /**
         * Default null-safe array comparator constructor with input comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultNullSafeArrayComparator(@Nullable final Comparator<? super T> comparator) {
            this(comparator, false);
        }

        /**
         * Default null-safe array comparator constructor with input comparator instance {@link Comparator} and "null" priority argument {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeArrayComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
            super((o1, o2) -> {
                int firstSize = o1.length;
                int lastSize = o2.length;
                if (firstSize < lastSize) {
                    return -1;
                } else if (firstSize > lastSize) {
                    return 1;
                }
                final Comparator<? super T> comp = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
                for (int temp, i = 0; i < firstSize; i++) {
                    temp = Objects.compare(o1[i], o2[i], comp);
                    if (0 != temp) {
                        return temp;
                    }
                }
                return 0;
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code T[]} array comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of array element to be compared by operation
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeArrayComparator<T> extends DefaultNullSafeComparator<T[]> {

        /**
         * Default null-safe lexicographical array comparator constructor
         */
        public LexicographicalNullSafeArrayComparator() {
            this(null, false);
        }

        /**
         * Default null-safe lexicographical array comparator constructor with input comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public LexicographicalNullSafeArrayComparator(@Nullable final Comparator<? super T> comparator) {
            this(comparator, false);
        }

        /**
         * Default null-safe lexicographical array comparator constructor with input comparator instance {@link Comparator} and "null" priority argument {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeArrayComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                final Comparator<? super T> comp = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
                for (int i = 0; i < minLength; i++) {
                    int result = Objects.compare(o1[i], o2[i], comp);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code short[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeShortArrayComparator extends DefaultNullSafeComparator<short[]> {

        /**
         * Default null-safe lexicographical short array comparator constructor
         */
        public LexicographicalNullSafeShortArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical short array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeShortArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Short.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code int[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeIntArrayComparator extends DefaultNullSafeComparator<int[]> {

        /**
         * Default null-safe lexicographical int array comparator constructor
         */
        public LexicographicalNullSafeIntArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical int array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeIntArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Integer.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code long[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeLongArrayComparator extends DefaultNullSafeComparator<long[]> {

        /**
         * Default null-safe lexicographical long array comparator constructor
         */
        public LexicographicalNullSafeLongArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical long array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeLongArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Long.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code float[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeFloatArrayComparator extends DefaultNullSafeComparator<float[]> {

        /**
         * Default null-safe lexicographical float array comparator constructor
         */
        public LexicographicalNullSafeFloatArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical float array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeFloatArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Float.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code double[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeDoubleArrayComparator extends DefaultNullSafeComparator<double[]> {

        /**
         * Default null-safe lexicographical double array comparator constructor
         */
        public LexicographicalNullSafeDoubleArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical double array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeDoubleArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Double.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code char[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeCharacterArrayComparator extends DefaultNullSafeComparator<char[]> {

        /**
         * Default null-safe lexicographical char array comparator constructor
         */
        public LexicographicalNullSafeCharacterArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical char array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeCharacterArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Character.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code boolean[]} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeBooleanArrayComparator extends DefaultNullSafeComparator<boolean[]> {

        /**
         * Default null-safe lexicographical boolean array comparator constructor
         */
        public LexicographicalNullSafeBooleanArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical boolean array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeBooleanArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = Boolean.compare(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return (o1.length - o2.length);
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe lexicographical {@code byte} array comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class LexicographicalNullSafeByteArrayComparator extends DefaultNullSafeComparator<byte[]> {

        /**
         * Default unsigned mask
         */
        private static final int DEFAULT_UNSIGNED_MASK = 0xFF;

        /**
         * Default null-safe lexicographical boolean array comparator constructor
         */
        public LexicographicalNullSafeByteArrayComparator() {
            this(false);
        }

        /**
         * Default null-safe lexicographical boolean array comparator constructor with input "null" priority argument {@link Boolean}
         *
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public LexicographicalNullSafeByteArrayComparator(boolean nullsInPriority) {
            super((o1, o2) -> {
                int minLength = Math.min(o1.length, o2.length);
                for (int i = 0; i < minLength; i++) {
                    int result = compareBy(o1[i], o2[i]);
                    if (0 != result) {
                        return result;
                    }
                }
                return 0;
            }, nullsInPriority);
        }

        /**
         * Compares input objects by value
         *
         * @param a - initial input first type argument
         * @param b - initial input last type argument
         * @return 0 if the arguments are identical and {@code c.compare(a, b)} otherwise.
         */
        private static int compareBy(byte a, byte b) {
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
     * Default null-safe iterable {@link Iterable} comparator implementation {@link DefaultNullSafeComparator}
     *
     * @param <T> type of input element to be compared by operation
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeIterableComparator<T> extends DefaultNullSafeComparator<Iterable<T>> {

        /**
         * Default null-safe iterable comparator constructor
         */
        public DefaultNullSafeIterableComparator() {
            this(null, false);
        }

        /**
         * Default null-safe iterable comparator constructor with input comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultNullSafeIterableComparator(@Nullable final Comparator<? super T> comparator) {
            this(comparator, false);
        }

        /**
         * Default null-safe iterable comparator constructor with input comparator instance {@link Comparator} and "null" priority argument {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeIterableComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
            super((o1, o2) -> {
                int firstSize = Iterables.size(o1);
                int lastSize = Iterables.size(o2);
                if (firstSize < lastSize) {
                    return -1;
                } else if (firstSize > lastSize) {
                    return 1;
                }
                final Iterator<T> iteratorFirst = o1.iterator();
                final Iterator<T> iteratorLast = o2.iterator();
                final Comparator<? super T> comp = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
                int temp;
                while (iteratorFirst.hasNext()) {
                    temp = Objects.compare(iteratorFirst.next(), iteratorLast.next(), comp);
                    if (0 != temp) {
                        return temp;
                    }
                }
                return 0;
            }, nullsInPriority);
        }
    }

    /**
     * Default null-safe big decimal {@link BigDecimal} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeBigDecimalComparator extends DefaultNullSafeComparator<BigDecimal> {

        /**
         * Default null-safe big decimal comparator constructor
         */
        public DefaultNullSafeBigDecimalComparator() {
            this(0, null);
        }

        /**
         * Default null-safe big decimal comparator constructor with input significant decimal places number, comparator instance {@link Comparator}
         *
         * @param significantDecimalPlaces - initial significant decimal places number
         * @param comparator               - initial input comparator instance {@link Comparator}
         */
        public DefaultNullSafeBigDecimalComparator(int significantDecimalPlaces, @Nullable final Comparator<? super BigDecimal> comparator) {
            this(significantDecimalPlaces, comparator, false);
        }

        /**
         * Default null-safe big decimal comparator constructor with input significant decimal places number, comparator instance {@link Comparator} and "null" priority argument {@link Boolean}
         *
         * @param significantDecimalPlaces - initial significant decimal places number
         * @param comparator               - initial input comparator instance {@link Comparator}
         * @param nullsInPriority          - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeBigDecimalComparator(int significantDecimalPlaces, @Nullable final Comparator<? super BigDecimal> comparator, boolean nullsInPriority) {
            super((o1, o2) -> {
                final Comparator<? super BigDecimal> comp = Objects.nonNull(comparator) ? comparator : ComparableComparator.getInstance();
                final BigDecimal firstRounded = o1.setScale(significantDecimalPlaces, BigDecimal.ROUND_HALF_UP);
                final BigDecimal lastRounded = o2.setScale(significantDecimalPlaces, BigDecimal.ROUND_HALF_UP);
                return Objects.compare(firstRounded, lastRounded, comp);
            }, nullsInPriority);
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
        private final Map<K, V> map;
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
        public DefaultMapValueComparator(final Map<K, V> map, @Nullable final Comparator<? super V> comparator) {
            this.map = Objects.requireNonNull(map);
            this.comparator = Objects.isNull(comparator) ? Comparator.comparing(Object::toString) : comparator;
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
     * Default map entry {@code Map.Entry} comparator implementation {@link Comparator}
     */
    @Builder
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
        public DefaultMapEntryComparator(@Nullable final Comparator<? super Map.Entry<K, V>> comparator) {
            this.comparator = Objects.isNull(comparator) ? Comparator.comparing(Object::toString) : comparator;
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
     * Default null-safe number {@link Number} comparator implementation {@link DefaultNullSafeComparator}
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    public static class DefaultNullSafeNumberComparator<T extends Number> extends DefaultNullSafeComparator<T> {

        /**
         * Default null-safe number comparator constructor
         */
        public DefaultNullSafeNumberComparator() {
            this(null, false);
        }

        /**
         * Default null-safe number comparator constructor with input comparator instance {@link Comparator}
         *
         * @param comparator - initial input comparator instance {@link Comparator}
         */
        public DefaultNullSafeNumberComparator(@Nullable final Comparator<? super T> comparator) {
            this(comparator, false);
        }

        /**
         * Default null-safe number comparator constructor with input comparator instance {@link Comparator} and "null" priority argument {@link Boolean}
         *
         * @param comparator      - initial input comparator instance {@link Comparator}
         * @param nullsInPriority - initial input "null" priority argument {@link Boolean}
         */
        public DefaultNullSafeNumberComparator(@Nullable final Comparator<? super T> comparator, boolean nullsInPriority) {
            super(comparator, nullsInPriority);
        }
    }
}
