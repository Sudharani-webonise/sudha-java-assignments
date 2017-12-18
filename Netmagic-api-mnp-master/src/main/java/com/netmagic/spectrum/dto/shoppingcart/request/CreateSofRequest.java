package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.netmagic.spectrum.dto.shoppingcart.response.BillingGroupPrice;

/**
 * This class maps the request required to create sof via shopping cart
 * 
 * @author webonise
 *
 */
public class CreateSofRequest implements Serializable {

    private static final long serialVersionUID = 4303562173398031937L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("acc")
    private String billToCustomerId;
    @JsonProperty("suppto")
    private String supportToCustomer;
    @JsonProperty("sofToken")
    private String sofTokenId;
    @JsonProperty("billgroup")
    private List<BillingGroupPrice> billingGroupPrices;

    public CreateSofRequest() {
    }

    public CreateSofRequest(String token, String billToCustomerId, String supportToCustomer) {
        super();
        this.token = token;
        this.billToCustomerId = billToCustomerId;
        this.supportToCustomer = supportToCustomer;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBillToCustomerId() {
        return billToCustomerId;
    }

    public void setBillToCustomerId(String billToCustomerId) {
        this.billToCustomerId = billToCustomerId;
    }

    public String getSupportToCustomer() {
        return supportToCustomer;
    }

    public void setSupportToCustomer(String supportToCustomer) {
        this.supportToCustomer = supportToCustomer;
    }

    public String getSofTokenId() {
        return sofTokenId;
    }

    public void setSofTokenId(String sofTokenId) {
        this.sofTokenId = sofTokenId;
    }

    public List<BillingGroupPrice> getBillingGroupPrices() {
        return billingGroupPrices;
    }

    public void setBillingGroupPrices(List<BillingGroupPrice> billingGroupPrices) {
        this.billingGroupPrices = billingGroupPrices;
    }
}
