package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The {@link Attributes} class stores name value pairs.
 * <p>
 * Problem: The HashMap for storing the name value pairs has a very high
 * memory footprint, replace it.
 */
public class Attributes<K, V> {
    private final Map<K, V> mNameValueMap = new HashMap<>();

    /**
     * Retrieves the value for the given key or null if attribute it not set.
     *
     * @param key
     * @return the value
     */
    public V getValue(final K key) {
        return this.mNameValueMap.get(key);
    }

    /**
     * Sets a key/value pair.
     *
     * @param key
     * @param value
     */
    public void setValue(final K key, final V value) {
        ValidationUtils.notNull(key, "key must not be null");
        ValidationUtils.notNull(value, "value must not be null");

        this.mNameValueMap.put(key, value);
    }

    /**
     * Iterates over the keys.
     *
     * @return key-{@link Iterator}
     */
    public Iterator<K> iterator() {
        return this.mNameValueMap.keySet().iterator();
    }
}
