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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.TimeProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Time {@link Validator} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TimeValidator extends AbstractCalendarValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -734806632242801553L;

    /**
     * Default {@link TimeValidator} instance
     */
    private static final TimeValidator VALIDATOR = new TimeValidator();

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the TimeValidator.
     */
    public static TimeValidator getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance with <i>short</i>
     * time style.
     */
    public TimeValidator() {
        this(true, DateFormat.SHORT);
    }

    /**
     * Construct an instance with the specified <i>strict</i>
     * and <i>time style</i> parameters.
     *
     * @param strict    <code>true</code> if strict
     *                  <code>Format</code> parsing should be used.
     * @param timeStyle the time style to use for Locale validation.
     */
    public TimeValidator(boolean strict, int timeStyle) {
        super(new TimeProcessor(strict, timeStyle));
    }

    /**
     * <p>Validate/convert a time using the default <code>Locale</code>
     * and <code>TimeZone</code>.
     *
     * @param value The value validation is being performed on.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code>
     * if invalid.
     */
    @Override
    public boolean validate(final String value) {
        return Objects.nonNull(this.getProcessor().processOrThrow(value));
    }

    /**
     * <p>Validate/convert a time using the specified <code>TimeZone</code>
     * and default <code>Locale</code>.
     *
     * @param value    The value validation is being performed on.
     * @param timeZone The Time Zone used to parse the time, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, timeZone));
    }

    /**
     * <p>Validate/convert a time using the specified <i>pattern</i> and
     * default <code>TimeZone</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public boolean validate(final String value, final String pattern) {
        return Objects.nonNull(this.getProcessor().process(value, pattern));
    }

    /**
     * <p>Validate/convert a time using the specified <i>pattern</i>
     * and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against.
     * @param timeZone The Time Zone used to parse the time, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, final String pattern, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, timeZone));
    }

    /**
     * <p>Validate/convert a time using the specified <code>Locale</code>
     * default <code>TimeZone</code>.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the time format, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public boolean validate(final String value, final Locale locale) {
        return Objects.nonNull(this.getProcessor().process(value, locale));
    }

    /**
     * <p>Validate/convert a time using the specified specified <code>Locale</code>
     * and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param locale   The locale to use for the time format, system default if null.
     * @param timeZone The Time Zone used to parse the time, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, final Locale locale, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, locale, timeZone));
    }

    /**
     * <p>Validate/convert a time using the specified pattern and <code>Locale</code>
     * and the default <code>TimeZone</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against, or the
     *                default for the <code>Locale</code> if <code>null</code>.
     * @param locale  The locale to use for the date format, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public boolean validate(final String value, final String pattern, final Locale locale) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, locale));
    }

    /**
     * <p>Validate/convert a time using the specified pattern, <code>Locale</code>
     * and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against, or the
     *                 default for the <code>Locale</code> if <code>null</code>.
     * @param locale   The locale to use for the date format, system default if null.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, final String pattern, final Locale locale, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, locale, timeZone));
    }
}
