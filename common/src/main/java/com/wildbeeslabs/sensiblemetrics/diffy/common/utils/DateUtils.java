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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nullable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Date utilities implementation {@link Date}, {@link LocalDate}, {@link LocalDateTime}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class DateUtils {

    /**
     * Default date format locale
     */
    public static final String DEFAULT_DATE_FORMAT_LOCALE = "de_DE";
    /**
     * Default date format pattern
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN_EXT = "yyyy-MM-dd HH:mm:ssZ";
    /**
     * Default date format pattern
     */
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * Default date pattern
     */
    public static final String DEFAULT_DATE_PATTERN = "EEEEE, dd/MM/yyyy";

    /**
     * Default {@link SimpleDateFormat} instances
     */
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_LOCALE_DATE_PATTERN_EXTENDED = ThreadLocal.withInitial(() -> new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US));
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_LOCALE_DATE_PATTERN_STANDARD = ThreadLocal.withInitial(() -> new SimpleDateFormat("EEEEEE, dd-MMM-yy HH:mm:ss zzz", Locale.US));
    public static final ThreadLocal<SimpleDateFormat> DEFAULT_LOCALE_DATE_PATTERN_SIMPLE = ThreadLocal.withInitial(() -> new SimpleDateFormat("EEE MMMM d HH:mm:ss yyyy", Locale.US));

    /**
     * Default time pattern
     */
    public static final String DEFAULT_TIME_PATTERN = "h:mm a";

    public static LocalDate toLocalDateBySql(final Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTimeBySql(final Date dateToConvert) {
        return new java.sql.Timestamp(dateToConvert.getTime()).toLocalDateTime();
    }

    public static Date toDate(final LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public Date toDateBySql(final LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
    }

    public static Date toDate(final LocalDateTime dateToConvert) {
        return java.util.Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate toLocalDate(final Date dateToConvert) {
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(final Date dateToConvert) {
        return LocalDateTime.ofInstant(dateToConvert.toInstant(), ZoneId.systemDefault());
    }

    public static Date toDate() {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @Nullable
    public static Date toDate(final String date, @NonNull final String dateFormat) {
        try {
            final SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            formatter.setLenient(true);
            return formatter.parse(date);
        } catch (ParseException e) {
            log.error(StringUtils.formatMessage("ERROR: cannot parse date by format={%s}", dateFormat));
            InvalidFormatException.throwInvalidFormat(date, e);
        }
        return null;
    }

    public static LocalDate now() {
        return now(ZoneId.systemDefault());
    }

    public static LocalDate now(final ZoneId zone) {
        return LocalDate.now(zone);
    }
}
