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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.utils;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.converter.Converter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Custom converter utilities implementation {@link Converter}
 *
 * @author Alexander Rogalskiy
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class ConverterUtils {

    /**
     * Returns converted value by converter instance {@link Converter}
     *
     * @param value     - initial argument value to be converted
     * @param converter - initial converter to process on {@link Converter}
     * @param <T>
     * @param <R>
     * @return converted value
     */
    public static <T, R> R convert(final T value, final Converter<T, R> converter) {
        Objects.requireNonNull(converter);
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
    public static Object convertFromString(final String value, final Class<?> toType, final String parserMethod) {
        Objects.requireNonNull(toType);
        final Method method;
        try {
            method = toType.getMethod(parserMethod, String.class);
            return method.invoke(toType, value);
        } catch (NoSuchMethodException e) {
            log.error(String.format("ERROR: cannot find method={%s} in type={%s},", parserMethod, toType));
        } catch (IllegalAccessException e) {
            log.error(String.format("ERROR: cannot access method={%s} in type={%s},", parserMethod, toType));
        } catch (InvocationTargetException e) {
            log.error(String.format("ERROR: cannot convert value=${%s} to type={%s},", value, toType));
        }
        return null;
    }
}
