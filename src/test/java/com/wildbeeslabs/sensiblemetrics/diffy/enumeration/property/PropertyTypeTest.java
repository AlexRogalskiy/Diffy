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

import com.wildbeeslabs.sensiblemetrics.diffy.property.enumeration.PropertyType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.startsWith;

/**
 * {@link PropertyType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class PropertyTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_PropertyType_ByEnum() {
        assertThat(PropertyType.METHOD.toString(), IsEqual.equalTo("METHOD"));
        assertThat(PropertyType.FIELD.toString(), IsEqual.equalTo("FIELD"));
        assertThat(PropertyType.GENERIC.toString(), IsEqual.equalTo("GENERIC"));
    }

    @Test
    public void test_check_PropertyType_ByName() {
        assertEquals(PropertyType.METHOD, PropertyType.valueOf("METHOD"));
        assertEquals(PropertyType.FIELD, PropertyType.valueOf("FIELD"));
        assertEquals(PropertyType.GENERIC, PropertyType.valueOf("GENERIC"));
    }

    @Test
    public void test_check_PropertyType_ByPropertyType() {
        assertEquals(PropertyType.FIELD, PropertyType.fromName("FIELD"));
        assertEquals(PropertyType.METHOD, PropertyType.fromName("METHOD"));
        assertEquals(PropertyType.GENERIC, PropertyType.fromName("GENERIC"));
    }

    @Test
    public void test_check_PropertyType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, PropertyType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_PropertyType_ByEmptyPropertyName() {
        assertNull("Property type is not null", PropertyType.fromName(null));
    }

    @Test
    public void test_check_PropertyType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, PropertyType.valueOf(null));
    }
}
