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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.AddressInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.sort.SortManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.OrderingComparison.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
     * Default {@link String} comparator instance {@link Comparator}
     */
    public static final Comparator<CharSequence> DEFAULT_STRING_COMPARATOR = (o1, o2) -> {
        int minLength = Math.min(o1.length(), o2.length());
        for (int i = 0; i < minLength; i += 2) {
            int result = Character.compare(o1.charAt(i), o2.charAt(i));
            if (0 != result) return result;
        }
        return 0;
    };

    /**
     * Default functional {@link Class} comparator instance {@link Comparator}
     */
    public static final Function<String, Comparator<Class<?>>> DEFAULT_CLASS_COMPARATOR = (className) -> (o1, o2) -> {
        final String n1 = o1.getName();
        final String n2 = o2.getName();
        boolean tika1 = n1.startsWith(className);
        boolean tika2 = n2.startsWith(className);
        if (tika1 == tika2) {
            return n1.compareTo(n2);
        }
        return tika1 ? -1 : 1;
    };

    /**
     * Default {@link Object} comparator instance {@link Comparator}
     */
    public static final Comparator<Object> DEFAULT_OBJECT_COMPARATOR = (o1, o2) -> Objects.compare(o1.getClass().toString(), o2.getClass().toString(), Comparator.naturalOrder());

    /**
     * Default {@link Double} comparator instance {@link Comparator}
     */
    public static final Comparator<Double> DEFAULT_DOUBLE_COMPARATOR = (o1, o2) -> {
        if (Math.abs(o1 - o2) < 1e-4) {
            return 0;
        }
        return (o1 < o2) ? -1 : 1;
    };

    @Test
    @DisplayName("Test different objects by custom comparator and not priority nulls")
    public void testObjectsByDefaultComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = new String();

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(DEFAULT_OBJECT_COMPARATOR, false);

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    @DisplayName("Test null objects by default comparator and not priority nulls")
    public void testNullObjectsByDefaultComparator() {
        // given
        final Object d1 = null;
        final Object d2 = null;

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test different objects with first null by default comparator and not priority nulls")
    public void testObjectsWithFirstNullByDefaultComparator() {
        // given
        final Object d1 = null;
        final Object d2 = new Object();

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different objects with last null by default comparator and priority nulls")
    public void testObjectsWithLastNullByDefaultComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = null;

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and not priority nulls")
    public void testDoubleObjectsByDefaultComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different double objects by custom comparator and not priority nulls")
    public void testDoubleObjectsByCustomComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(DEFAULT_DOUBLE_COMPARATOR, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and not priority nulls")
    public void testDoubleObjectsWithPriorityNullsByDefaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and priority nulls")
    public void testDoubleObjectsWithNonPriorityNullsByDefaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test null double objects by default comparator and priority nulls")
    public void testNullDoubleObjectsByDefaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test null double objects by non-safe custom comparator")
    public void testNullDoubleObjectsByCustomComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;

        // when
        final Comparator<? super Double> comparator = Comparator.comparingDouble((Double o) -> o);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test sort double array by default comparator and not priority nulls")
    public void testDoubleArrayByDefaultComparator() {
        // given
        int size = 100;
        final Double[] doubles = generateDoubles(size, 1000.0, 2000.0).val();

        // when
        Arrays.sort(doubles, ComparatorUtils.getNumberComparator(null, false));

        // then
        assertEquals(doubles.length, size);
        assertTrue(isSorted(doubles, SortManager.SortDirection.ASC));
    }

    @Test
    @DisplayName("Test sort integer array objects by default comparator and not priority nulls")
    public void testSortIntegerArrayByDefaultComparator() {
        // given
        int size = 100;
        final Integer[] ints = generateInts(size, 100, 200).val();

        // when
        Arrays.sort(ints, ComparatorUtils.getNumberComparator(null, false));

        // then
        assertEquals(ints.length, size);
        assertThat(Lists.newArrayList(ints), isInAscendingOrdering());
    }

    @Test
    @DisplayName("Test sort double array by custom comparator and not priority nulls")
    public void testSortDoubleArrayByCustomComparator() {
        // given
        int size = 100;
        final Double[] doubles = generateDoubles(size, 1000.0, 2000.0).val();

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(DEFAULT_DOUBLE_COMPARATOR, false);
        Arrays.sort(doubles, comparator);

        //then
        assertEquals(doubles.length, size);
        assertTrue(isSorted(doubles, SortManager.SortDirection.ASC));
    }

    @Test
    @DisplayName("Test equal double array objects by default comparator and not priority nulls")
    public void testEqualDoubleArrayObjectsByDefaultComparator() {
        // given
        final Double[] d1 = {3.4, 6.4, 2.1, 6.2, null};
        final Double[] d2 = {3.4, 6.4, 2.1, 6.2, null};

        // when
        final Comparator<? super Double[]> comparator = ComparatorUtils.<Double>getArrayComparator(null, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test equal double array objects by lexicographical default comparator and not priority nulls")
    public void testEqualDoubleArrayObjectsByLexicographicalDefaultComparator() {
        // given
        final Double[] d1 = {3.4, 6.4, 2.1, 6.2, null};
        final Double[] d2 = {3.4, 6.4, 2.1, 6.2, null};

        // when
        final Comparator<? super Double[]> comparator = ComparatorUtils.<Double>getLexicographicalArrayComparator(null, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test different double array objects by default comparator and not priority nulls")
    public void testDoubleArrayObjectsWithNullsByDefaultComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};

        // when
        final Comparator<? super Double[]> comparator = ComparatorUtils.<Double>getArrayComparator(null, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test different double array objects by custom null-safe comparator and not priority nulls")
    public void testDoubleArrayObjectsWithNullsByCustomComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};

        // when
        final Comparator<? super Double[]> comparator = ComparatorUtils.<Double>getArrayComparator((o1, o2) -> {
            if (o1 == o2) return 0;
            if (Objects.isNull(o2)) return 1;
            if (Objects.isNull(o1)) return -1;
            return Double.compare(o1, o2);
        }, false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different double array objects by default comparator and not priority nulls")
    public void testDoubleArrayObjectsByDefaultComparator() {
        // given
        final double[] d1 = {4.3, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2};

        // when
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different double array objects with different length by default comparator and not priority nulls")
    public void testDoubleArrayObjectsWithDifferentSizeAndDefaultComparator() {
        // given
        final double[] d1 = {3.4, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2, 7.9};

        // when
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different float array objects by default comparator and not priority nulls")
    public void testFloatArrayObjectsByDefaultComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f};

        // when
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different float array objects with different length by default comparator and not priority nulls")
    public void testFloatArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f, 7.9f};

        // when
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different integer array objects by default comparator and not priority nulls")
    public void testIntegerArrayObjectsByDefaultComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6};

        // when
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different integers array objects with different length by default comparator and not priority nulls")
    public void testIntArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6, 7};

        // when
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different long array objects by default comparator and not priority nulls")
    public void testLongArrayObjectsByDefaultComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444};

        // when
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different long array objects with different length by default comparator and not priority nulls")
    public void testLongArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444, 6_444};

        // when
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different boolean array objects by default comparator and not priority nulls")
    public void testBoolArrayObjectsByDefaultComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true};

        // when
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different boolean array objects with different length by default comparator and not priority nulls")
    public void testBoolArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true, false};

        // when
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different byte array objects by default comparator and not priority nulls")
    public void testByteArrayObjectsByDefaultComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1};

        // when
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different byte array objects with different lengths by default comparator and not priority nulls")
    public void testByteArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1, 67};

        // when
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different short array objects by default comparator and not priority nulls")
    public void testShortArrayObjectsByDefaultComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799};

        // when
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different short array objects with different length by default comparator and not priority nulls")
    public void testShortArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799, 58};

        // when
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different char array objects by default comparator and not priority nulls")
    public void testCharArrayObjectsByDefaultComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8'};

        // when
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different char array objects with different length by default comparator and not priority nulls")
    public void testCharArrayObjectsWithDifferentSizeByDefaultComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8', 'n'};

        // when
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different string objects by custom natural order comparator and not priority nulls")
    public void testStringObjectsByCustomNaturalOrderComparator() {
        // given
        final String d1 = "acde";
        final String d2 = "abcd";

        // when
        final Comparator<? super CharSequence> comparator = ComparatorUtils.getCharSequenceComparator((o1, o2) -> Objects.compare(String.valueOf(o1), String.valueOf(o2), Comparator.naturalOrder()), false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different string objects by custom comparator and not priority nulls")
    public void testStringObjectsByCustomComparator() {
        // given
        final String d1 = "acde";
        final String d2 = "abcd";

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultNullSafeCharSequenceComparator(DEFAULT_STRING_COMPARATOR);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test empty string objects by default comparator and not priority nulls")
    public void testEmptyStringObjectsByDefaultComparator() {
        // given
        final String d1 = "";
        final String d2 = "";

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultNullSafeCharSequenceComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test different class objects by default comparator and not priority nulls")
    public void testСlassObjectsByDefaultComparator() {
        // given
        final Class<DeliveryInfo> d1 = DeliveryInfo.class;
        final Class<AddressInfo> d2 = AddressInfo.class;

        // when
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test null class objects by default comparator and not priority nulls")
    public void testNullСlassObjectsByDefaultComparator() {
        // given
        final Class<DeliveryInfo> d1 = null;
        final Class<AddressInfo> d2 = AddressInfo.class;

        // when
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different class objects by custom comparator and not priority nulls")
    public void testDifferentСlassObjectsByCustomComparator() {
        // given
        final Class<Date> d1 = Date.class;
        final Class<AddressInfo> d2 = AddressInfo.class;

        // when
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator(DEFAULT_CLASS_COMPARATOR.apply("java.util."));

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test equal class objects by custom comparator and not priority nulls")
    public void testEqualСlassObjectsByCustomComparator() {
        // given
        final Class<AddressInfo> d1 = AddressInfo.class;
        final Class<AddressInfo> d2 = AddressInfo.class;

        // when
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator(DEFAULT_CLASS_COMPARATOR.apply("com.wildbeeslabs.sensiblemetrics.comparalyzer."));

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test string list objects by default comparator and not priority nulls")
    public void testStringListObjectsByDefaultComparator() {
        // given
        int size = 1000;
        final List<String> strings = generateStrings(size).val();

        // when
        Collections.sort(strings, new ComparatorUtils.DefaultNullSafeCharSequenceComparator());

        //then
        assertThat(strings, hasSize(size));
        assertTrue(Ordering.natural().isOrdered(strings));
    }

    @Test
    @DisplayName("Test integer list objects by custom comparator")
    public void testIntegerListObjectsByCustomComparator() {
        // given
        int size = 1000;
        List<Integer> ints = generateInts(size, 1000).val();

        // when
        Collections.sort(ints, ComparableComparator.getInstance());

        //then
        assertThat(ints, hasSize(size));
        assertTrue(Ordering.natural().isOrdered(ints));

        // given
        size = 100;
        ints = generateInts(size, 2000).val();

        // when
        Collections.sort(ints, Comparator.naturalOrder());

        //then
        assertThat(ints, hasSize(size));
        assertTrue(Ordering.natural().isOrdered(ints));
    }

    @Test
    @DisplayName("Test different locale objects by default comparator and not priority nulls")
    public void testLocaleObjectsByDefaultComparator() {
        // given
        final Locale d1 = Locale.FRENCH;
        final Locale d2 = Locale.CHINESE;

        // when
        final Comparator<? super Locale> comparator = new ComparatorUtils.DefaultNullSafeLocaleComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different null locale objects by default comparator and not priority nulls")
    public void testNullLocaleObjectsByDefaultComparator() {
        // given
        final Locale d1 = null;
        final Locale d2 = Locale.CHINESE;

        // when
        final Comparator<? super Locale> comparator = new ComparatorUtils.DefaultNullSafeLocaleComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different currency objects by default comparator and not priority nulls")
    public void testCurrencyObjectsByDefaultComparator() {
        // given
        final Currency d1 = Currency.getInstance(Locale.FRANCE);
        final Currency d2 = Currency.getInstance(Locale.CHINA);

        // when
        final Comparator<? super Currency> comparator = new ComparatorUtils.DefaultNullSafeCurrencyComparator();

        // then
        assertThat(comparator.compare(d1, d2), greaterThan(0));
    }

    @Test
    @DisplayName("Test different null currency objects by default comparator and not priority nulls")
    public void testNullCurrencyObjectsByDefaultComparator() {
        // given
        final Currency d1 = null;
        final Currency d2 = Currency.getInstance(Locale.CHINA);

        // when
        final Comparator<? super Currency> comparator = new ComparatorUtils.DefaultNullSafeCurrencyComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different url objects by default comparator and not priority nulls")
    public void testUrlObjectsByDefaultComparator() throws MalformedURLException {
        // given
        final URL d1 = new URL("https://mafiadoc.com/search/wifi");
        final URL d2 = new URL("https://mafiadoc.com/search/network");

        // when
        final Comparator<? super URL> comparator = new ComparatorUtils.DefaultNullSafeUrlComparator();

        // then
        assertThat(comparator.compare(d1, d2), greaterThan(1));
    }

    @Test
    @DisplayName("Test different null url objects by default comparator and not priority nulls")
    public void testNullUrlObjectsByDefaultComparator() throws MalformedURLException {
        // given
        final URL d1 = null;
        final URL d2 = new URL("https://mafiadoc.com/search/network");

        // when
        final Comparator<? super URL> comparator = new ComparatorUtils.DefaultNullSafeUrlComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different big decimal objects with zero significant places by default comparator and not priority nulls")
    public void testBigDecimalObjectsByDefaultComparator() {
        // given
        final BigDecimal d1 = BigDecimal.valueOf(1_999.0005);
        final BigDecimal d2 = BigDecimal.valueOf(2_000.0004);

        // when
        final Comparator<? super BigDecimal> comparator = new ComparatorUtils.DefaultNullSafeBigDecimalComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different null big decimal objects with zero significant places by default comparator and not priority nulls")
    public void testNullBigDecimalObjectsByDefaultComparator() {
        // given
        final BigDecimal d1 = null;
        final BigDecimal d2 = BigDecimal.valueOf(2_000.0004);

        // when
        final Comparator<? super BigDecimal> comparator = new ComparatorUtils.DefaultNullSafeBigDecimalComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different map value objects by custom natural order comparator")
    public void testValueMapObjectsByCustomComparator() {
        // given
        final String d1 = "ww";
        final String d2 = "aa";

        final Map<String, Integer> map = new ImmutableMap.Builder<String, Integer>()
            .put("aa", 1)
            .put("bb", 2)
            .put("cc", 33)
            .put("dd", 7)
            .put("ee", 78)
            .put("yy", 9)
            .put("ii", 90)
            .put("ww", 56)
            .build();

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator(map, Comparator.naturalOrder(), false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different map value objects by default comparator")
    public void testValueMapObjectsByDefaultComparator() {
        // given
        final String d1 = "ww";
        final String d2 = "aa";

        final Map<String, Integer> map = new ImmutableMap.Builder<String, Integer>()
            .put("aa", 1)
            .put("bb", 2)
            .put("cc", 33)
            .put("dd", 7)
            .put("ee", 78)
            .put("yy", 9)
            .put("ii", 90)
            .put("ww", 56)
            .build();

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator(map);

        // then
        assertThat(comparator.compare(d1, d2), greaterThan(0));
    }

    @Test
    @DisplayName("Test null map values by default comparator")
    public void testNullValueMapObjectsByDefaultComparator() {
        // given
        final String d1 = "w";
        final String d2 = "aa";

        final Map<String, Integer> map = new ImmutableMap.Builder<String, Integer>()
            .put("aa", 1)
            .put("bb", 2)
            .put("cc", 33)
            .put("dd", 7)
            .put("ee", 78)
            .put("yy", 9)
            .put("ii", 90)
            .put("ww", 56)
            .build();

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator(map);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different string objects by default positional comparator and not priority nulls")
    public void testStringObjectsByDefaultComparator() {
        // given
        final List<String> list = Arrays.asList("saf", "fas", "sfa", "sadf");
        final String d1 = "fas";
        final String d2 = "saf";

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultListPositionComparator<>(list);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different null string objects by default positional comparator and not priority nulls")
    public void testNullStringObjectsByDefaultComparator() {
        // given
        final List<String> list = Arrays.asList("saf", "fas", "sfa", "sadf");
        final String d1 = null;
        final String d2 = "saf";

        // when
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultListPositionComparator<>(list);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test(expected = ClassCastException.class)
    @DisplayName("Test map entry objects with class cast exception by default comparator")
    public void testMapEntryObjectsWithClassCastExceptionByDefaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator(ComparableComparator.getInstance());

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    @DisplayName("Test map entry objects by custom comparator")
    public void testMapEntryObjectsByCustomComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator(DEFAULT_OBJECT_COMPARATOR);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    @DisplayName("Test map entry objects by default comparator")
    public void testMapEntryObjectsByDefaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator();

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test null map entry objects by default comparator")
    public void testNullMapEntryObjectsByDefaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = null;
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator();

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    @DisplayName("Test different list objects by default comparator and not priority nulls")
    public void testIterableListObjectsByDefaultComparator() {
        // given
        final List<String> d1 = Arrays.asList("saf", "fas", "sfa", "sadf");
        final List<String> d2 = Arrays.asList("saf", "fas", "sfa", "sadf", "fsa");

        // when
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different null list objects by default comparator and not priority nulls")
    public void testNullListObjectsByDefaultComparator() {
        // given
        final List<String> d1 = null;
        final List<String> d2 = Arrays.asList("saf", "fas", "sfa", "sadf", "fsa");

        // when
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    @DisplayName("Test different set objects by default comparator and not priority nulls")
    public void testSetObjectsByDefaultComparator() {
        // given
        final Set<String> d1 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfa")
            .add("sadf")
            .add("fsa")
            .build();
        final Set<String> d2 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfaa")
            .add("sadf")
            .build();

        // when
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    @DisplayName("Test different list / set objects by default comparator and not priority nulls")
    public void testListAndSetObjectsByDefaultComparator() {
        // given
        final Set<String> d1 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfa")
            .add("sadf")
            .add("fsa")
            .build();
        final List<String> d2 = Arrays.asList("saf", "fas", "sfa", "sadf", "fsa");

        // when
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator();

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    /**
     * Return matcher {@link Matcher} by collection of integers {@link List} in descending order
     *
     * @return matcher {@link Matcher}
     */
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

    /**
     * Return matcher {@link Matcher} by collection of integers {@link List} in descending order
     *
     * @return matcher {@link Matcher}
     */
    protected Matcher<? super List<Integer>> isInDescendingOrdering() {
        return new TypeSafeMatcher<List<Integer>>() {
            @Override
            public void describeTo(final Description description) {
                description.appendText("collection should be sorted in descending order");
            }

            @Override
            protected boolean matchesSafely(final List<Integer> item) {
                for (int i = 0; i < item.size() - 1; i++) {
                    if (item.get(i) < item.get(i + 1)) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    /**
     * Returns binary flag to assert input array of doubles {@link Double} to be sorted by order {@link SortDirection}
     *
     * @param array - initial input array of doubles {@link Double}
     * @param order - initial input sort order {@link SortDirection}
     * @return binary flag to assert input array of doubles to be sorted by order {@link SortDirection}
     */
    protected static boolean isSorted(final Double[] array, final SortManager.SortDirection order) {
        for (int i = 0; i < array.length - 1; i++) {
            if (Objects.equals(Double.compare(array[i], array[i + 1]), order)) {
                return false;
            }
        }
        return true;
    }
}
