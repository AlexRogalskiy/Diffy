package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.CharacterValidator;

public class DigitCharValidator implements CharacterValidator {

    @Override
    public boolean isValid(char chr) {
        return (chr >= '0' && chr <= '9') ||
            chr == '*';
    }
}
