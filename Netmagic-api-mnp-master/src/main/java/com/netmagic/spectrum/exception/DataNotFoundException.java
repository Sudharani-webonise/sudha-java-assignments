package com.netmagic.spectrum.exception;

public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -157417501220281966L;

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DataNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
