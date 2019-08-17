package com.wildbeeslabs.sensiblemetrics.diffy.converter.provider;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.service.StringBasedConverter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ObjectUtils;

public class BytesToEnumConverterFactory implements ConverterFactory<byte[], Enum<?>> {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T extends Enum<?>> Converter<byte[], T> getConverter(final Class<T> targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        if (enumType == null) {
            throw new IllegalArgumentException("The target type " + targetType.getName() + " does not refer to an enum");
        }
        return new BytesToEnum(enumType);
    }

    private class BytesToEnum<T extends Enum<T>> extends StringBasedConverter implements Converter<byte[], T> {
        private final Class<T> enumType;

        public BytesToEnum(final Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(byte[] source) {
            if (ObjectUtils.isEmpty(source)) {
                return null;
            }
            return Enum.valueOf(this.enumType, toString(source).trim());
        }
    }
}
