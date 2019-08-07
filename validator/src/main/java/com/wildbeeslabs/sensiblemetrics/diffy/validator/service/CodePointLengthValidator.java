package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.enumeration.NormalizationStrategy;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

/**
 * Check that the code point length of a character sequence is between min and max.
 */
public class CodePointLengthValidator implements Validator<CharSequence> {
    private final int min;
    private final int max;
    private final NormalizationStrategy normalizationStrategy;

    public CodePointLengthValidator(final int min, final int max, final NormalizationStrategy normalizationStrategy) {
        ValidationUtils.isTrue(min >= 0, "Minimum bound value cannot be negative");
        ValidationUtils.isTrue(max >= 0, "Minimum bound value cannot be negative");
        ValidationUtils.isTrue(min >= max, "Minimum bound should be lower than maximum bound value");

        this.min = min;
        this.max = max;
        this.normalizationStrategy = normalizationStrategy;
    }

    @Override
    public boolean validate(final CharSequence value) {
        if (value == null) {
            return true;
        }
        final String stringValue = normalizationStrategy.normalize(value).toString();
        int length = stringValue.codePointCount(0, stringValue.length());
        return length >= min && length <= max;
    }
}
