package com.netmagic.spectrum.commons;

public enum AuthType {

    ADMIN("ADMIN"), USER("USER"), INTERNAL_USER("INTERNAL_USER");

    private String authType;

    private AuthType(String authType) {
        this.authType = authType;
    }

    public String getAuthType() {
        return authType;
    }
}
