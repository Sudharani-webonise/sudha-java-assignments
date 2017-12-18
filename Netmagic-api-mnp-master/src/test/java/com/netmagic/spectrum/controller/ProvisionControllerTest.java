package com.netmagic.spectrum.controller;

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
import com.netmagic.spectrum.service.ProvisionService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ProvisionController.class)
public class ProvisionControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProvisionController provisionControllerspy;

    @Mock
    private ProvisionService provisionService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(provisionControllerspy).build();
    }

    @Test
    public void testGetProvisionSofData() throws Exception {
        mockMvc.perform(get("/api/provision/sofDetails")
                .param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .param("supportToCustomer", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testGetProvisionLineItemData() throws Exception {
        mockMvc.perform(get("/api/provision/lineItemDetails")
                .param("sofNumber", MockData.SOF_NUMBER.getString())
                .param("lineItemNumber", MockData.LINE_ITEM_NUMBER.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

}
