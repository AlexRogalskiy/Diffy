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
import java.util.*;

/**
 * <p><b>Date Validation</b> and Conversion routines (<code>java.util.Date</code>).</p>
 *
 * <p>This validator provides a number of methods for validating/converting
 * a <code>String</code> date value to a <code>java.util.Date</code> using
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
 * <i>converted</i> <code>Date</code> value.</p>
 *
 * <p>Implementations of the <code>validate()</code> method are provided
 * to create <code>Date</code> objects for different <i>time zones</i>
 * if the system default is not appropriate.</p>
 *
 * <p>Once a value has been successfully converted the following
 * methods can be used to perform various date comparison checks:</p>
 * <ul>
 * <li><code>compareDates()</code> compares the day, month and
 * year of two dates, returning 0, -1 or +1 indicating
 * whether the first date is equal, before or after the second.</li>
 * <li><code>compareWeeks()</code> compares the week and
 * year of two dates, returning 0, -1 or +1 indicating
 * whether the first week is equal, before or after the second.</li>
 * <li><code>compareMonths()</code> compares the month and
 * year of two dates, returning 0, -1 or +1 indicating
 * whether the first month is equal, before or after the second.</li>
 * <li><code>compareQuarters()</code> compares the quarter and
 * year of two dates, returning 0, -1 or +1 indicating
 * whether the first quarter is equal, before or after the second.</li>
 * <li><code>compareYears()</code> compares the
 * year of two dates, returning 0, -1 or +1 indicating
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
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DateProcessor extends AbstractCalendarProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5599395962256741648L;

    /**
     * Default {@link DateProcessor} instance
     */
    private static final DateProcessor VALIDATOR = new DateProcessor();

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the DateValidator2.
     */
    public static DateProcessor getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance with <i>short</i>
     * date style.
     */
    public DateProcessor() {
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
    public DateProcessor(boolean strict, int dateStyle) {
        super(strict, dateStyle, -1);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the default
     * <code>Locale</code> and <code>TimeZone</code>.
     *
     * @param value The value validation is being performed on.
     * @return The parsed <code>Date</code> if valid or <code>null</code>
     * if invalid.
     */
    @Override
    public Object processOrThrow(final String value) {
        return this.parse(value, null, null, null);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * <code>TimeZone</code> and default <code>Locale</code>.
     *
     * @param value    The value validation is being performed on.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    public Object process(final String value, final TimeZone timeZone) {
        return this.parse(value, null, null, timeZone);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * <i>pattern</i> and default <code>TimeZone</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against, or the
     *                default for the <code>Locale</code> if <code>null</code>.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(final String value, final String pattern) {
        return this.parse(value, pattern, null, null);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * <i>pattern</i> and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against, or the
     *                 default for the <code>Locale</code> if <code>null</code>.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    public Object process(final String value, final String pattern, final TimeZone timeZone) {
        return this.parse(value, pattern, null, timeZone);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * <code>Locale</code> and default <code>TimeZone</code>.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the date format, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(final String value, final Locale locale) {
        return this.parse(value, null, locale, null);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * <code>Locale</code> and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param locale   The locale to use for the date format, system default if null.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(String value, Locale locale, TimeZone timeZone) {
        return this.parse(value, null, locale, timeZone);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified pattern
     * and <code>Locale</code> and the default <code>TimeZone</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against, or the
     *                default for the <code>Locale</code> if <code>null</code>.
     * @param locale  The locale to use for the date format, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(final String value, final String pattern, final Locale locale) {
        return this.parse(value, pattern, locale, null);
    }

    /**
     * <p>Validate/convert a <code>Date</code> using the specified
     * pattern, and <code>Locale</code> and <code>TimeZone</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against, or the
     *                 default for the <code>Locale</code> if <code>null</code>.
     * @param locale   The locale to use for the date format, system default if null.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed <code>Date</code> if valid or <code>null</code> if invalid.
     */
    @Override
    public Object process(final String value, final String pattern, final Locale locale, final TimeZone timeZone) {
        return this.parse(value, pattern, locale, timeZone);
    }

    /**
     * <p>Compare Dates (day, month and year - not time).</p>
     *
     * @param value    The <code>Calendar</code> value to check.
     * @param compare  The <code>Calendar</code> to compare the value to.
     * @param timeZone The Time Zone used to compare the dates, system default if null.
     * @return Zero if the dates are equal, -1 if first
     * date is less than the seconds and +1 if the first
     * date is greater than.
     */
    public int compareDates(final Date value, final Date compare, final TimeZone timeZone) {
        final Calendar calendarValue = this.getCalendar(value, timeZone);
        final Calendar calendarCompare = this.getCalendar(compare, timeZone);
        return this.compare(calendarValue, calendarCompare, Calendar.DATE);
    }

    /**
     * <p>Compare Weeks (week and year).</p>
     *
     * @param value    The <code>Date</code> value to check.
     * @param compare  The <code>Date</code> to compare the value to.
     * @param timeZone The Time Zone used to compare the dates, system default if null.
     * @return Zero if the weeks are equal, -1 if first
     * parameter's week is less than the seconds and +1 if the first
     * parameter's week is greater than.
     */
    public int compareWeeks(final Date value, final Date compare, final TimeZone timeZone) {
        final Calendar calendarValue = this.getCalendar(value, timeZone);
        final Calendar calendarCompare = this.getCalendar(compare, timeZone);
        return this.compare(calendarValue, calendarCompare, Calendar.WEEK_OF_YEAR);
    }

    /**
     * <p>Compare Months (month and year).</p>
     *
     * @param value    The <code>Date</code> value to check.
     * @param compare  The <code>Date</code> to compare the value to.
     * @param timeZone The Time Zone used to compare the dates, system default if null.
     * @return Zero if the months are equal, -1 if first
     * parameter's month is less than the seconds and +1 if the first
     * parameter's month is greater than.
     */
    public int compareMonths(final Date value, final Date compare, final TimeZone timeZone) {
        final Calendar calendarValue = this.getCalendar(value, timeZone);
        final Calendar calendarCompare = this.getCalendar(compare, timeZone);
        return this.compare(calendarValue, calendarCompare, Calendar.MONTH);
    }

    /**
     * <p>Compare Quarters (quarter and year).</p>
     *
     * @param value    The <code>Date</code> value to check.
     * @param compare  The <code>Date</code> to compare the value to.
     * @param timeZone The Time Zone used to compare the dates, system default if null.
     * @return Zero if the months are equal, -1 if first
     * parameter's quarter is less than the seconds and +1 if the first
     * parameter's quarter is greater than.
     */
    public int compareQuarters(final Date value, final Date compare, final TimeZone timeZone) {
        return this.compareQuarters(value, compare, timeZone, 1);
    }

    /**
     * <p>Compare Quarters (quarter and year).</p>
     *
     * @param value               The <code>Date</code> value to check.
     * @param compare             The <code>Date</code> to compare the value to.
     * @param timeZone            The Time Zone used to compare the dates, system default if null.
     * @param monthOfFirstQuarter The  month that the first quarter starts.
     * @return Zero if the quarters are equal, -1 if first
     * parameter's quarter is less than the seconds and +1 if the first
     * parameter's quarter is greater than.
     */
    public int compareQuarters(final Date value, final Date compare, final TimeZone timeZone, int monthOfFirstQuarter) {
        final Calendar calendarValue = this.getCalendar(value, timeZone);
        final Calendar calendarCompare = this.getCalendar(compare, timeZone);
        return super.compareQuarters(calendarValue, calendarCompare, monthOfFirstQuarter);
    }

    /**
     * <p>Compare Years.</p>
     *
     * @param value    The <code>Date</code> value to check.
     * @param compare  The <code>Date</code> to compare the value to.
     * @param timeZone The Time Zone used to compare the dates, system default if null.
     * @return Zero if the years are equal, -1 if first
     * parameter's year is less than the seconds and +1 if the first
     * parameter's year is greater than.
     */
    public int compareYears(final Date value, final Date compare, final TimeZone timeZone) {
        final Calendar calendarValue = this.getCalendar(value, timeZone);
        final Calendar calendarCompare = this.getCalendar(compare, timeZone);
        return this.compare(calendarValue, calendarCompare, Calendar.YEAR);
    }

    /**
     * <p>Returns the parsed <code>Date</code> unchanged.</p>
     *
     * @param value     The parsed <code>Date</code> object created.
     * @param formatter The Format used to parse the value with.
     * @return The parsed value converted to a <code>Calendar</code>.
     */
    @Override
    protected Object processParsedValue(final Object value, final Format formatter) {
        return value;
    }

    /**
     * <p>Convert a <code>Date</code> to a <code>Calendar</code>.</p>
     *
     * @param value The date value to be converted.
     * @return The converted <code>Calendar</code>.
     */
    private Calendar getCalendar(final Date value, final TimeZone timeZone) {
        Calendar calendar = null;
        if (Objects.nonNull(timeZone)) {
            calendar = Calendar.getInstance(timeZone);
        } else {
            calendar = Calendar.getInstance();
        }
        calendar.setTime(value);
        return calendar;
    }

    public static DateProcessor of(boolean strict, int dateStyle) {
        return new DateProcessor(strict, dateStyle);
    }
}
