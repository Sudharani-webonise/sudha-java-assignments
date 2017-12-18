package com.netmagic.spectrum.dto.user.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This DTO will store information required to SignUp a User.
 * 
 * @author webonise
 * 
 */
public class SignUpRequest implements Serializable {

    private static final long serialVersionUID = -1810582621367310046L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("emailId")
    private String emailId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("confirmPassword")
    private String confirmPassword;
    @JsonProperty("signUpAs")
    private String signUpAs;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("companyRegisteredAs")
    private String companyRegisteredAs;
    @JsonProperty("companyDunsNumber")
    private String companyDunsNumber;
    @JsonProperty("govtIdType")
    private String govtIdType;
    @JsonProperty("govtIdNo")
    private String govtIdNo;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("state")
    private String state;
    @JsonProperty("city")
    private String city;
    @JsonProperty("country")
    private String country;
    @JsonProperty("countryCode")
    private String countryCode;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("pincode")
    private String pincode;
    @JsonProperty("mobile")
    private String mobile;
    @JsonProperty("landline")
    private String landline;
    @JsonProperty("lid")
    private String lid;
    @JsonProperty("klid")
    private String sugarLid;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getSignUpAs() {
        return signUpAs;
    }

    public void setSignUpAs(String signUpAs) {
        this.signUpAs = signUpAs;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyRegisteredAs() {
        return companyRegisteredAs;
    }

    public void setCompanyRegisteredAs(String companyRegisteredAs) {
        this.companyRegisteredAs = companyRegisteredAs;
    }

    public String getCompanyDunsNumber() {
        return companyDunsNumber;
    }

    public void setCompanyDunsNumber(String companyDunsNumber) {
        this.companyDunsNumber = companyDunsNumber;
    }

    public String getGovtIdType() {
        return govtIdType;
    }

    public void setGovtIdType(String govtIdType) {
        this.govtIdType = govtIdType;
    }

    public String getGovtIdNo() {
        return govtIdNo;
    }

    public void setGovtIdNo(String govtIdNo) {
        this.govtIdNo = govtIdNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getSugarLid() {
        return sugarLid;
    }

    public void setSugarLid(String sugarLid) {
        this.sugarLid = sugarLid;
    }

}
