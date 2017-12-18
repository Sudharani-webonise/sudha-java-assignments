package com.netmagic.spectrum.dto.service.response;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class represents the data of as SOF
 * 
 * @author webonise
 * 
 */
public class SOF implements Serializable {

    private static final long serialVersionUID = -3266356449213295854L;

    @JsonProperty("SOFId")
    private String id;

    @JsonProperty("ContractPeriod")
    private String contractPeriod;

    @JsonProperty("ContractPeriodUnit")
    private String contractPeriodUnit;

    @JsonProperty("SalesOrganization")
    private String salesOrganization;

    @JsonProperty("CompanyCode")
    private String companyCode;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("ContractStartDate")
    private String contractStartDate;

    @JsonProperty("ContractEndDate")
    private String contractEndDate;

    @JsonProperty("IsChangeRequested")
    private String isChangeRequested;

    @JsonProperty("ContractType")
    private String contractType;

    @JsonProperty("RefrenceSOFnumber")
    private String refrenceSOFnumber;

    @JsonProperty("ExpectedRenewal")
    private String expectedRenewal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(String contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getContractPeriodUnit() {
        return contractPeriodUnit;
    }

    public void setContractPeriodUnit(String contractPeriodUnit) {
        this.contractPeriodUnit = contractPeriodUnit;
    }

    public String getSalesOrganization() {
        return salesOrganization;
    }

    public void setSalesOrganization(String salesOrganization) {
        this.salesOrganization = salesOrganization;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(String contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getIsChangeRequested() {
        return isChangeRequested;
    }

    public void setIsChangeRequested(String isChangeRequested) {
        this.isChangeRequested = isChangeRequested;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getRefrenceSOFnumber() {
        return refrenceSOFnumber;
    }

    public void setRefrenceSOFnumber(String refrenceSOFnumber) {
        this.refrenceSOFnumber = refrenceSOFnumber;
    }

    public String getExpectedRenewal() {
        return expectedRenewal;
    }

    public void setExpectedRenewal(String expectedRenewal) {
        this.expectedRenewal = expectedRenewal;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

}
