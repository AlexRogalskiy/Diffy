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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ConvertOperationException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;

/**
 * Abstract {@link Converter} implementation
 *
 * @param <T> type of input element to be converted from
 * @param <R> type of input element to be converted to
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractConverter<T, R> implements Converter<T, R> {

    /**
     * Returns converted value {@code R} by converter operation applied to input value {@code T}
     *
     * @param value - initial input argument value {@code T}
     * @return converted value {@code R}
     */
    @Override
    @Nullable
    public R convert(final T value) {
        try {
            return this.valueOf(value);
        } catch (RuntimeException e) {
            log.error("ERROR: cannot convert value = {}", value);
            ConvertOperationException.throwIncorrectConversion(value, e);
        }
        return null;
    }

    /**
     * Returns converted value {@code R} by initial input value {@code T}
     *
     * @param value - initial input argument value {@code T}
     * @return converted value {@code R}
     */
    protected abstract R valueOf(final T value);
}
