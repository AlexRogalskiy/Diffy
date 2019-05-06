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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.factory;

import com.google.common.collect.ImmutableMap;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator.DeliveryInfoDiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator.DeliveryInfoSortComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator.DeliveryInfoSortOrderComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.util.Comparator;
import java.util.Map;
import java.util.Objects;

import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingDouble;

/**
 * Default delivery info {@link DeliveryInfo} factory implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class DeliveryInfoFactory {

    /**
     * Default delivery info comparator by "id" field
     */
    public static final Comparator<DeliveryInfo> SORT_BY_ID = comparing(DeliveryInfo::getId);
    /**
     * Default delivery info comparator by "gid" field
     */
    public static final Comparator<DeliveryInfo> SORT_BY_GID = comparing(DeliveryInfo::getGid, String.CASE_INSENSITIVE_ORDER);
    /**
     * Default delivery info comparator by "status" field
     */
    public static final Comparator<DeliveryInfo> SORT_BY_STATUS = comparing(DeliveryInfo::getStatus, ComparableComparator.getInstance());
    /**
     * Default delivery info comparator by "balance" field
     */
    public static final Comparator<DeliveryInfo> SORT_BY_BALANCE = comparingDouble(DeliveryInfo::getBalance);

    /**
     * Default delivery info {@link DeliveryInfo} comparator map {@link Map}
     */
    public static final Map<String, Comparator<? super DeliveryInfo>> comparatorMap = ImmutableMap.of(
        "id", SORT_BY_ID,
        "gid", SORT_BY_GID,
        "status", SORT_BY_STATUS,
        "balance", SORT_BY_BALANCE
    );

    /**
     * Creates delivery information difference comparator {@link DeliveryInfoDiffComparator}
     *
     * @param <E> type of entity to be compared by
     * @return delivery information difference comparator {@link DeliveryInfoDiffComparator}
     */
    public static <E extends DiffComparator<DeliveryInfo>> E createDiffComparator() {
        return (E) new DeliveryInfoDiffComparator();
    }

    /**
     * Creates delivery information difference comparator {@link DeliveryInfoDiffComparator}
     *
     * @param <E>        type of entity to be compared by
     * @param comparator - initial comparator instance {@link Comparator}
     * @return delivery information difference comparator {@link DeliveryInfoDiffComparator}
     */
    public static <E extends DiffComparator<DeliveryInfo>> E createDiffComparator(final Comparator<? super DeliveryInfo> comparator) {
        return (E) new DeliveryInfoDiffComparator(comparator);
    }

    /**
     * Returns default delivery info sort order comparator {@link DeliveryInfoSortOrderComparator}
     *
     * @return delivery info sort order comparator {@link DeliveryInfoSortOrderComparator}
     */
    public static DeliveryInfoSortOrderComparator createDefaultSortOrderComparator() {
        return createSortOrderComparator(comparatorMap, SORT_BY_ID);
    }

    /**
     * Returns delivery info sort order comparator {@link DeliveryInfoSortOrderComparator} by input comparator map {@link Map} and default comparator {@link Comparator}
     *
     * @param comparatorMap - initial input comparator map {@link Map}
     * @param comparator    - initial input default comparator {@link Comparator}
     * @return delivery info sort order comparator {@link DeliveryInfoSortOrderComparator}
     */
    public static DeliveryInfoSortOrderComparator createSortOrderComparator(final Map<String, Comparator<? super DeliveryInfo>> comparatorMap, final Comparator<? super DeliveryInfo> comparator) {
        Objects.requireNonNull(comparatorMap);
        Objects.requireNonNull(comparator);
        return new DeliveryInfoSortOrderComparator(comparatorMap, comparator);
    }

    /**
     * Returns delivery info sort comparator {@link DeliveryInfoSortComparator} by input sort order comparator {@link DeliveryInfoSortOrderComparator}
     *
     * @param sortOrderComparator - initial input sort order comparator {@link DeliveryInfoSortOrderComparator}
     * @return delivery info sort comparator {@link DeliveryInfoSortComparator}
     */
    public static DeliveryInfoSortComparator createSortOrder(final DeliveryInfoSortOrderComparator sortOrderComparator) {
        Objects.requireNonNull(sortOrderComparator);
        return new DeliveryInfoSortComparator(sortOrderComparator);
    }
}
