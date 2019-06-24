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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.test.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception.BiMatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.ComparatorBiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.test.AbstractDeliveryInfoDiffTest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.*;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.startsWith;

/**
 * Delivery info binary matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoBiMatcherTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Default date format pattern
     */
    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    /**
     * Default number format pattern
     */
    private static final String DEFAULT_NUMBER_REGEX = "-?(?:0|[1-9]\\d*)(?:\\.\\d+)?(?:[eE][+-]?\\d+)?";
    /**
     * Default {@link DeliveryInfo} gid prefix
     */
    private final String DEFAULT_GID_PREFIX = "TEST";
    /**
     * Default {@link DeliveryInfo} type
     */
    public static final int DEFAULT_TYPE = 5;

    /**
     * Default {@link DeliveryInfo} model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test non-equal delivery info entities by custom comparator")
    public void test_nonEqual_deliveryInfo_by_andMatcher() {
        // given
        final DeliveryInfo d1 = this.getDeliveryInfoMock().val();
        final DeliveryInfo d2 = this.getDeliveryInfoMock().val();
        final Comparator<? super DeliveryInfo> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator<>();

        // when
        final BiMatcher<DeliveryInfo> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test equal delivery info entities by custom comparator")
    public void test_equal_deliveryInfo_by_andMatcher() {
        // given
        final DeliveryInfo d1 = this.getDeliveryInfo();
        final DeliveryInfo d2 = this.getDeliveryInfo();
        final Comparator<? super DeliveryInfo> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator<>();

        // when
        final BiMatcher<DeliveryInfo> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different objects by custom comparator and negate priority nulls")
    public void test_objects_by_defaultComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = new String();
        final Comparator<? super Object> comparator = ComparatorUtils.getObjectComparator(DEFAULT_OBJECT_COMPARATOR, false);

        // when
        final BiMatcher<Object> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null objects by default comparator and negate priority nulls")
    public void test_nullObjects_by_defaultComparator() {
        // given
        final Object d1 = null;
        final Object d2 = null;
        final Comparator<? super Object> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator<>();

        // when
        final BiMatcher<Object> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different objects with first null by default comparator and negate priority nulls")
    public void test_objectsWithFirstNull_by_defaultComparator() {
        // given
        final Object d1 = null;
        final Object d2 = new Object();
        final Comparator<? super Object> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator<>();

        // when
        final BiMatcher<Object> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different objects with last null by default comparator and priority nulls")
    public void test_objectsWithLastNull_by_defaultComparator() {
        // given
        final Object d1 = new Object();
        final Object d2 = null;
        final Comparator<? super Object> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator<>();

        // when
        final BiMatcher<Object> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator, nullable predefined order elements and priority nulls")
    public void test_integerObjects_by_defaultEmptyLiteralComparator() {
        // given
        final Integer d1 = 3;
        final Integer d2 = 12;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>();

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator, predefined order elements and priority nulls")
    public void test_integerObjectsWithPredefinedOrderElements_by_defaultLiteralComparator() {
        // given
        final Integer[] predefinedOrder = new Integer[]{5, 6, 13, 2, 12, 4, 3};
        final Integer d1 = 3;
        final Integer d2 = 12;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>(predefinedOrder);

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator, nullable predefined order elements and priority nulls")
    public void test_integerObjectsWithNullablePredefinedOrderElements_by_defaultLiteralComparator() {
        // given
        final Integer[] predefinedOrder = new Integer[]{5, 6, 78, 2, 12, null, 4, 3};
        final Integer d1 = null;
        final Integer d2 = 12;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>(predefinedOrder);

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator, multiple nullable predefined order elements and priority nulls")
    public void test_integerObjectsWithMultipleNullablePredefinedOrderElements_by_defaultLiteralComparator() {
        // given
        final Integer[] predefinedOrder = new Integer[]{5, 6, null, 2, 12, null, 4, 3};
        final Integer d1 = null;
        final Integer d2 = 12;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>(predefinedOrder);

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator, predefined order elements and priority nulls")
    public void test_invalidIntegerObjectsWithPredefinedOrderElements_by_defaultLiteralComparator() {
        // given
        final Integer[] predefinedOrder = new Integer[]{5, 6, null, 2, 12, null, 4, 3};
        final Integer d1 = 130;
        final Integer d2 = 120;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>(predefinedOrder);

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different arrays by default literal comparator and priority nulls")
    public void test_nullableIntegerObjects_by_defaultLiteralComparator() {
        // given
        final Integer d1 = null;
        final Integer d2 = null;
        final Comparator<? super Integer> comparator = new ComparatorUtils.DefaultLiteralComparator<>();

        // when
        final BiMatcher<Integer> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and negate priority nulls")
    public void test_doubleObjects_by_defaultComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);
        final Comparator<? super Double> comparator = new ComparatorUtils.DefaultNullSafeNumberComparator<>();

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double objects by custom comparator and negate priority nulls")
    public void test_doubleObjects_by_customComparator() {
        // given
        final Double d1 = Double.valueOf(0.1233334);
        final Double d2 = Double.valueOf(0.1233335);
        final Comparator<? super Double> comparator = ComparatorUtils.getNumberComparator(DEFAULT_DOUBLE_COMPARATOR, false);

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and negate priority nulls")
    public void test_doubleObjectsWithPriorityNulls_by_defaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);
        final Comparator<? super Double> comparator = new ComparatorUtils.DefaultNullSafeNumberComparator<>();

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double objects by default comparator and priority nulls")
    public void test_doubleObjectsWithNonPriorityNulls_by_defaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = Double.valueOf(0.1233335);
        final Comparator<? super Double> comparator = new ComparatorUtils.DefaultNullSafeNumberComparator<>();

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null double objects by default comparator and priority nulls")
    public void test_nullDoubleObjects_by_defaultComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;
        final Comparator<? super Double> comparator = new ComparatorUtils.DefaultNullSafeNumberComparator<>();

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null double objects by non-safe custom comparator")
    public void test_nullDoubleObjects_by_customComparator() {
        // given
        final Double d1 = null;
        final Double d2 = null;
        final Comparator<? super Double> comparator = Comparator.comparingDouble((Double o) -> o);

        // when
        final BiMatcher<Double> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different string array objects by custom null-safe comparator and negate priority nulls")
    public void test_stringArrayObjectsWithoutNulls_by_customComparator() {
        // given
        final String[] d1 = {"a", "bb", "ccc"};
        final String[] d2 = {"dd", "nn"};
        final Comparator<? super String[]> comparator = new ComparatorUtils.DefaultNullSafeStringArrayComparator();

        // when
        final BiMatcher<String[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different string array objects with nulls by custom null-safe comparator and negate priority nulls")
    public void test_stringArrayObjectsWithNulls_by_customComparator() {
        // given
        final String[] d1 = {"a", "bb", "ccc", null};
        final String[] d2 = {"dd", "nn", null};
        final Comparator<? super String[]> comparator = new ComparatorUtils.DefaultNullSafeStringArrayComparator();

        // when
        final BiMatcher<String[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test equal double array objects by default comparator and negate priority nulls")
    public void test_equalDoubleArrayObjects_by_defaultComparator() {
        // given
        final Double[] d1 = {3.4, 6.4, 2.1, 6.2, null};
        final Double[] d2 = {3.4, 6.4, 2.1, 6.2, null};
        final Comparator<? super Double[]> comparator = new ComparatorUtils.LexicographicalNullSafeArrayComparator<>();

        // when
        final BiMatcher<Double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test equal double array objects by lexicographical default comparator and negate priority nulls")
    public void test_equalDoubleArrayObjects_by_lexicographicalDefaultComparator() {
        // given
        final Double[] d1 = {3.4, 6.4, 2.1, 6.2, null};
        final Double[] d2 = {3.4, 6.4, 2.1, 6.2, null};
        final Comparator<? super Double[]> comparator = new ComparatorUtils.LexicographicalNullSafeArrayComparator<>();

        // when
        final BiMatcher<Double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double array objects by default comparator and negate priority nulls")
    public void test_doubleArrayObjectsWithNulls_by_defaultComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};
        final Comparator<? super Double[]> comparator = new ComparatorUtils.DefaultNullSafeArrayComparator<>();

        // then
        thrown.expect(BiMatchOperationException.class);
        thrown.expectMessage(startsWith("cannot process match operation"));

        // when
        final BiMatcher<Double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different array objects by default comparator and negate priority nulls")
    public void test_equalArrayObjects_by_defaultComparator() {
        // given
        final Date dateNow = Date.from(Instant.now());
        final Object[] d1 = {3.4, "object1", 2.1, 7, dateNow};
        final Object[] d2 = {3.4, "object1", 2.1, 7, dateNow};
        final Comparator<? super Object[]> comparator = new ComparatorUtils.DefaultNullSafeArrayComparator<>();

        // when
        final BiMatcher<Object[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double array objects by custom null-safe comparator and negate priority nulls")
    public void test_doubleArrayObjectsWithNulls_by_customComparator() {
        // given
        final Double[] d1 = {3.4, null, 2.1, 6.2};
        final Double[] d2 = {3.4, 6.4, null, 6.2};
        final Comparator<? super Double[]> comparator = ComparatorUtils.<Double>getArrayComparator((o1, o2) -> {
            if (o1 == o2) return 0;
            if (Objects.isNull(o2)) return 1;
            if (Objects.isNull(o1)) return -1;
            return Double.compare(o1, o2);
        }, false);

        // when
        final BiMatcher<Double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double array objects by default comparator and negate priority nulls")
    public void test_doubleArrayObjects_by_defaultComparator() {
        // given
        final double[] d1 = {4.3, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2};
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        // when
        final BiMatcher<double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different double array objects with different length by default comparator and negate priority nulls")
    public void test_doubleArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final double[] d1 = {3.4, 6.4, 2.1, 6.2};
        final double[] d2 = {3.4, 6.4, 2.1, 6.2, 7.9};
        final Comparator<? super double[]> comparator = ComparatorUtils.getDoubleArrayComparator(false);

        // when
        final BiMatcher<double[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different float array objects by default comparator and negate priority nulls")
    public void test_floatArrayObjects_by_defaultComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f};
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        // when
        final BiMatcher<float[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different float array objects with different length by default comparator and negate priority nulls")
    public void test_floatArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final float[] d1 = {4.3f, 6.4f, 2.1f, 6.2f};
        final float[] d2 = {3.4f, 6.4f, 2.1f, 6.2f, 7.9f};
        final Comparator<? super float[]> comparator = ComparatorUtils.getFloatArrayComparator(false);

        // when
        final BiMatcher<float[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different integer array objects by default comparator and negate priority nulls")
    public void test_integerArrayObjects_by_defaultComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6};
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        // when
        final BiMatcher<int[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different integers array objects with different length by default comparator and negate priority nulls")
    public void test_intArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final int[] d1 = {4, 6, 2, 6};
        final int[] d2 = {3, 6, 2, 6, 7};
        final Comparator<? super int[]> comparator = ComparatorUtils.getIntArrayComparator(false);

        // when
        final BiMatcher<int[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different long array objects by default comparator and negate priority nulls")
    public void test_longArrayObjects_by_defaultComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444};
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        // when
        final BiMatcher<long[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different long array objects with different length by default comparator and negate priority nulls")
    public void test_longArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final long[] d1 = {4, 6, 2, 6, 7_000_444};
        final long[] d2 = {3, 6, 2, 6, 3_344_444, 6_444};
        final Comparator<? super long[]> comparator = ComparatorUtils.getLongArrayComparator(false);

        // when
        final BiMatcher<long[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different boolean array objects by default comparator and negate priority nulls")
    public void test_BoolArrayObjects_by_defaultComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true};
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        // when
        final BiMatcher<boolean[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different boolean array objects with different length by default comparator and negate priority nulls")
    public void test_boolArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final boolean[] d1 = {true, false, true, true, false, false};
        final boolean[] d2 = {true, false, false, true, false, true, false};
        final Comparator<? super boolean[]> comparator = ComparatorUtils.getBooleanArrayComparator(false);

        // when
        final BiMatcher<boolean[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different byte array objects by default comparator and negate priority nulls")
    public void test_byteArrayObjects_by_defaultComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1};
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        // when
        final BiMatcher<byte[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different byte array objects with different lengths by default comparator and negate priority nulls")
    public void test_byteArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final byte[] d1 = {4, 6, 2, 6, 35, 127};
        final byte[] d2 = {3, 6, 2, 6, 127, -1, 67};
        final Comparator<? super byte[]> comparator = ComparatorUtils.getByteArrayComparator(false);

        // when
        final BiMatcher<byte[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different short array objects by default comparator and negate priority nulls")
    public void test_shortArrayObjects_by_defaultComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799};
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        // when
        final BiMatcher<short[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different short array objects with different length by default comparator and negate priority nulls")
    public void test_shortArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final short[] d1 = {4, 6, 2, 6, 35, 127, 255, 3, 2570};
        final short[] d2 = {3, 6, 2, 6, 127, -1, -267, 3, 799, 58};
        final Comparator<? super short[]> comparator = ComparatorUtils.getShortArrayComparator(false);

        // when
        final BiMatcher<short[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different char array objects by default comparator and negate priority nulls")
    public void test_charArrayObjects_by_defaultComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8'};
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        // when
        final BiMatcher<char[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different char array objects with different length by default comparator and negate priority nulls")
    public void test_charArrayObjectsWithDifferentSize_by_defaultComparator() {
        // given
        final char[] d1 = {4, 6, 2, 6, 35, 127, 'b', 3, 'c'};
        final char[] d2 = {3, 6, 2, 6, 127, 'a', 'a', 3, '8', 'n'};
        final Comparator<? super char[]> comparator = ComparatorUtils.getCharacterArrayComparator(false);

        // when
        final BiMatcher<char[]> biMatcher = new ComparatorBiMatcher<>(comparator);

        //then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different string objects by custom natural order comparator and negate priority nulls")
    public void test_StringObjects_by_customNaturalOrderComparator() {
        // given
        final CharSequence d1 = "acde";
        final CharSequence d2 = "abcd";
        final Comparator<? super CharSequence> comparator = ComparatorUtils.getCharSequenceComparator((o1, o2) -> Objects.compare(String.valueOf(o1), String.valueOf(o2), Comparator.naturalOrder()), false);

        // when
        final BiMatcher<CharSequence> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different string objects by custom comparator and negate priority nulls")
    public void test_stringObjects_by_customComparator() {
        // given
        final String d1 = "acde";
        final String d2 = "abcd";
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultNullSafeCharSequenceComparator(DEFAULT_CHAR_SEQUENCE_COMPARATOR);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test empty string objects by default comparator and negate priority nulls")
    public void test_emptyStringObjects_by_defaultComparator() {
        // given
        final String d1 = "";
        final String d2 = "";
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultNullSafeCharSequenceComparator();

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different class objects by default comparator and negate priority nulls")
    public void test_classObjects_by_defaultComparator() {
        // given
        final Class<DeliveryInfo> d1 = DeliveryInfo.class;
        final Class<AddressInfo> d2 = AddressInfo.class;
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator();

        // when
        final BiMatcher<Class<?>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null class objects by default comparator and negate priority nulls")
    public void test_nullСlassObjects_by_defaultComparator() {
        // given
        final Class<DeliveryInfo> d1 = null;
        final Class<AddressInfo> d2 = AddressInfo.class;
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator();

        // when
        final BiMatcher<Class<?>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different class objects by custom comparator and negate priority nulls")
    public void test_differentСlassObjects_by_customComparator() {
        // given
        final Class<Date> d1 = Date.class;
        final Class<AddressInfo> d2 = AddressInfo.class;
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator(DEFAULT_CLASS_COMPARATOR.apply("java.util."));

        // when
        final BiMatcher<Class<?>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test equal class objects by custom comparator and negate priority nulls")
    public void test_equalСlassObjects_by_customComparator() {
        // given
        final Class<AddressInfo> d1 = AddressInfo.class;
        final Class<AddressInfo> d2 = AddressInfo.class;
        final Comparator<? super Class<?>> comparator = new ComparatorUtils.DefaultNullSafeClassComparator(DEFAULT_CLASS_COMPARATOR.apply("com.wildbeeslabs.sensiblemetrics.diffy."));

        // when
        final BiMatcher<Class<?>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different locale objects by default comparator and negate priority nulls")
    public void test_localeObjects_by_defaultComparator() {
        // given
        final Locale d1 = Locale.FRENCH;
        final Locale d2 = Locale.CHINESE;
        final Comparator<? super Locale> comparator = new ComparatorUtils.DefaultNullSafeLocaleComparator();

        // when
        final BiMatcher<Locale> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null locale objects by default comparator and negate priority nulls")
    public void test_nullLocaleObjects_by_defaultComparator() {
        // given
        final Locale d1 = null;
        final Locale d2 = Locale.CHINESE;
        final Comparator<? super Locale> comparator = new ComparatorUtils.DefaultNullSafeLocaleComparator();

        // when
        final BiMatcher<Locale> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different currency objects by default comparator and negate priority nulls")
    public void test_currencyObjects_by_defaultComparator() {
        // given
        final Currency d1 = Currency.getInstance(Locale.FRANCE);
        final Currency d2 = Currency.getInstance(Locale.CHINA);
        final Comparator<? super Currency> comparator = new ComparatorUtils.DefaultNullSafeCurrencyComparator();

        // when
        final BiMatcher<Currency> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null currency objects by default comparator and negate priority nulls")
    public void tes_nullCurrencyObjects_by_defaultComparator() {
        // given
        final Currency d1 = null;
        final Currency d2 = Currency.getInstance(Locale.CHINA);
        final Comparator<? super Currency> comparator = new ComparatorUtils.DefaultNullSafeCurrencyComparator();

        // when
        final BiMatcher<Currency> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different url objects by default comparator and negate priority nulls")
    public void test_urlObjects_by_defaultComparator() throws MalformedURLException {
        // given
        final URL d1 = new URL("https://mafiadoc.com/search/wifi");
        final URL d2 = new URL("https://mafiadoc.com/search/network");
        final Comparator<? super URL> comparator = new ComparatorUtils.DefaultNullSafeUrlComparator();

        // when
        final BiMatcher<URL> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null url objects by default comparator and negate priority nulls")
    public void test_nullUrlObjects_by_defaultComparator() throws MalformedURLException {
        // given
        final URL d1 = null;
        final URL d2 = new URL("https://mafiadoc.com/search/network");
        final Comparator<? super URL> comparator = new ComparatorUtils.DefaultNullSafeUrlComparator();

        // when
        final BiMatcher<URL> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different big decimal objects with zero significant places by default comparator and negate priority nulls")
    public void test_bigDecimalObjects_by_defaultComparator() {
        // given
        final BigDecimal d1 = BigDecimal.valueOf(1_999.0005);
        final BigDecimal d2 = BigDecimal.valueOf(2_000.0004);
        final Comparator<? super BigDecimal> comparator = new ComparatorUtils.DefaultNullSafeBigDecimalComparator();

        // when
        final BiMatcher<BigDecimal> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null big decimal objects with zero significant places by default comparator and negate priority nulls")
    public void test_nullBigDecimalObjects_by_defaultComparator() {
        // given
        final BigDecimal d1 = null;
        final BigDecimal d2 = BigDecimal.valueOf(2_000.0004);
        final Comparator<? super BigDecimal> comparator = new ComparatorUtils.DefaultNullSafeBigDecimalComparator();

        // when
        final BiMatcher<BigDecimal> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different map value objects by custom natural order comparator")
    public void test_valueMapObjects_by_customComparator() {
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

        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator<>(map, Comparator.naturalOrder(), false);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different map value objects by default comparator")
    public void test_valueMapObjects_by_defaultComparator() {
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

        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator<>(map);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null map values by default comparator")
    public void test_nullValueMapObjects_by_defaultComparator() {
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

        final Comparator<? super String> comparator = new ComparatorUtils.DefaultMapValueComparator<>(map);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different string objects by default positional comparator and negate priority nulls")
    public void test_stringObjects_by_defaultComparator() {
        // given
        final List<String> list = asList("saf", "fas", "sfa", "sadf");
        final String d1 = "fas";
        final String d2 = "saf";
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultListPositionComparator<>(list);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null string objects by default positional comparator and negate priority nulls")
    public void test_nullStringObjects_by_defaultComparator() {
        // given
        final List<String> list = asList("saf", "fas", "sfa", "sadf");
        final String d1 = null;
        final String d2 = "saf";
        final Comparator<? super String> comparator = new ComparatorUtils.DefaultListPositionComparator<>(list);

        // when
        final BiMatcher<String> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test map entry objects with class cast exception by default comparator")
    public void test_mapEntryObjectsWithClassCastException_by_defaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator<>(DEFAULT_COMPARATOR);

        // then
        thrown.expect(BiMatchOperationException.class);
        thrown.expectMessage(startsWith("cannot process match operation"));

        // when
        final BiMatcher<Map.Entry<String, Integer>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test map entry objects by custom comparator")
    public void test_mapEntryObjects_by_customComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator<>(DEFAULT_OBJECT_COMPARATOR);

        // when
        final BiMatcher<Map.Entry<String, Integer>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test map entry objects by default comparator")
    public void test_mapEntryObjects_by_defaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = new AbstractMap.SimpleEntry<>("aa", 56);
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator<>();

        // when
        final BiMatcher<Map.Entry<String, Integer>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test null map entry objects by default comparator")
    public void test_nullMapEntryObjects_by_defaultComparator() {
        // given
        final Map.Entry<String, Integer> d1 = null;
        final Map.Entry<String, Integer> d2 = new AbstractMap.SimpleEntry<>("ww", 1);
        final Comparator<? super Map.Entry<String, Integer>> comparator = new ComparatorUtils.DefaultMapEntryComparator<>();

        // then
        thrown.expect(BiMatchOperationException.class);
        thrown.expectMessage(startsWith("cannot process match operation"));

        // when
        final BiMatcher<Map.Entry<String, Integer>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different list objects by default comparator and negate priority nulls")
    public void test_iterableListObjects_by_defaultComparator() {
        // given
        final Iterable<String> d1 = asList("saf", "fas", "sfa", "sadf");
        final Iterable<String> d2 = asList("saf", "fas", "sfa", "sadf", "fsa");
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator<>();

        // when
        final BiMatcher<Iterable<String>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different null list objects by default comparator and negate priority nulls")
    public void test_nullListObjects_by_defaultComparator() {
        // given
        final Iterable<String> d1 = null;
        final Iterable<String> d2 = asList("saf", "fas", "sfa", "sadf", "fsa");
        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator<>();

        // when
        final BiMatcher<Iterable<String>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different set objects by default comparator and negate priority nulls")
    public void test_setObjects_by_defaultComparator() {
        // given
        final Iterable<String> d1 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfa")
            .add("sadf")
            .add("fsa")
            .build();
        final Iterable<String> d2 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfaa")
            .add("sadf")
            .build();

        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator<>();

        // when
        final BiMatcher<Iterable<String>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different set objects by custom comparator")
    public void test_setObjects_by_customComparator() {
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
            .add("saddf")
            .build();

        // when
        final BiMatcher<Set<?>> biMatcher = new ComparatorBiMatcher<>(DEFAULT_SET_COMPARATOR);

        // then
        assertFalse(biMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test different list / set objects by default comparator and negate priority nulls")
    public void test_listAndSetObjects_by_defaultComparator() {
        // given
        final Iterable<String> d1 = new ImmutableSet.Builder<String>()
            .add("saf")
            .add("fas")
            .add("sfa")
            .add("sadf")
            .add("fsa")
            .build();
        final Iterable<String> d2 = asList("saf", "fas", "sfa", "sadf", "fsa");

        final Comparator<? super Iterable<String>> comparator = new ComparatorUtils.DefaultNullSafeIterableComparator<>();

        // when
        final BiMatcher<Iterable<String>> biMatcher = new ComparatorBiMatcher<>(comparator);

        // then
        assertTrue(biMatcher.matches(d1, d2));
    }
}
