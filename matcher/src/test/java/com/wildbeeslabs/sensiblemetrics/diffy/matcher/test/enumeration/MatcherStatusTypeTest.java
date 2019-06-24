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
package com.wildbeeslabs.sensiblemetrics.diffy.matcher.test.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherStatusType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.startsWith;

/**
 * {@link MatcherStatusType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class MatcherStatusTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_MatcherStatusType_ByEnum() {
        assertThat(MatcherStatusType.ENABLE.toString(), IsEqual.equalTo("ENABLE"));
        assertThat(MatcherStatusType.DISABLE.toString(), IsEqual.equalTo("DISABLE"));
    }

    @Test
    public void test_check_MatcherStatusType_ByName() {
        assertEquals(MatcherStatusType.ENABLE, MatcherStatusType.valueOf("ENABLE"));
        assertEquals(MatcherStatusType.DISABLE, MatcherStatusType.valueOf("DISABLE"));
    }

    @Test
    public void test_check_MatcherStatusType_ByBoolean() {
        assertEquals(MatcherStatusType.ENABLE, MatcherStatusType.from(true));
        assertEquals(MatcherStatusType.DISABLE, MatcherStatusType.from(false));

        assertTrue(MatcherStatusType.ENABLE.isEnable());
        assertFalse(MatcherStatusType.DISABLE.isEnable());
    }

    @Test
    public void test_check_MatcherStatusType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherStatusType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_MatcherStatusType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherStatusType.valueOf(null));
    }
}
