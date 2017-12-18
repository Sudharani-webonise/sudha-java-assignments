package com.netmagic.spectrum.mobile.service;

import org.springframework.security.access.prepost.PreAuthorize;

import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketCaseType;
import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketDetail;
import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketListResponse;
import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.dto.ticket.response.AttachedFileInformation;
import com.netmagic.spectrum.dto.ticket.response.FilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.RaiseTicketResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubTypeResponse;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface MobileTicketService {

    /***
     * Method returns the list of the tickets according to the passed
     * requestBody object. The <b>orderBy</b> parameter has possible values as:
     * ["ASC"|"DESC"]
     * 
     * @param ticketListRequest
     * @param orderBy
     * @return
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     * @param sortBy
     */

    @PreAuthorize("hasAuthority('TIC_LIST')")
    MobileTicketListResponse getMobileTicketList(TicketListRequest ticketListRequest, String orderBy, String sortBy)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the Ticket Details with Worklogs for Single Ticket
     * from NM Servers
     *
     * @param ticketNumber
     * @param orderBy
     * @param pageNumber
     * @return MobileTicketDetail
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    @PreAuthorize("hasAuthority('TIC_VIEW')")
    MobileTicketDetail getMobileTicketDetails(String ticketNumber, String orderBy, String pageNumber)
            throws ServiceUnavailableException;

    /**
     * This method sends the request for a new Ticket and returns the response
     * of the new ticket created
     *
     * @return RaiseTicketResponse
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    @PreAuthorize("hasAuthority('TIC_CRET')")
    RaiseTicketResponse createMobileTicket(RaiseTicketRequest raiseTicketRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the different case category of tickets for mobile
     *
     * @return MobileTicketService
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    MobileTicketCaseType getMobileTicketCaseTypes() throws ServiceUnavailableException;

    /**
     * This method fetches the different types of tickets based on given case
     * type got mobile
     *
     * @param caseTypeId
     * @return TicketCaseSubTypeResponse
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    TicketCaseSubTypeResponse getMobileTicketSubCaseTypes(Long caseTypeId)
            throws CacheServiceException, ServiceUnavailableException;

    /**
     * This method calls the Ticket Config API and filters the list of
     * TicketTypes, SeverityTypes, StatusTypes and returns them
     *
     *
     * @return
     * @throws ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    FilteredTicketConfig fetchFilteredTicketConfig() throws ServiceUnavailableException;

    /**
     * This method sends the API request to update work-log for a ticket. The
     * response can have values: ["Success"|"Failure"]
     *
     * @param requestBody
     * @return String
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     * 
     * @author webonise
     * @version Spectrum 1.0
     */
    String updateTicketWorklog(UpdateWorklogRequest requestBody)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method sends a request to update a ticket's status to reopen or
     * closed.
     * 
     * @param updateTicketRequest
     * @return boolean
     * @throws RequestViolationException
     *             ,ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('TIC_EDIT')")
    boolean updateTicket(UpdateTicketRequest updateTicketRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /***
     * This method fetch the file information
     * 
     * @param attachmentId
     * @param appType
     * @return
     * @throws ServiceUnavailableException
     */
    AttachedFileInformation getFileInformation(Long attachmentId, String appType) throws ServiceUnavailableException;
}
