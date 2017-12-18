package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class is used to hold the pricing information for a products belonging
 * to one billing group
 * 
 * @author webonise
 *
 */
public class BillingGroupPrice implements Serializable {

    private static final long serialVersionUID = -4871685147421519757L;

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
    private List<ProductPrice> products;
    @JsonProperty("pmrc")
    private Double totalMonthlyRecurringCost;
    @JsonProperty("potc")
    private Double totalOneTimeCost;
    @JsonProperty("pmrc_sale")
    private Double totalMonthlyRecurringCostSale;
    @JsonProperty("potc_sale")
    private Double totalOneTimeCostSale;
    @JsonProperty("pmrc_tax")
    private Double totalMonthlyRecurringCostWithTax;
    @JsonProperty("potc_tax")
    private Double totalOneTimeCostWithTax;
    @JsonProperty("poc")
    private String poc;
    @JsonProperty("pslctg")
    private String pslctg;

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

    public List<ProductPrice> getProducts() {
        return products;
    }

    public void setProducts(List<ProductPrice> products) {
        this.products = products;
    }

    public Double getTotalMonthlyRecurringCost() {
        return totalMonthlyRecurringCost;
    }

    public void setTotalMonthlyRecurringCost(Double totalMonthlyRecurringCost) {
        this.totalMonthlyRecurringCost = totalMonthlyRecurringCost;
    }

    public Double getTotalOneTimeCost() {
        return totalOneTimeCost;
    }

    public void setTotalOneTimeCost(Double totalOneTimeCost) {
        this.totalOneTimeCost = totalOneTimeCost;
    }

    public Double getTotalMonthlyRecurringCostSale() {
        return totalMonthlyRecurringCostSale;
    }

    public void setTotalMonthlyRecurringCostSale(Double totalMonthlyRecurringCostSale) {
        this.totalMonthlyRecurringCostSale = totalMonthlyRecurringCostSale;
    }

    public Double getTotalOneTimeCostSale() {
        return totalOneTimeCostSale;
    }

    public void setTotalOneTimeCostSale(Double totalOneTimeCostSale) {
        this.totalOneTimeCostSale = totalOneTimeCostSale;
    }

    public Double getTotalMonthlyRecurringCostWithTax() {
        return totalMonthlyRecurringCostWithTax;
    }

    public void setTotalMonthlyRecurringCostWithTax(Double totalMonthlyRecurringCostWithTax) {
        this.totalMonthlyRecurringCostWithTax = totalMonthlyRecurringCostWithTax;
    }

    public Double getTotalOneTimeCostWithTax() {
        return totalOneTimeCostWithTax;
    }

    public void setTotalOneTimeCostWithTax(Double totalOneTimeCostWithTax) {
        this.totalOneTimeCostWithTax = totalOneTimeCostWithTax;
    }

    public String getPoc() {
        return poc;
    }

    public void setPoc(String poc) {
        this.poc = poc;
    }

    public String getPslctg() {
        return pslctg;
    }

    public void setPslctg(String pslctg) {
        this.pslctg = pslctg;
    }

}
