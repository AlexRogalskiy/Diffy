package com.wildbeeslabs.sensiblemetrics.comparalyzer.matcher;

import com.wildbeeslabs.sensiblemetrics.comparalyzer.AbstractDiffTest;
import com.wildbeeslabs.sensiblemetrics.comparalyzer.examples.matcher.UrlParametersMatcher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Url parameters matcher unit test
 *
 * @author Alexander Rogalskiy
 * @version %I%, %G%
 * @since 1.0
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UrlParametersMatcherTest extends AbstractDiffTest {

    /**
     * Default url parameters string
     */
    private String urlString;

    @Before
    public void setUp() {
        this.urlString = getAlphaNumericStringMock().val();
    }

    @Test
    public void assertMatches() {
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = urlString;

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertTrue(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertLengthMismatch() {
        final String urlString = "arg1=val1&arg2=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3&arg4=val4";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertNamesMismatch() {
        final String urlString = "arg1=val1&arg22=val2&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }

    @Test
    public void assertValuesMismatch() {
        final String urlString = "arg1=val1&arg2=val22&arg3=val3";
        final String testString = "arg1=val1&arg2=val2&arg3=val3";

        final UrlParametersMatcher urlParametersMatcher = UrlParametersMatcher.getInstance(urlString);
        assertFalse(urlParametersMatcher.matchesSafe(testString));
    }
}