package com.wildbeeslabs.sensiblemetrics.diffy.converter.provider;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterProvider;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ConverterUtils;
import lombok.RequiredArgsConstructor;

public class StringToEnumConverterFactory implements ConverterProvider<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(final Class<T> targetType) {
        return new StringToEnum(ConverterUtils.getEnumType(targetType));
    }

    @RequiredArgsConstructor
    private class StringToEnum<T extends Enum> implements Converter<String, T> {
        private final Class<T> enumType;

        @Override
        public T convert(final String source) {
            if (source.isEmpty()) {
                return null;
            }
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }
}
