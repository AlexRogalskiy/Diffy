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
package com.wildbeeslabs.sensiblemetrics.diffy.configuration.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.configuration.impl.DiffyConfigurationKey;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;

import java.util.Map;

/**
 * Default configuration declaration
 *
 * @param <T> type of key
 */
public interface Configuration<T> {

    /**
     * Default empty {@link Configuration} implementation
     */
    Configuration<?> EMPTY_CONFIGURATION = new Configuration<>() {
        @Override
        public Map<String, DiffyConfigurationKey<Object>> getKeys() {
            return null;
        }

        @Override
        public DiffyConfigurationKey<Object> getKey(final String keyName) {
            return null;
        }

        @Override
        public void registerKey(final DiffyConfigurationKey<Object> key) {
        }

        @Override
        public void updateKey(final DiffyConfigurationKey<Object> key) {
        }

        @Override
        public DiffyConfigurationKey<Object> removeKey(final String keyName) {
            return null;
        }

        @Override
        public void clearKeys() {
        }
    };

    /**
     * Returns immutable {@link Map} collection of currently registered {@link DiffyConfigurationKey}s
     *
     * @return immutable copy of {@link Map} collection of currently registered {@link DiffyConfigurationKey}s
     * @throws InvalidParameterException - if key name is invalid
     */
    Map<String, DiffyConfigurationKey<T>> getKeys();

    /**
     * Returns {@link DiffyConfigurationKey} by input key name {@link String}
     *
     * @param - initial input key name {@link String}
     * @return {@link DiffyConfigurationKey}
     */
    DiffyConfigurationKey<T> getKey(final String keyName);

    /**
     * Registers {@link DiffyConfigurationKey} by input parameters
     *
     * @param key - initial input {@link DiffyConfigurationKey}
     */
    void registerKey(final DiffyConfigurationKey<T> key);

    /**
     * Updates current key by input {@link DiffyConfigurationKey}
     *
     * @param key - initial input {@link DiffyConfigurationKey}
     * @return {@link DiffyConfigurationKey}
     */
    void updateKey(final DiffyConfigurationKey<T> key);

    /**
     * Removes {@link DiffyConfigurationKey} by input key name {@link String}
     *
     * @param - initial input key name {@link String}
     * @return {@link DiffyConfigurationKey}
     */
    DiffyConfigurationKey<T> removeKey(final String keyName);

    /**
     * Removes all registered {@link DiffyConfigurationKey}s from current {@link Map} collection
     */
    void clearKeys();
}
