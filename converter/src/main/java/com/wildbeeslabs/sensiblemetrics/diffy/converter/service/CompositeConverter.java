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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.listOf;

/**
 * Composite {@link AbstractConverter} implementation
 */
@SuppressWarnings("unchecked")
public class CompositeConverter extends AbstractConverter<Object, Object> {

    private final List<Converter<Object, Object>> converters;

    public CompositeConverter(final Converter<Object, Object>... converters) {
        this.converters = listOf(converters);
    }

    @Override
    public Object valueOf(final Object value) {
        for (final Converter<Object, Object> converter : this.converters) {
            if (converter.canConvert((Class<Object>) value.getClass())) {
                return converter.convert(value);
            }
        }
        return null;
    }

    @Override
    public boolean canConvert(final Class clazz) {
        for (final Converter<Object, Object> converter : this.converters) {
            if (converter.canConvert(clazz)) {
                return true;
            }
        }
        return false;
    }
}
