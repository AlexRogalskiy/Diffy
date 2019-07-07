package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces;

/**
 * Represents a single hybrid cache entry.
 * The entry can avoid being cached, see {@link #dropMe()} for details.
 *
 * @author Jakub Podlesak (jakub.podlesak at oracle.com)
 */
public interface HybridCacheEntry<V> extends CacheEntry {

    /**
     * Getter for this cache entry internal value.
     *
     * @return Internal value.
     */
    V getValue();

    /**
     * Tell the cache if this entry should be dropped
     * as opposed to being kept in the cache.
     *
     * @return true if the entry should not be cached.
     */
    boolean dropMe();
}
