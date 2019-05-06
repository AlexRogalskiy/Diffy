package com.wildbeeslabs.sensiblemetrics.diffy.exception;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.StringUtils.formatMessage;

/**
 * Method invocation {@link RuntimeException} implementation
 */
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MethodInvocationException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -6770900397605904118L;

    public MethodInvocationException(final String message) {
        super(message);
    }

    public MethodInvocationException(final Throwable cause) {
        super(cause);
    }

    public MethodInvocationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@link MethodInvocationException} instance by input parameters
     *
     * @param methodName - initial input property name {@link String}
     * @param target     - initial input source target {@link Object}
     * @param throwable  - initial input cause instance {@link Throwable}
     * @return {@link MethodInvocationException} instance
     */
    public static final MethodInvocationException throwMethodInvocation(final String methodName, final Object target, final Throwable throwable) {
        throw new MethodInvocationException(formatMessage("ERROR: cannot invoke method: {%s} on target: {%s}", methodName, target), throwable);
    }
}
