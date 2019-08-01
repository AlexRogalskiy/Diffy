package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

public class NullValidator implements Validator<Object> {
    @Override
    public String getDescription() {
        return "Returns whether o is null";
    }

    @Override
    public boolean validate(final Object o) {
        return o == null;
    }
}
