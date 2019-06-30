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

import com.google.common.base.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Default list {@link AbstractConverter} implementation
 *
 * @param <T> the element converter type
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultListConverter<T> extends AbstractConverter<String, List<T>> {

    private final ListConverter splitter;
    private final Converter<String, T> converter;

    /**
     * Constructs a new converter.
     *
     * @param splitter  to split value into list of arguments
     * @param converter to convert list of arguments to target element type
     */
    public DefaultListConverter(final ListConverter splitter, final Converter<String, T> converter) {
        ValidationUtils.notNull(splitter, "Splitter should not be null");
        ValidationUtils.notNull(converter, "Converter should not be null");

        this.splitter = splitter;
        this.converter = converter;
    }

    @Override
    public List<T> valueOf(final String value) {
        final List<T> result = new ArrayList<>();
        for (final String param : this.splitter.convert(value)) {
            result.add(this.converter.convert(param));
        }
        return result;
    }
}
