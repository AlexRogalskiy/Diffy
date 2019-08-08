package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.math.BigDecimal;

/**
 * Validates that the character sequence (e.g. string) being validated consists of digits,
 * and matches the pattern defined in the constraint.
 */
public class DigitsValidatorForCharSequence implements Validator<CharSequence> {
    private final int maxIntegerLength;
    private final int maxFractionLength;

    public DigitsValidatorForCharSequence(final int maxIntegerLength, final int maxFractionLength) {
        ValidationUtils.isTrue(maxIntegerLength >= 0, "Max integer length should be greater than zero");
        ValidationUtils.isTrue(maxFractionLength >= 0, "Max fraction length should be greater than zero");

        this.maxIntegerLength = maxIntegerLength;
        this.maxFractionLength = maxFractionLength;
    }

    @Override
    public boolean validate(final CharSequence charSequence) {
        if (charSequence == null) {
            return true;
        }
        final BigDecimal bigNum = getBigDecimalValue(charSequence);
        if (bigNum == null) {
            return false;
        }
        final int integerPartLength = bigNum.precision() - bigNum.scale();
        final int fractionPartLength = bigNum.scale() < 0 ? 0 : bigNum.scale();
        return (this.maxIntegerLength >= integerPartLength && this.maxFractionLength >= fractionPartLength);
    }

    private BigDecimal getBigDecimalValue(final CharSequence charSequence) {
        try {
            return new BigDecimal(charSequence.toString());
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
}
