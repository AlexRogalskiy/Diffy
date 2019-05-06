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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.impl.IntConverter;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

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
     * Default {@link IntConverter} instance
     */
    private IntConverter intConverter;

    @Before
    public void setUp() {
        this.intConverter = new IntConverter();
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting invalid integer value")
    public void test_invalidIntValue() {
        // given
        final String intStr = "a56";

        // when
        final Integer intValue = this.getIntConverter().convert(intStr);

        // then
        assertNull(intValue);
    }

    @Test
    @DisplayName("Test converting valid integer value")
    public void test_validIntValue() {
        // given
        final String intStr = "2019";

        // when
        final Integer intValue = this.getIntConverter().convert(intStr);

        // then
        assertNotNull(intValue);
        assertThat(intValue.toString(), IsEqual.equalTo(intStr));
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting empty integer value")
    public void test_emptyIntValue() {
        // given
        final String intStr = "";

        // when
        this.getIntConverter().convert(intStr);
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting nullable integer value")
    public void test_nullableIntValue() {
        // given
        final String intStr = null;

        // when
        this.getIntConverter().convert(intStr);
    }
}
