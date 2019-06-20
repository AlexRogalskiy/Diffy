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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.iface.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.impl.IntConverter;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.ConvertOperationException;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.Matchers.startsWith;

/**
 * {@link IntConverter} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Getter
public class IntConverterTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Default {@link IntConverter} instance
     */
    private IntConverter intConverter;

    @Before
    public void setUp() {
        this.intConverter = new IntConverter();
    }

    @Test
    @DisplayName("Test converting invalid integer value")
    public void test_invalidInt_Converter() {
        // given
        final String value = "a56";

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        final Integer intValue = this.getIntConverter().convert(value);

        // then
        assertNull(intValue);
    }

    @Test
    @DisplayName("Test converting valid integer value")
    public void test_validInt_Converter() {
        // given
        final String value = "2019";

        // when
        final Integer intValue = this.getIntConverter().convert(value);

        // then
        assertNotNull(intValue);
        assertThat(intValue.toString(), IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting valid integer value by post converter")
    public void test_validInt_by_postConverter() {
        // given
        final String value = "2019";

        // when
        final String result = this.getIntConverter().andThen(String::valueOf).convert(value);

        // then
        assertNotNull(result);
        assertThat(result, IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting valid integer value by pre converter")
    public void test_validInt_by_preConverter() {
        // given
        final BigDecimal value = BigDecimal.ONE;

        // when
        final Integer result = this.getIntConverter().compose(BigDecimal::toString).convert(value);

        // then
        assertNotNull(result);
        assertThat(result, IsEqual.equalTo(result));
    }

    @Test
    @DisplayName("Test converting valid integer value by identity converter")
    public void test_validInt_by_identityConverter() {
        // given
        final Integer value = 5;

        // then
        final Integer result = Converter.<Integer>identity().convert(value);

        // then
        assertNotNull(result);
        assertThat(result, IsEqual.equalTo(value));
    }

    @Test
    @DisplayName("Test converting empty integer value")
    public void test_emptyInt_Converter() {
        // given
        final String value = "";

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        this.getIntConverter().convert(value);
    }

    @Test
    @DisplayName("Test converting nullable integer value")
    public void test_nullableInt_Converter() {
        // given
        final String value = null;

        // then
        thrown.expect(ConvertOperationException.class);
        thrown.expectMessage(startsWith("cannot process convert operation"));

        // when
        this.getIntConverter().convert(value);
    }
}
