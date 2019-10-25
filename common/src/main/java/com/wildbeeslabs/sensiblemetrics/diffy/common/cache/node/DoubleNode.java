package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.node;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Used for doubly linked lists with weak keys
 *
 * @param <K> key
 * @param <V> value
 */
public class DoubleNode<K, V> {
    private final WeakReference<K> weakKey;
    private final V value;
    private DoubleNode<K, V> previous;
    private DoubleNode<K, V> next;
    private K hardenedKey;

    public DoubleNode(final K key, final V value, final ReferenceQueue<? super K> queue) {
        weakKey = new WeakReference<>(key, queue);
        this.value = value;
    }

    /**
     * @return the previous
     */
    public DoubleNode<K, V> getPrevious() {
        return this.previous;
    }

    /**
     * @param previous the previous to set
     */
    public void setPrevious(DoubleNode<K, V> previous) {
        this.previous = previous;
    }

    /**
     * @return the next
     */
    public DoubleNode<K, V> getNext() {
        return this.next;
    }

    /**
     * @param next the next to set
     */
    public void setNext(DoubleNode<K, V> next) {
        this.next = next;
    }

    /**
     * @return the weakKey
     */
    public WeakReference<K> getWeakKey() {
        return weakKey;
    }

    /**
     * @return the value
     */
    public V getValue() {
        return this.value;
    }

    /**
     * @return the hardenedKey
     */
    public K getHardenedKey() {
        return this.hardenedKey;
    }

    /**
     * @param hardenedKey the hardenedKey to set
     */
    public void setHardenedKey(K hardenedKey) {
        this.hardenedKey = hardenedKey;
    }
}
