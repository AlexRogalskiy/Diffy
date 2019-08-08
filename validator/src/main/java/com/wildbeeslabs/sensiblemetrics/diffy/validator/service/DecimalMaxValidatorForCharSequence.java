package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.math.BigDecimal;

/**
 * Check that the character sequence (e.g. string) being validated represents a number, and has a value
 * less than or equal to the maximum value specified.
 */
public class DecimalMaxValidatorForCharSequence implements Validator<CharSequence> {
    private final BigDecimal maxValue;
    private final boolean inclusive;

    public DecimalMaxValidatorForCharSequence(final String maxValue, final boolean inclusive) {
        this.maxValue = this.parseValue(maxValue);
        this.inclusive = inclusive;
    }

    private BigDecimal parseValue(final String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException(value);
        }
    }

    @Override
    public boolean validate(final CharSequence value) {
        if (value == null) {
            return true;
        }
        try {
            int comparisonResult = new BigDecimal(value.toString()).compareTo(this.maxValue);
            return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }
}
