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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.event;

import com.wildbeeslabs.sensiblemetrics.diffy.common.event.impl.BaseEvent;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.BaseMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Base matcher event implementation
 *
 * @param <T> type of input element to be matched by operation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BaseMatcherEvent<T, S> extends BaseEvent<S> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 6800879300758322485L;

    /**
     * Default {@link BaseMatcher}
     */
    private final BaseMatcher<T, S> matcher;
    /**
     * Default {@link MatcherEventType}
     */
    private final MatcherEventType type;

    /**
     * Default base matcher event constructor by input arguments
     *
     * @param source  - initial input event source {@code S}
     * @param matcher - initial input {@link BaseMatcher}
     * @param type    - initial input {@link MatcherEventType}
     */
    public BaseMatcherEvent(final S source, final BaseMatcher<T, S> matcher, final MatcherEventType type) {
        super(source);
        this.matcher = matcher;
        this.type = type;
    }

    /**
     * Returns binary flag based on current {@link MatcherEventType} {@code MATCH_SUCCESS}
     *
     * @return true - if current {@link MatcherEventType} is {@code MATCH_SUCCESS}, false - otherwise
     */
    public boolean isSuccess() {
        return Objects.equals(this.getType(), MatcherEventType.MATCH_SUCCESS);
    }

    /**
     * Returns binary flag based on current {@link MatcherEventType} {@code MATCH_FAILURE}
     *
     * @return true - if current {@link MatcherEventType} is {@code MATCH_FAILURE}, false - otherwise
     */
    public boolean isFailure() {
        return Objects.equals(this.getType(), MatcherEventType.MATCH_FAILURE);
    }
}
