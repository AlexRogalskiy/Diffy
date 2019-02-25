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
package com.wildbeeslabs.sensiblemetrics.diffy.examples.matcher;

import com.wildbeeslabs.sensiblemetrics.diffy.examples.model.DeliveryInfo;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.impl.AbstractTypeSafeMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Custom delivery info matcher implementation {@link DeliveryInfo}
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UrlParametersMatcher extends AbstractTypeSafeMatcher<String> {

    /**
     * Default collection {@link Map} of url parameters
     */
    private final Map<String, String> expected;

    /**
     * Default url parameter matcher with expected input url argument {@link String}
     *
     * @param urlString - initial argument url string to match
     */
    public UrlParametersMatcher(final String urlString) {
        this.expected = paramStringToMap(urlString);
    }

    protected final Map<String, String> paramStringToMap(final String string) {
        return Arrays.stream(string.split("&"))
                .collect(Collectors.toMap(
                        (String s) -> s.split("=")[0],
                        (String s) -> s.split("=").length == 2 ? s.split("=")[1] : StringUtils.EMPTY));
    }

    @Override
    public boolean matchesSafe(final String value) {
        return getExpected().equals(paramStringToMap(value));
    }

    /**
     * Returns url parameters matcher instance {@link UrlParametersMatcher}
     *
     * @param urlString - initial argument url string to match
     * @return url parameters matcher instance {@link UrlParametersMatcher}
     */
    public static UrlParametersMatcher getInstance(final String urlString) {
        return new UrlParametersMatcher(urlString);
    }
}
