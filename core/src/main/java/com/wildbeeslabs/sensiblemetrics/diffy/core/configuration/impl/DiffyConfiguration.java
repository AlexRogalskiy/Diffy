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
package com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.core.configuration.iface.Configuration;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ServiceUtils.listOf;

/**
 * Diffy {@link Configuration} implementation
 *
 * @param <T> type of configuration key value
 */
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@SuppressWarnings("unchecked")
public class DiffyConfiguration<T> implements Configuration<T> {

    /**
     * Default {@link TreeMap} collection of {@link DiffyConfigurationKey}s
     */
    private final TreeMap<String, DiffyConfigurationKey<T>> registeredKeys = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Default {@link DiffyConfiguration} instance
     */
    public static final DiffyConfiguration<?> DEFAULT_CONFIGURATION = DiffyConfiguration.of();
//    public static final DiffyConfiguration PERMISSIVE = DiffyConfiguration.builder()
//        .maxContentLen(100 * 1024 * 1024)
//        .maxHeaderCount(-1)
//        .maxHeaderLen(-1)
//        .maxLineLen(-1)
//        .build();
//    public static final DiffyConfiguration STRICT = DiffyConfiguration.builder()
//        .strictParsing(true)
//        .malformedHeaderStartsBody(false)
//        .build();

//    private final boolean strictParsing;
//    private final int maxLineLen;
//    private final int maxHeaderCount;
//    private final int maxHeaderLen;
//    private final long maxContentLen;
//    private final boolean countLineNumbers;
//    private final String headlessParsing;
//    private final boolean malformedHeaderStartsBody;

    /**
     * Default {@link DiffyConfiguration} by input {@link Iterable} collection of {@link DiffyConfigurationKey}s
     *
     * @param keys - initial input {@link Iterable} collection of {@link DiffyConfigurationKey}s
     */
    public DiffyConfiguration(final Iterable<DiffyConfigurationKey<T>> keys) {
        listOf(keys).forEach(this::registerKey);
    }

    /**
     * Returns immutable {@link Map} collection of registered {@link DiffyConfigurationKey}s
     *
     * @return immutable {@link Map} collection of registered {@link DiffyConfigurationKey}s
     * @throws InvalidParameterException - if key name is invalid
     */
    @Override
    public Map<String, DiffyConfigurationKey<T>> getKeys() {
        synchronized (registeredKeys) {
            return Collections.unmodifiableMap((Map<String, DiffyConfigurationKey<T>>) registeredKeys.clone());
        }
    }

    /**
     * Returns {@link DiffyConfigurationKey} by input key name {@link String}
     *
     * @param - initial input key name {@link String} to be fetched
     * @return {@link DiffyConfigurationKey}
     */
    @Override
    public DiffyConfigurationKey<T> getKey(final String keyName) {
        synchronized (this.registeredKeys) {
            return this.registeredKeys.get(keyName);
        }
    }

    /**
     * Updates current key by input {@link DiffyConfigurationKey}
     *
     * @param key - initial input {@link DiffyConfigurationKey} to be updated
     */
    @Override
    public void updateKey(final DiffyConfigurationKey<T> key) {
        synchronized (this.registeredKeys) {
            this.registeredKeys.put(key.getName(), key);
        }
    }

    /**
     * Removes {@link DiffyConfigurationKey} by input key name {@link String}
     *
     * @param keyName - initial input key name {@link String} to be removed
     * @return {@link DiffyConfigurationKey}
     */
    @Override
    public DiffyConfigurationKey<T> removeKey(final String keyName) {
        synchronized (this.registeredKeys) {
            return this.registeredKeys.remove(keyName);
        }
    }

    /**
     * Removes all registered {@link DiffyConfigurationKey}s from current {@link Map} collection
     */
    @Override
    public void clearKeys() {
        synchronized (this.registeredKeys) {
            this.registeredKeys.clear();
        }
    }

    /**
     * Registers {@link DiffyConfigurationKey} by input parameters
     *
     * @param key - initial input {@link DiffyConfigurationKey} to be registered
     */
    @Override
    public void registerKey(final DiffyConfigurationKey<T> key) {
        synchronized (this.registeredKeys) {
            if (this.registeredKeys.containsKey(key.getName())) {
                throw new InvalidParameterException(String.format("ERROR: key with name = {%s} already registered", key.getName()));
            }
            this.registeredKeys.put(key.getName(), key);
        }
    }

    /**
     * Returns new {@link DiffyConfiguration}
     *
     * @return {@link DiffyConfiguration}
     */
    @Factory
    public static <T> DiffyConfiguration<T> of() {
        return new DiffyConfiguration<>();
    }

    /**
     * Returns copy of input {@link DiffyConfiguration}
     *
     * @param configuration - initial input {@link DiffyConfiguration} to be copied
     * @return copy of {@link DiffyConfiguration}
     * @throws NullPointerException if configuration is {@code null}
     */
    @Factory
    public static <T> DiffyConfiguration<T> copy(final DiffyConfiguration<T> configuration) {
        ValidationUtils.notNull(configuration, "Configuration should not be null");
        return new DiffyConfiguration<>(configuration.getKeys().values());
    }
}
