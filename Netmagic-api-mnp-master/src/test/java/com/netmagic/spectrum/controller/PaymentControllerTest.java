package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.PaymentForm;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.shoppingcart.response.SplitAccountPrice;
import com.netmagic.spectrum.dto.user.response.UserResponseTemp;
import com.netmagic.spectrum.helpers.MockCachedData;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.CustomerService;
import com.netmagic.spectrum.service.PaymentService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(PaymentController.class)
public class PaymentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private PaymentController paymentControllerSpy;

    @Mock
    private PaymentService paymentService;

    @Mock
    private CacheService<UserResponseTemp> userCacheService;

    @Mock
    private CustomerService customerService;

    private static final String CUSTOMER_ADDR_RESPONSE = "responses/customer/custromerAddressResponse.json";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentControllerSpy).build();
    }

    @Test
    public void testShowPaymentForm() throws Exception {
        mockMvc.perform(get("/payment/pay")).andExpect(status().isOk());
    }

    @Test
    public void testSubmitPayment() throws Exception {
        mockMvc.perform(post("/payment/submitpayment").content(ResourceLoader.asJsonString(new PaymentForm())))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaymentResponseSof() throws Exception {
        mockMvc.perform(post("/payment/paymentResponse/sof").content(ResourceLoader.asJsonString(new PaymentForm())))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaymentResponseOutstanding() throws Exception {
        mockMvc.perform(
                post("/payment/paymentResponse/outstanding").content(ResourceLoader.asJsonString(new PaymentForm())))
                .andExpect(status().isOk());
    }

    @Test
    public void testPaymentResponseOunAccount() throws Exception {
        mockMvc.perform(
                post("/payment/paymentResponse/onAccount").content(ResourceLoader.asJsonString(new PaymentForm())))
                .andExpect(status().isOk());
    }

    @Test
    public void testPayForSof() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        Mockito.when(
                paymentService.getInvoiceTotalAmount(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new SplitAccountPrice(15.00, 56.00));
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(new CustomerAddress());
        mockMvc.perform(post("/payment/paynow/sof").content(ResourceLoader.asJsonString(paymentForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPayForSofFromCache() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        Mockito.when(
                paymentService.getInvoiceTotalAmount(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new SplitAccountPrice(15.00, 56.00));
        MockCachedData.mockCachedTempUser(userCacheService);
        mockMvc.perform(post("/payment/paynow/sof").content(ResourceLoader.asJsonString(paymentForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPayForSofWithCustomerDetails() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        CustomerAddress customerAddress = ResourceLoader.readAndGetObject(CUSTOMER_ADDR_RESPONSE,
                CustomerAddress.class);
        Mockito.when(
                paymentService.getInvoiceTotalAmount(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new SplitAccountPrice(15.00, 56.00));
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(customerAddress);
        mockMvc.perform(post("/payment/paynow/sof").content(ResourceLoader.asJsonString(paymentForm)))
                .andExpect(status().isOk());
    }

    @Test
    public void testPayForInvoiceNMITS() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(new CustomerAddress());
        mockMvc.perform(post("/payment/paynow/invoice/{businessUnit}", "NMITS")
                .param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .content(ResourceLoader.asJsonString(paymentForm))).andExpect(status().isOk());
    }

    @Test
    public void testPayForInvoiceNMSPL() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(new CustomerAddress());
        mockMvc.perform(post("/payment/paynow/invoice/{businessUnit}", "NMSPL")
                .param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .content(ResourceLoader.asJsonString(paymentForm))).andExpect(status().isOk());
    }

    @Test
    public void testPayForInvoiceNMITSCustAddress() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        CustomerAddress customerAddress = ResourceLoader.readAndGetObject(CUSTOMER_ADDR_RESPONSE,
                CustomerAddress.class);
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(customerAddress);
        mockMvc.perform(post("/payment/paynow/invoice/{businessUnit}", "NMITS")
                .param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .content(ResourceLoader.asJsonString(paymentForm))).andExpect(status().isOk());
    }

    @Test
    public void testPayForOnAccount() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(new CustomerAddress());
        mockMvc.perform(post("/payment/paynow/onAccount").param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .content(ResourceLoader.asJsonString(paymentForm))).andExpect(status().isOk());
    }

    @Test
    public void testPayForPayForOnAccountCustAddress() throws Exception {
        PaymentForm paymentForm = getPaymentFormMock();
        CustomerAddress customerAddress = ResourceLoader.readAndGetObject(CUSTOMER_ADDR_RESPONSE,
                CustomerAddress.class);
        Mockito.when(customerService.getCustomerAddress(Mockito.anyString())).thenReturn(customerAddress);
        mockMvc.perform(post("/payment/paynow/onAccount").param("billToCustomer", MockData.MAIN_CUSTOMER_ID.getString())
                .content(ResourceLoader.asJsonString(paymentForm))).andExpect(status().isOk());
    }

    private PaymentForm getPaymentFormMock() {
        PaymentForm paymentForm = new PaymentForm();
        paymentForm.setEmail(MockData.USER_EMAIL.getString());
        paymentForm.setAmount(MockData.AMOUNT.getString());
        paymentForm.setReference_no(MockData.REFERENCE_NUMBER.getString());
        paymentForm.setName(MockData.USER.getString());
        paymentForm.setAmount(MockData.AMOUNT.getString());
        return paymentForm;
    }
}
