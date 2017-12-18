package com.netmagic.spectrum.exception;

public class WrongBillGroupException extends RuntimeException {

    private static final long serialVersionUID = 4081284861834600244L;

    public WrongBillGroupException(String message) {
        super(message);
    }

    public WrongBillGroupException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WrongBillGroupException(Throwable throwable) {
        super(throwable);
    }
}
