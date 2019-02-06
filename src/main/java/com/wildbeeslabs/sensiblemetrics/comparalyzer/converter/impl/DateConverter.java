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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.converter.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Default date converter implementation {@link Date}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DateConverter extends AbstractConverter<String, Date> {

    /**
     * Default date format pattern
     */
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Initial date format pattern
     */
    private final String format;

    public DateConverter(final String format) {
        this.format = Objects.isNull(format) ? DEFAULT_DATE_FORMAT : format;
    }

    /**
     * Returns date value {@link Date} by input argument {@link String}
     *
     * @param value - initial argument value {@link String}
     * @return converted integer value {@link Date}
     */
    @Override
    public Date convert(final String value) {
        try {
            new SimpleDateFormat(getFormat()).parse(value);
        } catch (ParseException e) {
            log.error(String.format("ERROR: cannot format input string={%s} by format={%s}", value, getFormat()));
        }
        return null;
    }
}
