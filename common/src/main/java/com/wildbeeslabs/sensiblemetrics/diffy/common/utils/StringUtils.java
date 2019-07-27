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

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.BadOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.SourceVersion;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.regex.Pattern;
import java.util.stream.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.streamOf;
import static org.apache.commons.lang.StringEscapeUtils.escapeHtml;
import static org.apache.commons.lang3.StringUtils.*;
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
@SuppressWarnings("unchecked")
public class StringUtils {

    /**
     * Default braces wrapper {@link Function}
     */
    public static final Function<Object, String> wrapInBraces = s -> "( " + s + " )";
    /**
     * Default brackets wrapper {@link Function}
     */
    public static final Function<Object, String> wrapInBrackets = s -> "[ " + s + " ]";
    /**
     * Default quotes wrapper {@link Function}
     */
    public static final Function<Object, String> wrapInQuotes = s -> "\" " + s + " \"";

    /**
     * Default string wrapper {@link Function}
     */
    public static final Function<String, String> wrapStr = StringUtils::formatNative;
    /**
     * Default character wrapper {@link Function}
     */
    public static final Function<Character, String> wrapChar = s -> "\" " + toNativeSyntax(s) + " \"";
    /**
     * Default short wrapper {@link Function}
     */
    public static final Function<Short, String> wrapShort = s -> "< " + s + " S>";
    /**
     * Default long wrapper {@link Function}
     */
    public static final LongFunction<String> wrapLong = s -> "< " + s + " L>";
    /**
     * Default float wrapper {@link Function}
     */
    public static final Function<Float, String> wrapFloat = s -> "< " + s + " F>";
    /**
     * Default double wrapper {@link Function}
     */
    public static final DoubleFunction<String> wrapDouble = s -> "< " + s + " D>";
    /**
     * Default int wrapper {@link Function}
     */
    public static final IntFunction<String> wrapInt = s -> "< " + s + " I>";
    /**
     * Default array wrapper {@link Function}
     */
    public static final Function<Object[], String> wrapArray = s -> "< " + join(s, "|") + " A>";
    /**
     * Default escape wrapper {@link Function}
     */
    public static final Function<String, String> wrapInEscapeHtml = s -> "'" + escapeHtml(s) + "'";

    /**
     * Default numeric pattern format
     */
    public static final String DEFAULT_FORMAT_PATTERN = "#.##";

    /**
     * Default package name
     */
    public static final String DEFAULT_PACKAGE_NAME = "";

    /**
     * Compiled {@code "\."} pattern used to split canonical package (and type) names.
     */
    private static final Pattern DOT_PATTERN = Pattern.compile("\\.");

    /**
     * Assert that the supplied package name is valid in terms of Java syntax.
     *
     * <p>Note: this method does not actually verify if the named package exists in the classpath.
     *
     * <p>The default package is represented by an empty string ({@code ""}).
     *
     * @param packageName the package name to validate
     * @throws NullPointerException if the supplied package name is
     *                              {@code null}, contains only whitespace, or contains parts that are not
     *                              valid in terms of Java syntax (e.g., containing keywords such as
     *                              {@code void}, {@code import}, etc.)
     * @see SourceVersion#isName(CharSequence)
     */
    public static boolean isValidPackage(final String packageName) {
        ValidationUtils.notNull(packageName, "package name must not be null");
        if (packageName.equals(DEFAULT_PACKAGE_NAME)) {
            return true;
        }
        ValidationUtils.isTrue(org.apache.commons.lang3.StringUtils.isNotBlank(packageName), "package name must not contain only whitespace");
        return Arrays.stream(DOT_PATTERN.split(packageName, -1)).allMatch(SourceVersion::isName);
    }

    /**
     * Default decimal format instance {@link DecimalFormat}
     */
    public static final ThreadLocal<DecimalFormat> DECIMAL_FORMATTER = ThreadLocal.withInitial(() -> {
        final DecimalFormatSymbols decimalSymbols = DecimalFormatSymbols.getInstance();
        decimalSymbols.setDecimalSeparator('.');
        return new DecimalFormat(DEFAULT_FORMAT_PATTERN, decimalSymbols);
    });

    private static String formatNative(final String value) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            sb.append(toNativeSyntax(value.charAt(i)));
        }
        return sb.toString();
    }

    private static String toNativeSyntax(char ch) {
        switch (ch) {
            case '"':
                return "\\\"";
            case '\n':
                return "\\n";
            case '\r':
                return "\\r";
            case '\t':
                return "\\t";
            default:
                return String.valueOf(ch);
        }
    }

    public static String convertValue(final Object value) {
        if (Objects.isNull(value)) {
            return "null";
        } else if (value instanceof String) {
            return wrapStr.apply((String) value);
        } else if (value instanceof Character) {
            return wrapChar.apply((Character) value);
        } else if (value instanceof Short) {
            return wrapShort.apply((Short) value);
        } else if (value instanceof Long) {
            return wrapLong.apply((Long) value);
        } else if (value instanceof Float) {
            return wrapFloat.apply((Float) value);
        } else if (value.getClass().isArray()) {
            return wrapArray.apply((Object[]) value);
        }
        return wrapInBrackets.apply(value);
    }

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
        int count = 0;
        int len = value.length();
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

    /**
     * Check whether string consists of unique set of characters
     *
     * @param value - input string
     * @return true - if string is unique, false - otherwise
     */
    public static boolean isUnique(final String value) {
        if (isEmpty(value)) {
            return false;
        }
        return value.codePoints().allMatch(new HashSet<>()::add);
    }

    public boolean isPalindrome(final String value) {
        if (isEmpty(value)) {
            return false;
        }
        final String temp = value.replaceAll("\\s+", org.apache.commons.lang3.StringUtils.EMPTY).toLowerCase();
        return IntStream.range(0, temp.length() / 2).noneMatch(i -> temp.charAt(i) != temp.charAt(temp.length() - i - 1));
    }

    public static boolean isPermutationOfPalindrome(final String value) {
        if (isEmpty(value)) {
            return false;
        }
        final AtomicInteger count = new AtomicInteger();
        value.codePoints().mapToObj(ch -> (char) ch).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).forEach((k, v) -> {
            if (v % 2 == 1) {
                if (count.get() > 1) {
                    return;
                }
                count.incrementAndGet();
            }
        });
        return count.get() <= 1;
    }

    /**
     * Determine if the supplied {@link String} contains any ISO control characters.
     *
     * @param value the string to check; may be {@code null}
     * @return {@code true} if the string contains an ISO control character
     * @see Character#isISOControl(int)
     */
    public static boolean containsIsoControlCharacter(final String value) {
        return isNotEmpty(value) && value.codePoints().anyMatch(Character::isISOControl);
    }

    /**
     * Determine if the supplied {@link String} does not contain any ISO control
     * characters.
     *
     * @param value the string to check; may be {@code null}
     * @return {@code true} if the string does not contain an ISO control character
     * @see #containsIsoControlCharacter(String)
     * @see Character#isISOControl(int)
     */
    public static boolean doesNotContainIsoControlCharacter(final String value) {
        return !containsIsoControlCharacter(value);
    }

    public static String toBase64(final String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    public static String fromBase64(final String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }

    public static List<String> toBinaryString(final IntStream stream) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        return stream.mapToObj(n -> Integer.toBinaryString(n)).collect(Collectors.toList());
    }

    public static Collection<Double> toDouble(final Stream<String> stream) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        return stream.flatMapToDouble(n -> DoubleStream.of(Double.parseDouble(n))).boxed().collect(Collectors.toList());
    }

    public static Collection<Integer> toInt(final Stream<String> stream) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        return stream.flatMapToInt(n -> IntStream.of(Integer.parseInt(n))).boxed().collect(Collectors.toList());
    }

    public static Collection<Long> toLong(final Stream<String> stream) {
        ValidationUtils.notNull(stream, "Stream should not be null");
        return stream.flatMapToLong(n -> LongStream.of(Long.parseLong(n))).boxed().collect(Collectors.toList());
    }

    public static Collection<Character> toCodePoints(final String value) {
        ValidationUtils.notNull(value, "Value should not be null");
        return value.codePoints().mapToObj(c -> (char) c).collect(Collectors.toList());
    }

    public static String encodeUtf8(final String value) {
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new BadOperationException(String.format("Unable to encode input value={%s}", value), e);
        }
    }

    public static String decodeUtf8(final String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new BadOperationException(String.format("Unable to decode input value={%s}", value), e);
        }
    }

    /**
     * Returns generated identifier {@link String}
     *
     * @param length - initial input identifier length
     * @return generated identifier {@link String}
     */
    public static String generateId(final int length) {
        ValidationUtils.isTrue(length > 0, "Length should be positive number");
        final StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char next = (char) ('a' + (int) Math.floor(Math.random() * 26));
            if (Math.random() < 0.5) {
                next = Character.toUpperCase(next);
            }
            buffer.append(next);
        }
        return buffer.toString();
    }

    public static String generatePassword(final String algorithm) {
        ValidationUtils.notNull(algorithm, "Algorithm should be null");
        try {
            final SecureRandom random = SecureRandom.getInstance(algorithm);
            byte[] passwordBytes = new byte[16];
            random.nextBytes(passwordBytes);
            return new String(org.apache.commons.codec.binary.Base64.encodeBase64(passwordBytes), StandardCharsets.UTF_8).replace("=", "");
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidParameterException(String.format("Unable to load algorithm={%s}", algorithm), e);
        }
    }

    public List<String> sortAlphabetic(List<String> combinations) {
        Collections.sort(combinations, (o1, o2) -> (o1.toLowerCase()).compareToIgnoreCase(o2.toLowerCase()));
        return combinations;
    }
}
