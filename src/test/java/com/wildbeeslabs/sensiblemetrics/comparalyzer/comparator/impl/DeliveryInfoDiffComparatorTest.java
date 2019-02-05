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
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.factory.DefaultDiffComparatorFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoDiffComparatorTest extends AbstractDeliveryInfoDiffComparatorTest {

    /**
     * Default delivery information entities
     */
    private DeliveryInfo deliveryInfoFirst;
    private DeliveryInfo deliveryInfoLast;

    @Before
    public void setUp() {
        this.deliveryInfoFirst = getDeliveryInfoUnit().val();
        this.deliveryInfoLast = getDeliveryInfoUnit().val();
    }

    @Test
    public void testCompareDifferentEntitiesWithAllProperties() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertThat(valueChangeList.size(), greaterThanOrEqualTo(0));
        assertThat(valueChangeList.size(), lessThanOrEqualTo(getAllFields(DeliveryInfo.class).length));

        assertEquals(valueChangeList.get(1).getPropertyName(), "description");
        assertEquals(valueChangeList.get(1).getFirst(), getDeliveryInfoFirst().getDescription());
        assertEquals(valueChangeList.get(1).getLast(), getDeliveryInfoLast().getDescription());
        assertNotEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());

        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getPropertyName(), "updatedAt");
        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getFirst(), getDeliveryInfoFirst().getUpdatedAt());
        assertEquals(valueChangeList.get(valueChangeList.size() - 1).getLast(), getDeliveryInfoLast().getUpdatedAt());
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());
    }

    @Test
    public void testCompareEqualEntitiesWithAllProperties() {
        final DiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoFirst());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertEquals(valueChangeList.size(), 0);
    }

    @Test
    public void testCompareDifferentEntitiesWithExcludedProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_COMPARATOR);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testCompareDifferentEntitiesWithIncludedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "description");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_COMPARATOR);
        diffComparator.setProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testDiffComparatorEntryWithExcludedProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_COMPARATOR);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testDiffComparatorEntryWithIncludedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class, DEFAULT_COMPARATOR);
        diffComparator.setProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testCompareDifferentEntitiesWithNonExistedExcludedProperty() {
        final List<String> excludedProperties = Arrays.asList("id", "country", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testCompareDifferentEntitiesWithIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "createdAt", "updatedAt", "description");
        final List<String> excludedProperties = Arrays.asList("createdAt", "updatedAt", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), CollectionUtils.subtract(includedProperties, excludedProperties).size());

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
    public void testCompareDifferentEntitiesWithNonIntersectedProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type");
        final List<String> excludedProperties = Arrays.asList("description", "createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.includeProperties(includedProperties);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), includedProperties.size());

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
    public void testCompareDifferentEntitiesWithIncludedDuplicateProperties() {
        final List<String> includedProperties = Arrays.asList("id", "type", "createdAt", "id", "type");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.setProperties(includedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

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
    public void testCompareDifferentEntitiesWithExcludedDuplicateProperties() {
        final List<String> excludedProperties = Arrays.asList("id", "type", "description", "type", "type", "id");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.excludeProperties(excludedProperties);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

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
    public void testCompareDifferentEntitiesAreNonEqual() {
        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
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
        getDeliveryInfoFirst().setDescription(null);
        getDeliveryInfoLast().setDescription(null);

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.setProperties(includedProperties);
        diffComparator.setComparator("_identCode", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

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
                .propertyName("updatedAt")
                .first(getDeliveryInfoFirst().getUpdatedAt())
                .last(getDeliveryInfoLast().getUpdatedAt())
                .build();
        assertNotEquals(getDeliveryInfoFirst().getUpdatedAt(), getDeliveryInfoLast().getUpdatedAt());
        assertFalse(valueChangeList.contains(entry));

        entry = DefaultDiffEntry
                .builder()
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
                .build();
        assertEquals(getDeliveryInfoFirst().getDescription(), getDeliveryInfoLast().getDescription());
        assertFalse(valueChangeList.contains(entry));
    }

    @Test
    public void testCompareDifferentEntitiesWithIncludedPropertiesAndEqualStringComparator() {
        final String defaultDeliveryDescription = "TEST_";
        final List<String> includedProperties = Arrays.asList("id", "type", "description", "createdAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.setProperties(includedProperties);
        getDeliveryInfoFirst().setDescription(defaultDeliveryDescription.concat(UUID.randomUUID().toString()));
        getDeliveryInfoLast().setDescription(defaultDeliveryDescription.concat(UUID.randomUUID().toString()));
        diffComparator.setComparator("description", (Comparator<String>) (o1, o2) -> o1.substring(0, 5).compareToIgnoreCase(o2.substring(0, 5)));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 3);

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
                .propertyName("description")
                .first(getDeliveryInfoFirst().getDescription())
                .last(getDeliveryInfoLast().getDescription())
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
    public void testCompareDifferentEntitiesWithIncludedPropertiesAndNonEqualDateComparator() {
        final List<String> includedProperties = Arrays.asList("createdAt", "updatedAt");

        final DefaultDiffComparator<DeliveryInfo> diffComparator = DefaultDiffComparatorFactory.create(DeliveryInfo.class);
        diffComparator.setProperties(includedProperties);
        diffComparator.setComparator("createdAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getHourOfDay()));
        diffComparator.setComparator("updatedAt", Comparator.comparingInt((Date d) -> LocalDateTime.fromDateFields(d).getDayOfMonth()));
        final Iterable<DefaultDiffEntry> iterable = diffComparator.diffCompare(getDeliveryInfoFirst(), getDeliveryInfoLast());
        assertNotNull(iterable);

        final List<DefaultDiffEntry> valueChangeList = Lists.newArrayList(iterable);
        assertThat(valueChangeList, not(empty()));
        assertEquals(valueChangeList.size(), 1);

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
}
