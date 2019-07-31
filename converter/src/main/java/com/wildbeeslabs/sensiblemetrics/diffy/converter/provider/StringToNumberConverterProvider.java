package com.wildbeeslabs.sensiblemetrics.diffy.converter.provider;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterProvider;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.NumberUtils;
import lombok.RequiredArgsConstructor;

class StringToNumberConverterProvider implements ConverterProvider<String, Number> {

    @Override
    public <T extends Number> Converter<String, T> getConverter(final Class<T> targetType) {
        return new StringToNumber<>(targetType);
    }


    @RequiredArgsConstructor
    private static final class StringToNumber<T extends Number> implements Converter<String, T> {
        private final Class<T> targetType;

        @Override
        public T convert(final String source) {
            if (source.isEmpty()) {
                return null;
            }
            return NumberUtils.parseNumber(source, this.targetType);
        }
    }
}
