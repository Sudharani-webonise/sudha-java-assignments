package com.netmagic.spectrum.commons.url;

/**
 * Enum mapping the possible URL values for Notification
 * 
 * @author Webonise
 *
 */
public enum Notification {

    VIEW_NOTIFICATION("customerAPI/manage/notification/view?customerId="),

    EDIT_NOTIFICATION("customerAPI/manage/notification/update");

    private String URL;

    private Notification(String URL) {
        this.URL = URL;
    }

    public String getURL() {
        return URL;
    }
}
