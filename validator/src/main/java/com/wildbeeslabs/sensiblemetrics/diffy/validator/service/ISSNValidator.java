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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.ISSNProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

import java.util.Objects;

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
 *
 * For example, abcd-efg would be 8a + 7b + 6c + 5d + 4e +3f +2g.
 * The check digit is modulus 11, where the value 10 is represented by 'X'
 * For example:
 * ISSN 0317-8471
 * ISSN 1050-124X
 *
 * This class strips off the 'ISSN ' prefix if it is present before passing
 * the remainder to the checksum routine.
 *
 * </pre>
 * <p>
 * Note: the {@link #validate(String)} methods strip off any leading
 * or trailing spaces before doing the validation.
 * To ensure that only a valid code (without 'ISSN ' prefix) is passed to a method,
 * use the following code:
 * <pre>
 * Object valid = validator.validate(input);
 * if (valid != null) {
 *    some_method(valid.toString());
 * }
 * </pre>
 *
 * @since 1.5.0
 */
public class ISSNValidator implements Validator<String> {

    /**
     * Default {@link ISSNValidator} instance
     */
    private static final ISSNValidator ISSN_VALIDATOR = new ISSNValidator();

    /**
     * Return a singleton instance of the ISSN validator
     *
     * @return A singleton instance of the ISSN validator.
     */
    public static ISSNValidator getInstance() {
        return ISSN_VALIDATOR;
    }

    /**
     * Default {@link ISSNProcessor} instance
     */
    private final ISSNProcessor processor;

    /**
     * Default issn validator constructor
     */
    public ISSNValidator() {
        this.processor = new ISSNProcessor();
    }

    /**
     * Check the code is a valid ISSN code after any transformation
     * by the validate routine.
     *
     * @param code The code to validate.
     * @return <code>true</code> if a valid ISSN
     * code, otherwise <code>false</code>.
     */
    @Override
    public boolean validate(final String code) {
        return Objects.nonNull(this.processor.processOrThrow(code));
    }
}
