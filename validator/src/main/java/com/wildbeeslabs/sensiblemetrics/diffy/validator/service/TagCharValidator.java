package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

public class TagCharValidator extends AtomCharValidator {

    @Override
    public boolean isValid(char chr) {
        return chr != '+' && super.isValid(chr);
    }
}
