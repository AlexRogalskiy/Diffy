/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl;

import com.google.common.collect.ImmutableMap;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.streamOf;

/**
 * Default tied map {@link Entry} implementatino
 *
 * @param <T> type of entry key
 * @param <V> type of map value
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultMapEntry<T, K, V> implements Entry<T, Map<K, V>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -921490395669655850L;

    /**
     * Default {@code T} key
     */
    private final T first;

    /**
     * Default {@link Map} value
     */
    private final Map<K, V> last;

    /**
     * Constructs a new entry with the given Map and key.
     *
     * @param map the map
     * @param key the key
     */
    public DefaultMapEntry(final T first, final Map<K, V> last) {
        this.first = first;
        this.last = Optional.ofNullable(last).orElseGet(Collections::emptyMap);
    }

    /**
     * Gets the key of this entry
     *
     * @return the key
     */
    public T getKey() {
        return this.first;
    }

    /**
     * Gets the value of this entry direct from the map.
     *
     * @return the value
     */
    public V getValue(final K key) {
        return this.last.get(key);
    }

    /**
     * Sets the value associated with the key direct onto the map.
     *
     * @param value the new value
     * @return the old value
     * @throws IllegalArgumentException if the value is set to this map entry
     */
    public V addValue(final K key, final V value) {
        return this.last.put(key, value);
    }

    /**
     * Sets the value associated with the key direct onto the map.
     *
     * @param value the new value
     * @return the old value
     * @throws IllegalArgumentException if the value is set to this map entry
     */
    public V addValue(final Map.Entry<K, V> entry) {
        return this.last.put(entry.getKey(), entry.getValue());
    }

    /**
     * Sets the value associated with the key direct onto the map.
     *
     * @param values the new value
     * @return the old value
     * @throws IllegalArgumentException if the value is set to this map entry
     */
    public void addValues(final Iterable<Map.Entry<K, V>> values) {
        streamOf(values).forEach(this::addValue);
    }

    /**
     * Returns entry last value {@link Map}
     *
     * @return entry last value {@link Map}
     */
    @Override
    public Map<K, V> getLast() {
        return ImmutableMap.<K, V>builder().putAll(this.last).build();
    }
}
