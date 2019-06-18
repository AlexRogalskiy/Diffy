package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.Objects;

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
        super(strict, PERCENT_FORMAT, true);
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
        BigDecimal parsedValue = (BigDecimal) super.parse(value, formatter);
        if (Objects.nonNull(parsedValue) || !(formatter instanceof DecimalFormat)) {
            return parsedValue;
        }
        final DecimalFormat decimalFormat = (DecimalFormat) formatter;
        final String pattern = decimalFormat.toPattern();
        if (pattern.indexOf(PERCENT_SYMBOL) >= 0) {
            final StringBuilder buffer = new StringBuilder(pattern.length());
            for (int i = 0; i < pattern.length(); i++) {
                if (pattern.charAt(i) != PERCENT_SYMBOL) {
                    buffer.append(pattern.charAt(i));
                }
            }
            decimalFormat.applyPattern(buffer.toString());
            parsedValue = (BigDecimal) super.parse(value, decimalFormat);
            if (Objects.nonNull(parsedValue)) {
                parsedValue = parsedValue.multiply(POINT_ZERO_ONE);
            }
        }
        return parsedValue;
    }
}
