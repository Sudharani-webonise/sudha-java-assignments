package com.netmagic.spectrum.dto.outstanding.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author webonsie
 *
 */
public class ShoppingCartPaymentRequest implements Serializable {

    private static final long serialVersionUID = 5305736364885818602L;

    @JsonProperty("transactionId")
    private String transactionId;
    @JsonProperty("transactionAmount")
    private Double transactionAmount;
    @JsonProperty("creditUsed")
    private Double creditUsed;
    @JsonProperty("ebsPaymentId")
    private String ebsPaymentId;
    @JsonProperty("ebsPaymentAmount")
    private Double ebsPaymentAmount;
    @JsonProperty("dateAndTime")
    private String dateAndTime;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("userFirstName")
    private String userFirstName;
    @JsonProperty("userLastName")
    private String userLastName;
    @JsonProperty("userEmailId")
    private String userEmailId;
    @JsonProperty("sofDetail")
    private List<SofDetail> sofDetail = new ArrayList<>();

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Double getCreditUsed() {
        return creditUsed;
    }

    public void setCreditUsed(Double creditUsed) {
        this.creditUsed = creditUsed;
    }

    public String getEbsPaymentId() {
        return ebsPaymentId;
    }

    public void setEbsPaymentId(String ebsPaymentId) {
        this.ebsPaymentId = ebsPaymentId;
    }

    public Double getEbsPaymentAmount() {
        return ebsPaymentAmount;
    }

    public void setEbsPaymentAmount(Double ebsPaymentAmount) {
        this.ebsPaymentAmount = ebsPaymentAmount;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
    }

    public List<SofDetail> getSofDetail() {
        return sofDetail;
    }

    public void setSofDetail(List<SofDetail> sofDetail) {
        this.sofDetail = sofDetail;
    }

}
