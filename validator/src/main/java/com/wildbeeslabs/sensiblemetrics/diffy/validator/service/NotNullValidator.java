package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;

public class NotNullValidator implements Validator<Object> {

    @Override
    public String getDescription() {
        return "Returns whether o is not null";
    }

    @Override
    public boolean validate(final Object o) {
        return o != null;
    }
}
