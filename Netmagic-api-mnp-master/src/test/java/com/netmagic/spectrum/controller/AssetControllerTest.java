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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netmagic.spectrum.dto.asset.request.AssetListRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.AssetService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(AssetController.class)
public class AssetControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private AssetController assetControllerSpy;

    @Mock
    private AssetService assetServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(assetControllerSpy).build();
    }

    @Test
    public void testFetchWidgetData() throws Exception {
        mockMvc.perform(get("/api/assets/widget/customer/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testFetchAssetTagList() throws JsonProcessingException, Exception {
        mockMvc.perform(post("/api/assets/").content(ResourceLoader.asJsonString(new AssetListRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testFetchAssetTagListGet() throws Exception {
        mockMvc.perform(get("/api/assets/tags/customer/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddAssetTag() throws Exception {
        mockMvc.perform(post("/api/assets/tag/{assetId}", MockData.ASSET_ID.getLong())
                .param("tagName", MockData.TAG_NAME.getString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveAssetTag() throws Exception {
        mockMvc.perform(post("/api/assets/tag/remove/{assetId}", MockData.ASSET_ID.getLong())
                .param("tagName", MockData.TAG_NAME.getString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
