package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.impl;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Simple Map implementation usable for caches where contents do not
 * expire, but where size needs to remain bounded.
 * <p>
 * Note: we probably should use weak references, or something similar
 * to limit maximum memory usage. This could be implemented in many
 * ways, perhaps by using two areas: first, smaller one, with strong
 * refs, and secondary bigger one that uses soft references.
 */

public final class SimpleCache<K, V> {
    final LimitMap<K, V> mItems;

    final int mMaxSize;

    public SimpleCache(int maxSize) {
        mItems = new LimitMap<K, V>(maxSize);
        mMaxSize = maxSize;
    }

    public V find(K key) {
        return mItems.get(key);
    }

    public void add(K key, V value) {
        mItems.put(key, value);
    }

    /*
    /////////////////////////////////////////////
    // Helper classes
    /////////////////////////////////////////////
     */

    @SuppressWarnings("serial")
    static final class LimitMap<K, V> extends LinkedHashMap<K, V> {
        final int mMaxSize;

        public LimitMap(int size) {
            super(size, 0.8f, true);
            // Let's not allow silly low values...
            mMaxSize = size;
        }

        @Override
        public boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return (size() >= mMaxSize);
        }
    }
}
