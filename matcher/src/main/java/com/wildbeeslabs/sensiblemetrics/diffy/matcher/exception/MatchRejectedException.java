package com.wildbeeslabs.sensiblemetrics.diffy.matcher.exception;

public class MatchRejectedException extends RuntimeException {
    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 7845397773603840602L;

    /**
     * Constructs a new instance with null as its detail message.
     */
    public MatchRejectedException() {
        super();
    }

    /**
     * Constructs a new instance with the specified detail message.
     *
     * @param message the detail message.
     */
    public MatchRejectedException(final String message) {
        super(message);
    }

    /**
     * Constructs a new throwable with the specified cause.
     *
     * @param cause a chained exception of type <code>Throwable</code>.
     */
    public MatchRejectedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new throwable with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause   a chained exception of type <code>Throwable</code>.
     */
    public MatchRejectedException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
