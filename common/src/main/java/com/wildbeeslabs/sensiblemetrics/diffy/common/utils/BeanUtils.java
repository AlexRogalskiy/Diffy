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
package com.wildbeeslabs.sensiblemetrics.diffy.common.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Encapsulates utility methods associated with standard java beans.
 *
 * @author urechm
 */
@UtilityClass
public class BeanUtils {

    /**
     * Default bean utils accessors
     */
    public static final String PREFIX_GETTER_IS = "is";
    public static final String PREFIX_GETTER_GET = "get";
    public static final String PREFIX_SETTER = "set";
    public static final String PREFIX_ADDER = "add";

    /**
     * @param method to check if it is an 'adder' method.
     * @return true if the given method is an 'adder' method.
     */
    public static boolean isAdder(final Method method) {
        final int parameterCount = getParameterCount(method);
        if (parameterCount != 1) {
            return false;
        }
        final Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {
            return false;
        }
        final String methodName = method.getName();
        return methodName.startsWith(PREFIX_ADDER);
    }

    /**
     * @param method to check if it is a standard java beans getter.
     * @return true if the given method is a standard java beans getter.
     */
    public static boolean isGetter(final Method method) {
        final int parameterCount = getParameterCount(method);
        if (parameterCount > 0) {
            return false;
        }
        final Class<?> returnType = method.getReturnType();
        if (returnType == void.class) {
            return false;
        }
        final String methodName = method.getName();
        if (!methodName.startsWith(PREFIX_GETTER_GET) && !methodName.startsWith(PREFIX_GETTER_IS)) {
            return false;
        }
        if (methodName.startsWith(PREFIX_GETTER_IS)) {
            if (!returnType.equals(boolean.class) && !returnType.equals(Boolean.class)) {
                return false;
            }
        }
        return true;
    }

    private static int getParameterCount(final Method method) {
        return method.getParameterTypes().length;
    }

    /**
     * @param method to check if it is a standard java beans setter.
     * @return true if the given method is a standard java beans setter.
     */
    public static boolean isSetter(final Method method) {
        final int parameterCount = getParameterCount(method);
        if (parameterCount != 1) {
            return false;
        }
        final Class<?> returnType = method.getReturnType();
        if (returnType != void.class) {
            return false;
        }
        final String methodName = method.getName();
        if (!methodName.startsWith(PREFIX_SETTER)) {
            return false;
        }
        return true;
    }

    /**
     * @param method to get the associated property name for.
     * @return The property name of the associated property if the given method matches a standard java beans getter or setter.
     */
    public static String getPropertyName(final Method method) {
        final String methodName = method.getName();
        String rawPropertyName = getSubstringIfPrefixMatches(methodName, PREFIX_GETTER_GET);
        if (Objects.isNull(rawPropertyName)) {
            rawPropertyName = getSubstringIfPrefixMatches(methodName, PREFIX_SETTER);
        }
        if (Objects.isNull(rawPropertyName)) {
            rawPropertyName = getSubstringIfPrefixMatches(methodName, PREFIX_GETTER_IS);
        }
        if (Objects.isNull(rawPropertyName)) {
            rawPropertyName = getSubstringIfPrefixMatches(methodName, PREFIX_ADDER);
        }
        return toLowerCamelCase(rawPropertyName);
    }

    /**
     * Converts the given String into lower camel case form.
     *
     * @param string to decapitalize.
     * @return null if the given String is null.
     * Emtpy string if the given string is empty.
     * The given string if the first two consecutive letters are in upper case.
     * The given string with the first letter in lower case otherwise, which might be the given string.
     */
    public static String toLowerCamelCase(final String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        if (string.length() > 1 && Character.isUpperCase(string.charAt(1)) && Character.isUpperCase(string.charAt(0))) {
            return string;
        }
        final char chars[] = string.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    private static String getSubstringIfPrefixMatches(final String wholeString, final String prefix) {
        if (wholeString.startsWith(prefix)) {
            return wholeString.substring(prefix.length());
        }
        return null;
    }
}
