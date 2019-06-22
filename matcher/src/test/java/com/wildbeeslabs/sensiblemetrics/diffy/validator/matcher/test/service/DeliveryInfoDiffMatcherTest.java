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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.test.service;

import com.google.common.collect.Lists;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.test.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.entry.impl.DefaultDiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher.DeliveryInfoDiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.MatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.factory.DefaultDiffMatcherFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.DiffMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.TypeSafeMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.DateUtils.toDate;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.startsWith;

/**
 * Delivery info difference matcher unit test {@link AbstractDeliveryInfoDiffTest}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public class DeliveryInfoDiffMatcherTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default {@link DeliveryInfo} type
     */
    public static final int DEFAULT_TYPE = 5;
    /**
     * Default date format pattern
     */
    private final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    /**
     * Default {@link DeliveryInfo} gid prefix
     */
    private final String DEFAULT_GID_PREFIX = "TEST";
    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    /**
     * Default {@link DeliveryInfo} model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = this.getDeliveryInfoMock().val();
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info matcher")
    public void test_deliveryInfo_by_Matcher() {
        // given
        DeliveryInfo deliveryInfo = this.getDeliveryInfo();
        DeliveryInfoDiffMatcher deliveryInfoMatcher = this.getDeliveryInfoMatcher(
            getIntMock().val(),
            getAlphaNumericStringMock().val(),
            getLocalDateMock().toUtilDate().val(),
            getLocalDateMock().toUtilDate().val()
        );

        // when
        Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.deliveryInfo);
        assertNotNull("Should not be null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.deliveryInfo);
        assertThat(diffMatchEntryList, hasItem(entry));

        // given
        this.getDeliveryInfo().setType(DEFAULT_TYPE);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        deliveryInfoMatcher = this.getDeliveryInfoMatcher(
            DEFAULT_TYPE,
            DEFAULT_GID_PREFIX,
            toDate("17/06/2013", DEFAULT_DATE_FORMAT),
            toDate("27/09/2018", DEFAULT_DATE_FORMAT)
        );

        // when
        iterable = deliveryInfoMatcher.diffMatch(deliveryInfo);
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test delivery info entity by default diff matcher")
    public void test_deliveryInfo_by_defaultTypeDiffMatcher() {
        // given
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create();

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test nullable delivery info entity by default diff matcher")
    public void test_nullableDeliveryInfo_by_defaultTypeDiffMatcher() {
        // given
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create();

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(null);
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test delivery info entity by custom created/update date fields diff matcher")
    public void test_deliveryInfo_by_dateDiffMatcher() {
        // given
        this.getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (
            LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20
        );
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());

        // given
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));

        // when
        iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test nullable delivery info entity by custom type field diff matcher")
    public void test_nullableDeliveryInfo_by_customTypeDiffMatcher() {
        // given
        this.getDeliveryInfo().setType(getCodeMock().val());

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (value.getType() > 1000 && value.getType() < 5000);
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // then
        thrown.expect(MatchOperationException.class);
        thrown.expectMessage(startsWith("cannot process match operation"));

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(null);
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(null);
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher with comparator")
    public void test_deliveryInfo_by_customTypeDiffMatcherWithComparator() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (0 == Objects.compare(value, null, new ComparatorUtils.DefaultNullSafeObjectComparator()));
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test invalid delivery info entity by custom type field diff matcher with comparator")
    public void test_invalidDeliveryInfo_by_customTypeDiffMatcherWithComparator() {
        // given
        this.getDeliveryInfo().setCreatedAt(null);

        final TypeSafeMatcher<DeliveryInfo> matcher = value -> LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5;
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // then
        thrown.expect(MatchOperationException.class);
        thrown.expectMessage(startsWith("cannot process match operation"));

        // when
        diffMatcher.diffMatch(this.getDeliveryInfo());
    }

    @Test
    @DisplayName("Test delivery info entity by nullable custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_validDiffMatcher() {
        // given
        this.getDeliveryInfo().setType(DEFAULT_TYPE);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoDiffMatcher deliveryInfoMatcher = DeliveryInfoDiffMatcher.of()
            .withType(DEFAULT_TYPE)
            .withGid(DEFAULT_GID_PREFIX);

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test delivery info entity by valid custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_validDiffMatcherWithDates() {
        // given
        this.getDeliveryInfo().setType(DEFAULT_TYPE);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoDiffMatcher deliveryInfoMatcher = DeliveryInfoDiffMatcher.of()
            .withType(DEFAULT_TYPE)
            .withGid(DEFAULT_GID_PREFIX)
            .withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT))
            .withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test delivery info entity by invalid custom delivery info and-chained matchers")
    public void test_deliveryInfo_by_invalidDiffMatcherWithDates() {
        // given
        this.getDeliveryInfo().setType(4);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final DeliveryInfoDiffMatcher deliveryInfoMatcher = DeliveryInfoDiffMatcher.of()
            .withType(DEFAULT_TYPE)
            .withGid(DEFAULT_GID_PREFIX)
            .withCreatedDate(toDate("17/06/2013", DEFAULT_DATE_FORMAT))
            .withUpdatedDate(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test delivery info entity by nullable custom delivery info not-chained matchers")
    public void test_deliveryInfo_by_invalidDiffMatcher2() {
        // given
        this.getDeliveryInfo().setType(4);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        final DeliveryInfoDiffMatcher deliveryInfoMatcher = DeliveryInfoDiffMatcher.of()
            .withType(DEFAULT_TYPE)
            .withGid(DEFAULT_GID_PREFIX);

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test delivery info entity by custom delivery info not-chained matchers")
    public void test_deliveryInfo_by_invalidDiffMatcher3() {
        // given
        this.getDeliveryInfo().setType(4);
        this.getDeliveryInfo().setGid(DEFAULT_GID_PREFIX);
        this.getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("27/09/2018", DEFAULT_DATE_FORMAT));

        final DeliveryInfoDiffMatcher deliveryInfoMatcher = DeliveryInfoDiffMatcher.of()
            .withType(DEFAULT_TYPE)
            .withGid(DEFAULT_GID_PREFIX)
            .withCreatedDate(toDate("18/06/2013", DEFAULT_DATE_FORMAT))
            .withUpdatedDate(toDate("26/09/2018", DEFAULT_DATE_FORMAT));

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = deliveryInfoMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));

        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher with identity comparator")
    public void test_deliveryInfo_by_customTypeDiffMatcherWithIdentityComparator() {
        // given
        final TypeSafeMatcher<DeliveryInfo> matcher = value -> (0 == Objects.compare(value, value, new ComparatorUtils.DefaultNullSafeObjectComparator()));
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    @Test
    @DisplayName("Test delivery info entity by custom type field diff matcher")
    public void test_deliveryInfo_by_customTypeDiffMatcher() {
        // given
        this.getDeliveryInfo().setType(getCodeMock().val());

        TypeSafeMatcher<DeliveryInfo> matcher = value -> (value.getType() > 1000 && value.getType() < 5000);
        DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));

        // given
        matcher = value -> (value.getType() >= 0 && value.getType() <= 10);
        diffMatcher = DefaultDiffMatcherFactory.create(matcher);

        // when
        iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
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

        this.getDeliveryInfo().setType(getCodeMock().val());
        this.getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        this.getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(dateMatcher, typeMatcher);

        // when
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatch(this.getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);

        // then
        assertThat(diffMatchEntryList, is(not(empty())));
        final DefaultDiffMatchEntry entry = DefaultDiffMatchEntry.of(this.getDeliveryInfo());
        assertThat(diffMatchEntryList, hasItem(entry));

        // given
        this.getDeliveryInfo().setType(1001);

        // when
        iterable = diffMatcher.diffMatch(getDeliveryInfo());
        assertNotNull("Result set is null", iterable);
        assertThat(iterable, emptyIterable());
    }

    /**
     * Returns new delivery info matcher {@link DeliveryInfoDiffMatcher} instance by initial input arguments
     *
     * @param type        - initial input type value
     * @param gid         - initial input global id value
     * @param createdDate - initial input created date value {@link Date}
     * @param updatedDate - initial input udpated date value {@link Date}
     * @return new delivery info matcher {@link DeliveryInfoDiffMatcher} instance
     */
    @NonNull
    protected DeliveryInfoDiffMatcher getDeliveryInfoMatcher(final Integer type, final String gid, final Date createdDate, final Date updatedDate) {
        return DeliveryInfoDiffMatcher.of()
            .withType(type)
            .withGid(gid)
            .withCreatedDate(createdDate)
            .withUpdatedDate(updatedDate);
    }
}
