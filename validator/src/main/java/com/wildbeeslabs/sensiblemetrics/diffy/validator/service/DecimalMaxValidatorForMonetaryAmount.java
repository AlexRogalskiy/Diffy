package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

/**
 * Check that the monetary amount being validated is less than or equal to the maximum
 * value specified.
 */
public class DecimalMaxValidatorForMonetaryAmount implements Validator<MonetaryAmount> {
    private final BigDecimal maxValue;
    private final boolean inclusive;

    public DecimalMaxValidatorForMonetaryAmount(final String maxValue, final boolean inclusive) {
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
    public boolean validate(final MonetaryAmount value) {
        if (value == null) {
            return true;
        }
        int comparisonResult = value.getNumber().numberValueExact(BigDecimal.class).compareTo(this.maxValue);
        return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
    }
}
