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
package com.wildbeeslabs.sensiblemetrics.diffy.converter;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.impl.BigDecimalConverter;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * {@link BigDecimalConverter} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Getter
public class BigDecimalConverterTest {

    /**
     * Default {@link BigDecimalConverter} instance
     */
    private BigDecimalConverter bigDecimalConverter;

    @Before
    public void setUp() {
        this.bigDecimalConverter = new BigDecimalConverter();
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting invalid big decimal value")
    public void test_invalidBigDecimalValue() {
        // given
        final String bigDecimalStr = "4,5";

        // when
        final BigDecimal bigDecimal = this.getBigDecimalConverter().convert(bigDecimalStr);

        // then
        assertNotNull(bigDecimal);
        assertThat(bigDecimal.toString(), IsEqual.equalTo(bigDecimalStr));
    }

    @Test
    @DisplayName("Test converting valid big decimal value")
    public void test_validBigDecimalValue() {
        // given
        final String bigDecimalStr = "4.5";

        // when
        final BigDecimal bigDecimal = this.getBigDecimalConverter().convert(bigDecimalStr);

        // then
        assertNotNull(bigDecimal);
        assertThat(bigDecimal.toString(), IsEqual.equalTo(bigDecimalStr));
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting empty big decimal value")
    public void test_emptyBigDecimalValue() {
        // given
        final String bigDecimalStr = "";

        // when
        this.getBigDecimalConverter().convert(bigDecimalStr);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test converting nullable big decimal value")
    public void test_nullableBigDecimalValue() {
        // given
        final String bigDecimalStr = null;

        // when
        this.getBigDecimalConverter().convert(bigDecimalStr);
    }
}
