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

import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Caching {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public class CachingMatcher<T> extends AbstractMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -4812424558046288887L;

    /**
     * Default {@link Matcher}
     */
    private final Matcher<? super T> matcher;
    /**
     * Default {@link ConcurrentMap}
     */
    protected final ConcurrentMap<? super T, Boolean> map;

    public CachingMatcher(final Matcher<? super T> matcher, final ConcurrentMap<? super T, Boolean> map) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        this.matcher = matcher;
        this.map = Optional.ofNullable(map).orElseGet(() -> new ConcurrentHashMap<>());
    }

    @Override
    public boolean matches(final T target) {
        Boolean cached = this.map.get(target);
        if (Objects.isNull(cached)) {
            cached = this.onCacheMiss(target);
        }
        return cached;
    }

    protected boolean onCacheMiss(final T target) {
        boolean cached = this.matcher.matches(target);
        this.map.put(target, cached);
        return cached;
    }

    public static class WithInlineEviction<S> extends CachingMatcher<S> {
        private final int evictionSize;

        public WithInlineEviction(final Matcher<? super S> matcher, final ConcurrentMap<? super S, Boolean> map, int evictionSize) {
            super(matcher, map);
            this.evictionSize = evictionSize;
        }

        @Override
        protected boolean onCacheMiss(final S target) {
            if (this.map.size() >= this.evictionSize) {
                final Iterator<?> iterator = this.map.entrySet().iterator();
                iterator.next();
                iterator.remove();
            }
            return super.onCacheMiss(target);
        }
    }
}
