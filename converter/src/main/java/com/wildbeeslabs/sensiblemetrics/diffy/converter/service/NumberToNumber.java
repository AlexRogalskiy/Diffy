package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.NumberUtils;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

@RequiredArgsConstructor
public class NumberToNumber<T extends Number> implements Converter<Number, T> {
    private final Class<T> targetType;

    @Nullable
    @Override
    public T convert(final Number source) {
        return NumberUtils.convertNumberToTargetClass(source, this.targetType);
    }
}
