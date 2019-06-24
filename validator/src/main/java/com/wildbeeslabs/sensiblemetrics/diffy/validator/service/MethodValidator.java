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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.lang.reflect.Method;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ReflectionUtils.arrayMemberEquals;

/**
 * Method name {@link Validator} implementation
 */
public class MethodValidator implements Validator<Method> {

    /**
     * Default {@code T} value
     */
    private final Method method;
    /**
     * Default strict binary flag
     */
    private final boolean strict;

    /**
     * Default method validator constructor by input parameters
     *
     * @param method - initial method {@link Method}
     * @throws NullPointerException if method is {@code null}
     */
    public MethodValidator(final Method method) {
        this(method, true);
    }

    /**
     * Default method validator constructor by input parameters
     *
     * @param method - initial method {@link Method}
     * @throws NullPointerException if method is {@code null}
     */
    public MethodValidator(final Method method, boolean strict) {
        ValidationUtils.notNull(method, "Method should not be null");
        this.method = method;
        this.strict = strict;
    }

    /**
     * Returns true if input value {@code T} is valid, false - otherwise
     *
     * @param value - initial input value to be validated {@link Method}
     * @return true - if input value {@code T} is valid, false - otherwise
     */
    @Override
    public boolean validate(final Method value) {
        final boolean f1 = this.hasSameMethod(value);
        final boolean f2 = this.hasSameMethod(value);
        if (this.strict) {
            return (f1 && f2);
        }
        return (f1 || f2);
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
     * @param value - initial input value {@code T}
     * @return {@link MethodValidator}
     */
    public static Validator<Method> of(final Method value) {
        return new MethodValidator(value);
    }
}
