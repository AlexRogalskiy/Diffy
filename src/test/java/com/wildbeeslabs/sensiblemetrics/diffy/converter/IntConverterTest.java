package com.wildbeeslabs.sensiblemetrics.diffy.converter;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.impl.IntConverter;
import lombok.Getter;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

/**
 * {@link IntConverter} unit test
 *
 * @author Alexander Rogalskiy
 * @version 1.1
 * @since 1.0
 */
@Getter
public class IntConverterTest {

    /**
     * Default {@link IntConverter} instance
     */
    private IntConverter intConverter;

    @Before
    public void setUp() {
        this.intConverter = new IntConverter();
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting invalid integer value")
    public void test_invalidIntValue() {
        // given
        final String intStr = "a56";

        // when
        final Integer intValue = this.getIntConverter().convert(intStr);

        // then
        assertNull(intValue);
    }

    @Test
    @DisplayName("Test converting valid integer value")
    public void test_validIntValue() {
        // given
        final String intStr = "2019";

        // when
        final Integer intValue = this.getIntConverter().convert(intStr);

        // then
        assertNotNull(intValue);
        assertThat(intValue.toString(), IsEqual.equalTo(intStr));
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting empty integer value")
    public void test_emptyIntValue() {
        // given
        final String intStr = "";

        // when
        this.getIntConverter().convert(intStr);
    }

    @Test(expected = NumberFormatException.class)
    @DisplayName("Test converting nullable integer value")
    public void test_nullableIntValue() {
        // given
        final String intStr = null;

        // when
        this.getIntConverter().convert(intStr);
    }
}
