package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the request data which is send during the call to NM server
 * to fetch Contact list
 * 
 * @author webonise
 *
 */
public class ContactRequest implements Serializable {

    private static final long serialVersionUID = 3342089987870054772L;

    @JsonProperty("callingCustCrmId")
    private Long callingCustCrmId;
    @JsonProperty("associatCustCrmId")
    private Long associatCustCrmId;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("retrieveBy")
    private String retrieveBy;

    public ContactRequest() {
        super();
    }

    public ContactRequest(Long callingCustCrmId, Long associatCustCrmId, String projectName) {
        super();
        this.callingCustCrmId = callingCustCrmId;
        this.associatCustCrmId = associatCustCrmId;
        this.projectName = projectName;
    }

    public Long getCallingCustCrmId() {
        return callingCustCrmId;
    }

    public void setCallingCustCrmId(Long callingCustCrmId) {
        this.callingCustCrmId = callingCustCrmId;
    }

    public Long getAssociatCustCrmId() {
        return associatCustCrmId;
    }

    public void setAssociatCustCrmId(Long associatCustCrmId) {
        this.associatCustCrmId = associatCustCrmId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRetrieveBy() {
        return retrieveBy;
    }

    public void setRetrieveBy(String retrieveBy) {
        this.retrieveBy = retrieveBy;
    }
}
