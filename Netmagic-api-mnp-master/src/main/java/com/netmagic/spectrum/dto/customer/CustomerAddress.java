package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerAddress implements Serializable {

    private static final long serialVersionUID = 5204807121809619229L;

    @JsonProperty("custAddressDetailsId")
    private Long custAddressDetailsId;
    @JsonProperty("companyAddress")
    private String companyAddress;
    @JsonProperty("country")
    private String country;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("pinCode")
    private String pinCode;
    @JsonProperty("phoneNo")
    private List<String> phoneNo;
    @JsonProperty("companyURL")
    private String companyURL;

    public Long getCustAddressDetailsId() {
        return custAddressDetailsId;
    }

    public void setCustAddressDetailsId(Long custAddressDetailsId) {
        this.custAddressDetailsId = custAddressDetailsId;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public List<String> getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(List<String> phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCompanyURL() {
        return companyURL;
    }

    public void setCompanyURL(String companyURL) {
        this.companyURL = companyURL;
    }
}
