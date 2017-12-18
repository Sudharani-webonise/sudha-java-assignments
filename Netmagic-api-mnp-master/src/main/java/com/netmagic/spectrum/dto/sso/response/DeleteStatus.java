package com.netmagic.spectrum.dto.sso.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * delete status for shopping cart item
 * 
 * @author webonise
 *
 */
public class DeleteStatus implements Serializable {

    private static final long serialVersionUID = 4037267586591235413L;

    @JsonProperty("HttpCode")
    private Integer httpCode;
    @JsonProperty("StatusDesc")
    private String statusDesc;

    public Integer getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

}
