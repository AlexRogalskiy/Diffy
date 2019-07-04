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

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Regex {@link AbstractMatcher} implementation
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuppressWarnings("unchecked")
public class RegexMatcher extends AbstractMatcher<String> {

    /**
     * Default regex format
     */
    private String regex;
    /**
     * Default pattern format
     */
    private Pattern pattern;

    public RegexMatcher(final String regex, final int code) {
        this.regex = regex;
        try {
            this.pattern = Pattern.compile(regex, code);
        } catch (PatternSyntaxException e) {
            throw new InvalidFormatException(String.format("ERROR: cannot process regex = {%s} with code = {%s}", regex, code), e);
        }
    }

    /**
     * Checks whether the input matches the regular expression
     *
     * @param value - initial input value to be matched
     * @return
     */
    @Override
    public boolean matches(final String value) {
        final Matcher matcher = this.pattern.matcher(value);
        return matcher.find();
    }
}
