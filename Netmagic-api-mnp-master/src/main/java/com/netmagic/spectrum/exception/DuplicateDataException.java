package com.netmagic.spectrum.exception;

public class DuplicateDataException extends RuntimeException {

    private static final long serialVersionUID = -7629607346883990565L;

    public DuplicateDataException(String message) {
        super(message);
    }

    public DuplicateDataException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public DuplicateDataException(Throwable throwable) {
        super(throwable);
    }
}
