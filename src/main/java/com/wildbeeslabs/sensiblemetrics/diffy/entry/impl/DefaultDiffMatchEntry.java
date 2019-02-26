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

import com.fasterxml.jackson.annotation.JsonView;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.DiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.description.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.entry.view.EntryView;
import lombok.*;

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
//@JsonRootName(value = "diffMatchEntry")
public class DefaultDiffMatchEntry implements DiffMatchEntry<Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 5902509374305990063L;

    /**
     * Default entry id
     */
    @JsonView(EntryView.Internal.class)
    private String id;

    /**
     * Default match description {@link MatchDescription}
     */
    @JsonView(EntryView.External.class)
    private MatchDescription description;

    /**
     * Default property value to be matched {@link Object}
     */
    @JsonView(EntryView.External.class)
    private Object value;
}
