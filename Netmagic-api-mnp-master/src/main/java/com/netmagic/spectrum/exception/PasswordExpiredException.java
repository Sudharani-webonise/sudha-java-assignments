package com.netmagic.spectrum.exception;

public class PasswordExpiredException extends RuntimeException {

    private static final long serialVersionUID = -3650059573215934628L;

    public PasswordExpiredException(String message) {
        super(message);
    }

    public PasswordExpiredException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public PasswordExpiredException(Throwable throwable) {
        super(throwable);
    }
}
