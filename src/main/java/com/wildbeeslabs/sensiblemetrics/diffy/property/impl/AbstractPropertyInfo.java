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

import com.wildbeeslabs.sensiblemetrics.diffy.property.iface.PropertyInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enums.PropertyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Abstract {@link PropertyInfo} implementation
 */
@Slf4j
@Data
@EqualsAndHashCode
@ToString
public abstract class AbstractPropertyInfo<M extends Member> implements PropertyInfo {

    protected final Class<?> initialType;
    protected final M member;
    protected final Class<?> type;
    protected final String name;
    protected final PropertyType propertyType;

    /**
     * Default abstract {@link Method} property info implementation
     */
    public static abstract class AbstractMethodInfo extends AbstractPropertyInfo<Method> {

        private AbstractMethodInfo(final Class<?> initialType, final Method method, final String name) {
            super(initialType, method, PropertyType.METHOD, name);
            method.setAccessible(true);
        }

        @Override
        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            return member.getAnnotation(annotationClass);
        }
    }

    /**
     * Default {@link Field} property info implementation
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

        @Override
        public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
            return member.getAnnotation(annotationClass);
        }

        @Override
        public Type getGenericType() {
            return member.getGenericType();
        }

        public Object getValue(final Object subject) {
            try {
                return member.get(subject);
            } catch (Exception e) {
                log.error("ERROR: cannot get member={%s} of type={%s}, message={%s}", subject, member, e.getMessage());
            }
            return null;
        }

        public void setValue(final Object subject, final Object value) {
            try {
                member.set(subject, value);
            } catch (Exception e) {
                log.error("ERROR: cannot set value={%s} for member={%s} of type={%s}, message={%s}", value, subject, member, e.getMessage());
            }
        }
    }

    /**
     * Default method accessor implementation
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

        @Override
        public Type getGenericType() {
            return member.getGenericReturnType();
        }

        public Object getValue(final Object subject) {
            try {
                return member.invoke(subject);
            } catch (Exception e) {
                log.error("ERROR: cannot get member={%s} of type={%s}, message={%s}", subject, member, e.getMessage());
            }
            return null;
        }
    }

    /**
     * Default method setter implementation
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

        @Override
        public Type getGenericType() {
            return member.getGenericParameterTypes()[0];
        }

        public void setValue(final Object subject, final Object value) {
            try {
                member.invoke(subject, value);
            } catch (Exception e) {
                log.error("ERROR: cannot set value={%s} for member={%s} of type={%s}, message={%s}", value, subject, member, e.getMessage());
            }
        }
    }

    private AbstractPropertyInfo(final Class<?> initialType, final M member, final PropertyType propertyType, final String name) {
        this.initialType = initialType;
        this.member = member;
        this.propertyType = propertyType;

        final Type genericType = getGenericType();
        this.type = Objects.isNull(genericType) ? initialType : genericType.getClass();
        this.name = name;
    }
}
