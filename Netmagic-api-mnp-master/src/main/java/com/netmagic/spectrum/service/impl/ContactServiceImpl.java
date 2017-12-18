package com.netmagic.spectrum.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Contacts;
import com.netmagic.spectrum.dto.contact.request.AddContactRequest;
import com.netmagic.spectrum.dto.contact.request.EditContactRequest;
import com.netmagic.spectrum.dto.contact.request.InviteContact;
import com.netmagic.spectrum.dto.contact.request.ListRequest;
import com.netmagic.spectrum.dto.contact.request.WidgetRequest;
import com.netmagic.spectrum.dto.contact.response.CachedContactSubTypes;
import com.netmagic.spectrum.dto.contact.response.CallingConfig;
import com.netmagic.spectrum.dto.contact.response.ContactDetails;
import com.netmagic.spectrum.dto.contact.response.ContactResponse;
import com.netmagic.spectrum.dto.contact.response.ContactSubTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactType;
import com.netmagic.spectrum.dto.contact.response.ContactTypeList;
import com.netmagic.spectrum.dto.contact.response.ContactWidget;
import com.netmagic.spectrum.dto.contact.response.InternalUserContact;
import com.netmagic.spectrum.dto.contact.response.ListResponse;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    private static final String IT_OPERATIONS = "IT Operations";

    private static final String IT_MANAGMENT = "IT Management";

    private static final String BILLING = "Billing";

    private static final String ASSOCIATE_CUSTOMER = "&associateCustomerId=";

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CacheService<CachedContactSubTypes> cachedContactSubTypeService;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Override
    public ContactWidget getWidgetData(WidgetRequest widgetRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.WIDGET.getURL());
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            ContactWidget contactWidget = apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(widgetRequest), Collections.emptyMap(), ContactWidget.class);
            if ( contactWidget == null ) {
                throw new NullPointerException("Contact Widget Data is null");
            }
            List<ContactType> contactTypeList = contactWidget.getContactTypes().stream()
                    .filter(contactType -> contactType.getContactType().equalsIgnoreCase(IT_OPERATIONS)
                            || contactType.getContactType().equalsIgnoreCase(IT_MANAGMENT)
                            || contactType.getContactType().equalsIgnoreCase(BILLING))
                    .collect(Collectors.toList());
            contactWidget.setContactTypes(contactTypeList);
            return contactWidget;
        } catch (JsonProcessingException | NullPointerException ex) {
            LOGGER.error("Error in json processing while fetching Contact Widget Data {} ", ex);
            throw new RequestViolationException("Error in processing  while fetching Contact Widget Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for contact URI");
        }
    }

    @Override
    public ListResponse getListData(ListRequest listRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.LIST.getURL());
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(listRequest),
                    Collections.emptyMap(), ListResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error json in processing while fetching Contact List Data", ex);
            throw new RequestViolationException("Error in processing Contact List Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for contact URI");
        }
    }

    @Override
    public ContactDetails getContactDetails(Long userId) throws ServiceUnavailableException {
        try {
            String url = String.format(Param.THREE.getParam(), apiBaseUrl, Contacts.DETAILS.getURL(), userId);
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    ContactDetails.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for contact URI");
        }
    }

    @Override
    public ContactResponse updateContactInformation(EditContactRequest updatedContactDetails)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.UPDATE.getURL());
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(updatedContactDetails), Collections.emptyMap(), ContactResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while updating new Contact Info", ex);
            throw new RequestViolationException("Error in json processing while updating new Contact Info");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("no internet to access the resource");
        }
    }

    @Override
    public ContactResponse addContact(AddContactRequest addContactRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.ADD.getURL());
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(addContactRequest), Collections.emptyMap(), ContactResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while creating new Contact Info", ex);
            throw new RequestViolationException("Error in json processing while creating new Contact Info");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for contact URI");
        }
    }

    @Override
    public ContactResponse inviteUser(InviteContact inviteContactRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.INVITE_CONTACT.getURL());
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(inviteContactRequest), Collections.emptyMap(), ContactResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while inviting new Contact Info", ex);
            throw new RequestViolationException("Error in json processing while inviting new Contact Info");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for contact URI");
        }
    }

    @Override
    public ContactTypeList getContactTypes() throws ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.TYPES.getURL());
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    ContactTypeList.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for contact URI");
        }
    }

    @Override
    public ContactSubTypeList getContactSubTypes(Long typeId) throws ServiceUnavailableException {
        try {
            CachedContactSubTypes cachedContactSubTypes = cachedContactSubTypeService
                    .get(new CachedContactSubTypes(typeId));
            if ( cachedContactSubTypes == null ) {
                String url = String.format(Param.THREE.getParam(), apiBaseUrl, Contacts.SUB_TYPES.getURL(), typeId);
                ContactSubTypeList subTypeList = apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                        Collections.emptyMap(), ContactSubTypeList.class);
                CachedContactSubTypes contactSubTypes = new CachedContactSubTypes(typeId);
                contactSubTypes.setContactSubTypes(subTypeList);
                cachedContactSubTypeService.save(contactSubTypes);
                return subTypeList;
            }
            return cachedContactSubTypes.getContactSubTypes();
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for contact URI");
        }
    }

    @Override
    public CallingConfig getContactCallingConfig() throws ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), apiBaseUrl, Contacts.CALLING_CONFIG.getURL());
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    CallingConfig.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for contact URI");
        }
    }

    @Override
    public InternalUserContact getInternalUserContacts(Long mainCustomerId, Long associatedCustomerId) {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Contacts.INTERNAL_CONTACTS.getURL(),
                    mainCustomerId, ASSOCIATE_CUSTOMER, associatedCustomerId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), InternalUserContact.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

}
