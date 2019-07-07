package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces;

/**
 * This is used by the cache to remove a series of entries that
 * match this filter
 *
 * @param <K> The key type for this filter
 * @author jwells
 */
public interface CacheKeyFilter<K> {

    /**
     * Returns true if the key matches the filter
     *
     * @param key The key from the cache to filter
     * @return true if the key matches, false otherwise
     */
    boolean matches(final K key);
}
