package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

/**
 * Interface for a component that processes Messages.
 *
 * @param <T> The message type this handler can process
 * @author Rene de Waele
 * @since 3.0
 */
public interface EventHandler<T, E extends Event<T>> {

    /**
     * Handles the given {@code message}.
     *
     * @param event The message to be processed.
     * @return The result of the message processing.
     * @throws Exception any exception that occurs during message handling
     */
    Object handle(final E event) throws Exception;

    /**
     * Indicates whether this handler can handle the given message
     *
     * @param event The message to verify
     * @return {@code true} if this handler can handle the message, otherwise {@code false}
     */
    default boolean canHandle(final E event) {
        return true;
    }

    /**
     * Returns the instance type that this handler delegates to.
     *
     * @return Returns the instance type that this handler delegates to
     */
    default Class<?> getTargetType() {
        return getClass();
    }
}
