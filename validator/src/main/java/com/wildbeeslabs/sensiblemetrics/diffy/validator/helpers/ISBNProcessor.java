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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.helpers;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.interfaces.GenericProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.EAN13DigitValidator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.digits.impl.ISBN10DigitValidator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.join;

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
public class ISBNProcessor implements GenericProcessor<String, String, InvalidParameterException> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -1362597436027958115L;

    /**
     * Default isbn length
     */
    private static final int ISBN_10_LEN = 10;

    /**
     * Default validation regex
     */
    private static final String SEP = "(?:\\-|\\s)";
    private static final String GROUP = "(\\d{1,5})";
    private static final String PUBLISHER = "(\\d{1,7})";
    private static final String TITLE = "(\\d{1,6})";

    /**
     * ISBN-10 consists of 4 groups of numbers separated by either dashes (-)
     * or spaces.  The first group is 1-5 characters, second 1-7, third 1-6,
     * and fourth is 1 digit or an X.
     */
    static final String ISBN10_REGEX = "^(?:(\\d{9}[0-9X])|(?:" + GROUP + SEP + PUBLISHER + SEP + TITLE + SEP + "([0-9X])))$";

    /**
     * ISBN-13 consists of 5 groups of numbers separated by either dashes (-)
     * or spaces.  The first group is 978 or 979, the second group is
     * 1-5 characters, third 1-7, fourth 1-6, and fifth is 1 digit.
     */
    static final String ISBN13_REGEX = "^(978|979)(?:(\\d{10})|(?:" + SEP + GROUP + SEP + PUBLISHER + SEP + TITLE + SEP + "([0-9])))$";

    /**
     * ISBN Code Validator (which converts ISBN-10 codes to ISBN-13
     */
    private static final ISBNProcessor ISBN_VALIDATOR = new ISBNProcessor();

    /**
     * ISBN Code Validator (which converts ISBN-10 codes to ISBN-13
     */
    private static final ISBNProcessor ISBN_VALIDATOR_NO_CONVERT = new ISBNProcessor(false);

    /**
     * ISBN-10 Code Validator
     */
    private final CodeProcessor isbn10Validator = new CodeProcessor(ISBN10_REGEX, 10, ISBN10DigitValidator.getInstance());

    /**
     * ISBN-13 Code Validator
     */
    private final CodeProcessor isbn13Validator = new CodeProcessor(ISBN13_REGEX, 13, EAN13DigitValidator.getInstance());

    /**
     * Default isbn validation converter binary flag {isbn10 / isbn13}
     */
    private final boolean convert;

    /**
     * Return a singleton instance of the ISBN validator which
     * converts ISBN-10 codes to ISBN-13.
     *
     * @return A singleton instance of the ISBN validator.
     */
    public static ISBNProcessor getInstance() {
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
    public static ISBNProcessor getInstance(boolean convert) {
        return (convert ? ISBN_VALIDATOR : ISBN_VALIDATOR_NO_CONVERT);
    }

    /**
     * Construct an ISBN validator which converts ISBN-10 codes
     * to ISBN-13.
     */
    public ISBNProcessor() {
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
    public ISBNProcessor(boolean convert) {
        this.convert = convert;
    }

    /**
     * Check the code is either a valid ISBN-10 or ISBN-13 code.
     * <p>
     * If valid, this method returns the ISBN code with
     * formatting characters removed (i.e. space or hyphen).
     * <p>
     * Converts an ISBN-10 codes to ISBN-13 if
     * <code>convertToISBN13</code> is <code>true</code>.
     *
     * @param code The code to validate.
     * @return A valid ISBN code if valid, otherwise <code>null</code>.
     */
    @Override
    public String processOrThrow(final String code) {
        String result = this.validateISBN13(code);
        if (Objects.isNull(result)) {
            result = this.validateISBN10(code);
            if (Objects.nonNull(result) && this.convert) {
                return this.convertToISBN13(result);
            }
        }
        return null;
    }

    /**
     * Check the code is a valid ISBN-10 code.
     * <p>
     * If valid, this method returns the ISBN-10 code with
     * formatting characters removed (i.e. space or hyphen).
     *
     * @param code The code to validate.
     * @return A valid ISBN-10 code if valid,
     * otherwise <code>null</code>.
     */
    public String validateISBN10(final String code) {
        final Object result = this.isbn10Validator.processOrThrow(code);
        return (Objects.isNull(result) ? null : result.toString());
    }

    /**
     * Check the code is a valid ISBN-13 code.
     * <p>
     * If valid, this method returns the ISBN-13 code with
     * formatting characters removed (i.e. space or hyphen).
     *
     * @param code The code to validate.
     * @return A valid ISBN-13 code if valid,
     * otherwise <code>null</code>.
     */
    public String validateISBN13(String code) {
        final Object result = this.isbn13Validator.processOrThrow(code);
        return (Objects.isNull(result) ? null : result.toString());
    }

    /**
     * Convert an ISBN-10 code to an ISBN-13 code.
     * <p>
     * This method requires a valid ISBN-10 with NO formatting
     * characters.
     *
     * @param isbn10 The ISBN-10 code to convert
     * @return A converted ISBN-13 code or <code>null</code>
     * if the ISBN-10 code is not valid
     */
    public String convertToISBN13(final String isbn10) {
        if (Objects.isNull(isbn10)) {
            return null;
        }
        final String input = isbn10.trim();
        if (input.length() != ISBN_10_LEN) {
            throw new IllegalArgumentException("Invalid length " + input.length() + " for '" + input + "'");
        }
        final String isbn13 = "978" + input.substring(0, ISBN_10_LEN - 1);
        try {
            return join(isbn13, this.isbn13Validator.processOrThrow(isbn13));
        } catch (Throwable t) {
            throw new InvalidParameterException(String.format("ERROR: cannot process input value = {%s}", isbn10), t);
        }
    }
}
