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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.matcher.DeliveryInfoMatcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl.AbstractTypeSafeMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils.toDate;
import static org.junit.Assert.*;

/**
 * Delivery info matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoMatcherTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default date format pattern
     */
    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    /**
     * Default delivery information gid prefix
     */
    private final String DEFAULT_GID_PREFIX = "TEST";

    /**
     * Default delivery information model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoUnit().val();
    }

    @Test
    public void testDeliveryInfoByMatcher() {
        DeliveryInfoMatcher deliveryInfoMatcher = getDeliveryInfoMatcher(
                getIntMock().val(),
                getAlphaNumericStringMock().val(),
                getLocalDateMock().toUtilDate().val(),
                getLocalDateMock().toUtilDate().val()
        );
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));

        getDeliveryInfo().setType(5);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        deliveryInfoMatcher = getDeliveryInfoMatcher(
                5,
                DEFAULT_GID_PREFIX,
                toDate("17/06/2013", DEFAULT_DATE_FORMAT),
                toDate("27/09/2018", DEFAULT_DATE_FORMAT)
        );
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));
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

        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));

        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
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

        getDeliveryInfo().setType(1);
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));

        getDeliveryInfo().setType(10);
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    @Test
    public void testDeliveryInfoListByCustomGidAndTypeMatcher() {
        final Integer DELIVERY_INFO_LOWER_TYPE_BOUND = 100;
        final Integer DELIVERY_INFO_UPPER_TYPE_BOUND = 1000;

        final Matcher<? super String> gidMatcher = new AbstractTypeSafeMatcher<String>() {
            @Override
            public boolean matchesSafe(final String value) {
                return String.valueOf(value).substring(0, 4).equalsIgnoreCase(DEFAULT_GID_PREFIX);
            }
        };
        final Matcher<? super Integer> typeMatcher = new AbstractTypeSafeMatcher<Integer>() {
            @Override
            public boolean matchesSafe(final Integer value) {
                return DELIVERY_INFO_LOWER_TYPE_BOUND < value && value < DELIVERY_INFO_UPPER_TYPE_BOUND;
            }
        };
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
                .withGidMatcher(gidMatcher)
                .withTypeMatcher(typeMatcher);

        final List<DeliveryInfo> deliveryInfoList = Arrays.asList(
                getDeliveryInfoUnit().val(),
                getDeliveryInfoUnit().val(),
                getDeliveryInfoUnit().val(),
                getDeliveryInfoUnit().val()
        );
        assertEquals(4, deliveryInfoList.size());
        assertTrue(deliveryInfoList.stream().noneMatch(entity -> deliveryInfoMatcher.matches(entity)));

        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX + UUID.randomUUID());
        getDeliveryInfo().setType(150);
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    protected DeliveryInfoMatcher getDeliveryInfoMatcher(final Integer type, final String gid, final Date createdDate, final Date updatedDate) {
        return DeliveryInfoMatcher.getInstance()
                .withType(type)
                .withGid(gid)
                .withCreatedDate(createdDate)
                .withUpdatedDate(updatedDate);
    }
}