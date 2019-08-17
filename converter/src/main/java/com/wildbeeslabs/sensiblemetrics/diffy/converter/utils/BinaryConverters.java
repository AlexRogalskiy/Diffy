package com.wildbeeslabs.sensiblemetrics.diffy.converter.utils;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterFactory;
import lombok.experimental.UtilityClass;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Helper class to register JSR-310 specific binary {@link Converter} implementations
 */
@UtilityClass
public class BinaryConverters {

    /**
     * Use {@literal UTF-8} as default charset.
     */
    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public static class StringBasedConverter {

        byte[] fromString(final String source) {
            return source.getBytes(CHARSET);
        }

        String toString(final byte[] source) {
            return new String(source, CHARSET);
        }
    }

    public static class StringToBytesConverter extends StringBasedConverter implements Converter<String, byte[]> {

        @Override
        public byte[] convert(final String source) {
            return fromString(source);
        }
    }

    public static class BytesToStringConverter extends StringBasedConverter implements Converter<byte[], String> {

        @Override
        public String convert(final byte[] source) {
            return toString(source);
        }

    }

    public static class NumberToBytesConverter extends StringBasedConverter implements Converter<Number, byte[]> {

        @Override
        public byte[] convert(final Number source) {
            return fromString(source.toString());
        }
    }

    public static class EnumToBytesConverter extends StringBasedConverter implements Converter<Enum<?>, byte[]> {

        @Override
        public byte[] convert(final Enum<?> source) {
            return fromString(source.name());
        }
    }

    public static final class BytesToEnumConverterFactory implements ConverterFactory<byte[], Enum<?>> {

        @Override
        @SuppressWarnings({"unchecked", "rawtypes"})
        public <T extends Enum<?>> Converter<byte[], T> getConverter(Class<T> targetType) {
            Class<?> enumType = targetType;
            while (enumType != null && !enumType.isEnum()) {
                enumType = enumType.getSuperclass();
            }
            if (enumType == null) {
                throw new IllegalArgumentException("The target type " + targetType.getName() + " does not refer to an enum");
            }
            return new BytesToEnum(enumType);
        }

        /**
         * @author Christoph Strobl
         * @since 1.7
         */
        private class BytesToEnum<T extends Enum<T>> extends StringBasedConverter implements Converter<byte[], T> {

            private final Class<T> enumType;

            public BytesToEnum(final Class<T> enumType) {
                this.enumType = enumType;
            }

            @Override
            public T convert(final byte[] source) {
                if (ObjectUtils.isEmpty(source)) {
                    return null;
                }
                return Enum.valueOf(this.enumType, toString(source).trim());
            }
        }
    }

    public static class BytesToNumberConverterFactory implements ConverterFactory<byte[], Number> {

        @Override
        public <T extends Number> Converter<byte[], T> getConverter(final Class<T> targetType) {
            return new BytesToNumberConverter<>(targetType);
        }

        private static final class BytesToNumberConverter<T extends Number> extends StringBasedConverter
            implements Converter<byte[], T> {

            private final Class<T> targetType;

            public BytesToNumberConverter(final Class<T> targetType) {
                this.targetType = targetType;
            }

            @Override
            public T convert(final byte[] source) {
                if (ObjectUtils.isEmpty(source)) {
                    return null;
                }
                return NumberUtils.parseNumber(toString(source), targetType);
            }
        }
    }

    public static class BooleanToBytesConverter extends StringBasedConverter implements Converter<Boolean, byte[]> {
        final byte[] _true = fromString("1");
        final byte[] _false = fromString("0");

        @Override
        public byte[] convert(final Boolean source) {
            return source.booleanValue() ? _true : _false;
        }
    }

    public static class BytesToBooleanConverter extends StringBasedConverter implements Converter<byte[], Boolean> {

        @Override
        public Boolean convert(final byte[] source) {
            if (ObjectUtils.isEmpty(source)) {
                return null;
            }
            String value = toString(source);
            return ("1".equals(value) || "true".equalsIgnoreCase(value)) ? Boolean.TRUE : Boolean.FALSE;
        }
    }

    public static class DateToBytesConverter extends StringBasedConverter implements Converter<Date, byte[]> {

        @Override
        public byte[] convert(final Date source) {
            return fromString(Long.toString(source.getTime()));
        }
    }

    public static class BytesToDateConverter extends StringBasedConverter implements Converter<byte[], Date> {

        @Override
        public Date convert(final byte[] source) {
            if (ObjectUtils.isEmpty(source)) {
                return null;
            }
            String value = toString(source);
            try {
                return new Date(NumberUtils.parseNumber(value, Long.class));
            } catch (NumberFormatException nfe) {

            }

            try {
                return DateFormat.getInstance().parse(value);
            } catch (ParseException e) {
            }
            throw new IllegalArgumentException(String.format("Cannot parse date out of %s", Arrays.toString(source)));
        }
    }

    public static class UuidToBytesConverter extends StringBasedConverter implements Converter<UUID, byte[]> {

        @Override
        public byte[] convert(final UUID source) {
            return fromString(source.toString());
        }
    }

    public static class BytesToUuidConverter extends StringBasedConverter implements Converter<byte[], UUID> {

        @Override
        public UUID convert(final byte[] source) {
            if (ObjectUtils.isEmpty(source)) {
                return null;
            }
            return UUID.fromString(toString(source));
        }
    }
}
