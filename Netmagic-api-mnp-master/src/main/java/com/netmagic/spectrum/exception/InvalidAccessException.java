package com.netmagic.spectrum.exception;

/**
 * Signals that the exception thrown when tried to manipulations
 * made on Invalid data
 *
 * @author webonise
 */

public class InvalidAccessException extends RuntimeException {

    private static final long serialVersionUID = 1129977889810657075L;

    public InvalidAccessException(String message) {
        super(message);
    }

    public InvalidAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public InvalidAccessException(Throwable throwable) {
        super(throwable);
    }

}
