package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the permission assigned to a internal user
 * 
 * @author webonise
 *
 */
public class InternalUserPermissions implements Serializable {

    private static final long serialVersionUID = 2208654284163705246L;

    @JsonProperty("roleId")
    private Long roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("mynmUserModuleAccessBean")
    private List<ModuleInformation> moduleInformations;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<ModuleInformation> getModuleInformations() {
        return moduleInformations;
    }

    public void setModuleInformations(List<ModuleInformation> moduleInformations) {
        this.moduleInformations = moduleInformations;
    }

}
