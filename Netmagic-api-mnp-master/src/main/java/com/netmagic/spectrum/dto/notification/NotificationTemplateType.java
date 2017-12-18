package com.netmagic.spectrum.dto.notification;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.contact.response.NotificationData;

/**
 * This class maps the request/response object for Notification Template Type
 * details like customer type id and notification type name and list of
 * notification type data
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */

public class NotificationTemplateType implements Serializable {

    private static final long serialVersionUID = -741801498631434038L;

    @JsonProperty("notificationTypeId")
    private long notificationTypeId;
    @JsonProperty("notificationTypeName")
    private String notificationTypeName;
    @JsonProperty("notificationTypeDataBeans")
    private List<NotificationData> notificationTypeData;

    public long getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(long notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public String getNotificationTypeName() {
        return notificationTypeName;
    }

    public void setNotificationTypeName(String notificationTypeName) {
        this.notificationTypeName = notificationTypeName;
    }

    public List<NotificationData> getNotificationTypeData() {
        return notificationTypeData;
    }

    public void setNotificationTypeData(List<NotificationData> notificationTypeData) {
        this.notificationTypeData = notificationTypeData;
    }
}
