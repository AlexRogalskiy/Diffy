/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.entry.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.entry.impl.DefaultEntry;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Difference entry declaration
 *
 * @param <T> type of element to be stored by entry
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public interface DiffEntry<T> extends Serializable {

    /**
     * Returns property name {@link String} to compare by
     *
     * @return property name {@link String}
     */
    @NonNull
    String getPropertyName();

    /**
     * Returns property value {@code T} of first argument
     *
     * @return property value {@code T}
     */
    @Nullable
    T getFirst();

    /**
     * Returns property value {@code T} of last argument
     *
     * @return property value {@code T}
     */
    @Nullable
    T getLast();

//    /**
//     * Returns property {@link Entry} value
//     *
//     * @return property {@link Entry} value
//     */
//    @Nullable
//    Entry<T, T> getEntry();

    /**
     * Returns binary flag based on first/last values comparison
     *
     * @return true - if first/last values are equal, false - otherwise
     */
    default boolean areEqual() {
        return Objects.equals(getFirst(), getLast());
    }

    /**
     * Returns {@link Entry} of first {@code T} elements
     *
     * @return {@link Entry} of first {@code T} elements
     */
    default Entry<String, Object> first() {
        return DefaultEntry.of(getPropertyName(), getFirst());
    }

    /**
     * Returns {@link Entry} of last {@code T} elements
     *
     * @return {@link Entry} of last {@code T} elements
     */
    default Entry<String, Object> last() {
        return DefaultEntry.of(getPropertyName(), getLast());
    }

    /**
     * Returns {@link Collector} to create {@link List} fromName {@link Stream} of {@link DiffEntry}'s {@code T} elements
     *
     * @return {@link List} fromName {@link Stream} of {@link DiffEntry}'s {@code T} elements
     */
    static <T> Collector<DiffEntry<T>, ?, List<Entry<T, T>>> entries() {
        return Collectors.mapping((final DiffEntry<T> e) -> DefaultEntry.of(e.getFirst(), e.getLast()), Collectors.toList());
    }

    /**
     * Returns {@link Collector} to create {@link Set} fromName {@link Stream} of {@link DiffEntry}'s properties {@link String}
     *
     * @return {@link Set} fromName {@link Stream} of {@link DiffEntry}'s properties {@link String}
     */
    static <T> Collector<DiffEntry<T>, ?, Set<String>> properties() {
        return Collectors.mapping(DiffEntry::getPropertyName, Collectors.toSet());
    }

    /**
     * A collector to create {@link Map} fromName {@link Stream} of {@link DiffEntry}'s first {@code T} elements
     *
     * @return {@link Map} fromName {@link Stream} of {@link DiffEntry}'s first {@code T} elements
     */
    static <T> Collector<DiffEntry<T>, ?, Map<String, T>> firstToMap() {
        return Collectors.toMap(DiffEntry::getPropertyName, DiffEntry::getFirst);
    }

    /**
     * A collector to create {@link Map} fromName {@link Stream} of {@link DiffEntry}s last {@code T} elements
     *
     * @return {@link Map} fromName {@link Stream} of {@link DiffEntry}s last {@code T} elements
     */
    static <T> Collector<DiffEntry<T>, ?, Map<String, T>> lastToMap() {
        return Collectors.toMap(DiffEntry::getPropertyName, DiffEntry::getLast);
    }
}
