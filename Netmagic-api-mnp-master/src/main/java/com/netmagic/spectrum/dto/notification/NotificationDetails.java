package com.netmagic.spectrum.dto.notification;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the response object for Notification details like customer id
 * and notification template
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */

public class NotificationDetails implements Serializable {

    private static final long serialVersionUID = -8503093333449701534L;

    @JsonProperty("customerId")
    private long customerId;
    @JsonProperty("notificationTemplateTypeBeans")
    private List<NotificationTemplateType> notificationTemplateType;

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<NotificationTemplateType> getNotificationTemplateType() {
        return notificationTemplateType;
    }

    public void setNotificationTemplateType(List<NotificationTemplateType> notificationTemplateType) {
        this.notificationTemplateType = notificationTemplateType;
    }
}
