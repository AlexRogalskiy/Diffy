package com.wildbeeslabs.sensiblemetrics.diffy.entry.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.entry.iface.Entry;
import lombok.*;

/**
 * Default {@link Entry} implementation
 *
 * @param <K> type of node key
 * @param <V> type of node value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefaultEntry<K, V> implements Entry<K, V> {

    /**
     * Default entry key {@code K}
     */
    private K key;
    /**
     * Default entry value {@code V}
     */
    private V value;

    /**
     * Returns {@link Entry} by input parameters
     *
     * @param <K>    type of node key
     * @param <V>    type of node value
     * @param first  - initial input node key {@code K}
     * @param second - initial input node value {@code V}
     * @return {@link Entry}
     */
    public static <K, V> Entry<K, V> of(final K first, final V second) {
        return new DefaultEntry<>(first, second);
    }
}
