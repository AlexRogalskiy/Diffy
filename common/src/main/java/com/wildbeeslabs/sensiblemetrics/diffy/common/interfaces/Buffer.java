package com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Defines a buffer that wait for the space to become non-empty when retrieving an
 * element, and wait for space to become available in the buffer when
 * storing an element.
 *
 * @param <E>
 * @author Nakul Mishra
 * @since 3.3
 */
public interface Buffer<E> {

    void put(final E e) throws InterruptedException;

    void putAll(final Collection<E> c) throws InterruptedException;

    E poll(long timeout, final TimeUnit unit) throws InterruptedException;

    E take() throws InterruptedException;

    E peek();

    int size();

    boolean isEmpty();

    int remainingCapacity();

    void clear();
}
