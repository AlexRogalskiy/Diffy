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
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.iface.DiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.DiffMatchEntryView;
import lombok.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.getRandomString;

/**
 * Default difference match entry implementation
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
public class DefaultDiffMatchEntry implements DiffMatchEntry<Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5902509374305990063L;

    /**
     * Default entry id {@link String}
     */
    @JsonView(DiffMatchEntryView.Internal.class)
    @JsonProperty(value = "id", required = true)
    @EqualsAndHashCode.Exclude
    private transient String id;

    /**
     * Default match description {@link MatchDescription}
     */
    @JsonView(DiffMatchEntryView.External.class)
    @JsonProperty("description")
    private MatchDescription description;

    /**
     * Default property value to be matched {@link Object}
     */
    @JsonView(DiffMatchEntryView.External.class)
    @JsonProperty("value")
    private transient Object value;

    /**
     * Creates new {@link DefaultDiffMatchEntry}
     *
     * @param value       - initial input argument value {@link Object}
     * @param description - initial input {@link MatchDescription}
     * @return {@link DefaultDiffMatchEntry}
     */
    @NonNull
    public static DefaultDiffMatchEntry of(final Object value, final MatchDescription description) {
        return DefaultDiffMatchEntry
            .builder()
            .id(getRandomString())
            .value(value)
            .description(description)
            .build();
    }
}
