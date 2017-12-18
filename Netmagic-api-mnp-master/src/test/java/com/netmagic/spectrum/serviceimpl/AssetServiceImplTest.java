package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import com.netmagic.spectrum.dto.asset.request.AssetListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagListRequest;
import com.netmagic.spectrum.dto.asset.request.AssetTagRequest;
import com.netmagic.spectrum.dto.asset.request.WidgetRequest;
import com.netmagic.spectrum.dto.asset.response.AssetList;
import com.netmagic.spectrum.dto.asset.response.AssetTagList;
import com.netmagic.spectrum.dto.asset.response.WidgetData;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.AssetServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ AssetServiceImpl.class })
public class AssetServiceImplTest {

    @InjectMocks
    private AssetServiceImpl assetServiceSpy;

    private static final String ASSET_WIDGET_RESPONSE = "responses/assets/assetWidgetResponse.json";

    private static final String ASSET_WIDGET_RESPONSE_NULL_PARAMS = "responses/assets/assetWidgetResponseForNullParameters.json";

    private static final String ASSET_TAG_LIST_RESPONSE = "responses/assets/assetTagListResponse.json";

    private static final String ASSET_LIST_RESPONSE = "responses/assets/assetListResponse.json";

    private static final String ASSET_LIST_REQUEST = "requests/assets/assetListRequest.json";

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private ApiClient apiClientMock;

    @Test
    public void testGetWidgetDataWithValidRequest() throws Exception {
        WidgetData widgetResponseData = ResourceLoader.readAndGetObject(ASSET_WIDGET_RESPONSE, WidgetData.class);
        WidgetRequest widgetRequest = new WidgetRequest(MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.ASSOCIATE_CUSTOMER_ID.getLong(), MockData.PROJECT_ID.getString());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<WidgetData>> any()))
                .thenReturn(widgetResponseData);
        WidgetData widgetData = Whitebox.invokeMethod(assetServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", widgetData);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponseData, widgetData);
    }

    @Test
    public void testGetWidgetDataWithProjectIdNull() throws Exception {
        WidgetRequest widgetRequest = new WidgetRequest(MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.ASSOCIATE_CUSTOMER_ID.getLong(), null);
        WidgetData widgetResponseData = ResourceLoader.readAndGetObject(ASSET_WIDGET_RESPONSE, WidgetData.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<WidgetData>> any()))
                .thenReturn(widgetResponseData);
        WidgetData widgetData = Whitebox.invokeMethod(assetServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", widgetData);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponseData, widgetData);
    }

    @Test
    public void testGetWidgetDataWithMainCustomerNull() throws Exception {
        WidgetRequest widgetRequest = new WidgetRequest(null, MockData.ASSOCIATE_CUSTOMER_ID.getLong(),
                MockData.PROJECT_ID.getString());
        WidgetData widgetResponseData = ResourceLoader.readAndGetObject(ASSET_WIDGET_RESPONSE_NULL_PARAMS,
                WidgetData.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<WidgetData>> any()))
                .thenReturn(widgetResponseData);
        WidgetData widgetData = Whitebox.invokeMethod(assetServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", widgetData);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponseData, widgetData);
    }

    @Test
    public void testGetWidgetDataWithAssociateCustomerNull() throws Exception {
        WidgetRequest widgetRequest = new WidgetRequest(MockData.MAIN_CUSTOMER_ID.getLong(), null,
                MockData.PROJECT_ID.getString());
        WidgetData widgetResponseData = ResourceLoader.readAndGetObject(ASSET_WIDGET_RESPONSE_NULL_PARAMS,
                WidgetData.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<WidgetData>> any()))
                .thenReturn(widgetResponseData);
        WidgetData widgetData = Whitebox.invokeMethod(assetServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", widgetData);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponseData, widgetData);
    }

    @Test
    public void testGetWidgetDataWithNullRequest() throws Exception {
        WidgetRequest widgetRequest = new WidgetRequest(null, null, null);
        WidgetData widgetResponseData = ResourceLoader.readAndGetObject(ASSET_WIDGET_RESPONSE_NULL_PARAMS,
                WidgetData.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<WidgetData>> any()))
                .thenReturn(widgetResponseData);
        WidgetData widgetData = Whitebox.invokeMethod(assetServiceSpy, "getWidgetData", widgetRequest);
        assertNotNull("response of getWidgetData should not be null", widgetData);
        assertEquals("response of getWidgetData must be equal to mock data", widgetResponseData, widgetData);
    }

    @Test
    public void testGetAssetList() throws Exception {
        AssetListRequest assetListRequest = ResourceLoader.readAndGetObject(ASSET_LIST_REQUEST, AssetListRequest.class);
        AssetList assetList = ResourceLoader.readAndGetObject(ASSET_LIST_RESPONSE, AssetList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetList>> any())).thenReturn(assetList);
        AssetList assets = Whitebox.invokeMethod(assetServiceSpy, "getAssetList", assetListRequest);
        assertNotNull("response of getAssetList should not be null", assets);
        assertEquals("response of getAssetList must be equal to mock data", assetList, assets);
    }

    @Test
    public void testGetAssetListWithNullParameters() throws Exception {
        AssetListRequest assetListRequest = null;
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetList>> any())).thenReturn(null);
        AssetList assets = Whitebox.invokeMethod(assetServiceSpy, "getAssetList", assetListRequest);
        assertNull("response of getAssetList should be null", assets);
        assertEquals("response of getAssetList must be equal to mock data", null, assets);
    }

    @Test
    public void testGetAssetTagList() throws Exception {
        AssetTagListRequest assetTagRequest = new AssetTagListRequest(MockData.MAIN_CUSTOMER_ID.getLong(),
                MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        AssetTagList assetTagList = ResourceLoader.readAndGetObject(ASSET_TAG_LIST_RESPONSE, AssetTagList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetTagList>> any()))
                .thenReturn(assetTagList);
        AssetTagList tagList = Whitebox.invokeMethod(assetServiceSpy, "getAssetTagList", assetTagRequest);
        assertNotNull("response of getAssetTagList should not be null", tagList);
        assertEquals("response of getAssetTagList must be equal to mock data", assetTagList, tagList);
    }

    @Test
    public void testGetAssetTagListWithAssociateCustomerIdNull() throws Exception {
        AssetTagListRequest assetTagRequest = new AssetTagListRequest(MockData.MAIN_CUSTOMER_ID.getLong(), null);
        AssetTagList assetTagList = ResourceLoader.readAndGetObject(ASSET_TAG_LIST_RESPONSE, AssetTagList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetTagList>> any()))
                .thenReturn(assetTagList);
        AssetTagList tagList = Whitebox.invokeMethod(assetServiceSpy, "getAssetTagList", assetTagRequest);
        assertNotNull("response of getAssetTagList should not be null", tagList);
        assertEquals("response of getAssetTagList must be equal to mock data", assetTagList, tagList);
    }

    @Test
    public void testGetAssetTagListWithMainCustomerNull() throws Exception {
        AssetTagListRequest assetTagRequest = new AssetTagListRequest(null, MockData.ASSOCIATE_CUSTOMER_ID.getLong());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetTagList>> any())).thenReturn(null);
        AssetTagList tagList = Whitebox.invokeMethod(assetServiceSpy, "getAssetTagList", assetTagRequest);
        assertNull("response of getAssetTagList should be null", tagList);
        assertEquals("response of getAssetTagList must be equal to mock data", null, tagList);
    }

    @Test
    public void testGetAssetTagListWithNullParameters() throws Exception {
        AssetTagListRequest assetTagRequest = new AssetTagListRequest(null, null);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AssetTagList>> any())).thenReturn(null);
        AssetTagList tagList = Whitebox.invokeMethod(assetServiceSpy, "getAssetTagList", assetTagRequest);
        assertNull("response of getAssetTagList should be null", tagList);
        assertEquals("response of getAssetTagList must be equal to mock data", null, tagList);
    }

    @Test
    public void testAddAssetTagWhenSuccess() throws Exception {
        AssetTagRequest assetTagAddRequest = new AssetTagRequest(MockData.ASSET_ID.getLong(),
                MockData.TAG_NAME.getString());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.SUCCESS.getString());
        String result = Whitebox.invokeMethod(assetServiceSpy, "addAssetTag", assetTagAddRequest);
        assertNotNull("response of addAssetTag should not be null", result);
        assertEquals("response of addAssetTag must be equal to mock data", MockData.SUCCESS.getString(), result);
    }

    @Test
    public void testAddAssetTagWhenFailure() throws Exception {
        AssetTagRequest assetTagAddRequest = new AssetTagRequest(MockData.ASSET_ID.getLong(),
                MockData.TAG_NAME.getString());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.FAILURE.getString());
        String result = Whitebox.invokeMethod(assetServiceSpy, "addAssetTag", assetTagAddRequest);
        assertNotNull("response of addAssetTag should not be null", result);
        assertEquals("response of addAssetTag must be equal to mock data", MockData.FAILURE.getString(), result);
    }

    @Test
    public void testRemoveAssetTag() throws Exception {
        AssetTagRequest assetTagRemoveRequest = new AssetTagRequest(MockData.ASSET_ID.getLong(),
                MockData.TAG_NAME.getString());
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.SUCCESS.getString());
        String result = Whitebox.invokeMethod(assetServiceSpy, "removeAssetTag", assetTagRemoveRequest);
        assertNotNull("response of removeAssetTag should not be null", result);
        assertEquals("response of removeAssetTag must be equal to mock data", MockData.SUCCESS.getString(), result);
    }
}
