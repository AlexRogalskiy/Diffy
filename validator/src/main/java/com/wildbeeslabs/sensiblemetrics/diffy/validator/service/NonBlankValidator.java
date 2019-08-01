package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.ExpressionUtils;

public class NonBlankValidator implements Validator<Object> {

    @Override
    public String getDescription() {
        return "Returns whether o is not null and not an empty string";
    }

    @Override
    public boolean validate(final Object o) {
        return ExpressionUtils.isNonBlankData(o);
    }
}
