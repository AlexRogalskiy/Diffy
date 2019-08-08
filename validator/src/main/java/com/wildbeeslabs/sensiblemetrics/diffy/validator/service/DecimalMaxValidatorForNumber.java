package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 *
 * @author Marko Bekhta
 */
public class DecimalMaxValidatorForNumber extends AbstractDecimalMaxValidator<Number> {

    public DecimalMaxValidatorForNumber(final String maxValue, final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    protected int compare(final Number number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMaxValue());
    }
}
