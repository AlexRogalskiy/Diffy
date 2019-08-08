package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.time.Clock;
import java.time.Duration;

/**
 * Base class for all time validators that use an epoch to be compared to the time reference.
 */
public abstract class AbstractEpochBasedTimeValidator<T> implements Validator<T> {
    private Clock referenceClock;
    private Clock baseClock;
    private Duration offsetDuration;

    public AbstractEpochBasedTimeValidator(final Clock baseClock, final Duration offsetDuration) {
        this.baseClock = baseClock;
        this.offsetDuration = offsetDuration;
        this.initialize();
    }

    protected void initialize() {
        try {
            this.referenceClock = Clock.offset(this.baseClock, this.offsetDuration);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean validate(final T value) {
        if (value == null) {
            return true;
        }
        final int result = Long.compare(getEpochMillis(value, referenceClock), referenceClock.millis());
        return this.isValid(result);
    }

    /**
     * Returns the temporal validation tolerance to apply.
     */
    protected abstract Duration getEffectiveTemporalValidationTolerance(Duration absoluteTemporalValidationTolerance);

    /**
     * Returns the millisecond based instant measured from Epoch. In the case of partials requiring a time reference, we
     * use the {@link Clock} provided by the {@link ClockProvider}.
     */
    protected abstract long getEpochMillis(T value, Clock reference);

    /**
     * Returns whether the result of the comparison between the validated value and the time reference is considered
     * valid.
     */
    protected abstract boolean isValid(int result);

}
