package com.netmagic.spectrum.mobile.service;

import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.MobileContact;
import com.netmagic.spectrum.dto.customer.MobileProject;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface MobileCustomerService {
    /**
     * This method fetches the Associated Customers for a given Customer for
     * mobile use
     *
     * @param customerId
     * @return List<AssociatedCustomerListResponse>
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    AssociatedCustomerListResponse getAssociatedMobileCustomers(Long customerId) throws ServiceUnavailableException;

    /**
     * This method fetches the Projects for a given Customer and an Associated
     * for mobile use Customer
     *
     * @param customerId
     * @param associatedCustomerId
     * @return MobileProject
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    MobileProject getMobileProjects(Long customerId, Long associatedCustomerId) throws ServiceUnavailableException;

    /**
     * This method fetches the Contacts for a given customerId,
     * AssociatedCustomerId and projectName where projectName is optional for
     * mobile user
     *
     * @param customerId
     * @param associatedCustomerId
     * @param projectName
     * @return
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    MobileContact getMobileContacts(Long customerId, Long associatedCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException;
}
