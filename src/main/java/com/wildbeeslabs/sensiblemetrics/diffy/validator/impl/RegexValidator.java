package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ValidationException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Regular Expression</b> validation (using JDK 1.4+ regex support).
 * <p>
 * Construct the validator either for a single regular expression or a set (array) of
 * regular expressions. By default validation is <i>case sensitive</i> but constructors
 * are provided to allow  <i>case in-sensitive</i> validation. For example to create
 * a validator which does <i>case in-sensitive</i> validation for a set of regular
 * expressions:
 * </p>
 * <pre>
 * <code>
 * String[] regexs = new String[] {...};
 * RegexValidator validator = new RegexValidator(regexs, false);
 * </code>
 * </pre>
 *
 * <ul>
 * <li>Validate <code>true</code> or <code>false</code>:</li>
 * <li>
 * <ul>
 * <li><code>boolean valid = validator.isValid(value);</code></li>
 * </ul>
 * </li>
 * <li>Validate returning an aggregated String of the matched groups:</li>
 * <li>
 * <ul>
 * <li><code>String result = validator.validate(value);</code></li>
 * </ul>
 * </li>
 * <li>Validate returning the matched groups:</li>
 * <li>
 * <ul>
 * <li><code>String[] result = validator.match(value);</code></li>
 * </ul>
 * </li>
 * </ul>
 *
 * <b>Note that patterns are matched against the entire input.</b>
 *
 * <p>
 * Cached instances pre-compile and re-use {@link Pattern}(s) - which according
 * to the {@link Pattern} API are safe to use in a multi-threaded environment.
 * </p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.4
 */
public class RegexValidator implements Validator<String>, ThrowingProcessor<String, String, ValidationException> {

    private final Pattern[] patterns;

    /**
     * Construct a <i>case sensitive</i> validator for a single
     * regular expression.
     *
     * @param regex The regular expression this validator will
     *              validate against
     */
    public RegexValidator(final String regex) {
        this(regex, true);
    }

    /**
     * Construct a validator for a single regular expression
     * with the specified case sensitivity.
     *
     * @param regex         The regular expression this validator will
     *                      validate against
     * @param caseSensitive when <code>true</code> matching is <i>case
     *                      sensitive</i>, otherwise matching is <i>case in-sensitive</i>
     */
    public RegexValidator(final String regex, boolean caseSensitive) {
        this(new String[]{regex}, caseSensitive);
    }

    /**
     * Construct a <i>case sensitive</i> validator that matches any one
     * of the set of regular expressions.
     *
     * @param regexs The set of regular expressions this validator will
     *               validate against
     */
    public RegexValidator(final String[] regexs) {
        this(regexs, true);
    }

    /**
     * Construct a validator that matches any one of the set of regular
     * expressions with the specified case sensitivity.
     *
     * @param regexs        The set of regular expressions this validator will
     *                      validate against
     * @param caseSensitive when <code>true</code> matching is <i>case
     *                      sensitive</i>, otherwise matching is <i>case in-sensitive</i>
     */
    public RegexValidator(final String[] regexs, boolean caseSensitive) {
        if (Objects.isNull(regexs) || regexs.length == 0) {
            throw new IllegalArgumentException("Regular expressions are missing");
        }
        this.patterns = new Pattern[regexs.length];
        int flags = (caseSensitive ? 0 : Pattern.CASE_INSENSITIVE);
        for (int i = 0; i < regexs.length; i++) {
            if (Objects.isNull(regexs[i]) || regexs[i].length() == 0) {
                throw new IllegalArgumentException("Regular expression[" + i + "] is missing");
            }
            this.patterns[i] = Pattern.compile(regexs[i], flags);
        }
    }

    /**
     * Validate a value against the set of regular expressions.
     *
     * @param value The value to validate.
     * @return <code>true</code> if the value is valid
     * otherwise <code>false</code>.
     */
    @Override
    public boolean validate(final String value) {
        if (Objects.isNull(value)) {
            return false;
        }
        for (int i = 0; i < this.patterns.length; i++) {
            if (this.patterns[i].matcher(value).matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Validate a value against the set of regular expressions
     * returning the array of matched groups.
     *
     * @param value The value to validate.
     * @return String array of the <i>groups</i> matched if
     * valid or <code>null</code> if invalid
     */
    public String[] match(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        for (int i = 0; i < this.patterns.length; i++) {
            final Matcher matcher = this.patterns[i].matcher(value);
            if (matcher.matches()) {
                int count = matcher.groupCount();
                String[] groups = new String[count];
                for (int j = 0; j < count; j++) {
                    groups[j] = matcher.group(j + 1);
                }
                return groups;
            }
        }
        return null;
    }


    /**
     * Validate a value against the set of regular expressions
     * returning a String value of the aggregated groups.
     *
     * @param value The value to validate.
     * @return Aggregated String value comprised of the
     * <i>groups</i> matched if valid or <code>null</code> if invalid
     */
    @Override
    public String processOrThrow(final String value) {
        if (Objects.isNull(value)) {
            return null;
        }
        for (int i = 0; i < this.patterns.length; i++) {
            final Matcher matcher = this.patterns[i].matcher(value);
            if (matcher.matches()) {
                int count = matcher.groupCount();
                if (count == 1) {
                    return matcher.group(1);
                }
                final StringBuilder buffer = new StringBuilder();
                for (int j = 0; j < count; j++) {
                    final String component = matcher.group(j + 1);
                    if (Objects.nonNull(component)) {
                        buffer.append(component);
                    }
                }
                return buffer.toString();
            }
        }
        return null;
    }

    /**
     * Provide a String representation of this validator.
     *
     * @return A String representation of this validator
     */
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("RegexValidator{");
        for (int i = 0; i < this.patterns.length; i++) {
            if (i > 0) {
                buffer.append(",");
            }
            buffer.append(patterns[i].pattern());
        }
        buffer.append("}");
        return buffer.toString();
    }
}
