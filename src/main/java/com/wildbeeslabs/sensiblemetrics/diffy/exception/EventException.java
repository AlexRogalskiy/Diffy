package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Event {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EventException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8527860211271422953L;

    /**
     * Event exception constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public EventException(final String message) {
        super(message);
    }

    /**
     * Event exception constructor with initial input {@link Throwable}
     *
     * @param cause - initial input cause target {@link Throwable}
     */
    public EventException(final Throwable cause) {
        super(cause);
    }

    /**
     * Event constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input cause target {@link Throwable}
     */
    public EventException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link EventException} by input parameters
     *
     * @param message - initial input raw message {@link String}
     * @return {@link EventException}
     */
    @Factory
    public static final EventException throwExecutionError(final String message) {
        throw new EventException(message);
    }

    /**
     * Returns {@link EventException} by input parameters
     *
     * @param message   - initial input raw message {@link String}
     * @param throwable - initial input cause target {@link Throwable}
     * @return {@link EventException}
     */
    @Factory
    public static final EventException throwExecutionError(final String message, final Throwable throwable) {
        throw new EventException(message, throwable);
    }
}
