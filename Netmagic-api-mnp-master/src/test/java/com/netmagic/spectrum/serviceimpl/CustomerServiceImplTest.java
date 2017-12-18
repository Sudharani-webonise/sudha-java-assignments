package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.core.ParameterizedTypeReference;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.dto.customer.ShoppingCartProject;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
import com.netmagic.spectrum.helpers.MockCachedData;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.CustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ CustomerServiceImpl.class })
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerServiceSpy;

    private static final String ASSOCIATED_CUSTOMER_LIST_RESPONSE = "responses/customer/associatedCustomerListResponse.json";

    private static final String PROJECT_LIST_RESPONSE = "responses/customer/projectListResponse.json";

    private static final String CONTACT_LIST_RESPONSE = "responses/customer/contactListResponse.json";

    private static final String COMBINATION_ID_RESPONSE = "responses/customer/billToCustomerResponse.json";

    private static final String CART_PROJECT_REQUEST = "requests/customer/shoppingCartProject.json";

    private static final String CUSTOMER_ADDRESS_RESPONSE = "responses/customer/cutomerAddressResponse.json";

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private AuthUser authUserMock;

    @Mock
    private CacheService<CachedShoppingCartDetails> cacheService;

    @Test
    public void testGetAssociatedCustomers() throws Exception {
        AssociatedCustomerListResponse associatedCustomerListResponse = ResourceLoader
                .readAndGetObject(ASSOCIATED_CUSTOMER_LIST_RESPONSE, AssociatedCustomerListResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssociatedCustomerListResponse>> any()))
                .thenReturn(associatedCustomerListResponse);
        AssociatedCustomerListResponse customerListResponse = Whitebox.invokeMethod(customerServiceSpy,
                "getAssociatedCustomers", MockData.MAIN_CUSTOMER_ID.getLong());
        assertNotNull("response of getAssociatedCustomers should not be null", customerListResponse);
        assertEquals("response of getAssociatedCustomers must be equal to mock data", associatedCustomerListResponse,
                customerListResponse);
    }

    @Test
    public void testGetProjects() throws Exception {
        List<Project> projectsList = ResourceLoader.readAndGetObject(PROJECT_LIST_RESPONSE,
                new TypeReference<List<Project>>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Project>>> any())).thenReturn(projectsList);
        List<Project> projects = Whitebox.invokeMethod(customerServiceSpy, "getProjects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        assertNotNull("response of getProjects should not be null", projects);
        assertEquals("response of getProjects must be equal to mock data", projectsList, projects);
    }

    @Test
    public void testGetContacts() throws Exception {
        List<Contact> contactsList = ResourceLoader.readAndGetObject(CONTACT_LIST_RESPONSE,
                new TypeReference<List<Contact>>() {
                });
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Contact>>> any())).thenReturn(contactsList);
        List<Contact> contacts = Whitebox.invokeMethod(customerServiceSpy, "getContacts",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_NAME.getString());
        assertNotNull("response of getContacts should not be null", contacts);
        assertEquals("response of getContacts must be equal to mock data", contactsList, contacts);
    }

    @Test
    public void testGetBillingGroup() throws Exception {
        BillingGroupResponse combinationIdResponse = ResourceLoader.readAndGetObject(COMBINATION_ID_RESPONSE,
                BillingGroupResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Object>> any()))
                .thenReturn(MockData.COMBINATION_ID.getString(), combinationIdResponse);
        BillingGroupResponse billingGroupResponse = Whitebox.invokeMethod(customerServiceSpy, "getBillToCutomerDetails",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_ID.getString());
        assertNotNull("Response of getBillToCutomerDetails should  not be null", billingGroupResponse);
        assertEquals("Response of getBillToCutomerDetails should be equal", combinationIdResponse,
                billingGroupResponse);
    }

    @Test
    public void testGetCombinationId() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.COMBINATION_ID.getString());
        String combinationId = Whitebox.invokeMethod(customerServiceSpy, "getCombinationId",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_ID.getString());
        assertNotNull("Response of getCombinationId should  not be null", combinationId);
        assertEquals("Response of getCombinationId should be equal", MockData.COMBINATION_ID.getString(),
                combinationId);
    }

    @Test
    public void testGetCombinationIdWithProjectIdNull() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.COMBINATION_ID.getString());
        String combinationId = Whitebox.invokeMethod(customerServiceSpy, "getCombinationId",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(), null);
        assertNotNull("Response of getCombinationId should not be null", combinationId);
        assertEquals("Response of getCombinationId should be equal", MockData.COMBINATION_ID.getString(),
                combinationId);
    }

    @Test
    public void testGetAllAssociatedCustomers() throws Exception {
        AssociatedCustomerListResponse associatedCustomerListResponse = ResourceLoader
                .readAndGetObject(ASSOCIATED_CUSTOMER_LIST_RESPONSE, AssociatedCustomerListResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssociatedCustomerListResponse>> any()))
                .thenReturn(associatedCustomerListResponse);
        AssociatedCustomerListResponse customerListResponse = Whitebox.invokeMethod(customerServiceSpy,
                "getAllAssociatedCustomers", MockData.MAIN_CUSTOMER_ID.getLong());
        assertNotNull("response of getAllAssociatedCustomers should not be null", customerListResponse);
        assertEquals("response of getAllAssociatedCustomers must be equal to mock data", associatedCustomerListResponse,
                customerListResponse);
    }

    @Test
    public void testGetAllProjects() throws Exception {
        List<Project> projectsList = ResourceLoader.readAndGetObject(PROJECT_LIST_RESPONSE,
                new TypeReference<List<Project>>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Project>>> any())).thenReturn(projectsList);
        List<Project> projects = Whitebox.invokeMethod(customerServiceSpy, "getAllProjects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        assertNotNull("response of getAllProjects should not be null", projects);
        assertEquals("response of getAllProjects must be equal to mock data", projectsList, projects);
    }

    @Test
    public void testGetAllProjectsForCachedCart() throws Exception {
        ShoppingCartProject cartProjectReuest = ResourceLoader.readAndGetObject(CART_PROJECT_REQUEST,
                ShoppingCartProject.class);
        List<Project> projectsList = ResourceLoader.readAndGetObject(PROJECT_LIST_RESPONSE,
                new TypeReference<List<Project>>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Project>>> any())).thenReturn(projectsList);
        MockCachedData.mockCachedShoppingCart(cacheService);
        List<Project> projects = Whitebox.invokeMethod(customerServiceSpy, "getProjectsForCart", cartProjectReuest);
        assertNotNull("response of getProjectsForCart should not be null", projects);
        assertEquals("response of getProjectsForCart must be equal to mock data", projectsList, projects);
    }

    @Test
    public void testGetCustomerAddress() throws Exception {
        CustomerAddress customerAddress = ResourceLoader.readAndGetObject(CUSTOMER_ADDRESS_RESPONSE,
                CustomerAddress.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<CustomerAddress>> any()))
                .thenReturn(customerAddress);
        CustomerAddress customerAddresss = Whitebox.invokeMethod(customerServiceSpy, "getCustomerAddress",
                MockData.MAIN_CUSTOMER_ID.getString());
        assertNotNull("Response of getCustomerAddress should  not be null", customerAddresss);
    }

}
