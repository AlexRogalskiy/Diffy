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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.iface.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.listOf;

/**
 * Collection one-to-one {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CollectionOneToOneMatcher<T> extends AbstractMatcher<Iterable<? extends T>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -2296393880356376030L;

    /**
     * Default {@link List} of {@link Matcher}
     */
    private final List<? extends Matcher<? super T>> matchers;

    public CollectionOneToOneMatcher(final List<? extends Matcher<? super T>> matchers) {
        ValidationUtils.notNull(matchers, "Matchers should not be null");
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Iterable<? extends T> target) {
        if (target instanceof Collection && ((Collection) target).size() != this.matchers.size()) {
            return false;
        }
        final Iterator<? extends Matcher<? super T>> iterator = this.matchers.iterator();
        final Iterator<? extends T> var3 = listOf(target).iterator();
        T value;
        do {
            if (!var3.hasNext()) {
                return true;
            }
            value = var3.next();
        } while (iterator.hasNext() && (iterator.next()).matches(value));
        return false;
    }
}
