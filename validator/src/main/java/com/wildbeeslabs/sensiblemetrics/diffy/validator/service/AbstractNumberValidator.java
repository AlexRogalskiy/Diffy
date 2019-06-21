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

import com.wildbeeslabs.sensiblemetrics.diffy.processor.impl.AbstractNumberProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.*;

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

    /**
     * Default {@link AbstractNumberProcessor} instance
     */
    @Getter(AccessLevel.PROTECTED)
    private final AbstractNumberProcessor processor;

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
    public AbstractNumberValidator(final AbstractNumberProcessor processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        this.processor = processor;
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
    public boolean validate(final String value, final String pattern, final Locale locale) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, locale));
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
        return (this.minValue(value, min) && this.maxValue(value, max));
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
        ValidationUtils.notNull(value, "Value should not be null");
        ValidationUtils.notNull(min, "Min value should not be null");

        if (this.getProcessor().isAllowFractions()) {
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
        ValidationUtils.notNull(value, "Value should not be null");
        ValidationUtils.notNull(max, "Max value should not be null");

        if (this.getProcessor().isAllowFractions()) {
            return (value.doubleValue() <= max.doubleValue());
        }
        return (value.longValue() <= max.longValue());
    }
}
