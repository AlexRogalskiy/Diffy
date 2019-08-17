package com.wildbeeslabs.sensiblemetrics.diffy.converter.test.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.service.StringToUUIDConverter;
import org.junit.Test;

import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StringToUUIDConverterTest {

    private StringToUUIDConverter stringToUUIDConverter;

    @Test
    public void test_convert_String_To_UUID_whenPassed_Valid_Message() {
        // given
        final String source = "d87c5036-972b-490d-8ac1-e017232192a8";
        final UUID expected = UUID.fromString(source);

        // when
        final UUID actual = this.stringToUUIDConverter.convert(source);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void test_convert_String_To_UUID_whenPassed_NULL() {
        // when
        final UUID actual = this.stringToUUIDConverter.convert(null);

        // then
        assertNull(actual);
    }

    @Test
    public void test_convert_String_To_UUID_whenPassed_EMPTY() {
        // when
        final UUID actual = this.stringToUUIDConverter.convert(EMPTY);

        // then
        assertNull(actual);
    }
}
