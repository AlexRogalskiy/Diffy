package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Illegal access {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class IllegalAccessException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8083840060136145248L;

    public IllegalAccessException(final String message) {
        super(message);
    }

    public IllegalAccessException(final Throwable cause) {
        super(cause);
    }

    public IllegalAccessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link IllegalAccessException} instance by input parameters
     *
     * @param message - initial input raw message {@link String}
     * @return {@link IllegalAccessException} instance
     */
    public static final IllegalAccessException throwIllegalAccess(final String message) {
        throw new IllegalAccessException(message);
    }

    /**
     * Returns {@link IllegalAccessException} instance by input parameters
     *
     * @param message   - initial input raw message {@link String}
     * @param throwable - initial input cause instance {@link Throwable}
     * @return {@link IllegalAccessException} instance
     */
    public static final IllegalAccessException throwIllegalAccess(final String message, final Throwable throwable) {
        throw new IllegalAccessException(message, throwable);
    }
}
