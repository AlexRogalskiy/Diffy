package com.wildbeeslabs.sensiblemetrics.diffy.validator;

/**
 * Validator interface declaration
 *
 * @param <T> type of validated value
 */
@FunctionalInterface
public interface Validator<T> {

    /**
     * Default true {@link Validator}
     */
    Validator<?> DEFAULT_TRUE_INSTANCE = (value) -> true;
    /**
     * Default false {@link Validator}
     */
    Validator<?> DEFAULT_FALSE_INSTANCE = (value) -> false;

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@code T}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    boolean validate(final T value);

    /**
     * Gets a new validator to use for the value of the field with the given name.
     *
     * @param fieldName the field name
     * @return a non-null validator
     */
    default Validator<T> getValidatorForField(final String fieldName) {
        return (Validator<T>) DEFAULT_TRUE_INSTANCE;
    }
}
