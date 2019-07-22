package com.wildbeeslabs.sensiblemetrics.diffy.common.event.monitor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;

/**
 * A message monitor that returns a NoOp message callback
 *
 * @author Marijn van Zelst
 * @since 3.0
 */
public enum NoOpMessageMonitor implements MessageMonitor<Object, Event<Object>> {

    /**
     * Singleton instance of a {@link NoOpMessageMonitor}.
     */
    INSTANCE;

    /**
     * Returns the instance of {@code {@link NoOpMessageMonitor}}.
     * This method is a convenience method, which can be used as a lambda expression
     *
     * @return the instance of {@code {@link NoOpMessageMonitor}}
     */
    @SuppressWarnings("SameReturnValue")
    public static NoOpMessageMonitor instance() {
        return INSTANCE;
    }

    @Override
    public MonitorCallback onEventIngested(final Event event) {
        return NoOpMessageMonitorCallback.INSTANCE;
    }

    /**
     * A NoOp MessageMonitor callback
     */
    public enum NoOpMessageMonitorCallback implements MessageMonitor.MonitorCallback {

        /**
         * Singleton instance of a {@link NoOpMessageMonitorCallback}.
         */
        INSTANCE;

        @Override
        public void reportSuccess() {
        }

        @Override
        public void reportFailure(final Throwable cause) {
        }

        @Override
        public void reportIgnored() {

        }
    }
}
