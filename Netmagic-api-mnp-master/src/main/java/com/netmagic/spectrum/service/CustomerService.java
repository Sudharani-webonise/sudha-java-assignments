package com.netmagic.spectrum.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.dto.customer.ShoppingCartProject;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

@Service
public interface CustomerService {
    /**
     * This method fetches the Associated Customers for a given Customer
     * 
     * @param mainCustomerId
     * @return List<AssociatedCustomerListResponse>
     * @throws ServiceUnavailableException
     */
    AssociatedCustomerListResponse getAssociatedCustomers(Long mainCustomerId) throws ServiceUnavailableException;

    /**
     * This method fetches the Projects for a given Customer and an Associated
     * Customer
     *
     * @param customerId
     * @param associatedCustomerId
     * @return List<Project>
     * @throws ServiceUnavailableException
     */
    List<Project> getProjects(Long customerId, Long associatedCustomerId) throws ServiceUnavailableException;

    /**
     * This method fetches the Contacts for a given customerId,
     * AssociatedCustomerId and projectName where projectName is optional
     *
     * @param customerId
     * @param associatedCustomerId
     * @param projectName
     * @return
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    List<Contact> getContacts(Long customerId, Long associatedCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method will get the combinationId for a given customerId,
     * associatedCustomerId, projectId
     * 
     * @param customerId
     * @param associatedCustomerId
     * @param projectId
     * @return Customer Id
     * @throws ServiceUnavailableException
     */
    String getCombinationId(Long customerId, Long associatedCustomerId, String projectId)
            throws ServiceUnavailableException;

    /***
     * This method will get the combinationId for customerId, assoCustomerId,
     * projectId and the fetch the customer details based on the combination id
     * 
     * @param customerId
     * @param assoCustomerId
     * @param projectId
     * @throws ServiceUnavailableException
     */
    BillingGroupResponse getBillToCutomerDetails(Long customerId, Long assoCustomerId, String projectId)
            throws ServiceUnavailableException;

    /**
     * This method fetches the all the Associated Customers for a given Customer
     * 
     * @return List<AssociatedCustomerListResponse>
     * @throws ServiceUnavailableException
     */
    AssociatedCustomerListResponse getAllAssociatedCustomers(Long customerId) throws ServiceUnavailableException;

    /**
     * This method fetches the all the Projects for a given Customer and an
     * Associated Customer
     *
     * @param customerId
     * @param associatedCustomerId
     * @return List<Project>
     * @throws ServiceUnavailableException
     */
    List<Project> getAllProjects(Long customerId, Long associatedCustomerId) throws ServiceUnavailableException;

    /**
     * This method fetches the all the Projects for a given Customer and an
     * Associated Customer as well as the new projects which are present in the
     * cart
     * 
     * @param shoppingCartProject
     * @return List<Project>
     * @throws ServiceUnavailableException
     */
    List<Project> getProjectsForCart(ShoppingCartProject shoppingCartProject) throws ServiceUnavailableException;

    /**
     * This method fetched the customer address details from helios
     * 
     * @param billToCustomer
     * @return
     * @throws ServiceUnavailableException
     */
    CustomerAddress getCustomerAddress(String billToCustomer) throws ServiceUnavailableException;

}
