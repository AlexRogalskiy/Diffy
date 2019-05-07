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
import com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher.DeliveryInfoMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractTypeSafeMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.DateUtils.toDate;
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
     * Default {@link DeliveryInfo} model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info matcher")
    public void testDeliveryInfoByMatcher() {
        // when
        DeliveryInfoMatcher deliveryInfoMatcher = getDeliveryInfoMatcher(
            getIntMock().val(),
            getAlphaNumericStringMock().val(),
            getLocalDateMock().toUtilDate().val(),
            getLocalDateMock().toUtilDate().val()
        );
        // then
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));

        // given
        getDeliveryInfo().setType(5);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        deliveryInfoMatcher = getDeliveryInfoMatcher(
            5,
            DEFAULT_GID_PREFIX,
            toDate("17/06/2013", DEFAULT_DATE_FORMAT),
            toDate("27/09/2018", DEFAULT_DATE_FORMAT)
        );

        // then
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_AndMatcher() {
        // given
        getDeliveryInfo().setType(5);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
            .withType(5)
            .withGid(DEFAULT_GID_PREFIX);
        final DeliveryInfoMatcher deliveryInfoMatcher2 = DeliveryInfoMatcher.getInstance()
            .withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT))
            .withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.and(deliveryInfoMatcher2).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_OrMatcher() {
        // given
        getDeliveryInfo().setType(5);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
            .withType(5)
            .withGid(DEFAULT_GID_PREFIX);
        final DeliveryInfoMatcher deliveryInfoMatcher2 = DeliveryInfoMatcher.getInstance()
            .withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT))
            .withUpdatedDate(toDate("2/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.or(deliveryInfoMatcher2).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info negate matcher")
    public void test_deliveryInfo_by_NegateMatcher() {
        // given
        getDeliveryInfo().setType(5);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
            .withType(50)
            .withGid(DEFAULT_GID_PREFIX);

        // then
        assertTrue(deliveryInfoMatcher.negate().matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom created/update date fields matcher")
    public void testDeliveryInfoByCustomDateMatcher() {
        // given
        final Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                    && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;
            }
        };
        final DeliveryInfoMatcher deliveryInfoMatcher = (DeliveryInfoMatcher) DeliveryInfoMatcher.getInstance().withMatcher(matcher);

        // when
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));

        // when
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field matcher")
    public void testDeliveryInfoByCustomTypeMatcher() {
        // given
        final Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return (1 == value.getType());
            }
        };

        // when
        final DeliveryInfoMatcher deliveryInfoMatcher = (DeliveryInfoMatcher) DeliveryInfoMatcher.getInstance().withMatcher(matcher);
        getDeliveryInfo().setType(1);

        // then
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));

        // when
        getDeliveryInfo().setType(10);

        // then
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom gid/type fields matchers")
    public void testDeliveryInfoListByCustomGidAndTypeMatcher() {
        // given
        final Integer DELIVERY_INFO_LOWER_TYPE_BOUND = 100;
        final Integer DELIVERY_INFO_UPPER_TYPE_BOUND = 1000;

        final Matcher<? super String> gidMatcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final String value) {
                return String.valueOf(value).substring(0, 4).equalsIgnoreCase(DEFAULT_GID_PREFIX);
            }
        };
        final Matcher<? super Integer> typeMatcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final Integer value) {
                return DELIVERY_INFO_LOWER_TYPE_BOUND < value && value < DELIVERY_INFO_UPPER_TYPE_BOUND;
            }
        };

        // when
        final DeliveryInfoMatcher deliveryInfoMatcher = DeliveryInfoMatcher.getInstance()
            .withGidMatcher(gidMatcher)
            .withTypeMatcher(typeMatcher);

        final List<DeliveryInfo> deliveryInfoList = Arrays.asList(
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val()
        );

        // then
        assertThat(deliveryInfoList.size(), IsEqual.equalTo(4));
        assertTrue(deliveryInfoList.stream().noneMatch(entity -> deliveryInfoMatcher.matches(entity)));

        // when
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX + UUID.randomUUID());
        getDeliveryInfo().setType(150);

        // then
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));
    }

    /**
     * Returns new delivery info matcher {@link DeliveryInfoMatcher} instance by initial input arguments
     *
     * @param type        - initial input type value
     * @param gid         - initial input global id value
     * @param createdDate - initial input created date value {@link Date}
     * @param updatedDate - initial input udpated date value {@link Date}
     * @return new delivery info matcher {@link DeliveryInfoMatcher} instance
     */
    protected DeliveryInfoMatcher getDeliveryInfoMatcher(final Integer type, final String gid, final Date createdDate, final Date updatedDate) {
        return DeliveryInfoMatcher.getInstance()
            .withType(type)
            .withGid(gid)
            .withCreatedDate(createdDate)
            .withUpdatedDate(updatedDate);
    }
}
