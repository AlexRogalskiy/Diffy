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
package com.wildbeeslabs.sensiblemetrics.diffy.core.property.impl;

import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * Default base naming strategy implementation
 */
public class BaseNamingStrategy {

    /**
     * Default snake case {@link BaseNamingStrategy} instance
     */
    public static final BaseNamingStrategy DEFAULT_SNAKE_CASE_STRATEGY = new SnakeCaseStrategy();
    /**
     * Default upper camel case {@link BaseNamingStrategy} instance
     */
    public static final BaseNamingStrategy DEFAULT_UPPER_CAMEL_CASE_STRATEGY = new UpperCamelCaseStrategy();
    /**
     * Default lower camel case {@link BaseNamingStrategy} instance
     */
    public static final BaseNamingStrategy DEFAULT_LOWER_CAMEL_CASE_STRATEGY = new BaseNamingStrategy();
    /**
     * Default lower case {@link BaseNamingStrategy} instance
     */
    public static final BaseNamingStrategy DEFAULT_LOWER_CASE_STRATEGY = new LowerCaseStrategy();
    /**
     * Default kebab case {@link BaseNamingStrategy} instance
     */
    public static final BaseNamingStrategy DEFAULT_KEBAB_CASE_STRATEGY = new KebabCaseStrategy();

    /**
     * Returns default {@link AnnotatedField} name by input parameters
     *
     * @param field       - initial input {@link AnnotatedField} instance
     * @param defaultName - initial input default property name
     * @return default property name
     */
    public String nameForField(final AnnotatedField field, final String defaultName) {
        return defaultName;
    }

    /**
     * Returns default {@link AnnotatedMethod} getter name by input parameters
     *
     * @param method      - initial input {@link AnnotatedMethod} instance
     * @param defaultName - initial input default method name
     * @return default {@link AnnotatedMethod} getter name
     */
    public String nameForGetterMethod(final AnnotatedMethod method, final String defaultName) {
        return defaultName;
    }

    /**
     * Returns default {@link AnnotatedMethod} setter name by input parameters
     *
     * @param method      - initial input {@link AnnotatedMethod} instance
     * @param defaultName - initial input default method name
     * @return default {@link AnnotatedMethod} setter name
     */
    public String nameForSetterMethod(final AnnotatedMethod method, final String defaultName) {
        return defaultName;
    }

    /**
     * Returns default {@link AnnotatedParameter} name by input parameters
     *
     * @param ctorParam   - initial input {@link AnnotatedParameter} instance
     * @param defaultName - initial input default property name
     * @return default property name
     */
    public String nameForConstructorParameter(final AnnotatedParameter ctorParam, final String defaultName) {
        return defaultName;
    }

    /**
     * Abstract {@link BaseNamingStrategy} implementation
     */
    public static abstract class AbstractBaseNamingStrategy extends BaseNamingStrategy {

        /**
         * Returns default {@link AnnotatedField} name by input parameters
         *
         * @param field       - initial input {@link AnnotatedField} instance
         * @param defaultName - initial input default property name
         * @return default property name
         */
        @Override
        public String nameForField(final AnnotatedField field, final String defaultName) {
            return this.translate(defaultName);
        }

        /**
         * Returns default {@link AnnotatedMethod} getter name by input parameters
         *
         * @param method      - initial input {@link AnnotatedMethod} instance
         * @param defaultName - initial input default method name
         * @return default {@link AnnotatedMethod} getter name
         */
        @Override
        public String nameForGetterMethod(final AnnotatedMethod method, final String defaultName) {
            return this.translate(defaultName);
        }

        /**
         * Returns default {@link AnnotatedMethod} setter name by input parameters
         *
         * @param method      - initial input {@link AnnotatedMethod} instance
         * @param defaultName - initial input default method name
         * @return default {@link AnnotatedMethod} setter name
         */
        @Override
        public String nameForSetterMethod(final AnnotatedMethod method, final String defaultName) {
            return this.translate(defaultName);
        }

        /**
         * Returns default {@link AnnotatedParameter} name by input parameters
         *
         * @param ctorParam   - initial input {@link AnnotatedParameter} instance
         * @param defaultName - initial input default property name
         * @return default property name
         */
        @Override
        public String nameForConstructorParameter(final AnnotatedParameter ctorParam, final String defaultName) {
            return this.translate(defaultName);
        }

        /**
         * Returns translated {@link String} property name by input parameter value
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        public abstract String translate(final String propertyName);
    }

    /**
     * Default snake case {@link AbstractBaseNamingStrategy} implementation
     */
    public static class SnakeCaseStrategy extends AbstractBaseNamingStrategy {

        /**
         * Returns "snake case" {@link String} property name by input parameter value
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        @Override
        public String translate(final String propertyName) {
            if (Objects.isNull(propertyName)) {
                return null;
            }
            int length = propertyName.length();
            final StringBuilder result = new StringBuilder(length * 2);
            int resultLength = 0;
            boolean wasPrevTranslated = false;
            for (int i = 0; i < length; i++) {
                char c = propertyName.charAt(i);
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
            return resultLength > 0 ? result.toString() : propertyName;
        }
    }

    /**
     * Default upper camel case {@link AbstractBaseNamingStrategy} implementation
     */
    public static class UpperCamelCaseStrategy extends AbstractBaseNamingStrategy {

        /**
         * Returns "upper camel case" {@link String} property name by input parameter value
         * <p>
         * For example, "userName" would be converted to "UserName".
         * </p>
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        @Override
        public String translate(final String propertyName) {
            if (isEmpty(propertyName)) {
                return propertyName;
            }
            char c = propertyName.charAt(0);
            char uc = Character.toUpperCase(c);
            if (c == uc) {
                return propertyName;
            }
            final StringBuilder sb = new StringBuilder(propertyName);
            sb.setCharAt(0, uc);
            return sb.toString();
        }
    }

    /**
     * Default lower case {@link AbstractBaseNamingStrategy} implementation
     */
    public static class LowerCaseStrategy extends AbstractBaseNamingStrategy {

        /**
         * Returns "lower case" {@link String} property name by input parameter value
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        @Override
        public String translate(final String propertyName) {
            return StringUtils.lowerCase(propertyName);
        }
    }

    /**
     * Default upper case {@link AbstractBaseNamingStrategy} implementation
     */
    public static class UpperCaseStrategy extends AbstractBaseNamingStrategy {

        /**
         * Returns "lower case" {@link String} property name by input parameter value
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        @Override
        public String translate(final String propertyName) {
            return StringUtils.upperCase(propertyName);
        }
    }

    /**
     * Default kebab {@link AbstractBaseNamingStrategy} implementation
     */
    public static class KebabCaseStrategy extends AbstractBaseNamingStrategy {

        /**
         * Returns "kebab case" {@link String} property name by input parameter value
         *
         * @param propertyName - initial input property name {@link String} to translate
         * @return translated {@link String} property name
         */
        @Override
        public String translate(final String propertyName) {
            if (isEmpty(propertyName)) {
                return null;
            }
            int length = propertyName.length();
            final StringBuilder result = new StringBuilder(length + (length >> 1));
            int upperCount = 0;
            for (int i = 0; i < length; ++i) {
                char ch = propertyName.charAt(i);
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
