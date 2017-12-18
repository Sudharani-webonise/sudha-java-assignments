package com.netmagic.spectrum.dto.user.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class maps the response when a Lead is converted to an opportunity
 * 
 * @author webonise
 *
 */
public class SignUpResponse implements Serializable {

    private static final long serialVersionUID = 3064543694108743498L;

    @JsonProperty("oid")
    private String oid;
    @JsonProperty("koid")
    private String sugarOpportunityId;
    @JsonProperty("acid")
    private String accountId;
    @JsonProperty("kacid")
    private String sugarAccountId;
    @JsonProperty("kcntid")
    private String sugarContactId;
    @JsonProperty("kaddress")
    private String address;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getSugarOpportunityId() {
        return sugarOpportunityId;
    }

    public void setSugarOpportunityId(String sugarOpportunityId) {
        this.sugarOpportunityId = sugarOpportunityId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getSugarAccountId() {
        return sugarAccountId;
    }

    public void setSugarAccountId(String sugarAccountId) {
        this.sugarAccountId = sugarAccountId;
    }

    public String getSugarContactId() {
        return sugarContactId;
    }

    public void setSugarContactId(String sugarContactId) {
        this.sugarContactId = sugarContactId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
