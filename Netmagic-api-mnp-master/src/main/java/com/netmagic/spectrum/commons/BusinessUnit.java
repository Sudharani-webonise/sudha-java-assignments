package com.netmagic.spectrum.commons;

public enum BusinessUnit {

    NMIT("NMIT"), NSPL("NSPL"), NMSPL("NMSPL"), NMITS("NMITS");

    private String type;

    private BusinessUnit(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
