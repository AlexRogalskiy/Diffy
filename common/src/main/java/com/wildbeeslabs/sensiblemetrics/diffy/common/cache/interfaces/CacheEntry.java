package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces;

/**
 * This can be used to remove a specific cache entry from the cache,
 * for better control of the caching release semantics
 *
 * @author jwells
 */
public interface CacheEntry {
    /**
     * Call this method on this entry to remove it from the LRUCache.  If this
     * entry has already been removed this method will do nothing.
     */
    void removeFromCache();
}
