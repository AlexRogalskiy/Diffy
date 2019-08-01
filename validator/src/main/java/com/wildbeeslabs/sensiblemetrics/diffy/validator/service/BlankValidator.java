package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import com.wildbeeslabs.sensiblemetrics.diffy.validator.utils.ExpressionUtils;

public class BlankValidator implements Validator<Object> {

    @Override
    public String getDescription() {
        return "Returns whether o is null or an empty string";
    }

    @Override
    public boolean validate(final Object o) {
        return !ExpressionUtils.isNonBlankData(o);
    }
}
