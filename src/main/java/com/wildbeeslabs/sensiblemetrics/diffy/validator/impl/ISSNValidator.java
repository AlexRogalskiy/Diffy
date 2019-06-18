package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ValidationException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;
import org.apache.commons.validator.routines.CodeValidator;
import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.apache.commons.validator.routines.checkdigit.ISSNCheckDigit;

import java.util.Objects;

/**
 * International Standard Serial Number (ISSN)
 * is an eight-digit serial number used to
 * uniquely identify a serial publication.
 * <pre>
 * The format is:
 *
 * ISSN dddd-dddC
 * where:
 * d = decimal digit (0-9)
 * C = checksum (0-9 or X)
 *
 * The checksum is formed by adding the first 7 digits multiplied by
 * the position in the entire number (counting from the right).
 *
 * For example, abcd-efg would be 8a + 7b + 6c + 5d + 4e +3f +2g.
 * The check digit is modulus 11, where the value 10 is represented by 'X'
 * For example:
 * ISSN 0317-8471
 * ISSN 1050-124X
 *
 * This class strips off the 'ISSN ' prefix if it is present before passing
 * the remainder to the checksum routine.
 *
 * </pre>
 * <p>
 * Note: the {@link #validate(String)} methods strip off any leading
 * or trailing spaces before doing the validation.
 * To ensure that only a valid code (without 'ISSN ' prefix) is passed to a method,
 * use the following code:
 * <pre>
 * Object valid = validator.validate(input);
 * if (valid != null) {
 *    some_method(valid.toString());
 * }
 * </pre>
 *
 * @since 1.5.0
 */
public class ISSNValidator implements Validator<String>, ThrowingProcessor<String, Object, ValidationException> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -7500338388453927189L;

    /**
     * Default issn regex
     */
    private static final String ISSN_REGEX = "(?:ISSN )?(\\d{4})-(\\d{3}[0-9X])$"; // We don't include the '-' in the code, so it is 8 chars

    /**
     * Default {@link CodeValidator} instance
     */
    private static final CodeValidator VALIDATOR = new CodeValidator(ISSN_REGEX, 8, ISSNCheckDigit.ISSN_CHECK_DIGIT);

    /**
     * Default {@link ISSNValidator} instance
     */
    private static final ISSNValidator ISSN_VALIDATOR = new ISSNValidator();

    /**
     * Return a singleton instance of the ISSN validator
     *
     * @return A singleton instance of the ISSN validator.
     */
    public static ISSNValidator getInstance() {
        return ISSN_VALIDATOR;
    }

    /**
     * Check the code is a valid ISSN code after any transformation
     * by the validate routine.
     *
     * @param code The code to validate.
     * @return <code>true</code> if a valid ISSN
     * code, otherwise <code>false</code>.
     */
    @Override
    public boolean validate(final String code) {
        return VALIDATOR.isValid(code);
    }

    /**
     * Returns validation result {@link Object} by input parameters
     *
     * @param code - initial input {@link String} value to validate
     * @return validation result {@link Object}
     */
    @Override
    public Object processOrThrow(final String code) {
        return VALIDATOR.validate(code);
    }

    /**
     * Convert an ISSN code to an EAN-13 code.
     * <p>
     * This method requires a valid ISSN code.
     * It may contain a leading 'ISSN ' prefix,
     * as the input is passed through the {@link #validate(String)}
     * method.
     *
     * @param issn   The ISSN code to convert
     * @param suffix the two digit suffix, e.g. "00"
     * @return A converted EAN-13 code or <code>null</code>
     * if the input ISSN code is not valid
     */
    public String convertToEAN13(final String issn, final String suffix) {
        if (Objects.isNull(suffix) || !suffix.matches("\\d\\d")) {
            throw new IllegalArgumentException("Suffix must be two digits: '" + suffix + "'");
        }
        final Object result = validate(issn);
        if (Objects.isNull(result)) {
            return null;
        }
        final String input = result.toString();
        String ean13 = "977" + input.substring(0, input.length() - 1) + suffix;
        try {
            String checkDigit = EAN13CheckDigit.EAN13_CHECK_DIGIT.calculate(ean13);
            ean13 += checkDigit;
            return ean13;
        } catch (CheckDigitException e) { // Should not happen
            throw new IllegalArgumentException("Check digit error for '" + ean13 + "' - " + e.getMessage());
        }
    }
}
