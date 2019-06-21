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

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.impl.Flags;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class UrlValidator implements Validator<String> {

    /**
     * Allows all validly formatted schemes to pass validation instead of
     * supplying a set of valid schemes.
     */
    public static final int ALLOW_ALL_SCHEMES = 1 << 0;

    /**
     * Allow two slashes in the path component of the URL.
     */
    public static final int ALLOW_2_SLASHES = 1 << 1;

    /**
     * Enabling this options disallows any URL fragments.
     */
    public static final int NO_FRAGMENTS = 1 << 2;

    private static final String ALPHA_CHARS = "a-zA-Z";

    private static final String SPECIAL_CHARS = ";/@&=,.?:+$";

    private static final String VALID_CHARS = "[^\\s" + SPECIAL_CHARS + "]";

    private static final String AUTHORITY_CHARS_REGEX = "\\p{Alnum}\\-\\.";

    private static final String ATOM = VALID_CHARS + '+';

    /**
     * This expression derived/taken from the BNF for URI (RFC2396).
     */
    private static final String URL_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    /**
     * Schema/Protocol (ie. http:, ftp:, file:, etc).
     */
    private static final int PARSE_URL_SCHEME = 2;

    /**
     * Includes hostname/ip and port number.
     */
    private static final int PARSE_URL_AUTHORITY = 4;

    private static final int PARSE_URL_PATH = 5;

    private static final int PARSE_URL_QUERY = 7;

    private static final int PARSE_URL_FRAGMENT = 9;

    /**
     * Protocol (ie. http:, ftp:,https:).
     */
    private static final Pattern SCHEME_PATTERN = Pattern.compile("^\\p{Alpha}[\\p{Alnum}\\+\\-\\.]*");

    private static final String AUTHORITY_REGEX = "^([" + AUTHORITY_CHARS_REGEX + "]*)(:\\d*)?(.*)?";

    private static final Pattern AUTHORITY_PATTERN = Pattern.compile(AUTHORITY_REGEX);

    private static final int PARSE_AUTHORITY_HOST_IP = 1;

    private static final int PARSE_AUTHORITY_PORT = 2;

    /**
     * Should always be empty.
     */
    private static final int PARSE_AUTHORITY_EXTRA = 3;

    private static final Pattern PATH_PATTERN = Pattern.compile("^(/[-\\w:@&?=+,.!/~*'%$_;]*)?$");

    private static final Pattern QUERY_PATTERN = Pattern.compile("^(.*)$");

    private static final Pattern LEGAL_ASCII_PATTERN = Pattern.compile("^\\p{ASCII}+$");

    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^" + ATOM + "(\\." + ATOM + ")*$");

    private static final Pattern PORT_PATTERN = Pattern.compile("^:(\\d{1,5})$");

    private static final Pattern ATOM_PATTERN = Pattern.compile("^(" + ATOM + ").*?$");

    private static final Pattern ALPHA_PATTERN = Pattern.compile("^[" + ALPHA_CHARS + "]");

    /**
     * Holds the set of current validation options.
     */
    private final Flags options;

    /**
     * The set of schemes that are allowed to be in a URL.
     */
    private final Set<String> allowedSchemes = new HashSet<>();

    /**
     * If no schemes are provided, default to this set.
     */
    protected String[] defaultSchemes = {"http", "https", "ftp"};

    /**
     * Create a UrlValidator with default properties.
     */
    public UrlValidator() {
        this(null);
    }

    /**
     * Behavior of validation is modified by passing in several strings options:
     *
     * @param schemes Pass in one or more url schemes to consider valid, passing in
     *                a null will default to "http,https,ftp" being valid.
     *                If a non-null schemes is specified then all valid schemes must
     *                be specified. Setting the ALLOW_ALL_SCHEMES option will
     *                ignore the contents of schemes.
     */
    public UrlValidator(final String[] schemes) {
        this(schemes, 0);
    }

    /**
     * Initialize a UrlValidator with the given validation options.
     *
     * @param options The options should be set using the public constants declared in
     *                this class.  To set multiple options you simply add them together.  For example,
     *                ALLOW_2_SLASHES + NO_FRAGMENTS enables both of those options.
     */
    public UrlValidator(int options) {
        this(null, options);
    }

    /**
     * Behavour of validation is modified by passing in options:
     *
     * @param schemes The set of valid schemes.
     * @param options The options should be set using the public constants declared in
     *                this class.  To set multiple options you simply add them together.  For example,
     *                ALLOW_2_SLASHES + NO_FRAGMENTS enables both of those options.
     */
    public UrlValidator(final String[] schemes, int options) {
        this.options = new Flags(options);
        if (this.options.isOn(ALLOW_ALL_SCHEMES)) {
            return;
        }
        this.allowedSchemes.addAll(Arrays.asList(Optional.ofNullable(schemes).orElse(this.defaultSchemes)));
    }

    /**
     * <p>Checks if a field has a valid url address.</p>
     *
     * @param value The value validation is being performed on.  A <code>null</code>
     *              value is considered invalid.
     * @return true if the url is valid.
     */
    @Override
    public boolean validate(final String value) {
        if (Objects.isNull(value)) {
            return false;
        }
        if (!LEGAL_ASCII_PATTERN.matcher(value).matches()) {
            return false;
        }
        final Matcher urlMatcher = URL_PATTERN.matcher(value);
        if (!urlMatcher.matches()) {
            return false;
        }
        if (!this.isValidScheme(urlMatcher.group(PARSE_URL_SCHEME))) {
            return false;
        }
        if (!this.isValidAuthority(urlMatcher.group(PARSE_URL_AUTHORITY))) {
            return false;
        }
        if (!this.isValidPath(urlMatcher.group(PARSE_URL_PATH))) {
            return false;
        }
        if (!this.isValidQuery(urlMatcher.group(PARSE_URL_QUERY))) {
            return false;
        }
        if (!this.isValidFragment(urlMatcher.group(PARSE_URL_FRAGMENT))) {
            return false;
        }
        return true;
    }

    /**
     * Validate scheme. If schemes[] was initialized to a non null,
     * then only those scheme's are allowed.  Note this is slightly different
     * than for the constructor.
     *
     * @param scheme The scheme to validate.  A <code>null</code> value is considered
     *               invalid.
     * @return true if valid.
     */
    protected boolean isValidScheme(final String scheme) {
        if (Objects.isNull(scheme)) {
            return false;
        }
        if (!SCHEME_PATTERN.matcher(scheme).matches()) {
            return false;
        }
        if (this.options.isOff(ALLOW_ALL_SCHEMES) && !this.allowedSchemes.contains(scheme)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the authority is properly formatted.  An authority is the combination
     * of hostname and port.  A <code>null</code> authority value is considered invalid.
     *
     * @param authority Authority value to validate.
     * @return true if authority (hostname and port) is valid.
     */
    protected boolean isValidAuthority(final String authority) {
        if (Objects.isNull(authority)) {
            return false;
        }
        final InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
        final Matcher authorityMatcher = AUTHORITY_PATTERN.matcher(authority);
        if (!authorityMatcher.matches()) {
            return false;
        }

        boolean hostname = false;
        String hostIP = authorityMatcher.group(PARSE_AUTHORITY_HOST_IP);
        boolean ipV4Address = inetAddressValidator.isValid(hostIP);

        if (!ipV4Address) {
            hostname = DOMAIN_PATTERN.matcher(hostIP).matches();
        }

        if (hostname) {
            char[] chars = hostIP.toCharArray();
            int size = 1;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '.') {
                    size++;
                }
            }
            final String[] domainSegment = new String[size];
            boolean match = true;
            int segmentCount = 0;
            int segmentLength = 0;

            while (match) {
                Matcher atomMatcher = ATOM_PATTERN.matcher(hostIP);
                match = atomMatcher.matches();
                if (match) {
                    domainSegment[segmentCount] = atomMatcher.group(1);
                    segmentLength = domainSegment[segmentCount].length() + 1;
                    hostIP = (segmentLength >= hostIP.length()) ? EMPTY : hostIP.substring(segmentLength);
                    segmentCount++;
                }
            }
            String topLevel = domainSegment[segmentCount - 1];
            if (topLevel.length() < 2 || topLevel.length() > 4) { // CHECKSTYLE IGNORE MagicNumber (deprecated code)
                return false;
            }
            if (!ALPHA_PATTERN.matcher(topLevel.substring(0, 1)).matches()) {
                return false;
            }
            if (segmentCount < 2) {
                return false;
            }
        }

        if (!hostname && !ipV4Address) {
            return false;
        }

        final String port = authorityMatcher.group(PARSE_AUTHORITY_PORT);
        if (port != null && !PORT_PATTERN.matcher(port).matches()) {
            return false;
        }
        final String extra = authorityMatcher.group(PARSE_AUTHORITY_EXTRA);
        if (!StringUtils.isBlank(extra)) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the path is valid.  A <code>null</code> value is considered invalid.
     *
     * @param path Path value to validate.
     * @return true if path is valid.
     */
    protected boolean isValidPath(final String path) {
        if (Objects.isNull(path)) {
            return false;
        }
        if (!PATH_PATTERN.matcher(path).matches()) {
            return false;
        }

        int slash2Count = countToken("//", path);
        if (this.options.isOff(ALLOW_2_SLASHES) && (slash2Count > 0)) {
            return false;
        }
        int slashCount = countToken("/", path);
        int dot2Count = countToken("..", path);
        if (dot2Count > 0 && (slashCount - slash2Count - 1) <= dot2Count) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if the query is null or it's a properly formatted query string.
     *
     * @param query Query value to validate.
     * @return true if query is valid.
     */
    protected boolean isValidQuery(final String query) {
        if (Objects.isNull(query)) {
            return true;
        }
        return QUERY_PATTERN.matcher(query).matches();
    }

    /**
     * Returns true if the given fragment is null or fragments are allowed.
     *
     * @param fragment Fragment value to validate.
     * @return true if fragment is valid.
     */
    protected boolean isValidFragment(final String fragment) {
        if (Objects.isNull(fragment)) {
            return true;
        }
        return this.options.isOff(NO_FRAGMENTS);
    }

    /**
     * Returns the number of times the token appears in the target.
     *
     * @param token  Token value to be counted.
     * @param target Target value to count tokens in.
     * @return the number of tokens.
     */
    protected int countToken(final String token, final String target) {
        int tokenIndex = 0;
        int count = 0;
        while (tokenIndex != -1) {
            tokenIndex = target.indexOf(token, tokenIndex);
            if (tokenIndex > -1) {
                tokenIndex++;
                count++;
            }
        }
        return count;
    }
}
