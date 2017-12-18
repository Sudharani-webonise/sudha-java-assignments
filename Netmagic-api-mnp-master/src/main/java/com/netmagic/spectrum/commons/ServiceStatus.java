package com.netmagic.spectrum.commons;

/**
 * Enum mapping the possible values of status for Service SOF
 * 
 * @author Webonise
 *
 */
public enum ServiceStatus {

    ACTIVE("Active"), NOT_DELIVERED("Not_delivered"), UNDER_DEACTIVATION(
            "Under_deactivation"), UNDER_CANCELLATION("Under_cancellation"), ALL("AllSOF");

    private String status;

    private ServiceStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
