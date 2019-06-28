/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.enumeration.ClockWithOffset;
import com.wildbeeslabs.sensiblemetrics.diffy.common.cache.interfaces.Clock;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A semi-persistent mapping from keys to values. Values are automatically loaded
 * by the cache, and are stored in the cache until evicted.
 *
 * @param <K> The type of keys maintained
 * @param <V> The type of values maintained
 */
@EqualsAndHashCode
@ToString
public class CacheService<K, V> {
    /**
     * Default {@link ScheduledExecutorService} implementation
     */
    private static final ScheduledExecutorService SERVICE = Executors.newSingleThreadScheduledExecutor();

    /**
     * Default {@link ConcurrentHashMap} instance
     */
    private final ConcurrentHashMap<K, CacheEntry<V>> map;
    /**
     * Default expire after (in ms)
     */
    private final long expireAfterMs;
    /**
     * Default {@link Function} operator
     */
    private final Function<K, CacheEntry<V>> entryGetter;
    /**
     * Default {@link Clock} instance
     */
    private final Clock clock;

    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static final class CacheEntry<V> {
        /**
         * Default cache {@code V} value
         */
        private final V value;
        /**
         * Default access time
         */
        private volatile long accessTime;
        /**
         * Default {@link Clock} instance
         */
        private final Clock clock;

        private V getValue() {
            this.accessTime = this.clock.now();
            return this.value;
        }
    }

    /**
     * Create a new CacheService that will expire entries after a given number of milliseconds
     * computing the values as needed using the given getter.
     *
     * @param expireAfterMs Number of milliseconds after which entries will be evicted
     * @param getter        BinaryFunction that will be used to compute the values
     */
    public CacheService(final long expireAfterMs, final Function<K, V> getter) {
        this(expireAfterMs, getter, TimeUnit.MINUTES.toMillis(1), ClockWithOffset.INSTANCE);
    }

    /**
     * For unit tests.
     * Create a new CacheService that will expire entries after a given number of milliseconds
     * computing the values as needed using the given getter.
     *
     * @param expireAfterMs    Number of milliseconds after which entries will be evicted
     * @param getter           BinaryFunction that will be used to compute the values
     * @param expirationFreqMs Frequency at which to schedule the job that evicts entries
     *                         from the cache.
     */
    public CacheService(final long expireAfterMs, final Function<K, V> getter, final long expirationFreqMs, final Clock clock) {
        ValidationUtils.isTrue(expireAfterMs > 0, "ERROR: expireAfterMs must be positive");
        ValidationUtils.isTrue(expirationFreqMs > 0, "ERROR: expirationFreqMs must be positive");

        this.map = new ConcurrentHashMap<>();
        this.expireAfterMs = expireAfterMs;
        this.entryGetter = this.toEntry(getter);
        this.clock = clock;

        final Runnable expirationJob = () -> {
            long tooOld = this.clock.now() - this.expireAfterMs;
            this.map.entrySet().stream().filter(entry -> entry.getValue().accessTime < tooOld).forEach(entry -> this.map.remove(entry.getKey(), entry.getValue()));
        };
        SERVICE.scheduleWithFixedDelay(expirationJob, 1, expirationFreqMs, TimeUnit.MILLISECONDS);
    }

    /**
     * Returns {@link Function} operator by input parameters
     *
     * @param function - initial input {@link Function} operator
     * @return {@link Function}
     */
    private Function<K, CacheEntry<V>> toEntry(final Function<K, V> function) {
        return key -> new CacheEntry<>(function.apply(key), 0L, clock);
    }

    /**
     * This method should be used instead of the
     * {@link ConcurrentHashMap#computeIfAbsent(Object, Function)} call to minimize
     * thread contention. This method does not require locking for the common case
     * where the key exists, but potentially performs additional computation when
     * absent.
     */
    private CacheEntry<V> computeIfAbsent(final K key) {
        CacheEntry<V> v = this.map.get(key);
        if (Objects.isNull(v)) {
            final CacheEntry<V> tmp = this.entryGetter.apply(key);
            v = Optional.ofNullable(this.map.putIfAbsent(key, tmp)).orElse(tmp);
        }
        return v;
    }

    /**
     * Get the (possibly cached) value for a given key.
     */
    public V get(final K key) {
        final CacheEntry<V> entry = this.computeIfAbsent(key);
        return entry.getValue();
    }

    /**
     * Get the list of all values that are members of this cache. Does not
     * affect the access time used for eviction.
     */
    public List<V> values() {
        final Collection<CacheEntry<V>> values = this.map.values();
        final List<V> res = values.stream().map(e -> e.value).collect(Collectors.toList());
        return Collections.unmodifiableList(res);
    }

    /**
     * Return the number of entries in the cache.
     */
    public int size() {
        return this.map.size();
    }
}
