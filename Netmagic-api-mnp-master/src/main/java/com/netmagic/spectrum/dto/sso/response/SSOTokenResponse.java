package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * this DTO used to give SSO token response
 * 
 * @author webonsie
 *
 */
public class SSOTokenResponse implements Serializable {

    private static final long serialVersionUID = -4791130912007714815L;

    @JsonProperty("Response")
    private SSOResponse response;

    public SSOResponse getResponse() {
        return response;
    }

    public void setResponse(SSOResponse response) {
        this.response = response;
    }

}
