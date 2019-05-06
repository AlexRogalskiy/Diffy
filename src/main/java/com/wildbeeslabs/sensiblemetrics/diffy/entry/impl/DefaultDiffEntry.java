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
package com.wildbeeslabs.sensiblemetrics.diffy.entry.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.iface.DiffEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.DefaultDiffEntryView;
import lombok.*;

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
public class DefaultDiffEntry implements DiffEntry<Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -8477472621769483552L;

    /**
     * Default entry id {@link String}
     */
    @JsonView(DefaultDiffEntryView.Internal.class)
    @JsonProperty("id")
    private String id;

    /**
     * Default property name {@link String}
     */
    @JsonView(DefaultDiffEntryView.External.class)
    @JsonProperty("property")
    private String propertyName;

    /**
     * Default property value of first argument {@link Object}
     */
    @JsonView(DefaultDiffEntryView.External.class)
    @JsonProperty("first")
    private transient Object first;

    /**
     * Default property value of last argument {@link Object}
     */
    @JsonView(DefaultDiffEntryView.External.class)
    @JsonProperty("last")
    private transient Object last;
}
