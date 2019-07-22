package com.wildbeeslabs.sensiblemetrics.diffy.common.event.monitor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Delegates messages and callbacks to the given list of message monitors
 *
 * @author Marijn van Zelst
 * @since 3.0
 */
public class MultiMessageMonitor<T, E extends Event<T>> implements MessageMonitor<T, E> {

    private final List<MessageMonitor<T, ? super E>> messageMonitors;

    /**
     * Initialize a message monitor with the given <name>messageMonitors</name>
     *
     * @param messageMonitors the list of event monitors to delegate to
     */
    @SafeVarargs
    public MultiMessageMonitor(final MessageMonitor<T, ? super E>... messageMonitors) {
        this(Arrays.asList(messageMonitors));
    }

    /**
     * Initialize a message monitor with the given list of <name>messageMonitors</name>
     *
     * @param messageMonitors the list of event monitors to delegate to
     */
    public MultiMessageMonitor(List<MessageMonitor<T, ? super E>> messageMonitors) {
        ValidationUtils.notNull(messageMonitors, "MessageMonitor list may not be null");
        this.messageMonitors = new ArrayList<>(messageMonitors);
    }

    /**
     * Calls the message monitors with the given message and returns a callback
     * that will trigger all the message monitor callbacks
     *
     * @param message the message to delegate to the message monitors
     * @return the callback that will trigger all the message monitor callbacks
     */
    public MonitorCallback onEventIngested(final E message) {
        final List<MonitorCallback> monitorCallbacks = this.messageMonitors.stream()
            .map(messageMonitor -> messageMonitor.onEventIngested(message))
            .collect(Collectors.toList());

        return new MonitorCallback() {
            @Override
            public void reportSuccess() {
                monitorCallbacks.forEach(MonitorCallback::reportSuccess);
            }

            @Override
            public void reportFailure(Throwable cause) {
                monitorCallbacks.forEach(resultCallback -> resultCallback.reportFailure(cause));
            }

            @Override
            public void reportIgnored() {
                monitorCallbacks.forEach(MonitorCallback::reportIgnored);
            }
        };
    }
}
