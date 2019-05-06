package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Property access {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PropertyAccessException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 531677297942341865L;

    public PropertyAccessException(final String message) {
        super(message);
    }

    public PropertyAccessException(final Throwable cause) {
        super(cause);
    }

    public PropertyAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link PropertyAccessException} instance by input parameters
     *
     * @param propertyName  - initial input property name {@link String}
     * @param propertyValue - initial input property value {@link String}
     * @param throwable     - initial input cause instance {@link Throwable}
     * @return {@link PropertyAccessException} instance
     */
    public static final PropertyAccessException throwIllegalAccess(final String propertyName, final Object propertyValue, final Throwable throwable) {
        throw new PropertyAccessException(formatMessage("ERROR: cannot access property: {%s} with value: {%s}", propertyName, propertyValue), throwable);
    }

    /**
     * Returns {@link PropertyAccessException} instance by input parameters
     *
     * @param throwable - initial input cause instance {@link Throwable}
     * @param target    - initial input raw message {@link String}
     * @param target    - initial input source target {@link Object}
     * @return {@link PropertyAccessException} instance
     */
    public static final PropertyAccessException throwIllegalAccess(final Throwable throwable, final String message, final Object... target) {
        throw new PropertyAccessException(formatMessage(message, target), throwable);
    }
}
