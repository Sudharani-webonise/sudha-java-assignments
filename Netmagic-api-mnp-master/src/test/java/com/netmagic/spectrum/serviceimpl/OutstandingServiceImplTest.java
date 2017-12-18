package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.apiclient.OustandingApiAuthorization;
import com.netmagic.spectrum.dto.outstanding.request.CartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountRequestWrapper;
import com.netmagic.spectrum.dto.outstanding.request.OutstandingRequest;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequest;
import com.netmagic.spectrum.dto.outstanding.response.CartPaymentResponse;
import com.netmagic.spectrum.dto.outstanding.response.OnAccountPaymentResponseWrapper;
import com.netmagic.spectrum.dto.outstanding.response.OnlineCreditResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingBusinessUnit;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingListResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingWidgetResponse;
import com.netmagic.spectrum.dto.outstanding.response.PaymentResponse;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.PaymentService;
import com.netmagic.spectrum.service.impl.OutstandingServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ OutstandingServiceImpl.class })
public class OutstandingServiceImplTest {

    @InjectMocks
    private OutstandingServiceImpl outstandingServiceSpy;

    private static final String OUTSTANDING_REQUEST = "requests/outstanding/outstandingRequest.json";

    private static final String PAYMENT_REQUEST = "requests/outstanding/paymentRequest.json";

    private static final String OUTSTANDING_WIDGET_RESPONSE = "responses/outstanding/widgetResponse.json";

    private static final String OUTSTANDING_LIST_RESPONSE = "responses/outstanding/listResponse.json";

    private static final String PAYMENT_RESPONSE = "responses/outstanding/paymentResponse.json";

    private static final String BUSINESS_UNIT_RESPONSE = "responses/outstanding/businessUnitResponse.json";

    private static final String ONLINE_CREDIT_LIMIT = "responses/outstanding/onlineCreditLimit.json";

    private static final String CART_PAYMENT_REQUEST = "requests/outstanding/cartRequest.json";

    private static final String CART_PAYMENT_RESPONSE = "responses/outstanding/cartResponse.json";

    private static final String ON_ACCOUNT_PAYMENT_REQUEST = "requests/outstanding/onAccountRequest.json";

    private static final String ON_ACCOUNT_PAYMENT_RESPONSE = "responses/outstanding/onAccountResponse.json";

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private PaymentService paymentService;

    @Mock
    private OustandingApiAuthorization outstandingApiAuthorizationMock;

    @Mock
    private OutstandingWidgetResponse outstandingWidgetMock;

    @Mock
    private OutstandingListResponse outstandingListMock;

    @Test
    public void testGetWidgetDataWhenAuthorizationFails() throws Exception {
        OutstandingRequest outstandingWidgetRequest = ResourceLoader.readAndGetObject(OUTSTANDING_REQUEST,
                OutstandingRequest.class);
        OutstandingWidgetResponse widgetResponse = ResourceLoader.readAndGetObject(OUTSTANDING_WIDGET_RESPONSE,
                OutstandingWidgetResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OutstandingWidgetResponse>> any()))
                .thenReturn(widgetResponse);
        OutstandingWidgetResponse widgetResponseValue = Whitebox.invokeMethod(outstandingServiceSpy, "getWidgetData",
                outstandingWidgetRequest);
        assertNotNull("response of getWidgetData must not be null", widgetResponseValue);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponse, widgetResponseValue);
    }

    @Test
    public void testGetListData() throws Exception {
        OutstandingRequest outstandingListRequest = ResourceLoader.readAndGetObject(OUTSTANDING_REQUEST,
                OutstandingRequest.class);
        OutstandingListResponse listResponse = ResourceLoader.readAndGetObject(OUTSTANDING_LIST_RESPONSE,
                OutstandingListResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OutstandingListResponse>> any()))
                .thenReturn(listResponse);
        OutstandingListResponse outstandingListResponse = Whitebox.invokeMethod(outstandingServiceSpy, "getListData",
                outstandingListRequest);
        assertNotNull("response of getListData must not be null", outstandingListResponse);
        assertEquals("response of getListData must be equal to mock data", listResponse, outstandingListResponse);
    }

    @Test
    public void testGetBusinessUnits() throws Exception {
        OutstandingBusinessUnit businessUnit = ResourceLoader.readAndGetObject(BUSINESS_UNIT_RESPONSE,
                OutstandingBusinessUnit.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OutstandingBusinessUnit>> any()))
                .thenReturn(businessUnit);
        OutstandingBusinessUnit outstandingBusinessUnit = Whitebox.invokeMethod(outstandingServiceSpy,
                "getBusinessUnits");
        assertNotNull("response of getBusinessUnits must not be null", outstandingBusinessUnit);
        assertEquals("response of getBusinessUnits must be equal to mock data", businessUnit, outstandingBusinessUnit);
    }

    @Test
    public void testGetOnlineCreditLimit() throws Exception {
        OnlineCreditResponse onlineCreditResponse = ResourceLoader.readAndGetObject(ONLINE_CREDIT_LIMIT,
                OnlineCreditResponse.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OnlineCreditResponse>> any()))
                .thenReturn(onlineCreditResponse);
        OnlineCreditResponse creditResponse = Whitebox.invokeMethod(outstandingServiceSpy, "getOnlineCreditLimit",
                MockData.MAIN_CUSTOMER_ID.getLong());
        assertNotNull("response of getOnlineCreditLimit must not be null", creditResponse);
        assertEquals("response of getOnlineCreditLimit must be equal to mock data", onlineCreditResponse,
                creditResponse);
    }

    @Test
    public void testSendPaymentRequestFalse() throws Exception {
        PaymentRequest paymentRequest = ResourceLoader.readAndGetObject(PAYMENT_REQUEST, PaymentRequest.class);
        PaymentResponse paymentResponseMock = ResourceLoader.readAndGetObject(PAYMENT_RESPONSE, PaymentResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<PaymentResponse>> any()))
                .thenReturn(paymentResponseMock);
        Boolean status = Whitebox.invokeMethod(outstandingServiceSpy, "sendPaymentRequest", paymentRequest,
                MockData.REFERENCE_NUMBER.getString(), MockData.PAYMENT_SOURCE.getString());
        assertFalse(status);
    }

    @Test
    public void testCartMakePayment() throws Exception {
        CartPaymentRequest cartPaymentRequest = ResourceLoader.readAndGetObject(CART_PAYMENT_REQUEST,
                CartPaymentRequest.class);
        CartPaymentResponse cartPaymentResponseMock = ResourceLoader.readAndGetObject(CART_PAYMENT_RESPONSE,
                CartPaymentResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<CartPaymentResponse>> any()))
                .thenReturn(cartPaymentResponseMock);
        CartPaymentResponse cartResponse = Whitebox.invokeMethod(outstandingServiceSpy, "cartMakePayment",
                cartPaymentRequest);
        assertNotNull("response of cartMakePayment must not be null", cartResponse);
        assertEquals("response of cartMakePayment must be equal to mock data", cartPaymentResponseMock, cartResponse);
    }

    @Test
    public void testSendOnAccountRequest() throws Exception {
        OnAccountRequestWrapper onAccountPaymentRequest = ResourceLoader.readAndGetObject(ON_ACCOUNT_PAYMENT_REQUEST,
                OnAccountRequestWrapper.class);
        OnAccountPaymentResponseWrapper onAccountResponseMock = ResourceLoader
                .readAndGetObject(ON_ACCOUNT_PAYMENT_RESPONSE, OnAccountPaymentResponseWrapper.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<OnAccountPaymentResponseWrapper>> any()))
                .thenReturn(onAccountResponseMock);
        OnAccountPaymentResponseWrapper onAccountResponse = Whitebox.invokeMethod(outstandingServiceSpy,
                "sendOnAccountRequest", onAccountPaymentRequest);
        assertNotNull("response of sendOnAccountRequest must not be null", onAccountResponse);
        assertEquals("response of sendOnAccountRequest must be equal to mock data", onAccountResponseMock,
                onAccountResponse);
    }

    @Test
    public void testPayOnAccount() throws Exception {
        OnAccountRequestWrapper onAccountPaymentRequest = ResourceLoader.readAndGetObject(ON_ACCOUNT_PAYMENT_REQUEST,
                OnAccountRequestWrapper.class);
        Mockito.when(paymentService
                .saveMasterUserTransactionDetailsOnAccount(onAccountPaymentRequest.getOnAccountPaymentRequest()))
                .thenReturn(true);
        Boolean status = Whitebox.invokeMethod(outstandingServiceSpy, "payOnAccount", onAccountPaymentRequest);
        assertTrue(status);
    }
}
