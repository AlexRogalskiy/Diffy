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
public final class LuhnDigitProcessor extends BaseDigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2666053866562353353L;

    /**
     * Singleton Luhn Check Digit instance
     */
    public static final DigitProcessor LUHN_CHECK_DIGIT = new LuhnDigitProcessor();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{2, 1};

    /**
     * Construct a modulus 10 Luhn Check Digit routine.
     */
    public LuhnDigitProcessor() {
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
        final int weight = POSITION_WEIGHT[rightPos % 2];
        final int weightedValue = charValue * weight;
        return weightedValue > 9 ? (weightedValue - 9) : weightedValue;
    }
}
