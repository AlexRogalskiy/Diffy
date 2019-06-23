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
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.lang.model.SourceVersion;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.base.Strings.isNullOrEmpty;
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
public class StringUtils {

    /**
     * Default alpha-numeric regex
     */
    public static final String DEFAULT_ALPHANUMERIC_REGEX = "[^a-zA-Z0-9]";
    /**
     * Default first path segment {@link Pattern}
     */
    public static final Pattern FIRST_PATH_FRAGMENT_PATTERN = Pattern.compile("^([/]?[\\w\\-\\.]+[/]?)");
    /**
     * Default host:port and protocol:\\host:port
     */
    private static final Pattern HOST_PORT_PATTERN = Pattern.compile(".*?\\[?([0-9a-zA-Z\\-%._:]*)\\]?:([0-9]+)");

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
        return sanitize(initialValue, DEFAULT_ALPHANUMERIC_REGEX);
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
        final char[] chars = value.toCharArray();
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
        final StringBuilder sb = new StringBuilder();
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

    public static String readFile(final String filename) {
        ValidationUtils.notNull(filename, "File name should not be null");
        final StringBuilder sb = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while (Objects.nonNull(line = br.readLine())) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
        } catch (IOException ex) {
            log.error(String.format("ERROR: cannot process read operation on file={%s}, message={%s}", filename, ex.getMessage()));
        }
        return sb.toString();
    }

    public static String readFile2(final String filename) {
        ValidationUtils.notNull(filename, "File name should not be null");
        final StringBuilder sb = new StringBuilder();
        try {
            final List<String> lines = Files.readAllLines(Paths.get(filename), StandardCharsets.UTF_8);
            lines.stream().map(line -> {
                sb.append(line);
                return line;
            }).forEach(_item -> sb.append(System.lineSeparator()));
        } catch (IOException e) {
            log.error(String.format("ERROR: cannot process read operations on file=%s, message=%s", filename, e.getMessage()));
        }
        return sb.toString();
    }

    public static String readFile3(final String filename) {
        ValidationUtils.notNull(filename, "File name should not be null");
        final StringBuilder sb = new StringBuilder();
        try (final Stream<String> stream = Files.lines(Paths.get(filename))) {
            stream.forEachOrdered(s -> {
                sb.append(s);
                sb.append(System.lineSeparator());
            });
        } catch (IOException ex) {
            log.error(String.format("ERROR: cannot process read operations on file=%s, message=%s", filename, ex.getMessage()));
        }
        return sb.toString();
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

    public static String stripSlashes(final String stringWithSlashes) {
        return stringWithSlashes.replace("/", "").replace("\\", "");
    }

    public static String chompLeadingSlash(final String path) {
        if (isNullOrEmpty(path) || !path.startsWith("/")) {
            return path;
        }
        return path.replaceFirst("^/", "");
    }

    public static String chompTrailingSlash(final String path) {
        if (isNullOrEmpty(path) || !path.endsWith("/")) {
            return path;
        }
        return path.replaceFirst("/$", "");
    }

    public static String firstPathSegment(final String path) {
        if (isNullOrEmpty(path)) {
            return path;
        }
        final Matcher matcher = FIRST_PATH_FRAGMENT_PATTERN.matcher(path);
        if (matcher.find()) {
            return chompTrailingSlash(matcher.group());
        }
        return path;
    }

    /**
     * Gets a uri friendly path from a request mapping pattern.
     * Typically involves removing any regex patterns or || conditions from a spring request mapping
     * This method will be called to resolve every request mapping endpoint.
     * A good extension point if you need to alter endpoints by adding or removing path segments.
     * Note: this should not be an absolute  uri
     *
     * @param requestMappingPattern request mapping pattern
     * @return the request mapping endpoint
     */
    public static String sanitizeRequest(final String requestMappingPattern) {
        String result = requestMappingPattern;
        //remove regex portion '/{businessId:\\w+}'
        result = result.replaceAll("\\{([^}]+?):([^/{}]|\\{[\\d,]+})+}", "{$1}");
        return result.isEmpty() ? "/" : result;
    }

    public static String removeAdjacentForwardSlashes(final String candidate) {
        return candidate.replaceAll("(?<!(http:|https:))//", "/");
    }

    public static String html2Text(final String content) {
        return Jsoup.clean(content, Whitelist.none());
    }

    /**
     * Extracts the hostname from a "host:port" address string.
     *
     * @param address address string to parse
     * @return hostname or null if the given address is incorrect
     */
    public static String getHost(final String address) {
        final Matcher matcher = HOST_PORT_PATTERN.matcher(address);
        return matcher.matches() ? matcher.group(1) : null;
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

    public static String readUrl(final String url) {
        ValidationUtils.notNull(url, "Url should not be null");
        try {
            return Jsoup.connect(url).get().html();
        } catch (IOException ex) {
            log.error(String.format("ERROR: cannot process read operations on url=%s, message=%s", url, ex.getMessage()));
        }
        return null;
    }

    public static String splitCamelCase(final String s, final String separator) {
        if (isNullOrEmpty(s)) {
            return EMPTY;
        }
        return s.replaceAll(
            String.format("%s|%s|%s",
                "(?<=[A-Z])(?=[A-Z][a-z])",
                "(?<=[^A-Z])(?=[A-Z])",
                "(?<=[A-Za-z])(?=[^A-Za-z])"
            ),
            separator
        );
    }

    public static <E> Stream<E> getFilteredStream(final Stream<E> stream, final Function<CharSequence, CharSequence> tokenFilter, final String tokenDelim) {
        return stream.flatMap(line -> Arrays.stream(String.valueOf(line).split(tokenDelim)))
            .map(String::trim)
            .map(item -> tokenFilter.apply(item))
            .filter(org.apache.commons.lang3.StringUtils::isNotBlank)
            .map(item -> (E) item);
    }

    public static <T> String joinWithPrefixPostfix(final Collection<T> list, final String delimiter, final String prefix, final String postfix) {
        return streamOf(list).map(Objects::toString).collect(Collectors.joining(delimiter, prefix, postfix));
    }

    public static Map<Integer, List<String>> getMapByLength(final String... array) {
        return streamOf(array).filter(Objects::nonNull).collect(Collectors.groupingBy(String::length));
    }

    public static List<String> split(final String value, final String delimiter, final Predicate<? super String> predicate) {
        return Arrays.stream(String.valueOf(value).split(delimiter))
            .map(String::trim)
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public static <T> String replace(final String input, final Pattern regex, final Function<Matcher, String> function) {
        final StringBuffer resultString = new StringBuffer();
        final Matcher regexMatcher = regex.matcher(input);
        while (regexMatcher.find()) {
            regexMatcher.appendReplacement(resultString, function.apply(regexMatcher));
        }
        regexMatcher.appendTail(resultString);
        return resultString.toString();
    }

    public static List<Character> splitToListOfChars(final String value) {
        return Optional.ofNullable(value).orElse(EMPTY)
            .chars()
            .mapToObj(item -> (char) item)
            .collect(Collectors.toList());
    }
}
