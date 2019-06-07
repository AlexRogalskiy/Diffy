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
package com.wildbeeslabs.sensiblemetrics.diffy.utility;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.formatMessage;
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
     * Default completable {@link BiConsumer} action
     */
    private static final BiConsumer<? super Object, ? super Throwable> DEFAULT_COMPLETABLE_ACTION = (response, error) -> {
        try {
            if (Objects.isNull(error)) {
                log.debug("Received completable future response [from={}]", response);
            } else {
                log.debug("Canceled completable future request [response={}, error={}]", response, error);
            }
        } catch (RuntimeException | Error e) {
            log.error("ERROR: cannot process completable future request callback", e);
        }
    };

    public static <E extends Enum<E>> E toEnum(final String propValue, final Class<E> enumType) throws IllegalArgumentException {
        try {
            return Enum.valueOf(enumType, propValue);
        } catch (IllegalArgumentException e) {
            BadOperationException.throwError(String.format("ERROR: cannot process enum type: {%s} by class: {%s}", enumType, propValue), e);
        }
        return null;
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input collection of {@link CompletableFuture} and {@link Executor} instance
     *
     * @param <T>      type of {@link CompletableFuture} result
     * @param executor - initial input {@link Executor} instance
     * @param futures  - initial input collection of {@link CompletableFuture}
     * @throws NullPointerException if futures is {@code null}
     */
    public static <T> void getResultAsync(final Executor executor, final CompletableFuture<T>... futures) {
        Objects.requireNonNull(futures, "Array of futures should not be null");
        CompletableFuture.allOf(futures).whenCompleteAsync(DEFAULT_COMPLETABLE_ACTION, executor).join();
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input {@link CompletableFuture} and {@link Executor} instance
     *
     * @param <T>      type of {@link CompletableFuture} result
     * @param executor - initial input {@link Executor} instance
     * @param future   - initial input {@link CompletableFuture} instance
     * @return result {@code T} of {@link CompletableFuture}
     * @throws NullPointerException if future is {@code null}
     */
    public static <T> T getResultAsync(final Executor executor, final CompletableFuture<T> future) {
        Objects.requireNonNull(future, "Future should not be null");
        return future.whenCompleteAsync(DEFAULT_COMPLETABLE_ACTION, executor).join();
    }

    /**
     * Returns result {@code T} of {@link CompletableFuture} by initial input {@link CompletableFuture}
     *
     * @param <T>    type of {@link CompletableFuture} result
     * @param future - initial input {@link CompletableFuture} instance
     * @return result {@code T} of {@link CompletableFuture}
     * @throws NullPointerException if future is {@code null}
     */
    public static <T> T getResultAsync(final CompletableFuture<T> future) {
        Objects.requireNonNull(future, "Future should not be null");
        return getResultAsync(Executors.newSingleThreadExecutor(), future);
    }

    /**
     * Checks if {@link Collection} matches {@link Predicate}
     *
     * @param <T>
     * @param list      - initial input {@link Collection}
     * @param predicate - initial input {@link Predicate}
     * @return true - if {@link Collection} matches, false - otherwise
     * @throws NullPointerException if list is {@code null}
     * @throws NullPointerException if predicate is {@code null}
     */
    public static <T> boolean contains(final Collection<T> list, final Predicate<? super T> predicate) {
        Objects.requireNonNull(list, "List should not be null");
        Objects.requireNonNull(predicate, "Predicate should not be null");

        return listOf(list).stream().filter(predicate).findFirst().isPresent();
    }

    public static <T> Stream<T> asStream(final Iterator<T> sourceIterator) {
        return asStream(sourceIterator, false);
    }

    public static <T> Stream<T> asStream(final Iterator<T> sourceIterator, final boolean parallel) {
        Objects.requireNonNull(sourceIterator, "Source iterator should not be null");
        final Iterable<T> iterable = () -> sourceIterator;
        return StreamSupport.stream(iterable.spliterator(), parallel);
    }

    /**
     * Returns converted value by converter instance {@link Converter}
     *
     * @param <T>       type of input element to be converted from by operation
     * @param <R>       type of input element to be converted to by operation
     * @param value     - initial argument value to be converted
     * @param converter - initial converter to process on {@link Converter}
     * @return converted value
     * @throws NullPointerException if converter is {@code null}
     */
    @Nullable
    public static <T, R> R convert(final T value, final Converter<T, R> converter) {
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
     * @throws NullPointerException if toType is {@code null}
     */
    @Nullable
    public static Object convert(final String value, final Class<?> toType, final String parserMethod) {
        Objects.requireNonNull(toType, "Destination type should not be null");
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
     * @throws NullPointerException if predicate is {@code null}
     * @throws NullPointerException if reducer is {@code null}
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
     * @throws NullPointerException if predicate is {@code null}
     * @throws NullPointerException if reducer is {@code null}
     * @throws NullPointerException if supplier is {@code null}
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
     * @param <T>      type of input element to be converted from by operation
     * @param iterable - initial input {@link Iterable} collection of {@code T} values
     * @return non-nullable {@link Iterable} collection
     */
    @NonNull
    public static <T> List<T> listOf(final Iterable<T> iterable) {
        return StreamSupport.stream(Optional.ofNullable(iterable).orElseGet(Collections::emptyList).spliterator(), false).collect(Collectors.toList());
    }

    /**
     * Returns {@link Iterable} by input {@link Iterator}
     *
     * @param <T>      type of input element to be converted from by operation
     * @param iterator - initial input {@link Iterator}
     * @return {@link Iterable}
     */
    @NonNull
    public static <T> Iterable<T> iterableOf(final Iterator<T> iterator) {
        return () -> iterator;
    }
}
