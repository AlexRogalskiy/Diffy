package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ObjectUtils;

public class BytesToBooleanConverter extends StringBasedConverter implements Converter<byte[], Boolean> {

    @Override
    public Boolean convert(byte[] source) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        final String value = toString(source);
        return ("1".equals(value) || "true".equalsIgnoreCase(value)) ? Boolean.TRUE : Boolean.FALSE;
    }
}
