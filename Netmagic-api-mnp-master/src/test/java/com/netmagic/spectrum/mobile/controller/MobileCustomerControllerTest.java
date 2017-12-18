package com.netmagic.spectrum.mobile.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.mobile.service.MobileCustomerService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(MobileCustomerController.class)
public class MobileCustomerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MobileCustomerController mobileCustomerControllerSpy;

    @Mock
    private MobileCustomerService mobileCustomerServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mobileCustomerControllerSpy).build();
    }

    @Test
    public void testGetMobileProjects() throws Exception {
        mockMvc.perform(get(
                "/mobile/api/customer/{customerId}/associated/{assoCustomerId}/projects",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMobileContacts() throws Exception {
        mockMvc.perform(get(
                "/mobile/api/customer/{customerId}/associated/{assoCustomerId}/contacts",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchAllAssociateMobileCustomers() throws Exception {
        mockMvc.perform(get("/mobile/api/customer/associated/{customerId}",
                MockData.MAIN_CUSTOMER_ID.getLong()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}
