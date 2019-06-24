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

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.processor.service.AbstractCalendarProcessor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Locale;
import java.util.Objects;

/**
 * <p>Abstract class for Date/Time/Calendar validation.</p>
 *
 * <p>This is a <i>base</i> class for building Date / Time
 * Validators using format parsing.</p>
 *
 * @version $Revision: 1739356 $
 * @since IBANEntry 1.3.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractCalendarValidator extends AbstractFormatValidator<String> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 2572083746416395942L;

    /**
     * Default {@link AbstractCalendarProcessor} instance
     */
    private final AbstractCalendarProcessor processor;

    /**
     * Construct an instance with the specified <i>strict</i>,
     * <i>time</i> and <i>date</i> style parameters.
     *
     * @param processor the time style to use for Locale validation.
     */
    public AbstractCalendarValidator(final AbstractCalendarProcessor processor) {
        ValidationUtils.notNull(processor, "Processor should not be null");
        this.processor = processor;
    }

    /**
     * <p>Validate using the specified <code>Locale</code>.
     *
     * @param value   The value validation is being performed on.
     * @param pattern The pattern used to format the value.
     * @param locale  The locale to use for the Format, defaults to the default
     * @return <code>true</code> if the value is valid.
     */
    @Override
    public boolean validate(final String value, final String pattern, final Locale locale) {
        return Objects.nonNull(this.getProcessor().process(value, pattern, locale));
    }
}
