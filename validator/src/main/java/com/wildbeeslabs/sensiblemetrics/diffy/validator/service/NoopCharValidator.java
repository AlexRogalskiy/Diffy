package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.CharacterValidator;

public class NoopCharValidator implements CharacterValidator {

    @Override
    public boolean isValid(char chr) {
        return true;
    }
}
