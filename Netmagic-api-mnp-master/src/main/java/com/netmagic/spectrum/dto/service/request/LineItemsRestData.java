package com.netmagic.spectrum.dto.service.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the rest data required for line item listing API in
 * service ledger
 * 
 * @author webonise
 *
 */
public class LineItemsRestData implements Serializable {

    private static final long serialVersionUID = -641873528034089144L;
    @JsonProperty("token")
    private String token;

    @JsonProperty("contractnumber")
    private String contractNumber;

    @JsonProperty("statuscode")
    private String statusCode;

    @JsonProperty("page")
    private Long page;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }
}
