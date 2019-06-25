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

import com.wildbeeslabs.sensiblemetrics.diffy.validator.helpers.CodeProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.helpers.ISBNProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * <b>ISBN-10</b> and <b>ISBN-13</b> Code Validation.
 * <p>
 * This validator validates the code is either a valid ISBN-10
 * (using a {@link CodeProcessor} with the {@code ISBN10DigitValidator.ISBN10_CHECK_DIGIT})
 * or a valid ISBN-13 code (using a {@link CodeProcessor} with the
 * the {@code EAN13DigitValidator.EAN13_CHECK_DIGIT} routine).
 * <p>
 * The <code>validate()</code> methods return the ISBN code with formatting
 * characters removed if valid or <code>null</code> if invalid.
 * <p>
 * This validator also provides the facility to convert ISBN-10 codes to
 * ISBN-13 if the <code>convert</code> property is <code>true</code>.
 * <p>
 * From 1st January 2007 the book industry will start to use a new 13 digit
 * ISBN number (rather than this 10 digit ISBN number). ISBN-13 codes are
 * <a href="http://en.wikipedia.org/wiki/European_Article_Number">EAN</a>
 * codes, for more information see:</p>
 *
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/ISBN">Wikipedia - International
 * Standard Book Number (ISBN)</a>.</li>
 * <li>EAN - see
 * <a href="http://en.wikipedia.org/wiki/European_Article_Number">Wikipedia -
 * European Article Number</a>.</li>
 * <li><a href="http://www.isbn.org/standards/home/isbn/transition.asp">ISBN-13
 * Transition details</a>.</li>
 * </ul>
 *
 * @version $Revision: 1715435 $
 * @since Validator 1.4
 */
@Data
@EqualsAndHashCode
@ToString
public class ISBNValidator implements Validator<String> {

    /**
     * ISBN Code Validator (which converts ISBN-10 codes to ISBN-13
     */
    private static final ISBNValidator ISBN_VALIDATOR = new ISBNValidator();

    /**
     * ISBN Code Validator (which converts ISBN-10 codes to ISBN-13
     */
    private static final ISBNValidator ISBN_VALIDATOR_NO_CONVERT = new ISBNValidator(false);

    /**
     * Default isbn validation converter binary flag {isbn10 / isbn13}
     */
    private final ISBNProcessor processor;

    /**
     * Return a singleton instance of the ISBN validator which
     * converts ISBN-10 codes to ISBN-13.
     *
     * @return A singleton instance of the ISBN validator.
     */
    public static ISBNValidator getInstance() {
        return ISBN_VALIDATOR;
    }

    /**
     * Return a singleton instance of the ISBN validator specifying
     * whether ISBN-10 codes should be converted to ISBN-13.
     *
     * @param convert <code>true</code> if valid ISBN-10 codes
     *                should be converted to ISBN-13 codes or <code>false</code>
     *                if valid ISBN-10 codes should be returned unchanged.
     * @return A singleton instance of the ISBN validator.
     */
    public static ISBNValidator getInstance(boolean convert) {
        return (convert ? ISBN_VALIDATOR : ISBN_VALIDATOR_NO_CONVERT);
    }

    /**
     * Construct an ISBN validator which converts ISBN-10 codes
     * to ISBN-13.
     */
    public ISBNValidator() {
        this(true);
    }

    /**
     * Construct an ISBN validator indicating whether
     * ISBN-10 codes should be converted to ISBN-13.
     *
     * @param convert <code>true</code> if valid ISBN-10 codes
     *                should be converted to ISBN-13 codes or <code>false</code>
     *                if valid ISBN-10 codes should be returned unchanged.
     */
    public ISBNValidator(boolean convert) {
        this.processor = new ISBNProcessor(convert);
    }

    /**
     * Check the code is either a valid ISBN-10 or ISBN-13 code.
     *
     * @param code The code to validate.
     * @return <code>true</code> if a valid ISBN-10 or
     * ISBN-13 code, otherwise <code>false</code>.
     */
    @Override
    public boolean validate(final String code) {
        return Objects.nonNull(this.processor.processOrThrow(code));
    }
}
