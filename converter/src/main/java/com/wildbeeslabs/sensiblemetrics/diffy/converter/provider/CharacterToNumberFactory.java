package com.wildbeeslabs.sensiblemetrics.diffy.converter.provider;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.ConverterFactory;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.NumberUtils;
import lombok.RequiredArgsConstructor;

public class CharacterToNumberFactory implements ConverterFactory<Character, Number> {

    @Override
    public <T extends Number> Converter<Character, T> getConverter(final Class<T> targetType) {
        return new CharacterToNumber<>(targetType);
    }

    @RequiredArgsConstructor
    private static final class CharacterToNumber<T extends Number> implements Converter<Character, T> {
        private final Class<T> targetType;

        @Override
        public T convert(final Character source) {
            return NumberUtils.convertNumberToTargetClass((short) source.charValue(), this.targetType);
        }
    }
}
