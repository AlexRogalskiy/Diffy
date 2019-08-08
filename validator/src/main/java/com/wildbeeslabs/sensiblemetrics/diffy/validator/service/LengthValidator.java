package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

/**
 * Check that the character sequence length is between min and max.
 */
public class LengthValidator implements Validator<CharSequence> {
    private final int min;
    private final int max;

    public LengthValidator(final int min, final int max) {
        ValidationUtils.isTrue(min >= 0, "Minimum value should be greater or equal to zero");
        ValidationUtils.isTrue(max >= 0, "Maximum value should be greater or equal to zero");
        ValidationUtils.isTrue(max >= min, "Maximum value should be greater that minimum value");

        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validate(final CharSequence value) {
        if (value == null) {
            return true;
        }
        int length = value.length();
        return length >= this.min && length <= this.max;
    }
}
