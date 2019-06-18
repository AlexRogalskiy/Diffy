package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitProcessorValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Modulus 10 <b>EAN-13</b> / <b>UPC</b> / <b>ISBN-13</b> Check Digit
 * calculation/validation.
 * <p>
 * Check digit calculation is based on <i>modulus 10</i> with digits in
 * an <i>odd</i> position (from right to left) being weighted 1 and <i>even</i>
 * position digits being weighted 3.
 * <p>
 * For further information see:
 * <ul>
 * <li>EAN-13 - see
 * <a href="http://en.wikipedia.org/wiki/European_Article_Number">Wikipedia -
 * European Article Number</a>.</li>
 * <li>UPC - see
 * <a href="http://en.wikipedia.org/wiki/Universal_Product_Code">Wikipedia -
 * Universal Product Code</a>.</li>
 * <li>ISBN-13 - see
 * <a href="http://en.wikipedia.org/wiki/ISBN">Wikipedia - International
 * Standard Book Number (ISBN)</a>.</li>
 * </ul>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.4
 */

/**
 * Ean13 {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class EAN13DigitValidator extends BaseDigitValidator {

    /**
     * Singleton EAN-13 Check Digit instance
     */
    public static final DigitProcessorValidator EAN13_CHECK_DIGIT = new EAN13DigitValidator();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{3, 1};

    /**
     * Construct a modulus 10 Check Digit routine for EAN/UPC.
     */
    public EAN13DigitValidator() {
        super(10);
    }

    /**
     * <p>Calculates the <i>weighted</i> value of a character in the
     * code at a specified position.</p>
     *
     * <p>For EAN-13 (from right to left) <b>odd</b> digits are weighted
     * with a factor of <b>one</b> and <b>even</b> digits with a factor
     * of <b>three</b>.</p>
     *
     * @param charValue The numeric value of the character.
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The weighted value of the character.
     */
    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        int weight = POSITION_WEIGHT[rightPos % 2];
        return charValue * weight;
    }
}
