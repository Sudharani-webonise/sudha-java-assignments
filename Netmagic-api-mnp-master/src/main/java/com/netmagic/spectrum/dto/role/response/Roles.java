package com.netmagic.spectrum.dto.role.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the response object for the role Information view details of
 * role id role name and role function
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
public class Roles implements Serializable {

    private static final long serialVersionUID = -5460528706437870138L;

    @JsonProperty("roleId")
    private Long roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleRelatedFunctions")
    private List<Integer> roleFunctions;

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

    public List<Integer> getRoleFunctions() {
        return roleFunctions;
    }

    public void setRoleFunctions(List<Integer> roleFunctions) {
        this.roleFunctions = roleFunctions;
    }
}
