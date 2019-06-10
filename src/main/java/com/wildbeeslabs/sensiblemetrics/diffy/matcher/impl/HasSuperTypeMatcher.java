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

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.iterableOf;

/**
 * Has super type {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class HasSuperTypeMatcher<T extends Iterable<Class<?>>> extends AbstractMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 450999386035072521L;

    private final Matcher<? super Type> matcher;

    public HasSuperTypeMatcher(final Matcher<? super Type> matcher) {
        this.matcher = matcher;
    }

    @Override
    public boolean matches(final T target) {
        final Set<Class<?>> checkedInterfaces = new HashSet();
        final Iterator<Class<?>> var3 = target.iterator();

        Class<?> typeDefinition;
        do {
            if (!var3.hasNext()) {
                return false;
            }
            typeDefinition = var3.next();
        } while (!this.matcher.matches(typeDefinition.getGenericSuperclass()) && !this.hasInterface(typeDefinition, checkedInterfaces));
        return true;
    }

    private boolean hasInterface(final Class<?> typeDefinition, final Set<Class<?>> checkedInterfaces) {
        final Iterator<Class<?>> var3 = iterableOf(typeDefinition.getInterfaces()).iterator();
        Class<?> interfaceType;
        do {
            do {
                if (!var3.hasNext()) {
                    return false;
                }
                interfaceType = var3.next();
            } while (!checkedInterfaces.add(interfaceType));
        } while (!this.matcher.matches(interfaceType.getGenericSuperclass()) && !this.hasInterface(interfaceType, checkedInterfaces));
        return true;
    }
}
