package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Modulus 10 <b>SEDOL</b> (UK Securities) Check Digit calculation/validation.
 *
 * <p>
 * SEDOL Numbers are 7 character alphanumeric codes used
 * to identify UK Securities (SEDOL stands for Stock Exchange Daily Official List).
 * </p>
 * <p>
 * Check digit calculation is based on <i>modulus 10</i> with digits being weighted
 * based on their position, from left to right, as follows:
 * </p>
 * <pre><code>
 *      position:  1  2  3  4  5  6  7
 *     weighting:  1  3  1  7  3  9  1
 * </code></pre>
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/SEDOL">Wikipedia - SEDOL</a>
 * for more details.
 * </p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.4
 */

/**
 * Sedol {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class SedolDigitValidator extends BaseDigitValidator {

    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Character.getNumericValue('Z')

    /**
     * Singleton SEDOL check digit instance
     */
    public static final DigitProcessorValidator SEDOL_CHECK_DIGIT = new SedolDigitValidator();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{1, 3, 1, 7, 3, 9, 1};

    /**
     * Construct a modulus 11 Check Digit routine for ISBN-10.
     */
    public SedolDigitValidator() {
        super(10);
    }

    /**
     * Calculate the modulus for an SEDOL code.
     *
     * @param code               The code to calculate the modulus for.
     * @param includesCheckDigit Whether the code includes the Check Digit or not.
     * @return The modulus value
     * @throws InvalidParameterException if an error occurs calculating the modulus
     *                                   for the specified code
     */
    @Override
    protected int calculateModulus(final String code, boolean includesCheckDigit) throws InvalidParameterException {
        if (code.length() > POSITION_WEIGHT.length) {
            throw new InvalidParameterException("Invalid Code Length = " + code.length());
        }
        return super.calculateModulus(code, includesCheckDigit);
    }

    /**
     * Calculates the <i>weighted</i> value of a charcter in the
     * code at a specified position.
     *
     * @param charValue The numeric value of the character.
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The weighted value of the character.
     */
    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        return charValue * POSITION_WEIGHT[leftPos - 1];
    }

    /**
     * Convert a character at a specified position to an integer value.
     *
     * @param character The character to convert
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The integer value of the character
     * @throws InvalidParameterException if character is not alphanumeric
     */
    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        int charValue = Character.getNumericValue(character);
        // the check digit is only allowed to reach 9
        final int charMax = rightPos == 1 ? 9 : MAX_ALPHANUMERIC_VALUE;
        if (charValue < 0 || charValue > charMax) {
            throw new InvalidParameterException("Invalid Character[" + leftPos + "," + rightPos + "] = '" + charValue + "' out of range 0 to " + charMax);
        }
        return charValue;
    }
}
