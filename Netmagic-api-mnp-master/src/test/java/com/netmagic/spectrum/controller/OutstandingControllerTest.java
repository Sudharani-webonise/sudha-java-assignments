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

import com.netmagic.spectrum.dto.outstanding.request.CartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountRequestWrapper;
import com.netmagic.spectrum.dto.outstanding.request.OutstandingRequest;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.OutstandingService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(OutstandingController.class)
public class OutstandingControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private OutstandingController outstandingControllerSpy;

    @Mock
    private OutstandingService outstandingServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(outstandingControllerSpy).build();
    }

    @Test
    public void testFetchWidgetData() throws Exception {
        mockMvc.perform(post("/api/outstanding/widget").content(ResourceLoader.asJsonString(new OutstandingRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchWidgetList() throws Exception {
        mockMvc.perform(post("/api/outstanding/list").content(ResourceLoader.asJsonString(new OutstandingRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testMakePayment() throws Exception {
        mockMvc.perform(post("/api/outstanding/payment/{paymentSource}", MockData.PAYMENT_SOURCE.getString())
                .param("merchantRefNumber", MockData.TICKET_NUMBER.getString())
                .content(ResourceLoader.asJsonString(new PaymentRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchBusinessUnits() throws Exception {
        mockMvc.perform(get("/api/outstanding/businessUnits").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOnlineCreditLimit() throws Exception {
        mockMvc.perform(get("/api/outstanding/onlineCreditLimit/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testSofPayment() throws Exception {
        mockMvc.perform(
                post("/api/outstanding/cart/payment").content(ResourceLoader.asJsonString(new CartPaymentRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testOnAccountPayment() throws Exception {
        mockMvc.perform(post("/api/outstanding/pay/onAccount")
                .content(ResourceLoader.asJsonString(new OnAccountRequestWrapper()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

}
