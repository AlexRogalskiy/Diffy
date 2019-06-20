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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;

/**
 * General Modulus 10 Check Digit calculation/validation.
 *
 * <h3>How if Works</h3>
 * <p>
 * This implementation calculates/validates the check digit in the following
 * way:
 * <ul>
 * <li>Converting each character to an integer value using
 * <code>Character.getNumericValue(char)</code> - negative integer values from
 * that method are invalid.</li>
 * <li>Calculating a <i>weighted value</i> by multiplying the character's
 * integer value by a <i>weighting factor</i>. The <i>weighting factor</i> is
 * selected from the configured <code>postitionWeight</code> array based on its
 * position. The <code>postitionWeight</code> values are used either
 * left-to-right (when <code>useRightPos=false</code>) or right-to-left (when
 * <code>useRightPos=true</code>).</li>
 * <li>If <code>sumWeightedDigits=true</code>, the <i>weighted value</i> is
 * re-calculated by summing its digits.</li>
 * <li>The <i>weighted values</i> of each character are totalled.</li>
 * <li>The total modulo 10 will be zero for a code with a valid Check Digit.</li>
 * </ul>
 * <h3>Limitations</h3>
 * <p>
 * This implementation has the following limitations:
 * <ul>
 * <li>It assumes the last character in the code is the Check Digit and
 * validates that it is a numeric character.</li>
 * <li>The only limitation on valid characters are those that
 * <code>Character.getNumericValue(char)</code> returns a positive value. If,
 * for example, the code should only contain numbers, this implementation does
 * not check that.</li>
 * <li>There are no checks on code length.</li>
 * </ul>
 * <p>
 * <b>Note:</b> This implementation can be combined with the
 * {@link DigitValidator} in order to ensure the length and characters are valid.
 *
 * <h3>Example Usage</h3>
 * <p>
 * This implementation was added after a number of Modulus 10 routines and these
 * are shown re-implemented using this routine below:
 *
 * <p>
 * <b>ABA Number</b> Check Digit Routine (equivalent of
 * {@link ABANDigitValidator}). Weighting factors are <code>[1, 7, 3]</code>
 * applied from right to left.
 *
 * <pre>
 * CheckDigit routine = new BaseTenDigitValidator(new int[] { 1, 7, 3 }, true);
 * </pre>
 *
 * <p>
 * <b>CUSIP</b> Check Digit Routine (equivalent of {@link CUSIPDigitValidator}).
 * Weighting factors are <code>[1, 2]</code> applied from right to left and the
 * digits of the <i>weighted value</i> are summed.
 *
 * <pre>
 * CheckDigit routine = new BaseTenDigitValidator(new int[] { 1, 2 }, true, true);
 * </pre>
 *
 * <p>
 * <b>EAN-13 / UPC</b> Check Digit Routine (equivalent of
 * {@link EAN13DigitValidator}). Weighting factors are <code>[1, 3]</code> applied
 * from right to left.
 *
 * <pre>
 * CheckDigit routine = new BaseTenDigitValidator(new int[] { 1, 3 }, true);
 * </pre>
 *
 * <p>
 * <b>Luhn</b> Check Digit Routine (equivalent of {@link LuhnDigitValidator}).
 * Weighting factors are <code>[1, 2]</code> applied from right to left and the
 * digits of the <i>weighted value</i> are summed.
 *
 * <pre>
 * CheckDigit routine = new BaseTenDigitValidator(new int[] { 1, 2 }, true, true);
 * </pre>
 *
 * <p>
 * <b>SEDOL</b> Check Digit Routine (equivalent of {@link SedolDigitValidator}).
 * Weighting factors are <code>[1, 3, 1, 7, 3, 9, 1]</code> applied from left to
 * right.
 *
 * <pre>
 * CheckDigit routine = new BaseTenDigitValidator(new int[] { 1, 3, 1, 7, 3, 9, 1 });
 * </pre>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.6
 */

/**
 * Ten {@link BaseDigitProcessor} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class BaseTenDigitProcessor extends BaseDigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -5382463789434694324L;

    private final int[] postitionWeight;
    private final boolean useRightPos;
    private final boolean sumWeightedDigits;

    /**
     * Construct a modulus 10 Check Digit routine with the specified weighting
     * from left to right.
     *
     * @param postitionWeight the weighted values to apply based on the
     *                        character position
     */
    public BaseTenDigitProcessor(int[] postitionWeight) {
        this(postitionWeight, false, false);
    }

    /**
     * Construct a modulus 10 Check Digit routine with the specified weighting,
     * indicating whether its from the left or right.
     *
     * @param postitionWeight the weighted values to apply based on the
     *                        character position
     * @param useRightPos     <code>true</code> if use positionWeights from right to
     *                        left
     */
    public BaseTenDigitProcessor(final int[] postitionWeight, boolean useRightPos) {
        this(postitionWeight, useRightPos, false);
    }

    /**
     * Construct a modulus 10 Check Digit routine with the specified weighting,
     * indicating whether its from the left or right and whether the weighted
     * digits should be summed.
     *
     * @param postitionWeight   the weighted values to apply based on the
     *                          character position
     * @param useRightPos       <code>true</code> if use positionWeights from right to
     *                          left
     * @param sumWeightedDigits <code>true</code> if sum the digits of the
     *                          weighted value
     */
    public BaseTenDigitProcessor(final int[] postitionWeight, boolean useRightPos, boolean sumWeightedDigits) {
        super(10);
        this.postitionWeight = Arrays.copyOf(postitionWeight, postitionWeight.length);
        this.useRightPos = useRightPos;
        this.sumWeightedDigits = sumWeightedDigits;
    }

    /**
     * Convert a character at a specified position to an integer value.
     * <p>
     * <b>Note:</b> this implementation only handlers values that
     * Character.getNumericValue(char) returns a non-negative number.
     *
     * @param character The character to convert
     * @param leftPos   The position of the character in the code, counting from
     *                  left to right (for identifying the position in the string)
     * @param rightPos  The position of the character in the code, counting from
     *                  right to left (not used here)
     * @return The integer value of the character
     * @throws InvalidParameterException if Character.getNumericValue(char) returns a
     *                                   negative number
     */
    @Override
    protected int toInt(char character, int leftPos, int rightPos) throws InvalidParameterException {
        int num = Character.getNumericValue(character);
        if (num < 0) {
            throw new InvalidParameterException("Invalid Character[" + leftPos + "] = '" + character + "'");
        }
        return num;
    }

    /**
     * Calculates the <i>weighted</i> value of a character in the code at a
     * specified position.
     *
     * @param charValue The numeric value of the character.
     * @param leftPos   The position of the character in the code, counting from
     *                  left to right
     * @param rightPos  The position of the character in the code, counting from
     *                  right to left
     * @return The weighted value of the character.
     */
    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        final int pos = useRightPos ? rightPos : leftPos;
        final int weight = postitionWeight[(pos - 1) % postitionWeight.length];
        int weightedValue = charValue * weight;
        if (this.sumWeightedDigits) {
            weightedValue = sumDigits(weightedValue);
        }
        return weightedValue;
    }
}
