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
package com.wildbeeslabs.sensiblemetrics.diffy.common.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.doThrow;
import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.throwAsUnchecked;

/**
 * Throwing {@link Processor} interface declaration
 *
 * @param <T> type of consumed value
 * @param <F> type of produced value
 * @param <E> type of throwable value
 */
@FunctionalInterface
public interface ThrowingProcessor<F, T, E extends Throwable> extends Processor<F, T> {

    /**
     * Processes input value {@code F} to {@code T}
     *
     * @param value - initial input {@code F} value to be processed
     * @return processed value {@code T}
     */
    @Override
    @Nullable
    default T process(@Nullable final F value) {
        try {
            return this.processOrThrow(value);
        } catch (final Throwable t) {
            doThrow(t);
        }
        return null;
    }

    /**
     * Processes input value {@code F} to {@code T}, potentially throwing an exception
     *
     * @param value - initial input {@code F} value to be processed
     * @return processed value {@code T}
     * @throws Throwable if processor produces exception
     */
    @Nullable
    T processOrThrow(@Nullable final F value) throws E;

    /**
     * Returns {@link ThrowingProcessor} by input parameters
     *
     * @param <V>       type of converted value
     * @param converter - initial input {@link Converter} operator
     * @return {@link ThrowingProcessor}
     */
    @NonNull
    default <V, E extends Throwable> ThrowingProcessor<V, T, E> mapBefore(final Converter<V, F> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return (value) -> this.process(converter.convert(value));
    }

    /**
     * Returns {@link ThrowingProcessor} by input parameters
     *
     * @param <V>       type of converted value
     * @param converter - initial input {@link Converter} operator
     * @return {@link ThrowingProcessor}
     */
    @NonNull
    default <V, E extends Throwable> ThrowingProcessor<F, V, E> mapAfter(final Converter<T, V> converter) {
        Objects.requireNonNull(converter, "Converter should not be null");
        return (value) -> converter.convert(this.process(value));
    }

    /**
     * Returns {@link ThrowingProcessor} by input parameters
     *
     * @param <F>       type of consumed value
     * @param <T>       type of supplied value
     * @param <E>       type of throwable value
     * @param processor - initial input {@link ThrowingProcessor} operator
     * @return {@link ThrowingSupplier}
     */
    @NonNull
    static <F, T, E extends Throwable> ThrowingProcessor<F, T, E> get(final ThrowingProcessor<F, T, E> processor) {
        Objects.requireNonNull(processor, "Processor should not be null");
        return processor::process;
    }

    /**
     * Processes input value {@code F} by input {@link ThrowingProcessor}
     *
     * @param <T>       type of consumed value
     * @param <F>       type of produced value
     * @param <E>       type of throwable value
     * @param processor - initial input {@link ThrowingProcessor} operator
     * @param value     - initial input {@code F} value to be processed
     * @return processed value {@code T}
     * @throws IllegalArgumentException if processor produces exception
     */
    @Nullable
    static <F, T, E extends Throwable> T processOrThrow(final ThrowingProcessor<F, T, E> processor, final F value) {
        Objects.requireNonNull(processor, "Processor should not be null");
        try {
            return processor.processOrThrow(value);
        } catch (Throwable t) {
            throw new IllegalArgumentException(String.format("ERROR: cannot operate on processor = {%s}, message = {%s}", processor, t.getMessage()), t);
        }
    }

    /**
     * Wraps input {@link ThrowingProcessor} by input exception {@link Class}
     *
     * @param <F>       type of consumed value
     * @param <F>       type of supplied value
     * @param <E>       type of exception value
     * @param processor - initial input {@link ThrowingProcessor}
     * @param clazz     - initial input {@link Class}
     * @return {@link ThrowingProcessor}
     */
    @NonNull
    static <F, T, E extends Exception> ThrowingProcessor<F, T, E> wrapConsumer(final ThrowingProcessor<F, T, E> processor, final Class<E> clazz) {
        Objects.requireNonNull(processor, "Processor should not be null");
        Objects.requireNonNull(clazz, "Class should not be null");

        return (value) -> {
            try {
                return processor.processOrThrow(value);
            } catch (Throwable ex) {
                try {
                    throwAsUnchecked(clazz.cast(ex));
                } catch (ClassCastException eex) {
                    throw new RuntimeException(ex);
                }
            }
            return null;
        };
    }
}
