package com.wildbeeslabs.sensiblemetrics.diffy.generator.exception;

public class ReflectionException extends RuntimeException {

    private static final long serialVersionUID = 7201284776139650104L;

    public ReflectionException(String message) {
        super(message);
    }

    public ReflectionException(Throwable cause) {
        super(cause.toString());
    }
}
