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
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.AddressInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.mockneat.abstraction.MockUnitInt;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static net.andreinc.mockneat.types.enums.StringFormatType.LOWER_CASE;
import static net.andreinc.mockneat.types.enums.StringFormatType.UPPER_CASE;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.number.OrderingComparison.greaterThan;
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
     * Default String comparator instance {@link Comparator}
     */
    public static final Comparator<String> DEFAULT_STRING_COMPARATOR = (o1, o2) -> {
        int minLength = Math.min(o1.length(), o2.length());
        for (int i = 0; i < minLength; i += 2) {
            int result = Character.compare(o1.charAt(i), o2.charAt(i));
            if (0 != result) {
                return result;
            }
        }
        return 0;
    };

    /**
     * Default Object comparator instance {@link Comparator}
     */
    public static final Comparator<Object> DEFAULT_OBJECT_COMPARATOR = (o1, o2) -> Objects.compare(o1.getClass().toString(), o2.getClass().toString(), Comparator.naturalOrder());

    /**
     * Default Double comparator instance {@link Comparator}
     */
    public static final Comparator<Double> DEFAULT_DOUBLE_COMPARATOR = (o1, o2) -> {
        if (Math.abs(o1 - o2) < 1e-4) {
            return 0;
        }
        return (o1 < o2) ? -1 : 1;
    };

    @Test
    public void testObjectWithEmptyDefaultNumberComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = new String();

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(DEFAULT_OBJECT_COMPARATOR, false);

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    public void testObjectWithBothNullsAndEmptyDefaultNumberComparator() {
        // given
        final Object d1 = null;
        final Object d2 = null;

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testObjectWithFirstNullsAndEmptyDefaultNumberComparator() {
        // given
        final Object d1 = null;
        final Object d2 = new Object();

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testObjectWithLastNullsAndEmptyDefaultNumberComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = null;

        // when
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(null, true);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testDoublesWithEmptyDefaultNumberComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testDoublesWithNonEmptyDefaultNumberComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);

        // when
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(DEFAULT_DOUBLE_COMPARATOR, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
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
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(DEFAULT_DOUBLE_COMPARATOR, false);
        Arrays.sort(doubles, comparator);

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
    public void testFloatArraysWithDefaultNumberComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f};

        // when
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testFloatArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f, 7.9f};

        // when
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testIntArraysWithDefaultNumberComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6};

        // when
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testIntArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6, 7};

        // when
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testLongArraysWithDefaultNumberComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444};

        // when
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testLongArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444, 6_444};

        // when
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testBoolArraysWithDefaultNumberComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true};

        // when
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testBoolArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true, false};

        // when
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testByteArraysWithDefaultNumberComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1};

        // when
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testByteArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1, 67};

        // when
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testShortArraysWithDefaultNumberComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799};

        // when
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testShortArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799, 58};

        // when
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testCharArraysWithDefaultNumberComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8'};

        // when
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testCharArraysWithDifferentSizesAndDefaultNumberComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8', 'n'};

        // when
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        //then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
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
    public void testStringsWithCustomDefaultNumberComparator() {
        // given
        final String d1 = "acde";
        final String d2 = "abcd";

        // when
        final Comparator<? super String> comparator = ComparatorUtils.getStringComparator(DEFAULT_STRING_COMPARATOR, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testEmptyStringsWithDefaultNumberComparator() {
        // given
        final String d1 = "";
        final String d2 = "";

        // when
        final Comparator<? super String> comparator = ComparatorUtils.getStringComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testСlassesWithDefaultNumberComparator() {
        // given
        final Class<DeliveryInfo> d1 = DeliveryInfo.class;
        final Class<AddressInfo> d2 = AddressInfo.class;

        // when
        final Comparator<? super Class<?>> comparator = ComparatorUtils.getClassComparator(null, false);

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

    @Test
    public void testLocalesWithDefaultNumberComparator() {
        // given
        final Locale d1 = Locale.FRENCH;
        final Locale d2 = Locale.CHINESE;

        // when
        final Comparator<? super Locale> comparator = ComparatorUtils.getLocaleComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testBidDecimalsWithDefaultNumberComparator() {
        // given
        final BigDecimal d1 = BigDecimal.valueOf(1_999.0005);
        final BigDecimal d2 = BigDecimal.valueOf(2_000.0004);

        // when
        final Comparator<? super BigDecimal> comparator = ComparatorUtils.getBigDecimalComparator(0, null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
    }

    @Test
    public void testValueMapWithCustomComparator() {
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
        final Comparator<? super String> comparator = ComparatorUtils.getValueMapComparator(map, Comparator.naturalOrder());

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(1));
    }

    @Test
    public void testValueMapWithDefaultNumberComparator() {
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
        final Comparator<? super String> comparator = ComparatorUtils.getValueMapComparator(map, null);

        // then
        assertThat(comparator.compare(d1, d2), greaterThan(0));
    }

    @Test(expected = NullPointerException.class)
    public void testNullValueMapWithDefaultNumberComparator() {
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
        final Comparator<? super String> comparator = ComparatorUtils.getValueMapComparator(map, null);

        // then
        assertThat(comparator.compare(d1, d2), greaterThan(0));
    }

    @Test(expected = ClassCastException.class)
    public void testMapEntryClassCastExceptionWithDefaultNumberComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = ComparatorUtils.getMapEntryComparator(ComparableComparator.getInstance());

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    public void testMapEntryWithCustomComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = ComparatorUtils.getMapEntryComparator(DEFAULT_OBJECT_COMPARATOR);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(0));
    }

    @Test
    public void testMapEntryWithDefaultNumberComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);

        // when
        final Comparator<? super Map.Entry<String, Integer>> comparator = ComparatorUtils.getMapEntryComparator(null);

        // then
        assertThat(comparator.compare(d1, d2), lessThan(0));
    }

    @Test
    public void testIterablesWithDefaultNumberComparator() {
        // given
        final List<String> d1 = Arrays.asList("saf", "fas", "sfa", "sadf");
        final List<String> d2 = Arrays.asList("saf", "fas", "sfa", "sadf", "fsa");

        // when
        final Comparator<? super Iterable<String>> comparator = ComparatorUtils.getIterableComparator(null, false);

        // then
        assertThat(comparator.compare(d1, d2), IsEqual.equalTo(-1));
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
