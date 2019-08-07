package com.wildbeeslabs.sensiblemetrics.diffy.converter.service;

import com.wildbeeslabs.sensiblemetrics.diffy.converter.exception.ConvertOperationException;

import java.util.Map;

/**
 * The MBeanValueConverter is used to convert {@link String} parameter values stored in a {@link Map} of
 * parameter names to values into their native types.
 */
public class MBeanValueConverter {
    private Map<String, String[]> parameterMap;

    /**
     * Constructor.
     *
     * @param parameterMap the {@link Map} of parameter names to {@link String} values.
     */
    public MBeanValueConverter(final Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    /**
     * Convert the {@link String} parameter value into its native type.
     * The {@link String} '&lt;null&gt;' is converted into a null value.
     * Only types String, Boolean, Int, Long, Float, and Double are supported.
     *
     * @param parameterName the parameter name to convert.
     * @param type          the native type for the parameter.
     * @return the converted value.
     * @throws NumberFormatException     the parameter is not a number
     * @throws ConvertOperationException unable to recognize the parameter type
     */
    public Object convertParameterValue(final String parameterName, final String type) throws NumberFormatException, ConvertOperationException {
        String[] valueList = parameterMap.get(parameterName);
        if (valueList == null || valueList.length == 0) return null;
        String value = valueList[0];
        if (value.equals("<null>")) return null;
        if (type.equals("java.lang.String")) return value;
        if (type.equals("boolean")) return Boolean.parseBoolean(value);
        if (type.equals("int")) return Integer.parseInt(value);
        if (type.equals("long")) return Long.parseLong(value);
        if (type.equals("float")) return Float.parseFloat(value);
        if (type.equals("double")) return Double.parseDouble(value);
        throw new ConvertOperationException("Cannot convert " + value + " into type " + type + " for parameter " + parameterName);
    }
}
