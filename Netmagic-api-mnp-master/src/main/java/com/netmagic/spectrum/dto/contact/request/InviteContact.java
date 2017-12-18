package com.netmagic.spectrum.dto.contact.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.contact.response.ContactTypeDetails;
import com.netmagic.spectrum.dto.contact.response.NotificationModule;
import com.netmagic.spectrum.dto.contact.response.RoleModule;

/**
 * This class holds the value of a inviting new Contact
 * 
 * @author webonise
 *
 */
public class InviteContact implements Serializable {

    private static final long serialVersionUID = -1622415310762209622L;

    @JsonProperty("mainCustCrmId")
    private String mainCustCrmId;
    @JsonProperty("mainCustName")
    private String mainCustName;
    @JsonProperty("primaryEmailId")
    private String primaryEmailId;
    @JsonProperty("contactTypeSubTypeBeansList")
    private List<ContactTypeDetails> contactTypeDetails;
    @JsonProperty("mynmRoleFunctionsBeanList")
    private List<RoleModule> rolesModules;
    @JsonProperty("notificationsModulesList")
    private List<NotificationModule> notificationsModules;

    public String getMainCustCrmId() {
        return mainCustCrmId;
    }

    public void setMainCustCrmId(String mainCustCrmId) {
        this.mainCustCrmId = mainCustCrmId;
    }

    public String getMainCustName() {
        return mainCustName;
    }

    public void setMainCustName(String mainCustName) {
        this.mainCustName = mainCustName;
    }

    public String getPrimaryEmailId() {
        return primaryEmailId;
    }

    public void setPrimaryEmailId(String primaryEmailId) {
        this.primaryEmailId = primaryEmailId;
    }

    public List<ContactTypeDetails> getContactTypeDetails() {
        return contactTypeDetails;
    }

    public void setContactTypeDetails(List<ContactTypeDetails> contactTypeDetails) {
        this.contactTypeDetails = contactTypeDetails;
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
