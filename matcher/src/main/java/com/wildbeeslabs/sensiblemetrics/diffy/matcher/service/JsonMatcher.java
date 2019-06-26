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
import com.jayway.jsonpath.ReadContext;
import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.io.IOException;

/**
 * Json {@link AbstractTypeSafeMatcher} implementation
 *
 * @param <T> type of matcher item
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JsonMatcher<T> extends AbstractTypeSafeMatcher<T> {
    private final Matcher<? super ReadContext> jsonMatcher;

    public JsonMatcher(final Matcher<? super ReadContext> jsonMatcher) {
        ValidationUtils.notNull(jsonMatcher, "Json matcher should not be null");
        this.jsonMatcher = jsonMatcher;
    }

    @Override
    public boolean matchesSafe(final T json) {
        try {
            final ReadContext context = parse(json);
            return this.jsonMatcher.matches(context);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("is json ").appendDescriptionOf(this.jsonMatcher);
    }

    protected void describeMismatchSafely(final T json, final MatchDescription mismatchDescription) {
        try {
            final ReadContext context = parse(json);
            this.jsonMatcher.describeBy(context, mismatchDescription);
        } catch (JsonPathException e) {
            buildMismatchDescription(json, mismatchDescription, e);
        } catch (IOException e) {
            buildMismatchDescription(json, mismatchDescription, e);
        }
    }

    private static <T> void buildMismatchDescription(final T json, final MatchDescription mismatchDescription, final Exception e) {
        mismatchDescription
            .appendText("was ")
            .append(json)
            .appendText(" which failed with ")
            .append(e.getMessage());
    }

    private static <T> ReadContext parse(final T object) throws IOException {
        if (object instanceof String) {
            return JsonPath.parse((String) object);
        } else if (object instanceof File) {
            return JsonPath.parse((File) object);
        }
        return JsonPath.parse(object);
    }
}
