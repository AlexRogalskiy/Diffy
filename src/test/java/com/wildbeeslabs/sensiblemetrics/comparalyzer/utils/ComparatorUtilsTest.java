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

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDiffTest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.abstraction.MockUnitInt;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static net.andreinc.mockneat.types.enums.StringFormatType.LOWER_CASE;
import static net.andreinc.mockneat.types.enums.StringFormatType.UPPER_CASE;
import static org.junit.Assert.assertThat;

/**
 * Сomparator utils unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ComparatorUtilsTest extends AbstractDiffTest {

    /**
     * Default double comparator instance {@link Comparator}
     */
    public static final Comparator<? super String> DEFAULT_STRING_COMPARATOR = ComparatorUtils.getStringComparator((o1, o2) -> {
        int minLength = Math.min(o1.length(), o2.length());
        for (int i = 0; i < minLength; i += 2) {
            int result = Character.compare(o1.charAt(i), o2.charAt(i));
            if (0 != result) {
                return result;
            }
        }
        return 0;
    }, false);

    /**
     * Default double comparator instance {@link Comparator}
     */
    public static final Comparator<? super Double> DEFAULT_DOUBLE_COMPARATOR = ComparatorUtils.getNumberComparator((o1, o2) -> {
        if (Math.abs(o1 - o2) < 1e-4) {
            return 0;
        }
        return (o1 < o2) ? -1 : 1;
    }, false);

    @Test
    public void testDoublesWithEmptyDefaultNumberComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
        //assertThat(orderedList, isInDescendingOrdering());
    }

    @Test
    public void testDoublesWithNonEmptyDefaultNumberComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // then
        assertThat(DEFAULT_DOUBLE_COMPARATOR.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testDoubleWithPriorityNullsAndEmptyDefaultNumberComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testDoubleWithNonPriorityNullsAndEmptyDefaultNumberComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testDoublesWithNullsAndEmptyDefaultNumberComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test(expected = NullPointerException.class)
    public void testDoublesWithNullsAndNonEmptyDefaultNumberComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;

        // when
        final Comparator<? super Double> comparator = (Comparator<Double>) (o1, o2) -> Double.compare(o1, o2);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testDoubleArrayWithEmptyDefaultNumberComparator() {
        // given
        final Double[] doubles = generateDoubles(100, 1000.0, 2000.0);

        // when
        Arrays.sort(doubles, ComparatorUtils.getNumberComparator(null, false));

        // then
        assertTrue(isSorted(doubles));
        //assertThat(Lists.newArrayList(doubles), isInDescendingOrdering());
    }

    @Test
    public void testIntegerArrayWithEmptyDefaultNumberComparator() {
        // given
        final Integer[] ints = generateInts(100, 100, 200);

        // when
        Arrays.sort(ints, ComparatorUtils.getNumberComparator(null, false));

        // then
        assertThat(Lists.newArrayList(ints), isInAscendingOrdering());
    }

    @Test
    public void testDoubleArrayWithDefaultNumberComparator() {
        // given
        final Double[] doubles = generateDoubles(100, 1000.0, 2000.0);

        // when
        Arrays.sort(doubles, DEFAULT_DOUBLE_COMPARATOR);

        //then
        assertTrue(isSorted(doubles));
    }

    @Test
    public void testEqualDoubleArraysWithDefaultNumberComparator() {
        // given
        final Double[] d1 = {3.4, 6.4, 2.1, 6.2, null};
        final Double[] d2 = {3.4, 6.4, 2.1, 6.2, null};

        // when
        final Comparator<? super Double[]> comparator = (Comparator<? super Double[]>) ComparatorUtils.getArrayComparator(null, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test(expected = NullPointerException.class)
    public void testEqualDoubleArraysWithNullsAndDefaultNumberComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};

        // when
        final Comparator<? super Double[]> comparator = (Comparator<? super Double[]>) ComparatorUtils.getArrayComparator(null, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testEqualDoubleArraysWithNullsAndNonDefaultNumberComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};

        // when
        final Comparator<? super Double[]> comparator = (Comparator<? super Double[]>) ComparatorUtils.getArrayComparator((Comparator<Double>) (o1, o2) -> {
            if (o1 == o2) {
                return 0;
            }
            if (Objects.isNull(o2)) {
                return 1;
            }
            if (Objects.isNull(o1)) {
                return -1;
            }
            return Double.compare(o1, o2);
        }, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testDoubleArraysWithDefaultNumberComparator() {
        // given
        final double[] d1 = {4.3, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2};

        // when
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testDoubleArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final double[] d1 = {3.4, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2, 7.9};

        // when
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testStringsWithNonEmptyDefaultNumberComparator() {
        // given
        final String d1 = "acde";
        final String d2 = "abcd";

        // when
        final Comparator<? super String> comparator = ComparatorUtils.getStringComparator(Comparator.naturalOrder(), false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testEmptyStringsWithDefaultNumberComparator() {
        // given
        final String d1 = "";
        final String d2 = "";

        // when
        final Comparator<? super String> comparator = ComparatorUtils.getStringComparator(Comparator.naturalOrder(), false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testStringsWithDefaultNumberComparator() {
        // given
        final List<String> strings = generateStrings(1000);

        // when
        Collections.sort(strings, ComparatorUtils.getStringComparator(null, false));

        //then
        assertTrue(Ordering.natural().isOrdered(strings));
    }

    protected List<String> generateStrings(int size) {
        final MockUnitInt num = mock.probabilites(Integer.class)
            .add(0.3, mock.ints().range(0, 10))
            .add(0.7, mock.ints().range(10, 20))
            .mapToInt(Integer::intValue);

        final List<String> strings = mock.fmt("#{first} #{last} #{num}")
            .param("first", mock.names().first().format(LOWER_CASE))
            .param("last", mock.names().last().format(UPPER_CASE))
            .param("num", num)
            .list(size)
            .val();
        return strings;
    }

    protected Double[] generateDoubles(int size, double lowerBound, double upperBound) {
        return this.mock.doubles()
            .range(lowerBound, upperBound)
            .array(size)
            .val();
    }

    protected Integer[] generateInts(int size, int lowerBound, int upperBound) {
        return this.mock.ints()
            .range(lowerBound, upperBound)
            .array(size)
            .val();
    }

    protected Matcher<? super List<Integer>> isInAscendingOrdering() {
        return new TypeSafeMatcher<List<Integer>>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("collection should be sorted in ascending order");
            }

            @Override
            protected boolean matchesSafely(final List<Integer> item) {
                for (int i = 0; i < item.size() - 1; i++) {
                    if (item.get(i) > item.get(i + 1)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    protected static boolean isSorted(final Double[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }
}
