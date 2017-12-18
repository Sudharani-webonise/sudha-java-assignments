package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;

/**
 * This class maps the request for deleting a item from shopping cart
 * 
 * @author webonise
 *
 */
public class DeleteLineItemRequest implements Serializable {

    private static final long serialVersionUID = 5768918327406202539L;

    private String lineItemToken;
    private String productId;
    private String userEmailId;
    private String mainCustomerId;
    private String associateCustomerId;
    private String projectName;
    private String contractPeriod;
    private String location;

    public String getLineItemToken() {
        return lineItemToken;
    }

    public void setLineItemToken(String lineItemToken) {
        this.lineItemToken = lineItemToken;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserEmailId() {
        return userEmailId;
    }

    public void setUserEmailId(String userEmailId) {
        this.userEmailId = userEmailId;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(String contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
