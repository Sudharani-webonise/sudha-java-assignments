package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.CustomerAddressRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.DeleteLineItemRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductPriceRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPriceResponse;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.ShoppingCartService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ShoppingCartController.class)
public class ShoppingCartControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ShoppingCartController shoppingCartControllerSpy;

    @Mock
    private ShoppingCartService cartServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(shoppingCartControllerSpy).build();
    }

    @Test
    public void testFetchLocations() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/locations")).andExpect(status().isOk());
    }

    @Test
    public void testFetchSubCategories() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/subCategories")).andExpect(status().isOk());
    }

    @Test
    public void testFetchSubCategoriesById() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/subSubCategories/{subCatId}", MockData.SUB_CAT_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchProducts() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/products/{subCatId}", MockData.SUB_CAT_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchProduct() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/product/{subCatId}", MockData.SUB_CAT_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchProductByProductId() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/product/{productId}", MockData.PRODUCT_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchProductPrice() throws Exception {
        mockMvc.perform(
                post("/api/shoppingCart/products/price").content(ResourceLoader.asJsonString(new ProductPriceRequest()))
                        .param("purpose", MockData.PURPOSE.getString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateContract() throws Exception {
        mockMvc.perform(
                post("/api/shoppingCart/contract/create").content(ResourceLoader.asJsonString(new CreateSofRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchContacts() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/customer/contacts/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateContractAddress() throws Exception {
        mockMvc.perform(post("/api/shoppingCart/customer/address")
                .content(ResourceLoader.asJsonString(new CustomerAddressRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testaddLineItems() throws Exception {
        mockMvc.perform(post("/api/shoppingCart/lineItem/add")
                .content(ResourceLoader.asJsonString(new ProductPriceResponse()))
                .param("emailId", MockData.USER_EMAIL.getString())
                .param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .param("projectId", MockData.PROJECT_ID.getString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateLineItems() throws Exception {
        mockMvc.perform(post("/api/shoppingCart/lineItem/update")
                .content(ResourceLoader.asJsonString(new ProductPriceResponse()))
                .param("emailId", MockData.USER_EMAIL.getString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteLineItems() throws Exception {
        mockMvc.perform(delete("/api/shoppingCart/lineItem/delete")
                .content(ResourceLoader.asJsonString(new DeleteLineItemRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchProductFromShoppingCart() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/cartProducts/customer/{mainCustomerId}/associated/{associateCustomerId}",
                MockData.MAIN_CUSTOMER_ID.getLong(), MockData.ASSOCIATE_CUSTOMER_ID.getLong()).param("emailId",
                        MockData.USER_EMAIL.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchProductFromShoppingCartFromDB() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/sofs/{transactionNumber}", MockData.TRANSACTION_NUMBER.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveProducts() throws Exception {
        mockMvc.perform(post("/api/shoppingCart/save/products", MockData.PAYMENT_SOURCE.getString())
                .content(ResourceLoader.asJsonString(new ProductPriceResponse()))
                .param("emailId", MockData.USER_EMAIL.getString()).contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testLineItemReadToken() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/lineItem/read/token")
                .param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associastedCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .param("lineItemToken", MockData.LINE_ITEM_NUMBER.getString())).andExpect(status().isOk());
    }

    @Test
    public void testLineItemReadInstaneId() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/lineItem/read/instanceId")
                .param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associastedCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .param("instanceId", "instance_id_val")).andExpect(status().isOk());
    }

    @Test
    public void testFetchBillGroupInfo() throws Exception {
        mockMvc.perform(get("/api/shoppingCart/billGroup/info").param("billTo", MockData.MAIN_CUSTOMER_ID.getString())
                .param("supportTo", MockData.ASSOCIATE_CUSTOMER_ID.getString())).andExpect(status().isOk());
    }

    @Test
    public void testPayForSof() throws Exception {
        mockMvc.perform(post("/api/shoppingCart/pay/sof/{paymentSource}", MockData.PAYMENT_SOURCE.getString())
                .param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("transactionNumber", MockData.TRANSACTION_NUMBER.getString())
                .param("merchantRefNumber", MockData.REFERENCE_NUMBER.getString())
                .content(ResourceLoader.asJsonString(new CreateSofRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testCreateSofsLineItem() throws Exception {
        mockMvc.perform(
                get("/api/shoppingCart/create/sofs/{merchantRefNumber}}", MockData.REFERENCE_NUMBER.getString()))
                .andExpect(status().isOk());
    }

}
