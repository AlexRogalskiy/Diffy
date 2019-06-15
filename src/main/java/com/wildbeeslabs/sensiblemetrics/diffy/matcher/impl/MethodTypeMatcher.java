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

import lombok.*;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Member type {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MethodTypeMatcher<T extends Method> extends AbstractMatcher<T> {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = 1204184525772829526L;

    private final MethodTypeMatcher.SortType sort;

    public MethodTypeMatcher(final MethodTypeMatcher.SortType sort) {
        Objects.requireNonNull(sort, "Sort type should not be null");
        this.sort = sort;
    }

    @Override
    public boolean matches(final T target) {
        return this.sort.checkType(target);
    }

    /**
     * Member sort type {@link Enum}
     */
    @Getter
    @RequiredArgsConstructor
    public enum SortType {
        SYNTHETIC("isSynthetic()") {
            @Override
            protected boolean checkType(final Method target) {
                return target.isSynthetic();
            }
        },
        BRIDGE("isBridge()") {
            @Override
            protected boolean checkType(final Method target) {
                return target.isBridge();
            }
        },
        DEFAULT("isDefault()") {
            @Override
            protected boolean checkType(final Method target) {
                return target.isDefault();
            }
        },
        VAR_ARGS("isVarArgs()") {
            @Override
            protected boolean checkType(final Method target) {
                return target.isVarArgs();
            }
        };

        private final String description;

        protected abstract boolean checkType(final Method target);
    }
}
