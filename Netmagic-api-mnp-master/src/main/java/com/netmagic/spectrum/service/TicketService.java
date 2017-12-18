package com.netmagic.spectrum.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.dto.ticket.response.FilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.RaiseTicketResponse;
import com.netmagic.spectrum.dto.ticket.response.Ticket;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubType;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseType;
import com.netmagic.spectrum.dto.ticket.response.TicketConfig;
import com.netmagic.spectrum.dto.ticket.response.TicketDetail;
import com.netmagic.spectrum.dto.ticket.response.TicketWidget;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

@Service
public interface TicketService {

    /**
     * This method sends the API request to update work-log for a ticket. The
     * response can have values: ["Success"|"Failure"]
     *
     * @param requestBody
     * @return String
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_EDIT')")
    String updateTicketWorklog(UpdateWorklogRequest requestBody)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the ticket Config Data from NM Servers
     *
     * @return
     * @throws CacheServiceException
     *             , ServiceUnavailableException
     */
    TicketConfig getTicketConfig() throws ServiceUnavailableException;

    /**
     * This method fetches the ticket Widget Data from NM Servers
     * 
     * @param customerId
     * @param associateCustomerId
     * @param projectId
     * @param filteredTicketConfig
     * @return {@link TicketWidget}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_WDGT')")
    TicketWidget getTicketWidgetData(Long customerId, Long associateCustomerId, Long projectId,
            FilteredTicketConfig filteredTicketConfig) throws RequestViolationException, ServiceUnavailableException;

    /**
     * Method returns the list of the tickets according to the passed
     * requestBody object. The <b>orderBy</b> parameter has possible values as:
     * ["ASC"|"DESC"]
     *
     * @param requestBody
     * @param orderBy
     * @return List of Ticket objects
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_LIST')")
    List<Ticket> getTicketList(TicketListRequest requestBody, String orderBy, String sortBy)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the Ticket Details with Worklogs for Single Ticket
     * from NM Servers
     *
     * @param ticketNumber
     * @param orderBy
     * @param pageNumber
     * @return TicketDetail
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_VIEW')")
    TicketDetail getTicketDetails(String ticketNumber, String orderBy, String pageNumber)
            throws ServiceUnavailableException;

    /**
     * This method fetches the different case category of tickets
     *
     * @return List<TicketCaseType>
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_CRET')")
    List<TicketCaseType> getTicketCaseTypes() throws ServiceUnavailableException;

    /**
     * This method fetches the different types of tickets based on given case
     * type
     *
     * @param caseTypeId
     * @return List<TicketCaseSubType>
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_CRET')")
    List<TicketCaseSubType> getTicketSubCaseTypes(Long caseTypeId)
            throws CacheServiceException, ServiceUnavailableException;

    /**
     * This method sends the request for a new Ticket and returns the response
     * of the new ticket created
     *
     * @return RaiseTicketResponse
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_CRET')")
    RaiseTicketResponse createTicket(RaiseTicketRequest raiseTicketRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method sends the request to update a ticket's status to reopen or
     * closed
     *
     * @param updateTicketRequest
     * @return boolean
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_ROPN')")
    Boolean updateTicket(UpdateTicketRequest updateTicketRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method calls the Ticket Config API and filters the list of
     * TicketTypes, SeverityTypes, StatusTypes and returns them
     *
     * @return
     * @throws ServiceUnavailableException
     */
    FilteredTicketConfig fetchFilteredTicketConfig() throws ServiceUnavailableException;

    /**
     * This method fetches the file in byte [] format from NM API and then the
     * byte [] array is converted to a file on the server and an output stream
     * is opened for the file to download
     * 
     * @param attachmentId
     * @param request
     * @param response
     * @throws ServiceUnavailableException
     * @throws IOException
     */
    void getFile(Long attachmentId, HttpServletResponse response) throws ServiceUnavailableException, IOException;

}
