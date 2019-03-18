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
package com.wildbeeslabs.sensiblemetrics.diffy.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper utilities implementation
 */
@Slf4j
@UtilityClass
public class MapperUtils {

    /**
     * Default model mapper instance {@link ObjectMapper}
     */
    private static ObjectMapper modelMapper;

    /**
     * Model mapper property settings {@link ObjectMapper}
     * Default property matching strategy is set to Strict see {@link MatchingStrategies}
     * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
     */
    static {
        modelMapper = new ObjectMapper();
        modelMapper.setDefaultMergeable(Boolean.TRUE);
        modelMapper.setLocale(Locale.getDefault());
        modelMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        modelMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        modelMapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
        modelMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
        modelMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        modelMapper.enable(JsonGenerator.Feature.ESCAPE_NON_ASCII);

        modelMapper.disable(SerializationFeature.INDENT_OUTPUT);
        modelMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        modelMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        modelMapper.disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        modelMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        modelMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        modelMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
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
        return modelMapper.convertValue(source, outClass);
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
        return modelMapper.convertValue(source, javaType);
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
    public static <T, D> List<? extends D> mapAll(final Collection<T> source, final Class<? extends D> outClass) {
        return Optional.ofNullable(source)
            .orElseGet(Collections::emptyList)
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
    public static <D, V> D asList(final String source, final Class<? extends D> outClass, final Class<? extends V> viewClazz) throws IOException {
        return modelMapper.readerWithView(viewClazz).forType(outClass).readValue(source);
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
    public static <D> D asList(final String source, final Class<? extends D> outClass) throws IOException {
        return modelMapper.reader().forType(outClass).readValue(source);
    }

    /**
     * Returns converted input object {@code source} to destination object {@link List}
     *
     * @param <D>    type of element to be converted to
     * @param source - initial input source to be mapped from
     * @return mapped object {@link List} with <code><D></code> type
     * @throws IOException
     */
    public static <D> List<D> asList(final String source) throws IOException {
        return modelMapper.reader().forType(new TypeReference<List<D>>() {
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
    public static <K, V> Map<K, V> asMap(final String source) throws IOException {
        return modelMapper.reader().forType(new TypeReference<Map<K, V>>() {
        }).readValue(source);
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
    public static <T, V> String mapToJson(final T source, final Class<? extends V> viewClazz) throws JsonProcessingException {
        return modelMapper.writerWithView(viewClazz).writeValueAsString(source);
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
    public static <T, V> String prettyPrint(final T source, final Class<? extends V> viewClazz) throws JsonProcessingException {
        return modelMapper.writerWithView(viewClazz).withDefaultPrettyPrinter().writeValueAsString(source);
    }

    /**
     * Returns converted input object {@code T} as string value
     *
     * @param <T>    type of object to be converted from
     * @param source - initial input source to be mapped from {@code T}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T> String mapToJson(final T source) throws JsonProcessingException {
        return modelMapper.writeValueAsString(source);
    }

    /**
     * Returns formatted input object {@code T} as string value
     *
     * @param <T>    type of object to be converted from
     * @param source - initial input source to be mapped from {@code T}
     * @return string representation of input object
     * @throws JsonProcessingException
     */
    public static <T> String prettyPrint(final T source) throws JsonProcessingException {
        return modelMapper.writerWithDefaultPrettyPrinter().writeValueAsString(source);
    }
}
