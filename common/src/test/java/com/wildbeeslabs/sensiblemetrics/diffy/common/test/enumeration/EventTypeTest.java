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
package com.wildbeeslabs.sensiblemetrics.diffy.common.test.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.common.enumeration.EventType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.startsWith;

/**
 * {@link EventType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class EventTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_EventType_ByEnum() {
        assertThat(EventType.BINARY_MATCHER_EVENT.toString(), IsEqual.equalTo("BINARY_MATCHER_EVENT"));
        assertThat(EventType.COMPARATOR_EVENT.toString(), IsEqual.equalTo("COMPARATOR_EVENT"));
        assertThat(EventType.MATCHER_EVENT.toString(), IsEqual.equalTo("MATCHER_EVENT"));
        assertThat(EventType.VALIDATOR_EVENT.toString(), IsEqual.equalTo("VALIDATOR_EVENT"));
    }

    @Test
    public void test_check_EventType_ByName() {
        assertEquals(EventType.BINARY_MATCHER_EVENT, EventType.valueOf("BINARY_MATCHER_EVENT"));
        assertEquals(EventType.COMPARATOR_EVENT, EventType.valueOf("COMPARATOR_EVENT"));
        assertEquals(EventType.MATCHER_EVENT, EventType.valueOf("MATCHER_EVENT"));
        assertEquals(EventType.VALIDATOR_EVENT, EventType.valueOf("VALIDATOR_EVENT"));
    }

    @Test
    public void test_check_EventType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, EventType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_EventType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, EventType.valueOf(null));
    }
}
