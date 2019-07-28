package com.wildbeeslabs.sensiblemetrics.diffy.generator.interfaces;

import java.util.Optional;

/**
 * {@link Generator}s are fed instances of this interface on each generation
 * so that, if they choose, they can use these instances to influence the
 * results of a generation for a particular property parameter.
 */
public interface GenerationStatus {
    /**
     * @return an arbitrary "size" parameter; this value (probabilistically)
     * increases for every successful generation
     */
    int size();

    /**
     * @return how many attempts have been made to generate a value for a
     * property parameter
     */
    int attempts();

    /**
     * <p>Signals part of an attempt to generate a value.</p>
     *
     * <p>Mostly for use by junit-quickcheck itself; generators can call this
     * to convince the framework that it's doing work to generate a complex
     * value, but doing so is not strictly necessary.</p>
     */
    default void semiAttempt() {
    }

    /**
     * Associates the given value with the given key for the duration of the
     * generation of a value for a property parameter, or until overwritten.
     *
     * @param <T>   type of the value
     * @param key   a key to associate with a value
     * @param value the associated value
     * @return self, so that calls to this method can be chained
     */
    <T> GenerationStatus setValue(Key<T> key, T value);

    /**
     * Retrieves the value associated with the given key.
     *
     * @param <T> type of the value associated with the key
     * @param key key to look up
     * @return the (optional) associated value
     */
    <T> Optional<T> valueOf(Key<T> key);

    /**
     * Type-safe keys for {@link GenerationStatus#setValue(Key, Object)
     * setValue} and {@link GenerationStatus#valueOf(Key) valueOf}.
     *
     * @param <T> type of value that can be associated with the key
     */
    final class Key<T> {
        private final String name;
        private final Class<T> type;

        public Key(final String name, final Class<T> type) {
            if (name == null) {
                throw new NullPointerException("name must not be null");
            }
            if (type == null) {
                throw new NullPointerException("type must not be null");
            }
            this.name = name;
            this.type = type;
        }

        public T cast(final Object o) {
            return type.cast(o);
        }
    }
}
