package com.wildbeeslabs.sensiblemetrics.diffy.core.helpers;

import com.wildbeeslabs.sensiblemetrics.diffy.common.executor.iface.Executor;
import org.apache.commons.lang3.LocaleUtils;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Mockito.*;

public class OptionalConsumerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Default {@link Function} test operators
     */
    private static final Consumer TEST_CONSUMER = v -> {
    };
    private static final Executor TEST_EXECUTOR = () -> {
    };

    @Test
    public void test_OptionalConsumer_withEmptyOperators() {
        // given
        final String value = "test";
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of(value);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(TEST_CONSUMER), IsEqual.equalTo(optionalConsumer));
        assertThat(optionalConsumer.ifNotPresent(TEST_EXECUTOR), IsEqual.equalTo(optionalConsumer));
    }

    @Test
    public void test_OptionalConsumer_whenPassedValue() {
        // given
        final String value = "test";
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of(value);
        final Consumer<String> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, times(1)).accept(anyString());

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, never()).execute();
    }

    @Test
    public void test_OptionalConsumer_whenPassedNULL() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of(null);
        final Consumer<String> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, never()).accept(anyString());

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, times(1)).execute();
    }

    @Test
    public void test_OptionalConsumer_whenPassed_nullable_Consumer() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of("test");

        // when
        thrown.expect(NullPointerException.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        optionalConsumer.ifNotPresent(null);
    }

    @Test
    public void test_OptionalConsumer_whenPassed_nullable_Executor() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of("test");

        // when
        thrown.expect(NullPointerException.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        optionalConsumer.ifPresent(null);
    }

    @Test
    public void test_OptionalConsumer_whenIfNotPresent_Throw_Exception() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of(null);

        // when
        thrown.expect(IllegalArgumentException.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        optionalConsumer.ifNotPresent(() -> {
                throw new IllegalArgumentException();
            }
        );
    }

    @Test
    public void test_OptionalConsumer_whenIfPresent_Throw_Exception() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of("test");

        // when
        thrown.expect(IllegalArgumentException.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        optionalConsumer.ifPresent(t -> {
                throw new IllegalArgumentException();
            }
        );
    }

    @Test
    public void test_OptionalConsumer_Filter_whenPassed_Invalid_Data() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of("test").filter(v -> v.length() > 4);
        final Consumer<String> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, never()).accept(anyString());

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, times(1)).execute();
    }

    @Test
    public void test_OptionalConsumer_Filter_whenPassed_Valid_Data() {
        // given
        final OptionalConsumer<String> optionalConsumer = OptionalConsumer.of("test").filter(v -> v.length() > 3);
        final Consumer<String> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, times(1)).accept(anyString());

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, never()).execute();
    }

    @Test
    public void test_OptionalConsumer_Mapper_whenPassed_Valid_Data() {
        // given
        final String languageTag = "en";
        final OptionalConsumer<Locale> optionalConsumer = OptionalConsumer.of(languageTag).map(LocaleUtils::toLocale);
        final Consumer<Locale> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, times(1)).accept(any(Locale.class));

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, never()).execute();
    }

    @Test
    public void test_OptionalConsumer_Mapper_whenPassed_Invalid_Data() {
        // given
        final String languageTag = null;
        final OptionalConsumer<Locale> optionalConsumer = OptionalConsumer.of(languageTag).map(LocaleUtils::toLocale);
        final Consumer<Locale> consumerMock = mock(Consumer.class);
        final Executor executorMock = mock(Executor.class);

        // then
        assertThat(optionalConsumer, is(not(nullValue())));
        assertThat(optionalConsumer.ifPresent(consumerMock), IsEqual.equalTo(optionalConsumer));
        verify(consumerMock, never()).accept(any(Locale.class));

        // then
        assertThat(optionalConsumer.ifNotPresent(executorMock), IsEqual.equalTo(optionalConsumer));
        verify(executorMock, times(1)).execute();
    }
}
