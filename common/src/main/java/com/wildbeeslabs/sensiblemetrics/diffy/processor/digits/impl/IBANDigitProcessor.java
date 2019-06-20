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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.iface.DigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.BaseDigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * <b>IBAN</b> (International Bank Account Number) Check Digit calculation/validation.
 * <p>
 * This routine is based on the ISO 7064 Mod 97,10 check digit calculation routine.
 * <p>
 * The two check digit characters in a IBAN number are the third and fourth characters
 * in the code. For <i>check digit</i> calculation/validation the first four characters are moved
 * to the end of the code.
 * So <code>CCDDnnnnnnn</code> becomes <code>nnnnnnnCCDD</code> (where
 * <code>CC</code> is the country code and <code>DD</code> is the check digit). For
 * check digit calculation the check digit value should be set to zero (i.e.
 * <code>CC00nnnnnnn</code> in this example.
 * <p>
 * Note: the class does not check the format of the IBAN number, only the check digits.
 * <p>
 * For further information see
 * <a href="http://en.wikipedia.org/wiki/International_Bank_Account_Number">Wikipedia -
 * IBAN number</a>.
 *
 * @version $Revision: 1739357 $
 * @since Validator 1.4
 */

/**
 * Iban {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public final class IBANDigitProcessor implements DigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -2670530817909099894L;

    private static final int MIN_CODE_LEN = 5;

    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Character.getNumericValue('Z')

    private static final long MAX = 999999999;

    private static final long MODULUS = 97;

    /**
     * Calculate the <i>Check Digit</i> for an IBAN code.
     * <p>
     * <b>Note:</b> The check digit is the third and fourth
     * characters and is set to the value "<code>00</code>".
     *
     * @param code The code to calculate the Check Digit for
     * @return The calculated Check Digit as 2 numeric decimal characters, e.g. "42"
     * @throws InvalidParameterException if an error occurs calculating
     *                                   the check digit for the specified code
     */
    @Override
    public String processOrThrow(final String code) throws InvalidParameterException {
        if (Objects.isNull(code) || code.length() < MIN_CODE_LEN) {
            throw new InvalidParameterException("Invalid Code length=" + (code == null ? 0 : code.length()));
        }
        final String newCode = code.substring(0, 2) + "00" + code.substring(4); // CHECKSTYLE IGNORE MagicNumber
        final int modulusResult = this.calculateModulus(newCode);
        final int charValue = (98 - modulusResult); // CHECKSTYLE IGNORE MagicNumber
        final String checkDigit = Integer.toString(charValue);
        return (charValue > 9 ? checkDigit : "0" + checkDigit); // CHECKSTYLE IGNORE MagicNumber
    }

    /**
     * Calculate the modulus for a code.
     *
     * @param code The code to calculate the modulus for.
     * @return The modulus value
     * @throws InvalidParameterException if an error occurs calculating the modulus
     *                                   for the specified code
     */
    public int calculateModulus(final String code) throws InvalidParameterException {
        final String reformattedCode = code.substring(4) + code.substring(0, 4); // CHECKSTYLE IGNORE MagicNumber
        long total = 0;
        for (int i = 0; i < reformattedCode.length(); i++) {
            int charValue = Character.getNumericValue(reformattedCode.charAt(i));
            if (charValue < 0 || charValue > MAX_ALPHANUMERIC_VALUE) {
                throw new InvalidParameterException("Invalid Character[" + i + "] = '" + charValue + "'");
            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue; // CHECKSTYLE IGNORE MagicNumber
            if (total > MAX) {
                total = total % MODULUS;
            }
        }
        return (int) (total % MODULUS);
    }
}
