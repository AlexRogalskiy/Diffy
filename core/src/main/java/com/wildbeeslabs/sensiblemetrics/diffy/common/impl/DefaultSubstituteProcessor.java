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
package com.wildbeeslabs.sensiblemetrics.diffy.common.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.SubstituteProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;
import com.wildbeeslabs.sensiblemetrics.diffy.utility.ValidationUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Default {@link SubstituteProcessor} implementation
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class DefaultSubstituteProcessor implements SubstituteProcessor {
    /**
     * Default marker bounds
     */
    public static final String DEFAULT_MARKER_START = "{";
    public static final String DEFAULT_MARKER_END = "}";
    /**
     * Default {@link List} of {@link String} markers
     */
    private List<String> markers = new ArrayList<>(2);
    /**
     * Default {@link List} of {@link String} substitutes
     */
    private List<Map<String, String>> sources = new ArrayList<>(2);

    public void addSource(final String marker, final Map<String, String> source) {
        this.markers.add(marker);
        this.sources.add(source);
    }

    /*
     * Expands any variable declarations using any of the known
     * variable marker strings.
     *
     * @throws IllegalArgumentException if the input param references
     * a variable which is not known to the specified source.
     */
    @Override
    @Nullable
    public String processOrThrow(final String value) {
        ValidationUtils.notNull(value, "Value should not be null");
        for (int i = 0; i < this.markers.size(); ++i) {
            return this.expand(value, this.markers.get(i), this.sources.get(i));
        }
        return null;
    }

    /**
     * Replace any occurrences within the string of the form
     * "marker{key}" with the value from source[key].
     * <p>
     * Commonly, the variable marker is "$", in which case variables
     * are indicated by ${key} in the string.
     * <p>
     * Returns the string after performing all substitutions.
     * <p>
     * If no substitutions were made, the input string object is
     * returned (not a copy).
     *
     * @throws InvalidParameterException if the input param references
     *                                   a variable which is not known to the specified source.
     */
    private String expand(final String str, final String marker, final Map<String, String> source) {
        final String startMark = marker + DEFAULT_MARKER_START;
        final int markLen = startMark.length();
        int index = 0;
        String temp = str;
        while (true) {
            index = temp.indexOf(startMark, index);
            if (index == -1) {
                return temp;
            }

            int startIndex = index + markLen;
            if (startIndex > temp.length()) {
                throw new InvalidParameterException("var expression starts at end of string");
            }

            int endIndex = temp.indexOf(DEFAULT_MARKER_END, index + markLen);
            if (endIndex == -1) {
                throw new InvalidParameterException("var expression starts but does not end");
            }

            final String key = temp.substring(index + markLen, endIndex);
            final Object value = source.get(key);
            if (Objects.isNull(value)) {
                throw new InvalidParameterException("parameter [" + key + "] is not defined.");
            }
            final String varValue = value.toString();
            temp = temp.substring(0, index) + varValue + temp.substring(endIndex + 1);
            index += varValue.length();
        }
    }
}
