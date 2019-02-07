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

import com.google.common.collect.Lists;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.factory.DefaultDiffMatcherFactory;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl.AbstractTypeSafeMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils.toDate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertFalse;

/**
 * Delivery info difference matcher unit test
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
     * Default delivery information model
     */
    private DeliveryInfo deliveryInfo;

    @Before
    public void setUp() {
        this.deliveryInfo = getDeliveryInfoUnit().val();
    }

    @Test
    public void testDeliveryInfoByDefaultTypeDiffMatcher() {
        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create();
        final Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatches(getDeliveryInfo());
        final List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, empty());
    }

    @Test
    public void testDeliveryInfoByDateDiffMatcher() {
        final Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                        && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;
            }
        };

        // Update delivery info created / updated dates
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatches(getDeliveryInfo());
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, empty());

        // Update delivery info created / updated dates
        getDeliveryInfo().setCreatedAt(toDate("17/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("27/06/2018", DEFAULT_DATE_FORMAT));

        // Check matchable delivery info
        iterable = diffMatcher.diffMatches(getDeliveryInfo());
        diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, not(empty()));

        DefaultDiffMatchEntry entry = DefaultDiffMatchEntry
                .builder()
                .value(getDeliveryInfo())
                .build();
        assertFalse(diffMatchEntryList.contains(entry));
    }

    @Test
    public void testDeliveryInfoByCustomTypeDiffMatcher() {
        Matcher<DeliveryInfo> matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return value.getType() > 1000 && value.getType() < 5000;
            }
        };
        DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(matcher);
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatches(getDeliveryInfo());
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, not(empty()));

        DefaultDiffMatchEntry entry = DefaultDiffMatchEntry
                .builder()
                .value(getDeliveryInfo())
                .build();
        assertFalse(diffMatchEntryList.contains(entry));

        // Update delivery info type matcher
        matcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return value.getType() >= 0 && value.getType() <= 10;
            }
        };

        // Check matchable delivery info
        diffMatcher = DefaultDiffMatcherFactory.create(matcher);
        iterable = diffMatcher.diffMatches(getDeliveryInfo());
        diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, empty());
    }

    @Test
    public void testDeliveryInfoByCustomTypeAndDateDiffMatcher() {
        final Matcher<DeliveryInfo> dateMatcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return LocalDateTime.fromDateFields(value.getCreatedAt()).getDayOfMonth() > 5
                        && LocalDateTime.fromDateFields(value.getUpdatedAt()).getDayOfMonth() < 20;
            }
        };
        final Matcher<DeliveryInfo> typeMatcher = new AbstractTypeSafeMatcher<DeliveryInfo>() {
            @Override
            public boolean matchesSafe(final DeliveryInfo value) {
                return value.getType() > 1000 && value.getType() < 5000;
            }
        };

        // Update delivery info created / updated dates
        getDeliveryInfo().setCreatedAt(toDate("07/06/2013", DEFAULT_DATE_FORMAT));
        getDeliveryInfo().setUpdatedAt(toDate("17/06/2018", DEFAULT_DATE_FORMAT));

        final DiffMatcher<DeliveryInfo> diffMatcher = DefaultDiffMatcherFactory.create(dateMatcher, typeMatcher);
        Iterable<DefaultDiffMatchEntry> iterable = diffMatcher.diffMatches(getDeliveryInfo());
        List<DefaultDiffMatchEntry> diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, not(empty()));

        DefaultDiffMatchEntry entry = DefaultDiffMatchEntry
                .builder()
                .value(getDeliveryInfo())
                .build();
        assertFalse(diffMatchEntryList.contains(entry));


        // Update delivery info type
        getDeliveryInfo().setType(1001);

        // Check matchable delivery info
        iterable = diffMatcher.diffMatches(getDeliveryInfo());
        diffMatchEntryList = Lists.newArrayList(iterable);
        assertThat(diffMatchEntryList, empty());
    }
}