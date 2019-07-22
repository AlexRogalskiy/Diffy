package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

import java.util.Arrays;
import java.util.List;

/**
 * Interface towards the Event Handling components of an application. This interface provides a friendlier API toward
 * the event bus. The EventGateway allows for components to easily publish events.
 */
public interface EventGateway<T> extends EventDispatchInterceptorSupport<T, Event<T>> {

    /**
     * Publish a collection of events on this bus (one, or multiple). The events will be dispatched to all subscribed
     * listeners.
     * <p>
     * Implementations may treat the given {@code events} as a single batch and distribute the events as such to
     * all subscribed EventListeners.
     *
     * @param events The collection of events to publish
     */
    default void publish(final Event<T>... events) {
        publish(Arrays.asList(events));
    }

    /**
     * Publish a collection of events on this bus (one, or multiple). The events will be dispatched to all subscribed
     * listeners.
     * <p>
     * Implementations may treat the given {@code events} as a single batch and distribute the events as such to
     * all subscribed EventListeners.
     *
     * @param events The collection of events to publish
     */
    void publish(final List<? extends Event<T>> events);
}
