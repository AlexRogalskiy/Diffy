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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.digits.impl.ISSNDigitProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.iface.DigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * International Standard Serial Number (ISSN)
 * is an eight-digit serial number used to
 * uniquely identify a serial publication.
 * <pre>
 * The format is:
 *
 * ISSN dddd-dddC
 * where:
 * d = decimal digit (0-9)
 * C = checksum (0-9 or X)
 *
 * The checksum is formed by adding the first 7 digits multiplied by
 * the position in the entire number (counting from the right).
 * For example, abcd-efg would be 8a + 7b + 6c + 5d + 4e +3f +2g.
 * The check digit is modulus 11, where the value 10 is represented by 'X'
 * For example:
 * ISSN 0317-8471
 * ISSN 1050-124X
 * </pre>
 * <p>
 * <b>Note:</b> This class expects the input to be numeric only,
 * with all formatting removed.
 * For example:
 * <pre>
 * 03178471
 * 1050124X
 * </pre>
 *
 * @since 1.5.0
 */

/**
 * Issn {@link BaseDigitValidator} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class ISSNDigitValidator extends BaseDigitValidator {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -7484235604108721545L;

    /**
     * Singleton ISSN Check Digit instance
     */
    private static final DigitValidator ISSN_CHECK_DIGIT = new ISSNDigitValidator();

    /**
     * Creates the instance using a checkdigit modulus of 11
     */
    public ISSNDigitValidator() {
        super(new ISSNDigitProcessor());
    }

    /**
     * Returns {@link DigitValidator} instance
     *
     * @return {@link DigitValidator} instance
     */
    public static DigitValidator getInstance() {
        return ISSN_CHECK_DIGIT;
    }
}
