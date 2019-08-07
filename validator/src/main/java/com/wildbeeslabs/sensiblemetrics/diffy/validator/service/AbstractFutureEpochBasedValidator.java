package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import java.time.Clock;
import java.time.Duration;

/**
 * Base class for all {@code @Future} validators that use an epoch to be compared to the time reference.
 */
public abstract class AbstractFutureEpochBasedValidator extends AbstractEpochBasedTimeValidator<Integer> {

    public AbstractFutureEpochBasedValidator(final Clock baseClock, final Duration offsetDuration) {
        super(baseClock, offsetDuration);
    }

    @Override
    public boolean validate(final Integer result) {
        return result > 0;
    }

    @Override
    protected Duration getEffectiveTemporalValidationTolerance(final Duration absoluteTemporalValidationTolerance) {
        return absoluteTemporalValidationTolerance.negated();
    }
}
