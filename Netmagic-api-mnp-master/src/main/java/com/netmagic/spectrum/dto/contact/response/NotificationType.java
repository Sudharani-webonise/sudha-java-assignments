package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the notification details for different associateCustomer
 * 
 * @author Pareekshit
 *
 */
public class NotificationType implements Serializable {

    private static final long serialVersionUID = 4539800416264351879L;

    @JsonProperty("associteCustomerID")
    private Long associateCustomerId;
    @JsonProperty("associteCustomerName")
    private String associateCustomerName;
    @JsonProperty("projectId")
    private Long projectId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("notificationTypeId")
    private Long id;
    @JsonProperty("notificationTypeName")
    private String name;
    @JsonProperty("contactNotifysList")
    private List<NotificationData> notificationDatas;

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public String getAssociateCustomerName() {
        return associateCustomerName;
    }

    public void setAssociateCustomerName(String associateCustomerName) {
        this.associateCustomerName = associateCustomerName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NotificationData> getNotificationDatas() {
        return notificationDatas;
    }

    public void setNotificationDatas(List<NotificationData> notificationDatas) {
        this.notificationDatas = notificationDatas;
    }
}
