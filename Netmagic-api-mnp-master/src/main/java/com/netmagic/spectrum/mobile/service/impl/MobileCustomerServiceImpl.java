package com.netmagic.spectrum.mobile.service.impl;

import java.util.ArrayList;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Customer;
import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.ContactRequest;
import com.netmagic.spectrum.dto.customer.MobileContact;
import com.netmagic.spectrum.dto.customer.MobileProject;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.mobile.service.MobileCustomerService;
import com.netmagic.spectrum.service.impl.CustomerServiceImpl;

@Component
public class MobileCustomerServiceImpl implements MobileCustomerService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private AuthUser authUser;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private static final String ACCESS_KEY_URL = "?accesskey=";

    private static final String ASSOCIATE_CUSTOMER = "&associateCustomerId=";

    private static final String USER_ID = "&userId=";

    @Value("${accessKey}")
    private String accessKey;

    @Override
    public AssociatedCustomerListResponse getAssociatedMobileCustomers(Long customerId)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl,
                    Customer.ASSOCIATE_CUSTOMERS.getURL(), authUser.getAuthenticatedUserId());
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), AssociatedCustomerListResponse.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public MobileProject getMobileProjects(Long customerId, Long associatedCustomerId)
            throws ServiceUnavailableException {
        try {
            MobileProject projects = new MobileProject();
            String queryString = String.format(Param.SEVEN.getParam(), apiBaseUrl, Customer.PROJECTS.getURL(),
                    customerId, ASSOCIATE_CUSTOMER, associatedCustomerId, USER_ID, authUser.getAuthenticatedUserId());
            projects.setProjects(apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), new ParameterizedTypeReference<ArrayList<Project>>() {
                    }));
            return projects;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public MobileContact getMobileContacts(Long customerId, Long associatedCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            MobileContact mobileContact = new MobileContact();
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            ContactRequest contactRequest = new ContactRequest();
            contactRequest.setCallingCustCrmId(customerId);
            contactRequest.setAssociatCustCrmId(associatedCustomerId);
            contactRequest.setProjectName(projectName);
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Customer.CONTACTS.getURL(),
                    ACCESS_KEY_URL, accessKey);
            mobileContact.setContacts(apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(contactRequest), Collections.emptyMap(),
                    new ParameterizedTypeReference<ArrayList<Contact>>() {
                    }));
            return mobileContact;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing contact data JSON ", ex);
            throw new RequestViolationException("Error in processing contact data JSON");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }

    }
}