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
package com.wildbeeslabs.sensiblemetrics.diffy.common.helpers.impl;

import com.wildbeeslabs.sensiblemetrics.diffy.common.exception.InvalidFormatException;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.join;

/**
 * Default file size helper implementation
 */
@Data
public class FileSizeHelper {
    /**
     * Default file part formats
     */
    private final static String LENGTH_PART = "([0-9]+)";
    private final static int DOUBLE_GROUP = 1;

    private final static String UNIT_PART = "(|kb|mb|gb)s?";
    private final static int UNIT_GROUP = 2;

    private static final Pattern FILE_SIZE_PATTERN = Pattern.compile(LENGTH_PART + "\\s*" + UNIT_PART, Pattern.CASE_INSENSITIVE);

    private static final long KB_COEFFICIENT = 1024;
    private static final long MB_COEFFICIENT = 1024 * KB_COEFFICIENT;
    private static final long GB_COEFFICIENT = 1024 * MB_COEFFICIENT;

    private final long size;

    public FileSizeHelper(final long size) {
        this.size = size;
    }

    public static FileSizeHelper valueOf(final String value) {
        final Matcher matcher = FILE_SIZE_PATTERN.matcher(value);
        long coefficient;
        if (matcher.matches()) {
            final String lenStr = matcher.group(DOUBLE_GROUP);
            final String unitStr = matcher.group(UNIT_GROUP);

            long lenValue = Long.valueOf(lenStr);
            if (unitStr.equalsIgnoreCase("")) {
                coefficient = 1;
            } else if (unitStr.equalsIgnoreCase("kb")) {
                coefficient = KB_COEFFICIENT;
            } else if (unitStr.equalsIgnoreCase("mb")) {
                coefficient = MB_COEFFICIENT;
            } else if (unitStr.equalsIgnoreCase("gb")) {
                coefficient = GB_COEFFICIENT;
            } else {
                throw new IllegalStateException("Unexpected " + unitStr);
            }
            return new FileSizeHelper(lenValue * coefficient);
        }
        throw new InvalidFormatException(String.format("ERROR: input value = {%s} is not in the expected format", value));
    }

    @Override
    public String toString() {
        long inKB = this.size / KB_COEFFICIENT;
        if (inKB == 0) {
            return join(this.size, " Bytes");
        }
        long inMB = this.size / MB_COEFFICIENT;
        if (inMB == 0) {
            return join(inKB, " KB");
        }
        long inGB = this.size / GB_COEFFICIENT;
        if (inGB == 0) {
            return join(inMB, " MB");
        }
        return join(inGB, " GB");
    }
}
