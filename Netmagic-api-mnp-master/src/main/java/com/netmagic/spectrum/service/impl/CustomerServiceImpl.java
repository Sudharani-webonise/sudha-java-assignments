package com.netmagic.spectrum.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Customer;
import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.ContactRequest;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.dto.customer.ShoppingCartProject;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
import com.netmagic.spectrum.dto.shoppingcart.response.BillingGroupPrice;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private CacheService<CachedShoppingCartDetails> cacheService;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${accessKey}")
    private String accessKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private static final String ACCESS_KEY_URL = "?accesskey=";

    private static final String ASSOCIATE_CUSTOMER = "&associateCustomerId=";

    private static final String USER_ID = "&userId=";

    private static final String CALLING_CUSTOMER_ID = "?callingCustomerId=";

    private static final String PROJECT_ID = "&projectId=";

    private static final String CUSTOMER_ID = "&customerId=";

    private static final String NO = "no";

    @Override
    public AssociatedCustomerListResponse getAssociatedCustomers(Long mainCustomerId)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Customer.ASSOCIATE_CUSTOMERS.getURL(),
                    authUser.getAuthenticatedUserId(), CUSTOMER_ID, mainCustomerId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), AssociatedCustomerListResponse.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public List<Project> getProjects(Long customerId, Long associatedCustomerId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.SEVEN.getParam(), apiBaseUrl, Customer.PROJECTS.getURL(),
                    customerId, ASSOCIATE_CUSTOMER, associatedCustomerId, USER_ID, authUser.getAuthenticatedUserId());
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), new ParameterizedTypeReference<ArrayList<Project>>() {
                    });
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public List<Contact> getContacts(Long customerId, Long associatedCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            ContactRequest contactRequest = new ContactRequest(customerId, associatedCustomerId, projectName);
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Customer.CONTACTS.getURL(),
                    ACCESS_KEY_URL, accessKey);
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(contactRequest), Collections.emptyMap(),
                    new ParameterizedTypeReference<ArrayList<Contact>>() {
                    });
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in processing contact data JSON ", ex);
            throw new RequestViolationException("Error in processing contact data JSON");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public BillingGroupResponse getBillToCutomerDetails(Long customerId, Long assoCustomerId, String projectId)
            throws ServiceUnavailableException {
        try {
            String combinationId = getCombinationId(customerId, assoCustomerId, projectId);
            String billToCustUrl = String.format(Param.THREE.getParam(), apiBaseUrl, Customer.VALIDATE_CUST_ID.getURL(),
                    combinationId);
            return apiClient.performGet(billToCustUrl, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), BillingGroupResponse.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for getBillToCutomerDetails URI");
        }
    }

    @Override
    public String getCombinationId(Long customerId, Long associatedCustomerId, String projectId)
            throws ServiceUnavailableException, RequestViolationException {
        try {
            String getCominationIdurl = String.format(Param.EIGHT.getParam(), apiBaseUrl,
                    Customer.COMBINATION_ID.getURL(), CALLING_CUSTOMER_ID, customerId, ASSOCIATE_CUSTOMER,
                    associatedCustomerId, PROJECT_ID, projectId);
            return apiClient.performGet(getCominationIdurl, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), String.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for getCombinationId URI");
        }
    }

    @Override
    public AssociatedCustomerListResponse getAllAssociatedCustomers(Long customerId)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl,
                    Customer.ASSOCIATE_CUSTOMERS_INTERNAL_API.getURL(), customerId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), AssociatedCustomerListResponse.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public List<Project> getAllProjects(Long customerId, Long associatedCustomerId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl,
                    Customer.PROJECTS_INTERNAL_API.getURL(), customerId, ASSOCIATE_CUSTOMER, associatedCustomerId);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), new ParameterizedTypeReference<ArrayList<Project>>() {
                    });
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer Service URI");
        }
    }

    @Override
    public List<Project> getProjectsForCart(ShoppingCartProject shoppingCartProject)
            throws ServiceUnavailableException {
        try {
            List<Project> projects = new ArrayList<Project>();
            if ( shoppingCartProject.getSugarMainCustomerId() != null
                    && shoppingCartProject.getAssociateCustomerId() != null ) {
                String queryString = String.format(Param.SEVEN.getParam(), apiBaseUrl, Customer.PROJECTS.getURL(),
                        shoppingCartProject.getMainCustomerId(), ASSOCIATE_CUSTOMER,
                        shoppingCartProject.getAssociateCustomerId(), USER_ID, authUser.getAuthenticatedUserId());
                projects = apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                        Collections.emptyMap(), new ParameterizedTypeReference<ArrayList<Project>>() {
                        });
            }
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(
                    shoppingCartProject.getUserEmail(), shoppingCartProject.getSugarMainCustomerId(),
                    shoppingCartProject.getSugarAssociateCustomerId());
            CachedShoppingCartDetails cachedCartData = cacheService.get(cacheShoppingBean);
            if ( cachedCartData != null ) {
                for ( BillingGroupPrice cachedBillingGroup : cachedCartData.getPriceResponseData()
                        .getBillingGroupPrices() ) {
                    if ( (cachedBillingGroup.getProjectId() == null || cachedBillingGroup.getProjectId().isEmpty())
                            && (cachedBillingGroup.getIsExistingProject() == null
                                    || cachedBillingGroup.getIsExistingProject().equalsIgnoreCase(NO)) ) {
                        Project project = new Project(cachedBillingGroup.getProjectName());
                        projects.add(project);
                    }
                }
            }
            return projects;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer projects Service URI");
        }
    }

    @Override
    public CustomerAddress getCustomerAddress(String billToCustomer) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, Customer.CUSTOMER_ADDRESS.getURL(),
                    billToCustomer);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), CustomerAddress.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for customer Service URI");
        }
    }

}
