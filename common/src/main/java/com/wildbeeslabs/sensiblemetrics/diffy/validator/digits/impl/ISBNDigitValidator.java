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

import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
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
public final class ISBNDigitValidator implements DigitValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 4948622299764254013L;

    /**
     * Singleton ISBN-10 Check Digit instance
     */
    public static final DigitValidator ISBN10_CHECK_DIGIT = ISBN10DigitValidator.getInstance();

    /**
     * Singleton ISBN-13 Check Digit instance
     */
    public static final DigitValidator ISBN13_CHECK_DIGIT = EAN13DigitValidator.getInstance();

    /**
     * Singleton combined ISBN-10 / ISBN-13 Check Digit instance
     */
    private static final DigitValidator ISBN_CHECK_DIGIT = new ISBNDigitValidator();

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
        } else if (code.length() == 10) {
            return ISBN10_CHECK_DIGIT.validate(code);
        } else if (code.length() == 13) {
            return ISBN13_CHECK_DIGIT.validate(code);
        } else {
            return false;
        }
    }

    /**
     * Returns {@link DigitValidator} instance
     *
     * @return {@link DigitValidator} instance
     */
    public static DigitValidator getInstance() {
        return ISBN_CHECK_DIGIT;
    }
}
