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

import com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface.SortComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils;
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
public class DeliveryInfoSortComparator implements SortComparator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -96283263770009545L;

    /**
     * Default delivery info sort order functional comparator {@link Function}
     */
    private final transient DeliveryInfoSortOrderComparator sortOrderComparator;

    public Comparator<? super DeliveryInfo> getComparator(final SortManager sortManager) {
        Objects.requireNonNull(sortManager);
        final List<Comparator<? super DeliveryInfo>> comparatorList = StreamSupport.stream(sortManager.spliterator(), false)
            .map(getSortOrderComparator())
            .collect(Collectors.toList());
        return new ComparatorUtils.DefaultMultiComparator<>(comparatorList);
    }
}
