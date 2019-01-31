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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils.*;
import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.StringUtils.sanitize;

/**
 * Abstract difference comparator implementation
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractDiffComparator<T> implements DiffComparator<T> {

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
    private final Map<String, Comparator<?>> propertyComparatorMap = new HashMap<>();
    /**
     * Default collection of properties {@link Map} to compare by with related property types {@link Class}
     */
    private final Map<String, Field> propertyMap = new HashMap<>();
    /**
     * Default collection of properties {@link Set} to compare by
     */
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
        this.propertySet.addAll(this.getFieldsList(this.clazz));
        this.propertyMap.putAll(this.getFieldsMap(this.clazz));
    }

    /**
     * Excludes properties from compare collection
     *
     * @param propertyList - collection of properties to be updated in exclude compare collection
     */
    public void excludeProperties(final List<String> propertyList) {
        Optional.ofNullable(propertyList)
                .orElse(Collections.emptyList())
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
     * Adds properties to compare collection
     *
     * @param propertyList - collection of properties to be added to compare collection
     */
    public void includeProperties(final List<String> propertyList) {
        getPropertySet().clear();
        Optional.ofNullable(propertyList)
                .orElse(Collections.emptyList())
                .forEach(property -> includeProperty(property));
    }

    /**
     * Adds property to compare collection
     *
     * @param property - property to be added to compare collection
     */
    protected void includeProperty(final String property) {
        if (Objects.nonNull(property)) {
            getPropertySet().add(property);
        }
    }

    /**
     * Updates property with related comparator instance {@link Comparator}
     *
     * @param property   - initial property name {@link String}
     * @param comparator -  initial comparator instance {@link Comparator}
     */
    public void setComparator(final String property, final Comparator<?> comparator) {
        Objects.requireNonNull(property);
        log.debug(String.format("AbstractDiffComparator: storing property with name={%s}, comparator={%s}", property, comparator));
        this.getPropertyComparatorMap().put(sanitize(property), comparator);
    }

    /**
     * Updates properties {@link Map} with related comparator instances {@link Comparator}
     *
     * @param propertyMap - initial property map {@link Map} with names {@link String} and comparators {@link Comparator}
     */
    public void setComparators(final Map<String, Comparator<?>> propertyMap) {
        Optional.ofNullable(propertyMap)
                .orElse(Collections.emptyMap())
                .entrySet()
                .forEach(entry -> setComparator(entry.getKey(), entry.getValue()));
    }

    /**
     * Removes property comparator from compare collection
     *
     * @param property - initial property name {@link String}
     */
    public void removeComparator(final String property) {
        log.debug(String.format("AbstractDiffComparator: removing comparator for property={%s}", property));
        this.getPropertyComparatorMap().remove(sanitize(property));
    }

    /**
     * Removes property comparators {@link List} from compare collection
     *
     * @param propertyList - initial list of properties to be removed (@link List)
     */
    protected void removeComparators(final List<String> propertyList) {
        Optional.ofNullable(propertyList)
                .orElse(Collections.emptyList())
                .forEach(property -> removeComparator(property));
    }

    /**
     * Returns numeric result of initial entities comparison by property value
     *
     * @param first      - initial first argument to be compared {@link T}
     * @param last       - initial last argument to be compared to {@link T}
     * @param property   - initial property name {@link String}
     * @param comparator - initial argument comparator instance {@link Comparator}
     * @return numeric result of initial arguments comparison
     */
    protected int compare(final T first, final T last, final String property, final Comparator<? super Object> comparator) {
        if (Objects.isNull(property)) {
            return getComparator().compare(first, last);
        }
        final Object firstValue = getProperty(first, property);
        final Object lastValue = getProperty(first, property);
        return ComparatorUtils.compare(firstValue, lastValue, comparator);
    }

    /**
     * Returns property comparator instance {@link Comparator}
     *
     * @param property - initial property name {@link String}
     * @return property comparator {@link Comparator}}
     */
    protected <T> Comparator<? super T> getPropertyComparator(final String property) {
        return (Comparator<? super T>) getPropertyComparatorMap().getOrDefault(sanitize(property), ComparatorUtils.getDefaultComparator());
    }

    /**
     * Returns list of field names {@link List} by class instance {@link Class}
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
     * Returns map of fields {@link Map} with names {@link String} and types {@link Class} by class instance {@link Class}
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
     * Returns map of fields {@link Map} with names {@link String} and types {@link Class} by class instance {@link Class}
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
     * Returns set of field names {@link Set} by class instance {@link Class}
     *
     * @param clazz - initial argument class {@link Class}
     * @return set of field names {@link Set}
     */
    protected Set<String> getFieldsSet(final Class<? extends T> clazz) {
        return Sets.newHashSet(this.getFieldsList(clazz));
    }
}
