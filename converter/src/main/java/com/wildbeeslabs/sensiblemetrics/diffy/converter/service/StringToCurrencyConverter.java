package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces.Converter;

import javax.annotation.Nullable;
import java.util.Currency;

public class StringToCurrencyConverter implements Converter<String, Currency> {

    @Nullable
    @Override
    public Currency convert(final String source) {
        return Currency.getInstance(source);
    }
}
