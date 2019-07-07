package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.node;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Used for doubly linked lists with weak keys
 *
 * @param <K> key
 * @param <V> value
 * @author jwells
 */
public class DoubleNode<K, V> {
    private final WeakReference<K> weakKey;
    private final V value;
    private DoubleNode<K, V> previous;
    private DoubleNode<K, V> next;
    private K hardenedKey;

    public DoubleNode(K key, V value, ReferenceQueue<? super K> queue) {
        weakKey = new WeakReference<K>(key, queue);
        this.value = value;
    }

    /**
     * @return the previous
     */
    public DoubleNode<K, V> getPrevious() {
        return previous;
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
        return next;
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
        return value;
    }

    /**
     * @return the hardenedKey
     */
    public K getHardenedKey() {
        return hardenedKey;
    }

    /**
     * @param hardenedKey the hardenedKey to set
     */
    public void setHardenedKey(K hardenedKey) {
        this.hardenedKey = hardenedKey;
    }


}
