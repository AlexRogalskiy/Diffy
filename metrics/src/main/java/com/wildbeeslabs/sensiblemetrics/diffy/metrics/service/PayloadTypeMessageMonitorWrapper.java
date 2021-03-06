package com.wildbeeslabs.sensiblemetrics.diffy.metrics.service;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.MessageMonitor;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A {@link MessageMonitor} implementation which creates a new MessageMonitor for every {@link Message} payload type
 * ingested by it. The PayloadTypeMessageMonitorWrapper keeps track of all distinct payload types it has created
 * and only creates a new one if there is none present.
 * <p>
 * The type of MessageMonitor which is created for every payload type is configurable, as long as it implements
 * MessageMonitor and {@link MetricSet}.
 *
 * @param <T> The type of the MessageMonitor created for every payload type.Must implement both {@link MessageMonitor}
 *            and {@link MetricSet}
 * @author Steven van Beelen
 * @since 3.0
 */
public class PayloadTypeMessageMonitorWrapper<T, C extends MessageMonitor<T, Event<T>> & MetricSet> implements MessageMonitor<T, Event<T>>, MetricSet {
    private final Supplier<C> monitorSupplier;
    private final Function<Class<?>, String> monitorNameBuilder;
    private final Map<String, C> payloadTypeMonitors;
    private final Map<String, Metric> metricSet;

    /**
     * Create a PayloadTypeMessageMonitorWrapper which builds monitors through a given {@code monitorSupplier} for
     * every message payload type encountered.
     *
     * @param monitorSupplier A Supplier of MessageMonitors of type {@code C} for every encountered payload type
     */
    public PayloadTypeMessageMonitorWrapper(final Supplier<C> monitorSupplier) {
        this(monitorSupplier, Class::getName);
    }

    /**
     * Create a PayloadTypeMessageMonitorWrapper which builds monitors through a given {@code monitorSupplier} for
     * every message payload type encountered and sets the monitor name as specified by the {@code monitorNameBuilder}.
     *
     * @param monitorSupplier    A Supplier of MessageMonitors of type {@code C} for every encountered payload type
     * @param monitorNameBuilder A Function where the payload type is the input (of type {@code Class<?>}) and output
     *                           is the desired name for the monitor (of type {@code String})
     */
    public PayloadTypeMessageMonitorWrapper(final Supplier<C> monitorSupplier, final Function<Class<?>, String> monitorNameBuilder) {
        this.monitorSupplier = monitorSupplier;
        this.monitorNameBuilder = monitorNameBuilder;
        this.payloadTypeMonitors = new ConcurrentHashMap<>();
        this.metricSet = Collections.unmodifiableMap(payloadTypeMonitors);
    }

    @Override
    public MonitorCallback onEventIngested(final Event<T> event) {
        final String monitorName = monitorNameBuilder.apply(event.getClass());
        final MessageMonitor<T, Event<T>> messageMonitorForPayloadType = this.payloadTypeMonitors.computeIfAbsent(monitorName, payloadType -> monitorSupplier.get());
        return messageMonitorForPayloadType.onEventIngested(event);
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public Map<String, Metric> getMetrics() {
        return metricSet;
    }
}
