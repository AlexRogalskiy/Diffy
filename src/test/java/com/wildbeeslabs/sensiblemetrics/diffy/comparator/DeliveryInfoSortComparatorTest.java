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
package com.wildbeeslabs.sensiblemetrics.diffy.comparator;

import com.google.common.collect.ImmutableList;
import com.wildbeeslabs.sensiblemetrics.diffy.AbstractDeliveryInfoDiffTest;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator.DeliveryInfoSortComparatorDispatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator.DeliveryInfoSortOrderComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.factory.DeliveryInfoFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Delivery info sort comparator unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoSortComparatorTest extends AbstractDeliveryInfoDiffTest {

    /**
     * Default {@link DeliveryInfoSortComparatorDispatcher} instance
     */
    private DeliveryInfoSortComparatorDispatcher sortComparator;

    @Before
    public void setUp() {
        final DeliveryInfoSortOrderComparator sortOrderComparator = DeliveryInfoFactory.createDefaultSortOrderComparator();
        this.sortComparator = new DeliveryInfoSortComparatorDispatcher(sortOrderComparator);
    }

    @Test
    @DisplayName("Test sort delivery info collection by sort order comparator")
    public void test_deliveryInfo_by_sortComparator() {
        // given
        final SortManager sort = new SortManager(
            new SortManager.SortOrder(SortManager.SortDirection.ASC, "id"),
            new SortManager.SortOrder(SortManager.SortDirection.DESC, "gid"),
            new SortManager.SortOrder(SortManager.SortDirection.ASC, "status"),
            new SortManager.SortOrder(SortManager.SortDirection.ASC, "balance")
        );

        // when
        final Comparator<? super DeliveryInfo> comparator = getSortComparator().getComparator(sort);
        final List<DeliveryInfo> sorted = ImmutableList.sortedCopyOf(comparator, givenDeliveryInfoList());

        // then
        then(sorted).extracting(DeliveryInfo::getId).containsExactly(1l, 2l, 3l, 4l, 5l);
    }

    /**
     * Returns immutable collection {@link Collection} of {@link DeliveryInfo} objects
     *
     * @return collection {@link Collection} of {@link DeliveryInfo} objects
     */
    private Collection<DeliveryInfo> givenDeliveryInfoList() {
        return ImmutableList.of(
            createDeliveryInfo(3l, "test3", DeliveryInfo.DeliveryStatus.DELIVERED, 230.00),
            createDeliveryInfo(2l, "test2", DeliveryInfo.DeliveryStatus.PENDING, 23.12),
            createDeliveryInfo(1l, "test1", DeliveryInfo.DeliveryStatus.REJECTED, 243.3),
            createDeliveryInfo(5l, "test5", DeliveryInfo.DeliveryStatus.PENDING, 315.4),
            createDeliveryInfo(4l, "test4", DeliveryInfo.DeliveryStatus.DELIVERED, 31.4)
        );
    }

    /**
     * Returns new instance of {@link DeliveryInfo} by initial input parameters
     *
     * @param id      - initial input id value
     * @param gid     - initial input global id value
     * @param status  - initial input delivery info status {@link DeliveryInfo.DeliveryStatus}
     * @param balance - initial input balance value
     * @return new instance of {@link DeliveryInfo}
     */
    @NonNull
    private DeliveryInfo createDeliveryInfo(final Long id, final String gid, final DeliveryInfo.DeliveryStatus status, final double balance) {
        final DeliveryInfo deliveryInfo = new DeliveryInfo();
        deliveryInfo.setId(id);
        deliveryInfo.setGid(gid);
        deliveryInfo.setStatus(status);
        deliveryInfo.setBalance(balance);
        return deliveryInfo;
    }
}
