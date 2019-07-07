package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces;

/**
 * @author jwells
 */
public interface WeakHashLRU<K> {
    /**
     * Adds the given key to the LRU.  It will
     * be placed at the MRU of the LRU.  If this
     * key already exists in the LRU it will
     * be moved to the MRU
     *
     * @param key Must not be null
     */
    public void add(K key);

    /**
     * Tells if the given key is in the LRU
     *
     * @param key The key to search for, may not be null
     * @return true if found, false otherwise
     */
    public boolean contains(K key);

    /**
     * Removes the given key from the LRU, if found
     *
     * @param key The key to remove, may not be null
     * @return true if removed, false otherwise
     */
    public boolean remove(K key);

    /**
     * Releases all keys that match the filter
     *
     * @param filter A non-null filter that can be used
     *               to delete every key that matches the filter
     */
    public void releaseMatching(CacheKeyFilter<K> filter);

    /**
     * Returns the number of elements currently
     * in the clock.  References that have gone
     * away because they were weakly referenced
     * will not be counted in the size
     *
     * @return The number of entries currently
     * in the LRU
     */
    public int size();

    /**
     * Removes the key that was Least
     * Recently Used
     *
     * @return The key that was removed, or
     * null if the list is empty
     */
    public K remove();

    /**
     * Removes all entries from this LRU
     */
    public void clear();

    /**
     * Causes stale references to be cleared from the data
     * structures.  Since this is a weak clock the references
     * can go away at any time, which happens whenever
     * any operation has been performed.  However, it may be
     * the case that no operation will be performed for a while
     * and so this method is provided to have a no-op operation
     * to call in order to clear out any stale references
     */
    public void clearStaleReferences();

}
