package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Interceptor that allows messages to be intercepted and modified before they are dispatched. This interceptor
 * provides a very early means to alter or reject Messages, even before any Unit of Work is created.
 *
 * @param <T> The message type this interceptor can process
 * @author Allard Buijze
 * @since 2.0
 */
public interface MessageDispatchInterceptor<T, E extends Event<T>> {

    /**
     * Invoked each time a message is about to be dispatched. The given {@code message} represents the message
     * being dispatched.
     *
     * @param message The message intended to be dispatched
     * @return the message to dispatch
     */
    default E handle(final E message) {
        return handle(Collections.singletonList(message)).apply(0, message);
    }

    /**
     * Apply this interceptor to the given list of {@code messages}. This method returns a function that can be
     * invoked to obtain a modified version of messages at each position in the list.
     *
     * @param messages The Messages to pre-process
     * @return a function that processes messages based on their position in the list
     */
    BiFunction<Integer, E, E> handle(final List<? extends E> messages);

}
