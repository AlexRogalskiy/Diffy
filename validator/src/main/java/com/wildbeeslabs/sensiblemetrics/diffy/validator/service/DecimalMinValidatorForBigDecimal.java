package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;

import java.math.BigDecimal;

/**
 * Check that the number being validated is greater than or equal to the minimum
 * value specified.
 *
 * @author Marko Bekhta
 */
public class DecimalMinValidatorForBigDecimal extends AbstractDecimalMinValidator<BigDecimal> {

    public DecimalMinValidatorForBigDecimal(final String value, final boolean inclusive) {
        super(value, inclusive);
    }

    @Override
    protected int compare(final BigDecimal number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMinValue());
    }
}
