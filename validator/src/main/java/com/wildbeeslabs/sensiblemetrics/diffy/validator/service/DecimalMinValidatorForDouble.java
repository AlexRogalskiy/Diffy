package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.InfinityNumberComparatorHelper;

/**
 * Check that the number being validated is greater than or equal to the minimum
 * value specified.
 */
public class DecimalMinValidatorForDouble extends AbstractDecimalMinValidator<Double> {

    public DecimalMinValidatorForDouble(final String minValue, final boolean inclusive) {
        super(minValue, inclusive);
    }

    @Override
    protected int compare(final Double number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMinValue(), InfinityNumberComparatorHelper.LESS_THAN);
    }
}
