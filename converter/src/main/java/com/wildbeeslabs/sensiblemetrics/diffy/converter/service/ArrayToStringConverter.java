package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import org.apache.commons.lang.ArrayUtils;

import javax.annotation.Nullable;

public class ArrayToStringConverter<T> implements Converter<T[], String> {

    @Nullable
    @Override
    public String convert(final T[] value) {
        return ArrayUtils.toString(value);
    }
}
