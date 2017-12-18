package com.netmagic.spectrum.exception;

/**
 * Signals that the exception thrown when their
 * is no user found in the Application
 *
 * @author webonise
 */
public class UserNotFoundException extends RuntimeException{

    private static final long serialVersionUID = -157417501220281926L;

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UserNotFoundException(Throwable throwable) {
        super(throwable);
    }
}