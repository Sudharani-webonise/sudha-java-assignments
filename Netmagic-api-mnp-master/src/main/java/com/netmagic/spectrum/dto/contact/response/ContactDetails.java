package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores all the details of a contact
 * 
 * @author Pareekshit
 *
 */
public class ContactDetails implements Serializable {

    private static final long serialVersionUID = -8748794045823740398L;

    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("contactSugarId")
    private String contactSugarId;
    @JsonProperty("isMynmUser")
    private String isMyNmUser;
    @JsonProperty("activeUser")
    private String isActiveUser;
    @JsonProperty("mainCustomerId")
    private Long customerId;
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
    @JsonProperty("kycStatus")
    private String kycStatus;
    @JsonProperty("accessCardStatus")
    private String accessCardStatus;
    @JsonProperty("callingPreferredDaysTypeId")
    private Long preferredCallingDaysId;
    @JsonProperty("callingPreferredDaysTypeName")
    private String preferredCallingDays;
    @JsonProperty("callingPreferredTime")
    private String preferredCallingTime;
    @JsonProperty("contactTypeSubTypeBeansList")
    private List<ContactTypeDetails> contactTypeDetails;
    @JsonProperty("contactNumbersInfoList")
    private List<ContactNumberInfo> contactNumbersInfo;
    @JsonProperty("mynmRoleFunctionsBeanList")
    private List<RoleModule> rolesModules;
    @JsonProperty("notificationsModulesList")
    private List<NotificationModule> notificationsModules;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContactSugarId() {
        return contactSugarId;
    }

    public void setContactSugarId(String contactSugarId) {
        this.contactSugarId = contactSugarId;
    }

    public String getIsMyNmUser() {
        return isMyNmUser;
    }

    public void setIsMyNmUser(String isMyNmUser) {
        this.isMyNmUser = isMyNmUser;
    }

    public String getIsActiveUser() {
        return isActiveUser;
    }

    public void setIsActiveUser(String isActiveUser) {
        this.isActiveUser = isActiveUser;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
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

    public String getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(String kycStatus) {
        this.kycStatus = kycStatus;
    }

    public String getAccessCardStatus() {
        return accessCardStatus;
    }

    public void setAccessCardStatus(String accessCardStatus) {
        this.accessCardStatus = accessCardStatus;
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

    public List<ContactNumberInfo> getContactNumbersInfo() {
        return contactNumbersInfo;
    }

    public void setContactNumbersInfo(List<ContactNumberInfo> contactNumbersInfo) {
        this.contactNumbersInfo = contactNumbersInfo;
    }

    public List<RoleModule> getRolesModules() {
        return rolesModules;
    }

    public void setRolesModules(List<RoleModule> rolesModules) {
        this.rolesModules = rolesModules;
    }

    public List<NotificationModule> getNotificationsModules() {
        return notificationsModules;
    }

    public void setNotificationsModules(List<NotificationModule> notificationsModules) {
        this.notificationsModules = notificationsModules;
    }
}
