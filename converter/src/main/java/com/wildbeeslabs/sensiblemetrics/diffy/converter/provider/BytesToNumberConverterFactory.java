package com.wildbeeslabs.sensiblemetrics.diffy.converter.provider;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.service.StringBasedConverter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.NumberUtils;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ObjectUtils;

public class BytesToNumberConverterFactory implements ConverterFactory<byte[], Number> {

    @Override
    public <T extends Number> Converter<byte[], T> getConverter(final Class<T> targetType) {
        return new BytesToNumberConverter<>(targetType);
    }

    private static final class BytesToNumberConverter<T extends Number> extends StringBasedConverter implements Converter<byte[], T> {
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
