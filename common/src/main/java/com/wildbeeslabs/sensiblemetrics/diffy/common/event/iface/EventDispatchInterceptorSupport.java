package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

/**
 * Interface marking components capable of registering Dispatch Interceptors. Generally, these are Messaging components
 * injected into the sending end of the communication.
 * <p>
 * Dispatch Interceptors are always invoked in the thread that dispatches the message to the messaging component. If a
 * Unit of Work is active, it is not that of the dispatched message, but of the message that triggered this message to
 * be published.
 *
 * @param <T> The type of Message the interceptor works with
 */
public interface EventDispatchInterceptorSupport<T, E extends Event<T>> {

    /**
     * Register the given DispatchInterceptor. After registration, the interceptor will be invoked for each Message
     * dispatched on the messaging component that it was registered to.
     *
     * @param dispatchInterceptor The interceptor to register
     * @return a Registration, which may be used to remove the unregister the interceptor
     */
    void registerDispatchInterceptor(final EventDispatchInterceptor<T, ? super E> dispatchInterceptor);
}
