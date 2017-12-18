package com.netmagic.spectrum.dto.contact.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains details of Role associated with a user
 * 
 * @author Pareekshit
 */
public class RoleModule implements Serializable {

    private static final long serialVersionUID = 4940356573684177646L;

    @JsonProperty("associteCustomerID")
    private Long associateCustomerId;
    @JsonProperty("associteCustomerName")
    private String associateCustomerName;
    @JsonProperty("projectId")
    private String projectId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("roleId")
    private Long roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("isActive")
    private String isActive;
    @JsonProperty("functionIds")
    private List<Long> functionIds;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public List<Long> getFunctionIds() {
        return functionIds;
    }

    public void setFunctionIds(List<Long> functionIds) {
        this.functionIds = functionIds;
    }
}
