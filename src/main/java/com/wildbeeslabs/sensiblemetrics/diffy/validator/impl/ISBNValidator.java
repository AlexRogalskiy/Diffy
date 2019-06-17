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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

public class ISBNValidator implements Validator<String> {

    /**
     * If the ISBN is formatted with space or dash separators its format is
     * validated.  Then the digits in the number are weighted, summed, and
     * divided by 11 according to the ISBN algorithm.  If the result is zero,
     * the ISBN is valid.  This method accepts formatted or raw ISBN codes.
     *
     * @param isbn Candidate ISBN number to be validated. <code>null</code> is
     *             considered invalid.
     * @return true if the string is a valid ISBN code.
     */
    @Override
    public boolean validate(final String isbn) {
        return org.apache.commons.validator.routines.ISBNValidator.getInstance().isValidISBN10(isbn);
    }
}
