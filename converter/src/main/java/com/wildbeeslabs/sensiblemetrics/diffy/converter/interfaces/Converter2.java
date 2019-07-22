package com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces;

/**
 * Interface describing a mechanism that can convert data from one to another type.
 *
 * @author Rene de Waele
 */
public interface Converter2 {

    /**
     * Indicates whether this converter is capable of converting the given {@code sourceType} to the {@code targetType}.
     *
     * @param sourceType The type of data to convert from
     * @param targetType The type of data to convert to
     * @return {@code true} if conversion is possible, {@code false} otherwise
     */
    boolean canConvert(final Class<?> sourceType, final Class<?> targetType);

    /**
     * Converts the given object into another.
     *
     * @param original   the value to convert
     * @param targetType The type of data to convert to
     * @param <T>        the target data type
     * @return the converted value
     */
    default <T> T convert(final Object original, final Class<T> targetType) {
        return convert(original, original.getClass(), targetType);
    }

    /**
     * Converts the given object into another using the source type to find the conversion path.
     *
     * @param original   the value to convert
     * @param sourceType the type of data to convert
     * @param targetType The type of data to convert to
     * @param <T>        the target data type
     * @return the converted value
     */
    <T> T convert(final Object original, final Class<?> sourceType, final Class<T> targetType);
}
