package com.wildbeeslabs.sensiblemetrics.diffy.validator;

/**
 * Field name {@link Validator}
 */
public class FieldNameValidator<T> implements Validator<T> {

    @Override
    public boolean validate(final T value) {
        return true;
    }

    @Override
    public Validator getValidatorForField(final String fieldName) {
        return this;
    }
}
