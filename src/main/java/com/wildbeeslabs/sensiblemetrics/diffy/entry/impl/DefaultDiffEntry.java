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
package com.wildbeeslabs.sensiblemetrics.diffy.entry.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.iface.DiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.DiffEntryView;
import lombok.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.getRandomString;

/**
 * Default difference entry implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DefaultDiffEntry implements DiffEntry<Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8477472621769483552L;

    /**
     * Default entry id {@link String}
     */
    @JsonView(DiffEntryView.Internal.class)
    @JsonProperty(value = "id", required = true)
    @EqualsAndHashCode.Exclude
    private transient String id;

    /**
     * Default property name {@link String}
     */
    @JsonView(DiffEntryView.External.class)
    @JsonProperty(value = "property", required = true)
    private String propertyName;

    /**
     * Default property value of first argument {@link Object}
     */
    @JsonView(DiffEntryView.External.class)
    @JsonProperty("first")
    private transient Object first;

    /**
     * Default property value of last argument {@link Object}
     */
    @JsonView(DiffEntryView.External.class)
    @JsonProperty("last")
    private transient Object last;

    /**
     * Creates new {@link DefaultDiffEntry}
     *
     * @param propertyName - initial input property name {@link String}
     * @param first        - initial input first element value {@link Object}
     * @param last         - initial input last element value {@link Object}
     * @return {@link DefaultDiffEntry}
     */
    @NonNull
    public static DefaultDiffEntry of(final String propertyName, final Object first, final Object last) {
        return DefaultDiffEntry
            .builder()
            .id(getRandomString())
            .propertyName(propertyName)
            .first(first)
            .last(last)
            .build();
    }
}
