package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.InfinityNumberComparatorHelper;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 */
public class DecimalMaxValidatorForDouble extends AbstractDecimalMaxValidator<Double> {

    public DecimalMaxValidatorForDouble(final String maxValue, final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    protected int compare(final Double number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMaxValue(), InfinityNumberComparatorHelper.GREATER_THAN);
    }
}
