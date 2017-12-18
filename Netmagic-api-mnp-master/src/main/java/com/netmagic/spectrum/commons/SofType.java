package com.netmagic.spectrum.commons;

/**
 * 
 * @author webonsie
 *
 */
public enum SofType {

    NEW("New"), AMENDMENT("Amendment");

    private String SofType;

    private SofType(String sofType) {
        SofType = sofType;
    }

    public String getSofType() {
        return SofType;
    }

}
