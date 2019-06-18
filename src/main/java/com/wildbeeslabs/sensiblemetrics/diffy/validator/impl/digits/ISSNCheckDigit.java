package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.digits;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.DigitValidator;

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
 * For example, abcd-efg would be 8a + 7b + 6c + 5d + 4e +3f +2g.
 * The check digit is modulus 11, where the value 10 is represented by 'X'
 * For example:
 * ISSN 0317-8471
 * ISSN 1050-124X
 * </pre>
 * <p>
 * <b>Note:</b> This class expects the input to be numeric only,
 * with all formatting removed.
 * For example:
 * <pre>
 * 03178471
 * 1050124X
 * </pre>
 *
 * @since 1.5.0
 */
public final class ISSNCheckDigit extends BaseDigitValidator {

    /**
     * Singleton ISSN Check Digit instance
     */
    public static final DigitValidator ISSN_CHECK_DIGIT = new ISSNCheckDigit();

    /**
     * Creates the instance using a checkdigit modulus of 11
     */
    public ISSNCheckDigit() {
        super(11); // CHECKSTYLE IGNORE MagicNumber
    }

    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) throws InvalidParameterException {
        return charValue * (9 - leftPos);
    }

    @Override
    protected String toCheckDigit(int charValue) throws InvalidParameterException {
        if (charValue == 10) {
            return "X";
        }
        return super.toCheckDigit(charValue);
    }

    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        if (rightPos == 1 && character == 'X') {
            return 10;
        }
        return super.toInt(character, leftPos, rightPos);
    }
}
