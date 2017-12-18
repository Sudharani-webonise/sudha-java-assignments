package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the details of notification for a user for different
 * modules
 * 
 * @author Pareekshit
 *
 */
public class NotificationModule implements Serializable {

    private static final long serialVersionUID = -8688595950260458063L;

    @JsonProperty("notificationModuleName")
    private String name;

    @JsonProperty("contactTypesList")
    private List<NotificationType> notificationTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }

    public void setNotificationTypes(List<NotificationType> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }
}
