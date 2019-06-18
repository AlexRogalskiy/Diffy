package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import lombok.Data;
import org.apache.commons.validator.ValidatorException;
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
public final class CodeValidator implements Validator<String>, ThrowingProcessor<String, Object, ValidatorException> {

    private final RegexValidator regexValidator;
    private final int minLength;
    private final int maxLength;
    private final CheckDigit checkdigit;

    /**
     * Construct a code validator with a specified regular
     * expression and {@link CheckDigit}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex      The format regular expression
     * @param checkdigit The check digit validation routine
     */
    public CodeValidator(final String regex, final CheckDigit checkdigit) {
        this(regex, -1, -1, checkdigit);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, length and {@link CheckDigit}.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex      The format regular expression.
     * @param length     The length of the code
     *                   (sets the mimimum/maximum to the same)
     * @param checkdigit The check digit validation routine
     */
    public CodeValidator(final String regex, int length, final CheckDigit checkdigit) {
        this(regex, length, length, checkdigit);
    }

    /**
     * Construct a code validator with a specified regular
     * expression, minimum/maximum length and {@link CheckDigit} validation.
     * The RegexValidator validator is created to be case-sensitive
     *
     * @param regex      The regular expression
     * @param minLength  The minimum length of the code
     * @param maxLength  The maximum length of the code
     * @param checkdigit The check digit validation routine
     */
    public CodeValidator(final String regex, int minLength, int maxLength, final CheckDigit checkdigit) {
        if (Objects.nonNull(regex) && regex.length() > 0) {
            this.regexValidator = new RegexValidator(regex);
        } else {
            this.regexValidator = null;
        }
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.checkdigit = checkdigit;
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator and {@link CheckDigit} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param checkdigit     The check digit validation routine.
     */
    public CodeValidator(final RegexValidator regexValidator, final CheckDigit checkdigit) {
        this(regexValidator, -1, -1, checkdigit);
    }

    /**
     * Construct a code validator with a specified regular expression,
     * validator, length and {@link CheckDigit} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param length         The length of the code
     *                       (sets the mimimum/maximum to the same value)
     * @param checkdigit     The check digit validation routine
     */
    public CodeValidator(final RegexValidator regexValidator, int length, final CheckDigit checkdigit) {
        this(regexValidator, length, length, checkdigit);
    }

    /**
     * Construct a code validator with a specified regular expression
     * validator, minimum/maximum length and {@link CheckDigit} validation.
     *
     * @param regexValidator The format regular expression validator
     * @param minLength      The minimum length of the code
     * @param maxLength      The maximum length of the code
     * @param checkdigit     The check digit validation routine
     */
    public CodeValidator(final RegexValidator regexValidator, int minLength, int maxLength, final CheckDigit checkdigit) {
        this.regexValidator = regexValidator;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.checkdigit = checkdigit;
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
        return Objects.nonNull(this.process(input));
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
        // validate the check digit
        if (Objects.nonNull(this.checkdigit) && !this.checkdigit.isValid(code)) {
            return null;
        }
        return code;
    }
}
