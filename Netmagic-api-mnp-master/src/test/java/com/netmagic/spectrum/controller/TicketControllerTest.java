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

import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.TicketService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(TicketController.class)
public class TicketControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TicketController ticketControllerSpy;

    @Mock
    private TicketService ticketServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketControllerSpy).build();
    }

    @Test
    public void testGetTicketWidgetCount() throws Exception {
        mockMvc.perform(get("/api/tickets/widget/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                .param("associateCustomerId", MockData.ASSOCIATE_CUSTOMER_ID.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTicketList() throws Exception {
        mockMvc.perform(
                post("/api/tickets/").content(ResourceLoader.asJsonString(new TicketListRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchTicketDetail() throws Exception {
        mockMvc.perform(get("/api/tickets/{ticketNumber}", MockData.TICKET_NUMBER.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateWorkLog() throws Exception {
        mockMvc.perform(put("/api/tickets/{ticketNumber}/worklog/update",
                MockData.TICKET_NUMBER.getString())
                        .content(ResourceLoader.asJsonString(new UpdateWorklogRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTicket() throws Exception {
        mockMvc.perform(
                put("/api/tickets/{ticketNumber}/update", MockData.TICKET_NUMBER.getString())
                        .content(ResourceLoader.asJsonString(new UpdateTicketRequest()))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testFetchTicketTypes() throws Exception {
        mockMvc.perform(get("/api/tickets/types")).andExpect(status().isOk());
    }

    @Test
    public void testFetchTicketSubTypes() throws Exception {
        mockMvc.perform(
                get("/api/tickets/subtypes").param("caseTypeId", MockData.TYPE_ID.getString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateNewTicket() throws Exception {
        mockMvc.perform(post("/api/tickets/create")
                .content(ResourceLoader.asJsonString(new RaiseTicketRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

    @Test
    public void testGetFilteredTicketConfig() throws Exception {
        mockMvc.perform(get("/api/tickets/config")).andExpect(status().isOk());
    }

    @Test
    public void testFetchFile() throws Exception {
        mockMvc.perform(
                get("/api/tickets/download/{attachmentId}", MockData.ATTACHMENT_ID.getLong()))
                .andExpect(status().isOk());
    }

}
