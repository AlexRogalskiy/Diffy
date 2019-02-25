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
package com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;

/**
 * Default sort order functional comparator implementation {@link Function}
 *
 * @param <T> type of input element to be compared by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public abstract class DefaultSortOrderComparator<T> implements Function<SortManager.SortOrder, Comparator<? super T>> {

    /**
     * Default property by comparator map instance {@link Map}
     */
    private final Map<String, Comparator<? super T>> comparatorMap;
    /**
     * Default property comparator {@link Comparator}
     */
    private final Comparator<? super T> defaultComparator;

    /**
     * Returns comparator instance {@link Comparator} by input sort order value {@code SortManager.SortOrder}
     *
     * @param order - initial input sort order value {@code SortManager.SortOrder}
     * @return comparator instance {@link Comparator}
     */
    @Override
    public Comparator<? super T> apply(final SortManager.SortOrder order) {
        final Comparator<? super T> comparator = getComparatorMap().getOrDefault(order.getProperty(), getDefaultComparator());
        return order.getDirection().isDescending() ? comparator.reversed() : comparator;
    }
}
