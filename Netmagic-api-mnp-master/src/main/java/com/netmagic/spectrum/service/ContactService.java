package com.netmagic.spectrum.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.netmagic.spectrum.dto.contact.request.AddContactRequest;
import com.netmagic.spectrum.dto.contact.request.EditContactRequest;
import com.netmagic.spectrum.dto.contact.request.InviteContact;
import com.netmagic.spectrum.dto.contact.request.ListRequest;
import com.netmagic.spectrum.dto.contact.request.WidgetRequest;
import com.netmagic.spectrum.dto.contact.response.CallingConfig;
import com.netmagic.spectrum.dto.contact.response.ContactDetails;
import com.netmagic.spectrum.dto.contact.response.ContactResponse;
import com.netmagic.spectrum.dto.contact.response.ContactSubTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactWidget;
import com.netmagic.spectrum.dto.contact.response.InternalUserContact;
import com.netmagic.spectrum.dto.contact.response.ListResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface ContactService {
    /**
     * This method fetches the contacts Widget Data from NM Servers
     * 
     * @param widgetRequest
     * @return {@link ContactWidget}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('CNT_WDGT')")
    ContactWidget getWidgetData(WidgetRequest widgetRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the contacts List Data from NM Servers
     * 
     * @param listRequest
     * @return {@link ListResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('CNT_LIST')")
    ListResponse getListData(ListRequest listRequest) throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the contacts Detail Data from NM Servers
     *
     * @param userId
     * @return Contact Details
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('CNT_DTLS')")
    ContactDetails getContactDetails(Long userId) throws ServiceUnavailableException;

    /**
     * This method sends request to NM server to add a new contact. Addition of
     * contact can be success or failure.
     *
     * @param addContactRequest
     * @return
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */

    @PreAuthorize("hasAuthority('CNT_ADD')")
    ContactResponse addContact(AddContactRequest addContactRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method sends request to NM server to update a existing contact.
     * Updating of contact can be success or failure.
     *
     * @param updatedContactRequest
     * @return SUCCESS/FAILURE
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('CNT_EDIT')")
    ContactResponse updateContactInformation(EditContactRequest updatedContactRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method sends request to NM Server to send an Invite to a Contact
     * 
     * @param inviteContactRequest
     * @return {@link ContactResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    ContactResponse inviteUser(InviteContact inviteContactRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method gets all the Contact Types
     * 
     * @return {@link ContactTypeList}
     * @throws ServiceUnavailableException
     */
    ContactTypeList getContactTypes() throws ServiceUnavailableException;

    /**
     * This method gets the Contact Sub Types for a particular Contact Type
     * 
     * @param typeId
     * @return {@link ContactSubTypeList}
     * @throws ServiceUnavailableException
     */
    ContactSubTypeList getContactSubTypes(Long typeId) throws ServiceUnavailableException;

    /**
     * This method gets the Contact Calling Configuration
     * 
     * @return {@link CallingConfig}
     * @throws ServiceUnavailableException
     */
    CallingConfig getContactCallingConfig() throws ServiceUnavailableException;

    /**
     * This method fetch the information of contact details of internal users
     * based on mail customer id and associated customer id
     * 
     * @param mainCustomerId
     * @param associatedCustomerId
     * @return
     * @throws ServiceUnavailableException
     */
    InternalUserContact getInternalUserContacts(Long mainCustomerId, Long associatedCustomerId)
            throws ServiceUnavailableException;
}
