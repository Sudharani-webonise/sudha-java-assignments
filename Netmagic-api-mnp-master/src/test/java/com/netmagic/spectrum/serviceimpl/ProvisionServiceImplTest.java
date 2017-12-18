package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
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
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.dto.provision.LineItemDetails;
import com.netmagic.spectrum.dto.provision.SofDetails;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.ProvisionServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ProvisionServiceImpl.class })
public class ProvisionServiceImplTest {

    @InjectMocks
    private ProvisionServiceImpl provisionServiceSpy;

    private static final String SOF_RESPONSE = "responses/provision/SofResponse.json";

    private static final String LINE_ITEM_RESPONSE = "responses/provision/LineItemResponse.json";

    private static final String BILL_TO_CUSTOMER = "test-customer";

    private static final String SUPPORT_TO_CUSTOMER = "test-customer";

    private static final Long SOF_NUMBER = 1L;

    private static final Long LINE_ITEM_NUMBER = 1L;

    @Mock
    private ApiClient apiClientMock;

    @Test
    public void testGetProvisionSofData() throws Exception {
        List<SofDetails> provisionSofResponseExpected = ResourceLoader.readAndGetObject(SOF_RESPONSE,
                new TypeReference<ArrayList<SofDetails>>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<SofDetails>>> any())).thenReturn(provisionSofResponseExpected);
        List<SofDetails> provisionSofResponseActual = Whitebox.invokeMethod(provisionServiceSpy, "getProvisionSofData",
                BILL_TO_CUSTOMER, SUPPORT_TO_CUSTOMER, MockData.PROJECT_NAME.getString());
        assertNotNull("response of getProvisionSofData should not be null", provisionSofResponseActual);
        assertEquals("response of getProvisionSofData must be equal to mock data", provisionSofResponseExpected,
                provisionSofResponseActual);
    }

    @Test
    public void testGetProvisionSofWithNullData() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<SofDetails>>> any())).thenReturn(null);
        List<SofDetails> provisionSofResponseActual = Whitebox.invokeMethod(provisionServiceSpy, "getProvisionSofData",
                new Object[3]);
        assertNull("response of getProvisionSofData must be equal to mock data", provisionSofResponseActual);
    }

    @Test
    public void testGetProvisionLineItemData() throws Exception {
        LineItemDetails lineItemResponseExpected = ResourceLoader.readAndGetObject(LINE_ITEM_RESPONSE,
                LineItemDetails.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<LineItemDetails>> any()))
                .thenReturn(lineItemResponseExpected);
        LineItemDetails lineItemResponseActual = Whitebox.invokeMethod(provisionServiceSpy, "getProvisionLineItemData",
                SOF_NUMBER, LINE_ITEM_NUMBER);
        assertNotNull("response of getProvisionLineItemData should not be null", lineItemResponseActual);
        assertEquals("response of getProvisionLineItemData must be equal to mock data", lineItemResponseExpected,
                lineItemResponseActual);
    }

    @Test
    public void testGetProvisionLineItemWithNullData() throws Exception {
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<LineItemDetails>> any())).thenReturn(null);
        LineItemDetails lineItemResponseActual = Whitebox.invokeMethod(provisionServiceSpy, "getProvisionLineItemData",
                new Object[2]);
        assertNull("response of getProvisionLineItemData must be equal to mock data", lineItemResponseActual);
    }
}
