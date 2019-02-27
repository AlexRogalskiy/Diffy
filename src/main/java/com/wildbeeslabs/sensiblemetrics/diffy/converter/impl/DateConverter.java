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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.Converter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Default date converter implementation {@link Date}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public class DateConverter implements Converter<String, Date> {

    /**
     * Default date format pattern
     */
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Initial date format pattern
     */
    private final String format;

    /**
     * Default date converter constructor
     */
    public DateConverter() {
        this(DEFAULT_DATE_FORMAT);
    }

    /**
     * Default date converter constructor with initial input date format
     *
     * @param format - initial input date format
     */
    public DateConverter(@NonNull final String format) {
        this.format = format;
    }

    /**
     * Returns date value {@link Date} by input argument {@link String}
     *
     * @param value - initial argument value {@link String}
     * @return converted integer value {@link Date}
     */
    @Override
    @Nullable
    public Date convert(final String value) {
        try {
            new SimpleDateFormat(getFormat()).parse(value);
        } catch (ParseException e) {
            log.error(String.format("ERROR: cannot format input string={%s} by format={%s}", value, getFormat()));
        }
        return null;
    }
}
