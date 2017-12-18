package com.netmagic.spectrum.dto.provision;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is a response data for provision Line Item api
 * 
 * @author webonise
 */
public class LineItemDetails implements Serializable {

    private static final long serialVersionUID = -4292188484823638862L;

    @JsonProperty("token")
    private String token;
    @JsonProperty("sofNumber")
    private Long sofNumber;
    @JsonProperty("lineItemNumber")
    private Long lineItemNumber;
    @JsonProperty("lineItemProductCode")
    private String lineItemProductCode;
    @JsonProperty("serviceTermDays")
    private Long serviceTermDays;
    @JsonProperty("serviceTermMonth")
    private Long serviceTermMonth;
    @JsonProperty("location")
    private String location;
    @JsonProperty("taskNumber")
    private String taskNumber;
    @JsonProperty("customerId")
    private Long customerId;
    @JsonProperty("billToCustomer")
    private Long billToCustomer;
    @JsonProperty("quantity")
    private Double quantity;
    @JsonProperty("ipType")
    private String ipType;
    @JsonProperty("vmName")
    private String vmName;
    @JsonProperty("ipAddress")
    private String ipAddress;
    @JsonProperty("requestStatus")
    private String requestStatus;
    @JsonProperty("sofServiceContractSource")
    private String sofServiceContractSource;
    @JsonProperty("projectSugarId")
    private String projectSugarId;
    @JsonProperty("combinationId")
    private String combinationId;
    @JsonProperty("productInfoBeans")
    private List<ProductInfo> productInfoList;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getSofNumber() {
        return sofNumber;
    }

    public void setSofNumber(Long sofNumber) {
        this.sofNumber = sofNumber;
    }

    public String getLineItemProductCode() {
        return lineItemProductCode;
    }

    public void setLineItemProductCode(String lineItemProductCode) {
        this.lineItemProductCode = lineItemProductCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public Long getLineItemNumber() {
        return lineItemNumber;
    }

    public void setLineItemNumber(Long lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getBillToCustomer() {
        return billToCustomer;
    }

    public void setBillToCustomer(Long billToCustomer) {
        this.billToCustomer = billToCustomer;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getIpType() {
        return ipType;
    }

    public void setIpType(String ipType) {
        this.ipType = ipType;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Long getServiceTermDays() {
        return serviceTermDays;
    }

    public void setServiceTermDays(Long serviceTermDays) {
        this.serviceTermDays = serviceTermDays;
    }

    public Long getServiceTermMonth() {
        return serviceTermMonth;
    }

    public void setServiceTermMonth(Long serviceTermMonth) {
        this.serviceTermMonth = serviceTermMonth;
    }

    public String getCombinationId() {
        return combinationId;
    }

    public void setCombinationId(String combinationId) {
        this.combinationId = combinationId;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getSofServiceContractSource() {
        return sofServiceContractSource;
    }

    public void setSofServiceContractSource(String sofServiceContractSource) {
        this.sofServiceContractSource = sofServiceContractSource;
    }

    public String getProjectSugarId() {
        return projectSugarId;
    }

    public void setProjectSugarId(String projectSugarId) {
        this.projectSugarId = projectSugarId;
    }

    public List<ProductInfo> getProductInfoList() {
        return productInfoList;
    }

    public void setProductInfoList(List<ProductInfo> productInfoList) {
        this.productInfoList = productInfoList;
    }

}
