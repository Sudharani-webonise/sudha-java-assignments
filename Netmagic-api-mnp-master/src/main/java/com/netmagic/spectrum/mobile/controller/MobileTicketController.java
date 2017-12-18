package com.netmagic.spectrum.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.commons.AuthType;
import com.netmagic.spectrum.commons.AuthUser;
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
import com.netmagic.spectrum.dto.user.request.UserPermissionRequest;
import com.netmagic.spectrum.dto.user.response.UserPermissions;
import com.netmagic.spectrum.mobile.service.MobileTicketService;
import com.netmagic.spectrum.service.UserService;

@RequestMapping(value = "/mobile/api/tickets")
@RestController
@CrossOrigin
public class MobileTicketController {

    @Autowired
    private MobileTicketService mobileTicketService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthUser authUser;

    private static final String DESCENDING = "DESC";

    private static final String DEFAULT_SORT = "ticket Number";

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MobileTicketListResponse getTicketList(@RequestBody final TicketListRequest mobileTicketListRequest,
            @RequestParam(value = "orderBy", defaultValue = DESCENDING) String orderBy,
            @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT) String sortBy) {
        setUserPermission(mobileTicketListRequest);
        return mobileTicketService.getMobileTicketList(mobileTicketListRequest, orderBy, sortBy);
    }

    private void setUserPermission(final TicketListRequest mobileTicketListRequest) {
        UserPermissionRequest userPermissionRequest = new UserPermissionRequest(authUser.getAuthenticatedUserId(),
                mobileTicketListRequest.getMainCustomerId(), mobileTicketListRequest.getAssociateCustomerId(),
                mobileTicketListRequest.getProjectId());
        UserPermissions userPermissions = userService.getPermissions(userPermissionRequest);
        authUser.updateAuthAuthorities(userPermissions, AuthType.USER.getAuthType());
    }

    @RequestMapping(value = "/{ticketNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MobileTicketDetail fetchTicketDetail(@PathVariable("ticketNumber") String ticketNumber,
            @RequestParam(value = "page", defaultValue = "1") String pageNo,
            @RequestParam(value = "orderBy", defaultValue = "DESC") String orderBy) {
        return mobileTicketService.getMobileTicketDetails(ticketNumber, orderBy, pageNo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public RaiseTicketResponse createNewTicket(@RequestBody final RaiseTicketRequest raiseTicketRequest) {
        return mobileTicketService.createMobileTicket(raiseTicketRequest);
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public MobileTicketCaseType getTicketCaseTypes() {
        return mobileTicketService.getMobileTicketCaseTypes();
    }

    @RequestMapping(value = "/subtypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TicketCaseSubTypeResponse getTicketCaseSubTypes(@RequestParam("caseTypeId") Long caseTypeId) {
        return mobileTicketService.getMobileTicketSubCaseTypes(caseTypeId);
    }

    @RequestMapping(value = "/config", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FilteredTicketConfig getFilteredTicketConfig() {
        return mobileTicketService.fetchFilteredTicketConfig();
    }

    @RequestMapping(value = "/{ticketNumber}/worklog/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateTicketWorklog(@PathVariable("ticketNumber") String ticketNumber,
            @RequestBody final UpdateWorklogRequest updateWorklogRequest) {
        return mobileTicketService.updateTicketWorklog(updateWorklogRequest);
    }

    @RequestMapping(value = "/{ticketNumber}/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean updateTicket(@PathVariable("ticketNumber") String ticketNumber,
            @RequestBody final UpdateTicketRequest updateTicketRequest) {
        return mobileTicketService.updateTicket(updateTicketRequest);
    }

    @RequestMapping(value = "/download/{attachmentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AttachedFileInformation fetchFileContent(@PathVariable(value = "attachmentId") Long attachmentId,
            @RequestParam(value = "appType", defaultValue = "android") String appType) {
        return mobileTicketService.getFileInformation(attachmentId, appType);
    }
}
