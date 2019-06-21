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
package com.wildbeeslabs.sensiblemetrics.diffy.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.*;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * This class contains basic methods for performing validations that return the
 * correctly typed class based on the validation performed.
 *
 * @version $Revision: 1716212 $
 */
@Slf4j
@UtilityClass
public class FormatUtils {

    /**
     * Checks if the value can safely be converted to a byte primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Byte value.
     */
    public static Byte formatByte(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Byte.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to a byte primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Byte value.
     */
    public static Byte formatByte(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= Byte.MIN_VALUE &&
            num.doubleValue() <= Byte.MAX_VALUE) {
            return Byte.valueOf(num.byteValue());
        }
        return null;
    }

    /**
     * Checks if the value can safely be converted to a short primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Short value.
     */
    public static Short formatShort(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Short.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to a short primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Short value.
     */
    public static Short formatShort(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= Short.MIN_VALUE &&
            num.doubleValue() <= Short.MAX_VALUE) {
            return Short.valueOf(num.shortValue());
        }
        return null;
    }

    /**
     * Checks if the value can safely be converted to a int primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Integer value.
     */
    public static Integer formatInt(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to an int primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Integer value.
     */
    public static Integer formatInt(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= Integer.MIN_VALUE &&
            num.doubleValue() <= Integer.MAX_VALUE) {
            return Integer.valueOf(num.intValue());
        }
        return null;
    }

    /**
     * Checks if the value can safely be converted to a long primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Long value.
     */
    public static Long formatLong(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to a long primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Long value.
     */
    public static Long formatLong(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= Long.MIN_VALUE &&
            num.doubleValue() <= Long.MAX_VALUE) {
            return Long.valueOf(num.longValue());
        }
        return null;
    }

    /**
     * Checks if the value can safely be converted to a float primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Float value.
     */
    public static Float formatFloat(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to a float primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Float value.
     */
    public static Float formatFloat(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= (Float.MAX_VALUE * -1) &&
            num.doubleValue() <= Float.MAX_VALUE) {
            return Float.valueOf(num.floatValue());
        }
        return null;
    }

    /**
     * Checks if the value can safely be converted to a double primitive.
     *
     * @param value The value validation is being performed on.
     * @return the converted Double value.
     */
    public static Double formatDouble(String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Checks if the value can safely be converted to a double primitive.
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use to parse the number (system default if
     *               null)
     * @return the converted Double value.
     */
    public static Double formatDouble(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final NumberFormat formatter = NumberFormat.getNumberInstance(defaultLocale);
        formatter.setParseIntegerOnly(true);

        final ParsePosition pos = new ParsePosition(0);
        final Number num = formatter.parse(value, pos);

        if (pos.getErrorIndex() == -1 && pos.getIndex() == value.length() &&
            num.doubleValue() >= (Double.MAX_VALUE * -1) &&
            num.doubleValue() <= Double.MAX_VALUE) {
            return Double.valueOf(num.doubleValue());
        }
        return null;
    }

    /**
     * Checks if the field is a valid date.
     *
     * <p>The {@code Locale} is used with {@code java.text.DateFormat}. The {@link java.text.DateFormat#setLenient(boolean)}
     * method is set to {@code false} for all.
     * </p>
     *
     * @param value  The value validation is being performed on.
     * @param locale The Locale to use to parse the date (system default if null)
     * @return the converted Date value.
     */
    public static Date formatDate(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return null;
        }

        Date date = null;
        try {
            final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
            final DateFormat formatterShort = DateFormat.getDateInstance(DateFormat.SHORT, defaultLocale);
            formatterShort.setLenient(false);
            final DateFormat formatterDefault = DateFormat.getDateInstance(DateFormat.DEFAULT, defaultLocale);
            formatterDefault.setLenient(false);

            try {
                date = formatterShort.parse(value);
            } catch (ParseException e) {
                date = formatterDefault.parse(value);
            }
        } catch (ParseException e) {
            log.debug("Date parse failed value=[" + value + "], " + "locale=[" + locale + "] " + e);
        }
        return date;
    }

    /**
     * Checks if the field is a valid date.
     *
     * <p>The pattern is used with {@code java.text.SimpleDateFormat}.
     * If strict is true, then the length will be checked so '2/12/1999' will
     * not pass validation with the format 'MM/dd/yyyy' because the month isn't
     * two digits. The {@link java.text.SimpleDateFormat#setLenient(boolean)}
     * method is set to {@code false} for all.
     * </p>
     *
     * @param value       The value validation is being performed on.
     * @param datePattern The pattern passed to {@code SimpleDateFormat}.
     * @param strict      Whether or not to have an exact match of the
     *                    datePattern.
     * @return the converted Date value.
     */
    public static Date formatDate(final String value, final String datePattern, boolean strict) {
        if (Objects.isNull(value) || Objects.isNull(datePattern) || datePattern.length() == 0) {
            return null;
        }
        Date date = null;
        try {
            final SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
            formatter.setLenient(false);
            date = formatter.parse(value);

            if (strict && datePattern.length() != value.length()) {
                date = null;
            }
        } catch (ParseException e) {
            log.debug("Date parse failed value=[" + value + "], " + "pattern=[" + datePattern + "], " + "strict=[" + strict + "] " + e);
        }

        return date;
    }
}
