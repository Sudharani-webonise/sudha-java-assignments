package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the details of the documents which are to be verified for a
 * on boarding user
 * 
 * @author webonise
 *
 */
public class DocumentDetails implements Serializable {

    private static final long serialVersionUID = 4730309082240012028L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("idType")
    private String idType;
    @JsonProperty("idDetails")
    private String idDetails;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdDetails() {
        return idDetails;
    }

    public void setIdDetails(String idDetails) {
        this.idDetails = idDetails;
    }
}
