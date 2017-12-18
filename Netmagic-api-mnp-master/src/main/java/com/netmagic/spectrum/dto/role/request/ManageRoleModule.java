package com.netmagic.spectrum.dto.role.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.contact.response.RolePermission;

/**
 * This class maps the request object for the role name and list of Role
 * permission view details of request
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
public class ManageRoleModule implements Serializable {

    private static final long serialVersionUID = -1731600588889241641L;

    @JsonProperty("roleModuleName")
    private String roleModuleName;
    @JsonProperty("rolesFunctionBeans")
    private List<RolePermission> rolePermissions;

    public String getRoleModuleName() {
        return roleModuleName;
    }

    public void setRoleModuleName(String roleModuleName) {
        this.roleModuleName = roleModuleName;
    }

    public List<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(List<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
