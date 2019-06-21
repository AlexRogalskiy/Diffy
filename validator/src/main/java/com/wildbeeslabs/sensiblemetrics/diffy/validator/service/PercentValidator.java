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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.PercentProcessor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p><b>Percentage Validation</b> and Conversion routines (<code>java.math.BigDecimal</code>).</p>
 *
 * <p>This is one implementation of a percent validator that has the following features:</p>
 * <ul>
 * <li>It is <i>lenient</i> about the the presence of the <i>percent symbol</i></li>
 * <li>It converts the percent to a <code>java.math.BigDecimal</code></li>
 * </ul>
 *
 * <p>However any of the <i>number</i> validators can be used for <i>percent</i> validation.
 * For example, if you wanted a <i>percent</i> validator that converts to a
 * <code>java.lang.Float</code> then you can simply instantiate an
 * <code>FloatValidator</code> with the appropriate <i>format type</i>:</p>
 *
 * <p><code>... = new FloatValidator(false, FloatValidator.PERCENT_FORMAT);</code></p>
 *
 * <p>Pick the appropriate validator, depending on the type (i.e Float, Double or BigDecimal)
 * you want the percent converted to. Please note, it makes no sense to use
 * one of the validators that doesn't handle fractions (i.e. byte, short, integer, long
 * and BigInteger) since percentages are converted to fractions (i.e <code>50%</code> is
 * converted to <code>0.5</code>).</p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.3.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PercentValidator extends BigDecimalValidator2 {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5913191650461007355L;

    /**
     * Default {@link PercentValidator} instance
     */
    private static final PercentValidator VALIDATOR = new PercentValidator();

    /**
     * DecimalFormat's percent (thousand multiplier) symbol
     */
    private static final char PERCENT_SYMBOL = '%';

    /**
     * Default {@link BigDecimal} procentile
     */
    private static final BigDecimal POINT_ZERO_ONE = new BigDecimal("0.01");

    /**
     * Return a singleton instance of this validator.
     *
     * @return A singleton instance of the PercentValidator.
     */
    public static PercentValidator getInstance() {
        return VALIDATOR;
    }

    /**
     * Construct a <i>strict</i> instance.
     */
    public PercentValidator() {
        this(true);
    }

    /**
     * Construct an instance with the specified strict setting.
     *
     * @param strict <code>true</code> if strict
     *               <code>Format</code> parsing should be used.
     */
    public PercentValidator(boolean strict) {
        super(new PercentProcessor(strict));
    }
}
