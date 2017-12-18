package com.netmagic.spectrum.dto.contact.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.contact.response.ContactNumberInfo;
import com.netmagic.spectrum.dto.contact.response.ContactTypeDetails;
import com.netmagic.spectrum.dto.contact.response.NotificationModule;
import com.netmagic.spectrum.dto.contact.response.RoleModule;

/**
 * This class contains request for contact information details which are to be
 * updated
 * 
 * @author Pareekshit
 *
 */

public class EditContactRequest implements Serializable {

    private static final long serialVersionUID = -6661344910753002690L;

    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("isMynmUser")
    private String isMynmUser;
    @JsonProperty("activeUser")
    private String activeUser;
    @JsonProperty("mainCustomerId")
    private String customerId;
    @JsonProperty("mainCustomerName")
    private String customerName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("designation")
    private String designation;
    @JsonProperty("primaryEmailId")
    private String primaryEmailId;
    @JsonProperty("secondaryEmailId")
    private String secondaryEmailId;
    @JsonProperty("callingPreferredDaysTypeId")
    private Long preferredCallingDaysId;
    @JsonProperty("callingPreferredDaysTypeName")
    private String preferredCallingDays;
    @JsonProperty("callingPreferredTime")
    private String preferredCallingTime;
    @JsonProperty("contactTypeSubTypeBeansList")
    private List<ContactTypeDetails> contactTypeDetails;
    @JsonProperty("removedContactTypeSubTypeBeansList")
    private List<ContactTypeDetails> removedContactTypeDetails;
    @JsonProperty("contactNumbersInfoList")
    private List<ContactNumberInfo> contactNumbersInfoList;
    @JsonProperty("mynmRoleFunctionsBeanList")
    private List<RoleModule> rolesModulesList;
    @JsonProperty("notificationsModulesList")
    private List<NotificationModule> notificationsModulesList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIsMynmUser() {
        return isMynmUser;
    }

    public void setIsMynmUser(String isMynmUser) {
        this.isMynmUser = isMynmUser;
    }

    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPrimaryEmailId() {
        return primaryEmailId;
    }

    public void setPrimaryEmailId(String primaryEmailId) {
        this.primaryEmailId = primaryEmailId;
    }

    public String getSecondaryEmailId() {
        return secondaryEmailId;
    }

    public void setSecondaryEmailId(String secondaryEmailId) {
        this.secondaryEmailId = secondaryEmailId;
    }

    public Long getPreferredCallingDaysId() {
        return preferredCallingDaysId;
    }

    public void setPreferredCallingDaysId(Long preferredCallingDaysId) {
        this.preferredCallingDaysId = preferredCallingDaysId;
    }

    public String getPreferredCallingDays() {
        return preferredCallingDays;
    }

    public void setPreferredCallingDays(String preferredCallingDays) {
        this.preferredCallingDays = preferredCallingDays;
    }

    public String getPreferredCallingTime() {
        return preferredCallingTime;
    }

    public void setPreferredCallingTime(String preferredCallingTime) {
        this.preferredCallingTime = preferredCallingTime;
    }

    public List<ContactTypeDetails> getContactTypeDetails() {
        return contactTypeDetails;
    }

    public void setContactTypeDetails(List<ContactTypeDetails> contactTypeDetails) {
        this.contactTypeDetails = contactTypeDetails;
    }

    public List<ContactTypeDetails> getRemovedContactTypeDetails() {
        return removedContactTypeDetails;
    }

    public void setRemovedContactTypeDetails(List<ContactTypeDetails> removedContactTypeDetails) {
        this.removedContactTypeDetails = removedContactTypeDetails;
    }

    public List<ContactNumberInfo> getContactNumbersInfoList() {
        return contactNumbersInfoList;
    }

    public void setContactNumbersInfoList(List<ContactNumberInfo> contactNumbersInfoList) {
        this.contactNumbersInfoList = contactNumbersInfoList;
    }

    public List<RoleModule> getRolesModulesList() {
        return rolesModulesList;
    }

    public void setRolesModulesList(List<RoleModule> rolesModulesList) {
        this.rolesModulesList = rolesModulesList;
    }

    public List<NotificationModule> getNotificationsModulesList() {
        return notificationsModulesList;
    }

    public void setNotificationsModulesList(List<NotificationModule> notificationsModulesList) {
        this.notificationsModulesList = notificationsModulesList;
    }
}
