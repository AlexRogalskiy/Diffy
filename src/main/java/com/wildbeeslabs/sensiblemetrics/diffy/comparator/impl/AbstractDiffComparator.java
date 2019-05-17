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

import com.google.common.collect.Sets;
import com.wildbeeslabs.sensiblemetrics.diffy.comparator.iface.DiffComparator;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.comparators.ComparableComparator;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ReflectionUtils.*;
import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.sanitize;

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
    private final transient Comparator<? super T> comparator;
    /**
     * Default class instance {@link Class}
     */
    private final Class<? extends T> clazz;
    /**
     * Default property map {@link Map} by names {@link String} and values {@link Comparator}
     */
    private final transient Map<String, Comparator<?>> propertyComparatorMap = new HashMap<>();
    /**
     * Default collection of properties {@link Map} to compare by with related property types {@link Class}
     */
    private final transient Map<String, Field> propertyMap = new HashMap<>();
    /**
     * Default collection of properties {@link Set} to compare by
     */
    private final Set<String> propertySet = new HashSet<>();

    /**
     * Creates difference comparator with initial input {@link Class}
     *
     * @param clazz - initial input {@link Class}
     */
    public AbstractDiffComparator(final Class<? extends T> clazz) {
        this(clazz, null);
    }

    /**
     * Creates difference comparator with initial input {@link Class} and {@link Comparator}
     *
     * @param clazz      - initial input {@link Class}
     * @param comparator - initial input {@link Comparator}
     */
    public AbstractDiffComparator(final Class<? extends T> clazz, final Comparator<? super T> comparator) {
        this.clazz = Objects.requireNonNull(clazz, "Class should not be null!");
        this.comparator = Objects.nonNull(comparator)
            ? comparator
            : ComparableComparator.getInstance();
        this.getPropertyMap().putAll(this.getFieldsMap(this.clazz));
        this.getPropertySet().addAll(this.getPropertyMap().keySet());
    }

    /**
     * Excludes {@link Iterable} collection of properties {@link String} from comparison
     *
     * @param properties - initial input {@link Iterable} collection of properties {@link String} to exclude from comparison
     */
    public void excludeProperties(final Iterable<String> properties) {
        Optional.ofNullable(properties)
            .orElseGet(Collections::emptyList)
            .forEach(this::excludeProperty);
    }

    /**
     * Exclude property {@link String} from comparison
     *
     * @param property - initial input property {@link String} to exclude from comparison
     */
    public void excludeProperty(final String property) {
        if (Objects.nonNull(property)) {
            this.getPropertySet().remove(property);
        }
    }

    /**
     * Includes {@link Iterable} collection of properties {@link String} in comparison
     *
     * @param properties - initial input {@link Iterable} collection of properties to include in comparison
     */
    public void includeProperties(final Iterable<String> properties) {
        this.getPropertySet().clear();
        Optional.ofNullable(properties)
            .orElseGet(Collections::emptyList)
            .forEach(this::includeProperty);
    }

    /**
     * Includes property {@link String} in comparison
     *
     * @param property - initial input property {@link String} to include in comparison
     */
    protected void includeProperty(final String property) {
        if (Objects.nonNull(property)) {
            this.getPropertySet().add(property);
        }
    }

    /**
     * Sets property {@link String} {@link Comparator}
     *
     * @param property   - initial input property {@link String}
     * @param comparator -  initial input property {@link Comparator}
     */
    public void setComparator(final String property, final Comparator<?> comparator) {
        Objects.requireNonNull(property, "Property should not be null!");
        log.debug("DEBUG <{}>: storing property by name={}, comparator={}", getClass().getName(), property, comparator);
        this.getPropertyComparatorMap().put(sanitize(property), comparator);
    }

    /**
     * Sets {@link Map} collection of properties {@link String} with related {@link Comparator}'s
     *
     * @param propertyMap - initial input {@link Map} collection of properties {@link String} with related {@link Comparator}'s
     */
    public void setComparators(final Map<String, Comparator<?>> propertyMap) {
        Optional.ofNullable(propertyMap)
            .orElseGet(Collections::emptyMap)
            .entrySet()
            .forEach(entry -> this.setComparator(entry.getKey(), entry.getValue()));
    }

    /**
     * Removes comparator from comparison by property {@link String}
     *
     * @param property - initial input property {@link String}
     */
    public void removeComparator(final String property) {
        log.debug("DEBUG: <{}>: removing comparator for property={}", getClass().getName(), property);
        this.getPropertyComparatorMap().remove(sanitize(property));
    }

    /**
     * Removes comparators from comparison by {@link Iterable} collection of properties {@link String}
     *
     * @param properties - initial input {@link Iterable} collection of properties {@link String}
     */
    protected void removeComparators(final Iterable<String> properties) {
        Optional.ofNullable(properties)
            .orElseGet(Collections::emptyList)
            .forEach(this::removeComparator);
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
            return this.getComparator().compare(first, last);
        }
        return ComparatorUtils.compare(getProperty(first, property), getProperty(first, property), comparator);
    }

    /**
     * Returns comparator instance {@link Comparator} by property name {@link String}
     *
     * @param property - initial property name {@link String}
     * @return property comparator {@link Comparator}}
     */
    @SuppressWarnings("unchecked")
    protected Comparator<?> getPropertyComparator(final String property) {
        return this.getPropertyComparatorMap().getOrDefault(sanitize(property), new ComparatorUtils.DefaultNullSafeObjectComparator());
    }

    /**
     * Returns result of comparison {@code SortManager.SortDirection} by property name {@link String}
     *
     * @param first    - initial input first value {@code T}
     * @param last     - initial input last value {@code T}
     * @param property - initial property name {@link String}
     * @return result of comparison {@code SortManager.SortDirection}
     */
    protected <T> SortManager.SortDirection compare(final T first, final T last, final String property) {
        return SortManager.SortDirection.getDirectionByCode(Objects.compare(first, last, (Comparator<? super T>) this.getPropertyComparator(property)));
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
            .map(Field::getName)
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
            .collect(Collectors.toMap(Field::getName, field -> field));
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
