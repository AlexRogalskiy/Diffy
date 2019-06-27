package com.wildbeeslabs.sensiblemetrics.diffy.common.test;

import com.wildbeeslabs.sensiblemetrics.diffy.common.utils.ValidationUtils;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.InputStream;
import java.util.Base64;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Slf4j
@UtilityClass
public class TestUtils {

    public static byte[] base64Decode(final byte[] value) {
        ValidationUtils.notNull(value, "Value should not be null");
        // then
        return Base64.getMimeDecoder().decode(value);
    }

    public static byte[] base64Encode(final byte[] value) {
        ValidationUtils.notNull(value, "Value should not be null");
        // then
        return Base64.getMimeEncoder().encode(value);
    }

    public static InputStream getInputStream(final String fileName) {
        ValidationUtils.notNull(fileName, "File name should not be null");
        // then
        return TestUtils.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static <T> void checkSingleMatch(final Validator validator, final T entity, final String message) {
        ValidationUtils.notNull(validator, "Validator should not be null");
        // when
        final Set<ConstraintViolation<T>> validates = validator.validate(entity);
        // then
        assertThat(validates, is(not(empty())));
        assertThat(validates, hasSize(1));
        assertTrue(validates.stream().allMatch(v -> v.getMessageTemplate().equalsIgnoreCase(message)));
    }

    public static <T> void checkAnyMatch(final Validator validator, final T entity, final String message) {
        ValidationUtils.notNull(validator, "Validator should not be null");
        // when
        final Set<ConstraintViolation<T>> validates = validator.validate(entity);
        // then
        assertThat(validates, is(not(empty())));
        assertTrue(validates.stream().anyMatch(v -> v.getMessageTemplate().equalsIgnoreCase(message)));
    }

    public static <T> void checkNoneMatch(final Validator validator, final T entity, final String message) {
        ValidationUtils.notNull(validator, "Validator should not be null");
        // when
        final Set<ConstraintViolation<T>> validates = validator.validate(entity);
        // then
        assertThat(validates, is(not(empty())));
        assertTrue(validates.stream().noneMatch(v -> v.getMessageTemplate().equalsIgnoreCase(message)));
    }
}
