package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import lombok.RequiredArgsConstructor;

/**
 * Check that the number being validated is less than or equal to the maximum
 * value specified.
 */
@RequiredArgsConstructor
public abstract class AbstractMaxValidator<T> implements Validator<T> {
    protected final long maxValue;

    @Override
    public boolean validate(final T value) {
        // null values are valid
        if (value == null) {
            return true;
        }
        return this.compare(value) <= 0;
    }

    protected abstract int compare(final T number);
}
