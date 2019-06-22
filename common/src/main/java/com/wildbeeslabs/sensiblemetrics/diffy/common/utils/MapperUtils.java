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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper utilities implementation
 */
@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class MapperUtils {

    /**
     * Default model mapper instance {@link ObjectMapper}
     */
    private static ObjectMapper objectMapper;

    /**
     * Model mapper property settings {@link ObjectMapper}
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        objectMapper = new ObjectMapper();
        //final SimpleModule module = new SimpleModule("UnQuote");
        //module.addSerializer(new UnQuotesSerializer());
        //objectMapper.registerModule(module);

        objectMapper.setDefaultMergeable(Boolean.TRUE);
        objectMapper.setLocale(Locale.getDefault());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        objectMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        objectMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        objectMapper.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);
        objectMapper.disable(JsonGenerator.Feature.QUOTE_FIELD_NAMES);
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        objectMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);

        objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);

        objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    }

    /**
     * Returns {@link ObjectMapper}
     *
     * @return {@link ObjectMapper}
     */
    @NonNull
    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Converts input entity by initial output class instance {@link Class}
     *
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <T>      type of object to be converted from
     * @param <D>      type of objects in result list
     * @param source   - initial input object to be mapped {@code T}
     * @param outClass - initial input class to map by {@link Class}
     * @return mapped object of <code>outClass</code> type
     */
    public static <T, D> D map(final T source, final Class<? extends D> outClass) {
        return objectMapper.convertValue(source, outClass);
    }

    /**
     * Converts input object {@code source} to destination object {@code destination}
     *
     * @param <T>      type of object to be converted from
     * @param <D>      type of object to be converted to
     * @param source   - initial input source to be mapped from {@code T}
     * @param javaType - initial java type to be mapped to {@link JavaType}
     * @return mapped object with <code><D></code> type
     */
    public static <T, D> D map(final T source, final JavaType javaType) {
        return objectMapper.convertValue(source, javaType);
    }

    /**
     * Converts input collection of objects {@link Collection} by initial output class instance {@link Class}
     * <p>Note: outClass object must have default constructor with no arguments</p>
     *
     * @param <T>      type of object to be converted from
     * @param <D>      type of objects in result list
     * @param source   - initial input collection of objects {@code T} to be mapped {@link Collection}
     * @param outClass - initial input class to map by {@link Class}
     * @return list of mapped objects of <code>outClass</code> type
     */
    public static <T, D> List<? extends D> toList(final Collection<T> source, final Class<? extends D> outClass) {
        return ServiceUtils.listOf(source)
            .stream()
            .map(entity -> map(entity, outClass))
            .collect(Collectors.toList());
    }

    /**
     * Returns converted input object {@code source} to destination object {@code destination}
     *
     * @param <D>       type of object to be converted to
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@code T}
     * @param outClass  - initial input class to convert to {@link Class}
     * @param viewClazz - initial input view class to converted by {@link Class}
     * @return mapped object with <code><D></code> type
     * @throws IOException
     */
    public static <D, V> D map(final String source, final Class<? extends D> outClass, final Class<? extends V> viewClazz) throws IOException {
        return objectMapper.readerWithView(viewClazz).forType(outClass).readValue(source);
    }

    /**
     * Returns converted input object {@code source} to destination object {@code destination}
     *
     * @param <D>      type of object to be converted to
     * @param source   - initial input source to be mapped from {@code T}
     * @param outClass - initial input class to convert to {@link Class}
     * @return mapped object with <code><D></code> type
     * @throws IOException
     */
    public static <D> D map(final String source, final Class<? extends D> outClass) throws IOException {
        return objectMapper.reader().forType(outClass).readValue(source);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link List}
     *
     * @param <D>    type of element to be converted to
     * @param source - initial input source to be mapped from
     * @return mapped object {@link List} with <code><D></code> type
     * @throws IOException
     */
    public static <D> List<D> toList(final String source) throws IOException {
        return objectMapper.reader().forType(new TypeReference<List<D>>() {
        }).readValue(source);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link Map}
     *
     * @param <K>    type of key element
     * @param <V>    type of value element
     * @param source - initial input source to be mapped from
     * @return mapped object {@link Map} with <code><K></code> key type, <code><V></code> value type
     * @throws IOException
     */
    public static <K, V> Map<K, V> toMap(final String source) throws IOException {
        return objectMapper.reader().forType(new TypeReference<Map<K, V>>() {
        }).readValue(source);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link List}
     *
     * @param <T>    type of key element
     * @param source - initial input source to be mapped from
     * @param clazz  - initial input element {@link Class}
     * @return mapped object {@link Map} with <code><K></code> key type, <code><V></code> value type
     * @throws IOException
     */
    public static <T> List<T> toList(final String source, final Class<? extends T> clazz) throws IOException {
        return toCollection(source, List.class, clazz);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link Set}
     *
     * @param <T>    type of key element
     * @param source - initial input source to be mapped from
     * @param clazz  - initial input element {@link Class}
     * @return mapped object {@link Map} with <code><K></code> key type, <code><V></code> value type
     * @throws IOException
     */
    public static <T> Set<T> toSet(final String source, final Class<? extends T> clazz) throws IOException {
        return toCollection(source, Set.class, clazz);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link Set}
     *
     * @param <T>             type of key element
     * @param source          - initial input source to be mapped from
     * @param collectionClazz - initial input collection {@link Class}
     * @param elementClazz    - initial input element {@link Class}
     * @return mapped object {@link Map} with <code><K></code> key type, <code><V></code> value type
     * @throws IOException
     */
    public static <T, S extends Collection<T>> S toCollection(final String source, final Class<? extends S> collectionClazz, final Class<? extends T> elementClazz) throws IOException {
        final CollectionType collectionType = TypeFactory.defaultInstance().constructCollectionType(collectionClazz, elementClazz);
        return objectMapper.readerFor(collectionType).readValue(source);
    }

    /**
     * Returns converted input object {@code T} as string value
     *
     * @param <T>       type of object to be converted from
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@code T}
     * @param viewClazz - initial input view class to converted by {@link Class}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T, V> String toJson(final T source, final Class<? extends V> viewClazz) throws JsonProcessingException {
        return objectMapper.writerWithView(viewClazz).writeValueAsString(source);
    }

    /**
     * Returns formatted input object {@code T} as string value
     *
     * @param <T>       type of object to be converted from
     * @param <V>       type of object to be mapped by
     * @param source    - initial input source to be mapped from {@code T}
     * @param viewClazz - initial input view class to converted by {@link Class}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T, V> String toFormatString(final T source, final Class<? extends V> viewClazz) throws JsonProcessingException {
        return objectMapper.writerWithView(viewClazz).withDefaultPrettyPrinter().writeValueAsString(source);
    }

    /**
     * Returns converted input object {@code T} as string value
     *
     * @param <T>    type of object to be converted from
     * @param source - initial input source to be mapped from {@code T}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T> String toJson(final T source) throws JsonProcessingException {
        return objectMapper.writeValueAsString(source);
    }

    /**
     * Returns formatted input object {@code T} as string value
     *
     * @param <T>    type of object to be converted from
     * @param source - initial input source to be mapped from {@code T}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T> String toFormatString(final T source) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(source);
    }

    /**
     * Returns mapped {@code T} by input file name source {@link String} and {@link Class}
     *
     * @param <T>      type of object to be converted to
     * @param fileName - initial input file name source {@link String} to be mapped from
     * @param clazz    - initial input {@link Class} source to be mapped to {@code T}
     * @return mapped {@code T} by input source {@link String} and {@link Class}
     * @throws NullPointerException if filename is {@code null}
     * @throws NullPointerException if clazz is {@code null}
     */
    public static <T> T fromYaml(final String fileName, final Class<T> clazz) {
        Objects.requireNonNull(fileName, "File name should not be null");
        Objects.requireNonNull(clazz, "Class should not be null");
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            final URL url = MapperUtils.class.getClassLoader().getResource(fileName);
            return mapper.readValue(url, clazz);
        } catch (Exception e) {
            log.error(String.format("ERROR: cannot read properties from file = {%s}", fileName), e);
        }
        return null;
    }
}
