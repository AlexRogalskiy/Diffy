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
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidOperationException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.listOf;

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
     * Custom mappings are added using {@link ObjectMapper#addMappings(PropertyMap)}
     */
    static {
        objectMapper = new ObjectMapper();
        //final SimpleModule module = new SimpleModule("UnQuote");
        //module.addSerializer(new UnQuotesSerializer());
        //objectMapper.registerModule(module);
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.registerModule(new KotlinModule());

        final SimpleModule module = new SimpleModule("JSONModule", new Version(2, 0, 0, null, null, null));
        module.addSerializer(Date.class, new DateSerializer());
        module.addDeserializer(Date.class, new DateDeserializers.DateDeserializer());
        objectMapper.registerModule(module);

        objectMapper.setDefaultMergeable(Boolean.TRUE);
        objectMapper.setLocale(Locale.getDefault());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //objectMapper.setDateFormat(new StdDateFormat());

        //final CarSerializer carSerializer = new CarSerializer(Car.class);
        //final SimpleModule module = new SimpleModule("CarSerializer", new Version(2, 1, 3, null, null, null));
        //module.addSerializer(Car.class, carSerializer);
        //objectMapper.registerModule(module);

        objectMapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
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
        objectMapper.disable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

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
        return listOf(source)
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
        ValidationUtils.notNull(fileName, "File name should not be null");
        ValidationUtils.notNull(clazz, "Class should not be null");
        ValidationUtils.notNull(fileName, "File name should not be null");
        ValidationUtils.notNull(clazz, "Class should not be null");

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            final URL url = MapperUtils.class.getClassLoader().getResource(fileName);
            return mapper.readValue(url, clazz);
        } catch (Exception e) {
            log.error(String.format("ERROR: cannot read properties from file = {%s}", fileName), e);
        }
        return null;
    }

    /**
     * Returns serialized value {@link String} from input parameters
     *
     * @param <T>     type of object to be converted to
     * @param value   - initial input {@code T} value to be serialized
     * @param pattern - initial input date pattern
     * @return serialized value {@code T} as string
     */
    public static <T> String toDateString(final T value, final String pattern) throws JsonProcessingException {
        ValidationUtils.isTrue(StringUtils.isNotBlank(pattern), "Pattern should not be null or empty");
        final SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        objectMapper.setDateFormat(dateFormat);
        return objectMapper.writeValueAsString(value);
    }

    /**
     * Returns serialized value {@code T} as bytes from input parameters
     *
     * @param <T>   type of object to be converted to
     * @param value - initial input {@code T} value to be serialized
     * @return serialized value {@code T} as byte array
     */
    public static <T> byte[] toBytesCbor(final T value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper(new CBORFactory());
        return objectMapper.writeValueAsBytes(value);
    }

    /**
     * Returns serialized value {@code T} as bytes from input parameters
     *
     * @param <T>   type of object to be converted to
     * @param value - initial input {@code T} value to be serialized
     * @return serialized value {@code T} as byte array
     */
    public static <T> byte[] toBytesByMessagePack(final T value) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        return objectMapper.writeValueAsBytes(value);
    }

    public static String parse(final Object obj) {
        final StringWriter writer = new StringWriter();
        try {
            final JsonGenerator generator = objectMapper.getFactory().createGenerator(writer);
            objectMapper.writeValue(generator, obj);
            writer.flush();
            writer.close();
            generator.close();
            return writer.getBuffer().toString();
        } catch (IOException e) {
            throw new InvalidFormatException(String.format("ERROR: cannot parse input value = {%s}", obj), e);
        }
    }

    public static <T> T parse(final String json, final Class<T> clazz) {
        try {
            return objectMapper.reader().forType(clazz).readValue(json);
        } catch (IOException e) {
            throw new InvalidFormatException(String.format("ERROR: cannot parse input value = {%s}", json), e);
        }
    }

    public static <T> T parse(final InputStream jsonStream, final Class<T> clazz, final String charset) {
        try {
            return objectMapper.reader().forType(clazz).readValue(new InputStreamReader(jsonStream, charset));
        } catch (IOException e) {
            throw new InvalidFormatException(String.format("ERROR: cannot parse input value = {%s} with charset = {%s}", jsonStream, charset), e);
        }
    }

    public static <T> List<T> fromFile(final String filename) {
        ValidationUtils.notNull(filename, "File name should not be null");
        try {
            return objectMapper.readValue(new File(filename), new TypeReference<List<Object>>() {
            });
        } catch (Exception e) {
            throw new InvalidOperationException(String.format("ERROR: cannot process file = {%s}", filename), e);
        }
    }

    public static <T> void toFile(final String filename, final List<T> values) {
        ValidationUtils.notNull(filename, "File name should not be null");
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), values);
        } catch (Exception e) {
            throw new InvalidOperationException(String.format("ERROR: cannot write to file = {%s}", filename), e);
        }
    }
}
