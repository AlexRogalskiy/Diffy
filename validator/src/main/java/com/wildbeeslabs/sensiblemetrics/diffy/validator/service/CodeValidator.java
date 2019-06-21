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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.CodeProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.RegexProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.Data;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;

import java.util.Objects;

/**
 * Generic <b>Code Validation</b> providing format, minimum/maximum
 * length and {@link CheckDigit} validations.
 * <p>
 * Performs the following validations on a code:
 * <ul>
 * <li>if the code is null, return null/false as appropriate</li>
 * <li>trim the input. If the resulting code is empty, return null/false as appropriate</li>
 * <li>Check the <i>format</i> of the code using a <i>regular expression.</i> (if specified)</li>
 * <li>Check the <i>minimum</i> and <i>maximum</i> length  (if specified) of the <i>parsed</i> code
 * (i.e. parsed by the <i>regular expression</i>).</li>
 * <li>Performs {@link CheckDigit} validation on the parsed code (if specified).</li>
 * <li>The {@link #validate(String)} method returns the trimmed, parsed input (or null if validation failed)</li>
 * </ul>
 * <p>
 * <b>Note</b>
 * The {@link #validate(String)} method will return true if the input passes validation.
 * Since this includes trimming as well as potentially dropping parts of the input,
 * it is possible for a String to pass validation
 * but fail the checkdigit test if passed directly to it (the check digit routines generally don't trim input
 * nor do they generally check the format/length).
 * To be sure that you are passing valid input to a method use {@link #validate(String)} as follows:
 * <pre>
 * Object valid = validator.validate(input);
 * if (valid != null) {
 *    some_method(valid.toString());
 * }
 * </pre>
 * <p>
 * Configure the validator with the appropriate regular expression, minimum/maximum length
 * and {@link CheckDigit} validator and then call one of the two validation
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
public final class CodeValidator implements Validator<String> {

    /**
     * Default {@link CodeProcessor} instance
     */
    private final CodeProcessor processor;

    /**
     * Construct a code validator with a specified regular
     * expression and {@link CheckDigit}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The format regular expression
     * @param validator The check digit validation routine
     */
    public CodeValidator(final String regex, final DigitValidator validator) {
        this(regex, -1, -1, validator);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, length and {@link CheckDigit}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The format regular expression.
     * @param length    The length of the code
     *                  (sets the mimimum/maximum to the same)
     * @param validator The check digit validation routine
     */
    public CodeValidator(final String regex, int length, final DigitValidator validator) {
        this(regex, length, length, validator);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, minimum/maximum length and {@link CheckDigit} validation.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex     The regular expression
     * @param minLength The minimum length of the code
     * @param maxLength The maximum length of the code
     * @param validator The check digit validation routine
     */
    public CodeValidator(final String regex, int minLength, int maxLength, final DigitValidator validator) {
        this.processor = new CodeProcessor(regex, minLength, maxLength, validator);
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator and {@link CheckDigit} validation.
     *
     * @param processor The format regular expression processor
     * @param validator The check digit validation routine.
     */
    public CodeValidator(final RegexProcessor processor, final DigitValidator validator) {
        this(processor, -1, -1, validator);
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator, length and {@link CheckDigit} validation.
     *
     * @param processor The format regular expression processor
     * @param length    The length of the code
     *                  (sets the mimimum/maximum to the same value)
     * @param validator The check digit validation routine
     */
    public CodeValidator(final RegexProcessor processor, int length, final DigitValidator validator) {
        this(processor, length, length, validator);
    }

    /**
     * Construct a code validator with a specified regular expression
     * validator, minimum/maximum length and {@link CheckDigit} validation.
     *
     * @param processor The format regular expression processor
     * @param minLength The minimum length of the code
     * @param maxLength The maximum length of the code
     * @param validator The check digit validation routine
     */
    public CodeValidator(final RegexProcessor processor, int minLength, int maxLength, final DigitValidator validator) {
        this.processor = new CodeProcessor(processor, minLength, maxLength, validator);
    }

    /**
     * Validate the code returning either <code>true</code>
     * or <code>false</code>.
     * <p>
     * This calls {@link #validate(String)} and returns false
     * if the return value is null, true otherwise.
     * <p>
     * Note that {@link #validate(String)} trims the input
     * and if there is a {@link RegexValidator} it may also
     * change the input as part of the validation.
     *
     * @param input The code to validate
     * @return <code>true</code> if valid, otherwise
     * <code>false</code>
     */
    @Override
    public boolean validate(final String input) {
        return Objects.nonNull(this.getProcessor().processOrThrow(input));
    }
}
