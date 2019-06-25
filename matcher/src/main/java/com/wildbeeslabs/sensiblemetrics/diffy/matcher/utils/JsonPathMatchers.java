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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.utils;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Predicate;
import com.jayway.jsonpath.ReadContext;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.JsonMatcher;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.WithJsonPath;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.service.WithoutJsonPath;
import lombok.experimental.UtilityClass;

import java.io.File;

import static com.wildbeeslabs.sensiblemetrics.diffy.matcher.interfaces.Matcher.DEFAULT_TRUE_MATCHER;

@UtilityClass
public class JsonPathMatchers {

    public static <T> Matcher<? super Object> hasJsonPath(final String jsonPath, final Matcher<T> resultMatcher) {
        return isJson(withJsonPath(jsonPath, resultMatcher));
    }

    public static Matcher<? super Object> hasNoJsonPath(final String jsonPath) {
        return isJson(withoutJsonPath(jsonPath));
    }

    public static Matcher<Object> isJson(final Matcher<? super ReadContext> matcher) {
        return new JsonMatcher<>(matcher);
    }

    public static Matcher<String> isJsonString(final Matcher<? super ReadContext> matcher) {
        return new JsonMatcher<>(matcher);
    }

    public static Matcher<File> isJsonFile(final Matcher<? super ReadContext> matcher) {
        return new JsonMatcher<>(matcher);
    }

    public static Matcher<? super ReadContext> withJsonPath(final String jsonPath, final Predicate... filters) {
        return withJsonPath(JsonPath.compile(jsonPath, filters), DEFAULT_TRUE_MATCHER);
    }

    public static Matcher<? super ReadContext> withoutJsonPath(final String jsonPath, final Predicate... filters) {
        return withoutJsonPath(JsonPath.compile(jsonPath, filters));
    }

    public static Matcher<? super ReadContext> withoutJsonPath(final JsonPath jsonPath) {
        return new WithoutJsonPath(jsonPath);
    }

    public static <T> Matcher<? super ReadContext> withJsonPath(final String jsonPath, final Matcher<T> resultMatcher) {
        return withJsonPath(JsonPath.compile(jsonPath), resultMatcher);
    }

    public static <T> Matcher<? super ReadContext> withJsonPath(final JsonPath jsonPath, final Matcher<T> resultMatcher) {
        return new WithJsonPath<>(jsonPath, resultMatcher);
    }
}
