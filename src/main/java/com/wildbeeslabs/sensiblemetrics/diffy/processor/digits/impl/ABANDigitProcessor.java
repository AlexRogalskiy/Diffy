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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Modulus 10 <b>ABA Number</b> (or <b>Routing Transit Number</b> (RTN)) Check Digit
 * calculation/validation.
 *
 * <p>
 * ABA Numbers (or Routing Transit Numbers) are a nine digit numeric code used
 * to identify American financial institutions for things such as checks or deposits
 * (ABA stands for the American Bankers Association).
 * </p>
 * <p>
 * Check digit calculation is based on <i>modulus 10</i> with digits being weighted
 * based on their position (from right to left) as follows:
 *
 * <ul>
 * <li>Digits 1, 4 and &amp; 7 are weighted 1</li>
 * <li>Digits 2, 5 and &amp; 8 are weighted 7</li>
 * <li>Digits 3, 6 and &amp; 9 are weighted 3</li>
 * </ul>
 *
 * <p>
 * For further information see
 * <a href="http://en.wikipedia.org/wiki/Routing_transit_number">Wikipedia -
 * Routing transit number</a>.
 * </p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.4
 */

/**
 * Aban {@link BaseDigitProcessor} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ABANDigitProcessor extends BaseDigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8277776776836771642L;

    /**
     * Singleton Routing Transit Number Check Digit instance
     */
    public static final DigitProcessor ABAN_CHECK_DIGIT = new ABANDigitProcessor();

    /**
     * weighting given to digits depending on their right position
     */
    private static final int[] POSITION_WEIGHT = new int[]{3, 1, 7};

    /**
     * Construct a modulus 10 Check Digit routine for ABA Numbers.
     */
    public ABANDigitProcessor() {
        super(10);
    }

    /**
     * Calculates the <i>weighted</i> value of a character in the
     * code at a specified position.
     * <p>
     * ABA Routing numbers are weighted in the following manner:
     * <pre><code>
     *     left position: 1  2  3  4  5  6  7  8  9
     *            weight: 3  7  1  3  7  1  3  7  1
     * </code></pre>
     *
     * @param charValue The numeric value of the character.
     * @param leftPos   The position of the character in the code, counting from left to right
     * @param rightPos  The positionof the character in the code, counting from right to left
     * @return The weighted value of the character.
     */
    @Override
    protected int weightedValue(int charValue, int leftPos, int rightPos) {
        final int weight = POSITION_WEIGHT[rightPos % 3];
        return charValue * weight;
    }
}
