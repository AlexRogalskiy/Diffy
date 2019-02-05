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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.factory;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.impl.DefaultDiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.comparator.DeliveryInfoDiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.model.DeliveryInfo;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;

/**
 * Default difference comparator factory implementation
 */
@Slf4j
@UtilityClass
public class DefaultDiffComparatorFactory {

    /**
     * Creates difference comparator {@link DiffComparator} by class instance {@link Class}
     *
     * @param <T>
     * @param clazz - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz) {
        return (E) new DefaultDiffComparator<>(clazz);
    }

    /**
     * Creates difference comparator {@link DiffComparator} by class instance {@link Class} with comparator instance {@link Comparator}
     *
     * @param <T>
     * @param clazz      - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param comparator - initial comparator instance {@link Comparator}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Comparator<? super T> comparator) {
        return (E) new DefaultDiffComparator<>(clazz, comparator);
    }

    /**
     * Creates delivery information difference comparator {@link DeliveryInfoDiffComparator}
     *
     * @param <E>
     * @return delivery information difference comparator {@link DeliveryInfoDiffComparator}
     */
    public static <E extends DiffComparator<DeliveryInfo>> E create() {
        return (E) new DeliveryInfoDiffComparator();
    }

    /**
     * Creates delivery information difference comparator {@link DeliveryInfoDiffComparator}
     *
     * @param <E>
     * @param comparator - initial comparator instance {@link Comparator}
     * @return delivery information difference comparator {@link DeliveryInfoDiffComparator}
     */
    public static <E extends DiffComparator<DeliveryInfo>> E create(final Comparator<? super DeliveryInfo> comparator) {
        return (E) new DeliveryInfoDiffComparator(comparator);
    }
}
