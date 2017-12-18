package com.netmagic.spectrum.service;

import java.util.List;

import com.netmagic.spectrum.dto.provision.LineItemDetails;
import com.netmagic.spectrum.dto.provision.SofDetails;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface ProvisionService {

    /***
     * This method will fetch the provisioning SOF details related to
     * Provisioning
     * 
     * @param billToCustomer
     * @param supportToCustomer
     * @param projectName
     * @return List<SofDetails>
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    List<SofDetails> getProvisionSofData(String billToCustomerId, String supportToCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException;

    /***
     * This method will fetch the provisioning Line Item details related to
     * Provisioning
     * 
     * @param sofNumber
     * @param lineItemNumber
     * @return LineItemDetails
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    LineItemDetails getProvisionLineItemData(Long sofNumber, Long lineItemNumber)
            throws RequestViolationException, ServiceUnavailableException;

}
