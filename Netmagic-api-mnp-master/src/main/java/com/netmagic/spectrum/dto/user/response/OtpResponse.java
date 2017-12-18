package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the response after generation of token
 * 
 * @author webonise
 *
 */
public class OtpResponse implements Serializable {

    private static final long serialVersionUID = -5522180525922436950L;

    @JsonProperty("userEmail")
    private String userEmail;
    @JsonProperty("response")
    private String response;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
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
