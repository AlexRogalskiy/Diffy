package com.wildbeeslabs.sensiblemetrics.diffy.converter.interfaces;

import org.apache.commons.beanutils.Converter;


/**
 * <p>General purpose locale-sensitive data type converter that can be registered and used
 * within the BeanUtils package to manage the conversion of objects from
 * one type to another.
 *
 * @version $Id: LocaleConverter.java 1540186 2013-11-08 21:08:30Z oheger $
 */

public interface LocaleConverter extends Converter {


    /**
     * Convert the specified locale-sensitive input object into an output object of the
     * specified type.
     *
     * @param <T> The desired target type of the conversion
     * @param type Data type to which this value should be converted
     * @param value The input value to be converted
     * @param pattern The user-defined pattern is used for the input object formatting.
     * @return The converted value
     *
     * @exception org.apache.commons.beanutils.ConversionException if conversion
     * cannot be performed successfully or if the target type is not supported
     */
    <T> T convert(Class<T> type, Object value, String pattern);
}
