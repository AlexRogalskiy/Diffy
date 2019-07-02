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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * Array {@link AbstractConverter} implementation
 */
public class ArrayConverter extends AbstractConverter<Object, Object> {

    private static final Map<Class, Converter<Object, Object>> CNV = new HashMap<>();

    @Override
    public Object valueOf(final Object value) {
        if (!CNV.containsKey(value.getClass())) {
            throw new ConvertOperationException(String.format("ERROR: cannot convert type: {%s} to: {%s}", value.getClass().getName(), Boolean.class.getName()));
        }
        return CNV.get(value.getClass()).convert(value);
    }

    @Override
    public boolean canConvert(final Class<Object> clazz) {
        return CNV.containsKey(clazz);
    }

    static {
        CNV.put(String[].class,
            (Converter) o -> {
                final Object[] old = (Object[]) o;
                final String[] n = new String[old.length];
                for (int i = 0; i < old.length; i++) {
                    n[i] = String.valueOf(old[i]);
                }
                return n;
            }
        );

        CNV.put(Integer[].class,
            (Converter) o -> {
                final Object[] old = (Object[]) o;
                final Integer[] n = new Integer[old.length];
                for (int i = 0; i < old.length; i++) {
                    n[i] = Integer.parseInt(String.valueOf(old[i]));
                }
                return n;
            });

        CNV.put(int[].class,
            (Converter) o -> {
                final Object[] old = (Object[]) o;
                final int[] n = new int[old.length];
                for (int i = 0; i < old.length; i++) {
                    n[i] = Integer.parseInt(String.valueOf(old[i]));
                }
                return n;
            });
    }
}
