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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.BiMatchOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enums.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.event.BiMatcherEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.handler.iface.MatcherHandler;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.BiMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.sort.SortManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static com.wildbeeslabs.sensiblemetrics.diffy.utils.ComparatorUtils.compare;

/**
 * Abstract {@link BiMatcher} implementation
 *
 * @param <T> type of input element to be matched by operation
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class AbstractBiMatcher<T> extends AbstractBaseMatcher<T> implements BiMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -923562195703553471L;

    /**
     * Default {@link Comparator}
     */
    private final Comparator<? super T> comparator;

    /**
     * Default abstract matcher constructor
     */
    public AbstractBiMatcher() {
        this(null, null);
    }

    /**
     * Default abstract matcher constructor with input {@link Comparator}
     *
     * @param comparator - initial input {@link Comparator}
     */
    public AbstractBiMatcher(final Comparator<? super T> comparator) {
        this(null, comparator);
    }

    /**
     * Default abstract matcher constructor with input {@link MatcherHandler} and {@link Comparator}
     *
     * @param handler    - initial input {@link MatcherHandler}
     * @param comparator - initial input {@link Comparator}
     */
    public AbstractBiMatcher(final MatcherHandler<T> handler, final Comparator<? super T> comparator) {
        super(handler);
        this.comparator = Optional.ofNullable(comparator).orElseGet(() -> Comparator.comparing(Object::toString));
    }

    /**
     * Compares the two provided objects whether they are equal.
     *
     * @param value1 - initial input first value {@code T}
     * @param value2 - initial input last value {@code T}
     * @return true - if objects {@code T} are equal, false - otherwise
     */
    @Override
    public boolean matches(final T value1, final T value2) {
        boolean result = false;
        try {
            this.handleEvent(BiMatcherEvent.of(this, value1, value2, MatcherEventType.MATCH_START));
            result = Objects.equals(compare(value1, value2, this.getComparator()), SortManager.SortDirection.EQ);
            this.handleEvent(BiMatcherEvent.of(this, value1, value2, result));
        } catch (RuntimeException e) {
            BiMatchOperationException.throwIncorrectMatch(value1, value2, e);
        }
        this.handleEvent(BiMatcherEvent.of(this, value1, value2, MatcherEventType.MATCH_COMPLETE));
        return result;
    }
}
