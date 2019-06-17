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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * <p>Abstract class for Number Validation.</p>
 *
 * <p>This is a <i>base</i> class for building Number
 * Validators using format parsing.</p>
 *
 * @version $Revision: 1739356 $
 * @since Validator 1.3.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractNumberValidator extends AbstractFormatValidator<String> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2600767075967362020L;

    /**
     * Standard <code>NumberFormat</code> type
     */
    public static final int STANDARD_FORMAT = 0;

    /**
     * Currency <code>NumberFormat</code> type
     */
    public static final int CURRENCY_FORMAT = 1;

    /**
     * Percent <code>NumberFormat</code> type
     */
    public static final int PERCENT_FORMAT = 2;

    private final boolean allowFractions;
    private final int formatType;

    /**
     * Construct an instance with specified <i>strict</i>
     * and <i>decimal</i> parameters.
     *
     * @param strict         <code>true</code> if strict
     *                       <code>Format</code> parsing should be used.
     * @param formatType     The <code>NumberFormat</code> type to
     *                       create for validation, default is STANDARD_FORMAT.
     * @param allowFractions <code>true</code> if fractions are
     *                       allowed or <code>false</code> if integers only.
     */
    public AbstractNumberValidator(boolean strict, int formatType, boolean allowFractions) {
        super(strict);
        this.allowFractions = allowFractions;
        this.formatType = formatType;
    }

    /**
     * <p>Validate using the specified <code>Locale</code>.</p>
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against, or the
     *                default for the <code>Locale</code> if <code>null</code>.
     * @param locale  The locale to use for the date format, system default if null.
     * @return <code>true</code> if the value is valid.
     */
    @Override
    public boolean isValid(final String value, final String pattern, final Locale locale) {
        final Object parsedValue = this.parse(value, pattern, locale);
        return (Objects.isNull(parsedValue) ? false : true);
    }

    /**
     * Check if the value is within a specified range.
     *
     * @param value The value validation is being performed on.
     * @param min   The minimum value of the range.
     * @param max   The maximum value of the range.
     * @return <code>true</code> if the value is within the
     * specified range.
     */
    public boolean isInRange(final Number value, final Number min, final Number max) {
        return (minValue(value, min) && maxValue(value, max));
    }

    /**
     * Check if the value is greater than or equal to a minimum.
     *
     * @param value The value validation is being performed on.
     * @param min   The minimum value.
     * @return <code>true</code> if the value is greater than
     * or equal to the minimum.
     */
    public boolean minValue(final Number value, final Number min) {
        if (isAllowFractions()) {
            return (value.doubleValue() >= min.doubleValue());
        }
        return (value.longValue() >= min.longValue());
    }

    /**
     * Check if the value is less than or equal to a maximum.
     *
     * @param value The value validation is being performed on.
     * @param max   The maximum value.
     * @return <code>true</code> if the value is less than
     * or equal to the maximum.
     */
    public boolean maxValue(final Number value, final Number max) {
        if (isAllowFractions()) {
            return (value.doubleValue() <= max.doubleValue());
        }
        return (value.longValue() <= max.longValue());
    }

    /**
     * <p>Parse the value using the specified pattern.</p>
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to validate the value against, or the
     *                default for the <code>Locale</code> if <code>null</code>.
     * @param locale  The locale to use for the date format, system default if null.
     * @return The parsed value if valid or <code>null</code> if invalid.
     */
    protected Object parse(final String value, final String pattern, final Locale locale) {
        String newValue = (Objects.isNull(value) ? null : value.trim());
        if (StringUtils.isBlank(newValue)) {
            return null;
        }
        final Format formatter = getFormat(pattern, locale);
        return parse(value, formatter);
    }

    /**
     * <p>Process the parsed value, performing any further validation
     * and type conversion required.</p>
     *
     * @param value     The parsed object created.
     * @param formatter The Format used to parse the value with.
     * @return The parsed value converted to the appropriate type
     * if valid or <code>null</code> if invalid.
     */
    @Override
    protected abstract Object processParsedValue(final Object value, Format formatter);

    /**
     * <p>Returns a <code>NumberFormat</code> for the specified <i>pattern</i>
     * and/or <code>Locale</code>.</p>
     *
     * @param pattern The pattern used to validate the value against or
     *                <code>null</code> to use the default for the <code>Locale</code>.
     * @param locale  The locale to use for the currency format, system default if null.
     * @return The <code>NumberFormat</code> to created.
     */
    @Override
    protected Format getFormat(final String pattern, final Locale locale) {
        NumberFormat formatter = null;
        boolean usePattern = (pattern != null && pattern.length() > 0);
        if (!usePattern) {
            formatter = (NumberFormat) getFormat(locale);
        } else if (locale == null) {
            formatter = new DecimalFormat(pattern);
        } else {
            final DecimalFormatSymbols symbols = new DecimalFormatSymbols(locale);
            formatter = new DecimalFormat(pattern, symbols);
        }

        if (determineScale(formatter) == 0) {
            formatter.setParseIntegerOnly(true);
        }
        return formatter;
    }

    /**
     * <p>Returns the <i>multiplier</i> of the <code>NumberFormat</code>.</p>
     *
     * @param format The <code>NumberFormat</code> to determine the
     *               multiplier of.
     * @return The multiplying factor for the format..
     */
    protected int determineScale(final NumberFormat format) {
        if (!isStrict()) {
            return -1;
        }
        if (!isAllowFractions() || format.isParseIntegerOnly()) {
            return 0;
        }
        int minimumFraction = format.getMinimumFractionDigits();
        int maximumFraction = format.getMaximumFractionDigits();
        if (minimumFraction != maximumFraction) {
            return -1;
        }
        int scale = minimumFraction;
        if (format instanceof DecimalFormat) {
            int multiplier = ((DecimalFormat) format).getMultiplier();
            if (multiplier == 100) {
                scale += 2;
            } else if (multiplier == 1000) {
                scale += 3;
            }
        } else if (formatType == PERCENT_FORMAT) {
            scale += 2;
        }
        return scale;
    }

    /**
     * <p>Returns a <code>NumberFormat</code> for the specified Locale.</p>
     *
     * @param locale The locale a <code>NumberFormat</code> is required for,
     *               system default if null.
     * @return The <code>NumberFormat</code> to created.
     */
    protected Format getFormat(final Locale locale) {
        switch (this.formatType) {
            case CURRENCY_FORMAT:
                if (Objects.isNull(locale)) {
                    return NumberFormat.getCurrencyInstance();
                }
                return NumberFormat.getCurrencyInstance(locale);
            case PERCENT_FORMAT:
                if (Objects.isNull(locale)) {
                    return NumberFormat.getPercentInstance();
                }
                return NumberFormat.getPercentInstance(locale);
            default:
                if (Objects.isNull(locale)) {
                    return NumberFormat.getInstance();
                }
                return NumberFormat.getInstance(locale);
        }
    }
}
