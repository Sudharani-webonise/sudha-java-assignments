package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class holds the information of associate customer which is fetched while
 * creating a ticket
 * 
 * @author webonise
 *
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 7299360647276144558L;
    @JsonProperty("cvName")
    private String name;
    @JsonProperty("cvCode")
    private String code;
    @JsonProperty("cvCrmId")
    private Long crmId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCrmId() {
        return crmId;
    }

    public void setCrmId(Long crmId) {
        this.crmId = crmId;
    }
}
