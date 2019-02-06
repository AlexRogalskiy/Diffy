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

import com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.comparalyzer.utils.ReflectionUtils.getMatchableClass;

/**
 * Custom instance matcher implementation by object instance {@link Object}
 *
 * @author Alexander Rogalskiy
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InstanceMatcher extends AbstractMatcher<Object> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 296097385512552875L;

    /**
     * Default matchable class instance {@link Class}
     */
    private final Class<?> matchableClazz;

    /**
     * Default instance matcher constructor with class instance {@link Class}
     *
     * @param clazz The predicate evaluates to true for instances of this class
     *              or one of its subclasses.
     */
    public InstanceMatcher(final Class<?> clazz) {
        this.matchableClazz = getMatchableClass(clazz);
    }

    @Override
    public boolean matches(final Object value) {
        return (!Objects.isNull(value) && getMatchableClazz().isInstance(value));
    }

    @SuppressWarnings("unchecked")
    public static <T> Matcher<T> getMatcher(final Class<? extends T> type) {
        return (Matcher<T>) new InstanceMatcher(type);
    }
}
