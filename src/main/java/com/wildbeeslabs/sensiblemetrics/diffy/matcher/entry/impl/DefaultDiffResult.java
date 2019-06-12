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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.iface.DiffEntry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.*;

/**
 * Default {@link Iterable} difference result implementation
 */
@Data
@EqualsAndHashCode
@ToString
public class DefaultDiffResult<T> implements Iterable<DiffEntry<?>> {

    /**
     * <p>
     * The {@code String} returned when the objects have no differences:
     * {@value}
     * </p>
     */
    public static final String OBJECTS_SAME_STRING = "no differs";
    /**
     * <p>
     * The {@code String} returned when the objects have differences:
     * {@value}
     * </p>
     */
    private static final String DIFFERS_STRING = "differs from";

    /**
     * Default {@link List} of {@link DiffEntry}s
     */
    private final List<DiffEntry<?>> diffEntryList;
    /**
     * Default first {@code T} value
     */
    private final T first;
    /**
     * Default last {@code T} value
     */
    private final T last;
    /**
     * Default {@link ToStringStyle}
     */
    private final ToStringStyle style;

    /**
     * <p>
     * Creates a {@link DefaultDiffResult} containing the differences between two
     * objects.
     * </p>
     *
     * @param first the left hand object
     * @param last  the right hand object
     * @param diffs the list of differences, may be empty
     * @param style the style to use for the {@link #toString()} method. May be
     *              {@code null}, in which case
     *              {@link ToStringStyle#DEFAULT_STYLE} is used
     * @throws IllegalArgumentException if {@code first}, {@code last} or {@code diffEntryList} is {@code null}
     */
    public DefaultDiffResult(final T first, final T last, final List<DiffEntry<?>> diffs, final ToStringStyle style) {
        Objects.requireNonNull(first, "First object should not be null");
        Objects.requireNonNull(last, "Last object should not be null");
        Objects.requireNonNull(diffs, "Collection of diff entries should not be null");

        this.diffEntryList = diffs;
        this.first = first;
        this.last = last;
        this.style = Optional.ofNullable(style).orElse(ToStringStyle.DEFAULT_STYLE);
    }

    /**
     * <p>
     * Returns an unmodifiable list of {@code Diff}s. The list may be empty if
     * there were no differences between the objects.
     * </p>
     *
     * @return an unmodifiable list of {@code Diff}s
     */
    public List<DiffEntry<?>> getDiffEntryList() {
        return Collections.unmodifiableList(this.diffEntryList);
    }

    /**
     * <p>
     * Returns the number of differences between the two objects.
     * </p>
     *
     * @return the number of differences
     */
    public int getNumberOfDiffs() {
        return this.diffEntryList.size();
    }

    /**
     * <p>
     * Builds a {@code String} description of the differences contained within
     * this {@code DefaultDiffResult}, using the supplied {@code ToStringStyle}.
     * </p>
     *
     * @param style the {@code ToStringStyle} to use when outputting the objects
     * @return a {@code String} description of the differences.
     */
    public String toString(final ToStringStyle style) {
        if (this.diffEntryList.isEmpty()) {
            return OBJECTS_SAME_STRING;
        }
        final ToStringBuilder lhsBuilder = new ToStringBuilder(this.first, style);
        final ToStringBuilder rhsBuilder = new ToStringBuilder(this.last, style);

        for (final DiffEntry<?> diff : this.diffEntryList) {
            lhsBuilder.append(diff.getPropertyName(), diff.getFirst());
            rhsBuilder.append(diff.getPropertyName(), diff.getLast());
        }
        return String.format("%s %s %s", lhsBuilder.build(), DIFFERS_STRING, rhsBuilder.build());
    }

    /**
     * <p>
     * Returns an iterator over the {@code Diff} objects contained in this list.
     * </p>
     *
     * @return the iterator
     */
    @Override
    public Iterator<DiffEntry<?>> iterator() {
        return this.diffEntryList.iterator();
    }
}
