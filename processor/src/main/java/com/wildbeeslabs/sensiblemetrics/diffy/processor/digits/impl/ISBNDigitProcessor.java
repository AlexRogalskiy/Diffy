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

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.iface.DigitProcessor;
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
 * Isbn {@link DigitProcessor} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public final class ISBNDigitProcessor implements DigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8440785212674003101L;

    /**
     * Singleton ISBN-10 Check Digit instance
     */
    public static final DigitProcessor ISBN10_CHECK_DIGIT = ISBN10DigitProcessor.ISBN10_CHECK_DIGIT;

    /**
     * Singleton ISBN-13 Check Digit instance
     */
    public static final DigitProcessor ISBN13_CHECK_DIGIT = EAN13DigitProcessor.EAN13_CHECK_DIGIT;

    /**
     * Singleton combined ISBN-10 / ISBN-13 Check Digit instance
     */
    public static final DigitProcessor ISBN_CHECK_DIGIT = new ISBNDigitProcessor();

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
        }
        throw new InvalidParameterException("Invalid ISBN Length = " + code.length());
    }
}
