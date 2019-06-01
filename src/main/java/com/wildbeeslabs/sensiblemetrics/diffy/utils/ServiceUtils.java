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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Service utilities implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ServiceUtils {

    /**
     * Returns converted value by converter instance {@link Converter}
     *
     * @param <T>       type of input element to be converted from by operation
     * @param <R>       type of input element to be converted to by operation
     * @param value     - initial argument value to be converted
     * @param converter - initial converter to process on {@link Converter}
     * @return converted value
     */
    @Nullable
    public static <T, R> R convert(final T value, @NonNull final Converter<T, R> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return converter.convert(value);
    }

    /**
     * Returns converted from string value to type {@link Class} by method name {@link String}
     *
     * @param value        - initial argument value to be converted {@link String}
     * @param toType       - initial type to be converted to {@link Converter}
     * @param parserMethod - initial method name to process the conversion {@link String}
     * @return converted value {@link Object}
     */
    @Nullable
    public static Object convert(final String value, @NonNull final Class<?> toType, final String parserMethod) {
        try {
            final Method method = toType.getMethod(parserMethod, String.class);
            return method.invoke(toType, value);
        } catch (NoSuchMethodException e) {
            log.error(formatMessage("ERROR: cannot find method={%s} in type={%s},", parserMethod, toType));
        } catch (IllegalAccessException e) {
            log.error(formatMessage("ERROR: cannot access method={%s} in type={%s},", parserMethod, toType));
        } catch (InvocationTargetException e) {
            log.error(formatMessage("ERROR: cannot convert value={%s} to type={%s},", value, toType));
        }
        return null;
    }

    /**
     * Returns {@link Optional} of {@code T} by input parameters
     *
     * @param <T>       type of input element to be converted from by operation
     * @param predicate - initial input {@link Predicate}
     * @param reducer   - initial input {@link BinaryOperator}
     * @param values    - initial input collection of {@code T}
     * @return {@link Optional} of {@code T}
     */
    @NonNull
    public static <T> Optional<T> reduce(final T[] values, final Predicate<T> predicate, final BinaryOperator<T> reducer) {
        Objects.requireNonNull(predicate, "Predicate should not be null");
        Objects.requireNonNull(reducer, "Reducer should not be null");

        return streamOf(values).filter(predicate).reduce(reducer);
    }

    /**
     * Returns {@link Optional} of {@code T} by input parameters
     *
     * @param <T>       type of input element to be converted from by operation
     * @param predicate - initial input {@link Predicate}
     * @param reducer   - initial input {@link BinaryOperator}
     * @param values    - initial input collection of {@code T}
     * @return {@link Optional} of {@code T}
     */
    @NonNull
    public static <T, K extends Throwable> T reduceOrThrow(final T[] values, final Predicate<T> predicate, final BinaryOperator<T> reducer, final Supplier<? extends K> supplier) {
        Objects.requireNonNull(predicate, "Predicate should not be null");
        Objects.requireNonNull(reducer, "Reducer should not be null");
        Objects.requireNonNull(supplier, "Supplier should not be null");

        try {
            return reduce(values, predicate, reducer).orElseThrow(supplier);
        } catch (Throwable k) {
            throw new InvalidParameterException(String.format("ERROR: cannot operate reducer on values = {%s}", join(values, "|")), k);
        }
    }

    /**
     * Returns non-nullable {@link Stream} of {@code T} by input collection of {@code T} values
     *
     * @param <T>    type of input element to be converted from by operation
     * @param values - initial input collection of {@code T} values
     * @return non-nullable {@link Stream} of {@code T}
     */
    @NonNull
    public static <T> Stream<T> streamOf(final T... values) {
        return Arrays.stream(Optional.ofNullable(values).orElseGet(() -> (T[]) new Objects[]{}));
    }

    /**
     * Returns non-nullable {@link Iterable} collection from input {@link Iterable} collection of values {@code T}
     *
     * @param <T>    type of input element to be converted from by operation
     * @param values - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Iterable} collection
     */
    @NonNull
    public static <T> Iterable<T> iterableOf(final Iterable<T> values) {
        return Optional.ofNullable(values).orElseGet(Collections::emptyList);
    }
}
