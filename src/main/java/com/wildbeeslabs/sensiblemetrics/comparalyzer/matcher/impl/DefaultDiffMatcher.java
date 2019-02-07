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
package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.DiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.description.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.entry.impl.DefaultDiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.DiffMatcher;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

/**
 * Default difference matcher implementation by input object instance
 *
 * @param <T>
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefaultDiffMatcher<T> extends AbstractMatcher<T> implements DiffMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -5261047750917117837L;

    /**
     * Default difference matcher constructor
     */
    public DefaultDiffMatcher() {
        super(null);
    }

    /**
     * Default difference matcher constructor with input iterable collection of matchers {@link Iterable}
     *
     * @param matchers - initial input iterable collection of matchers {@link Iterable}
     */
    public DefaultDiffMatcher(final Iterable<Matcher<? super T>> matchers) {
        super(matchers);
    }

    /**
     * Returns iterable collection of difference match entries {@link Iterable}
     *
     * @param value - initial input argument to be matched by
     * @return iterable collection of difference match entries {@link Iterable}
     */
    @Override
    public <S extends Iterable<? extends DiffMatchEntry<?>>> S diffMatches(T value) {
        super.matches(value);
        return (S) getFailedMatchers().stream().map(matcher -> createDiffMatchEntry(value, matcher.getDescription())).collect(Collectors.toList());
    }

    /**
     * Creates new default difference match entry {@link DefaultDiffMatchEntry}
     *
     * @param value       - initial input argument value {@link Object}
     * @param description - initial input match description {@link MatchDescription}
     * @return default difference match entry {@link DefaultDiffMatchEntry}
     */
    protected DefaultDiffMatchEntry createDiffMatchEntry(final Object value, final MatchDescription description) {
        return DefaultDiffMatchEntry
                .builder()
                .value(value)
                .description(description)
                .build();
    }
}
