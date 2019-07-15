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
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.exception.UnsupportedEventTypeException.throwUnsupportedEventType;
import static java.util.Arrays.asList;

/**
 * Default event type {@link Enum}
 */
public enum EventType {
    MATCHER_EVENT,
    BINARY_MATCHER_EVENT,
    COMPARATOR_EVENT,
    VALIDATOR_EVENT,
    CONVERTER_EVENT,
    PROCESSOR_EVENT,
    @JsonEnumDefaultValue
    EMPTY_EVENT;

    /**
     * Returns {@link EventType} by input event type name {@link String}
     *
     * @param name - initial input event type name {@link String}
     * @return {@link EventType}
     */
    @JsonCreator
    @NonNull
    public static EventType fromName(final String name) {
        return Arrays.stream(values())
            .filter(type -> type.name().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> throwUnsupportedEventType(name));
    }

    /**
     * Returns {@link List} of all {@link EventType}s
     *
     * @return {@link List} of all {@link EventType}s
     */
    public static List<EventType> valuesList() {
        return Collections.unmodifiableList(asList(EventType.values()));
    }
}
