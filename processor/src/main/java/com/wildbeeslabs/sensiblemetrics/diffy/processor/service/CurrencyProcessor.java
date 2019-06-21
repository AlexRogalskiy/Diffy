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
package com.wildbeeslabs.sensiblemetrics.diffy.processor.service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.DecimalFormat;
import java.text.Format;

/**
 * <p><b>Currency Validation</b> and Conversion routines (<code>java.math.BigDecimal</code>).</p>
 *
 * <p>This is one implementation of a currency validator that has the following features:</p>
 * <ul>
 * <li>It is <i>lenient</i> about the the presence of the <i>currency symbol</i></li>
 * <li>It converts the currency to a <code>java.math.BigDecimal</code></li>
 * </ul>
 *
 * <p>However any of the <i>number</i> validators can be used for <i>currency</i> validation.
 * For example, if you wanted a <i>currency</i> validator that converts to a
 * <code>java.lang.Integer</code> then you can simply instantiate an
 * <code>IntegerProcessor</code> with the appropriate <i>format type</i>:</p>
 *
 * <p><code>... = new IntegerProcessor(false, IntegerProcessor.CURRENCY_FORMAT);</code></p>
 *
 * <p>Pick the appropriate validator, depending on the type (e.g Float, Double, Integer, Long etc)
 * you want the currency converted to. One thing to note - only the CurrencyProcessor
 * implements <i>lenient</i> behaviour regarding the currency symbol.</p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.3.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyProcessor extends BigDecimalProcessor {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -1300716919863020893L;

    /**
     * Default {@link CurrencyProcessor} instance
     */
    private static final CurrencyProcessor VALIDATOR = new CurrencyProcessor();

    /**
     * DecimalFormat's currency symbol
     */
    private static final char CURRENCY_SYMBOL = '\u00A4';

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the CurrencyProcessor.
     */
    public static CurrencyProcessor getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance.
     */
    public CurrencyProcessor() {
        this(true, true);
    }

    /**
     * Construct an instance with the specified strict setting.
     *
     * @param strict         <code>true</code> if strict
     *                       <code>Format</code> parsing should be used.
     * @param allowFractions <code>true</code> if fractions are
     *                       allowed or <code>false</code> if integers only.
     */
    public CurrencyProcessor(boolean strict, boolean allowFractions) {
        super(strict, CURRENCY_FORMAT, allowFractions);
    }

    /**
     * <p>Parse the value with the specified <code>Format</code>.</p>
     *
     * <p>This implementation is lenient whether the currency symbol
     * is present or not. The default <code>NumberFormat</code>
     * behaviour is for the parsing to "fail" if the currency
     * symbol is missing. This method re-parses with a format
     * without the currency symbol if it fails initially.</p>
     *
     * @param value     The value to be parsed.
     * @param formatter The Format to parse the value with.
     * @return The parsed value if valid or <code>null</code> if invalid.
     */
    @Override
    protected Object parse(final String value, final Format formatter) {
        Object parsedValue = super.parse(value, formatter);
        if (parsedValue != null || !(formatter instanceof DecimalFormat)) {
            return parsedValue;
        }
        // Re-parse using a pattern without the currency symbol
        final DecimalFormat decimalFormat = (DecimalFormat) formatter;
        final String pattern = decimalFormat.toPattern();
        if (pattern.indexOf(CURRENCY_SYMBOL) >= 0) {
            final StringBuilder buffer = new StringBuilder(pattern.length());
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != CURRENCY_SYMBOL) {
                    buffer.append(pattern.charAt(i));
                }
            }
            decimalFormat.applyPattern(buffer.toString());
            parsedValue = super.parse(value, decimalFormat);
        }
        return parsedValue;
    }
}
