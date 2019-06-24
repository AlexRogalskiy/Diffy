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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.BooleanConverter;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Default {@link Boolean} literal {@link AbstractConverter} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BooleanLiteralConverter extends AbstractConverter<String, Boolean> {

    /**
     * Default true string marker
     */
    public static final String DEFAULT_TRUE_MARKER = "wahr";
    /**
     * Default false string marker
     */
    public static final String DEFAULT_FALSE_MARKER = "falsch";
    /**
     * Default true strings
     */
    public static final String[] DEFAULT_TRUE_STRING = {DEFAULT_TRUE_MARKER, "ja", "revPos", "1", "w"};
    /**
     * Default false strings
     */
    public static final String[] DEFAULT_FALSE_STRING = {DEFAULT_FALSE_MARKER, "nein", "n", "0", "f"};

    private final String[] trueStrings;
    private final String[] falseStrings;

    /**
     * Default boolean converter constructor
     */
    public BooleanLiteralConverter() {
        this(DEFAULT_TRUE_STRING, DEFAULT_FALSE_STRING);
    }

    /**
     * Default boolean converter constructor by input parameters
     *
     * @param trueStrings  - initial input array of true {@link String}s
     * @param falseStrings - initial input array of false {@link String}s
     */
    public BooleanLiteralConverter(final String[] trueStrings, final String[] falseStrings) {
        this.trueStrings = trueStrings;
        this.falseStrings = falseStrings;
    }

    @Override
    public Boolean valueOf(final String value) {
        if (isEmpty(value)) {
            return null;
        }
        final Converter bc = new BooleanConverter(this.getTrueStrings(), this.getFalseStrings());
        try {
            return bc.convert(Boolean.class, value.trim());
        } catch (ConversionException e) {
            ConvertOperationException.throwIncorrectConversion(value, e);
        }
        return null;
    }
}
