package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception.MatchOperationException;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Matcher that will match an Object if all the fields on that Object contain values equal to the same field in the
 * expected instance.
 *
 * @param <T> The type of object
 * @author Allard Buijze
 * @since 1.1
 */
public class EqualFieldsMatcher<T> extends AbstractMatcher<T> {

    private final T expected;
    private Predicate<Field> predicate;
    private Field failedField;
    private Object failedFieldExpectedValue;
    private Object failedFieldActualValue;

    /**
     * Initializes an EqualFieldsMatcher that will match an object with equal properties as the given
     * {@code expected} object.
     *
     * @param expected The expected object
     */
    public EqualFieldsMatcher(final T expected) {
        this(expected, v -> true);
    }

    /**
     * Initializes an EqualFieldsMatcher that will match an object with equal properties as the given
     * {@code expected} object.
     *
     * @param expected  The expected object
     * @param predicate The filter describing the fields to include in the comparison
     */
    public EqualFieldsMatcher(final T expected, final Predicate<Field> predicate) {
        this.expected = expected;
        this.predicate = predicate;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(final Object item) {
        return expected.getClass().isInstance(item) && matchesSafely(item);
    }

    private boolean matchesSafely(final Object actual) {
        return expected.getClass().equals(actual.getClass())
            && fieldsMatch(expected.getClass(), expected, actual);
    }

    private boolean fieldsMatch(final Class<?> aClass, final Object expectedValue, final Object actual) {
        boolean match = true;
        for (final Field field : aClass.getDeclaredFields()) {
            if (predicate.test(field)) {
                field.setAccessible(true);
                try {
                    Object expectedFieldValue = field.get(expectedValue);
                    Object actualFieldValue = field.get(actual);
                    if (!Objects.deepEquals(expectedFieldValue, actualFieldValue)) {
                        failedField = field;
                        failedFieldExpectedValue = expectedFieldValue;
                        failedFieldActualValue = actualFieldValue;
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    throw new MatchOperationException("Could not confirm object equality due to an exception", e);
                }
            }
        }
        if (aClass.getSuperclass() != Object.class) {
            match = fieldsMatch(aClass.getSuperclass(), expectedValue, actual);
        }
        return match;
    }

    /**
     * Returns the field that failed comparison, if any. This value is only populated after {@link #matches(Object)} is
     * called and a mismatch has been detected.
     *
     * @return the field that failed comparison, if any
     */
    public Field getFailedField() {
        return failedField;
    }

    /**
     * Returns the expected value of a failed field comparison, if any. This value is only populated after {@link
     * #matches(Object)} is called and a mismatch has been detected.
     *
     * @return the expected value of the field that failed comparison, if any
     */
    public Object getFailedFieldExpectedValue() {
        return failedFieldExpectedValue;
    }

    /**
     * Returns the actual value of a failed field comparison, if any. This value is only populated after {@link
     * #matches(Object)} is called and a mismatch has been detected.
     *
     * @return the actual value of the field that failed comparison, if any
     */
    public Object getFailedFieldActualValue() {
        return failedFieldActualValue;
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText(this.expected.getClass().getName());
        if (this.failedField != null) {
            description.appendText(" (failed on field '")
                .appendText(this.failedField.getName())
                .appendText("')");
        }
    }
}
