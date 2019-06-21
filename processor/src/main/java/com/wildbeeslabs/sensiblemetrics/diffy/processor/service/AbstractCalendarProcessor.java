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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * <p>Abstract class for Date/Time/Calendar validation.</p>
 *
 * <p>This is a <i>base</i> class for building Date / Time
 * Validators using format parsing.</p>
 *
 * @version $Revision: 1739356 $
 * @since IBANEntry 1.3.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractCalendarProcessor extends AbstractFormatProcessor<String, Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -2462992801172095957L;

    /**
     * Default date style
     */
    private final int dateStyle;
    /**
     * Default time style
     */
    private final int timeStyle;

    /**
     * Construct an instance with the specified <i>strict</i>,
     * <i>time</i> and <i>date</i> style parameters.
     *
     * @param strict    <code>true</code> if strict
     *                  <code>Format</code> parsing should be used.
     * @param dateStyle the date style to use for Locale validation.
     * @param timeStyle the time style to use for Locale validation.
     */
    public AbstractCalendarProcessor(boolean strict, int dateStyle, int timeStyle) {
        super(strict);
        this.dateStyle = dateStyle;
        this.timeStyle = timeStyle;
    }

    /**
     * <p>Format an object into a <code>String</code> using
     * the default Locale.</p>
     *
     * @param value    The value validation is being performed on.
     * @param timeZone The Time Zone used to format the date,
     *                 system default if null (unless value is a <code>Calendar</code>.
     * @return The value formatted as a <code>String</code>.
     */
    public Object process(final String value, final TimeZone timeZone) {
        return this.process(value, null, null, timeZone);
    }

    /**
     * <p>Format an object into a <code>String</code> using
     * the specified pattern.</p>
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to format the value.
     * @param timeZone The Time Zone used to format the date,
     *                 system default if null (unless value is a <code>Calendar</code>.
     * @return The value formatted as a <code>String</code>.
     */
    public Object process(final String value, final String pattern, final TimeZone timeZone) {
        return this.process(value, pattern, null, timeZone);
    }

    /**
     * <p>Format an object into a <code>String</code> using
     * the specified Locale.</p>
     *
     * @param value    The value validation is being performed on.
     * @param locale   The locale to use for the Format.
     * @param timeZone The Time Zone used to format the date,
     *                 system default if null (unless value is a <code>Calendar</code>.
     * @return The value formatted as a <code>String</code>.
     */
    public Object process(final String value, final Locale locale, final TimeZone timeZone) {
        return this.process(value, null, locale, timeZone);
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
    @Override
    public Object process(final String value, final String pattern, final Locale locale) {
        return this.process(value, pattern, locale, null);
    }

    /**
     * <p>Format an object using the specified pattern and/or
     * <code>Locale</code>.
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to format the value.
     * @param locale   The locale to use for the Format.
     * @param timeZone The Time Zone used to format the date,
     *                 system default if null (unless value is a <code>Calendar</code>.
     * @return The value formatted as a <code>String</code>.
     */
    public Object process(final String value, final String pattern, final Locale locale, final TimeZone timeZone) {
        final DateFormat formatter = (DateFormat) getFormat(pattern, locale);
        if (Objects.nonNull(timeZone)) {
            formatter.setTimeZone(timeZone);
        }
//        } else if (value instanceof Calendar) {
//            formatter.setTimeZone(((Calendar) value).getTimeZone());
        return this.process(value, formatter);
    }

    /**
     * <p>Format a value with the specified <code>DateFormat</code>.</p>
     *
     * @param value     The value to be formatted.
     * @param formatter The Format to use.
     * @return The formatted value.
     */
    @Override
    protected Object process(final String value, final Format formatter) {
        if (Objects.isNull(value)) {
            return null;
        }
//        } else if (newValue instanceof Calendar) {
//            newValue = ((Calendar) newValue).getTime();
//        }
        return formatter.format(value);
    }

    /**
     * <p>Checks if the value is valid against a specified pattern.</p>
     *
     * @param value    The value validation is being performed on.
     * @param pattern  The pattern used to validate the value against, or the
     *                 default for the <code>Locale</code> if <code>null</code>.
     * @param locale   The locale to use for the date format, system default if null.
     * @param timeZone The Time Zone used to parse the date, system default if null.
     * @return The parsed value if valid or <code>null</code> if invalid.
     */
    protected Object parse(final String value, final String pattern, Locale locale, TimeZone timeZone) {
        final String newValue = StringUtils.trimToNull(value);
        if (StringUtils.isBlank(newValue)) {
            return null;
        }
        final DateFormat formatter = (DateFormat) getFormat(pattern, locale);
        if (Objects.nonNull(timeZone)) {
            formatter.setTimeZone(timeZone);
        }
        return this.parse(newValue, formatter);
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
    @Override
    protected abstract Object processParsedValue(final Object value, final Format formatter);

    /**
     * <p>Returns a <code>DateFormat</code> for the specified <i>pattern</i>
     * and/or <code>Locale</code>.</p>
     *
     * @param pattern The pattern used to validate the value against or
     *                <code>null</code> to use the default for the <code>Locale</code>.
     * @param locale  The locale to use for the currency format, system default if null.
     * @return The <code>DateFormat</code> to created.
     */
    @Override
    protected Format getFormat(final String pattern, final Locale locale) {
        DateFormat formatter = null;
        boolean usePattern = (pattern != null && pattern.length() > 0);
        if (!usePattern) {
            formatter = (DateFormat) getFormat(locale);
        } else if (locale == null) {
            formatter = new SimpleDateFormat(pattern);
        } else {
            DateFormatSymbols symbols = new DateFormatSymbols(locale);
            formatter = new SimpleDateFormat(pattern, symbols);
        }
        formatter.setLenient(false);
        return formatter;
    }

    /**
     * <p>Returns a <code>DateFormat</code> for the specified Locale.</p>
     *
     * @param locale The locale a <code>DateFormat</code> is required for,
     *               system default if null.
     * @return The <code>DateFormat</code> to created.
     */
    protected Format getFormat(final Locale locale) {
        DateFormat formatter = null;
        if (dateStyle >= 0 && timeStyle >= 0) {
            if (locale == null) {
                formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle);
            } else {
                formatter = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
            }
        } else if (timeStyle >= 0) {
            if (locale == null) {
                formatter = DateFormat.getTimeInstance(timeStyle);
            } else {
                formatter = DateFormat.getTimeInstance(timeStyle, locale);
            }
        } else {
            int useDateStyle = dateStyle >= 0 ? dateStyle : DateFormat.SHORT;
            if (locale == null) {
                formatter = DateFormat.getDateInstance(useDateStyle);
            } else {
                formatter = DateFormat.getDateInstance(useDateStyle, locale);
            }
        }
        formatter.setLenient(false);
        return formatter;
    }

    /**
     * <p>Compares a calendar value to another, indicating whether it is
     * equal, less then or more than at a specified level.</p>
     *
     * @param value   The Calendar value.
     * @param compare The <code>Calendar</code> to check the value against.
     * @param field   The field <i>level</i> to compare to - e.g. specifying
     *                <code>Calendar.MONTH</code> will compare the year and month
     *                portions of the calendar.
     * @return Zero if the first value is equal to the second, -1
     * if it is less than the second or +1 if it is greater than the second.
     */
    protected int compare(final Calendar value, final Calendar compare, int field) {
        int result = 0;

        // Compare Year
        result = calculateCompareResult(value, compare, Calendar.YEAR);
        if (result != 0 || field == Calendar.YEAR) {
            return result;
        }

        // Compare Week of Year
        if (field == Calendar.WEEK_OF_YEAR) {
            return calculateCompareResult(value, compare, Calendar.WEEK_OF_YEAR);
        }

        // Compare Day of the Year
        if (field == Calendar.DAY_OF_YEAR) {
            return calculateCompareResult(value, compare, Calendar.DAY_OF_YEAR);
        }

        // Compare Month
        result = calculateCompareResult(value, compare, Calendar.MONTH);
        if (result != 0 || field == Calendar.MONTH) {
            return result;
        }

        // Compare Week of Month
        if (field == Calendar.WEEK_OF_MONTH) {
            return calculateCompareResult(value, compare, Calendar.WEEK_OF_MONTH);
        }

        // Compare Date
        result = calculateCompareResult(value, compare, Calendar.DATE);
        if (result != 0 || (field == Calendar.DATE ||
            field == Calendar.DAY_OF_WEEK ||
            field == Calendar.DAY_OF_WEEK_IN_MONTH)) {
            return result;
        }

        // Compare Time fields
        return compareTime(value, compare, field);
    }

    /**
     * <p>Compares a calendar time value to another, indicating whether it is
     * equal, less then or more than at a specified level.</p>
     *
     * @param value   The Calendar value.
     * @param compare The <code>Calendar</code> to check the value against.
     * @param field   The field <i>level</i> to compare to - e.g. specifying
     *                <code>Calendar.MINUTE</code> will compare the hours and minutes
     *                portions of the calendar.
     * @return Zero if the first value is equal to the second, -1
     * if it is less than the second or +1 if it is greater than the second.
     */
    protected int compareTime(final Calendar value, final Calendar compare, int field) {
        int result = 0;

        // Compare Hour
        result = calculateCompareResult(value, compare, Calendar.HOUR_OF_DAY);
        if (result != 0 || (field == Calendar.HOUR || field == Calendar.HOUR_OF_DAY)) {
            return result;
        }

        // Compare Minute
        result = calculateCompareResult(value, compare, Calendar.MINUTE);
        if (result != 0 || field == Calendar.MINUTE) {
            return result;
        }

        // Compare Second
        result = calculateCompareResult(value, compare, Calendar.SECOND);
        if (result != 0 || field == Calendar.SECOND) {
            return result;
        }

        // Compare Milliseconds
        if (field == Calendar.MILLISECOND) {
            return calculateCompareResult(value, compare, Calendar.MILLISECOND);
        }
        throw new IllegalArgumentException("Invalid field: " + field);
    }

    /**
     * <p>Compares a calendar's quarter value to another, indicating whether it is
     * equal, less then or more than the specified quarter.</p>
     *
     * @param value               The Calendar value.
     * @param compare             The <code>Calendar</code> to check the value against.
     * @param monthOfFirstQuarter The  month that the first quarter starts.
     * @return Zero if the first quarter is equal to the second, -1
     * if it is less than the second or +1 if it is greater than the second.
     */
    protected int compareQuarters(final Calendar value, final Calendar compare, int monthOfFirstQuarter) {
        int valueQuarter = calculateQuarter(value, monthOfFirstQuarter);
        int compareQuarter = calculateQuarter(compare, monthOfFirstQuarter);
        if (valueQuarter < compareQuarter) {
            return -1;
        } else if (valueQuarter > compareQuarter) {
            return 1;
        }
        return 0;
    }

    /**
     * <p>Calculate the quarter for the specified Calendar.</p>
     *
     * @param calendar            The Calendar value.
     * @param monthOfFirstQuarter The  month that the first quarter starts.
     * @return The calculated quarter.
     */
    private int calculateQuarter(final Calendar calendar, int monthOfFirstQuarter) {
        int year = calendar.get(Calendar.YEAR);

        int month = (calendar.get(Calendar.MONTH) + 1);
        int relativeMonth = (month >= monthOfFirstQuarter)
            ? (month - monthOfFirstQuarter)
            : (month + (12 - monthOfFirstQuarter));
        int quarter = ((relativeMonth / 3) + 1);
        if (month < monthOfFirstQuarter) {
            --year;
        }
        return (year * 10) + quarter;
    }

    /**
     * <p>Compares the field from two calendars indicating whether the field for the
     * first calendar is equal to, less than or greater than the field from the
     * second calendar.
     *
     * @param value   The Calendar value.
     * @param compare The <code>Calendar</code> to check the value against.
     * @param field   The field to compare for the calendars.
     * @return Zero if the first calendar's field is equal to the seconds, -1
     * if it is less than the seconds or +1 if it is greater than the seconds.
     */
    private int calculateCompareResult(final Calendar value, final Calendar compare, int field) {
        int difference = value.get(field) - compare.get(field);
        if (difference < 0) {
            return -1;
        } else if (difference > 0) {
            return 1;
        }
        return 0;
    }
}
