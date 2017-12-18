package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class stores the pricing response which is received from Sugar CRM
 * 
 * @author webonise
 *
 */
@JsonIgnoreProperties({ "poc", "pocnum" })
public class PriceResponseData implements Serializable {

    private static final long serialVersionUID = -6585435810687422936L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("acc")
    private String mainCustomerId;
    @JsonProperty("suppto")
    private String associateCustomerId;
    @JsonProperty("billgroup")
    private List<BillingGroupPrice> billingGroupPrices;

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

    public List<BillingGroupPrice> getBillingGroupPrices() {
        return billingGroupPrices;
    }

    public void setBillingGroupPrices(List<BillingGroupPrice> billingGroupPrices) {
        this.billingGroupPrices = billingGroupPrices;
    }

}
