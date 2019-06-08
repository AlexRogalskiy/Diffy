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
package com.wildbeeslabs.sensiblemetrics.diffy.enumeration.validator;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.enumeration.StringValidatorType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.startsWith;


/**
 * {@link StringValidatorType} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
public class StringValidatorTypeTest {

    /**
     * Default {@link ExpectedException} rule
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void test_check_StringValidatorType_ByEnum() {
        assertThat(StringValidatorType.IS_EMPTY.toString(), IsEqual.equalTo("IS_EMPTY"));
        assertThat(StringValidatorType.IS_ALPHA.toString(), IsEqual.equalTo("IS_ALPHA"));
        assertThat(StringValidatorType.IS_ALPHA_NUMERIC.toString(), IsEqual.equalTo("IS_ALPHA_NUMERIC"));
        assertThat(StringValidatorType.IS_ALPHA_NUMERIC_SPACE.toString(), IsEqual.equalTo("IS_ALPHA_NUMERIC_SPACE"));
        assertThat(StringValidatorType.IS_ALPHA_SPACE.toString(), IsEqual.equalTo("IS_ALPHA_SPACE"));
        assertThat(StringValidatorType.IS_ASCII_PRINTABLE.toString(), IsEqual.equalTo("IS_ASCII_PRINTABLE"));
        assertThat(StringValidatorType.IS_BLANK.toString(), IsEqual.equalTo("IS_BLANK"));
        assertThat(StringValidatorType.IS_CONTAIN_ISO_CHAR.toString(), IsEqual.equalTo("IS_CONTAIN_ISO_CHAR"));
        assertThat(StringValidatorType.IS_CREATABLE.toString(), IsEqual.equalTo("IS_CREATABLE"));
        assertThat(StringValidatorType.IS_DIGITS.toString(), IsEqual.equalTo("IS_DIGITS"));
        assertThat(StringValidatorType.IS_LOWER_CASE.toString(), IsEqual.equalTo("IS_LOWER_CASE"));
        assertThat(StringValidatorType.IS_MIXED_CASE.toString(), IsEqual.equalTo("IS_MIXED_CASE"));
        assertThat(StringValidatorType.IS_NOT_BLANK.toString(), IsEqual.equalTo("IS_NOT_BLANK"));
        assertThat(StringValidatorType.IS_NOT_CONTAIN_ISO_CHAR.toString(), IsEqual.equalTo("IS_NOT_CONTAIN_ISO_CHAR"));
        assertThat(StringValidatorType.IS_NULL_OR_EMPTY.toString(), IsEqual.equalTo("IS_NULL_OR_EMPTY"));
        assertThat(StringValidatorType.IS_NUMBER.toString(), IsEqual.equalTo("IS_NUMBER"));
        assertThat(StringValidatorType.IS_NUMERIC.toString(), IsEqual.equalTo("IS_NUMERIC"));
        assertThat(StringValidatorType.IS_NUMERIC_SPACE.toString(), IsEqual.equalTo("IS_NUMERIC_SPACE"));
        assertThat(StringValidatorType.IS_PALINDROME.toString(), IsEqual.equalTo("IS_PALINDROME"));
        assertThat(StringValidatorType.IS_PARSABLE.toString(), IsEqual.equalTo("IS_PARSABLE"));
        assertThat(StringValidatorType.IS_PERMUTATION_PALINDROME.toString(), IsEqual.equalTo("IS_PERMUTATION_PALINDROME"));
        assertThat(StringValidatorType.IS_UNIQUE.toString(), IsEqual.equalTo("IS_UNIQUE"));
        assertThat(StringValidatorType.IS_UPPER_CASE.toString(), IsEqual.equalTo("IS_UPPER_CASE"));
        assertThat(StringValidatorType.IS_WHITESPACE.toString(), IsEqual.equalTo("IS_WHITESPACE"));
    }

    @Test
    public void test_check_StringValidatorType_ByName() {
        assertEquals(StringValidatorType.IS_EMPTY, StringValidatorType.valueOf("IS_EMPTY"));
        assertEquals(StringValidatorType.IS_ALPHA, StringValidatorType.valueOf("IS_ALPHA"));
        assertEquals(StringValidatorType.IS_ALPHA_NUMERIC, StringValidatorType.valueOf("IS_ALPHA_NUMERIC"));
        assertEquals(StringValidatorType.IS_ALPHA_NUMERIC_SPACE, StringValidatorType.valueOf("IS_ALPHA_NUMERIC_SPACE"));
        assertEquals(StringValidatorType.IS_ALPHA_SPACE, StringValidatorType.valueOf("IS_ALPHA_SPACE"));
        assertEquals(StringValidatorType.IS_ASCII_PRINTABLE, StringValidatorType.valueOf("IS_ASCII_PRINTABLE"));
        assertEquals(StringValidatorType.IS_BLANK, StringValidatorType.valueOf("IS_BLANK"));
        assertEquals(StringValidatorType.IS_CONTAIN_ISO_CHAR, StringValidatorType.valueOf("IS_CONTAIN_ISO_CHAR"));
        assertEquals(StringValidatorType.IS_CREATABLE, StringValidatorType.valueOf("IS_CREATABLE"));
        assertEquals(StringValidatorType.IS_DIGITS, StringValidatorType.valueOf("IS_DIGITS"));
        assertEquals(StringValidatorType.IS_LOWER_CASE, StringValidatorType.valueOf("IS_LOWER_CASE"));
        assertEquals(StringValidatorType.IS_NOT_BLANK, StringValidatorType.valueOf("IS_NOT_BLANK"));
        assertEquals(StringValidatorType.IS_NOT_CONTAIN_ISO_CHAR, StringValidatorType.valueOf("IS_NOT_CONTAIN_ISO_CHAR"));
        assertEquals(StringValidatorType.IS_NULL_OR_EMPTY, StringValidatorType.valueOf("IS_NULL_OR_EMPTY"));
        assertEquals(StringValidatorType.IS_NUMBER, StringValidatorType.valueOf("IS_NUMBER"));
        assertEquals(StringValidatorType.IS_NUMERIC, StringValidatorType.valueOf("IS_NUMERIC"));
        assertEquals(StringValidatorType.IS_NUMERIC_SPACE, StringValidatorType.valueOf("IS_NUMERIC_SPACE"));
        assertEquals(StringValidatorType.IS_PALINDROME, StringValidatorType.valueOf("IS_PALINDROME"));
        assertEquals(StringValidatorType.IS_PARSABLE, StringValidatorType.valueOf("IS_PARSABLE"));
        assertEquals(StringValidatorType.IS_PERMUTATION_PALINDROME, StringValidatorType.valueOf("IS_PERMUTATION_PALINDROME"));
        assertEquals(StringValidatorType.IS_UNIQUE, StringValidatorType.valueOf("IS_UNIQUE"));
        assertEquals(StringValidatorType.IS_UPPER_CASE, StringValidatorType.valueOf("IS_UPPER_CASE"));
        assertEquals(StringValidatorType.IS_WHITESPACE, StringValidatorType.valueOf("IS_WHITESPACE"));
    }

    @Test
    public void test_check_StringValidatorType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, StringValidatorType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_StringValidatorType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, StringValidatorType.valueOf(null));
    }
}
