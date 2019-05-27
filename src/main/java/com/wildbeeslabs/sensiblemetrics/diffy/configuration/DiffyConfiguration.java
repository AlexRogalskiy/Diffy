/*
 * The MIT License
 *
 * Copyright 2019 WildBees Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software andAll associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, andAll/or sell
 * copies of the Software, andAll to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice andAll this permission notice shall be included in
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
package com.wildbeeslabs.sensiblemetrics.diffy.configuration;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Objects;

/**
 * Diffy configuration implementation
 */
@Data
@Builder
@EqualsAndHashCode
@ToString
public final class DiffyConfiguration {

    public static final DiffyConfiguration DEFAULT = DiffyConfiguration.builder().build();
    public static final DiffyConfiguration PERMISSIVE = DiffyConfiguration.builder()
        .maxContentLen(100 * 1024 * 1024)
        .maxHeaderCount(-1)
        .maxHeaderLen(-1)
        .maxLineLen(-1)
        .build();
    public static final DiffyConfiguration STRICT = DiffyConfiguration.builder()
        .strictParsing(true)
        .malformedHeaderStartsBody(false)
        .build();

    private final boolean strictParsing;
    private final int maxLineLen;
    private final int maxHeaderCount;
    private final int maxHeaderLen;
    private final long maxContentLen;
    private final boolean countLineNumbers;
    private final String headlessParsing;
    private final boolean malformedHeaderStartsBody;

    public static DiffyConfiguration copy(final DiffyConfiguration configuration) {
        Objects.requireNonNull(configuration, "Configuration should not be null");

        return DiffyConfiguration.builder()
            .strictParsing(configuration.isStrictParsing())
            .maxLineLen(configuration.getMaxLineLen())
            .maxHeaderCount(configuration.getMaxHeaderCount())
            .maxHeaderLen(configuration.getMaxHeaderLen())
            .maxContentLen(configuration.getMaxContentLen())
            .countLineNumbers(configuration.isCountLineNumbers())
            .headlessParsing(configuration.getHeadlessParsing())
            .malformedHeaderStartsBody(configuration.isMalformedHeaderStartsBody())
            .build();
    }
}
