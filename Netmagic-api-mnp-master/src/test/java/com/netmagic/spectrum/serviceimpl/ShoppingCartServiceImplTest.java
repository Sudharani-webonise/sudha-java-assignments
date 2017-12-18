package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.CustomerAddressRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductPriceRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerAddressResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerContactResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.LocationList;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductList;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPriceResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SubCategoryList;
import com.netmagic.spectrum.dto.shoppingcart.response.SubSubCategoryList;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.ShoppingCartServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ ShoppingCartServiceImpl.class })
public class ShoppingCartServiceImplTest {

    private static final String GB = "GB";

    private static final String MBPS = "MBPS";

    private static final String IS_CAPPED_YES = "YES";

    @InjectMocks
    private ShoppingCartServiceImpl shopppingCartServiceSpy;

    private static final String SUB_CATEGORY_ID = "c1cfc2a6-4197-5d23-af2e-579b35b4e76a";

    private static final String LOCATION_ID = "902d86fb-935b-353e-d543-52f4ceeb0629";

    private static final String SUB_SUB_CATEGORY_RESPONSE = "responses/shoppingCart/subSubCategory.json";

    private static final String PRODUCT_LIST_RESPONSE = "responses/shoppingCart/productList.json";

    private static final String PRODUCT_DETAIL_RESPONSE = "responses/shoppingCart/productDetail.json";

    private static final String SUB_CATEGORY_RESPONSE = "responses/shoppingCart/subCategory.json";

    private static final String LOCATION_RESPONSE = "responses/shoppingCart/location.json";

    private static final String PRODUCT_PRICING_REQUEST = "requests/shoppingCart/productPricing.json";

    private static final String PRODUCT_PRICING_RESPONSE = "responses/shoppingCart/productPricing.json";

    private static final String CREATE_SOF_REQUEST = "requests/shoppingCart/createSof.json";

    private static final String SOF_RESPONSE = "responses/shoppingCart/sofList.json";

    private static final String CONTACT_RESPONSE = "responses/shoppingCart/customerContacts.json";

    private static final String ADDRESS_RESPONSE = "responses/shoppingCart/customerAddresses.json";

    private static final String ADDRESS_REQUEST = "requests/shoppingCart/customerAddress.json";

    private static final String PRODUCT_ID = "test-product";

    private static final String IS_CAPPED_NO = "No";;

    private static final String PURPOSE = "Calculate";

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private SugarCRMAuthorization sugarCRMAuthorizationMock;

    @Test
    public void testGetLocation() throws Exception {
        LocationList locationList = ResourceLoader.readAndGetObject(LOCATION_RESPONSE, LocationList.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<LocationList>> any()))
                .thenReturn(locationList);
        LocationList locations = Whitebox.invokeMethod(shopppingCartServiceSpy, "getLocations");
        assertNotNull("response of getLocations should not be null", locations);
        assertEquals("response of getLocations must be equal to mock data", locationList, locations);
    }

    @Test
    public void testGetSubCategories() throws Exception {
        SubCategoryList subCategoryList = ResourceLoader.readAndGetObject(SUB_CATEGORY_RESPONSE, SubCategoryList.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<SubCategoryList>> any()))
                .thenReturn(subCategoryList);
        SubCategoryList subSubCategories = Whitebox.invokeMethod(shopppingCartServiceSpy, "getSubCategories");
        assertNotNull("response of getSubCategories should not be null", subSubCategories);
        assertEquals("response of getSubCategories must be equal to mock data", subCategoryList, subSubCategories);
    }

    @Test
    public void testGetSubSubCategories() throws Exception {
        SubSubCategoryList subSubCategoryList = ResourceLoader.readAndGetObject(SUB_SUB_CATEGORY_RESPONSE,
                SubSubCategoryList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<SubSubCategoryList>> any()))
                .thenReturn(subSubCategoryList);
        SubSubCategoryList subSubCategories = Whitebox.invokeMethod(shopppingCartServiceSpy, "getSubSubCategories",
                SUB_CATEGORY_ID);
        assertNotNull("response of getSubSubCategories should not be null", subSubCategories);
        assertEquals("response of getSubSubCategories must be equal to mock data", subSubCategoryList,
                subSubCategories);
    }

    @Test
    public void testGetProductsGB() throws Exception {
        ProductList productList = ResourceLoader.readAndGetObject(PRODUCT_LIST_RESPONSE, ProductList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ProductList>> any()))
                .thenReturn(productList);
        ProductList products = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProducts", LOCATION_ID,
                SUB_CATEGORY_ID, GB, IS_CAPPED_NO);
        assertNotNull("response of getProducts should not be null", products);
        assertEquals("response of getProducts must be equal to mock data", productList, products);
    }

    @Test
    public void testGetProductsMBPS() throws Exception {
        ProductList productList = ResourceLoader.readAndGetObject(PRODUCT_LIST_RESPONSE, ProductList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ProductList>> any()))
                .thenReturn(productList);
        ProductList products = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProducts", LOCATION_ID,
                SUB_CATEGORY_ID, MBPS, IS_CAPPED_NO);
        assertNotNull("response of getProducts should not be null", products);
        assertEquals("response of getProducts must be equal to mock data", productList, products);
    }

    @Test
    public void testGetProductsMBPSyes() throws Exception {
        ProductList productList = ResourceLoader.readAndGetObject(PRODUCT_LIST_RESPONSE, ProductList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ProductList>> any()))
                .thenReturn(productList);
        ProductList products = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProducts", LOCATION_ID,
                SUB_CATEGORY_ID, MBPS, IS_CAPPED_YES);
        assertNotNull("response of getProducts should not be null", products);
        assertEquals("response of getProducts must be equal to mock data", productList, products);
    }

    @Test
    public void testGetProductswithEmptyStringPlan() throws Exception {
        ProductList productList = ResourceLoader.readAndGetObject(PRODUCT_LIST_RESPONSE, ProductList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ProductList>> any()))
                .thenReturn(productList);
        ProductList products = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProducts", LOCATION_ID,
                SUB_CATEGORY_ID, "", IS_CAPPED_NO);
        assertNotNull("response of getProducts should not be null", products);
        assertEquals("response of getProducts must be equal to mock data", productList, products);
    }

    @Test
    public void testGetProduct() throws Exception {
        ProductList productDetail = ResourceLoader.readAndGetObject(PRODUCT_DETAIL_RESPONSE, ProductList.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<ProductList>> any()))
                .thenReturn(productDetail);
        ProductList product = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProduct", PRODUCT_ID);
        assertNotNull("response of getProduct should not be null", product);
        assertEquals("response of getProduct must be equal to mock data", productDetail, product);
    }

    @Test
    public void testGetProductPricing() throws Exception {
        String productPricingResponseJson = ResourceLoader.readFile(PRODUCT_PRICING_RESPONSE);
        ProductPriceResponse productPriceResponse = ResourceLoader.readAndGetObject(PRODUCT_PRICING_RESPONSE,
                ProductPriceResponse.class);
        ProductPriceRequest productPriceRequest = ResourceLoader.readAndGetObject(PRODUCT_PRICING_REQUEST,
                ProductPriceRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<ProductPriceRequest>> any(),
                Mockito.anyString())).thenReturn(productPricingResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<ProductPriceResponse>> any()))
                .thenReturn(productPriceResponse);
        ProductPriceResponse priceResponse = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProductPricing",
                PURPOSE, productPriceRequest);
        assertNotNull("response of getProductPricing should not be null", priceResponse);
        assertEquals("response of getproductpricing must be equal to mock data", productPriceResponse, priceResponse);
    }

    @Test
    public void testGetProductPricingWithInvalidToken() throws Exception {
        String productPricingResponseJson = ResourceLoader.readFile(PRODUCT_PRICING_RESPONSE);
        ProductPriceResponse productPriceResponse = ResourceLoader.readAndGetObject(PRODUCT_PRICING_RESPONSE,
                ProductPriceResponse.class);
        ProductPriceRequest productPriceRequest = ResourceLoader.readAndGetObject(PRODUCT_PRICING_REQUEST,
                ProductPriceRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.generateServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<ProductPriceRequest>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.toString())
                .thenReturn(productPricingResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<ProductPriceResponse>> any()))
                .thenReturn(productPriceResponse);
        ProductPriceResponse priceResponse = Whitebox.invokeMethod(shopppingCartServiceSpy, "getProductPricing",
                PURPOSE, productPriceRequest);
        assertNotNull("response of getProductPricing should not be null", priceResponse);
        assertEquals("response of getproductpricing must be equal to mock data", productPriceResponse, priceResponse);
    }

    @Test
    public void testCreateSof() throws Exception {
        String sofResponseJson = ResourceLoader.readFile(SOF_RESPONSE);
        SofResponse sofResponse = ResourceLoader.readAndGetObject(SOF_RESPONSE, SofResponse.class);
        CreateSofRequest createSofRequest = ResourceLoader.readAndGetObject(CREATE_SOF_REQUEST, CreateSofRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(sofResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<SofResponse>> any()))
                .thenReturn(sofResponse);
        SofResponse sofs = Whitebox.invokeMethod(shopppingCartServiceSpy, "createSof", createSofRequest, "new");
        assertNotNull("response of createSof should not be null", sofs);
        assertEquals("response of createSof must be equal to mock data", sofResponse, sofs);
    }

    @Test
    public void testCreateSofWithInvalidToken() throws Exception {
        String sofResponseJson = ResourceLoader.readFile(SOF_RESPONSE);
        SofResponse sofResponse = ResourceLoader.readAndGetObject(SOF_RESPONSE, SofResponse.class);
        CreateSofRequest productPriceRequest = ResourceLoader.readAndGetObject(CREATE_SOF_REQUEST,
                CreateSofRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.generateServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.toString()).thenReturn(sofResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<SofResponse>> any()))
                .thenReturn(sofResponse);
        SofResponse sofs = Whitebox.invokeMethod(shopppingCartServiceSpy, "createSof", productPriceRequest, "new");
        assertNotNull("response of createSof should not be null", sofs);
        assertEquals("response of createSof must be equal to mock data", sofResponse, sofs);
    }

    @Test
    public void testGetCustomerAddress() throws Exception {
        String customerAddressJson = ResourceLoader.readFile(ADDRESS_RESPONSE);
        CustomerAddressResponse addressResponse = ResourceLoader.readAndGetObject(ADDRESS_RESPONSE,
                CustomerAddressResponse.class);
        CustomerAddressRequest createSofRequest = ResourceLoader.readAndGetObject(ADDRESS_REQUEST,
                CustomerAddressRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(customerAddressJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<CustomerAddressResponse>> any()))
                .thenReturn(addressResponse);
        CustomerAddressResponse sofs = Whitebox.invokeMethod(shopppingCartServiceSpy, "getCustomerAddress",
                createSofRequest);
        assertNotNull("response of getCustomerAddress should not be null", sofs);
        assertEquals("response of getCustomerAddress must be equal to mock data", addressResponse, sofs);
    }

    @Test
    public void testGetCustomerAddressWithInvalidToken() throws Exception {
        String customerAddressJson = ResourceLoader.readFile(ADDRESS_RESPONSE);
        CustomerAddressResponse addressResponse = ResourceLoader.readAndGetObject(ADDRESS_RESPONSE,
                CustomerAddressResponse.class);
        CustomerAddressRequest productPriceRequest = ResourceLoader.readAndGetObject(ADDRESS_REQUEST,
                CustomerAddressRequest.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.generateServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.toString()).thenReturn(customerAddressJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<CustomerAddressResponse>> any()))
                .thenReturn(addressResponse);
        CustomerAddressResponse sofs = Whitebox.invokeMethod(shopppingCartServiceSpy, "getCustomerAddress",
                productPriceRequest);
        assertNotNull("response of getCustomerAddress should not be null", sofs);
        assertEquals("response of getCustomerAddress must be equal to mock data", addressResponse, sofs);
    }

    @Test
    public void testGetContacts() throws Exception {
        String contactsResponseJson = ResourceLoader.readFile(CONTACT_RESPONSE);
        CustomerContactResponse contactResponse = ResourceLoader.readAndGetObject(CONTACT_RESPONSE,
                CustomerContactResponse.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(contactsResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<CustomerContactResponse>> any()))
                .thenReturn(contactResponse);
        CustomerContactResponse customerContactResponse = Whitebox.invokeMethod(shopppingCartServiceSpy, "getContacts",
                MockData.MAIN_CUSTOMER_ID.getString());
        assertNotNull("response of getProductPricing should not be null", customerContactResponse);
        assertEquals("response of getproductpricing must be equal to mock data", contactResponse,
                customerContactResponse);
    }

    @Test
    public void testGetContactsWithInvalidToken() throws Exception {
        String contactsResponseJson = ResourceLoader.readFile(CONTACT_RESPONSE);
        CustomerContactResponse contactResponse = ResourceLoader.readAndGetObject(CONTACT_RESPONSE,
                CustomerContactResponse.class);
        Mockito.when(sugarCRMAuthorizationMock.fetchServiceToken()).thenReturn(MockData.INVALID_TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.generateServiceToken()).thenReturn(MockData.TOKEN.toString());
        Mockito.when(sugarCRMAuthorizationMock.getSugarServiceResponse(Mockito.<Class<CreateSofRequest>> any(),
                Mockito.anyString())).thenReturn(MockData.INVALID_TOKEN.toString()).thenReturn(contactsResponseJson);
        Mockito.when(mapperMock.readValue(Mockito.anyString(), Mockito.<Class<CustomerContactResponse>> any()))
                .thenReturn(contactResponse);
        CustomerContactResponse customerContactResponse = Whitebox.invokeMethod(shopppingCartServiceSpy, "getContacts",
                MockData.MAIN_CUSTOMER_ID.getString());
        assertNotNull("response of getProductPricing should not be null", customerContactResponse);
        assertEquals("response of getproductpricing must be equal to mock data", contactResponse,
                customerContactResponse);
    }
}
