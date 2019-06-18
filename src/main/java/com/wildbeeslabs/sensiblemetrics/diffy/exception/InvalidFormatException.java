package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.formatMessage;

/**
 * Invalid format {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvalidFormatException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 9096822578867185563L;

    /**
     * Invalid format exception constructor with initial input message
     *
     * @param message - initial input message {@link String}
     */
    public InvalidFormatException(final String message) {
        super(message);
    }

    /**
     * Invalid format exception constructor with initial input {@link Throwable}
     *
     * @param cause - initial input cause target {@link Throwable}
     */
    public InvalidFormatException(final Throwable cause) {
        super(cause);
    }

    /**
     * Invalid format exception constructor with initial input message and {@link Throwable}
     *
     * @param message - initial input message {@link String}
     * @param cause   - initial input cause target {@link Throwable}
     */
    public InvalidFormatException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link InvalidFormatException} by input parameters
     *
     * @param target    - initial input source target {@link Object}
     * @param throwable - initial input cause target {@link Throwable}
     * @return {@link InvalidFormatException}
     */
    @Factory
    public static final InvalidFormatException throwInvalidFormat(final Object target, final Throwable throwable) {
        throw new InvalidFormatException(formatMessage("ERROR: unsupported format on target = {%s}", target), throwable);
    }
}
