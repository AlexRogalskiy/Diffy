package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 */
public class DecimalMinValidatorForMonetaryAmount implements Validator<MonetaryAmount> {
    private final BigDecimal minValue;
    private final boolean inclusive;

    public DecimalMinValidatorForMonetaryAmount(final String minValue, final boolean inclusive) {
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
    public boolean validate(final MonetaryAmount value) {
        if (value == null) {
            return true;
        }
        final int comparisonResult = value.getNumber().numberValueExact(BigDecimal.class).compareTo(this.minValue);
        return this.inclusive ? comparisonResult >= 0 : comparisonResult > 0;
    }
}
