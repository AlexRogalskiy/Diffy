package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Modulus 10 <b>Luhn</b> Check Digit calculation/validation.
 * <p>
 * Luhn check digits are used, for example, by:
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/Credit_card">Credit Card Numbers</a></li>
 * <li><a href="http://en.wikipedia.org/wiki/IMEI">IMEI Numbers</a> - International
 * Mobile Equipment Identity Numbers</li>
 * </ul>
 * Check digit calculation is based on <i>modulus 10</i> with digits in
 * an <i>odd</i> position (from right to left) being weighted 1 and <i>even</i>
 * position digits being weighted 2 (weighted values greater than 9 have 9 subtracted).
 *
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Luhn_algorithm">Wikipedia</a>
 * for more details.
 * </p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.4
 */

/**
 * Luhn {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class LuhnDigitValidator extends BaseDigitValidator {

    /**
     * Singleton Luhn Check Digit instance
     */
    public static final DigitProcessorValidator LUHN_CHECK_DIGIT = new LuhnDigitValidator();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{2, 1};

    /**
     * Construct a modulus 10 Luhn Check Digit routine.
     */
    public LuhnDigitValidator() {
        super(10);
    }

    /**
     * <p>Calculates the <i>weighted</i> value of a charcter in the
     * code at a specified position.</p>
     *
     * <p>For Luhn (from right to left) <b>odd</b> digits are weighted
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
        int weightedValue = charValue * weight;
        return weightedValue > 9 ? (weightedValue - 9) : weightedValue;
    }
}
