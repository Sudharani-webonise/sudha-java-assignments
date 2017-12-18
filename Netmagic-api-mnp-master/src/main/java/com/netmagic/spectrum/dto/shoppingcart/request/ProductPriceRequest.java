package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used for mapping the Product price request which is to be sent
 * to Sugar CRM
 * 
 * @author webonise
 *
 */
public class ProductPriceRequest implements Serializable {

    private static final long serialVersionUID = 8547198900987894761L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("acc")
    private String mainCustomerId;
    @JsonProperty("suppto")
    private String associateCustomerId;
    @JsonProperty("billgroup")
    private List<BillingGroup> billingGroups;

    public ProductPriceRequest() {
        super();
    }

    public ProductPriceRequest(String mainCustomerId, String associateCustomerId, List<BillingGroup> billingGroups) {
        super();
        this.mainCustomerId = mainCustomerId;
        this.associateCustomerId = associateCustomerId;
        this.billingGroups = billingGroups;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(String mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public String getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(String associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public List<BillingGroup> getBillingGroups() {
        return billingGroups;
    }

    public void setBillingGroups(List<BillingGroup> billingGroups) {
        this.billingGroups = billingGroups;
    }

}
