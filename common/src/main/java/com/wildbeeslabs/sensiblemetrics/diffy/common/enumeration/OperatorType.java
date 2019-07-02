/*
 * The MIT License
 *
 * Copyright 2018 WildBees Labs.
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
package com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

import static com.wildbeeslabs.sensiblemetrics.diffy.common.exception.UnsupportedOperatorTypeException.throwUnsupportedOperatorType;

/**
 * Default operator type {@link Enum}
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-07
 */
@Getter
@RequiredArgsConstructor
public enum OperatorType {
    GTE(">="),
    LTE("<="),
    EQ("=="),
    /**
     * Type safe equals
     */
    TSEQ("==="),
    NE("!="),
    /**
     * Type safe not equals
     */
    TSNE("!=="),
    LT("<"),
    GT(">"),
    REGEX("=~"),
    NIN("NIN"),
    IN("IN"),
    CONTAINS("CONTAINS"),
    ALL("ALL"),
    SIZE("SIZE"),
    EXISTS("EXISTS"),
    TYPE("TYPE"),
    MATCHES("MATCHES"),
    EMPTY("EMPTY"),
    SUBSETOF("SUBSETOF");
    /**
     * Default operator code
     */
    private final String operatorCode;

    @NonNull
    public static OperatorType fromCode(final String operatorCode) {
        return Arrays.stream(values())
            .filter(type -> type.getOperatorCode().equalsIgnoreCase(operatorCode))
            .findFirst()
            .orElseThrow(() -> throwUnsupportedOperatorType(operatorCode));
    }
}
