package com.wildbeeslabs.sensiblemetrics.diffy.common.event.monitor;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A {@link MessageMonitor} implementation which creates a new MessageMonitor for every {@link Event} payload type
 * ingested by it. The PayloadTypeMessageMonitorWrapper keeps track of all distinct payload types it has created
 * and only creates a new one if there is none present.
 * <p>
 * The type of MessageMonitor which is created for every payload type is configurable, as long as it implements
 * MessageMonitor}.
 *
 * @param <T> The type of the MessageMonitor created for every payload type.Must implement both {@link MessageMonitor}
 * @author Steven van Beelen
 * @author Marijn van Zelst
 * @since 4.1
 */
public class PayloadTypeMessageMonitorWrapper<T, C extends MessageMonitor<T, Event<T>>> implements MessageMonitor<T, Event<T>> {
    private final Function<String, C> monitorSupplier;
    private final Map<String, C> payloadTypeMonitors;
    private final Function<Class<?>, String> monitorNameBuilder;

    /**
     * Create a PayloadTypeMessageMonitorWrapper which builds monitors through a given {@code monitorSupplier} for
     * every message payload type encountered.
     *
     * @param monitorSupplier A Supplier of MessageMonitors of type {@code C} for every encountered payload type
     */
    public PayloadTypeMessageMonitorWrapper(final Function<String, C> monitorSupplier) {
        this(monitorSupplier, Class::getName);
    }

    /**
     * Create a PayloadTypeMessageMonitorWrapper which builds monitors through a given {@code monitorSupplier} for
     * every message payload type encountered and sets the monitor name as specified by the {@code monitorNameBuilder}.
     *
     * @param monitorSupplier    A Function to create a MessageMonitor of type {@code T}, with the given {@code String}
     *                           as name, for every encountered payload type
     * @param monitorNameBuilder A Function where the payload type is the input (of type {@code Class<?>}) and output
     *                           is the desired name for the monitor (of type {@code String})
     */
    public PayloadTypeMessageMonitorWrapper(final Function<String, C> monitorSupplier, final Function<Class<?>, String> monitorNameBuilder) {
        this.monitorSupplier = monitorSupplier;
        this.payloadTypeMonitors = new ConcurrentHashMap<>();
        this.monitorNameBuilder = monitorNameBuilder;
    }

    @Override
    public MonitorCallback onEventIngested(final Event<T> event) {
        final String monitorName = this.monitorNameBuilder.apply(event.getClass());
        final MessageMonitor<T, Event<T>> messageMonitorForPayloadType = this.payloadTypeMonitors.computeIfAbsent(monitorName, monitorSupplier);
        return messageMonitorForPayloadType.onEventIngested(event);
    }
}
