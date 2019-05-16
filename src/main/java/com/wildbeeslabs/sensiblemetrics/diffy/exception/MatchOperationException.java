package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Illegal access {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MatchOperationException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -6770900397605904118L;

    /**
     * Match operation exception constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public MatchOperationException(final String message) {
        super(message);
    }

    /**
     * Match operation exception constructor with initial input {@link Throwable}
     *
     * @param cause - initial input {@link Throwable}
     */
    public MatchOperationException(final Throwable cause) {
        super(cause);
    }

    /**
     * Match operation exception constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input {@link Throwable}
     */
    public MatchOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link MatchOperationException} instance by input parameters
     *
     * @param target    - initial input source target {@link Object}
     * @param throwable - initial input cause instance {@link Throwable}
     * @return {@link MatchOperationException}
     */
    public static final MatchOperationException throwIncorrectMatch(final Object target, final Throwable throwable) {
        throw new MatchOperationException(formatMessage("ERROR: cannot process match operation on target: {%s}", target), throwable);
    }
}
