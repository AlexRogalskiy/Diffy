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
package com.wildbeeslabs.sensiblemetrics.diffy.property.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.property.enums.PropertyType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

/**
 * Encapsulates information for a property
 */
public interface PropertyInfo {
    /**
     * Returns the annotation on the property's member for the {@code annotationClass} or {@code null}
     * if none exists.
     *
     * @param <T>             annotation type
     * @param annotationClass to get annotation for
     */
    <T extends Annotation> T getAnnotation(final Class<T> annotationClass);

    /**
     * Returns the generic type represented by the property. For fields this will be the field's
     * generic type. For accessor methods this will be the generic return type. For mutator methods
     * this will be the single parameter's generic type.
     * <ul>
     * <li>For properties of type {@link PropertyType#FIELD} this will be the field's
     * {@link java.lang.reflect.Field#getGenericType() generic type}.
     * <li>For accessors of type {@link PropertyType#METHOD} this will be the method's
     * {@link java.lang.reflect.Method#getGenericReturnType() return type}.
     * <li>For mutators of type {@link PropertyType#METHOD} this will be the single parameter's
     * {@link java.lang.reflect.Method#getGenericParameterTypes() generic type}.
     * <li>For properties of type {@link PropertyType#GENERIC} this will be the same as
     * {@link #getType()}.
     * </ul>
     */
    Type getGenericType();

    /**
     * Returns the initial type in the member declaring class' type hierarchy fromName which this property
     * info was initiated. This is useful in resolving generic type information for the property where
     * the type parameter may have been declared on the initial type.
     */
    Class<?> getInitialType();

    /**
     * Returns the encapsulated member or {@code null} if none exists.
     */
    Member getMember();

    /**
     * Returns the property name.
     */
    String getName();

    /**
     * Returns the member type.
     */
    PropertyType getPropertyType();

    /**
     * Returns the type represented by the property. For fields this will be the field type. For
     * accessor methods this will be the return type. For mutator methods this will be the single
     * parameter type.
     */
    Class<?> getType();
}
