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

import com.wildbeeslabs.sensiblemetrics.diffy.matcher.enumeration.MatcherEventType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.startsWith;

/**
 * {@link MatcherEventType} unit test
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
        assertThat(MatcherEventType.MATCH_COMPLETE.toString(), IsEqual.equalTo("MATCH_COMPLETE"));
        assertThat(MatcherEventType.MATCH_ERROR.toString(), IsEqual.equalTo("MATCH_ERROR"));
        assertThat(MatcherEventType.MATCH_START.toString(), IsEqual.equalTo("MATCH_START"));
        assertThat(MatcherEventType.MATCH_SUCCESS.toString(), IsEqual.equalTo("MATCH_SUCCESS"));
        assertThat(MatcherEventType.MATCH_AFTER.toString(), IsEqual.equalTo("MATCH_AFTER"));
        assertThat(MatcherEventType.MATCH_BEFORE.toString(), IsEqual.equalTo("MATCH_BEFORE"));
        assertThat(MatcherEventType.MATCH_FAILURE.toString(), IsEqual.equalTo("MATCH_FAILURE"));
        assertThat(MatcherEventType.MATCH_SKIP.toString(), IsEqual.equalTo("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByName() {
        assertEquals(MatcherEventType.MATCH_COMPLETE, MatcherEventType.valueOf("MATCH_COMPLETE"));
        assertEquals(MatcherEventType.MATCH_ERROR, MatcherEventType.valueOf("MATCH_ERROR"));
        assertEquals(MatcherEventType.MATCH_START, MatcherEventType.valueOf("MATCH_START"));
        assertEquals(MatcherEventType.MATCH_SUCCESS, MatcherEventType.valueOf("MATCH_SUCCESS"));
        assertEquals(MatcherEventType.MATCH_AFTER, MatcherEventType.valueOf("MATCH_AFTER"));
        assertEquals(MatcherEventType.MATCH_BEFORE, MatcherEventType.valueOf("MATCH_BEFORE"));
        assertEquals(MatcherEventType.MATCH_FAILURE, MatcherEventType.valueOf("MATCH_FAILURE"));
        assertEquals(MatcherEventType.MATCH_SKIP, MatcherEventType.valueOf("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByPropertyType() {
        assertEquals(MatcherEventType.MATCH_COMPLETE, MatcherEventType.fromName("MATCH_COMPLETE"));
        assertEquals(MatcherEventType.MATCH_ERROR, MatcherEventType.fromName("MATCH_ERROR"));
        assertEquals(MatcherEventType.MATCH_START, MatcherEventType.fromName("MATCH_START"));
        assertEquals(MatcherEventType.MATCH_SUCCESS, MatcherEventType.fromName("MATCH_SUCCESS"));
        assertEquals(MatcherEventType.MATCH_AFTER, MatcherEventType.fromName("MATCH_AFTER"));
        assertEquals(MatcherEventType.MATCH_BEFORE, MatcherEventType.fromName("MATCH_BEFORE"));
        assertEquals(MatcherEventType.MATCH_FAILURE, MatcherEventType.fromName("MATCH_FAILURE"));
        assertEquals(MatcherEventType.MATCH_SKIP, MatcherEventType.fromName("MATCH_SKIP"));
    }

    @Test
    public void test_check_MatcherEventType_ByBoolean() {
        assertEquals(MatcherEventType.MATCH_SUCCESS, MatcherEventType.fromSuccess(true));
        assertEquals(MatcherEventType.MATCH_FAILURE, MatcherEventType.fromSuccess(false));

        assertEquals(MatcherEventType.MATCH_BEFORE, MatcherEventType.fromBefore(true));
        assertEquals(MatcherEventType.MATCH_AFTER, MatcherEventType.fromBefore(false));

        assertEquals(MatcherEventType.MATCH_START, MatcherEventType.fromStart(true));
        assertEquals(MatcherEventType.MATCH_COMPLETE, MatcherEventType.fromStart(false));
    }

    @Test
    public void test_check_MatcherEventType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherEventType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_MatcherEventType_ByNullableName() {
        assertNull(MatcherEventType.fromName(null));
    }

    @Test
    public void test_check_MatcherEventType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, MatcherEventType.valueOf(null));
    }
}
