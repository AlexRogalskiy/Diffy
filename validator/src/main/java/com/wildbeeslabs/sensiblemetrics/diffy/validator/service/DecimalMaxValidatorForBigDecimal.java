package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;

import java.math.BigDecimal;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 */
public class DecimalMaxValidatorForBigDecimal extends AbstractDecimalMaxValidator<BigDecimal> {

    public DecimalMaxValidatorForBigDecimal(final String maxValue, final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    protected int compare(final BigDecimal number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMaxValue());
    }
}
