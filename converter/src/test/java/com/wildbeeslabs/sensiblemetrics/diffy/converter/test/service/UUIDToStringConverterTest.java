package com.wildbeeslabs.sensiblemetrics.diffy.converter.test.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.service.UUIDToStringConverter;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UUIDToStringConverterTest {

    private UUIDToStringConverter uuidToStringConverter;

    @Test
    public void test_convert_UUID_To_String_whenPassed_Valid_Message() {
        // given
        final String expected = "d87c5036-972b-490d-8ac1-e017232192a8";
        final UUID source = UUID.fromString(expected);

        // when
        final String actual = this.uuidToStringConverter.convert(source);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void test_convert_UUID_To_String_whenPassed_NULL() {
        // when
        final String actual = this.uuidToStringConverter.convert(null);

        // then
        assertNull(actual);
    }
}
