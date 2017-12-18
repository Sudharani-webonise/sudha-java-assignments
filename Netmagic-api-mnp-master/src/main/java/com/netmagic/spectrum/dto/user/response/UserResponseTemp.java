package com.netmagic.spectrum.dto.user.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.dto.user.request.SignUpRequest;

/***
 * This DTO will contain the information about Signed up user stores in Cache.
 * 
 * @author webonise
 *
 */
@JsonIgnoreProperties({ "lid", "klid" })
public class UserResponseTemp extends SignUpRequest implements Cacheable {

    private static final long serialVersionUID = 5107150408010402826L;

    private static final String OBJECT_KEY = "USER";

    private String emailId;
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
    private String kaddress;

    public UserResponseTemp() {
        super();
    }

    public UserResponseTemp(String emailId) {
        this.emailId = emailId;
    }

    @Override
    public String getEmailId() {
        return emailId;
    }

    @Override
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

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

    public String getKaddress() {
        return kaddress;
    }

    public void setKaddress(String kaddress) {
        this.kaddress = kaddress;
    }

    @Override
    public String getCacheKey() {
        return emailId;
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }

}
