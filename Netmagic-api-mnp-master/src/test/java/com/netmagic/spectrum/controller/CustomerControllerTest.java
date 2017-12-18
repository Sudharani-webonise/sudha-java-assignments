package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.dto.customer.ShoppingCartProject;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.CustomerService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(CustomerController.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private CustomerController customerControllerSpy;

    @Mock
    private CustomerService customerServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(customerControllerSpy).build();
    }

    @Test
    public void testGetAssociateCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/associatedCustomers/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testGetProjects() throws Exception {
        mockMvc.perform(get("/api/customer/{customerId}/associated/{associateCustomerId}/projects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetContacts() throws Exception {
        mockMvc.perform(get("/api/customer/{customerId}/associated/{associateCustomerId}/contacts",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerDetails() throws Exception {
        mockMvc.perform(get("/api/customer/billingGroup").param("customerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchCombinationId() throws Exception {
        mockMvc.perform(get("/api/customer/combinationId").param("customerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchAllAssociateCustomers() throws Exception {
        mockMvc.perform(
                get("/api/customer/internal/associatedCustomers/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetInternalAssociateCustomers() throws Exception {
        mockMvc.perform(get("/api/customer/internal/{customerId}/associated/{associateCustomerId}/projects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProjectsForCart() throws Exception {
        mockMvc.perform(post("/api/customer/projects/cart").contentType(MediaType.APPLICATION_JSON)
                .content(ResourceLoader.asJsonString(new ShoppingCartProject()))).andExpect(status().isOk());
    }

    @Test
    public void testGetcustromerAddress() throws Exception {
        mockMvc.perform(get("/api/customer/address/{billToCustomer}", MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.ASSOCIATE_CUSTOMER_ID.getLong()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }
}
