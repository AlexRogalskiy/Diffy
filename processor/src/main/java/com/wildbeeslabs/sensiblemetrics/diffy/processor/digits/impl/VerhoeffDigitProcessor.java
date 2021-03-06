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
 * <b>Verhoeff</b> (Dihedral) Check Digit calculation/validation.
 * <p>
 * Check digit calculation for numeric codes using a
 * <a href="http://en.wikipedia.org/wiki/Dihedral_group">Dihedral Group</a>
 * of order 10.
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Verhoeff_algorithm">Wikipedia
 * - Verhoeff algorithm</a> for more details.
 *
 * @version $Revision: 1739357 $
 * @since Validator 1.4
 */

/**
 * Verhoeff {@link DigitProcessor} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public final class VerhoeffDigitProcessor implements DigitProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 562051775473029248L;

    /**
     * Singleton Verhoeff Check Digit instance
     */
    public static final DigitProcessor VERHOEFF_CHECK_DIGIT = new VerhoeffDigitProcessor();

    /**
     * D - multiplication table
     */
    private static final int[][] D_TABLE = new int[][]{
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
        {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
        {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
        {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
        {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
        {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
        {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
        {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
        {9, 8, 7, 6, 5, 4, 3, 2, 1, 0}};

    /**
     * P - permutation table
     */
    private static final int[][] P_TABLE = new int[][]{
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8}};

    /**
     * inv: inverse table
     */
    private static final int[] INV_TABLE = new int[]
        {0, 4, 3, 2, 1, 5, 6, 7, 8, 9};

    /**
     * Calculate a Verhoeff <i>Check Digit</i> for a code.
     *
     * @param code The code to calculate the Check Digit for
     * @return The calculated Check Digit
     * @throws InvalidParameterException if an error occurs calculating
     *                                   the check digit for the specified code
     */
    @Override
    public String processOrThrow(final String code) throws InvalidParameterException {
        if (StringUtils.isBlank(code)) {
            throw new InvalidParameterException("Code is missing");
        }
        final int checksum = this.calculateChecksum(code, false);
        return Integer.toString(INV_TABLE[checksum]);
    }

    /**
     * Calculate the checksum.
     *
     * @param code               The code to calculate the checksum for.
     * @param includesCheckDigit Whether the code includes the Check Digit or not.
     * @return The checksum value
     * @throws InvalidParameterException if the code contains an invalid character (i.e. not numeric)
     */
    public int calculateChecksum(final String code, boolean includesCheckDigit) throws InvalidParameterException {
        int checksum = 0;
        for (int i = 0; i < code.length(); i++) {
            int idx = code.length() - (i + 1);
            int num = Character.getNumericValue(code.charAt(idx));
            if (num < 0 || num > 9) { // CHECKSTYLE IGNORE MagicNumber
                throw new InvalidParameterException("Invalid Character[" + i + "] = '" + ((int) code.charAt(idx)) + "'");
            }
            int pos = includesCheckDigit ? i : i + 1;
            checksum = D_TABLE[checksum][P_TABLE[pos % 8][num]]; // CHECKSTYLE IGNORE MagicNumber
        }
        return checksum;
    }
}
