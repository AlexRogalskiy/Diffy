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

import com.google.common.collect.Sets;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.comparator.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ComparatorUtils;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.StringUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils.*;

/**
 * Abstract difference comparator implementation by input object instance
 *
 * @param <T> type of input element to be compared by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractDiffComparator<T> implements DiffComparator<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 412171656598855542L;

    /**
     * Default comparator instance {@link Comparator}
     */
    private final Comparator<? super T> comparator;
    /**
     * Default class instance {@link Class}
     */
    private final Class<? extends T> clazz;
    /**
     * Default property map {@link Map} by names {@link String} and values {@link Comparator}
     */
    @Getter(AccessLevel.PROTECTED)
    private final Map<String, Comparator<?>> propertyComparatorMap = new HashMap<>();
    /**
     * Default collection of properties {@link Map} to compare by with related property types {@link Class}
     */
    @Getter(AccessLevel.PROTECTED)
    private final Map<String, Field> propertyMap = new HashMap<>();
    /**
     * Default collection of properties {@link Set} to compare by
     */
    @Getter(AccessLevel.PROTECTED)
    private final Set<String> propertySet = new HashSet<>();

    /**
     * Creates difference comparator with initial class {@link Class}
     *
     * @param clazz - initial class instance {@link Class}
     */
    public AbstractDiffComparator(final Class<? extends T> clazz) {
        this(clazz, null);
    }

    /**
     * Creates difference comparator with initial class {@link Class} and comparator {@link Comparator}
     *
     * @param clazz      - initial class instance {@link Class}
     * @param comparator - initial comparator instance {@link Comparator}
     */
    public AbstractDiffComparator(final Class<? extends T> clazz, final Comparator<? super T> comparator) {
        this.clazz = Objects.requireNonNull(clazz);
        this.comparator = Objects.nonNull(comparator)
                ? comparator
                : ComparableComparator.getInstance();
        this.propertyMap.putAll(this.getFieldsMap(this.clazz));
        this.propertySet.addAll(this.propertyMap.keySet());
    }

    /**
     * Excludes properties from compare collection
     *
     * @param properties - collection of properties to be updated in exclude compare collection
     */
    public void excludeProperties(final Iterable<String> properties) {
        Optional.ofNullable(properties)
                .orElseGet(Collections::emptyList)
                .forEach(property -> excludeProperty(property));
    }

    /**
     * Exclude property from compare collection
     *
     * @param property - property to be added to exclude compare collection
     */
    public void excludeProperty(final String property) {
        if (Objects.nonNull(property)) {
            getPropertySet().remove(property);
        }
    }

    /**
     * Adds iterable collection of property names {@link Iterable} to compare collection
     *
     * @param properties - collection of properties to be added to compare collection
     */
    public void includeProperties(final Iterable<String> properties) {
        getPropertySet().clear();
        Optional.ofNullable(properties)
                .orElseGet(Collections::emptyList)
                .forEach(property -> includeProperty(property));
    }

    /**
     * Adds property name {@link String} to compare collection
     *
     * @param property - property to be added to compare collection
     */
    protected void includeProperty(final String property) {
        if (Objects.nonNull(property)) {
            getPropertySet().add(property);
        }
    }

    /**
     * Updates property name {@link String} by related comparator instance {@link Comparator}
     *
     * @param property   - initial property name {@link String}
     * @param comparator -  initial comparator instance {@link Comparator}
     */
    public void setComparator(final String property, final Comparator<?> comparator) {
        Objects.requireNonNull(property);
        log.debug(String.format("{%s}: storing property by name={%s}, comparator={%s}", getClass().getName(), property, comparator));
        this.getPropertyComparatorMap().put(StringUtils.sanitize(property), comparator);
    }

    /**
     * Updates properties {@link Map} by related comparator instances {@link Comparator}
     *
     * @param propertyMap - initial property map {@link Map} with names {@link String} and comparators {@link Comparator}
     */
    public void setComparators(final Map<String, Comparator<?>> propertyMap) {
        Optional.ofNullable(propertyMap)
                .orElseGet(Collections::emptyMap)
                .entrySet()
                .forEach(entry -> setComparator(entry.getKey(), entry.getValue()));
    }

    /**
     * Removes comparator from compare collection by property name {@link String}
     *
     * @param property - initial property name {@link String}
     */
    public void removeComparator(final String property) {
        log.debug(String.format("{%s}: removing comparator for property={%s}", getClass().getName(), property));
        this.getPropertyComparatorMap().remove(StringUtils.sanitize(property));
    }

    /**
     * Removes comparators from compare collection by iterable collection of property names {@link List}
     *
     * @param properties - initial collection of property names to be removed (@link List)
     */
    protected void removeComparators(final Iterable<String> properties) {
        Optional.ofNullable(properties)
                .orElseGet(Collections::emptyList)
                .forEach(property -> removeComparator(property));
    }

    /**
     * Returns numeric result of initial arguments comparison by property name {@link String}
     *
     * @param first      - initial first argument to be compared
     * @param last       - initial last argument to be compared with
     * @param property   - initial property name {@link String}
     * @param comparator - initial argument comparator instance {@link Comparator}
     * @return numeric result of initial arguments comparison
     */
    @SuppressWarnings("unchecked")
    protected int compare(final T first, final T last, final String property, final Comparator<? super Object> comparator) {
        if (Objects.isNull(property)) {
            return getComparator().compare(first, last);
        }
        final Object firstValue = getProperty(first, property);
        final Object lastValue = getProperty(first, property);
        return ComparatorUtils.compare(firstValue, lastValue, comparator);
    }

    /**
     * Returns comparator instance {@link Comparator} by property name {@link String}
     *
     * @param property - initial property name {@link String}
     * @return property comparator {@link Comparator}}
     */
    @SuppressWarnings("unchecked")
    protected <T> Comparator<? super T> getPropertyComparator(final String property) {
        return (Comparator<? super T>) getPropertyComparatorMap().getOrDefault(StringUtils.sanitize(property), ComparatorUtils.getObjectComparator(false));
    }

    /**
     * Returns collection of property names {@link List} by class instance {@link Class}
     *
     * @param clazz - initial class to reflect {@link Class}
     * @return list of field names {@link List}
     */
    protected List<String> getFieldsList(final Class<? extends T> clazz) {
        return getValidFields(getAllFields(clazz), false, false)
                .stream()
                .map(field -> field.getName())
                .collect(Collectors.toList());
    }

    /**
     * Returns collection of properties {@link Map} by names {@link String} and related types {@link Class} of input class instance {@link Class}
     *
     * @param clazz - initial class to reflect {@link Class}
     * @return map of fields {@link Map} by names {@link String} and types {@link Class}
     */
    protected Map<String, Class<?>> getFieldsClassMap(final Class<? extends T> clazz) {
        return getValidFields(getAllFields(clazz), false, false)
                .stream()
                .collect(Collectors.toMap(Field::getName, Field::getType));
    }

    /**
     * Returns collection of properties {@link Map} by names {@link String} and related fields {@link Field} of class instance {@link Class}
     *
     * @param clazz - initial class to reflect {@link Class}
     * @return map of fields {@link Map} by names {@link String} and types {@link Class}
     */
    protected Map<String, Field> getFieldsMap(final Class<? extends T> clazz) {
        return getValidFields(getAllFields(clazz), false, false)
                .stream()
                .collect(Collectors.toMap(field -> field.getName(), field -> field));
    }

    /**
     * Returns collection of unique property names {@link Set} by class instance {@link Class}
     *
     * @param clazz - initial argument class {@link Class}
     * @return set of field names {@link Set}
     */
    protected Set<String> getFieldsSet(final Class<? extends T> clazz) {
        return Sets.newHashSet(this.getFieldsList(clazz));
    }
}
