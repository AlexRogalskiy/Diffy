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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.iface;

import com.google.common.base.Function;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.streamOf;

/**
 * Converter interface declaration
 *
 * @param <T> type of input element to be converted from
 * @param <R> type of input element to be converted to
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@FunctionalInterface
public interface Converter<T, R> {

    /**
     * Default null {@link Converter}
     */
    Converter<?, ?> DEFAULT_NULL_CONVERTER = value -> null;
    /**
     * Default true {@link Converter}
     */
    Converter<?, Boolean> DEFAULT_TRUE_CONVERTER = value -> true;
    /**
     * Default false {@link Converter}
     */
    Converter<?, Boolean> DEFAULT_FALSE_CONVERTER = value -> false;
    /**
     * Default null {@link Converter}
     */
    Converter<?, String> DEFAULT_STRING_CONVERTER = Objects::toString;

    /**
     * Default exception {@link Converter}
     */
    Converter<?, ?> DEFAULT_EXCEPTION_CONVERTER = value -> {
        throw new ConvertOperationException();
    };

    /**
     * Returns converted value {@code R} by input argument value {@code T}
     *
     * @param value - initial input argument value {@code T}
     * @return converted value {@code R}
     */
    @Nullable
    R convert(final T value);

    /**
     * Returns composed {@link Converter} function that applies input {@link Converter}
     * function to its input, and then applies current {@link Converter} function to the result
     *
     * @param <V>    the type of input to be converted from
     * @param before - initial input {@link Converter} function to apply before current function is applied
     * @return composed {@link Converter} function
     * @throws NullPointerException if before is {@code null}
     */
    @NonNull
    default <V> Converter<V, R> compose(final Converter<? super V, ? extends T> before) {
        Objects.requireNonNull(before, "Converter should not be null!");
        return (final V v) -> convert(before.convert(v));
    }

    /**
     * Returns composed {@link Converter} function that first applies current {@link Converter} function to
     * its input, and then applies input {@link Converter} function to the result
     *
     * @param <V>   type of input element to be converted from
     * @param after - initial input {@link Converter} function to apply after current function is applied
     * @return composed {@link Converter} function
     * @throws NullPointerException if after is {@code null}
     */
    @NonNull
    default <V> Converter<T, V> andThen(final Converter<? super R, ? extends V> after) {
        Objects.requireNonNull(after, "Converter should not be null!");
        return (final T t) -> after.convert(convert(t));
    }

    /**
     * Returns {@link Converter} function that always returns its input argument
     *
     * @param <T> type of input element to be converted from
     * @return {@link Converter} function
     */
    @NonNull
    static <T> Converter<T, T> identity() {
        return t -> t;
    }

    /**
     * Returns {@link Validator} by input {@link Predicate}
     *
     * @param <T>       type of input element to be converted from
     * @param predicate - initial input {@link Predicate}
     * @return {@link Validator}
     */
    @NonNull
    static <T> Validator<T> toValidator(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "Predicate should not be null");
        return predicate::test;
    }

    /**
     * Returns {@link Predicate} by input {@link Validator}
     *
     * @param <T>     type of input element to be converted from
     * @param validator - initial input {@link Validator}
     * @return {@link Matcher}
     */
    @NonNull
    static <T> Predicate<T> toPredicate(final Validator<T> validator) {
        Objects.requireNonNull(validator, "Validator should not be null");
        return (value) -> {
            try {
                return validator.validate(value);
            } catch (Throwable throwable) {
                return false;
            }
        };
    }

    /**
     * Returns {@link Matcher} by input {@link Predicate}
     *
     * @param <T>       type of input element to be converted from
     * @param predicate - initial input {@link Predicate}
     * @return {@link Matcher}
     */
    @NonNull
    static <T> Matcher<T> toMatcher(final Predicate<T> predicate) {
        Objects.requireNonNull(predicate, "Predicate should not be null");
        return predicate::test;
    }

    /**
     * Returns {@link Predicate} by input {@link Matcher}
     *
     * @param <T>     type of input element to be converted from
     * @param matcher - initial input {@link Matcher}
     * @return {@link Matcher}
     */
    @NonNull
    static <T> Predicate<T> convert(final Matcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return matcher::matches;
    }

    /**
     * Returns {@link BiMatcher} by input {@link BiPredicate}
     *
     * @param <T>       type of input element to be converted from
     * @param predicate - initial input {@link BiPredicate}
     * @return {@link BiMatcher}
     */
    @NonNull
    static <T> BiMatcher<T> toBiMatcher(final BiPredicate<T, T> predicate) {
        Objects.requireNonNull(predicate, "Predicate should not be null");
        return predicate::test;
    }

    /**
     * Returns {@link BiPredicate} by input {@link BiMatcher}
     *
     * @param <T>     type of input element to be converted from
     * @param matcher - initial input {@link BiMatcher}
     * @return {@link BiPredicate}
     */
    @NonNull
    static <T> BiPredicate<T, T> toBiPredicate(final BiMatcher<T> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        return matcher::matches;
    }

    /**
     * Returns {@link Converter} by input {@link Function}
     *
     * @param <T>      type of input element to be converted from
     * @param function - initial input {@link Function}
     * @return {@link Converter}
     */
    @NonNull
    static <T, R> Converter<T, R> toConverter(final Function<T, R> function) {
        Objects.requireNonNull(function, "Function should not be null");
        return function::apply;
    }

    /**
     * Returns {@link Function} by input {@link Converter}
     *
     * @param <T>       type of input element to be converted from
     * @param converter - initial input {@link Converter}
     * @return {@link Function}
     */
    @NonNull
    static <T, R> Function<T, R> toFunction(final Converter<T, R> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return converter::convert;
    }

    /**
     * Returns converted input array of {@code T} items by input parameters
     *
     * @param <T>       type of input element to be converted from
     * @param <R>       type of input element to be converted to
     * @param values    - initial input array of {@code T} items to be converted from
     * @param converter - initial input {@link Converter}
     * @return array of {@code R} items
     */
    @NonNull
    static <T, R> R[] toArray(final T[] values, final Converter<? super T, R> converter) {
        return (R[]) streamOf(values).map(converter::convert).toArray();
    }

    /**
     * Returns converted input {@link List} of {@code T} items by input parameters
     *
     * @param <T>       type of input element to be converted from
     * @param <R>       type of input element to be converted to
     * @param values    - initial input {@link Iterable} of {@code T} items to be converted from
     * @param converter - initial input {@link Converter}
     * @return {@link List} of {@code R} items
     */
    @NonNull
    static <T, R> List<R> toList(final Iterable<? extends T> values, final Converter<? super T, R> converter) {
        return streamOf(values).map(converter::convert).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
}
