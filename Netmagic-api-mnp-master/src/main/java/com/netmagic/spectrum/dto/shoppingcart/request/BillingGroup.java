package com.netmagic.spectrum.dto.shoppingcart.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to hold the products belonging to one billing group
 * 
 * @author webonise
 *
 */
public class BillingGroup implements Serializable {

    private static final long serialVersionUID = 4673818594315266531L;

    @JsonProperty("projectname")
    private String projectName;
    @JsonProperty("isExistingProject")
    private String isExistingProject;
    @JsonProperty("projectid")
    private String projectId;
    @JsonProperty("loc")
    private String location;
    @JsonProperty("cntrperiod")
    private String cntrperiod;
    @JsonProperty("cntrperiodunit")
    private String cntrperiodunit;
    @JsonProperty("start_date")
    private String startDate;
    @JsonProperty("end_date")
    private String endDate;
    @JsonProperty("billtoadd")
    private String mainCustomerAddress;
    @JsonProperty("supptoadd")
    private String associateCustomerAddress;
    @JsonProperty("bill_cont")
    private String billingContact;
    @JsonProperty("spoc_cont")
    private String spocContact;
    @JsonProperty("tech_cont")
    private String tecnicalContact;
    @JsonProperty("auth_sign")
    private String authorizeSignatory;
    @JsonProperty("selectSCFAction")
    private String scfAction;
    @JsonProperty("varGroupProdAddedOfType")
    private String variableProductTypeUom;
    @JsonProperty("items")
    private List<SugarProduct> products = new ArrayList<SugarProduct>();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIsExistingProject() {
        return isExistingProject;
    }

    public void setIsExistingProject(String isExistingProject) {
        this.isExistingProject = isExistingProject;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCntrperiod() {
        return cntrperiod;
    }

    public void setCntrperiod(String cntrperiod) {
        this.cntrperiod = cntrperiod;
    }

    public String getCntrperiodunit() {
        return cntrperiodunit;
    }

    public void setCntrperiodunit(String cntrperiodunit) {
        this.cntrperiodunit = cntrperiodunit;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMainCustomerAddress() {
        return mainCustomerAddress;
    }

    public void setMainCustomerAddress(String mainCustomerAddress) {
        this.mainCustomerAddress = mainCustomerAddress;
    }

    public String getAssociateCustomerAddress() {
        return associateCustomerAddress;
    }

    public void setAssociateCustomerAddress(String associateCustomerAddress) {
        this.associateCustomerAddress = associateCustomerAddress;
    }

    public String getBillingContact() {
        return billingContact;
    }

    public void setBillingContact(String billingContact) {
        this.billingContact = billingContact;
    }

    public String getSpocContact() {
        return spocContact;
    }

    public void setSpocContact(String spocContact) {
        this.spocContact = spocContact;
    }

    public String getTecnicalContact() {
        return tecnicalContact;
    }

    public void setTecnicalContact(String tecnicalContact) {
        this.tecnicalContact = tecnicalContact;
    }

    public String getAuthorizeSignatory() {
        return authorizeSignatory;
    }

    public void setAuthorizeSignatory(String authorizeSignatory) {
        this.authorizeSignatory = authorizeSignatory;
    }

    public String getScfAction() {
        return scfAction;
    }

    public void setScfAction(String scfAction) {
        this.scfAction = scfAction;
    }

    public String getVariableProductTypeUom() {
        return variableProductTypeUom;
    }

    public void setVariableProductTypeUom(String variableProductTypeUom) {
        this.variableProductTypeUom = variableProductTypeUom;
    }

    public List<SugarProduct> getProducts() {
        return products;
    }

    public void setProducts(List<SugarProduct> products) {
        this.products = products;
    }
}
