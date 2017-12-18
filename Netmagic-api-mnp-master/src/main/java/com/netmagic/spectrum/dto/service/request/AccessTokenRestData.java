package com.netmagic.spectrum.dto.service.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the rest data required for access token generation API in
 * service ledger
 * 
 * @author webonise
 *
 */
public class AccessTokenRestData implements Serializable {

    private static final long serialVersionUID = -6424376141979801943L;

    @JsonProperty("user")
    private String user;

    @JsonProperty("authkey")
    private String authorizationKey;

    public AccessTokenRestData() {
        super();
    }

    public AccessTokenRestData(String user, String authorizationKey) {
        super();
        this.user = user;
        this.authorizationKey = authorizationKey;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAuthorizationKey() {
        return authorizationKey;
    }

    public void setAuthorizationKey(String authorizationKey) {
        this.authorizationKey = authorizationKey;
    }
}
