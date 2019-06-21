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

import java.util.Iterator;
import java.util.Objects;

import static com.wildbeeslabs.sensiblemetrics.diffy.utility.ServiceUtils.listOf;

/**
 * Collection element {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CollectionElementMatcher<T> extends AbstractMatcher<Iterable<? extends T>> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -288578317981936198L;

    /**
     * Default collection index
     */
    private final int index;
    /**
     * Default {@link Matcher}
     */
    private final Matcher<? super T> matcher;

    public CollectionElementMatcher(int index, final Matcher<? super T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        this.index = index;
        this.matcher = matcher;
    }

    @Override
    public boolean matches(final Iterable<? extends T> target) {
        final Iterator<? extends T> iterator = listOf(target).iterator();
        for (int index = 0; index < this.index; ++index) {
            if (!iterator.hasNext()) {
                return false;
            }
            iterator.next();
        }
        return iterator.hasNext() && this.matcher.matches(iterator.next());
    }
}
