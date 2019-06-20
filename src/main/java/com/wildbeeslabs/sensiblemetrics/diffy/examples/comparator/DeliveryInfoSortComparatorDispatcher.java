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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.comparator;

import com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface.ComparatorDispatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ComparatorUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Custom delivery info sort implementation {@link Function}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DeliveryInfoSortComparatorDispatcher implements ComparatorDispatcher<DeliveryInfo> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -96283263770009545L;

    /**
     * Default {@link DeliveryInfoSortOrderComparator}
     */
    private final transient DeliveryInfoSortOrderComparator sortOrderComparator;

    /**
     * Returns {@link DeliveryInfo} {@link Comparator} by input {@link SortManager}
     *
     * @param sortManager - initial input {@link SortManager}
     * @return {@link DeliveryInfo} {@link Comparator}
     * @throws NullPointerException if the argument is {@code null}
     */
    @Override
    public Comparator<? super DeliveryInfo> getComparator(final SortManager sortManager) {
        ValidationUtils.notNull(sortManager, "Sort manager should not be null!");
        final List<Comparator<? super DeliveryInfo>> comparatorList = StreamSupport.stream(sortManager.spliterator(), false)
            .map(this.getSortOrderComparator())
            .collect(Collectors.toList());
        return new ComparatorUtils.DefaultMultiComparator<>(comparatorList);
    }
}
