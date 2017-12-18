package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This api maps the customer address details
 * 
 * @author webonise
 *
 */
public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = -6575260006067381695L;

    @JsonProperty("acc_ban_id")
    private String accBanId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("address_type")
    private String addressType;
    @JsonProperty("address")
    private String address;

    public String getAccBanId() {
        return accBanId;
    }

    public void setAccBanId(String accBanId) {
        this.accBanId = accBanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
