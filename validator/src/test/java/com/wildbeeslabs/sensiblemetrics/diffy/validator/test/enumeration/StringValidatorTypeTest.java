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
package com.wildbeeslabs.sensiblemetrics.diffy.validator.test.enumeration;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.enumeration.StringMatcherType;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.startsWith;


/**
 * {@link StringMatcherType} unit test
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
        assertThat(StringMatcherType.IS_EMPTY.toString(), IsEqual.equalTo("IS_EMPTY"));
        assertThat(StringMatcherType.IS_ALPHA.toString(), IsEqual.equalTo("IS_ALPHA"));
        assertThat(StringMatcherType.IS_ALPHA_NUMERIC.toString(), IsEqual.equalTo("IS_ALPHA_NUMERIC"));
        assertThat(StringMatcherType.IS_ALPHA_NUMERIC_SPACE.toString(), IsEqual.equalTo("IS_ALPHA_NUMERIC_SPACE"));
        assertThat(StringMatcherType.IS_ALPHA_SPACE.toString(), IsEqual.equalTo("IS_ALPHA_SPACE"));
        assertThat(StringMatcherType.IS_ASCII_PRINTABLE.toString(), IsEqual.equalTo("IS_ASCII_PRINTABLE"));
        assertThat(StringMatcherType.IS_BLANK.toString(), IsEqual.equalTo("IS_BLANK"));
        assertThat(StringMatcherType.IS_CONTAIN_ISO_CHAR.toString(), IsEqual.equalTo("IS_CONTAIN_ISO_CHAR"));
        assertThat(StringMatcherType.IS_CREATABLE.toString(), IsEqual.equalTo("IS_CREATABLE"));
        assertThat(StringMatcherType.IS_DIGITS.toString(), IsEqual.equalTo("IS_DIGITS"));
        assertThat(StringMatcherType.IS_LOWER_CASE.toString(), IsEqual.equalTo("IS_LOWER_CASE"));
        assertThat(StringMatcherType.IS_MIXED_CASE.toString(), IsEqual.equalTo("IS_MIXED_CASE"));
        assertThat(StringMatcherType.IS_NOT_BLANK.toString(), IsEqual.equalTo("IS_NOT_BLANK"));
        assertThat(StringMatcherType.IS_NOT_CONTAIN_ISO_CHAR.toString(), IsEqual.equalTo("IS_NOT_CONTAIN_ISO_CHAR"));
        assertThat(StringMatcherType.IS_NULL_OR_EMPTY.toString(), IsEqual.equalTo("IS_NULL_OR_EMPTY"));
        assertThat(StringMatcherType.IS_NUMBER.toString(), IsEqual.equalTo("IS_NUMBER"));
        assertThat(StringMatcherType.IS_NUMERIC.toString(), IsEqual.equalTo("IS_NUMERIC"));
        assertThat(StringMatcherType.IS_NUMERIC_SPACE.toString(), IsEqual.equalTo("IS_NUMERIC_SPACE"));
        assertThat(StringMatcherType.IS_PALINDROME.toString(), IsEqual.equalTo("IS_PALINDROME"));
        assertThat(StringMatcherType.IS_PARSABLE.toString(), IsEqual.equalTo("IS_PARSABLE"));
        assertThat(StringMatcherType.IS_PERMUTATION_PALINDROME.toString(), IsEqual.equalTo("IS_PERMUTATION_PALINDROME"));
        assertThat(StringMatcherType.IS_UNIQUE.toString(), IsEqual.equalTo("IS_UNIQUE"));
        assertThat(StringMatcherType.IS_UPPER_CASE.toString(), IsEqual.equalTo("IS_UPPER_CASE"));
        assertThat(StringMatcherType.IS_WHITESPACE.toString(), IsEqual.equalTo("IS_WHITESPACE"));
    }

    @Test
    public void test_check_StringValidatorType_ByName() {
        assertEquals(StringMatcherType.IS_EMPTY, StringMatcherType.valueOf("IS_EMPTY"));
        assertEquals(StringMatcherType.IS_ALPHA, StringMatcherType.valueOf("IS_ALPHA"));
        assertEquals(StringMatcherType.IS_ALPHA_NUMERIC, StringMatcherType.valueOf("IS_ALPHA_NUMERIC"));
        assertEquals(StringMatcherType.IS_ALPHA_NUMERIC_SPACE, StringMatcherType.valueOf("IS_ALPHA_NUMERIC_SPACE"));
        assertEquals(StringMatcherType.IS_ALPHA_SPACE, StringMatcherType.valueOf("IS_ALPHA_SPACE"));
        assertEquals(StringMatcherType.IS_ASCII_PRINTABLE, StringMatcherType.valueOf("IS_ASCII_PRINTABLE"));
        assertEquals(StringMatcherType.IS_BLANK, StringMatcherType.valueOf("IS_BLANK"));
        assertEquals(StringMatcherType.IS_CONTAIN_ISO_CHAR, StringMatcherType.valueOf("IS_CONTAIN_ISO_CHAR"));
        assertEquals(StringMatcherType.IS_CREATABLE, StringMatcherType.valueOf("IS_CREATABLE"));
        assertEquals(StringMatcherType.IS_DIGITS, StringMatcherType.valueOf("IS_DIGITS"));
        assertEquals(StringMatcherType.IS_LOWER_CASE, StringMatcherType.valueOf("IS_LOWER_CASE"));
        assertEquals(StringMatcherType.IS_NOT_BLANK, StringMatcherType.valueOf("IS_NOT_BLANK"));
        assertEquals(StringMatcherType.IS_NOT_CONTAIN_ISO_CHAR, StringMatcherType.valueOf("IS_NOT_CONTAIN_ISO_CHAR"));
        assertEquals(StringMatcherType.IS_NULL_OR_EMPTY, StringMatcherType.valueOf("IS_NULL_OR_EMPTY"));
        assertEquals(StringMatcherType.IS_NUMBER, StringMatcherType.valueOf("IS_NUMBER"));
        assertEquals(StringMatcherType.IS_NUMERIC, StringMatcherType.valueOf("IS_NUMERIC"));
        assertEquals(StringMatcherType.IS_NUMERIC_SPACE, StringMatcherType.valueOf("IS_NUMERIC_SPACE"));
        assertEquals(StringMatcherType.IS_PALINDROME, StringMatcherType.valueOf("IS_PALINDROME"));
        assertEquals(StringMatcherType.IS_PARSABLE, StringMatcherType.valueOf("IS_PARSABLE"));
        assertEquals(StringMatcherType.IS_PERMUTATION_PALINDROME, StringMatcherType.valueOf("IS_PERMUTATION_PALINDROME"));
        assertEquals(StringMatcherType.IS_UNIQUE, StringMatcherType.valueOf("IS_UNIQUE"));
        assertEquals(StringMatcherType.IS_UPPER_CASE, StringMatcherType.valueOf("IS_UPPER_CASE"));
        assertEquals(StringMatcherType.IS_WHITESPACE, StringMatcherType.valueOf("IS_WHITESPACE"));
    }

    @Test
    public void test_check_StringValidatorType_ByUndefinedName() {
        // when
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, StringMatcherType.valueOf("UNDEFINED"));
    }

    @Test
    public void test_check_StringValidatorType_ByNullableEnumName() {
        // when
        thrown.expect(NullPointerException.class);
        thrown.expectMessage(startsWith("No enum constant"));

        // then
        assertEquals(null, StringMatcherType.valueOf(null));
    }
}
