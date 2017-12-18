package com.netmagic.spectrum.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.dto.ticket.response.FilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.RaiseTicketResponse;
import com.netmagic.spectrum.dto.ticket.response.Ticket;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubType;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseType;
import com.netmagic.spectrum.dto.ticket.response.TicketDetail;
import com.netmagic.spectrum.dto.ticket.response.TicketWidget;
import com.netmagic.spectrum.service.TicketService;

@RequestMapping(value = "/api/tickets")
@RestController
public class TicketController {

    @Autowired
    private TicketService ticketService;

    private static final String DESCENDING = "DESC";

    private static final String DEFAULT_SORT = "ticket Number";

    @Value("${page-size}")
    private String ticketsPageSize;

    @RequestMapping(value = "/widget/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TicketWidget getTicketWidgetCount(@PathVariable("customerId") Long customerId,
            @RequestParam(value = "associateCustomerId") Long associatedCustomerId,
            @RequestParam(value = "projectId", required = false) Long projectId) {
        return ticketService.getTicketWidgetData(customerId, associatedCustomerId, projectId,
                ticketService.fetchFilteredTicketConfig());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Ticket> getTicketList(@RequestBody final TicketListRequest ticketListRequest,
            @RequestParam(value = "orderBy", defaultValue = DESCENDING) String orderBy,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT) String sortBy) {
        return ticketService.getTicketList(ticketListRequest, orderBy, sortBy);
    }

    @RequestMapping(value = "/{ticketNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TicketDetail fetchTicketDetail(@PathVariable("ticketNumber") String ticketNumber,
            @RequestParam(value = "page", defaultValue = "1") String pageNo,
            @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy) {
        return ticketService.getTicketDetails(ticketNumber, orderBy, pageNo);
    }

    @RequestMapping(value = "/{ticketNumber}/worklog/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateTicketWorklog(@PathVariable("ticketNumber") String ticketNumber,
            @RequestBody final UpdateWorklogRequest updateWorklogRequest) {
        return ticketService.updateTicketWorklog(updateWorklogRequest);
    }

    @RequestMapping(value = "/{ticketNumber}/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateTicket(@PathVariable("ticketNumber") String ticketNumber,
            @RequestBody final UpdateTicketRequest updateTicketRequest) {
        return ticketService.updateTicket(updateTicketRequest);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TicketCaseType> getTicketCaseTypes() {
        return ticketService.getTicketCaseTypes();
    }

    @RequestMapping(value = "/subtypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TicketCaseSubType> getTicketCaseSubTypes(@RequestParam("caseTypeId") Long caseTypeId) {
        return ticketService.getTicketSubCaseTypes(caseTypeId);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RaiseTicketResponse createNewTicket(@RequestBody final RaiseTicketRequest raiseTicketRequest) {
        return ticketService.createTicket(raiseTicketRequest);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FilteredTicketConfig getFilteredTicketConfig() {
        return ticketService.fetchFilteredTicketConfig();
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void fetchFile(@PathVariable(value = "attachmentId") Long attachmentId, HttpServletResponse response)
            throws IOException {
        ticketService.getFile(attachmentId, response);
    }
}
