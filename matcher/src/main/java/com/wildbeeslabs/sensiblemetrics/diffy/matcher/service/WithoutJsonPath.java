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
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.description.iface.MatchDescription;

/**
 * Without json path {@link AbstractTypeSafeMatcher} implementation
 */
public class WithoutJsonPath extends AbstractTypeSafeMatcher<ReadContext> {
    private final JsonPath jsonPath;

    public WithoutJsonPath(final JsonPath jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public boolean matchesSafe(final ReadContext actual) {
        try {
            actual.read(this.jsonPath);
            return false;
        } catch (JsonPathException e) {
            return true;
        }
    }

    @Override
    public void describeTo(final MatchDescription description) {
        description.appendText("without json path ").append(this.jsonPath.getPath());
    }
}
