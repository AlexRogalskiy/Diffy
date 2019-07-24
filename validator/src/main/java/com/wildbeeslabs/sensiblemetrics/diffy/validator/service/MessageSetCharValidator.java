package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.CharacterValidator;

public class MessageSetCharValidator implements CharacterValidator {

    @Override
    public boolean isValid(char chr) {
        return Character.isDigit(chr) ||
            chr == ':' ||
            chr == '*' ||
            chr == ',';
    }
}
