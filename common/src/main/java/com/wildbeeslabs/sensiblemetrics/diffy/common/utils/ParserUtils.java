package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.streamOf;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Slf4j
@UtilityClass
@SuppressWarnings("unchecked")
public class ParserUtils {

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
     * Returns {@link List} collection of tokens {@link String} by input string value and delimiter
     *
     * @param value - initial input string value
     * @param delim - initial input string delimiter
     * @return {@link List} collection of tokens
     */
    public List<String> getTokensWithCollection(final String value, final String delim) {
        return Collections.list(new StringTokenizer(value, delim)).stream()
            .map(token -> (String) token)
            .collect(Collectors.toList());
    }

    public static List<String> getLinks(final String text) {
        final Pattern pattern = Pattern.compile("(?i)<a([^>]+)>(.+?)</a>");
        final Matcher matcher = pattern.matcher(text);
        final List<String> list = new ArrayList<>();
        int start = 0;
        while (matcher.find(start)) {
            if (matcher.group(2).matches("<(.)+?>")) {
                StringBuffer sb = new StringBuffer();
                sb.append("<a ").append(matcher.group(1)).append(">").append(matcher.group(2).replaceAll("<(.)+?>", "")).append("</a>");
                list.add(sb.toString());
            } else {
                list.add(matcher.group());
            }
            start = matcher.end();
        }
        return list;
    }

    public static List<String> getStringsWithoutLinks(final String str) {
        final List<String> list = new ArrayList<>();
        final String[] strings = str.split("(?i)<a([^>]+)>(.+?)</a>");
        for (String s : strings) {
            //reaplace html text separator to plain text separator
            s = s.replaceAll("(?i)<p.*?>|</p>|<br>|<br.?/>|<h[1-6]>|</h[1-6]>", "\n");
            // replace all html tags
            s = s.replaceAll("<(.)+?>", "");
            // replace three or two new line
            s = s.replaceAll("\n\n\n|\n\n", "\n");
            // replace non-breaking space, <, >
            s = s.replaceAll("&nbsp;|&lt;|&gt;", "");
            list.add(s);
        }
        return list;
    }

    /**
     * Unzips compressed string to raw format string output
     *
     * @param value input string.
     * @return String Unzip raw string.
     * @throws Exception On unzip operation.
     * @see Exception
     */
    public static String ungzip(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        try {
            return ungzip(value.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            return null;
        }
    }

    public static String ungzip(final byte[] bytes) throws Exception {
        if (isGZIPStream(bytes)) {
            final InputStreamReader isr = new InputStreamReader(new GZIPInputStream(new ByteArrayInputStream(bytes)), StandardCharsets.UTF_8);
            final StringWriter sw = new StringWriter();
            final char[] chars = new char[1024];
            for (int len; (len = isr.read(chars)) > 0; ) {
                sw.write(chars, 0, len);
            }
            return sw.toString();
        }
        return (new String(bytes, 0, bytes.length, StandardCharsets.UTF_8));
    }

    /**
     * Checks whether input array of bytes is GZIP formatted or not
     *
     * @param bytes input array of bytes.
     * @return boolean true - if GZIP formatted, false - otherwise.
     */
    private static boolean isGZIPStream(final byte[] bytes) {
        if (ArrayUtils.isEmpty(bytes)) {
            return false;
        }
        return (bytes[0] == (byte) GZIPInputStream.GZIP_MAGIC)
            && (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >>> Byte.SIZE));
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

    private static char toHex(int nibble) {
        final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        return hexDigit[nibble & 0xF];
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
}
