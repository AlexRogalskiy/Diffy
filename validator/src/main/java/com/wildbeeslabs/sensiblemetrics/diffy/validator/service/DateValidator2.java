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

import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>Perform date validations.</p>
 * <p>
 * This class is a Singleton; you can retrieve the instance via the
 * getInstance() method.
 * </p>
 *
 * @version $Revision: 1739358 $
 * @since Validator 1.1
 */
public class DateValidator2 implements Validator<String> {

    /**
     * Default date pattern
     */
    private final String datePattern;
    /**
     * Default strict pattern
     */
    private final boolean strict;

    /**
     * Protected constructor for subclasses to use.
     */
    public DateValidator2(final String datePattern) {
        this(datePattern, false);
    }

    /**
     * Protected constructor for subclasses to use.
     */
    public DateValidator2(final String datePattern, final boolean strict) {
        ValidationUtils.notNull(datePattern, "Date pattern should not be null");
        this.datePattern = datePattern;
        this.strict = strict;
    }

    /**
     * <p>Checks if the field is a valid date.  The pattern is used with
     * <code>java.text.SimpleDateFormat</code>.  If strict is true, then the
     * length will be checked so '2/12/1999' will not pass validation with
     * the format 'MM/dd/yyyy' because the month isn't two digits.
     * The setLenient method is set to <code>false</code> for all.</p>
     *
     * @param value The value validation is being performed on.
     * @return true if the date is valid.
     */
    @Override
    public boolean validate(final String value) {
        if (Objects.isNull(value) || this.datePattern.length() <= 0) {
            return false;
        }
        final SimpleDateFormat formatter = new SimpleDateFormat(this.datePattern);
        formatter.setLenient(false);
        try {
            formatter.parse(value);
        } catch (ParseException e) {
            return false;
        }
        if (this.strict && (this.datePattern.length() != value.length())) {
            return false;
        }
        return true;
    }

    /**
     * <p>Checks if the field is a valid date.  The <code>Locale</code> is
     * used with <code>java.text.DateFormat</code>.  The setLenient method
     * is set to <code>false</code> for all.</p>
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the date format, defaults to the default
     *               system default if null.
     * @return true if the date is valid.
     */
    public static boolean validate(final String value, final Locale locale) {
        if (Objects.isNull(value)) {
            return false;
        }
        final Locale defaultLocale = Optional.ofNullable(locale).orElseGet(Locale::getDefault);
        final DateFormat defaultFormatter = DateFormat.getDateInstance(DateFormat.SHORT, defaultLocale);
        defaultFormatter.setLenient(false);
        try {
            defaultFormatter.parse(value);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns {@link DateValidator2} instance by input parameters
     *
     * @param datePattern - initial input date pattern {@link String}
     * @param strict      - initial input strict flag
     * @return {@link DateValidator2} instance
     */
    public static DateValidator2 of(final String datePattern, final boolean strict) {
        return new DateValidator2(datePattern, strict);
    }
}
