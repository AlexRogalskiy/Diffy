package com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces;

/**
 * Interface describing a mechanism that converts the data type of IntermediateRepresentations of SerializedObjects for
 * Upcasters. Different upcasters may require different data type (e.g. {@code byte[]} or
 * {@code InputStream}), or may produce a different data type than they consume.
 *
 * @param <S> The expected source type
 * @param <T> The output type
 * @author Allard Buijze
 * @since 2.0
 */
public interface ContentTypeConverter<S, T> {

    /**
     * The expected type of input data.
     *
     * @return the expected data format in IntermediateRepresentation
     */
    Class<S> expectedSourceType();

    /**
     * The returned type of IntermediateRepresentation
     *
     * @return the output data format in IntermediateRepresentation
     */
    Class<T> targetType();

    /**
     * Converts the given object into another. Typically, these values are contained by instance.
     *
     * @param original the value to convert
     * @return the converted value
     */
    T convert(final S original);
}
