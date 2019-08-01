package com.wildbeeslabs.sensiblemetrics.diffy.validator.service;

import com.wildbeeslabs.sensiblemetrics.diffy.validator.interfaces.Validator;
import org.apache.commons.lang3.StringUtils;

public class NumericValidator implements Validator<Object> {

    @Override
    public String getDescription() {
        return "Returns whether o can represent a number";
    }

    @Override
    public boolean validate(final Object o) {
        if (o instanceof Number) {
            return true;
        }
        String s = (o instanceof String) ? (String) o : o.toString();
        return StringUtils.isNumeric(s);
    }
}
