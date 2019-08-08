package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Check that the number being validated is greater than or equal to the minimum
 * value specified.
 **/
@Data
public abstract class AbstractDecimalMinValidator<T> implements Validator<T> {
    private final BigDecimal minValue;
    private final boolean inclusive;

    public AbstractDecimalMinValidator(final String minValue, final boolean inclusive) {
        this.minValue = this.parseValue(minValue);
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
        final int comparisonResult = this.compare(value);
        return this.inclusive ? comparisonResult >= 0 : comparisonResult > 0;
    }

    protected abstract int compare(final T number);
}
