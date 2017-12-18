package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.dto.service.request.LineItemsRestData;
import com.netmagic.spectrum.dto.service.request.SOFListRestData;
import com.netmagic.spectrum.dto.service.request.WidgetRestData;
import com.netmagic.spectrum.dto.service.response.SOF;
import com.netmagic.spectrum.dto.service.response.ServiceLineItem;
import com.netmagic.spectrum.dto.service.response.ServiceWidget;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.ServiceLedgerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ServiceLedgerServiceImpl.class })
public class ServiceLedgerServiceImplTest {

    @InjectMocks
    private ServiceLedgerServiceImpl serviceLedgerSpy;

    private static final String SERVICE_WIDGET_RESPONSE = "responses/serviceLedger/serviceWidgetResponse.json";

    private static final String SOF_LIST_REQUEST = "requests/serviceLedger/SOFList.json";

    private static final String SOF_LIST_RESPONSE = "responses/serviceLedger/sofListResponse.json";

    private static final String LINE_ITEM_LIST_REQUEST = "requests/serviceLedger/LineItemsList.json";

    private static final String LINE_ITEM_LIST_RESPONSE = "responses/serviceLedger/LineItemsList.json";

    @Mock
    private SugarCRMAuthorization sugarCRMAuthorizationMock;

    @Mock
    private ObjectMapper mapperMock;

    @Test
    public void testGetWidgetData() throws Exception {
        WidgetRestData widgetRestData = new WidgetRestData(MockData.MAIN_CUSTOMER_ID.getString(),
                MockData.ASSOCIATE_CUSTOMER_ID.toString(), MockData.PROJECT_ID.getString());
        widgetRestData.setToken(MockData.TOKEN.toString());
        String widgetDataJson = ResourceLoader.readFile(SERVICE_WIDGET_RESPONSE);
        ServiceWidget serviceWidgetData = ResourceLoader.readAndGetObject(SERVICE_WIDGET_RESPONSE, ServiceWidget.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<WidgetRestData>> any(),
                Mockito.anyString())).thenReturn(widgetDataJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Matchers.<Class<ServiceWidget>> any()))
                .thenReturn(serviceWidgetData);
        ServiceWidget widgetData = Whitebox.invokeMethod(serviceLedgerSpy, "getWidgetData", widgetRestData);
        assertNotNull(serviceWidgetData);
        assertEquals("Response of SOFList Response should be equal to mock data", serviceWidgetData, widgetData);
    }

    @Test
    public void testGetSOFList() throws Exception {
        String SOFListResponseJson = ResourceLoader.readFile(SOF_LIST_RESPONSE);
        SOFListRestData SOFListRequest = ResourceLoader.readAndGetObject(SOF_LIST_REQUEST, SOFListRestData.class);
        List<SOF> SOFListResponseExpected = ResourceLoader.readAndGetObject(SOF_LIST_RESPONSE,
                new TypeReference<List<SOF>>() {
                });
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SOFListRestData>> any(),
                Mockito.anyString())).thenReturn(SOFListResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Matchers.<TypeReference<SOF>> any()))
                .thenReturn(SOFListResponseExpected);
        List<SOF> SOFListResponseActual = Whitebox.invokeMethod(serviceLedgerSpy, "getSOFList", SOFListRequest);
        assertNotNull("SOFList Response should not be null", SOFListResponseActual);
        assertEquals("Response of SOFList Response should be equal to mock data", SOFListResponseExpected,
                SOFListResponseActual);
    }

    @Test
    public void testGetSOFListWithInValidToken() throws Exception {
        String SOFListResponseJson = ResourceLoader.readFile(SOF_LIST_RESPONSE);
        SOFListRestData SOFListRequest = ResourceLoader.readAndGetObject(SOF_LIST_REQUEST, SOFListRestData.class);
        List<SOF> SOFListResponseExpected = ResourceLoader.readAndGetObject(SOF_LIST_RESPONSE,
                new TypeReference<List<SOF>>() {
                });
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.getString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SOFListRestData>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.getString()).thenReturn(SOFListResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Matchers.<TypeReference<SOF>> any()))
                .thenReturn(SOFListResponseExpected);
        List<SOF> SOFListResponseActual = Whitebox.invokeMethod(serviceLedgerSpy, "getSOFList", SOFListRequest);
        assertNotNull("SOFList Response should not be null", SOFListResponseActual);
        assertEquals("Response of SOFList Response should be equal to mock data", SOFListResponseExpected,
                SOFListResponseActual);
    }

    @Test
    public void testGetLineItems() throws Exception {
        String lineItemsListResponseJson = ResourceLoader.readFile(LINE_ITEM_LIST_RESPONSE);
        LineItemsRestData lineItemsListRequest = ResourceLoader.readAndGetObject(LINE_ITEM_LIST_REQUEST,
                LineItemsRestData.class);
        List<ServiceLineItem> lineItemListResponseExpected = ResourceLoader.readAndGetObject(LINE_ITEM_LIST_RESPONSE,
                new TypeReference<List<ServiceLineItem>>() {
                });
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SOFListRestData>> any(),
                Mockito.anyString())).thenReturn(lineItemsListResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Matchers.<TypeReference<ServiceLineItem>> any()))
                .thenReturn(lineItemListResponseExpected);
        List<ServiceLineItem> lineItemsListResponseActual = Whitebox.invokeMethod(serviceLedgerSpy, "getLineItems",
                lineItemsListRequest);
        assertNotNull("Line Item list Response should not be null", lineItemListResponseExpected);
        assertEquals("Response of Line Item list Response should be equal to mock data", lineItemListResponseExpected,
                lineItemsListResponseActual);
    }

    @Test
    public void testGetLineItemsWithInvalidToken() throws Exception {
        String lineItemsListResponseJson = ResourceLoader.readFile(LINE_ITEM_LIST_RESPONSE);
        LineItemsRestData lineItemsListRequest = ResourceLoader.readAndGetObject(LINE_ITEM_LIST_REQUEST,
                LineItemsRestData.class);
        List<ServiceLineItem> lineItemListResponseExpected = ResourceLoader.readAndGetObject(LINE_ITEM_LIST_RESPONSE,
                new TypeReference<List<ServiceLineItem>>() {
                });
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.getString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<SOFListRestData>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.getString())
                .thenReturn(lineItemsListResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Matchers.<TypeReference<ServiceLineItem>> any()))
                .thenReturn(lineItemListResponseExpected);
        List<ServiceLineItem> lineItemsListResponseActual = Whitebox.invokeMethod(serviceLedgerSpy, "getLineItems",
                lineItemsListRequest);
        assertNotNull("Line Item list Response should not be null", lineItemListResponseExpected);
        assertEquals("Response of Line Item list Response should be equal to mock data", lineItemListResponseExpected,
                lineItemsListResponseActual);
    }
}
