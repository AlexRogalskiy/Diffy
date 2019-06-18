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
package com.wildbeeslabs.sensiblemetrics.diffy.processor;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.DateFormat;
import java.text.Format;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Time {@link AbstractCalendarProcessor} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TimeProcessor extends AbstractCalendarProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -734806632242801553L;

    /**
     * Default {@link TimeProcessor} instance
     */
    private static final TimeProcessor VALIDATOR = new TimeProcessor();

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the TimeValidator.
     */
    public static TimeProcessor getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance with <i>short</i>
     * time style.
     */
    public TimeProcessor() {
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
    public TimeProcessor(boolean strict, int timeStyle) {
        super(strict, -1, timeStyle);
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
    public Object processOrThrow(final String value) {
        return this.parse(value, null, null, null);
    }

    /**
     * <p>Validate/convert a time using the specified <code>TimeZone</code>
     * and default <code>Locale</code>.
     *
     * @param value    The value validation is being performed on.
     * @param timeZone The Time Zone used to parse the time, system default if null.
     * @return The parsed <code>Calendar</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(final String value, TimeZone timeZone) {
        return this.parse(value, null, null, timeZone);
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
    public Object process(final String value, final String pattern) {
        return this.parse(value, pattern, null, null);
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
    @Override
    public Object process(final String value, final String pattern, final TimeZone timeZone) {
        return this.parse(value, pattern, null, timeZone);
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
    public Object process(final String value, final Locale locale) {
        return this.parse(value, null, locale, null);
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
    @Override
    public Object process(final String value, final Locale locale, final TimeZone timeZone) {
        return this.parse(value, null, locale, timeZone);
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
    public Object process(final String value, final String pattern, final Locale locale) {
        return this.parse(value, pattern, locale, null);
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
    @Override
    public Object process(final String value, final String pattern, final Locale locale, final TimeZone timeZone) {
        return this.parse(value, pattern, locale, timeZone);
    }

    /**
     * <p>Compare Times (hour, minute, second and millisecond - not date).</p>
     *
     * @param value   The <code>Calendar</code> value to check.
     * @param compare The <code>Calendar</code> to compare the value to.
     * @return Zero if the hours are equal, -1 if first
     * time is less than the seconds and +1 if the first
     * time is greater than.
     */
    public int compareTime(final Calendar value, final Calendar compare) {
        return this.compareTime(value, compare, Calendar.MILLISECOND);
    }

    /**
     * <p>Compare Seconds (hours, minutes and seconds).</p>
     *
     * @param value   The <code>Calendar</code> value to check.
     * @param compare The <code>Calendar</code> to compare the value to.
     * @return Zero if the hours are equal, -1 if first
     * parameter's seconds are less than the seconds and +1 if the first
     * parameter's seconds are greater than.
     */
    public int compareSeconds(final Calendar value, final Calendar compare) {
        return this.compareTime(value, compare, Calendar.SECOND);
    }

    /**
     * <p>Compare Minutes (hours and minutes).</p>
     *
     * @param value   The <code>Calendar</code> value to check.
     * @param compare The <code>Calendar</code> to compare the value to.
     * @return Zero if the hours are equal, -1 if first
     * parameter's minutes are less than the seconds and +1 if the first
     * parameter's minutes are greater than.
     */
    public int compareMinutes(final Calendar value, final Calendar compare) {
        return this.compareTime(value, compare, Calendar.MINUTE);
    }

    /**
     * <p>Compare Hours.</p>
     *
     * @param value   The <code>Calendar</code> value to check.
     * @param compare The <code>Calendar</code> to compare the value to.
     * @return Zero if the hours are equal, -1 if first
     * parameter's hour is less than the seconds and +1 if the first
     * parameter's hour is greater than.
     */
    public int compareHours(final Calendar value, final Calendar compare) {
        return this.compareTime(value, compare, Calendar.HOUR_OF_DAY);
    }

    /**
     * <p>Convert the parsed <code>Date</code> to a <code>Calendar</code>.</p>
     *
     * @param value     The parsed <code>Date</code> object created.
     * @param formatter The Format used to parse the value with.
     * @return The parsed value converted to a <code>Calendar</code>.
     */
    @Override
    protected Object processParsedValue(final Object value, final Format formatter) {
        return ((DateFormat) formatter).getCalendar();
    }
}
