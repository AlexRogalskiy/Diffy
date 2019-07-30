package com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces;

/**
 * A factory for "ranged" converters that can convert objects from S to subtypes of R.
 *
 * <p>Implementations may additionally implement {@link ConditionalConverter}.
 */
@FunctionalInterface
public interface ConverterProvider<S, R> {

    /**
     * Get the converter to convert from S to target type T, where T is also an instance of R.
     *
     * @param <T>        the target type
     * @param targetType the target type to convert to
     * @return a converter from S to T
     */
    <T extends R> Converter<S, T> getConverter(final Class<T> targetType);
}
