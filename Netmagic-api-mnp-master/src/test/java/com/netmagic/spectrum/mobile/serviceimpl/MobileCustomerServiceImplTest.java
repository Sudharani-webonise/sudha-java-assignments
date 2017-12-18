package com.netmagic.spectrum.mobile.serviceimpl;

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
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.customer.AssociatedCustomerListResponse;
import com.netmagic.spectrum.dto.customer.Contact;
import com.netmagic.spectrum.dto.customer.MobileContact;
import com.netmagic.spectrum.dto.customer.MobileProject;
import com.netmagic.spectrum.dto.customer.Project;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.mobile.service.impl.MobileCustomerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ MobileCustomerServiceImpl.class })
public class MobileCustomerServiceImplTest {

    @InjectMocks
    private MobileCustomerServiceImpl mobileCustomerServiceSpy;

    private static final String ASSOCIATED_CUSTOMER_LIST_RESPONSE = "responses/customer/associatedCustomerListResponse.json";

    private static final String PROJECT_LIST_RESPONSE = "responses/customer/MobileProjectListResponse.json";

    private static final String CONTACT_LIST_RESPONSE = "responses/customer/MobileContactListResponse.json";

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private AuthUser authUserMock;

    @Test
    public void testGetAssociatedCustomers() throws Exception {
        AssociatedCustomerListResponse associatedCustomerListResponse = ResourceLoader
                .readAndGetObject(ASSOCIATED_CUSTOMER_LIST_RESPONSE, AssociatedCustomerListResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssociatedCustomerListResponse>> any()))
                .thenReturn(associatedCustomerListResponse);
        AssociatedCustomerListResponse customerListResponse = Whitebox.invokeMethod(mobileCustomerServiceSpy,
                "getAssociatedMobileCustomers", MockData.MAIN_CUSTOMER_ID.getLong());
        assertNotNull("response of getTicketList should not be null", customerListResponse);
        assertEquals("response of getTicketList must be equal to mock data", associatedCustomerListResponse,
                customerListResponse);
    }

    @Test
    public void testGetProjects() throws Exception {
        String projectListResponse = ResourceLoader.readFile(PROJECT_LIST_RESPONSE);
        MobileProject projectsList = new ObjectMapper().readValue(projectListResponse,
                new TypeReference<MobileProject>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Project>>> any())).thenReturn(projectsList.getProjects());
        MobileProject projects = Whitebox.invokeMethod(mobileCustomerServiceSpy, "getMobileProjects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        assertNotNull("response of getTicketList should not be null", projects);
        assertEquals("response of getTicketList must be equal to mock data", projectsList.getProjects(),
                projects.getProjects());
    }

    @Test
    public void testGetContacts() throws Exception {
        String contactListResponse = ResourceLoader.readFile(CONTACT_LIST_RESPONSE);
        MobileContact contactsList = new ObjectMapper().readValue(contactListResponse,
                new TypeReference<MobileContact>() {
                });
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<Contact>>> any())).thenReturn(contactsList.getContacts());
        MobileContact contacts = Whitebox.invokeMethod(mobileCustomerServiceSpy, "getMobileContacts",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_NAME.getString());
        assertNotNull("response of getTicketList should not be null", contacts);
        assertEquals("response of getTicketList must be equal to mock data", contactsList.getContacts(),
                contacts.getContacts());
    }
}
