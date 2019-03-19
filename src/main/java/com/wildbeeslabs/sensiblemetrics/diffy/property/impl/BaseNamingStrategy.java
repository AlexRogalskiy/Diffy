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
package com.wildbeeslabs.sensiblemetrics.diffy.property.impl;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;

import java.util.Objects;

public class BaseNamingStrategy {
    /**
     * Default property naming strategies {@link BaseNamingStrategy}
     */
    public static final BaseNamingStrategy DEFAULT_SNAKE_CASE_STRATEGY = new SnakeCaseStrategy();
    public static final BaseNamingStrategy DEFAULT_UPPER_CAMEL_CASE_STRATEGY = new UpperCamelCaseStrategy();
    public static final BaseNamingStrategy DEFAULT_LOWER_CAMEL_CASE_STRATEGY = new BaseNamingStrategy();
    public static final BaseNamingStrategy DEFAULT_LOWER_CASE_STRATEGY = new LowerCaseStrategy();
    public static final BaseNamingStrategy DEFAULT_KEBAB_CASE_STRATEGY = new KebabCaseStrategy();

    public String nameForField(final AnnotatedField field, final String defaultName) {
        return defaultName;
    }

    public String nameForGetterMethod(final AnnotatedMethod method, final String defaultName) {
        return defaultName;
    }

    public String nameForSetterMethod(final AnnotatedMethod method, final String defaultName) {
        return defaultName;
    }

    public String nameForConstructorParameter(final AnnotatedParameter ctorParam, final String defaultName) {
        return defaultName;
    }

    public static abstract class AbstractBaseNamingStrategy extends BaseNamingStrategy {

        @Override
        public String nameForField(final AnnotatedField field, final String defaultName) {
            return translate(defaultName);
        }

        @Override
        public String nameForGetterMethod(final AnnotatedMethod method, final String defaultName) {
            return translate(defaultName);
        }

        @Override
        public String nameForSetterMethod(final AnnotatedMethod method, final String defaultName) {
            return translate(defaultName);
        }

        @Override
        public String nameForConstructorParameter(final AnnotatedParameter ctorParam, final String defaultName) {
            return translate(defaultName);
        }

        public abstract String translate(final String propertyName);
    }

    public static class SnakeCaseStrategy extends AbstractBaseNamingStrategy {

        @Override
        public String translate(final String input) {
            if (Objects.isNull(input)) {
                return null;
            }
            int length = input.length();
            final StringBuilder result = new StringBuilder(length * 2);
            int resultLength = 0;
            boolean wasPrevTranslated = false;
            for (int i = 0; i < length; i++) {
                char c = input.charAt(i);
                if (i > 0 || c != '_') {
                    if (Character.isUpperCase(c)) {
                        if (!wasPrevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != '_') {
                            result.append('_');
                            resultLength++;
                        }
                        c = Character.toLowerCase(c);
                        wasPrevTranslated = true;
                    } else {
                        wasPrevTranslated = false;
                    }
                    result.append(c);
                    resultLength++;
                }
            }
            return resultLength > 0 ? result.toString() : input;
        }
    }

    public static class UpperCamelCaseStrategy extends AbstractBaseNamingStrategy {
        /**
         * Converts camelCase to PascalCase
         * <p>
         * For example, "userName" would be converted to
         * "UserName".
         *
         * @param input formatted as camelCase string
         * @return input converted to PascalCase format
         */
        @Override
        public String translate(final String input) {
            if (org.apache.commons.lang3.StringUtils.isEmpty(input)) {
                return input;
            }
            char c = input.charAt(0);
            char uc = Character.toUpperCase(c);
            if (c == uc) {
                return input;
            }
            final StringBuilder sb = new StringBuilder(input);
            sb.setCharAt(0, uc);
            return sb.toString();
        }
    }

    public static class LowerCaseStrategy extends AbstractBaseNamingStrategy {

        @Override
        public String translate(final String input) {
            return input.toLowerCase();
        }
    }

    public static class KebabCaseStrategy extends AbstractBaseNamingStrategy {

        @Override
        public String translate(final String input) {
            if (org.apache.commons.lang3.StringUtils.isEmpty(input)) {
                return null;
            }
            int length = input.length();
            final StringBuilder result = new StringBuilder(length + (length >> 1));
            int upperCount = 0;
            for (int i = 0; i < length; ++i) {
                char ch = input.charAt(i);
                char lc = Character.toLowerCase(ch);

                if (lc == ch) {
                    if (upperCount > 1) {
                        result.insert(result.length() - 1, '-');
                    }
                    upperCount = 0;
                } else {
                    if ((upperCount == 0) && (i > 0)) {
                        result.append('-');
                    }
                    ++upperCount;
                }
                result.append(lc);
            }
            return result.toString();
        }
    }
}
