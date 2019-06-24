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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.entry.iface.Entry;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.iface.DiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.entry.impl.DefaultDiffMatchEntry;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.stream.Collectors;

/**
 * Default {@link AbstractDiffMatcher} implementation
 *
 * @param <T> type of input element to be matched by difference operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public class DefaultDiffMatcher<T> extends AbstractDiffMatcher<T, Entry<T, T>> {

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
     * Default difference matcher constructor with input {@link MatcherHandler}
     *
     * @param handler - initial input {@link MatcherHandler}
     */
    public DefaultDiffMatcher(final MatcherHandler<T, Entry<T, T>> handler) {
        super(handler);
    }

    /**
     * Returns {@link Iterable} collection of {@link DiffMatchEntry}s
     *
     * @param value - initial input argument to be matched by
     * @return {@link Iterable} collection of {@link DiffMatchEntry}s
     */
    @Override
    public <S extends Iterable<? extends DiffMatchEntry<?>>> S diffMatch(final T value) {
        return (S) this.getMatchers()
            .stream()
            .filter(m -> m.negate().matches(value))
            .map(m -> DefaultDiffMatchEntry.of(value, m.getDescription()))
            .collect(Collectors.toList());
    }
}
