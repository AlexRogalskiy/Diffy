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

import com.google.common.collect.ImmutableList;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.streamOf;

@Data
@EqualsAndHashCode
@ToString
public class DefaultListEntry<K, V> implements Entry<K, List<V>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -3288692849302851343L;

    /**
     * The key
     */
    private final K first;

    /**
     * The map underlying the entry/iterator
     */
    private final List<V> last;

    /**
     * Constructs a new entry with the given Map and key.
     *
     * @param first the map
     * @param last  the key
     */
    public DefaultListEntry(final K first, final V last) {
        this(first, Collections.singletonList(last));
    }

    /**
     * Constructs a new entry with the given Map and key.
     *
     * @param first the map
     * @param last  the key
     */
    public DefaultListEntry(final K first, final List<V> last) {
        this.first = first;
        this.last = Optional.ofNullable(last).orElseGet(Collections::emptyList);
    }

    /**
     * Sets the value associated with the key direct onto the map.
     *
     * @param value the new value
     * @return the old value
     * @throws IllegalArgumentException if the value is set to this map entry
     */
    public boolean addValue(final V value) {
        return CollectionUtils.addIgnoreNull(this.last, value);
    }

    /**
     * Sets the value associated with the key direct onto the map.
     *
     * @param values the new value
     * @throws IllegalArgumentException if the value is set to this map entry
     */
    public void addValues(final Iterable<V> values) {
        ServiceUtils.streamOf(values).forEach(this::addValue);
    }

    /**
     * Returns entry last value {@link List}
     *
     * @return entry last value {@link List}
     */
    @Override
    public List<V> getLast() {
        return ImmutableList.<V>builder().addAll(this.last).build();
    }
}
