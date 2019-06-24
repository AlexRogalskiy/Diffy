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
package com.wildbeeslabs.sensiblemetrics.diffy.converter.test.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.service.BigDecimalConverter;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.startsWith;

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
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Default {@link BigDecimalConverter} instance
     */
    private BigDecimalConverter bigDecimalConverter;

    @Before
    public void setUp() {
        this.bigDecimalConverter = new BigDecimalConverter();
    }

    @Test
    @DisplayName("Test converting invalid big decimal value")
    public void test_invalidBigDecimal_Converter() {
        // given
        final String value = "4,5";

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        final BigDecimal result = this.getBigDecimalConverter().convert(value);

        // then
        assertNotNull(result);
        assertThat(result.toString(), IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting valid big decimal value")
    public void test_validBigDecimal_Converter() {
        // given
        final String value = "4.5";

        // when
        final BigDecimal result = this.getBigDecimalConverter().convert(value);

        // then
        assertNotNull(result);
        assertThat(result.toString(), IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting valid big decimal value by post converter")
    public void test_validBigDecimal_by_postConverter() {
        // given
        final String value = "443.345";

        // when
        final String result = this.getBigDecimalConverter().andThen(String::valueOf).convert(value);

        // then
        assertNotNull(result);
        assertThat(result, IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting valid big decimal value by pre converter")
    public void test_validBigDecimal_by_preConverter() {
        // given
        final Long value = 443_345L;

        // when
        final BigDecimal result = this.getBigDecimalConverter().compose(String::valueOf).convert(value);

        // then
        assertNotNull(result);
        assertThat(result.toString(), IsEqual.equalTo(value.toString()));
    }

    @Test
    @DisplayName("Test converting valid big decimal value by identity converter")
    public void test_validBigDecimal_by_identityConverter() {
        // given
        final BigDecimal value = BigDecimal.TEN;

        // then
        final BigDecimal result = Converter.<BigDecimal>identity().convert(value);

        // then
        assertNotNull(result);
        assertThat(result, IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting empty big decimal value")
    public void test_emptyBigDecimal_Converter() {
        // given
        final String value = "";

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        this.getBigDecimalConverter().convert(value);
    }

    @Test
    @DisplayName("Test converting nullable big decimal value")
    public void test_nullableBigDecimal_Converter() {
        // given
        final String value = null;

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        this.getBigDecimalConverter().convert(value);
    }
}
