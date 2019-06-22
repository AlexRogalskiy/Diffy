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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl.IBANDigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.security.InvalidParameterException;

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
public final class IBANDigitValidator implements DigitValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 276277976729224940L;

    /**
     * Singleton IBAN Check Digit instance
     */
    private static final DigitValidator IBAN_CHECK_DIGIT = new IBANDigitValidator();

    private static final int MIN_CODE_LEN = 5;

    /**
     * Default {@link IBANDigitProcessor} instance
     */
    private final IBANDigitProcessor processor;

    public IBANDigitValidator() {
        this.processor = new IBANDigitProcessor();
    }

    /**
     * Validate the check digit of an IBAN code.
     *
     * @param code The code to validate
     * @return <code>true</code> if the check digit is valid, otherwise
     * <code>false</code>
     */
    @Override
    public boolean validate(final String code) {
        if (code == null || code.length() < MIN_CODE_LEN) {
            return false;
        }
        String check = code.substring(2, 4);
        if ("00".equals(check) || "01".equals(check) || "99".equals(check)) {
            return false;
        }
        try {
            int modulusResult = this.getProcessor().calculateModulus(code);
            return (modulusResult == 1);
        } catch (InvalidParameterException ex) {
            return false;
        }
    }

    /**
     * Returns {@link DigitValidator} instance
     *
     * @return {@link DigitValidator} instance
     */
    public static DigitValidator getInstance() {
        return IBAN_CHECK_DIGIT;
    }
}
