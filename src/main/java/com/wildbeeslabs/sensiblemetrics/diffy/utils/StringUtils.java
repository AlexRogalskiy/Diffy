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
package com.wildbeeslabs.sensiblemetrics.diffy.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Function;

/**
 * String utilities implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@UtilityClass
public class StringUtils {

    /**
     * Default regular expression (only alpha-numeric characters)
     */
    public static final String DEFAULT_ALPHANUMERIC_PATTERN = "[^a-zA-Z0-9]";

    /**
     * Default brackets wrapper {@link Function}
     */
    public static final Function<Object, String> wrapInBrackets = s -> "[ " + s + " ]";

    /**
     * Default quotes wrapper {@link Function}
     */
    public static final Function<Object, String> wrapInQuotes = s -> "\" " + s + " \"";
    /**
     * Default numeric pattern format
     */
    public static final String DEFAULT_FORMAT_PATTERN = "#.##";

    /**
     * Default decimal format instance {@link DecimalFormat}
     */
    public static final ThreadLocal<DecimalFormat> DECIMAL_FORMATTER = ThreadLocal.withInitial(() -> {
        final DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        return new DecimalFormat(DEFAULT_FORMAT_PATTERN, decimalSymbols);
    });

    /**
     * Returns {@link String} message by default {@link Locale} instance, initial string message {@link String} and array of arguments
     *
     * @param message - initial input raw string message {@link String}
     * @param args    - initial input array of arguments
     * @return localized string message {@link String}
     */
    public static String formatMessage(final String message, final Object... args) {
        return formatMessage(Locale.getDefault(), message, args);
    }

    /**
     * Returns formatted string message {@link String} by initial input {@link Locale} instance, string message {@link String} and array of arguments
     *
     * @param locale  - initial input {@link Locale} instance
     * @param message - initial input raw string message {@link String}
     * @param args    - initial input array of arguments
     * @return formatted string message {@link String}
     */
    public static String formatMessage(@NonNull final Locale locale, final String message, final Object... args) {
        return String.format(locale, message, args);
    }

    /**
     * Returns string value {@link String} with replaced values by pattern
     *
     * @param initialValue - initial input argument value {@link String} to be processed
     * @param pattern      - initial input pattern value {@link String} to be replaced by
     * @param replaceValue - initial input value {@link String} to replace by pattern
     * @return formatted string value stripped default regex pattern {@link String}
     */
    public static String replaceAll(@NonNull final String initialValue, @NonNull final String pattern, final String replaceValue) {
        return initialValue.replaceAll(pattern, replaceValue);
    }

    /**
     * Returns string value sanitized by default regex pattern {@link String}
     *
     * @param initialValue - initial input argument value {@link String} to be processed
     * @param pattern      - initial input pattern value {@link String} to be replaced by
     * @return formatted string stripped by default regex pattern {@link String}
     */
    public static String sanitize(final String initialValue, final String pattern) {
        return replaceAll(initialValue, pattern, org.apache.commons.lang3.StringUtils.EMPTY).trim();
    }

    /**
     * Returns string value sanitized by default regex pattern {@link String}
     *
     * @param initialValue - initial argument value {@link String} to be processed
     * @return formatted string value stripped by default regex pattern {@link String}
     */
    public static String sanitize(final String initialValue) {
        return sanitize(initialValue, DEFAULT_ALPHANUMERIC_PATTERN);
    }

    /**
     * Returns decapitalized string by input string value {@link String}
     *
     * @param value - initial input string value {@link String}
     * @return decapitalized string {@link String}
     */
    public static String decapitalize(final String value) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(value)) {
            return value;
        }
        if (value.length() > 1 && Character.isUpperCase(value.charAt(1)) && Character.isUpperCase(value.charAt(0))) {
            return value;
        }
        char chars[] = value.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return String.valueOf(chars);
    }

    /**
     * Returns random {@link String}
     *
     * @return random {@link String}
     */
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }
}
