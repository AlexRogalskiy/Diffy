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

import com.wildbeeslabs.sensiblemetrics.diffy.converter.impl.DateConverter;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * {@link DateConverter} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Getter
public class DateConverterTest {

    /**
     * Default date format pattern
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy/mm/dd";

    /**
     * Default {@link DateConverter} instance
     */
    private DateConverter dateConverter;

    @Before
    public void setUp() {
        this.dateConverter = new DateConverter(DEFAULT_DATE_FORMAT);
    }

    @Test
    @DisplayName("Test converting invalid date value")
    public void test_invalidDateValue_Converter() {
        // given
        final String dateStr = "a3563/56/56";

        // when
        final Date date = this.getDateConverter().convert(dateStr);

        // then
        assertNull(date);
    }

    @Test
    @DisplayName("Test converting valid date value")
    public void test_validDateValue_Converter() {
        // given
        final String dateStr = "2019/12/12";

        // when
        final Date date = this.getDateConverter().convert(dateStr);

        // then
        assertNotNull(date);
        assertThat(format(date, DEFAULT_DATE_FORMAT), IsEqual.equalTo(dateStr));
    }

    @Test
    @DisplayName("Test converting empty date value")
    public void test_emptyDateValue_Converter() {
        // given
        final String dateStr = "";

        // when
        this.getDateConverter().convert(dateStr);
    }

    @Test(expected = NullPointerException.class)
    @DisplayName("Test converting nullable date value")
    public void test_nullableDateValue_Converter() {
        // given
        final String dateStr = null;

        // when
        this.getDateConverter().convert(dateStr);
    }

    private String format(final Date date, final String pattern) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}