package com.netmagic.spectrum.dto.shoppingcart.response;

import java.io.Serializable;

/**
 * 
 * @author webonise
 *
 */
public class SofTransactionDetails implements Serializable {

    private static final long serialVersionUID = 1279811255774871670L;

    private String sofNo;
    private String projectName;
    private String contractPeriod;
    private String serviceType;
    private String provision;

    public SofTransactionDetails(String sofNo, String projectName, String contractPeriod, String serviceType) {
        super();
        this.sofNo = sofNo;
        this.projectName = projectName;
        this.contractPeriod = contractPeriod;
        this.serviceType = serviceType;
    }

    public SofTransactionDetails() {
        super();
    }

    public String getSofNo() {
        return sofNo;
    }

    public void setSofNo(String sofNo) {
        this.sofNo = sofNo;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getProvision() {
        return provision;
    }

    public void setProvision(String provision) {
        this.provision = provision;
    }

}
