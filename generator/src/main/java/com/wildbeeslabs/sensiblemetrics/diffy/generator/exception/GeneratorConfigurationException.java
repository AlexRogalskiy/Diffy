package com.wildbeeslabs.sensiblemetrics.diffy.generator.exception;

import com.wildbeeslabs.sensiblemetrics.diffy.generator.service.Generator;

/**
 * Raised if a problem arises when attempting to configure a generator with
 * annotations from a property parameter.
 *
 * @see Generator#configure(java.lang.reflect.AnnotatedType)
 */
public class GeneratorConfigurationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GeneratorConfigurationException(String message) {
        super(message);
    }

    public GeneratorConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
