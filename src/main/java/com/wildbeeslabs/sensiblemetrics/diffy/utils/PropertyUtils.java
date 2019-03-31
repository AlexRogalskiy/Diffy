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
package com.wildbeeslabs.sensiblemetrics.diffy.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.property.NamingPredicate;
import com.wildbeeslabs.sensiblemetrics.diffy.property.NamingTokenizer;
import com.wildbeeslabs.sensiblemetrics.diffy.property.NamingTransformer;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enums.NameableType;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enums.PropertyType;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * Property utilities implementation {@link NamingPredicate}, {@link NamingTransformer}, {@link NamingTokenizer}
 */
@Slf4j
@UtilityClass
public class PropertyUtils {

    /**
     * Default property accessor method prefixes
     */
    public static final String GETTER_ACCESSOR_PREFIX = "get";
    public static final String SETTER_ACCESSOR_PREFIX = "set";
    public static final String BOOLEAN_ACCESSOR_PREFIX = "is";

    /**
     * Default camel case tokenizer pattern {@link Pattern}
     */
    private static final Pattern DEFAULT_CAMEL_CASE_PATTERN = Pattern.compile("(?<=[A-Z])(?=[A-Z][a-z])|(?<=[^A-Z])(?=[A-Z])|(?<=[A-Za-z])(?=[^A-Za-z])");
    /**
     * Default underscore tokenizer pattern {@link Pattern}
     */
    private static final Pattern DEFAULT_UNDERSCORE_PATTERN = Pattern.compile("_");

    /**
     * Default property getter {@link NamingPredicate}
     */
    public static final NamingPredicate DEFAULT_PROPERTY_GETTER_PREDICATE = (propertyName, propertyType) ->
        PropertyType.FIELD.equals(propertyType)
            || propertyName.startsWith(GETTER_ACCESSOR_PREFIX) && propertyName.length() > 3
            || propertyName.startsWith(BOOLEAN_ACCESSOR_PREFIX) && propertyName.length() > 2;
    /**
     * Default property setter {@link NamingPredicate}
     */
    public static final NamingPredicate DEFAULT_PROPERTY_SETTER_PREDICATE = (propertyName, propertyType) ->
        PropertyType.FIELD.equals(propertyType)
            || propertyName.startsWith(SETTER_ACCESSOR_PREFIX) && propertyName.length() > 3;
    /**
     * Default property valid {@link NamingPredicate}
     */
    public static final NamingPredicate DEFAULT_PROPERTY_VALID_PREDICATE = (propertyName, propertyType) -> true;

    /**
     * Default property getter {@link NamingTransformer}
     */
    public static final NamingTransformer DEFAULT_PROPERTY_GETTER_TRANSFORMER = (name, nameableType) -> {
        if (NameableType.METHOD.equals(nameableType)) {
            if (name.startsWith(GETTER_ACCESSOR_PREFIX) && name.length() > 3) {
                return StringUtils.decapitalize(name.substring(3));
            } else if (name.startsWith(BOOLEAN_ACCESSOR_PREFIX) && name.length() > 2) {
                return StringUtils.decapitalize(name.substring(2));
            }
        }
        return name;
    };

    /**
     * Default property setter {@link NamingTransformer}
     */
    public static final NamingTransformer DEFAULT_PROPERTY_SETTER_TRANSFORMER = (name, nameableType) -> {
        if (NameableType.METHOD.equals(nameableType) && name.startsWith(SETTER_ACCESSOR_PREFIX) && name.length() > 3) {
            return StringUtils.decapitalize(name.substring(3));
        }
        return name;
    };

    /**
     * Default camelcase property {@link NamingTokenizer}
     */
    public static final NamingTokenizer DEFAULT_CAMELCASE_TOKENIZER = (name, nameableType) -> DEFAULT_CAMEL_CASE_PATTERN.split(name);

    /**
     * Default underscore property {@link NamingTokenizer}
     */
    public static NamingTokenizer DEFAULT_UNDERSCORE_TOKENIZER = (name, nameableType) -> DEFAULT_UNDERSCORE_PATTERN.split(name);
}
