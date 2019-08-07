package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Check that the number being validated is greater than or equal to the minimum
 * value specified.
 */
@RequiredArgsConstructor
public abstract class AbstractMinValidator<T> implements Validator<T> {
    protected final long minValue;

    @Override
    public boolean validate(final T value) {
        if (value == null) {
            return true;
        }
        return this.compare(value) >= 0;
    }

    protected abstract int compare(final T number);
}
