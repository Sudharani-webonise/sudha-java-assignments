package com.netmagic.spectrum.dto.asset.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class contains the data for the asset tag list request.
 * 
 * @author webonise
 *
 */
public class AssetTagListRequest implements Serializable {

    private static final long serialVersionUID = 6386155441292194851L;

    @JsonProperty("mainCustomerId")
    private Long customerId;
    @JsonProperty("associateCustomerId")
    private Long associateCustomerId;

    public AssetTagListRequest() {
        super();
    }

    public AssetTagListRequest(Long customerId, Long associateCustomerId) {
        super();
        this.customerId = customerId;
        this.associateCustomerId = associateCustomerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }
}
