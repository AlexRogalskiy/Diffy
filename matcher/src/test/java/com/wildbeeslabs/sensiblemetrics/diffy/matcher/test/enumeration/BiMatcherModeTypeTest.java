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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.BiMatcherModeType;
import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherStatusType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.startsWith;

/**
 * {@link BiMatcherModeType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class BiMatcherModeTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_BiMatcherModeType_ByEnum() {
        assertThat(BiMatcherModeType.STRICT.toString(), IsEqual.equalTo("STRICT"));
        assertThat(BiMatcherModeType.LENIENT.toString(), IsEqual.equalTo("LENIENT"));
        assertThat(BiMatcherModeType.SEALED.toString(), IsEqual.equalTo("SEALED"));
        assertThat(BiMatcherModeType.SILENT.toString(), IsEqual.equalTo("SILENT"));
    }

    @Test
    public void test_check_BiMatcherModeType_ByName() {
        assertEquals(BiMatcherModeType.STRICT, BiMatcherModeType.valueOf("STRICT"));
        assertEquals(BiMatcherModeType.LENIENT, BiMatcherModeType.valueOf("LENIENT"));
        assertEquals(BiMatcherModeType.SEALED, BiMatcherModeType.valueOf("SEALED"));
        assertEquals(BiMatcherModeType.SILENT, BiMatcherModeType.valueOf("SILENT"));
    }

    @Test
    public void test_check_BiMatcherModeType_ByPropertyType() {
        assertEquals(BiMatcherModeType.STRICT, BiMatcherModeType.STRICT.fromExtension(true));
        assertEquals(BiMatcherModeType.SEALED, BiMatcherModeType.STRICT.fromExtension(false));
        assertEquals(BiMatcherModeType.STRICT, BiMatcherModeType.STRICT.fromStatus(MatcherStatusType.ENABLE));
        assertEquals(BiMatcherModeType.LENIENT, BiMatcherModeType.STRICT.fromStatus(MatcherStatusType.DISABLE));

        assertEquals(BiMatcherModeType.LENIENT, BiMatcherModeType.LENIENT.fromExtension(true));
        assertEquals(BiMatcherModeType.SILENT, BiMatcherModeType.LENIENT.fromExtension(false));
        assertEquals(BiMatcherModeType.STRICT, BiMatcherModeType.LENIENT.fromStatus(MatcherStatusType.ENABLE));
        assertEquals(BiMatcherModeType.LENIENT, BiMatcherModeType.LENIENT.fromStatus(MatcherStatusType.DISABLE));

        assertEquals(BiMatcherModeType.STRICT, BiMatcherModeType.SEALED.fromExtension(true));
        assertEquals(BiMatcherModeType.SEALED, BiMatcherModeType.SEALED.fromExtension(false));
        assertEquals(BiMatcherModeType.SEALED, BiMatcherModeType.SEALED.fromStatus(MatcherStatusType.ENABLE));
        assertEquals(BiMatcherModeType.SILENT, BiMatcherModeType.SEALED.fromStatus(MatcherStatusType.DISABLE));

        assertEquals(BiMatcherModeType.LENIENT, BiMatcherModeType.SILENT.fromExtension(true));
        assertEquals(BiMatcherModeType.SILENT, BiMatcherModeType.SILENT.fromExtension(false));
        assertEquals(BiMatcherModeType.SEALED, BiMatcherModeType.SILENT.fromStatus(MatcherStatusType.ENABLE));
        assertEquals(BiMatcherModeType.SILENT, BiMatcherModeType.SILENT.fromStatus(MatcherStatusType.DISABLE));
    }

    @Test
    public void test_check_BiMatcherModeType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, BiMatcherModeType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_BiMatcherModeType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, BiMatcherModeType.valueOf(null));
    }
}
