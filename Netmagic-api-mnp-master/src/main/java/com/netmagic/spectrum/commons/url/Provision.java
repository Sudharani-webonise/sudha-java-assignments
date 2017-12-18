package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Provision
 * 
 * @author Webonise
 *
 */
public enum Provision {

    SOF("billingGroupAPI/validate/group/provisioningServices"),

    LINE_ITEM("/services/mnp/provisioning");

    private String URL;

    private Provision(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
