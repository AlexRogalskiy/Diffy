package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.time.Duration;

/**
 * Checks that a validated {@link Duration} length is shorter than or equal to the
 * one specified with the annotation.
 */
public class DurationMaxValidator implements Validator<Duration> {
    private final Duration maxDuration;
    private final boolean inclusive;

    public DurationMaxValidator(final Duration maxDuration, final boolean inclusive) {
        ValidationUtils.notNull(maxDuration, "Maximum duration should not be null");
        this.maxDuration = maxDuration;
        this.inclusive = inclusive;
    }

    @Override
    public boolean validate(final Duration value) {
        if (value == null) {
            return true;
        }
        int comparisonResult = this.maxDuration.compareTo(value);
        return this.inclusive ? comparisonResult >= 0 : comparisonResult > 0;
    }
}
