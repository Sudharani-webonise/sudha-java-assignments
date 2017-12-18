package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.netmagic.spectrum.dto.contact.request.AddContactRequest;
import com.netmagic.spectrum.dto.contact.request.EditContactRequest;
import com.netmagic.spectrum.dto.contact.request.InviteContact;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.ContactService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(ContactController.class)
public class ContactControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ContactController contactControllerSpy;

    @Mock
    private ContactService contactServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contactControllerSpy).build();
    }

    @Test
    public void testFetchWidgetData() throws Exception {
        mockMvc.perform(get("/api/contacts/widget/customer/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchListData() throws Exception {
        mockMvc.perform(get("/api/contacts/customer/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchContactDetails() throws Exception {
        mockMvc.perform(get("/api/contacts/details/user/{userId}", MockData.USER_ID.getLong())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testEditContact() throws Exception {
        mockMvc.perform(put("/api/contacts/edit").content(ResourceLoader.asJsonString(new EditContactRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testAddNewContact() throws Exception {
        mockMvc.perform(post("/api/contacts/add").content(ResourceLoader.asJsonString(new AddContactRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testInviteContact() throws Exception {
        mockMvc.perform(post("/api/contacts/invite").content(ResourceLoader.asJsonString(new InviteContact()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchContactTypes() throws Exception {
        mockMvc.perform(get("/api/contacts/types").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchContactSubTypes() throws Exception {
        mockMvc.perform(get("/api/contacts/subtypes").param("typeId", MockData.TYPE_ID.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchContactCallingConfig() throws Exception {
        mockMvc.perform(get("/api/contacts/calling/config").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchContactDetailsGet() throws Exception {
        mockMvc.perform(get("/api/contacts/internal").param("mainCustomerId", MockData.MAIN_CUSTOMER_ID.getString())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

}
