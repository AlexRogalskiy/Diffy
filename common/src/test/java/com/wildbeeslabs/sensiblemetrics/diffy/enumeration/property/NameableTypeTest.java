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
package com.wildbeeslabs.sensiblemetrics.diffy.enumeration.property;

import com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration.NameableType;
import com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration.PropertyType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.startsWith;

/**
 * {@link NameableType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class NameableTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_NameableType_ByEnum() {
        assertThat(NameableType.METHOD.toString(), IsEqual.equalTo("METHOD"));
        assertThat(NameableType.CLASS.toString(), IsEqual.equalTo("CLASS"));
        assertThat(NameableType.FIELD.toString(), IsEqual.equalTo("FIELD"));
        assertThat(NameableType.GENERIC.toString(), IsEqual.equalTo("GENERIC"));
    }

    @Test
    public void test_check_NameableType_ByName() {
        assertEquals(NameableType.METHOD, NameableType.valueOf("METHOD"));
        assertEquals(NameableType.CLASS, NameableType.valueOf("CLASS"));
        assertEquals(NameableType.FIELD, NameableType.valueOf("FIELD"));
        assertEquals(NameableType.GENERIC, NameableType.valueOf("GENERIC"));
    }

    @Test
    public void test_check_NameableType_ByPropertyType() {
        assertEquals(NameableType.FIELD, NameableType.fromProperty(PropertyType.FIELD));
        assertEquals(NameableType.METHOD, NameableType.fromProperty(PropertyType.METHOD));
        assertEquals(NameableType.GENERIC, NameableType.fromProperty(PropertyType.GENERIC));
    }

    @Test
    public void test_check_NameableType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, NameableType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_NameableType_ByNullablePropertyName() {
        assertEquals(NameableType.CLASS, NameableType.fromProperty(null));
    }

    @Test
    public void test_check_NameableType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, NameableType.valueOf(null));
    }
}
