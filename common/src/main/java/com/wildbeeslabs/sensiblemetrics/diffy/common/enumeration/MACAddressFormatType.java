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

import com.wildbeeslabs.sensiblemetrics.diffy.common.interfaces.TriConsumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.BiFunction;

@Getter
@RequiredArgsConstructor
public enum MACAddressFormatType {

    DASH_EVERY_2_DIGITS(MACAddressFormatType::line2Digits),
    COLON_EVERY_2_DIGITS(MACAddressFormatType::colon2Digits),
    DOT_EVERY_2_DIGITS(MACAddressFormatType::point2Digits),
    DOT_EVERY_4_DIGITS(MACAddressFormatType::point4Digits);

    private final TriConsumer<Integer, StringBuilder, BiFunction<Integer, Integer, Integer>> consumer;

    private static void everyDigits(final Integer i, final StringBuilder buff, final BiFunction<Integer, Integer, Integer> rand, final String chr, final Integer digits) {
        if (i % digits == 0) {
            buff.append(chr);
        }
        buff.append(Integer.toHexString(rand.apply(0, 16)));
    }

    private static void line2Digits(final Integer i, final StringBuilder buff, final BiFunction<Integer, Integer, Integer> rand) {
        everyDigits(i, buff, rand, "-", 2);
    }

    private static void colon2Digits(final Integer i, final StringBuilder buff, final BiFunction<Integer, Integer, Integer> rand) {
        everyDigits(i, buff, rand, ":", 2);
    }

    private static void point2Digits(final Integer i, final StringBuilder buff, final BiFunction<Integer, Integer, Integer> rand) {
        everyDigits(i, buff, rand, ".", 2);
    }

    private static void point4Digits(final Integer i, final StringBuilder buff, final BiFunction<Integer, Integer, Integer> rand) {
        everyDigits(i, buff, rand, ".", 4);
    }
}
