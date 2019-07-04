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
package com.wildbeeslabs.sensiblemetrics.diffy.common.event.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default base {@link Event} implementation
 *
 * @param <T> type of event source
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class BaseEvent<T> implements Event<T> {

    /**
     * Default event {@code T} source
     */
    private transient T source;
    /**
     * Default event {@link Throwable} cause
     */
    private final Throwable cause;
    /**
     * Default event time registration
     */
    private final long timestamp;

    /**
     * Default base event constructor by input arguments
     *
     * @param source - initial input event source {@code T}
     */
    public BaseEvent(final T source) {
        this(source, null);
    }

    /**
     * Default base event constructor by input arguments
     *
     * @param source - initial input event source {@code T}
     * @param cause  - initial input error source {@link Throwable}
     */
    public BaseEvent(final T source, final Throwable cause) {
        ValidationUtils.notNull(source, "Event source should not be null");
        this.source = source;
        this.cause = cause;
        this.timestamp = System.currentTimeMillis();
    }
}
