package com.netmagic.spectrum.mobile.controller;

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

import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.mobile.service.MobileTicketService;
import com.netmagic.spectrum.service.UserService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(MobileTicketController.class)
public class MobileTicketControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MobileTicketController mobileTicketControllerSpy;

    @Mock
    private MobileTicketService mobileTicketServiceSpy;

    @Mock
    private UserService userServiceSpy;

    @Mock
    private AuthUser authUserSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mobileTicketControllerSpy).build();
    }

    @Test
    public void testGetMobileTicketList() throws Exception {
        mockMvc.perform(post("/mobile/api/tickets/")
                .content(ResourceLoader.asJsonString(new TicketListRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testFetchMobileTicketDetail() throws Exception {
        mockMvc.perform(
                get("/mobile/api/tickets/{ticketNumber}", MockData.TICKET_NUMBER.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testMobileUpdateWorkLog() throws Exception {
        mockMvc.perform(put("/mobile/api/tickets/{ticketNumber}/worklog/update",
                MockData.TICKET_NUMBER.getString())
                        .content(ResourceLoader.asJsonString(new UpdateWorklogRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMobileTicket() throws Exception {
        mockMvc.perform(
                put("/mobile/api/tickets/{ticketNumber}/update", MockData.TICKET_NUMBER.getString())
                        .content(ResourceLoader.asJsonString(new UpdateTicketRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchMobileTicketTypes() throws Exception {
        mockMvc.perform(get("/mobile/api/tickets/types")).andExpect(status().isOk());
    }

    @Test
    public void testFetchMobileTicketSubTypes() throws Exception {
        mockMvc.perform(get("/mobile/api/tickets/subtypes").param("caseTypeId",
                MockData.TYPE_ID.getString())).andExpect(status().isOk());
    }

    @Test
    public void testCreateNewTicketMobile() throws Exception {
        mockMvc.perform(post("/mobile/api/tickets/create")
                .content(ResourceLoader.asJsonString(new RaiseTicketRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testGetFilteredMobileTicketConfig() throws Exception {
        mockMvc.perform(get("/mobile/api/tickets/config")).andExpect(status().isOk());
    }

    @Test
    public void testFetchFileMobile() throws Exception {
        mockMvc.perform(get("/mobile/api/tickets/download/{attachmentId}",
                MockData.ATTACHMENT_ID.getLong())).andExpect(status().isOk());
    }

}
