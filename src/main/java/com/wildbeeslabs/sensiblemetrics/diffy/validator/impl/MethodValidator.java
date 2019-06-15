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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.iface.Validator;

import java.lang.reflect.Method;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ReflectionUtils.arrayMemberEquals;

/**
 * Method name {@link Validator} implementation
 *
 * @param <T> type of validated value
 */
public class MethodValidator<T> implements Validator<Method> {

    /**
     * Default {@code T} value
     */
    private final Method method;

    /**
     * Default method validator constructor by input parameters
     *
     * @param method - initial method {@link Method}
     * @throws NullPointerException if method is {@code null}
     */
    public MethodValidator(final Method method) {
        Objects.requireNonNull(method, "Method should not be null");
        this.method = method;
    }

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@link Method}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    @Override
    public boolean validate(final Method value) {
        return false;
    }

    private boolean hasSimilarMethod(final Method value) {
        final String wantedMethodName = value.getName();
        final String currentMethodName = this.method.getName();

        final boolean methodNameEquals = wantedMethodName.equals(currentMethodName);
        final boolean methodEquals = this.hasSameMethod(value);

        if (!methodNameEquals) {
            return false;
        }
        final boolean overloadedButSameArgs = !methodEquals && arrayMemberEquals(value.getParameters(), this.method.getParameters());
        return !overloadedButSameArgs;
    }

    private boolean hasSameMethod(final Method value) {
        final Method m1 = this.method;
        final Method m2 = value;

        if (m1.getName() != null && m1.getName().equals(m2.getName())) {
            final Class[] params1 = m1.getParameterTypes();
            final Class[] params2 = m2.getParameterTypes();
            if (params1.length == params2.length) {
                for (int i = 0; i < params1.length; i++) {
                    if (params1[i] != params2[i])
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Returns {@link MethodValidator} by input value {@code T}
     *
     * @param <T>   type of validated value
     * @param value - initial input value {@code T}
     * @return {@link MethodValidator}
     */
    public static <T extends Method> MethodValidator<T> of(final T value) {
        return new MethodValidator<>(value);
    }
}
