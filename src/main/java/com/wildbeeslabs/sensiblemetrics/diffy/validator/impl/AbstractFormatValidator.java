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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * <p>Abstract class for <i>Format</i> based Validation.</p>
 *
 * <p>This is a <i>base</i> class for building Date and Number
 * Validators using format parsing.</p>
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractFormatValidator<T> implements Validator<T>, Serializable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8898033189508711830L;

    /**
     * Default strict binary flag
     */
    private final boolean strict;

    /**
     * Construct an instance with the specified strict setting.
     *
     * @param strict <code>true</code> if strict
     *               <code>Format</code> parsing should be used.
     */
    public AbstractFormatValidator(boolean strict) {
        this.strict = strict;
    }

    /**
     * <p>Validate using the default <code>Locale</code>.
     *
     * @param value The value validation is being performed on.
     * @return <code>true</code> if the value is valid.
     */
    @Override
    public boolean validate(final T value) {
        return this.validate(value, null, null);
    }

    /**
     * <p>Validate using the specified <i>pattern</i>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against.
     * @return <code>true</code> if the value is valid.
     */
    public boolean validate(final T value, final String pattern) {
        return this.validate(value, pattern, null);
    }

    /**
     * <p>Validate using the specified <code>Locale</code>.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the Format, defaults to the default
     * @return <code>true</code> if the value is valid.
     */
    public boolean validate(final T value, final Locale locale) {
        return this.validate(value, null, locale);
    }

    /**
     * <p>Validate using the specified pattern and/or <code>Locale</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to format the value.
     * @param locale  The locale to use for the Format, defaults to the default
     * @return <code>true</code> if the value is valid.
     */
    public abstract boolean validate(final T value, final String pattern, final Locale locale);

    /**
     * <p>Format an object into a <code>String</code> using
     * the default Locale.</p>
     *
     * @param value The value validation is being performed on.
     * @return The value formatted as a <code>String</code>.
     */
    public String format(final Object value) {
        return this.format(value, null, null);
    }

    /**
     * <p>Format an object into a <code>String</code> using
     * the specified pattern.</p>
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to format the value.
     * @return The value formatted as a <code>String</code>.
     */
    public String format(final Object value, final String pattern) {
        return this.format(value, pattern, null);
    }

    /**
     * <p>Format an object into a <code>String</code> using
     * the specified Locale.</p>
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the Format.
     * @return The value formatted as a <code>String</code>.
     */
    public String format(final Object value, final Locale locale) {
        return this.format(value, null, locale);
    }

    /**
     * <p>Format an object using the specified pattern and/or
     * <code>Locale</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to format the value.
     * @param locale  The locale to use for the Format.
     * @return The value formatted as a <code>String</code>.
     */
    public String format(final Object value, final String pattern, final Locale locale) {
        final Format formatter = getFormat(pattern, locale);
        return this.format(value, formatter);
    }

    /**
     * <p>Format a value with the specified <code>Format</code>.</p>
     *
     * @param value     The value to be formatted.
     * @param formatter The Format to use.
     * @return The formatted value.
     */
    protected String format(final Object value, final Format formatter) {
        return formatter.format(value);
    }

    /**
     * <p>Parse the value with the specified <code>Format</code>.</p>
     *
     * @param value     The value to be parsed.
     * @param formatter The Format to parse the value with.
     * @return The parsed value if valid or <code>null</code> if invalid.
     */
    protected Object parse(final String value, final Format formatter) {
        final ParsePosition pos = new ParsePosition(0);
        Object parsedValue = formatter.parseObject(value, pos);
        if (pos.getErrorIndex() > -1) {
            return null;
        }
        if (isStrict() && pos.getIndex() < value.length()) {
            return null;
        }
        if (parsedValue != null) {
            parsedValue = this.processParsedValue((T) parsedValue, formatter);
        }
        return parsedValue;
    }

    /**
     * <p>Process the parsed value, performing any further validation
     * and type conversion required.</p>
     *
     * @param value     The parsed object created.
     * @param formatter The Format used to parse the value with.
     * @return The parsed value converted to the appropriate type
     * if valid or <code>null</code> if invalid.
     */
    protected abstract Object processParsedValue(final Object value, final Format formatter);

    /**
     * <p>Returns a <code>Format</code> for the specified <i>pattern</i>
     * and/or <code>Locale</code>.</p>
     *
     * @param pattern The pattern used to validate the value against or
     *                <code>null</code> to use the default for the <code>Locale</code>.
     * @param locale  The locale to use for the currency format, system default if null.
     * @return The <code>NumberFormat</code> to created.
     */
    protected abstract Format getFormat(final String pattern, final Locale locale);
}
