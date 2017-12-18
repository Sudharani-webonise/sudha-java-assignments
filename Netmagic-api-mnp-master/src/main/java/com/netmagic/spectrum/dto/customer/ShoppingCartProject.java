package com.netmagic.spectrum.dto.customer;

import java.io.Serializable;

public class ShoppingCartProject implements Serializable {

    private static final long serialVersionUID = -2700894383334844069L;

    private Long mainCustomerId;

    private Long associateCustomerId;

    private String sugarMainCustomerId;

    private String sugarAssociateCustomerId;

    private String userEmail;

    public Long getMainCustomerId() {
        return mainCustomerId;
    }

    public void setMainCustomerId(Long mainCustomerId) {
        this.mainCustomerId = mainCustomerId;
    }

    public Long getAssociateCustomerId() {
        return associateCustomerId;
    }

    public void setAssociateCustomerId(Long associateCustomerId) {
        this.associateCustomerId = associateCustomerId;
    }

    public String getSugarMainCustomerId() {
        return sugarMainCustomerId;
    }

    public void setSugarMainCustomerId(String sugarMainCustomerId) {
        this.sugarMainCustomerId = sugarMainCustomerId;
    }

    public String getSugarAssociateCustomerId() {
        return sugarAssociateCustomerId;
    }

    public void setSugarAssociateCustomerId(String sugarAssociateCustomerId) {
        this.sugarAssociateCustomerId = sugarAssociateCustomerId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
