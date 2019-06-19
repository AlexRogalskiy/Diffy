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

import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl.BaseDigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * Abstract <b>Modulus</b> Check digit calculation/validation.
 * <p>
 * Provides a <i>base</i> class for building <i>modulus</i> Check
 * Digit routines.
 * <p>
 * This implementation only handles <i>single-digit numeric</i> codes, such as
 * <b>EAN-13</b>. For <i>alphanumeric</i> codes such as <b>EAN-128</b> you
 * will need to implement/override the <code>toInt()</code> and
 * <code>toChar()</code> methods.
 * <p>
 *
 * @version $Revision: 1739357 $
 * @since Validator 1.4
 */

/**
 * Base {@link DigitValidator} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class BaseDigitValidator implements DigitValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6459345454087974579L;

    // N.B. The modulus can be > 10 provided that the implementing class overrides toCheckDigit and toInt
    // (for example as in ISBN10DigitValidator)
    private final BaseDigitProcessor processor;

    /**
     * Construct modulus check digit routine for a specified modulus.
     *
     * @param modulus The modulus value to use for the check digit calculation
     */
    public BaseDigitValidator(final BaseDigitProcessor processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        this.processor = processor;
    }

    /**
     * Validate a modulus check digit for a code.
     *
     * @param code The code to validate
     * @return <code>true</code> if the check digit is valid, otherwise
     * <code>false</code>
     */
    @Override
    public boolean validate(final String code) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        try {
            int modulusResult = this.getProcessor().calculateModulus(code, true);
            return (modulusResult == 0);
        } catch (InvalidParameterException ex) {
            return false;
        }
    }
}
