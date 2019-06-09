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
package com.wildbeeslabs.sensiblemetrics.diffy.common.entry.impl.entry;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.wildbeeslabs.sensiblemetrics.diffy.annotation.Factory;
import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.DiffEntryView;
import lombok.*;

import java.util.Optional;

/**
 * Default {@link Entry} implementation
 *
 * @param <K> type of entry first value
 * @param <V> type of entry last value
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultEntry<K, V> implements Entry<K, V> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -921490395669655850L;

    /**
     * Default entry first value {@code K}
     */
    @JsonView(DiffEntryView.External.class)
    @JsonProperty("first")
    private K first;
    /**
     * Default entry last value {@code V}
     */
    @JsonView(DiffEntryView.External.class)
    @JsonProperty("last")
    private V last;

    /**
     * Returns {@link Entry} by input parameters
     *
     * @param <K>   type of entry first value
     * @param <V>   type of entry last value
     * @param first - initial input first value {@code K}
     * @param last  - initial input last value {@code V}
     * @return {@link Entry}
     */
    @Factory
    public static <K, V> Entry<K, V> of(final K first, final V last) {
        return new DefaultEntry<>(first, last);
    }

    /**
     * Returns {@link Entry} if both {@link Optional} instances have values or {@link Optional#empty()} if one or both
     * are missing.
     *
     * @param <K>   type of entry key
     * @param <V>   type of entry value
     * @param first - initial input first value {@link Optional}
     * @param last  - initial input last value {@link Optional}
     * @return {@link Optional} of {@link Entry}
     */
    @NonNull
    public static <K, V> Optional<Entry<K, V>> with(final Optional<K> first, final Optional<V> last) {
        return first.flatMap(f -> last.map(l -> DefaultEntry.of(f, l)));
    }
}
