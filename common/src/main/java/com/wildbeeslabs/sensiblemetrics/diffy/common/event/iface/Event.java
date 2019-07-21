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
package com.wildbeeslabs.sensiblemetrics.diffy.common.event.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration.EventType;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Event interface declaration
 *
 * @param <T> type of event item
 */
public interface Event<T> extends Serializable {

    /**
     * Default empty {@link Event} implementation
     */
    Event<?> EMPTY_EVENT = new Event<>() {
        @Override
        public String getEventId() {
            return UUID.randomUUID().toString();
        }

        @Override
        public String getName() {
            return EMPTY;
        }

        @Override
        public String getDescription() {
            return EMPTY;
        }

        @Override
        public EventType getType() {
            return EventType.EMPTY_EVENT;
        }

        @Override
        public Instant getTimeStamp() {
            return Instant.now();
        }
    };

    /**
     * Returns the identifier of the serialized event.
     *
     * @return the identifier of the serialized event
     */
    String getEventId();

    /**
     * Name of event, should be unique.
     * Is logged by JDK logger.
     *
     * @return event name.
     */
    default String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * Name of event, should be unique.
     * Is logged by JDK logger.
     *
     * @return event name.
     */
    default String getDescription() {
        return this.getClass().getCanonicalName();
    }

    /**
     * The name of the event (case-insensitive). The name must be an XML name.
     */
    EventType getType();

    /**
     * Used to indicate the <code>EventTarget</code> to which the event was
     * originally dispatched.
     */
//    EventListenerAdapter<T> getTarget();

    /**
     * Returns the timestamp at which the event was first created.
     *
     * @return the timestamp at which the event was first created
     */
    Instant getTimeStamp();
}
