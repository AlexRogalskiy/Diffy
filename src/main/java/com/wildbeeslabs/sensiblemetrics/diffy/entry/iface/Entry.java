package com.wildbeeslabs.sensiblemetrics.diffy.entry.iface;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Entry declaration
 *
 * @param <K> type of entry first value
 * @param <V> type of entry last value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface Entry<K, V> extends Serializable {

    /**
     * Returns entry first value {@code T}
     *
     * @return entry first value {@code T}
     */
    @Nullable
    K getFirst();

    /**
     * Returns entry last value {@code T}
     *
     * @return entry last value {@code T}
     */
    @Nullable
    V getLast();

    /**
     * A collector to create a {@link Map} from a {@link Stream} of {@link Entry}s.
     *
     * @param <K> type of entry first value
     * @param <V> type of entry last value
     * @return {@link Map} from a {@link Stream} of {@link Entry}s
     */
    static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toMap() {
        return Collectors.toMap(Entry::getFirst, Entry::getLast);
    }

    /**
     * Invokes the given {@link BiConsumer} if all given {@link Optional} are present.
     *
     * @param <K>      type of entry first value
     * @param <V>      type of entry last value
     * @param first    - initial input entry first value {@link Optional}
     * @param last     - initial input entry last value {@link Optional}
     * @param consumer - initial input {@link BiConsumer} operator
     */
    static <K, V> void ifAllPresent(final Optional<K> first, final Optional<V> last, final BiConsumer<K, V> consumer) {
        Objects.requireNonNull(first, "Optional entry first value should not be null!");
        Objects.requireNonNull(last, "Optional entry last value should not be null!");
        Objects.requireNonNull(consumer, "Binary consumer operator should not be null!");

        mapIfAllPresent(first, last, (f, l) -> {
            consumer.accept(f, l);
            return null;
        });
    }

    /**
     * Maps the values contained in the given {@link Optional} if both of them are present.
     *
     * @param <K>      type of entry first value
     * @param <V>      type of entry last value
     * @param <R>      type of function result
     * @param first    - initial input entry first value {@link Optional}
     * @param last     - initial input entry last value {@link Optional}
     * @param function - initial input {@link BiFunction} operator
     * @return {@link Optional} of {@link BiFunction} operator result {@code R}
     */
    static <K, V, R> Optional<R> mapIfAllPresent(final Optional<K> first, final Optional<V> last, final BiFunction<K, V, R> function) {
        Objects.requireNonNull(first, "Optional entry first value should not be null!");
        Objects.requireNonNull(last, "Optional entry last value should not be null!");
        Objects.requireNonNull(function, "Binary function operator should not be null!");

        return first.flatMap(f -> last.map(l -> function.apply(f, l)));
    }
}
