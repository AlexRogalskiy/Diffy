package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;
import com.wildbeeslabs.sensiblemetrics.diffy.converter.utils.ObjectUtils;

import java.util.UUID;

public class BytesToUuidConverter extends StringBasedConverter implements Converter<byte[], UUID> {

    @Override
    public UUID convert(byte[] source) {
        if (ObjectUtils.isEmpty(source)) {
            return null;
        }
        return UUID.fromString(toString(source));
    }
}
