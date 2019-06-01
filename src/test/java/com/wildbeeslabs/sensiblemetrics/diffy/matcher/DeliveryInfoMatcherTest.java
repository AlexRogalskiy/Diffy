/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
import com.wildbeeslabs.sensiblemetrics.diffy.examples.utils.DeliveryInfoMatcherUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractTypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.listener.impl.DefaultMatcherEventListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.core.IsEqual;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.DateUtils.toDate;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertTrue;
import static org.apache.commons.lang3.StringUtils.trim;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;

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

    @Test(expected = NullPointerException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_nullableAndMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = null;

        // then
        assertTrue(deliveryInfoMatcher.and(deliveryInfoMatcher2).and(deliveryInfoMatcher3).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info matchers and default matcher handler")
    public void test_deliveryInfo_by_validMatcherAndDefaultHandler() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DefaultMatcherEventListener<DeliveryInfo> listener = new DefaultMatcherEventListener<>();
        final AbstractMatcher<DeliveryInfo> deliveryInfoMatcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return Objects.equals(DEFAULT_GID_PREFIX, value.getGid())
                    && Objects.equals(DEFAULT_TYPE, value.getType())
                    && Objects.equals(toDate("17/06/2013", DEFAULT_DATE_FORMAT), value.getCreatedAt());
            }
        };
        deliveryInfoMatcher.addListener(listener);

        // then
        assertTrue(deliveryInfoMatcher.matches(getDeliveryInfo()));
        assertThat(listener.getFailedMatchers(), hasSize(0));
        assertThat(listener.getSuccessMatchers(), hasSize(1));

        assertTrue(listener.getSuccessMatchers().contains(deliveryInfoMatcher));
        assertFalse(listener.getFailedMatchers().contains(deliveryInfoMatcher));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info matchers and default matcher handler")
    public void test_deliveryInfo_by_invalidMatcherAndDefaultHandler() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("26/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DefaultMatcherEventListener<DeliveryInfo> listener = new DefaultMatcherEventListener<>();
        final AbstractMatcher<DeliveryInfo> deliveryInfoMatcher = new AbstractTypeSafeMatcher<>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return Objects.equals(DEFAULT_GID_PREFIX, value.getGid())
                    && Objects.equals(DEFAULT_TYPE, value.getType())
                    && Objects.equals(toDate("17/06/2013", DEFAULT_DATE_FORMAT), value.getCreatedAt());
            }
        };
        deliveryInfoMatcher.addListener(listener);

        // then
        assertFalse(deliveryInfoMatcher.matches(getDeliveryInfo()));
        assertThat(listener.getFailedMatchers(), hasSize(1));
        assertThat(listener.getSuccessMatchers(), hasSize(0));

        assertFalse(listener.getSuccessMatchers().contains(deliveryInfoMatcher));
        assertTrue(listener.getFailedMatchers().contains(deliveryInfoMatcher));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_validAndMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.and(deliveryInfoMatcher2).and(deliveryInfoMatcher3).and(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_invalidAndMatcher() {
        // given
        getDeliveryInfo().setType(4);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.and(deliveryInfoMatcher2).and(deliveryInfoMatcher3).and(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test valid delivery info entity by regex pattern")
    public void test_deliveryInfo_by_validRegexMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid("5678");

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final TypeSafeMatcher<DeliveryInfo> deliveryInfoMatcher2 = value -> Objects.nonNull(value) && trim(value.getGid()).matches(DEFAULT_NUMBER_REGEX);

        // then
        assertTrue(deliveryInfoMatcher.and(deliveryInfoMatcher2).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test invalid delivery info entity by regex pattern")
    public void test_deliveryInfo_by_invalidRegexMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid("5678d");

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final TypeSafeMatcher<DeliveryInfo> deliveryInfoMatcher2 = value -> Objects.nonNull(value) && trim(value.getGid()).matches(DEFAULT_NUMBER_REGEX);

        // then
        assertFalse(deliveryInfoMatcher.and(deliveryInfoMatcher2).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info andAll-chained matchers")
    public void test_deliveryInfo_by_validAndAllMatcher() {
        // given
        getDeliveryInfo().setType(4);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(4);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.andAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by invalid custom delivery info andAll-chained matchers")
    public void test_deliveryInfo_by_invalidAndAllMatcher() {
        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(4);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = null;

        // then
        assertTrue(Matcher.andAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info andAll-chained matchers")
    public void test_deliveryInfo_by_nullableAndAllMatcher() {
        // when
        final Matcher<DeliveryInfo> matcher = null;

        // then
        assertTrue(Matcher.andAll(matcher).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by empty custom delivery info andAll-chained matchers")
    public void test_deliveryInfo_by_emptyAndAllMatcher() {
        // when
        final Matcher<DeliveryInfo>[] matcher = new Matcher[0];

        // then
        assertTrue(Matcher.andAll(matcher).matches(getDeliveryInfo()));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info not-chained matchers")
    public void test_deliveryInfo_by_nullableNotMatcher() {
        // given
        getDeliveryInfo().setType(4);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = null;

        // then
        assertTrue(deliveryInfoMatcher.not(deliveryInfoMatcher2).not(deliveryInfoMatcher3).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info not-chained matchers")
    public void test_deliveryInfo_by_validNotMatcher() {
        // given
        getDeliveryInfo().setType(4);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("26/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.not(deliveryInfoMatcher2).not(deliveryInfoMatcher3).not(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_nullableOrMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = null;

        // then
        assertTrue(deliveryInfoMatcher.or(deliveryInfoMatcher2).or(deliveryInfoMatcher3).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_validOrMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("2/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.or(deliveryInfoMatcher2).or(deliveryInfoMatcher3).or(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_invalidOrMatcher() {
        // given
        getDeliveryInfo().setType(6);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("2/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.or(deliveryInfoMatcher2).or(deliveryInfoMatcher3).or(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info orAll-chained matchers")
    public void test_deliveryInfo_by_validOrAllMatcher() {
        // given
        getDeliveryInfo().setType(6);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.orAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by invalid custom delivery info orAll-chained matchers")
    public void test_deliveryInfo_by_invalidOrAllMatcher() {
        // given
        getDeliveryInfo().setType(6);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = null;

        // then
        assertTrue(Matcher.orAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info orAll-chained matchers")
    public void test_deliveryInfo_by_nullableOrAllMatcher() {
        // when
        final Matcher<DeliveryInfo> matcher = null;

        // then
        assertTrue(Matcher.orAll(matcher).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by empty custom delivery info orAll-chained matchers")
    public void test_deliveryInfo_by_emptyOrAllMatcher() {
        // when
        final Matcher<DeliveryInfo>[] matcher = new Matcher[0];

        // then
        assertTrue(Matcher.orAll(matcher).matches(getDeliveryInfo()));
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_nullableXorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = null;

        // then
        assertTrue(deliveryInfoMatcher.xor(deliveryInfoMatcher2).xor(deliveryInfoMatcher3).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_validXorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("10/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("2/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.xor(deliveryInfoMatcher2).xor(deliveryInfoMatcher3).xor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info or-chained matchers")
    public void test_deliveryInfo_by_invalidXorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.xor(deliveryInfoMatcher2).xor(deliveryInfoMatcher3).xor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info nand-chained matchers")
    public void test_deliveryInfo_by_validNandMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.nand(deliveryInfoMatcher2).nand(deliveryInfoMatcher2).nand(deliveryInfoMatcher3).nand(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidOperationException.class)
    @DisplayName("Test delivery info entity by invalid custom delivery info nand-chained matchers")
    public void test_deliveryInfo_by_invalidNandMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/0DEFAULT_TYPE/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/08/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.nand(deliveryInfoMatcher2).nand(deliveryInfoMatcher3).nand(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info nor-chained matchers")
    public void test_deliveryInfo_by_validNorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.nor(deliveryInfoMatcher2).nor(deliveryInfoMatcher3).nor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info nor-chained matchers")
    public void test_deliveryInfo_by_invalidNorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.nor(deliveryInfoMatcher2).nor(deliveryInfoMatcher3).nor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info xnor-chained matchers")
    public void test_deliveryInfo_by_validXnorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(deliveryInfoMatcher.xnor(deliveryInfoMatcher2).xnor(deliveryInfoMatcher3).xnor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info xnor-chained matchers")
    public void test_deliveryInfo_by_invalidXnorMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(deliveryInfoMatcher.xnor(deliveryInfoMatcher2).xnor(deliveryInfoMatcher3).xnor(deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info xorAll-chained matchers")
    public void test_deliveryInfo_by_validXorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.xorAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by invalid custom delivery info xorAll-chained matchers")
    public void test_deliveryInfo_by_invalidXorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = null;

        // then
        assertFalse(Matcher.xorAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by nullable custom delivery info xorAll-chained matchers")
    public void test_deliveryInfo_by_nullableXorAllMatcher() {
        // when
        final Matcher<DeliveryInfo> matcher = null;

        // then
        assertFalse(Matcher.xorAll(matcher).matches(getDeliveryInfo()));
    }

    @Test(expected = InvalidParameterException.class)
    @DisplayName("Test delivery info entity by empty custom delivery info xorAll-chained matchers")
    public void test_deliveryInfo_by_emptyXorAllMatcher() {
        // when
        final Matcher<DeliveryInfo>[] matcher = new Matcher[0];

        // then
        assertFalse(Matcher.xorAll(matcher).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info nandAll-chained matchers")
    public void test_deliveryInfo_by_validNandAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.nandAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info nandAll-chained matchers")
    public void test_deliveryInfo_by_invalidNandAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.nandAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info norAll-chained matchers")
    public void test_deliveryInfo_by_validNorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("28/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(Matcher.norAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info norAll-chained matchers")
    public void test_deliveryInfo_by_invalidNorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(DEFAULT_TYPE);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(Matcher.norAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info xnorAll-chained matchers")
    public void test_deliveryInfo_by_validXnorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("28/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(Matcher.xnorAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info xnorAll-chained matchers")
    public void test_deliveryInfo_by_invalidXnorAllMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(6);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withGid(DEFAULT_GID_PREFIX);
        final Matcher<DeliveryInfo> deliveryInfoMatcher3 = DeliveryInfoMatcherUtils.withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        final Matcher<DeliveryInfo> deliveryInfoMatcher4 = DeliveryInfoMatcherUtils.withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(Matcher.xnorAll(deliveryInfoMatcher, deliveryInfoMatcher2, deliveryInfoMatcher3, deliveryInfoMatcher4).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info negate matcher")
    public void test_deliveryInfo_by_validNegateMatcher() {
        // given
        getDeliveryInfo().setType(DEFAULT_TYPE);
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withType(50);

        // then
        assertTrue(deliveryInfoMatcher.negate().matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by nullable first custom delivery info equal-chained matchers")
    public void test_deliveryInfo_by_nullableFirstEqualMatcher() {
        // given
        final DeliveryInfo deliveryInfo = getDeliveryInfo();

        // then
        assertFalse(Matcher.isEqual(null).matches(deliveryInfo));
    }

    @Test
    @DisplayName("Test delivery info entity by nullable last custom delivery info equal-chained matchers")
    public void test_deliveryInfo_by_nullableLastEqualMatcher() {
        // given
        final DeliveryInfo deliveryInfo = getDeliveryInfo();

        // then
        assertFalse(Matcher.isEqual(deliveryInfo).matches(null));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info equal-chained matchers")
    public void test_deliveryInfo_by_validEqualMatcher() {
        // given
        final DeliveryInfo deliveryInfo = getDeliveryInfo();
        deliveryInfo.setType(4);

        // then
        assertTrue(Matcher.isEqual(deliveryInfo).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info equal-chained matchers")
    public void test_deliveryInfo_by_invalidEqualMatcher() {
        // given
        final DeliveryInfo deliveryInfo = getDeliveryInfoMock().val();

        // then
        assertFalse(Matcher.isEqual(deliveryInfo).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom created/update date fields matcher")
    public void test_deliveryInfo_by_customDateMatcher() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > DEFAULT_TYPE
            && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;

        // when
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        // then
        assertTrue(matcher.matches(getDeliveryInfo()));

        // when
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));

        // then
        assertFalse(matcher.matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field matcher")
    public void test_deliveryInfo_by_customTypeMatcher() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (1 == value.getType());

        // when
        getDeliveryInfo().setType(1);

        // then
        assertTrue(matcher.matches(getDeliveryInfo()));

        // when
        getDeliveryInfo().setType(10);

        // then
        assertFalse(matcher.matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom gid/type fields matchers")
    public void test_deliveryInfoList_by_customGidAndTypeMatcher() {
        // given
        final Integer DELIVERY_INFO_LOWER_TYPE_BOUND = 100;
        final Integer DELIVERY_INFO_UPPER_TYPE_BOUND = 1000;

        final TypeSafeMatcher<DeliveryInfo> gidMatcher = value -> String.valueOf(value.getGid()).substring(0, 4).equalsIgnoreCase(DEFAULT_GID_PREFIX);
        final TypeSafeMatcher<DeliveryInfo> typeMatcher = value -> DELIVERY_INFO_LOWER_TYPE_BOUND < value.getType() && value.getType() < DELIVERY_INFO_UPPER_TYPE_BOUND;

        // when
        final List<DeliveryInfo> deliveryInfoList = asList(
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val(),
            getDeliveryInfoMock().val()
        );

        // then
        assertThat(deliveryInfoList.size(), IsEqual.equalTo(4));
        assertTrue(deliveryInfoList.stream().noneMatch(entity -> gidMatcher.and(typeMatcher).matches(entity)));

        // when
        getDeliveryInfo().setGid(DEFAULT_GID_PREFIX + UUID.randomUUID());
        getDeliveryInfo().setType(150);

        // then
        assertTrue(gidMatcher.and(typeMatcher).matches(getDeliveryInfo()));
    }

    @Test
    @DisplayName("Test delivery info entity by nullable custom gid/type fields matchers")
    public void test_deliveryInfoList_by_nullableGidAndTypeMatcher() {
        // given
        final TypeSafeMatcher<String> gidMatcher = value -> String.valueOf(value).substring(0, 4).equalsIgnoreCase(DEFAULT_GID_PREFIX);

        // when
        final Matcher<DeliveryInfo> deliveryInfoMatcher = DeliveryInfoMatcherUtils.withGidMatcher(gidMatcher);
        final Matcher<DeliveryInfo> deliveryInfoMatcher2 = DeliveryInfoMatcherUtils.withTypeMatcher(null);

        // then
        assertFalse(deliveryInfoMatcher.and(deliveryInfoMatcher2).matches(getDeliveryInfo()));
    }
}
