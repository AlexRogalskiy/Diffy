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
package com.wildbeeslabs.sensiblemetrics.diffy.configuration.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.configuration.enums.ConfigurationKeyType;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Default configuration key implementation
 *
 * @param <T> type of configuration key value
 */
@Getter
@EqualsAndHashCode
@ToString
public class DiffyConfigurationKey<T> {

    /**
     * Default configuration key name pattern
     */
    private static final Pattern DEFAULT_KEY_NAME_PATTERN = Pattern.compile("[-_a-zA-Z][-.\\w]*(?<![-.])");

    /**
     * Default key name {@link String}
     */
    private final String name;
    /**
     * Default key value {@code T}
     */
    private final T value;
    /**
     * Default key description {@link String}
     */
    private final String description;
    /**
     * Default key type {@link ConfigurationKeyType}
     */
    private final ConfigurationKeyType type;
    /**
     * Default key hidden flag
     */
    private final boolean hidden;

    /**
     * Default configuration key constructor by input parameters
     *
     * @param name        - initial input key name {@link String}
     * @param value       - initial input key value {@link String}
     * @param description - initial input key description {@link String}
     */
    public DiffyConfigurationKey(final String name, final T value, final String description) {
        this(name, value, description, ConfigurationKeyType.READ_ONLY, false);
    }

    /**
     * Default configuration key constructor by input parameters
     *
     * @param name        - initial input key name {@link String}
     * @param value       - initial input key value {@link String}
     * @param description - initial input key description {@link String}
     * @param type        - initial input key type {@link ConfigurationKeyType}
     * @param hidden      - initial input key hidden flag
     */
    public DiffyConfigurationKey(final String name, final T value, final String description, final ConfigurationKeyType type, final boolean hidden) {
        this.name = this.checkName(name);
        this.value = value;
        this.type = type;
        this.description = description;
        this.hidden = hidden;
    }

    /**
     * Returns key name {@link String} based on input key name validation
     *
     * @param keyName - initial input key name {@link String}
     * @return key name {@link String}
     * @throws InvalidParameterException - if key name is invalid
     */
    private String checkName(final String keyName) {
        Objects.requireNonNull(keyName, "Key should not be null");
        if (!DEFAULT_KEY_NAME_PATTERN.matcher(keyName).matches()) {
            throw new InvalidParameterException(String.format("ERROR: invalid key name = {%s}", keyName));
        }
        return keyName;
    }

    /**
     * Returns {@link DiffyConfigurationKey} by input parameters
     *
     * @param <T>         type of configuration key value
     * @param name        - initial input key name {@link String}
     * @param value       - initial input key value {@link String}
     * @param description - initial input key description {@link String}
     * @return {@link DiffyConfigurationKey}
     */
    @NotNull
    @Contract("_, _ -> new")
    public static <T> DiffyConfigurationKey<T> of(final String name, final T value, final String description) {
        return new DiffyConfigurationKey<>(name, value, description);
    }

    /**
     * Returns {@link DiffyConfigurationKey} by input parameters
     *
     * @param <T>         type of configuration key value
     * @param name        - initial input key name {@link String}
     * @param value       - initial input key value {@link String}
     * @param description - initial input key description {@link String}
     * @param type        - initial input key type {@link ConfigurationKeyType}
     * @param hidden      - initial input key hidden flag
     * @return {@link DiffyConfigurationKey}
     */
    @NotNull
    @Contract("_, _, _, _ -> new")
    public static <T> DiffyConfigurationKey<T> of(final String name, final T value, final String description, final ConfigurationKeyType type, final boolean hidden) {
        return new DiffyConfigurationKey<>(name, value, description, type, hidden);
    }
}
