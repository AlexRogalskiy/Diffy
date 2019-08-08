package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 */
@Getter
public abstract class AbstractDecimalMaxValidator<T> implements Validator<T> {
    private final BigDecimal maxValue;
    private final boolean inclusive;

    public AbstractDecimalMaxValidator(final String maxValue, final boolean inclusive) {
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
    public boolean validate(final T value) {
        if (value == null) {
            return true;
        }
        int comparisonResult = this.compare(value);
        return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
    }

    protected abstract int compare(final T number);
}
