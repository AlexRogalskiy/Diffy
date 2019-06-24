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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl.VerhoeffDigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.security.InvalidParameterException;

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
 * Verhoeff {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public final class VerhoeffDigitValidator implements DigitValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6282375228099937558L;

    /**
     * Singleton Verhoeff Check Digit instance
     */
    private static final DigitValidator VERHOEFF_CHECK_DIGIT = new VerhoeffDigitValidator();

    /**
     * Default {@link VerhoeffDigitProcessor} instance
     */
    private final VerhoeffDigitProcessor processor;

    /**
     * Default verhoeff digit validator constructor
     */
    public VerhoeffDigitValidator() {
        this.processor = new VerhoeffDigitProcessor();
    }

    /**
     * Validate the Verhoeff <i>Check Digit</i> for a code.
     *
     * @param code The code to validate
     * @return <code>true</code> if the check digit is valid,
     * otherwise <code>false</code>
     */
    @Override
    public boolean validate(final String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        try {
            return (this.processor.calculateChecksum(code, true) == 0);
        } catch (InvalidParameterException e) {
            return false;
        }
    }

    /**
     * Returns {@link DigitValidator} instance
     *
     * @return {@link DigitValidator} instance
     */
    public static DigitValidator getInstance() {
        return VERHOEFF_CHECK_DIGIT;
    }
}
