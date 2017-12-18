package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * this DTO used to give sso token response
 * 
 * @author webonsie
 *
 */
public class SingleSignOnToken implements Serializable {

    private static final long serialVersionUID = 4454439460320473647L;

    @JsonProperty("Token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
