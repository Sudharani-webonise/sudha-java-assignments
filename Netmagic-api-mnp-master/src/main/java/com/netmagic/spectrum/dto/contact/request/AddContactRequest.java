package com.netmagic.spectrum.dto.contact.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.contact.response.ContactNumberInfo;
import com.netmagic.spectrum.dto.contact.response.ContactTypeDetails;
import com.netmagic.spectrum.dto.contact.response.NotificationModule;
import com.netmagic.spectrum.dto.contact.response.RoleModule;

/**
 * This class holds the value of a new Contact
 * 
 * @author webonise
 *
 */
public class AddContactRequest implements Serializable {

    private static final long serialVersionUID = -9035733771156076913L;

    @JsonProperty("isMynmUser")
    private String isMynmUser;
    @JsonProperty("mainCustomerId")
    private Long mainCustCrmId;
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
    private Long callingPreferredDaysTypeId;
    @JsonProperty("callingPreferredDaysTypeName")
    private String callingPreferredDaysTypeName;
    @JsonProperty("callingPreferredTime")
    private String callingPreferredTime;
    @JsonProperty("contactTypeSubTypeBeansList")
    private List<ContactTypeDetails> contactTypeDetails;
    @JsonProperty("contactNumbersInfoList")
    private List<ContactNumberInfo> contactNumbersInfo;
    @JsonProperty("mynmRoleFunctionsBeanList")
    private List<RoleModule> rolesModules;
    @JsonProperty("notificationsModulesList")
    private List<NotificationModule> notificationsModules;

    public String getIsMynmUser() {
        return isMynmUser;
    }

    public void setIsMynmUser(String isMynmUser) {
        this.isMynmUser = isMynmUser;
    }

    public Long getMainCustCrmId() {
        return mainCustCrmId;
    }

    public void setMainCustCrmId(Long mainCustCrmId) {
        this.mainCustCrmId = mainCustCrmId;
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

    public Long getCallingPreferredDaysTypeId() {
        return callingPreferredDaysTypeId;
    }

    public void setCallingPreferredDaysTypeId(Long callingPreferredDaysTypeId) {
        this.callingPreferredDaysTypeId = callingPreferredDaysTypeId;
    }

    public String getCallingPreferredDaysTypeName() {
        return callingPreferredDaysTypeName;
    }

    public void setCallingPreferredDaysTypeName(String callingPreferredDaysTypeName) {
        this.callingPreferredDaysTypeName = callingPreferredDaysTypeName;
    }

    public String getCallingPreferredTime() {
        return callingPreferredTime;
    }

    public void setCallingPreferredTime(String callingPreferredTime) {
        this.callingPreferredTime = callingPreferredTime;
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
