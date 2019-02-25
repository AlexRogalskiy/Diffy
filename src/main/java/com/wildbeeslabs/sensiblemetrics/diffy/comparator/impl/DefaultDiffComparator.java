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
import com.wildbeeslabs.sensiblemetrics.diffy.entry.DiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.impl.DefaultDiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ReflectionUtils.setAccessible;
import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.sanitize;

/**
 * Difference comparator implementation by input class {@link Class} / comparator instance {@link Comparator}
 *
 * @param <T> type of input element to be compared by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultDiffComparator<T> extends AbstractDiffComparator<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2088063953605270171L;

    /**
     * Creates default difference comparator with initial class {@link Class}
     *
     * @param clazz - initial class instance {@link Class}
     */
    public DefaultDiffComparator(final Class<? extends T> clazz) {
        super(clazz);
    }

    /**
     * Creates default difference comparator with initial class {@link Class} and comparator instance {@link Comparator}
     *
     * @param clazz      - initial class instance {@link Class}
     * @param comparator - initial comparator instance {@link Comparator}
     */
    public DefaultDiffComparator(final Class<? extends T> clazz, final Comparator<? super T> comparator) {
        super(clazz, comparator);
    }

    /**
     * Returns comparator instance {@link Comparator} by property name {@link String}
     *
     * @param property - initial property name {@link String}
     * @return property comparator {@link Comparator}}
     */
    @Override
    @SuppressWarnings("unchecked")
    protected Comparator<?> getPropertyComparator(final String property) {
        final Class<?> fieldClazz = getPropertyMap().get(property).getType();
        if (Locale.class.isAssignableFrom(fieldClazz)) {
            return new ComparatorUtils.DefaultNullSafeLocaleComparator();
        } else if (Currency.class.isAssignableFrom(fieldClazz)) {
            return new ComparatorUtils.DefaultNullSafeCurrencyComparator();
        } else if (Class.class.isAssignableFrom(fieldClazz)) {
            return new ComparatorUtils.DefaultNullSafeClassComparator();
        } else if (URL.class.isAssignableFrom(fieldClazz)) {
            return new ComparatorUtils.DefaultNullSafeUrlComparator();
        }
        if (fieldClazz.isArray()) {
            if (Object.class.isAssignableFrom(fieldClazz.getComponentType())) {
                return new ComparatorUtils.DefaultNullSafeArrayComparator<>();
            }
        }
        return getPropertyComparatorMap().getOrDefault(sanitize(property), new ComparatorUtils.DefaultNullSafeObjectComparator());
    }

    /**
     * Creates new default difference entry {@link DefaultDiffEntry}
     *
     * @param first        - initial first argument to be compared {@link Object}
     * @param last         - initial last argument to be compared with {@link Object}
     * @param propertyName - initial property name {@link String}
     * @return default difference entry {@link DefaultDiffEntry}
     */
    protected DefaultDiffEntry createDiffEntry(final Object first, final Object last, final String propertyName) {
        return DefaultDiffEntry
            .builder()
            .first(first)
            .last(last)
            .propertyName(propertyName)
            .build();
    }

    /**
     * Returns iterable collection of difference entries {@link DiffEntry}
     *
     * @param <S>   type of difference entry collection
     * @param first - initial first argument to be compared {@code T}
     * @param last  - initial last argument to be compared with {@code T}
     * @return collection of difference entries {@link DiffEntry}
     */
    @Override
    public <S extends Iterable<? extends DiffEntry<?>>> S diffCompare(final T first, final T last) {
        final List<DiffEntry<?>> result = new ArrayList<>();
        getPropertySet()
            .stream()
            .filter(property -> getPropertyMap().containsKey(property))
            .forEach(property -> {
                try {
                    setAccessible(getPropertyMap().get(property));
                    final Object firstValue = getPropertyMap().get(property).get(first);
                    final Object lastValue = getPropertyMap().get(property).get(last);
                    if (!Objects.equals(compare(firstValue, lastValue, property), SortManager.SortDirection.EQ)) {
                        result.add(createDiffEntry(firstValue, lastValue, property));
                    }
                } catch (IllegalAccessException e) {
                    log.error(String.format("ERROR: cannot get value of property={%s}, message={%s}", property, e.getMessage()));
                }
            });
        return (S) result;
    }
}
