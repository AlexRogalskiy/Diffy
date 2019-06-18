package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * Combined <b>ISBN-10</b> / <b>ISBN-13</b> Check Digit calculation/validation.
 * <p>
 * This implementation validates/calculates ISBN check digits
 * based on the length of the code passed to it - delegating
 * either to the {@link ISBNDigitValidator#ISBN10_CHECK_DIGIT} or the
 * {@link ISBNDigitValidator#ISBN13_CHECK_DIGIT} routines to perform the actual
 * validation/calculation.
 * <p>
 * <b>N.B.</b> From 1st January 2007 the book industry will start to use a new 13 digit
 * ISBN number (rather than this 10 digit ISBN number) which uses the EAN-13 / UPC
 * standard.
 *
 * @version $Revision: 1739357 $
 * @since Validator 1.4
 */

/**
 * Isbn {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public final class ISBNDigitValidator implements DigitProcessorValidator {

    /**
     * Singleton ISBN-10 Check Digit instance
     */
    public static final DigitProcessorValidator ISBN10_CHECK_DIGIT = ISBN10DigitValidator.ISBN10_CHECK_DIGIT;

    /**
     * Singleton ISBN-13 Check Digit instance
     */
    public static final DigitProcessorValidator ISBN13_CHECK_DIGIT = EAN13DigitValidator.EAN13_CHECK_DIGIT;

    /**
     * Singleton combined ISBN-10 / ISBN-13 Check Digit instance
     */
    public static final DigitProcessorValidator ISBN_CHECK_DIGIT = new ISBNDigitValidator();

    /**
     * Calculate an ISBN-10 or ISBN-13 check digit, depending
     * on the length of the code.
     * <p>
     * If the length of the code is 9, it is treated as an ISBN-10
     * code or if the length of the code is 12, it is treated as an ISBN-13
     * code.
     *
     * @param code The ISBN code to validate (should have a length of
     *             9 or 12)
     * @return The ISBN-10 check digit if the length is 9 or an ISBN-13
     * check digit if the length is 12.
     * @throws InvalidParameterException if the code is missing, or an invalid
     *                                   length (i.e. not 9 or 12) or if there is an error calculating the
     *                                   check digit.
     */
    @Override
    public String processOrThrow(final String code) throws InvalidParameterException {
        if (StringUtils.isBlank(code)) {
            throw new InvalidParameterException("ISBN Code is missing");
        } else if (code.length() == 9) { // CHECKSTYLE IGNORE MagicNumber
            return ISBN10_CHECK_DIGIT.processOrThrow(code);
        } else if (code.length() == 12) { // CHECKSTYLE IGNORE MagicNumber
            return ISBN13_CHECK_DIGIT.processOrThrow(code);
        } else {
            throw new InvalidParameterException("Invalid ISBN Length = " + code.length());
        }
    }

    /**
     * <p>Validate an ISBN-10 or ISBN-13 check digit, depending
     * on the length of the code.</p>
     * <p>
     * If the length of the code is 10, it is treated as an ISBN-10
     * code or ff the length of the code is 13, it is treated as an ISBN-13
     * code.
     *
     * @param code The ISBN code to validate (should have a length of
     *             10 or 13)
     * @return <code>true</code> if the code has a length of 10 and is
     * a valid ISBN-10 check digit or the code has a length of 13 and is
     * a valid ISBN-13 check digit - otherwise <code>false</code>.
     */
    @Override
    public boolean validate(final String code) throws Throwable {
        if (StringUtils.isBlank(code)) {
            return false;
        } else if (code.length() == 10) { // CHECKSTYLE IGNORE MagicNumber
            return ISBN10_CHECK_DIGIT.validate(code);
        } else if (code.length() == 13) { // CHECKSTYLE IGNORE MagicNumber
            return ISBN13_CHECK_DIGIT.validate(code);
        } else {
            return false;
        }
    }
}
