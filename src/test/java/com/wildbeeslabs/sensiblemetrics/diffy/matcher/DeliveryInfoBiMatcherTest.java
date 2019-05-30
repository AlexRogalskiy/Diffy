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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher;

import com.wildbeeslabs.sensiblemetrics.diffy.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.DefaultBiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Comparator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Delivery info binary matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoBiMatcherTest extends AbstractDeliveryInfoDiffTest {

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
        final DeliveryInfo d1 = getDeliveryInfoMock().val();
        final DeliveryInfo d2 = getDeliveryInfoMock().val();

        // when
        final Comparator<? super DeliveryInfo> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator();

        // when
        final BiMatcher<DeliveryInfo> deliveryInfoMatcher = new DefaultBiMatcher(comparator);

        // then
        assertFalse(deliveryInfoMatcher.matches(d1, d2));
    }

    @Test
    @DisplayName("Test equal delivery info entities by custom comparator")
    public void test_equal_deliveryInfo_by_andMatcher() {
        // given
        final DeliveryInfo d1 = getDeliveryInfo();
        final DeliveryInfo d2 = getDeliveryInfo();

        // when
        final Comparator<? super DeliveryInfo> comparator = new ComparatorUtils.DefaultNullSafeObjectComparator();

        // when
        final BiMatcher<DeliveryInfo> deliveryInfoMatcher = new DefaultBiMatcher(comparator);

        // then
        assertTrue(deliveryInfoMatcher.matches(d1, d2));
    }
}
