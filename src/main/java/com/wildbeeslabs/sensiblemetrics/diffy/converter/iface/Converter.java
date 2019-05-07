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

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Custom converter interface declaration
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
     * @throws NullPointerException if before is null
     */
    default <V> Converter<V, R> compose(final Converter<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return (final V v) -> convert(before.convert(v));
    }

    /**
     * Returns composed {@link Converter} function that first applies current {@link Converter} function to
     * its input, and then applies input {@link Converter} function to the result
     *
     * @param <V>   type of input element to be converted from
     * @param after - initial input {@link Converter} function to apply after current function is applied
     * @return composed {@link Converter} function
     * @throws NullPointerException if after is null
     */
    default <V> Converter<T, V> andThen(final Converter<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (final T t) -> after.convert(convert(t));
    }

    /**
     * Returns {@link Converter} function that always returns its input argument
     *
     * @param <T> type of input element to be converted from
     * @return {@link Converter} function
     */
    static <T> Converter<T, T> identity() {
        return t -> t;
    }
}
