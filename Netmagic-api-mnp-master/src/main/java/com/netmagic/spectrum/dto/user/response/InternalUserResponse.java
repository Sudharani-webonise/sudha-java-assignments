package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.customer.Customer;

/**
 * This class maps the response for an internal user
 * 
 * @author webonise
 *
 */
@JsonIgnoreProperties("combinationId")
public class InternalUserResponse implements Serializable {

    private static final long serialVersionUID = -865806501214731072L;

    private String authToken;

    private String isInternalUser;

    @JsonProperty("loginUserId")
    private Long userId;
    @JsonProperty("userFirstName")
    private String userFirstName;
    @JsonProperty("userLastName")
    private String userLastName;
    @JsonProperty("loginUserEmail")
    private String userEmail;
    @JsonProperty("apiKey")
    private String apiKey;
    @JsonProperty("secretKey")
    private String secretKey;
    @JsonProperty("customerBasicHeaderBeanList")
    private List<Customer> customers;
    @JsonProperty("userRoleModulePermissionsBean")
    private InternalUserPermissions internalUserPermissions;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getIsInternalUser() {
        return isInternalUser;
    }

    public void setIsInternalUser(String isInternalUser) {
        this.isInternalUser = isInternalUser;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public InternalUserPermissions getInternalUserPermissions() {
        return internalUserPermissions;
    }

    public void setInternalUserPermissions(InternalUserPermissions internalUserPermissions) {
        this.internalUserPermissions = internalUserPermissions;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String fieldName, Object value) {
        this.additionalProperties.put(fieldName, value);
    }
}
