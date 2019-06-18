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
package com.wildbeeslabs.sensiblemetrics.diffy.processor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.EAN13DigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.impl.CodeValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.ISSNDigitValidator.ISSN_CHECK_DIGIT;
import static org.apache.commons.lang3.StringUtils.join;

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
@Data
@EqualsAndHashCode
@ToString
public class ISSNProcessor implements ThrowingProcessor<String, Object, InvalidParameterException>, Serializable {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -7500338388453927189L;

    /**
     * Default issn regex
     */
    private static final String ISSN_REGEX = "(?:ISSN )?(\\d{4})-(\\d{3}[0-9X])$";

    /**
     * Default {@link CodeValidator} instance
     */
    private static final CodeProcessor VALIDATOR = new CodeProcessor(ISSN_REGEX, 8, ISSN_CHECK_DIGIT);

    /**
     * Default {@link ISSNProcessor} instance
     */
    private static final ISSNProcessor ISSN_VALIDATOR = new ISSNProcessor();

    /**
     * Return a singleton instance of the ISSN validator
     *
     * @return A singleton instance of the ISSN validator.
     */
    public static ISSNProcessor getInstance() {
        return ISSN_VALIDATOR;
    }

    /**
     * Returns validation result {@link Object} by input parameters
     *
     * @param code - initial input {@link String} value to validate
     * @return validation result {@link Object}
     */
    @Override
    public Object processOrThrow(final String code) throws InvalidParameterException {
        return VALIDATOR.processOrThrow(code);
    }

    /**
     * Convert an ISSN code to an EAN-13 code.
     * <p>
     * This method requires a valid ISSN code.
     * It may contain a leading 'ISSN ' prefix,
     * as the input is passed through the {@link #validate(String)}
     * method.
     *
     * @param issn   The ISSN code to convert
     * @param suffix the two digit suffix, e.g. "00"
     * @return A converted EAN-13 code or <code>null</code>
     * if the input ISSN code is not valid
     */
    public String convertToEAN13(final String issn, final String suffix) {
        if (Objects.isNull(suffix) || !suffix.matches("\\d\\d")) {
            throw new IllegalArgumentException("Suffix must be two digits: '" + suffix + "'");
        }
        final Object result = VALIDATOR.processOrThrow(issn);
        if (Objects.isNull(result)) {
            return null;
        }
        final String input = result.toString();
        String ean13 = "977" + input.substring(0, input.length() - 1) + suffix;
        return join(ean13, EAN13DigitValidator.EAN13_CHECK_DIGIT.processOrThrow(ean13));
    }
}
