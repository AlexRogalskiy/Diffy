package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

/**
 * A consumer that allows implementations to throw exceptions.
 *
 * @author Steve Chernyak
 * @since 3.0
 */
@FunctionalInterface
public interface EventConsumer<T> {

    /**
     * Performs this operation on the given argument.
     *
     * @param var the input argument
     * @throws Exception if the operation results in an exception
     */
    void accept(final T var);
}
