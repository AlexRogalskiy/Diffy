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
package com.wildbeeslabs.sensiblemetrics.diffy.factory;

import com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.impl.DefaultDiffComparator;
import lombok.experimental.UtilityClass;

import java.util.Comparator;

/**
 * Default difference comparator factory implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@UtilityClass
public class DefaultDiffComparatorFactory {

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class}
     *
     * @param <T>   type of input element to create comparator for
     * @param <E>   type of difference comparator instance
     * @param clazz - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz) {
        return (E) new DefaultDiffComparator<>(clazz);
    }

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class} and iterable collections of included/excluded properties {@link Iterable}
     *
     * @param <T>               type of input element to create comparator for
     * @param <E>               type of difference comparator instance
     * @param clazz             - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param includeProperties - initial iterable collection of included properties {@link Iterable}
     * @param excludeProperties - initial iterable collection of excluded properties {@link Iterable}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Iterable<String> includeProperties, final Iterable<String> excludeProperties) {
        final DefaultDiffComparator<T> defaultDiffComparator = new DefaultDiffComparator<>(clazz);
        defaultDiffComparator.includeProperties(includeProperties);
        defaultDiffComparator.excludeProperties(excludeProperties);
        return (E) defaultDiffComparator;
    }

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class} and iterable collection of excluded properties {@link Iterable}
     *
     * @param <T>               type of input element to create comparator for
     * @param <E>               type of difference comparator instance
     * @param clazz             - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param excludeProperties - initial iterable collection of excluded properties {@link Iterable}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Iterable<String> excludeProperties) {
        final DefaultDiffComparator<T> defaultDiffComparator = new DefaultDiffComparator<>(clazz);
        defaultDiffComparator.excludeProperties(excludeProperties);
        return (E) defaultDiffComparator;
    }

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class} with comparator instance {@link Comparator}
     *
     * @param <T>        type of input element to create comparator for
     * @param <E>        type of difference comparator instance
     * @param clazz      - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param comparator - initial comparator instance {@link Comparator}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Comparator<? super T> comparator) {
        return (E) new DefaultDiffComparator<>(clazz, comparator);
    }

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class}, comparator instance {@link Comparator}, iterable collections of included/excluded properties {@link Iterable}
     *
     * @param <T>               type of input element to create comparator for
     * @param <E>               type of difference comparator instance
     * @param clazz             - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param comparator        - initial comparator instance {@link Comparator}
     * @param includeProperties - initial iterable collection of included properties {@link Iterable}
     * @param excludeProperties - initial iterable collection of excluded properties {@link Iterable}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Comparator<? super T> comparator, final Iterable<String> includeProperties, final Iterable<String> excludeProperties) {
        final DefaultDiffComparator<T> defaultDiffComparator = new DefaultDiffComparator<>(clazz, comparator);
        defaultDiffComparator.includeProperties(includeProperties);
        defaultDiffComparator.excludeProperties(excludeProperties);
        return (E) defaultDiffComparator;
    }

    /**
     * Creates difference comparator instance {@link DiffComparator} by class instance {@link Class}, comparator instance {@link Comparator}, iterable collection of excluded properties {@link Iterable}
     *
     * @param <T>               type of input element to create comparator for
     * @param <E>               type of difference comparator instance
     * @param clazz             - initial class instance {@link Class} to initialize comparator {@link DiffComparator}
     * @param comparator        - initial comparator instance {@link Comparator}
     * @param excludeProperties - initial iterable collection of excluded properties {@link Iterable}
     * @return difference comparator {@link DiffComparator}
     */
    public static <T, E extends DiffComparator<T>> E create(final Class<? extends T> clazz, final Comparator<? super T> comparator, final Iterable<String> excludeProperties) {
        final DefaultDiffComparator<T> defaultDiffComparator = new DefaultDiffComparator<>(clazz, comparator);
        defaultDiffComparator.excludeProperties(excludeProperties);
        return (E) defaultDiffComparator;
    }
}
