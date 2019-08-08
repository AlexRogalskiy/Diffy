package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.DecimalNumberComparatorHelper;

import java.math.BigInteger;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 *
 * @author Marko Bekhta
 */
public class DecimalMaxValidatorForBigInteger extends AbstractDecimalMaxValidator<BigInteger> {

    public DecimalMaxValidatorForBigInteger(final String maxValue, final boolean inclusive) {
        super(maxValue, inclusive);
    }

    @Override
    protected int compare(final BigInteger number) {
        return DecimalNumberComparatorHelper.compare(number, this.getMaxValue());
    }
}
