package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Specifies a mechanism to monitor message processing. When a message is supplied to
 * a message monitor it returns a callback which should be used to notify the message monitor
 * of the result of the processing of the event.
 * <p>
 * For example, a message monitor can track various things like message processing times, failure and success rates and
 * occurred exceptions. It also can gather information contained in messages headers like timestamps and tracers
 *
 * @author Marijn van Zelst
 * @author Nakul Mishra
 * @since 3.0
 */
public interface MessageMonitor<T, E extends Event<T>> {

    /**
     * Takes a message and returns a callback that should be used
     * to inform the message monitor about the result of processing the message
     *
     * @param event the message to monitor
     * @return the callback
     */
    MonitorCallback onEventIngested(final E event);

    /**
     * Takes a collection of messages and returns a map containing events along with their callbacks
     *
     * @param events to monitor
     * @return map where key = event and value = the callback
     */
    default Map<? super T, MonitorCallback> onMessagesIngested(final Collection<? extends E> events) {
        return events.stream().collect(Collectors.toMap(event -> event, this::onEventIngested));
    }

    /**
     * An interface to let the message processor inform the message monitor of the result
     * of processing the message
     */
    interface MonitorCallback {

        /**
         * Notify the monitor that the message was handled successfully
         */
        void reportSuccess();

        /**
         * Notify the monitor that a failure occurred during processing of the message
         *
         * @param cause or {@code null} if unknown
         */
        void reportFailure(final Throwable cause);

        /**
         * Notify the monitor that the message was ignored
         */
        void reportIgnored();
    }
}
