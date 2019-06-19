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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl.SedolDigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
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

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8496323811106253904L;

    private static final int MAX_ALPHANUMERIC_VALUE = 35; // Character.getNumericValue('Z')

    /**
     * Singleton SEDOL check digit instance
     */
    private static final DigitValidator SEDOL_CHECK_DIGIT = new SedolDigitValidator();

    /**
     * Construct a modulus 11 Check Digit routine for ISBN-10.
     */
    public SedolDigitValidator() {
        super(new SedolDigitProcessor());
    }

    /**
     * Returns {@link DigitValidator} instance
     *
     * @return {@link DigitValidator} instance
     */
    public static DigitValidator getInstance() {
        return SEDOL_CHECK_DIGIT;
    }
}
