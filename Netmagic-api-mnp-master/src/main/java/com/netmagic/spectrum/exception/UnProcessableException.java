package com.netmagic.spectrum.exception;

/**
 * Signals that the exception thrown when their is data miss match and which is
 * not processable
 *
 * @author webonise
 */
public class UnProcessableException extends RuntimeException {

    private static final long serialVersionUID = -157417501220281926L;

    public UnProcessableException(String message) {
        super(message);
    }

    public UnProcessableException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UnProcessableException(Throwable throwable) {
        super(throwable);
    }
}