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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.DiffEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Default difference comparator implementation by input object instance
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
     * Returns new default difference entry {@link DefaultDiffEntry}
     *
     * @param first        - initial first argument to be compared
     * @param last         - initial last argument to be compared with
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
     * @param <S>
     * @param first - initial first argument to be compared
     * @param last  - initial last argument to be compared with
     * @return collection of difference entries {@link DiffEntry}
     */
    @Override
    public <S extends Iterable<? extends DiffEntry<?>>> S diffCompare(final T first, final T last) {
        final List<DiffEntry<?>> resultList = new ArrayList<>();
        getPropertySet().stream()
                .filter(property -> getPropertyMap().containsKey(property))
                .forEach(property -> {
                    try {
                        getPropertyMap().get(property).setAccessible(true);
                        final Object firstValue = getPropertyMap().get(property).get(first);
                        final Object lastValue = getPropertyMap().get(property).get(last);
                        if (0 != Objects.compare(firstValue, lastValue, getPropertyComparator(property))) {
                            resultList.add(createDiffEntry(firstValue, lastValue, property));
                        }
                    } catch (IllegalAccessException e) {
                        log.error(String.format("ERROR: cannot get value of field={%s}", property));
                    }
                });
        return (S) resultList;
    }
}
