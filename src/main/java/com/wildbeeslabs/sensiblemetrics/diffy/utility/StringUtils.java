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

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.streamOf;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.text.WordUtils.capitalize;
import static org.apache.commons.text.WordUtils.capitalizeFully;

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
     * Default escape wrapper {@link Function}
     */
    public static Function<String, String> wrapInEscapeHtml = s -> "'" + escapeHtml(s) + "'";

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
        return replaceAll(initialValue, pattern, EMPTY).trim();
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
        if (isEmpty(value)) {
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
     * Returns sum length by input array of {@link String}
     *
     * @param array - initial input array of {@link String}
     * @return sum length
     */
    public static int length(final String[] array) {
        return streamOf(array).filter(org.apache.commons.lang3.StringUtils::isNoneBlank).map(String::length).mapToInt(Integer::intValue).sum();
    }

    /**
     * Returns random {@link String}
     *
     * @return random {@link String}
     */
    public static String getRandomString() {
        return UUID.randomUUID().toString();
    }

    /**
     * Converts a string from the Unicode representation into something that can
     * be embedded in a java properties file. All references outside the ASCII
     * range are replaced with \\uXXXX.
     *
     * @param value The string to convert
     * @return the ASCII string
     */
    public static String native2Ascii(final String value) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char aChar = value.charAt(i);
            if ((aChar < 0x0020) || (aChar > 0x007e)) {
                sb.append('\\');
                sb.append('u');
                sb.append(toHex((aChar >> 12) & 0xF));
                sb.append(toHex((aChar >> 8) & 0xF));
                sb.append(toHex((aChar >> 4) & 0xF));
                sb.append(toHex(aChar & 0xF));
            } else {
                sb.append(aChar);
            }
        }
        return sb.toString();
    }

    private static char toHex(int nibble) {
        final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return hexDigit[nibble & 0xF];
    }

    public static String sort(final String s) {
        final char[] content = s.toCharArray();
        Arrays.sort(content);
        return new String(content);
    }

    public static String compress(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        final StringBuilder compStr = new StringBuilder(value.length());
        int count = 0, len = value.length();
        for (int i = 0; i < len; i++) {
            count++;
            if (i + 1 >= value.length() || value.codePointAt(i) != value.codePointAt(i + 1)) {
                compStr.append(Character.toChars(value.codePointAt(i)));
                compStr.append(count);
                count = 0;
            }
        }
        return compStr.length() < len ? compStr.toString() : value;
    }

    public static String reverse(final String value) {
        if (Objects.isNull(value) || value.length() == 0) {
            return EMPTY;
        }
        int length = value.length();
        final char[] chInputArray = value.toCharArray();
        for (int index = 0; index < length / 2; index++) {
            char firstHalf = chInputArray[index];
            char secondHalf = chInputArray[length - index - 1];
            chInputArray[index] = secondHalf;
            chInputArray[length - index - 1] = firstHalf;
        }
        return String.valueOf(chInputArray);
    }

    public static String titleCaseWordFull(final String text) {
        return capitalizeFully(text);
    }

    public static String titleCaseWord(final String text) {
        return capitalize(text);
    }
}
