package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * this DTO used to give SSO token response
 * 
 * @author webonsie
 *
 */
public class SSOResponse implements Serializable {

    private static final long serialVersionUID = -2462494678763109132L;

    @JsonProperty("SingleSignOnToken")
    private SingleSignOnToken singleSignOnToken;

    public SingleSignOnToken getSingleSignOnToken() {
        return singleSignOnToken;
    }

    public void setSingleSignOnToken(SingleSignOnToken singleSignOnToken) {
        this.singleSignOnToken = singleSignOnToken;
    }

}
