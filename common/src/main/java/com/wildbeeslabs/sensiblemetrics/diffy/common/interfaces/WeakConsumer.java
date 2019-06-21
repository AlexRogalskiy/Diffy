package com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Weak {@link Consumer} declaration
 *
 * @param <T> type of consumer item
 */
public class WeakConsumer<T> implements Consumer<T> {

    private final WeakReference<Consumer<T>> reference;

    public WeakConsumer(final Consumer<T> consumer) {
        reference = new WeakReference<>(consumer);
    }

    @Override
    public void accept(final T t) {
        final Consumer<T> consumer = this.reference.get();
        if (Objects.nonNull(consumer)) {
            consumer.accept(t);
        }
    }
}
