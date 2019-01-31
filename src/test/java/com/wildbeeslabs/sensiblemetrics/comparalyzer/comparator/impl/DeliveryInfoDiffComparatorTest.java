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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDeliveryInfoDiffComparatorTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entity.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.factory.DefaultDiffComparatorFactory;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils.getAllFields;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Delivery info difference comparator unit test
 */
public class DeliveryInfoDiffComparatorTest extends AbstractDeliveryInfoDiffComparatorTest {

    /**
     * Default delivery information entities
     */
    private DeliveryInfo deliveryInfoFirst;
    private DeliveryInfo deliveryInfoLast;

    @Before
    public void setUp() {
        this.deliveryInfoFirst = getDeliveryInfoReflect().val();
        this.deliveryInfoLast = getDeliveryInfoReflect().val();
    }

    @Test
    public void testCompareDifferentEntitiesWithAllProperties() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));

        assertEquals(valueChangeList.get(1).getPropertyName(), "description");
        assertEquals(valueChangeList.get(1).getFirst(), this.deliveryInfoFirst.getDescription());
        assertEquals(valueChangeList.get(1).getLast(), this.deliveryInfoLast.getDescription());
        assertNotEquals(this.deliveryInfoFirst.getDescription(), this.deliveryInfoLast.getDescription());

        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getPropertyName(), "updatedAt");
        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getFirst(), this.deliveryInfoFirst.getUpdatedAt());
        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getLast(), this.deliveryInfoLast.getUpdatedAt());
        assertNotEquals(this.deliveryInfoFirst.getUpdatedAt(), this.deliveryInfoLast.getUpdatedAt());
    }

    @Test
    public void testCompareEqualEntitiesWithAllProperties() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoFirst);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertEquals(valueChangeList.size(), 0);
    }

    @Test
    public void testCompareDifferentEntitiesWithExcludedProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, deliveryInfoComparator);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(this.deliveryInfoFirst.getType())
                .last(this.deliveryInfoLast.getType())
                .build();
        assertTrue(valueChangeList.contains(entry));

    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "description");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, deliveryInfoComparator);
        diffComparator.includeProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(includedProperties.size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
    }

    @Test
    public void testDiffComparatorEntryWithExcludedProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, deliveryInfoComparator);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getDescription(), this.deliveryInfoLast.getDescription());
    }

    @Test
    public void testDiffComparatorEntryWithIncludedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, deliveryInfoComparator);
        diffComparator.includeProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(includedProperties.size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getCreatedAt(), this.deliveryInfoLast.getCreatedAt());
    }

    @Test(expected = RuntimeException.class)
    public void testCompareDifferentEntitiesWithNonExistedIncludedProperty() {
        final List<String> includedProperties = Arrays.asList("id", "city", "firstName", "lastName");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
    }

    @Test
    public void testCompareDifferentEntitiesWithNonExistedExcludedProperty() {
        final List<String> excludedProperties = Arrays.asList("id", "country", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(this.deliveryInfoFirst.getType())
                .last(this.deliveryInfoLast.getType())
                .build();
        assertTrue(valueChangeList.contains(entry));

        assertTrue(valueChangeList.stream().noneMatch(value -> "_sums".equalsIgnoreCase(value.getPropertyName())));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getId(), this.deliveryInfoLast.getId());
    }

    @Test
    public void testCompareDifferentEntitiesWithIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt", "updatedAt", "description");
        final List<String> excludedProperties = Arrays.asList("createdAt", "updatedAt", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), CollectionUtils.subtract(includedProperties, excludedProperties).size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getId(), this.deliveryInfoLast.getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(this.deliveryInfoFirst.getType())
                .last(this.deliveryInfoLast.getType())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithIntersectedProperties2() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt", "updatedAt", "description");
        final List<String> excludedProperties = Arrays.asList("createdAt", "updatedAt", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 2);

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("type")
                .first(this.deliveryInfoFirst.getType())
                .last(this.deliveryInfoLast.getType())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getDescription(), this.deliveryInfoLast.getDescription());
    }

    @Test
    public void testCompareDifferentEntitiesWithNonIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type");
        final List<String> excludedProperties = Arrays.asList("description", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), includedProperties.size());

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getId(), this.deliveryInfoLast.getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedDuplicateProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "createdAt", "id", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getId(), this.deliveryInfoLast.getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertNotEquals(this.deliveryInfoFirst.getDescription(), this.deliveryInfoLast.getDescription());
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithExcludedDuplicateProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type", "description", "type", "type", "id");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length - Sets.newHashSet(excludedProperties).size()));

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getCreatedAt(), this.deliveryInfoLast.getCreatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesAreNonEqual() {
        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));

        assertTrue(valueChangeList.stream().noneMatch(value -> Objects.equals(value.getFirst(), value.getLast())));
    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedPropertiesAndNonEqualStringComparator() {
        final List<String> includedProperties = Arrays.asList("id", "type", "createdAt");
        this.deliveryInfoFirst.setDescription(null);
        this.deliveryInfoLast.setDescription(null);

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("_identCode", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getId(), this.deliveryInfoLast.getId());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertNotEquals(this.deliveryInfoFirst.getUpdatedAt(), this.deliveryInfoLast.getUpdatedAt());
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertEquals(this.deliveryInfoFirst.getDescription(), this.deliveryInfoLast.getDescription());
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedPropertiesAndEqualStringComparator() {
        final String defaultDeliveryDescription = "TEST_";
        final List<String> includedProperties = Arrays.asList("id", "type", "description", "createdAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        this.deliveryInfoFirst.setDescription(defaultDeliveryDescription.concat(UUID.randomUUID().toString()));
        this.deliveryInfoLast.setDescription(defaultDeliveryDescription.concat(UUID.randomUUID().toString()));
        diffComparator.setComparator("description", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getUpdatedAt(), this.deliveryInfoLast.getUpdatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(this.deliveryInfoFirst.getDescription())
                .last(this.deliveryInfoLast.getDescription())
                .build();
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("id")
                .first(this.deliveryInfoFirst.getId())
                .last(this.deliveryInfoLast.getId())
                .build();
        assertTrue(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedPropertiesAndNonEqualDateComparator() {
        final List<String> includedProperties = Arrays.asList("createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.setComparator("createdAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getHourOfDay()));
        diffComparator.setComparator("updatedAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getDayOfMonth()));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(this.deliveryInfoFirst, this.deliveryInfoLast);
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 1);

        DefaultDiffEntry entry = DefaultDiffEntry
                .builder()
                .propertyName("createdAt")
                .first(this.deliveryInfoFirst.getCreatedAt())
                .last(this.deliveryInfoLast.getCreatedAt())
                .build();
        assertFalse(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getCreatedAt(), this.deliveryInfoLast.getCreatedAt());

        entry = DefaultDiffEntry
                .builder()
                .propertyName("updatedAt")
                .first(this.deliveryInfoFirst.getUpdatedAt())
                .last(this.deliveryInfoLast.getUpdatedAt())
                .build();
        assertTrue(valueChangeList.contains(entry));
        assertNotEquals(this.deliveryInfoFirst.getUpdatedAt(), this.deliveryInfoLast.getUpdatedAt());
    }
}
