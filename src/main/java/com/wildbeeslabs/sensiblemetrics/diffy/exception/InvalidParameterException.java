package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Invalid parameter {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidParameterException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5312338835743224799L;

    public InvalidParameterException(final String message) {
        super(message);
    }

    public InvalidParameterException(final Throwable cause) {
        super(cause);
    }

    public InvalidParameterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidParameterException} instance by input parameters
     *
     * @param message   - initial input raw message {@link String}
     * @param throwable - initial input cause instance {@link Throwable}
     * @return {@link InvalidParameterException} instance
     */
    public static final InvalidParameterException throwInvalidParameter(final String message, final Throwable throwable) {
        throw new InvalidParameterException(message, throwable);
    }

    /**
     * Returns {@link InvalidParameterException} instance by input parameters
     *
     * @param message - initial input raw message {@link String}
     * @return {@link InvalidParameterException} instance
     */
    public static final InvalidParameterException throwInvalidParameter(final String message) {
        throw new InvalidParameterException(message);
    }
}
