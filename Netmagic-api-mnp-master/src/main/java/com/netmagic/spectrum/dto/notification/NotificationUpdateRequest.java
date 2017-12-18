package com.netmagic.spectrum.dto.notification;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for Notification Template Type details
 * like customer type id and notification type name and list of newly
 * notification and removed type data
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */

public class NotificationUpdateRequest implements Serializable {

    private static final long serialVersionUID = -4853124514050684821L;

    @JsonProperty("customerId")
    private String customerId;
    @JsonProperty("newlyAddedTemplateTypeBeans")
    private List<NotificationTemplateType> newlyAddedTemplateTypeBeans;
    @JsonProperty("removedTemplateTypeBeans")
    private List<NotificationTemplateType> removedTemplateTypeBeans;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<NotificationTemplateType> getNewlyAddedTemplateTypeBeans() {
        return newlyAddedTemplateTypeBeans;
    }

    public void setNewlyAddedTemplateTypeBeans(List<NotificationTemplateType> newlyAddedTemplateTypeBeans) {
        this.newlyAddedTemplateTypeBeans = newlyAddedTemplateTypeBeans;
    }

    public List<NotificationTemplateType> getRemovedTemplateTypeBeans() {
        return removedTemplateTypeBeans;
    }

    public void setRemovedTemplateTypeBeans(List<NotificationTemplateType> removedTemplateTypeBeans) {
        this.removedTemplateTypeBeans = removedTemplateTypeBeans;
    }
}
