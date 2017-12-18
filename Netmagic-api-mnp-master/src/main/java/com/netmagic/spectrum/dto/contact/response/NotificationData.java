package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the data for Notification send to a particular user
 * 
 * @author Pareekshit
 *
 */
public class NotificationData implements Serializable {

    private static final long serialVersionUID = -3715426997623263210L;

    @JsonProperty("notificationType")
    private String notificationType;
    @JsonProperty("sendEmailNotification")
    private String sendEmailNotification;
    @JsonProperty("sendSmsNotification")
    private String sendSmsNotification;

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getSendEmailNotification() {
        return sendEmailNotification;
    }

    public void setSendEmailNotification(String sendEmailNotification) {
        this.sendEmailNotification = sendEmailNotification;
    }

    public String getSendSmsNotification() {
        return sendSmsNotification;
    }

    public void setSendSmsNotification(String sendSmsNotification) {
        this.sendSmsNotification = sendSmsNotification;
    }
}
