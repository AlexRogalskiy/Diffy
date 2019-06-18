package com.wildbeeslabs.sensiblemetrics.diffy.validator.iface;

import com.wildbeeslabs.sensiblemetrics.diffy.common.iface.ThrowingProcessor;
import com.wildbeeslabs.sensiblemetrics.diffy.exception.InvalidParameterException;

/**
 * Digit {@link Validator} interface declaration
 */
public interface DigitValidator extends Validator<String>, ThrowingProcessor<String, String, InvalidParameterException> {
}
