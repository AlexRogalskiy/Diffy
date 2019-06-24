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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.constants;

import com.google.common.collect.Iterables;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;

/**
 * Collection matcher type {@link Enum}
 */
@Getter
@RequiredArgsConstructor
public enum CollectionMatcherType {
    /**
     * org.apache.commons.collections.CollectionUtils
     */
    IS_EMPTY(CollectionUtils::isEmpty),
    IS_FULL(CollectionUtils::isFull),
    IS_NOT_EMPTY(CollectionUtils::isNotEmpty),
    IS_SIZE_EMPTY(CollectionUtils::sizeIsEmpty),
    IS_SIZEEMPTY(Iterables::isEmpty);

    /**
     * Collection {@link Matcher} operator
     */
    private final Matcher<Collection<?>> matcher;
}
