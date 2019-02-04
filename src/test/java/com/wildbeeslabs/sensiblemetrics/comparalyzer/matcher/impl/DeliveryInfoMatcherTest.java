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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDeliveryInfoDiffComparatorTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entity.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.Matcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Delivery info matcher unit test
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoMatcherTest extends AbstractDeliveryInfoDiffComparatorTest {

    /**
     * Default delivery info instance
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoUnit().val();
    }

    @Test
    public void testDeliveryInfoByMatcher() {
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
                .withType(mockUnitInt.val())
                .withGid(alphaNumbericMockUnitString.val())
                .withCreatedDate(mockUnitLocalDate.toUtilDate().val())
                .withUpdatedDate(mockUnitLocalDate.toUtilDate().val());
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    @Test
    public void testDeliveryInfoByCustomDateMatcher() {
        final Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                        && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;
            }
        };
        final DeliveryInfoMatcher deliveryInfoMatcher = (DeliveryInfoMatcher) DeliveryInfoMatcher.getInstance().withMatcher(matcher);

        this.deliveryInfo.setCreatedAt(DateUtils.toDate("07/06/2013", "dd/MM/yyyy"));
        this.deliveryInfo.setUpdatedAt(DateUtils.toDate("17/06/2018", "dd/MM/yyyy"));
        assertTrue(deliveryInfoMatcher.matches(this.deliveryInfo));

        this.deliveryInfo.setCreatedAt(DateUtils.toDate("17/06/2013", "dd/MM/yyyy"));
        this.deliveryInfo.setUpdatedAt(DateUtils.toDate("27/06/2018", "dd/MM/yyyy"));
        assertFalse(deliveryInfoMatcher.matches(this.deliveryInfo));
    }

    @Test
    public void testDeliveryInfoByCustomTypeMatcher() {
        final Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return 1 == value.getType();
            }
        };
        final DeliveryInfoMatcher deliveryInfoMatcher = (DeliveryInfoMatcher) DeliveryInfoMatcher.getInstance().withMatcher(matcher);

        this.deliveryInfo.setType(1);
        assertTrue(deliveryInfoMatcher.matches(this.deliveryInfo));

        this.deliveryInfo.setType(10);
        assertFalse(deliveryInfoMatcher.matches(this.deliveryInfo));
    }
}