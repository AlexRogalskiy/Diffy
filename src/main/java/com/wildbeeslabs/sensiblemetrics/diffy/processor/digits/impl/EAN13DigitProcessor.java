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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.iface.DigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.BaseDigitValidator;
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
public final class EAN13DigitProcessor extends BaseDigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8288232890425192068L;

    /**
     * Singleton EAN-13 Check Digit instance
     */
    public static final DigitProcessor EAN13_CHECK_DIGIT = new EAN13DigitProcessor();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{3, 1};

    /**
     * Construct a modulus 10 Check Digit routine for EAN/UPC.
     */
    public EAN13DigitProcessor() {
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
        final int weight = POSITION_WEIGHT[rightPos % 2];
        return charValue * weight;
    }
}
