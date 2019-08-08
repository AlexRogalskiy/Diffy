package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.time.Duration;

/**
 * Checks that a validated {@link Duration} length is longer than or equal to the
 * one specified with the annotation.
 */
public class DurationMinValidator implements Validator<Duration> {
    private final Duration minDuration;
    private final boolean inclusive;

    public DurationMinValidator(final Duration minDuration, final boolean inclusive) {
        ValidationUtils.notNull(minDuration, "Minimum duration should not be null");
        this.minDuration = minDuration;
        this.inclusive = inclusive;
    }

    @Override
    public boolean validate(final Duration value) {
        if (value == null) {
            return true;
        }
        int comparisonResult = this.minDuration.compareTo(value);
        return this.inclusive ? comparisonResult <= 0 : comparisonResult < 0;
    }
}
