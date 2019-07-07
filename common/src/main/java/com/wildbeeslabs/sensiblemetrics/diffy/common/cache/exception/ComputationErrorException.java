package com.wildbeeslabs.sensiblemetrics.diffy.common.cache.exception;

/**
 * This exception should be thrown from the method
 * if the returned computation should NOT be kept in the cache.  The actual value
 * returned by the {@link #getComputation()} should be returned to the caller, but
 * that value should be considered volatile and should NOT be kept in the cache
 *
 * @author jwells
 */
public class ComputationErrorException extends RuntimeException {
    private static final long serialVersionUID = 1186268368624844657L;
    public Object computation;

    public ComputationErrorException() {
        super();
    }

    public ComputationErrorException(Object computation) {
        this.computation = computation;
    }

    public Object getComputation() {
        return computation;
    }
}
