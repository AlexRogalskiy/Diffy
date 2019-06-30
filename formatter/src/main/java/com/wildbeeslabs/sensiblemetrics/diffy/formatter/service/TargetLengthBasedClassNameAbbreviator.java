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
package com.wildbeeslabs.sensiblemetrics.diffy.formatter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.formatter.interfaces.Abbreviator;

import java.util.Objects;

/**
 * Target length based class name {@link Abbreviator} implementation
 */
public class TargetLengthBasedClassNameAbbreviator implements Abbreviator {

    /**
     * Default target length
     */
    private final int targetLength;

    public TargetLengthBasedClassNameAbbreviator(int targetLength) {
        this.targetLength = targetLength;
    }

    public String abbreviate(final String value) {
        StringBuilder buf = new StringBuilder(targetLength);
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("Class name may not be null");
        }

        final int inLen = value.length();
        if (inLen < targetLength) {
            return value;
        }

        int[] dotIndexesArray = new int[MAX_DOTS];
        // a.b.c contains 2 dots but 2+1 parts.
        // see also http://jira.qos.ch/browse/LBCLASSIC-110
        final int[] lengthArray = new int[MAX_DOTS + 1];
        final int dotCount = computeDotIndexes(value, dotIndexesArray);

        // System.out.println();
        // System.out.println("Dot count for [" + className + "] is " + dotCount);
        // if there are not dots than abbreviation is not possible
        if (dotCount == 0) {
            return value;
        }
        // printArray("dotArray: ", dotArray);
        this.computeLengthArray(value, dotIndexesArray, lengthArray, dotCount);
        // printArray("lengthArray: ", lengthArray);
        for (int i = 0; i <= dotCount; i++) {
            if (i == 0) {
                buf.append(value.substring(0, lengthArray[i] - 1));
            } else {
                buf.append(value.substring(dotIndexesArray[i - 1], dotIndexesArray[i - 1] + lengthArray[i]));
            }
            // System.out.println("i=" + i + ", buf=" + buf);
        }
        return buf.toString();
    }

    private static int computeDotIndexes(final String className, final int[] dotArray) {
        int dotCount = 0;
        int k = 0;
        while (true) {
            k = className.indexOf(DOT, k);
            if (k != -1 && dotCount < MAX_DOTS) {
                dotArray[dotCount] = k;
                dotCount++;
                k++;
            } else {
                break;
            }
        }
        return dotCount;
    }

    private void computeLengthArray(final String className, final int[] dotArray, final int[] lengthArray, final int dotCount) {
        int toTrim = className.length() - targetLength;
        // int toTrimAvarage = 0;
        int len;
        for (int i = 0; i < dotCount; i++) {
            int previousDotPosition = -1;
            if (i > 0) {
                previousDotPosition = dotArray[i - 1];
            }
            int available = dotArray[i] - previousDotPosition - 1;
            len = (available < 1) ? available : 1;
            if (toTrim > 0) {
                len = (available < 1) ? available : 1;
            } else {
                len = available;
            }
            toTrim -= (available - len);
            lengthArray[i] = len + 1;
        }
        int lastDotIndex = dotCount - 1;
        lengthArray[dotCount] = className.length() - dotArray[lastDotIndex];
    }
}
