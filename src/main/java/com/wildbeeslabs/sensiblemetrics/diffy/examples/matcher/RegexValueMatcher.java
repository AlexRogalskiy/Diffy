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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.BiMatcherException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.PatternType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.DefaultBiMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Custom regular expression {@link BiMatcher} implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegexValueMatcher<T> extends DefaultBiMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8015934407469822842L;

    /**
     * Default expected pattern {@link Pattern}
     */
    private final Pattern expectedPattern;

    /**
     * Default regular expression value matcher constructor
     */
    public RegexValueMatcher() {
        this(null);
    }

    /**
     * Default regular expression value matcher constructor
     *
     * @param pattern - initial input pattern {@link String}
     * @throws IllegalArgumentException if pattern is non-null and not a valid regular expression.
     */
    public RegexValueMatcher(final String pattern) throws IllegalArgumentException {
        Objects.requireNonNull(pattern, "Pattern should not be null");
        this.expectedPattern = this.getPattern(pattern);
    }

    /**
     * Returns {@link Pattern} by input pattern {@link String}
     *
     * @param pattern - initial input pattern {@link String}
     * @return {@link Pattern}
     */
    private Pattern getPattern(final String pattern) {
        try {
            return Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            InvalidParameterException.throwInvalidParameter(String.format("ERROR: invalid pattern = {%s}, message = {%s}", pattern, e.getMessage()), e);
        }
        return null;
    }

    /**
     * Compares the two provided objects whether they are equal
     *
     * @param actual   - initial input actual value {@code T}
     * @param expected - initial input expected value {@code T}
     * @return true - if objects {@code T} are equal, false - otherwise
     */
    @Override
    public boolean matches(final T actual, final T expected) {
        final String actualString = actual.toString();
        final String expectedString = expected.toString();
        try {
            final Pattern pattern = isStaticPattern() ? this.expectedPattern : Pattern.compile(expectedString);
            if (!pattern.matcher(actualString).matches()) {
                throw new BiMatcherException(String.format("ERROR: expected pattern with type = {%s} did not match value", this.getPatternType()), pattern.toString(), actualString);
            }
        } catch (PatternSyntaxException e) {
            throw new BiMatcherException(String.format("ERROR: expected pattern with type = {%s} is invalid, message ={%s} ", this.getPatternType(), e.getMessage()), e, expectedString, actualString);
        }
        return true;
    }

    /**
     * Returns binary flag by expected pattern static pattern
     *
     * @return true - if expected pattern is static, false - otherwise
     */
    private boolean isStaticPattern() {
        return Objects.nonNull(this.expectedPattern);
    }

    /**
     * Returns {@link PatternType} by current static pattern flag
     *
     * @return {@link PatternType}
     */
    private PatternType getPatternType() {
        return this.isStaticPattern() ? PatternType.STATIC : PatternType.DYNAMIC;
    }
}
