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
package com.wildbeeslabs.sensiblemetrics.diffy.configuration.enumeration;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Configuration key type {@link Enum}
 */
public enum ConfigurationKeyType {
    READ_ONLY,
    READ_WRITE;

    /**
     * Return binary flag based on current mode {@code READ_ONLY}
     *
     * @return true - if current mode is {@code READ_ONLY}, false - otherwise
     */
    public boolean isReadOnly() {
        return this.equals(READ_ONLY);
    }

    /**
     * Returns {@link ConfigurationKeyType} by input configuration key type value {@link String}
     *
     * @param name - initial input configuration key type value {@link String}
     * @return {@link ConfigurationKeyType}
     */
    @Nullable
    public static ConfigurationKeyType fromName(final String name) {
        return Arrays.stream(values())
            .filter(type -> type.name().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }

    /**
     * Returns binary flag based on input {@link ConfigurationKeyType}es comparison
     *
     * @param p1 - initial input {@link ConfigurationKeyType} to compare with
     * @param p2 - initial input {@link ConfigurationKeyType} to compare by
     * @return true - if {@link ConfigurationKeyType} are equal, false - otherwise
     */
    public static boolean equals(final ConfigurationKeyType p1, final ConfigurationKeyType p2) {
        return Objects.equals(p1, p2);
    }
}
