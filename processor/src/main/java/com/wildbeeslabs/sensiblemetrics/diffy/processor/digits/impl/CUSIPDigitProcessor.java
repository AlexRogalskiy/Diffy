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

/**
 * Cusip {@link BaseDigitProcessor} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class CUSIPDigitProcessor extends BaseDigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 797710928495202813L;

    /**
     * Singleton CUSIP Check Digit instance
     */
    public static final DigitProcessor CUSIP_CHECK_DIGIT = new CUSIPDigitProcessor();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{2, 1};

    /**
     * Construct an CUSIP Indetifier Check Digit routine.
     */
    public CUSIPDigitProcessor() {
        super(10);
    }

    /**
     * Convert a character at a specified position to an integer value.
     *
     * @param character The character to convert
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The position of the character in the code, counting from right to left
     * @return The integer value of the character
     * @throws InvalidParameterException if character is not alphanumeric
     */
    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        final int charValue = Character.getNumericValue(character);
        final int charMax = rightPos == 1 ? 9 : 35;
        if (charValue < 0 || charValue > charMax) {
            throw new InvalidParameterException("Invalid Character[" + leftPos + "," + rightPos + "] = '" + charValue + "' out of range 0 to " + charMax);
        }
        return charValue;
    }

    /**
     * <p>Calculates the <i>weighted</i> value of a charcter in the
     * code at a specified position.</p>
     *
     * <p>For CUSIP (from right to left) <b>odd</b> digits are weighted
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
        final int weightedValue = (charValue * weight);
        return sumDigits(weightedValue);
    }
}
