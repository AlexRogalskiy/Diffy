package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class StringToBooleanConverter implements Converter<String, Boolean> {

    private static final Set<String> trueValues = new HashSet<>(4);
    private static final Set<String> falseValues = new HashSet<>(4);

    static {
        trueValues.add("true");
        trueValues.add("on");
        trueValues.add("yes");
        trueValues.add("1");

        falseValues.add("false");
        falseValues.add("off");
        falseValues.add("no");
        falseValues.add("0");
    }

    @Nullable
    @Override
    public Boolean convert(final String source) {
        String value = source.trim();
        if (value.isEmpty()) {
            return null;
        }
        value = value.toLowerCase();
        if (trueValues.contains(value)) {
            return Boolean.TRUE;
        } else if (falseValues.contains(value)) {
            return Boolean.FALSE;
        }
        throw new IllegalArgumentException("Invalid boolean value '" + source + "'");
    }
}
