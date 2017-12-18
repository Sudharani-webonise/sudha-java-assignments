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
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.role.request.RoleAddRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.RoleService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(RoleController.class)
public class RoleControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RoleController roleControllerSpy;

    @Mock
    private RoleService roleServiceSpy;

    @Mock
    private AuthUser authUser;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(roleControllerSpy).build();
    }

    @Test
    public void testFetchRoleDetails() throws Exception {
        mockMvc.perform(get("/api/role/customer/{mainCustomerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testAddRoleDetails() throws JsonProcessingException, Exception {
        mockMvc.perform(post("/api/role/add").content(ResourceLoader.asJsonString(new RoleAddRequest()))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
