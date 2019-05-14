package com.wildbeeslabs.sensiblemetrics.diffy.entry.iface;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Entry declaration
 *
 * @param <K> type of entry key
 * @param <V> type of entry value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface Entry<K, V> {

    /**
     * Returns entry key {@code T}
     *
     * @return entry key {@code T}
     */
    @Nullable
    K getKey();

    /**
     * Returns entry value {@code T}
     *
     * @return entry value {@code T}
     */
    @Nullable
    V getValue();

    /**
     * A collector to create a {@link Map} from a {@link Stream} of {@link Entry}s.
     *
     * @return
     */
    static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }
}
