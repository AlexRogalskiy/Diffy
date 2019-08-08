package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;

/**
 * Check that the number being validated is greater than or equal to the minimum
 * value specified.
 */
public class DecimalMinValidatorForLong extends AbstractDecimalMinValidator<Long> {

    public DecimalMinValidatorForLong(final String minValue, final boolean inclusive) {
        super(minValue, inclusive);
    }

    @Override
    protected int compare(final Long number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMinValue());
    }
}
