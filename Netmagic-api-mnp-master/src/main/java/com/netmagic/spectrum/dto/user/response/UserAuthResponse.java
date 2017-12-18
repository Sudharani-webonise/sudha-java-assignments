package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the request object for the user authentication Response
 * 
 * @author webonise
 * @version Spectrum 1.0
 */
public class UserAuthResponse implements Serializable {

    private static final long serialVersionUID = -2175508668649887173L;

    private String authToken;

    private boolean isExistingUser;

    @JsonProperty("loginUserId")
    private Long userId;

    @JsonProperty("loginUserName")
    private String userName;

    @JsonProperty("loginUserEmail")
    private String userEmail;

    @JsonProperty("apiKey")
    private String apiKey;

    @JsonProperty("secretKey")
    private String secretKey;

    @JsonProperty("customerSplashPageDataBeans")
    private List<CustomerDetails> customerDetails;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public boolean isExistingUser() {
        return isExistingUser;
    }

    public void setExistingUser(boolean isExistingUser) {
        this.isExistingUser = isExistingUser;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public List<CustomerDetails> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetails> customerDetails) {
        this.customerDetails = customerDetails;
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
