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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherStateEventType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.startsWith;

/**
 * {@link MatcherStateEventType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class MatcherEventTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_MatcherEventType_ByEnum() {
        assertThat(MatcherStateEventType.MATCH_COMPLETE.toString(), IsEqual.equalTo("MATCH_COMPLETE"));
        assertThat(MatcherStateEventType.MATCH_ERROR.toString(), IsEqual.equalTo("MATCH_ERROR"));
        assertThat(MatcherStateEventType.MATCH_START.toString(), IsEqual.equalTo("MATCH_START"));
        assertThat(MatcherStateEventType.MATCH_SUCCESS.toString(), IsEqual.equalTo("MATCH_SUCCESS"));
        assertThat(MatcherStateEventType.MATCH_AFTER.toString(), IsEqual.equalTo("MATCH_AFTER"));
        assertThat(MatcherStateEventType.MATCH_BEFORE.toString(), IsEqual.equalTo("MATCH_BEFORE"));
        assertThat(MatcherStateEventType.MATCH_FAILURE.toString(), IsEqual.equalTo("MATCH_FAILURE"));
        assertThat(MatcherStateEventType.MATCH_SKIP.toString(), IsEqual.equalTo("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByName() {
        assertEquals(MatcherStateEventType.MATCH_COMPLETE, MatcherStateEventType.valueOf("MATCH_COMPLETE"));
        assertEquals(MatcherStateEventType.MATCH_ERROR, MatcherStateEventType.valueOf("MATCH_ERROR"));
        assertEquals(MatcherStateEventType.MATCH_START, MatcherStateEventType.valueOf("MATCH_START"));
        assertEquals(MatcherStateEventType.MATCH_SUCCESS, MatcherStateEventType.valueOf("MATCH_SUCCESS"));
        assertEquals(MatcherStateEventType.MATCH_AFTER, MatcherStateEventType.valueOf("MATCH_AFTER"));
        assertEquals(MatcherStateEventType.MATCH_BEFORE, MatcherStateEventType.valueOf("MATCH_BEFORE"));
        assertEquals(MatcherStateEventType.MATCH_FAILURE, MatcherStateEventType.valueOf("MATCH_FAILURE"));
        assertEquals(MatcherStateEventType.MATCH_SKIP, MatcherStateEventType.valueOf("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByPropertyType() {
        assertEquals(MatcherStateEventType.MATCH_COMPLETE, MatcherStateEventType.fromName("MATCH_COMPLETE"));
        assertEquals(MatcherStateEventType.MATCH_ERROR, MatcherStateEventType.fromName("MATCH_ERROR"));
        assertEquals(MatcherStateEventType.MATCH_START, MatcherStateEventType.fromName("MATCH_START"));
        assertEquals(MatcherStateEventType.MATCH_SUCCESS, MatcherStateEventType.fromName("MATCH_SUCCESS"));
        assertEquals(MatcherStateEventType.MATCH_AFTER, MatcherStateEventType.fromName("MATCH_AFTER"));
        assertEquals(MatcherStateEventType.MATCH_BEFORE, MatcherStateEventType.fromName("MATCH_BEFORE"));
        assertEquals(MatcherStateEventType.MATCH_FAILURE, MatcherStateEventType.fromName("MATCH_FAILURE"));
        assertEquals(MatcherStateEventType.MATCH_SKIP, MatcherStateEventType.fromName("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByBoolean() {
        assertEquals(MatcherStateEventType.MATCH_SUCCESS, MatcherStateEventType.fromSuccess(true));
        assertEquals(MatcherStateEventType.MATCH_FAILURE, MatcherStateEventType.fromSuccess(false));

        assertEquals(MatcherStateEventType.MATCH_BEFORE, MatcherStateEventType.fromBefore(true));
        assertEquals(MatcherStateEventType.MATCH_AFTER, MatcherStateEventType.fromBefore(false));

        assertEquals(MatcherStateEventType.MATCH_START, MatcherStateEventType.fromStart(true));
        assertEquals(MatcherStateEventType.MATCH_COMPLETE, MatcherStateEventType.fromStart(false));
    }

    @Test
    public void test_check_MatcherEventType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherStateEventType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_MatcherEventType_ByNullableName() {
        assertNull(MatcherStateEventType.fromName(null));
    }

    @Test
    public void test_check_MatcherEventType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherStateEventType.valueOf(null));
    }
}
