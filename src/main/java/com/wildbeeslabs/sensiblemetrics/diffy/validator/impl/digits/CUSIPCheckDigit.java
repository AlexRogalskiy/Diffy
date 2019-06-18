package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.digits;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.DigitValidator;

public final class CUSIPCheckDigit extends BaseDigitValidator {

    /**
     * Singleton CUSIP Check Digit instance
     */
    public static final DigitValidator CUSIP_CHECK_DIGIT = new CUSIPCheckDigit();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{2, 1};

    /**
     * Construct an CUSIP Indetifier Check Digit routine.
     */
    public CUSIPCheckDigit() {
        super(10); // CHECKSTYLE IGNORE MagicNumber
    }

    /**
     * Convert a character at a specified position to an integer value.
     *
     * @param character The character to convert
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The position of the character in the code, counting from right to left
     * @return The integer value of the character
     * @throws InvalidParameterException if character is not alphanumeric
     */
    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        int charValue = Character.getNumericValue(character);
        // the final character is only allowed to reach 9
        final int charMax = rightPos == 1 ? 9 : 35;  // CHECKSTYLE IGNORE MagicNumber
        if (charValue < 0 || charValue > charMax) {
            throw new InvalidParameterException("Invalid Character[" + leftPos + "," + rightPos + "] = '" + charValue + "' out of range 0 to " + charMax);
        }
        return charValue;
    }

    /**
     * <p>Calculates the <i>weighted</i> value of a charcter in the
     * code at a specified position.</p>
     *
     * <p>For CUSIP (from right to left) <b>odd</b> digits are weighted
     * with a factor of <b>one</b> and <b>even</b> digits with a factor
     * of <b>two</b>. Weighted values &gt; 9, have 9 subtracted</p>
     *
     * @param charValue The numeric value of the character.
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The weighted value of the character.
     */
    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        int weight = POSITION_WEIGHT[rightPos % 2];
        int weightedValue = (charValue * weight);
        return sumDigits(weightedValue);
    }
}
