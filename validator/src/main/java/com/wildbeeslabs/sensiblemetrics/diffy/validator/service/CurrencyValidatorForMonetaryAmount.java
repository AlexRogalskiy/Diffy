package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

import java.util.Collections;
import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Check that the validated {@link MonetaryAmount} is in the right {@link CurrencyUnit}.
 *
 * @author Guillaume Smet
 */
public class CurrencyValidatorForMonetaryAmount implements Validator<Currency> {
    private final List<String> acceptedCurrencies;

    public CurrencyValidatorForMonetaryAmount(final List<Currency> currencyList) {
        this.acceptedCurrencies = Optional.ofNullable(currencyList)
            .orElseGet(Collections::emptyList)
            .stream()
            .map(Currency::getCurrencyCode)
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean validate(final Currency value) {
        if (value == null) {
            return true;
        }
        return this.acceptedCurrencies.contains(value.getCurrencyCode());
    }
}
