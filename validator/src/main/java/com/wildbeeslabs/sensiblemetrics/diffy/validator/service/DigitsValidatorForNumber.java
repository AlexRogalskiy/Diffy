package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.math.BigDecimal;

/**
 * Validates that the {@code Number} being validated matches the pattern
 * defined in the constraint.
 */
public class DigitsValidatorForNumber implements Validator<Number> {
    private final int maxIntegerLength;
    private final int maxFractionLength;

    public DigitsValidatorForNumber(final int maxIntegerLength, final int maxFractionLength) {
        ValidationUtils.isTrue(maxIntegerLength > 0, "Max integer length should be greater than zero");
        ValidationUtils.isTrue(maxFractionLength > 0, "Max fraction length should be greater than zero");

        this.maxIntegerLength = maxIntegerLength;
        this.maxFractionLength = maxFractionLength;
    }

    @Override
    public boolean validate(final Number num) {
        if (num == null) {
            return true;
        }
        BigDecimal bigNum;
        if (num instanceof BigDecimal) {
            bigNum = (BigDecimal) num;
        } else {
            bigNum = new BigDecimal(num.toString()).stripTrailingZeros();
        }
        final int integerPartLength = bigNum.precision() - bigNum.scale();
        final int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
        return (this.maxIntegerLength >= integerPartLength && this.maxFractionLength >= fractionPartLength);
    }
}
