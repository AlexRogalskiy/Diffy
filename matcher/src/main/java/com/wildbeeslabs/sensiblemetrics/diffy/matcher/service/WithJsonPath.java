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

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.JsonPathException;
import com.jayway.jsonpath.PathNotFoundException;
import com.jayway.jsonpath.ReadContext;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;

/**
 * With json path {@link AbstractTypeSafeMatcher} implementation
 *
 * @param <T> type of matcher item
 */
@SuppressWarnings("unchecked")
public class WithJsonPath<T> extends AbstractTypeSafeMatcher<ReadContext> {
    private final JsonPath jsonPath;
    private final Matcher<T> matcher;

    public WithJsonPath(final JsonPath jsonPath, final Matcher<T> matcher) {
        ValidationUtils.notNull(matcher, "Matcher should not be null");
        this.jsonPath = jsonPath;
        this.matcher = matcher;
    }

    @Override
    public boolean matchesSafe(final ReadContext context) {
        try {
            final T value = context.read(this.jsonPath);
            return this.matcher.matches(value);
        } catch (JsonPathException e) {
            return false;
        }
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description
            .appendText("with json path ")
            .append(jsonPath.getPath())
            .appendText(" evaluated to ")
            .appendDescriptionOf(this.matcher);
    }

    protected void describeMismatchSafely(final ReadContext context, final MatchDescription mismatchDescription) {
        try {
            final T value = this.jsonPath.read((String) context.json());
            mismatchDescription
                .appendText("json path ")
                .append(this.jsonPath.getPath())
                .appendText(" was evaluated to ")
                .append(value);
        } catch (PathNotFoundException e) {
            mismatchDescription
                .appendText("json path ")
                .append(this.jsonPath.getPath())
                .appendText(" was not found in ")
                .append(context.json());
        } catch (JsonPathException e) {
            mismatchDescription
                .appendText("was ")
                .append(context.json());
        }
    }
}
