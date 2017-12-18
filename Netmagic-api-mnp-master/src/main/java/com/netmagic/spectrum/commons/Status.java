package com.netmagic.spectrum.commons;

public enum Status {

    SUCCESS("success"), FAILURE("failure");

    private String status;

    private Status(String s) {
        status = s;
    }

    public String getStatus() {
        return status;
    }
}
