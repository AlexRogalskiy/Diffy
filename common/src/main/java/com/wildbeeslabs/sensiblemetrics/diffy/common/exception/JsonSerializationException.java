package com.wildbeeslabs.sensiblemetrics.diffy.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.MissingFormatArgumentException;

/**
 * Excessive or missing argument {@link MissingFormatArgumentException} implementation
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JsonSerializationException extends RuntimeException {

    /**
     * Default explicit serialVersionUID for interoperability
     */
    private static final long serialVersionUID = -890916602971805738L;

    public JsonSerializationException(final String message) {
        super(message);
    }
}
