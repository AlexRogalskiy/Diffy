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
package com.wildbeeslabs.sensiblemetrics.diffy.processor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidFormatException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.GenericProcessorValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.RegexValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Generic <b>Code Validation</b> providing format, minimum/maximum
 * length and {@link GenericProcessorValidator} validations.
 * <p>
 * Performs the following validations on a code:
 * <ul>
 * <li>if the code is null, return null/false as appropriate</li>
 * <li>trim the input. If the resulting code is empty, return null/false as appropriate</li>
 * <li>Check the <i>format</i> of the code using a <i>regular expression.</i> (if specified)</li>
 * <li>Check the <i>minimum</i> and <i>maximum</i> length  (if specified) of the <i>parsed</i> code
 * (i.e. parsed by the <i>regular expression</i>).</li>
 * <li>Performs {@link GenericProcessorValidator} validation on the parsed code (if specified).</li>
 * <li>The {@link #processOrThrow(String)} method returns the trimmed, parsed input (or null if validation failed)</li>
 * </ul>
 * <p>
 * <b>Note</b>
 * The {@link #processOrThrow(String)} method will return true if the input passes validation.
 * Since this includes trimming as well as potentially dropping parts of the input,
 * it is possible for a String to pass validation
 * but fail the checkdigit test if passed directly to it (the check digit routines generally don't trim input
 * nor do they generally check the format/length).
 * To be sure that you are passing valid input to a method use {@link #processOrThrow(String)} as follows:
 * <pre>
 * Object valid = validator.validate(input);
 * if (valid != null) {
 *    some_method(valid.toString());
 * }
 * </pre>
 * <p>
 * Configure the validator with the appropriate regular expression, minimum/maximum length
 * and {@link GenericProcessorValidator} validator and then call one of the two validation
 * methods provided:</p>
 * <ul>
 * <li><code>boolean isValid(code)</code></li>
 * <li><code>String validate(code)</code></li>
 * </ul>
 * <p>
 * Codes often include <i>format</i> characters - such as hyphens - to make them
 * more easily human readable. These can be removed prior to length and check digit
 * validation by  specifying them as a <i>non-capturing</i> group in the regular
 * expression (i.e. use the <code>(?:   )</code> notation).
 * <br>
 * Or just avoid using parentheses except for the parts you want to capture
 *
 * @version $Revision: 1781789 $
 * @since Validator 1.4
 */
@Data
@EqualsAndHashCode
@ToString
public final class CodeProcessor implements ThrowingProcessor<String, Object, InvalidFormatException>, Serializable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4508144749847080016L;

    /**
     * Default min length
     */
    private final int minLength;
    /**
     * Default max length
     */
    private final int maxLength;
    private final DigitProcessorValidator validator;
    private final RegexValidator regexValidator;

    /**
     * Construct a code validator with a specified regular
     * expression and {@link GenericProcessorValidator}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The format regular expression
     * @param validator The check digit validation routine
     */
    public CodeProcessor(final String regex, final DigitProcessorValidator validator) {
        this(regex, -1, -1, validator);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, length and {@link GenericProcessorValidator}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The format regular expression.
     * @param length    The length of the code
     *                  (sets the mimimum/maximum to the same)
     * @param validator The check digit validation routine
     */
    public CodeProcessor(final String regex, int length, final DigitProcessorValidator validator) {
        this(regex, length, length, validator);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, minimum/maximum length and {@link GenericProcessorValidator} validation.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The regular expression
     * @param minLength The minimum length of the code
     * @param maxLength The maximum length of the code
     * @param validator The check digit validation routine
     */
    public CodeProcessor(final String regex, int minLength, int maxLength, final DigitProcessorValidator validator) {
        if (StringUtils.isNotBlank(regex)) {
            this.regexValidator = new RegexValidator(regex);
        } else {
            this.regexValidator = null;
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validator = validator;
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator and {@link GenericProcessorValidator} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param validator      The check digit validation routine.
     */
    public CodeProcessor(final RegexValidator regexValidator, final DigitProcessorValidator validator) {
        this(regexValidator, -1, -1, validator);
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator, length and {@link GenericProcessorValidator} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param length         The length of the code
     *                       (sets the mimimum/maximum to the same value)
     * @param validator      The check digit validation routine
     */
    public CodeProcessor(final RegexValidator regexValidator, int length, final DigitProcessorValidator validator) {
        this(regexValidator, length, length, validator);
    }

    /**
     * Construct a code validator with a specified regular expression
     * validator, minimum/maximum length and {@link GenericProcessorValidator} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param minLength      The minimum length of the code
     * @param maxLength      The maximum length of the code
     * @param validator      The check digit validation routine
     */
    public CodeProcessor(final RegexValidator regexValidator, int minLength, int maxLength, final DigitProcessorValidator validator) {
        this.regexValidator = regexValidator;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.validator = validator;
    }

    /**
     * Validate the code returning either the valid code or
     * <code>null</code> if invalid.
     * <p>
     * Note that this method trims the input
     * and if there is a {@link RegexValidator} it may also
     * change the input as part of the validation.
     *
     * @param input The code to validate
     * @return The code if valid, otherwise <code>null</code>
     * if invalid
     */
    @Override
    public Object processOrThrow(final String input) {
        if (Objects.isNull(input)) {
            return null;
        }
        String code = input.trim();
        if (code.length() == 0) {
            return null;
        }

        // validate/reformat using regular expression
        if (Objects.nonNull(this.regexValidator)) {
            code = this.regexValidator.process(code);
            if (Objects.isNull(code)) {
                return null;
            }
        }
        // check the length (must be done after validate as that can change the code)
        if ((this.minLength >= 0 && code.length() < this.minLength) || (this.maxLength >= 0 && code.length() > this.maxLength)) {
            return null;
        }
        try {
            if (Objects.nonNull(this.validator) && !this.validator.validate(code)) {
                return null;
            }
        } catch (Throwable t) {
            throw new InvalidFormatException(String.format("ERROR: invalid input parameter = {%s}", input), t);
        }
        return code;
    }
}
