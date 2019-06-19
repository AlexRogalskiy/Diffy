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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.CalendarProcessor;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * <p><b>Calendar Validation</b> and Conversion routines (<code>java.util.Calendar</code>).</p>
 *
 * <p>This validator provides a number of methods for validating/converting
 * a <code>String</code> date value to a <code>java.util.Calendar</code> using
 * <code>java.text.DateFormat</code> to parse either:</p>
 * <ul>
 * <li>using the default format for the default <code>Locale</code></li>
 * <li>using a specified pattern with the default <code>Locale</code></li>
 * <li>using the default format for a specified <code>Locale</code></li>
 * <li>using a specified pattern with a specified <code>Locale</code></li>
 * </ul>
 *
 * <p>For each of the above mechanisms, conversion method (i.e the
 * <code>validate</code> methods) implementations are provided which
 * either use the default <code>TimeZone</code> or allow the
 * <code>TimeZone</code> to be specified.</p>
 *
 * <p>Use one of the <code>isValid()</code> methods to just validate or
 * one of the <code>validate()</code> methods to validate and receive a
 * <i>converted</i> <code>Calendar</code> value.</p>
 *
 * <p>Implementations of the <code>validate()</code> method are provided
 * to create <code>Calendar</code> objects for different <i>time zones</i>
 * if the system default is not appropriate.</p>
 *
 * <p>Alternatively the CalendarValidator's <code>adjustToTimeZone()</code> method
 * can be used to adjust the <code>TimeZone</code> of the <code>Calendar</code>
 * object afterwards.</p>
 *
 * <p>Once a value has been successfully converted the following
 * methods can be used to perform various date comparison checks:</p>
 * <ul>
 * <li><code>compareDates()</code> compares the day, month and
 * year of two calendars, returning 0, -1 or +1 indicating
 * whether the first date is equal, before or after the second.</li>
 * <li><code>compareWeeks()</code> compares the week and
 * year of two calendars, returning 0, -1 or +1 indicating
 * whether the first week is equal, before or after the second.</li>
 * <li><code>compareMonths()</code> compares the month and
 * year of two calendars, returning 0, -1 or +1 indicating
 * whether the first month is equal, before or after the second.</li>
 * <li><code>compareQuarters()</code> compares the quarter and
 * year of two calendars, returning 0, -1 or +1 indicating
 * whether the first quarter is equal, before or after the second.</li>
 * <li><code>compareYears()</code> compares the
 * year of two calendars, returning 0, -1 or +1 indicating
 * whether the first year is equal, before or after the second.</li>
 * </ul>
 *
 * <p>So that the same mechanism used for parsing an <i>input</i> value
 * for validation can be used to format <i>output</i>, corresponding
 * <code>format()</code> methods are also provided. That is you can
 * format either:</p>
 * <ul>
 * <li>using a specified pattern</li>
 * <li>using the format for a specified <code>Locale</code></li>
 * <li>using the format for the <i>default</i> <code>Locale</code></li>
 * </ul>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.3.0
 */
public class CalendarValidator extends AbstractCalendarValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2016089791523065833L;

    /**
     * Default {@link CalendarValidator} instance
     */
    private static final CalendarValidator VALIDATOR = new CalendarValidator();

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the CalendarValidator.
     */
    public static CalendarValidator getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance with <i>short</i>
     * date style.
     */
    public CalendarValidator() {
        this(true, DateFormat.SHORT);
    }

    /**
     * Construct an instance with the specified <i>strict</i>
     * and <i>date style</i> parameters.
     *
     * @param strict    <code>true</code> if strict
     *                  <code>Format</code> parsing should be used.
     * @param dateStyle the date style to use for Locale validation.
     */
    public CalendarValidator(boolean strict, int dateStyle) {
        super(new CalendarProcessor(strict, dateStyle));
    }

    /**
     * <p>Validate/convert a <code>Calendar</code> using the default
     * <code>Locale</code> and <code>TimeZone</code>.
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
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * <code>TimeZone</code> and default <code>Locale</code>.
     *
     * @param value    The value validation is being performed on.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code>
     * if invalid.
     */
    public boolean validate(final String value, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, timeZone));
    }

    /**
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * <i>pattern</i> and default <code>TimeZone</code>.
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
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * <i>pattern</i> and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, final String pattern, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, timeZone));
    }

    /**
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * <code>Locale</code> and default <code>TimeZone</code>.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the date format, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public boolean validate(final String value, final Locale locale) {
        return Objects.nonNull(this.getProcessor().process(value, locale));
    }

    /**
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * <code>Locale</code> and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param locale   The locale to use for the date format, system default if null.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    public boolean validate(final String value, final Locale locale, final TimeZone timeZone) {
        return Objects.nonNull(this.getProcessor().process(value, locale, timeZone));
    }

    /**
     * <p>Validate/convert a <code>Calendar</code> using the specified pattern
     * and <code>Locale</code> and the default <code>TimeZone</code>.
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
     * <p>Validate/convert a <code>Calendar</code> using the specified
     * pattern, and <code>Locale</code> and <code>TimeZone</code>.
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
