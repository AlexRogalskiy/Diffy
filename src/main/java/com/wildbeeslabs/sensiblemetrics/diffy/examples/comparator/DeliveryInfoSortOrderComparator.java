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

import com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl.DefaultSortOrderComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Comparator;
import java.util.Map;

/**
 * Custom delivery info {@link DeliveryInfo} sort order functional comparator implementation {@link DefaultSortOrderComparator}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeliveryInfoSortOrderComparator extends DefaultSortOrderComparator<DeliveryInfo> {

    /**
     * Default delivery info sort order comparator constructor with input comparator map {@link Map} and default comparator instance {@link Comparator}
     *
     * @param comparatorMap     - initial input comparator map {@link Map}
     * @param defaultComparator - initial input default comparator instance {@link Comparator}
     */
    public DeliveryInfoSortOrderComparator(final Map<String, Comparator<? super DeliveryInfo>> comparatorMap, final Comparator<? super DeliveryInfo> defaultComparator) {
        super(comparatorMap, defaultComparator);
    }
}
