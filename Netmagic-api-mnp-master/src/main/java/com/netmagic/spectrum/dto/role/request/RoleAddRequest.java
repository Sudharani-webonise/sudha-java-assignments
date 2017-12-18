package com.netmagic.spectrum.dto.role.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the role add details request
 *
 * @author Webonsie
 * @version Spectrum 1.0
 */
public class RoleAddRequest implements Serializable {

    private static final long serialVersionUID = -107407127779035574L;

    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("customerId")
    private Long customerId;
    @JsonProperty("creatorId")
    private Long userId;
    @JsonProperty("functionIds")
    private List<Integer> functions;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Integer> getFunctions() {
        return functions;
    }

    public void setFunctions(List<Integer> functions) {
        this.functions = functions;
    }
}
