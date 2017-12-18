package com.netmagic.spectrum.exception;

/**
 * Signals that the exception thrown when for an authorized user
 *
 * @author webonise
 */
public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = -3444244818878427759L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UnauthorizedException(Throwable throwable) {
        super(throwable);
    }
}
