package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract <b>Modulus</b> Check digit calculation/validation.
 * <p>
 * Provides a <i>base</i> class for building <i>modulus</i> Check
 * Digit routines.
 * <p>
 * This implementation only handles <i>single-digit numeric</i> codes, such as
 * <b>EAN-13</b>. For <i>alphanumeric</i> codes such as <b>EAN-128</b> you
 * will need to implement/override the <code>toInt()</code> and
 * <code>toChar()</code> methods.
 * <p>
 *
 * @version $Revision: 1739357 $
 * @since Validator 1.4
 */

/**
 * Base {@link DigitProcessorValidator} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class BaseDigitValidator implements DigitProcessorValidator {

    // N.B. The modulus can be > 10 provided that the implementing class overrides toCheckDigit and toInt
    // (for example as in ISBN10DigitValidator)
    private final int modulus;

    /**
     * Construct modulus check digit routine for a specified modulus.
     *
     * @param modulus The modulus value to use for the check digit calculation
     */
    public BaseDigitValidator(int modulus) {
        this.modulus = modulus;
    }

    /**
     * Validate a modulus check digit for a code.
     *
     * @param code The code to validate
     * @return <code>true</code> if the check digit is valid, otherwise
     * <code>false</code>
     */
    @Override
    public boolean validate(final String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        try {
            int modulusResult = this.calculateModulus(code, true);
            return (modulusResult == 0);
        } catch (InvalidParameterException ex) {
            return false;
        }
    }

    /**
     * Calculate a modulus <i>Check Digit</i> for a code which does not yet have one.
     *
     * @param code The code for which to calculate the Check Digit;
     *             the check digit should not be included
     * @return The calculated Check Digit
     * @throws InvalidParameterException if an error occurs calculating the check digit
     */
    @Override
    public String processOrThrow(final String code) throws InvalidParameterException {
        if (StringUtils.isBlank(code)) {
            throw new InvalidParameterException("Code is missing");
        }
        int modulusResult = calculateModulus(code, false);
        int charValue = (this.modulus - modulusResult) % this.modulus;
        return this.toCheckDigit(charValue);
    }

    /**
     * Calculate the modulus for a code.
     *
     * @param code               The code to calculate the modulus for.
     * @param includesCheckDigit Whether the code includes the Check Digit or not.
     * @return The modulus value
     * @throws InvalidParameterException if an error occurs calculating the modulus
     *                                   for the specified code
     */
    protected int calculateModulus(final String code, boolean includesCheckDigit) throws InvalidParameterException {
        int total = 0;
        for (int i = 0; i < code.length(); i++) {
            int lth = code.length() + (includesCheckDigit ? 0 : 1);
            int leftPos = i + 1;
            int rightPos = lth - i;
            int charValue = toInt(code.charAt(i), leftPos, rightPos);
            total += weightedValue(charValue, leftPos, rightPos);
        }
        if (total == 0) {
            throw new InvalidParameterException("Invalid code, sum is zero");
        }
        return total % this.modulus;
    }

    /**
     * Calculates the <i>weighted</i> value of a character in the
     * code at a specified position.
     * <p>
     * Some modulus routines weight the value of a character
     * depending on its position in the code (e.g. ISBN-10), while
     * others use different weighting factors for odd/even positions
     * (e.g. EAN or Luhn). Implement the appropriate mechanism
     * required by overriding this method.
     *
     * @param charValue The numeric value of the character
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The weighted value of the character
     * @throws InvalidParameterException if an error occurs calculating
     *                                   the weighted value
     */
    protected abstract int weightedValue(int charValue, int leftPos, int rightPos) throws InvalidParameterException;

    /**
     * Convert a character at a specified position to an integer value.
     * <p>
     * <b>Note:</b> this implementation only handlers numeric values
     * For non-numeric characters, override this method to provide
     * character--&gt;integer conversion.
     *
     * @param character The character to convert
     * @param leftPos   The position of the character in the code, counting from left to right (for identifiying the position in the string)
     * @param rightPos  The position of the character in the code, counting from right to left (not used here)
     * @return The integer value of the character
     * @throws InvalidParameterException if character is non-numeric
     */
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        if (Character.isDigit(character)) {
            return Character.getNumericValue(character);
        }
        throw new InvalidParameterException("Invalid Character[" + leftPos + "] = '" + character + "'");
    }

    /**
     * Convert an integer value to a check digit.
     * <p>
     * <b>Note:</b> this implementation only handles single-digit numeric values
     * For non-numeric characters, override this method to provide
     * integer--&gt;character conversion.
     *
     * @param charValue The integer value of the character
     * @return The converted character
     * @throws InvalidParameterException if integer character value
     *                                   doesn't represent a numeric character
     */
    protected String toCheckDigit(int charValue) throws InvalidParameterException {
        if (charValue >= 0 && charValue <= 9) {
            return Integer.toString(charValue);
        }
        throw new InvalidParameterException("Invalid Check Digit Value =" + charValue);
    }

    /**
     * Add together the individual digits in a number.
     *
     * @param number The number whose digits are to be added
     * @return The sum of the digits
     */
    public static int sumDigits(int number) {
        int total = 0;
        int todo = number;
        while (todo > 0) {
            total += todo % 10;
            todo = todo / 10;
        }
        return total;
    }
}
