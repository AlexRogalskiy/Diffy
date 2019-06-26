/*
 * The MIT License
 *
 * Copyright 2017 WildBees Labs.
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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.text.StringEscapeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Helper class to handle number format operations
 *
 * @author alexander.rogalskiy
 * @version 1.0
 * @since 2017-12-12
 */
@UtilityClass
public class NumberFormatterUtils {

    /**
     * Default number format pattern
     */
    public static final String DEFAULT_NUMBER_FORMAT_PATTERN = "#.##";
    /**
     * Default locale format instance
     */
    private static final Locale DEFAULT_LOCALE = Locale.getDefault();
    /**
     * Default number format instance
     */
    private static final ThreadLocal<DecimalFormat> DEFAULT_NUMBER_FORMAT = ThreadLocal.withInitial(() -> new DecimalFormat(DEFAULT_NUMBER_FORMAT_PATTERN, DecimalFormatSymbols.getInstance(DEFAULT_LOCALE)));
    /**
     * Default java escape format operator
     */
    public static final UnaryOperator<String> DEFAULT_JAVA_ESCAPE_FORMAT = input -> "'" + escape(input, StringEscapeUtils::escapeJava) + "'";
    /**
     * Default html4 escape format operator
     */
    public static final UnaryOperator<String> DEFAULT_HTML_ESCAPE_FORMAT = input -> "'" + escape(input, StringEscapeUtils::escapeHtml4) + "'";
    /**
     * Default json escape format operator
     */
    public static final UnaryOperator<String> DEFAULT_JSON_ESCAPE_FORMAT = input -> "'" + escape(input, StringEscapeUtils::escapeJson) + "'";
    /**
     * Default csv escape format operator
     */
    public static final UnaryOperator<String> DEFAULT_CSV_ESCAPE_FORMAT = input -> "'" + escape(input, StringEscapeUtils::escapeCsv) + "'";
    /**
     * Default xml escape format operator
     */
    public static final UnaryOperator<String> DEFAULT_XML_ESCAPE_FORMAT = input -> "'" + escape(input, StringEscapeUtils::escapeXml11) + "'";

    private static String escape(final String input, final Function<String, String> function) {
        return function.apply(input);
    }

    public static <T> String format(final Comparable<? super T> value) {
        return DEFAULT_NUMBER_FORMAT.get().format(value);
    }

    public static <T> String formatByPattern(final Comparable<? super T> value, final String pattern) {
        return formatByPattern(value, pattern, DEFAULT_LOCALE);
    }

    public static <T> String formatByPattern(final Comparable<? super T> value, final String pattern, Locale locale) {
        final DecimalFormat formatter = new DecimalFormat(pattern, DecimalFormatSymbols.getInstance(locale));
        return formatter.format(value);
    }

    public static <T> String format(final Comparable<? super T> value, int minFractionDigits, int maxFractionDigits, int minIntDigits, int maxIntDigits, final String negPrefix, final String negSuffix) {
        final DecimalFormat formatter = getFormatter(minFractionDigits, maxFractionDigits, minIntDigits, maxIntDigits, negPrefix, negSuffix, RoundingMode.UP);
        return formatter.format(value);
    }

    public static DecimalFormat getFormatter(int minFractionDigits, int maxFractionDigits, int minIntDigits, int maxIntDigits, final String negPrefix, final String negSuffix, final RoundingMode roundingMode) {
        final DecimalFormat formatter = new DecimalFormat();
        formatter.setMinimumFractionDigits(minFractionDigits);
        formatter.setMaximumFractionDigits(maxFractionDigits);
        formatter.setMinimumIntegerDigits(minIntDigits);
        formatter.setMaximumIntegerDigits(maxIntDigits);
        formatter.setNegativePrefix(negPrefix);
        formatter.setNegativeSuffix(negSuffix);
        formatter.setGroupingUsed(true);
        formatter.setRoundingMode(roundingMode);
        return formatter;
    }

    /**
     * Round the given value to the specified number of decimal places. The
     * value is rounded using the {@link BigDecimal#ROUND_HALF_UP} method.
     *
     * @param x     the value to round.
     * @param scale the number of digits to the right of the decimal point.
     * @return the rounded value.
     * @since 1.1
     */
    public static double round(double x, int scale) {
        return round(x, scale, RoundingMode.HALF_UP);
    }

    /**
     * Round the given value to the specified number of decimal places. The
     * value is rounded using the given method which is any method defined in
     * {@link BigDecimal}.
     *
     * @param x            the value to round.
     * @param scale        the number of digits to the right of the decimal point.
     * @param roundingMode the rounding method as defined in
     *                     {@link BigDecimal}.
     * @return the rounded value.
     * @since 1.1
     */
    public static double round(double x, int scale, final RoundingMode roundingMode) {
        try {
            return (new BigDecimal(Double.toString(x))
                .setScale(scale, roundingMode))
                .doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            }
            return Double.NaN;
        }
    }

    //new Locale("sk", "SK")
    public static String formatPercent(final Locale locale, final Object object) {
        final NumberFormat nf = NumberFormat.getPercentInstance(locale);
        return nf.format(locale);
    }

    //new Locale("sk", "SK")
    public static String formatCurrency(final Locale locale, final Object object) {
        final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(locale);
    }

    public static String prependZeroesToSize(final String string, final int size) {
        if (string.length() < size) {
            int difference = size - string.length();
            final StringBuilder buff = new StringBuilder();
            for (int i = 0; i < difference; i++) {
                buff.append("0");
            }
            buff.append(string);
            return buff.toString();
        }
        return string;
    }
}
