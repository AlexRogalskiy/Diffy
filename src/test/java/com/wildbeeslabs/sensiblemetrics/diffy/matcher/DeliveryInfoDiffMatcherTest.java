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

import com.google.common.collect.Lists;
import com.wildbeeslabs.sensiblemetrics.diffy.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.impl.DefaultDiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.MatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.factory.DefaultDiffMatcherFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.DiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.DateUtils.toDate;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertNotNull;

/**
 * Delivery info difference matcher unit test {@link AbstractDeliveryInfoDiffTest}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoDiffMatcherTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default date format pattern
     */
    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Default {@link DeliveryInfo} model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test delivery info entity by default diff matcher")
    public void test_deliveryInfo_by_defaultTypeDiffMatcher() {
        // given
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create();

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));
    }

    @Test
    @DisplayName("Test nullable delivery info entity by default diff matcher")
    public void test_nullableDeliveryInfo_by_defaultTypeDiffMatcher() {
        // given
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create();

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(null);
        assertNotNull("Should not be null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom created/update date fields diff matcher")
    public void test_deliveryInfo_by_dateDiffMatcher() {
        // given
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (
            LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20
        );
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));

        // given
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));

        // when
        iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(getDeliveryInfo(), MatchDescription.EMPTY_MATCH_DESCRIPTION);
        assertTrue(diffMatchEntryList.contains(entry));
    }

    @Test(expected = MatchOperationException.class)
    @DisplayName("Test nullable delivery info entity by custom type field diff matcher")
    public void test_nullableDeliveryInfo_by_customTypeDiffMatcher() {
        // given
        getDeliveryInfo().setType(getCodeMock().val());

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (value.getType() > 1000 && value.getType() < 5000);
        DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(null);
        assertNotNull("Should not be null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));

        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(null, MatchDescription.EMPTY_MATCH_DESCRIPTION);
        assertTrue(diffMatchEntryList.contains(entry));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher with comparator")
    public void test_deliveryInfo_by_customTypeDiffMatcherWithComparator() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (0 == Objects.compare(value, null, new ComparatorUtils.DefaultNullSafeObjectComparator()));
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(getDeliveryInfo(), MatchDescription.EMPTY_MATCH_DESCRIPTION);
        assertTrue(diffMatchEntryList.contains(entry));
    }

    @Test(expected = MatchOperationException.class)
    @DisplayName("Test invalid delivery info entity by custom type field diff matcher with comparator")
    public void test_invalidDeliveryInfo_by_customTypeDiffMatcherWithComparator() {
        // given
        getDeliveryInfo().setCreatedAt(null);

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5;
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher with identity comparator")
    public void test_deliveryInfo_by_customTypeDiffMatcherWithIdentityComparator() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (0 == Objects.compare(value, value, new ComparatorUtils.DefaultNullSafeObjectComparator()));
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher")
    public void test_deliveryInfo_by_customTypeDiffMatcher() {
        // given
        getDeliveryInfo().setType(getCodeMock().val());

        TypeSafeMatcher<DeliveryInfo> matcher = value -> (value.getType() > 1000 && value.getType() < 5000);
        DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(getDeliveryInfo(), MatchDescription.EMPTY_MATCH_DESCRIPTION);
        assertTrue(diffMatchEntryList.contains(entry));

        // given
        matcher = value -> (value.getType() >= 0 && value.getType() <= 10);
        diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type/date fields diff matchers")
    public void test_deliveryInfo_by_customTypeAndDateDiffMatcher() {
        // given
        final TypeSafeMatcher<DeliveryInfo> dateMatcher = value -> (
            LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20
        );
        final TypeSafeMatcher<DeliveryInfo> typeMatcher = value -> (value.getType() > 1000 && value.getType() < 5000);

        getDeliveryInfo().setType(getCodeMock().val());
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(dateMatcher, typeMatcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(getDeliveryInfo(), MatchDescription.EMPTY_MATCH_DESCRIPTION);
        assertTrue(diffMatchEntryList.contains(entry));

        // given
        getDeliveryInfo().setType(1001);

        // when
        iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Should not be null", iterable);
        diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(empty()));
    }
}
