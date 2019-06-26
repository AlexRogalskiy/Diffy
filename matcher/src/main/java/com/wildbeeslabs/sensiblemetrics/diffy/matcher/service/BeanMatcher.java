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

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>Predicate implementation that applies the given <code>Predicate</code>
 * to the result of calling the given property getter.
 * </p>
 */
@Data
@EqualsAndHashCode
@ToString
@SuppressWarnings("unchecked")
public class BeanMatcher<T> implements Matcher<T> {
    /**
     * Name of the property whose value will be predicated
     */
    private final String propertyName;
    /**
     * <code>Predicate</code> to be applied to the property value
     */
    private final Matcher<T> matcher;

    /**
     * Constructs a <code>BeanMatcher</code> that applies the given
     * <code>Predicate</code> to the named property value.
     *
     * @param propertyName the name of the property whose value is to be predicated,
     *                     not null
     * @param matcher      the <code>Predicate</code> to be applied,
     *                     not null
     */
    public BeanMatcher(final String propertyName, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        this.propertyName = propertyName;
        this.matcher = matcher;
    }

    /**
     * Evaluates the given object by applying the {@code matches()}
     * to a property value named by {@link #getPropertyName()}.
     *
     * @param value The object being evaluated
     * @return the result of the predicate evaluation
     * @throws IllegalArgumentException when the property cannot be evaluated
     */
    @Override
    public boolean matches(final T value) {
        try {
            final T propValue = (T) PropertyUtils.getProperty(value, this.propertyName);
            return this.matcher.matches(propValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(String.format("ERROR: problem during evaluation of property = {%s}", this.propertyName), e);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("ERROR: unable to access the property provided = {%s}", this.propertyName), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("ERROR: exception occurred in property's getter = {%s}", this.propertyName), e);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("ERROR: property not found = {%s}", this.propertyName), e);
        }
    }
}
