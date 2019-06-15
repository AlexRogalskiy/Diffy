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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.iterableOf;
import static java.util.Arrays.asList;

/**
 * Method override {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MethodOverrideMatcher<T extends Method> extends AbstractMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 8175070304947376468L;

    private final Matcher<? super Parameter[]> matcher;

    public MethodOverrideMatcher(final Matcher<? super Parameter[]> matcher) {
        Objects.requireNonNull(matcher, "Matcher should not be null");
        this.matcher = matcher;
    }

    @Override
    public boolean matches(final T target) {
        final Set<Class<?>> duplicates = new HashSet();
        final Iterator<Class<?>> var3 = iterableOf(target.getParameterTypes()).iterator();

        Class<?> typeDefinition;
        do {
            if (!var3.hasNext()) {
                return false;
            }
            typeDefinition = var3.next();
        } while (!this.matches(target, typeDefinition) && !this.matches(target, asList(typeDefinition.getInterfaces()), duplicates));
        return true;
    }

    private boolean matches(final T target, final List<Class<?>> typeDefinitions, final Set<Class<?>> duplicates) {
        final Iterator<Class<?>> var4 = typeDefinitions.iterator();

        Class<?> anInterface;
        do {
            do {
                if (!var4.hasNext()) {
                    return false;
                }
                anInterface = var4.next();
            } while (!duplicates.add(anInterface));
        } while (!this.matches(target, anInterface) && !this.matches(target, asList(anInterface.getInterfaces()), duplicates));
        return true;
    }

    private boolean matches(final T target, final Class<?> typeDefinition) {
        final Iterator<Method> var3 = iterableOf(typeDefinition.getDeclaredMethods()).iterator();

        while (var3.hasNext()) {
            final Method methodDescription = var3.next();
            if (Objects.equals(methodDescription.getParameterTypes(), target.getParameterTypes())) {
                if (this.matcher.matches(methodDescription.getParameters())) {
                    return true;
                }
                break;
            }
        }
        return false;
    }
}
