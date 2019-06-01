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
package com.wildbeeslabs.sensiblemetrics.diffy.property.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.exception.IllegalAccessException;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration.PropertyType;
import com.wildbeeslabs.sensiblemetrics.diffy.property.iface.PropertyInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.StringUtils.formatMessage;

/**
 * Abstract {@link PropertyInfo} implementation
 */
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractPropertyInfo<M extends Member> implements PropertyInfo {

    /**
     * Default initial {@link Class} type
     */
    protected final Class<?> initialType;
    /**
     * Default {@link Member} member type
     */
    protected final M member;
    /**
     * Default {@link Class} type
     */
    protected final Class<?> type;
    /**
     * Default property name
     */
    protected final String name;
    /**
     * Default {@link PropertyType} instance
     */
    protected final PropertyType propertyType;

    /**
     * Default {@link AbstractPropertyInfo} implementation
     */
    public static abstract class AbstractMethodInfo extends AbstractPropertyInfo<Method> {

        /**
         * Private abstract method info constructor by input arguments
         *
         * @param initialType - initial input {@link Class} instance
         * @param method      - - initial input {@link Method} instance
         * @param name        - initial input method name value
         */
        private AbstractMethodInfo(final Class<?> initialType, final Method method, final String name) {
            super(initialType, method, PropertyType.METHOD, name);
            method.setAccessible(true);
        }

        /**
         * Returns {@code T} annotation by input annotation {@link Class}
         *
         * @param <T>
         * @param annotationClass - initial input annotation {@link Class}
         * @return {@code T} annotation
         */
        @Override
        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            return this.getMember().getAnnotation(annotationClass);
        }
    }

    /**
     * Default {@link AbstractPropertyInfo} implementation
     */
    public static class FieldPropertyInfo extends AbstractPropertyInfo<Field> {

        /**
         * Default field property info constructor
         *
         * @param initialType - initial input property class {@link Class}
         * @param field       - initial input property type {@link Field}
         * @param name        - initial property name {@link String}
         */
        public FieldPropertyInfo(final Class<?> initialType, final Field field, final String name) {
            super(initialType, field, PropertyType.FIELD, name);
            field.setAccessible(true);
        }

        /**
         * Returns {@code T} annotation by input annotation {@link Class}
         *
         * @param <T>
         * @param annotationClass - initial input annotation {@link Class}
         * @return {@code T} annotation
         */
        @Override
        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            return this.getMember().getAnnotation(annotationClass);
        }

        /**
         * Returns {@link Field} generic parameter {@link Type}
         *
         * @return {@link Field} generic parameter {@link Type}
         */
        @Override
        public Type getGenericType() {
            return this.getMember().getGenericType();
        }

        /**
         * Returns {@link Field} value on target {@link Object}
         *
         * @param subject - initial input target to get field from {@link Object}
         * @return updated {@link Object}
         */
        public Object getValue(final Object subject) {
            try {
                return this.getMember().get(subject);
            } catch (Exception e) {
                IllegalAccessException.throwInvalidAccess(formatMessage("ERROR: cannot get member: {%s} of type: {%s}", subject, this.member), e);
            }
            return null;
        }

        /**
         * Updates {@link Field} instance on target {@link Object} by new value {@link Object}
         *
         * @param subject - initial input target {@link Object} to update
         * @param value   - initial input value {@link Object} to set
         */
        public void setValue(final Object subject, final Object value) {
            try {
                this.getMember().set(subject, value);
            } catch (Exception e) {
                IllegalAccessException.throwInvalidAccess(formatMessage("ERROR: cannot set value: {%s} for member: {%s} of type: {%s}", value, subject, this.member), e);
            }
        }
    }

    /**
     * Default {@link AbstractMethodInfo} implementation
     */
    public static class MethodGetterAccessor extends AbstractMethodInfo {

        /**
         * Default method getter accessor constructor
         *
         * @param initialType - initial input property class {@link Class}
         * @param method      - initial input method type {@link Method}
         * @param name        - initial property name {@link String}
         */
        public MethodGetterAccessor(final Class<?> initialType, final Method method, final String name) {
            super(initialType, method, name);
        }

        /**
         * Returns {@link Method} generic parameter {@link Type}
         *
         * @return {@link Method} generic parameter {@link Type}
         */
        @Override
        public Type getGenericType() {
            return this.getMember().getGenericReturnType();
        }

        /**
         * Returns result set by {@link Method} on target {@link Object}
         *
         * @param subject - initial input target {@link Object} to get result set from
         * @return method result set {@link Object}
         */
        public Object getValue(final Object subject) {
            try {
                return this.getMember().invoke(subject);
            } catch (Exception e) {
                IllegalAccessException.throwInvalidAccess(formatMessage("ERROR: cannot get member: {%s} of type: {%s}", subject, this.member), e);
            }
            return null;
        }
    }

    /**
     * Default {@link AbstractMethodInfo} implementation
     */
    public static class MethodSetterAccessor extends AbstractMethodInfo {

        /**
         * Default method setter accessor constructor
         *
         * @param initialType - initial input property class {@link Class}
         * @param method      - initial input method type {@link Method}
         * @param name        - initial property name {@link String}
         */
        public MethodSetterAccessor(final Class<?> initialType, final Method method, final String name) {
            super(initialType, method, name);
        }

        /**
         * Returns {@link Method} generic parameter {@link Type}
         *
         * @return {@link Method} generic parameter {@link Type}
         */
        @Override
        public Type getGenericType() {
            return this.getMember().getGenericParameterTypes()[0];
        }

        /**
         * Updates target {@link Object} by new value {@link Object}
         *
         * @param subject - initial input target {@link Object} to update
         * @param value   - initial input value {@link Object} to set
         */
        public void setValue(final Object subject, final Object value) {
            try {
                this.getMember().invoke(subject, value);
            } catch (Exception e) {
                IllegalAccessException.throwInvalidAccess(formatMessage("ERROR: cannot set value={%s} for member={%s} of type={%s}, message={%s}", value, subject, this.member), e);
            }
        }
    }

    /**
     * Default private abstract property info constructor by input parameters
     *
     * @param initialType  - initial input {@link Class} type
     * @param member       - initial input member {@code M} type
     * @param propertyType - initial input {@link PropertyType} instance
     * @param name         - initial input property name {@link String}
     */
    private AbstractPropertyInfo(final Class<?> initialType, final M member, final PropertyType propertyType, final String name) {
        this.initialType = initialType;
        this.member = member;
        this.propertyType = propertyType;

        final Type genericType = this.getGenericType();
        this.type = Objects.isNull(genericType) ? initialType : genericType.getClass();
        this.name = name;
    }
}
