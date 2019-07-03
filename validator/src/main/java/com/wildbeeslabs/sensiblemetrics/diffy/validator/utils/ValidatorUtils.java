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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.service.CreditCardValidator2;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.service.DateValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.service.EmailValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.service.UrlValidator;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator utilities implementation
 */
@Slf4j
@UtilityClass
public class ValidatorUtils {

    /**
     * Default password regex pattern
     */
    private static final Pattern DEFAULT_PASSWORD_STRENGH_PATTERN = Pattern.compile("^(?=.*[A-Z].*[A-Z])(?=.*[!@#$&amp;*])(?=.*[0-9].*[0-9])(?=.*[a-z].*[a-z].*[a-z]).{8}$");
    /**
     * Default hex color regex pattern
     */
    private static final Pattern DEFAULT_HEX_COLOR_PATTERN = Pattern.compile("^\\#([a-fA-F]|[0-9]){3,6}$");
    /**
     * Default email regex pattern
     */
    private static final Pattern DEFAULT_EMAIL_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");//S "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    /**
     * Default ipv4 regex pattern
     */
    private static final Pattern DEFAULT_IPV4_PATTERN = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
    /**
     * Default ipv6 standard regex pattern
     */
    private static final Pattern DEFAULT_IPV6_STD_PATTERN = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
    /**
     * Default ipv6 compressed regex pattern
     */
    private static final Pattern DEFAULT_IPV6_HEX_COMPRESSED_PATTERN = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    /**
     * Default escape regex pattern
     */
    private static final String TO_ESCAPE = "\\.[]{}()*+-?^$|";

    /**
     * UrlValidator used in wrapper method.
     */
    private static final UrlValidator URL_VALIDATOR = new UrlValidator();

    /**
     * <p>Checks if the field is a valid date.  The <code>Locale</code> is
     * used with <code>java.text.DateFormat</code>.  The setLenient method
     * is set to <code>false</code> for all.</p>
     *
     * @param value  The value validation is being performed on.
     * @param locale The locale to use for the date format, defaults to the
     *               system default if null.
     * @return true if the value can be converted to a Date.
     */
    public static boolean isDate(final String value, final Locale locale) {
        return DateValidator.getInstance().validate(value, locale);
    }

    /**
     * <p>Checks if the field is a valid date.  The pattern is used with
     * <code>java.text.SimpleDateFormat</code>.  If strict is true, then the
     * length will be checked so '2/12/1999' will not pass validation with
     * the format 'MM/dd/yyyy' because the month isn't two digits.
     * The setLenient method is set to <code>false</code> for all.</p>
     *
     * @param value       The value validation is being performed on.
     * @param datePattern The pattern passed to <code>SimpleDateFormat</code>.
     * @param strict      Whether or not to have an exact match of the datePattern.
     * @return true if the value can be converted to a Date.
     */
    public static boolean isDate(final String value, final String datePattern, boolean strict) {
        return DateValidator.of(strict, DateFormat.SHORT).validate(value, datePattern);
    }

    /**
     * Checks if the field is a valid credit card number.
     *
     * @param value The value validation is being performed on.
     * @return true if the value is valid Credit Card Number.
     */
    public static boolean isCreditCard(final String value) {
        return CreditCardValidator2.getInstance().validate(value);
    }

    /**
     * <p>Checks if a field has a valid e-mail address.</p>
     *
     * @param value The value validation is being performed on.
     * @return true if the value is valid Email Address.
     */
    public static boolean isEmail(final String value) {
        return EmailValidator.getInstance().validate(value);
    }

    /**
     * <p>Checks if a field is a valid url address.</p>
     * If you need to modify what is considered valid then
     * consider using the UrlValidator directly.
     *
     * @param value The value validation is being performed on.
     * @return true if the value is valid Url.
     */
    public static boolean isUrl(final String value) {
        return URL_VALIDATOR.validate(value);
    }

    public static String escape(final String literal) {
        String escaped = literal;
        for (int i = 0; i < TO_ESCAPE.length(); i++) {
            char c = TO_ESCAPE.charAt(i);
            escaped = escaped.replace(c + "", "\\" + c);
        }
        return escaped;
    }

    public static int execute(final String regex, final String text) {
        return execute(regex, text, 0);
    }

    public static int execute(final String regex, final String text, int flags) {
        final Matcher matcher = matcher(regex, text, flags);
        int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }

    public static Matcher matcher(@NonNull final String regex, @NonNull final String text, int flags) {
        ValidationUtils.isTrue(flags >= 0, "Flags should be positive or equal zero");
        final Pattern pattern = Pattern.compile(regex, flags);
        return pattern.matcher(text);
    }

    public static boolean isIPv4Address(final String input) {
        return DEFAULT_IPV4_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6StdAddress(final String input) {
        return DEFAULT_IPV6_STD_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6HexCompressedAddress(final String input) {
        return DEFAULT_IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
    }

    public static boolean isIPv6Address(final String input) {
        return isIPv6StdAddress(input) || isIPv6HexCompressedAddress(input);
    }

    public static boolean isEmailAddress(final String value, final Boolean allowLocal) {
        return allowLocal && isEmailAddress(value);
    }

    public boolean isEmailAddress(final String input) {
        return DEFAULT_EMAIL_PATTERN.matcher(input).matches();
    }

    public boolean isValidHexColor(final String input) {
        return DEFAULT_HEX_COLOR_PATTERN.matcher(input).matches();
    }

    public boolean isValidStrengthPassword(final String input) {
        return DEFAULT_PASSWORD_STRENGH_PATTERN.matcher(input).matches();
    }

//    /**
//     * Returns {@link Optional} of parsed input address {@link String}
//     *
//     * @param source - initial input address string
//     * @return {@link Optional} of parsed input address {@link String}
//     */
//    public static Optional<String> getRFC822Email(final String source) {
//        try {
//            return Optional.ofNullable(new InternetAddress(source, true).getAddress());
//        } catch (AddressException e) {
//            return Optional.empty();
//        }
//    }

//    /**
//     * Returns {@link Optional} of parsed input address {@link String}
//     *
//     * @param source - initial input address string
//     * @return {@link Optional} of parsed input address {@link String}
//     */
//    public static Optional<String> getRFC822Email(final String source) {
//        try {
//            return Optional.ofNullable(new InternetAddress(source, true).getAddress());
//        } catch (AddressException e) {
//            return Optional.empty();
//        }
//    }
}
