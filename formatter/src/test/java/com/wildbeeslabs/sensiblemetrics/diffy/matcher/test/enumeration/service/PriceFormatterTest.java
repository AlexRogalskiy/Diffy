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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.test.enumeration.service;

import com.wildbeeslabs.sensiblemetrics.diffy.formatter.service.PriceFormatter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Price formatter unit test
 */
public class PriceFormatterTest {

    @Test
    public void testFormat() {
        // when
        final PriceFormatter pf = PriceFormatter.getInstance();

        // then
        assertEquals("10.00", pf.format(10));
        assertEquals("10.00", pf.format(10.0));
        assertEquals("10.10", pf.format(10.1));
        assertEquals("10.01", pf.format(10.01));
        assertEquals("10.01", pf.format(10.0100));
        assertEquals("10.01", pf.format(10.0123));
        assertEquals("10.02", pf.format(10.0199));
        assertEquals("1234567890.01", pf.format(1234567890.01));
    }
}
