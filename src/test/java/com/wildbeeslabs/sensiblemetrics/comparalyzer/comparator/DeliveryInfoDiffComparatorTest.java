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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.impl.DefaultDiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.factory.DefaultDiffComparatorFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils.now;
import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.DateUtils.toDate;
import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils.getAllFields;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Delivery info difference comparator unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoDiffComparatorTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default delivery information models
     */
    private DeliveryInfo deliveryInfoFirst;
    private DeliveryInfo deliveryInfoLast;

    @Before
    public void setUp() {
        this.deliveryInfoFirst = getDeliveryInfoMock().val();
        this.deliveryInfoLast = getDeliveryInfoMock().val();
    }

    @Test
    public void testCompareDifferentEntities() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));

        assertEquals("gid", valueChangeList.get(2).getPropertyName());
        assertEquals(getDeliveryInfoFirst().getGid(), valueChangeList.get(2).getFirst());
        assertEquals(getDeliveryInfoLast().getGid(), valueChangeList.get(2).getLast());
        assertNotEquals(getDeliveryInfoFirst().getBalance(), getDeliveryInfoLast().getBalance());

        assertEquals("updatedAt", valueChangeList.get(valueChangeList.size() - 1).getPropertyName());
        assertEquals(getDeliveryInfoFirst().getUpdatedAt(), valueChangeList.get(valueChangeList.size() - 1).getFirst());
        assertEquals(getDeliveryInfoLast().getUpdatedAt(), valueChangeList.get(valueChangeList.size() - 1).getLast());
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());
    }

    @Test
    public void testCompareEqualEntities() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoFirst());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertEquals(0, valueChangeList.size());
    }

    @Test
    public void testCompareByExcludedPropertiesAndDefaultComparator() {
        final List<String> excludedProperties = Arrays.asList("id", "createdAt", "updatedAt");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(getDeliveryInfoFirst().getType())
                .last(getDeliveryInfoLast().getType())
                .build();
        assertTrue(valueChangeList.contains(entry));

    }

    @Test
    public void testCompareByIncludedPropertiesAndDefaultComparator() {
        final List<String> includedProperties = Arrays.asList("id", "type", "description");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, includedProperties, Collections.emptyList());
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(includedProperties.size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByExcludedProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());
    }

    @Test
    public void testCompareByIncludedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, includedProperties, Collections.emptyList());
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(includedProperties.size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getCreatedAt(), getDeliveryInfoLast().getCreatedAt());
    }

    @Test
    public void testCompareByNonExistingProperty() {
        final List<String> excludedProperties = Arrays.asList("id", "country", "createdAt", "updatedAt");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(getDeliveryInfoFirst().getType())
                .last(getDeliveryInfoLast().getType())
                .build();
        assertTrue(valueChangeList.contains(entry));

        assertTrue(valueChangeList.stream().noneMatch(value -> "_sums".equalsIgnoreCase(value.getPropertyName())));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getId(), getDeliveryInfoLast().getId());
    }

    @Test
    public void testCompareByIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt", "updatedAt", "description");
        final List<String> excludedProperties = Arrays.asList("createdAt", "updatedAt", "type");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, includedProperties, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(CollectionUtils.subtract(includedProperties, excludedProperties).size(), valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getId(), getDeliveryInfoLast().getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(getDeliveryInfoFirst().getType())
                .last(getDeliveryInfoLast().getType())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());
    }

    @Test
    public void testCompareByNonIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "balance");
        final List<String> excludedProperties = Arrays.asList("description", "createdAt", "updatedAt");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, includedProperties, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(includedProperties.size(), valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getId(), getDeliveryInfoLast().getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByIncludedDuplicateProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "createdAt", "id", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(3, valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getId(), getDeliveryInfoLast().getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertNotEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByExcludedDuplicateProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type", "description", "type", "type", "id");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getCreatedAt(), getDeliveryInfoLast().getCreatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByDefaultComparator() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));

        assertTrue(valueChangeList.stream().noneMatch(value -> Objects.equals(value.getFirst(), value.getLast())));
    }

    @Test
    public void testCompareByNonEqualGidFieldAndComparator() {
        final List<String> includedProperties = Arrays.asList("id", "type", "createdAt", "description");

        getDeliveryInfoFirst().setDescription(null);
        getDeliveryInfoLast().setDescription(null);

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("gid", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(3, valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getId(), getDeliveryInfoLast().getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("gid")
                .first(getDeliveryInfoFirst().getGid())
                .last(getDeliveryInfoLast().getGid())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getGid(), getDeliveryInfoLast().getGid());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());
    }

    @Test
    public void testCompareByEqualsGidFieldAndComparator() {
        final List<String> includedProperties = Arrays.asList("id", "type", "gid", "createdAt");

        final String DEFAULT_GID_PREFIX = "TEST_";
        getDeliveryInfoFirst().setGid(DEFAULT_GID_PREFIX + UUID.randomUUID().toString());
        getDeliveryInfoLast().setGid(DEFAULT_GID_PREFIX + UUID.randomUUID().toString());

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("gid", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(3, valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("gid")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getGid(), getDeliveryInfoLast().getGid());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getId())
                .last(getDeliveryInfoLast().getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByDateFieldsAndComparators() {
        final List<String> includedProperties = Arrays.asList("createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("createdAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getHourOfDay()));
        diffComparator.setComparator("updatedAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getDayOfMonth()));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(1, valueChangeList.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getCreatedAt(), getDeliveryInfoLast().getCreatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());
    }

    @Test
    public void testCompareByDateFieldAndComparator() {
        final int DEFAULT_DIFFERENCE_DELTA = 24 * 60 * 60 * 1000;
        final List<String> includedProperties = Arrays.asList("createdAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("createdAt", (Comparator<Date>) (d1, d2) -> Math.abs(d1.getTime() - d2.getTime()) <= DEFAULT_DIFFERENCE_DELTA ? 0 : d1.compareTo(d2));

        final LocalDate initialDate = now();
        getDeliveryInfoFirst().setCreatedAt(toDate(initialDate));
        getDeliveryInfoLast().setCreatedAt(toDate(initialDate.plus(1, ChronoUnit.DAYS)));

        Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, empty());

        getDeliveryInfoFirst().setCreatedAt(toDate(initialDate));
        getDeliveryInfoLast().setCreatedAt(toDate(initialDate.minus(2, ChronoUnit.DAYS)));

        iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        valueChangeList = Lists.newArrayList(iterable);
        assertEquals(1, valueChangeList.size());
    }

    @Test
    public void testCompareByBalanceFieldAndComparator() {
        final double DEFAULT_DIFFERENCE_DELTA = 0.0001;
        final List<String> includedProperties = Arrays.asList("balance");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("balance", (Comparator<Double>) (d1, d2) -> Math.abs(d1 - d2) <= DEFAULT_DIFFERENCE_DELTA ? 0 : d1.compareTo(d2));

        getDeliveryInfoFirst().setBalance(1.0000567);
        getDeliveryInfoLast().setBalance(1.0000547);

        Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, empty());

        getDeliveryInfoFirst().setBalance(1.0000547);
        getDeliveryInfoLast().setBalance(1.0001567);

        iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        valueChangeList = Lists.newArrayList(iterable);
        assertEquals(1, valueChangeList.size());
    }

    @Test
    public void testCompareByNonEqualAddressField() {
        final List<String> includedProperties = Arrays.asList("addresses");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, includedProperties, Collections.emptyList());
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getDeliveryInfoFirst().getAddresses().size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(getDeliveryInfoFirst().getCreatedAt())
                .last(getDeliveryInfoLast().getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("addresses")
                .first(getDeliveryInfoFirst().getAddresses())
                .last(getDeliveryInfoLast().getAddresses())
                .build();
        assertTrue(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareByEqualAddressField() {
        final List<String> includedProperties = Arrays.asList("addresses");

        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_DELIVERY_INFO_COMPARATOR, includedProperties, Collections.emptyList());
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoFirst());

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, empty());
    }
}
