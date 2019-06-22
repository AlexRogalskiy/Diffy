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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.test.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.matcher.enumeration.MatcherModeType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Matchers.startsWith;

/**
 * {@link MatcherModeType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class MatcherModeTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_MatcherModeType_ByEnum() {
        assertThat(MatcherModeType.STRICT.toString(), IsEqual.equalTo("STRICT"));
        assertThat(MatcherModeType.SILENT.toString(), IsEqual.equalTo("SILENT"));
    }

    @Test
    public void test_check_MatcherModeType_ByName() {
        assertEquals(MatcherModeType.STRICT, MatcherModeType.valueOf("STRICT"));
        assertEquals(MatcherModeType.SILENT, MatcherModeType.valueOf("SILENT"));
    }

    @Test
    public void test_check_MatcherModeType_ByStatusType() {
        assertTrue(MatcherModeType.STRICT.isStrict());
        assertFalse(MatcherModeType.SILENT.isStrict());

        assertTrue(MatcherModeType.STRICT.isEnable());
        assertFalse(MatcherModeType.SILENT.isEnable());
    }

    @Test
    public void test_check_MatcherModeType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherModeType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_MatcherModeType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherModeType.valueOf(null));
    }
}
