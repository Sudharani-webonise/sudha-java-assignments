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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.service.ServiceLedgerService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ServiceLedgerController.class)
public class ServiceLedgerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ServiceLedgerController serviceLedgerControllerSpy;

    @Mock
    private ServiceLedgerService ledgerServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(serviceLedgerControllerSpy).build();
    }

    @Test
    public void testGetSOFList() throws Exception {
        mockMvc.perform(get("/api/services/sofs/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetLineItems() throws Exception {
        mockMvc.perform(get("/api/services/line-items/{sofNumber}", MockData.SOF_NUMBER.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetServiceWidgetData() throws Exception {
        mockMvc.perform(get("/api/services/widget/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())).andExpect(status().isOk());
    }

}
