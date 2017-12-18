package com.netmagic.spectrum.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Tickets;
import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketWidgetRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.dto.ticket.response.AttachedFileInformation;
import com.netmagic.spectrum.dto.ticket.response.CachedFilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.FilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.RaiseTicketResponse;
import com.netmagic.spectrum.dto.ticket.response.Ticket;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubType;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubTypeResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseType;
import com.netmagic.spectrum.dto.ticket.response.TicketConfig;
import com.netmagic.spectrum.dto.ticket.response.TicketDetail;
import com.netmagic.spectrum.dto.ticket.response.TicketListResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketSeverity;
import com.netmagic.spectrum.dto.ticket.response.TicketSeverityCount;
import com.netmagic.spectrum.dto.ticket.response.TicketState;
import com.netmagic.spectrum.dto.ticket.response.TicketStatusCount;
import com.netmagic.spectrum.dto.ticket.response.TicketWidget;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.TicketService;

@Component
public class TicketServiceImpl implements TicketService {

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CacheService<TicketConfig> ticketConfigCacheService;

    @Autowired
    private CacheService<TicketCaseSubTypeResponse> ticketCaseSubTypeCacheService;

    @Autowired
    private CacheService<CachedFilteredTicketConfig> filteredTicketConfigCacheService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TicketServiceImpl.class);

    private static final int BUFFER_SIZE = 4096;

    private static final String ACCESS_KEY_URL = "?accesskey=";

    private static final String ACCESS_SOURCE = "&source=";

    private static final String TICKET_NUMBER_URL = "&ticketNumber=";

    private static final String RECORD_PER_PAGE = "&recordsPerPage=";

    private static final String PAGE_NUMBER_URL = "&requestPage=";

    private static final String TICKET_SUB_TYPE = "&ticketCaseTypeId=";

    private static final String SEV_4_TO_7 = "S4-7";

    private static final String ASSIGNED_AND_WIP = "Assigned/WIP";

    private static final String CONTENT_DISPOSITION = "Content-Disposition";

    private static final String ATTACHMENT_FILENAME = "attachment; filename=\"%s\"";

    private static final String FILTER_CASE_TYPE = "Incident|Request";

    private static final String FILTER_STATE_TYPE = "Opened|Assigned|Work in Progress|Waiting on Vendor|Monitoring|Resolved Pending Closure|Waiting on Customer|Reopen";

    private static final String STATUS_TYPES = "Opened|Resolved Pending Closure|Waiting on Customer|Reopen";

    private static final String FILTER_TICKET_STATUS = "Assigned|Work in Progress|Waiting on Vendor|Monitoring";

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${source}")
    private String source;

    @Value("${page-size}")
    private String ticketsPageSize;

    @Value("#{T(java.util.Arrays).asList('${time.delay.units}')}")
    private List<String> timeDelayUnit;

    @Value("${file.download.path}")
    private String downloadedFilePath;

    @Override
    public String updateTicketWorklog(UpdateWorklogRequest requestBody)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl,
                    Tickets.WORKLOG_UPDATE.getURL() + ACCESS_KEY_URL, accessKey);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(requestBody), Collections.emptyMap(), String.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error occurred while updating ticket worklog", ex);
            throw new RequestViolationException(
                    "Exception was raised in parsing JSON or in parsing request body in runtime");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public List<Ticket> getTicketList(TicketListRequest requestBody, String orderBy, String sortBy)
            throws RequestViolationException, IllegalArgumentException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Tickets.LIST.getURL(), orderBy,
                    ACCESS_KEY_URL, accessKey);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            requestBody.setSortByFieldName(sortBy);
            TicketListResponse ticketListResponse = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(requestBody), Collections.emptyMap(), TicketListResponse.class);
            List<Ticket> tickets = ticketListResponse.getTickets();
            for ( Ticket ticket : tickets ) {
                if ( ticket.getStatus().matches(FILTER_TICKET_STATUS) )
                    ticket.setStatus(ASSIGNED_AND_WIP);
            }
            return tickets;
        } catch (IllegalArgumentException | JsonProcessingException ex) {
            LOGGER.error("Error occurred while fetching ticket list response", ex);
            throw new RequestViolationException(
                    "Exception was raised in parsing JSON or the passed arguments were illegal");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    public TicketConfig getTicketConfig() throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Tickets.CONFIG.getURL(), accessKey,
                    ACCESS_SOURCE, source);
            TicketConfig ticketConfig = ticketConfigCacheService.get(new TicketConfig());
            if ( ticketConfig == null ) {
                ticketConfig = apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                        Collections.emptyMap(), TicketConfig.class);
                ticketConfigCacheService.save(ticketConfig);
            }
            return ticketConfig;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public TicketWidget getTicketWidgetData(Long customerId, Long associateCustomerId, Long projectId,
            FilteredTicketConfig filteredTicketConfig) throws RequestViolationException, IllegalStateException {
        try {
            TicketWidgetRequest requestBody = getTicketRequestBody(customerId, associateCustomerId, projectId,
                    filteredTicketConfig);
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Tickets.WIDGET.getURL(),
                    ACCESS_KEY_URL, accessKey);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            TicketWidget ticketWidget = apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(requestBody), Collections.emptyMap(), TicketWidget.class);
            ticketSeverityDataRestructure(ticketWidget);
            ticketStatusDataRestructure(ticketWidget);
            return ticketWidget;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error fetching Ticket Widget Data from API", ex);
            throw new RequestViolationException("Error fetching Ticket Widget Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    private void ticketStatusDataRestructure(TicketWidget ticketWidget) {
        long assignedAndWorkInProgressSum = ticketWidget.getTicketsCountByStatus().stream()
                .filter(status -> status.getTicketStatus().matches(FILTER_TICKET_STATUS))
                .mapToLong(status -> status.getTicketCount()).sum();
        List<TicketStatusCount> statusCounts = new ArrayList<TicketStatusCount>();
        TicketStatusCount assignedAndWip = new TicketStatusCount();
        assignedAndWip.setTicketStatus(ASSIGNED_AND_WIP);
        assignedAndWip.setTicketCount(assignedAndWorkInProgressSum);
        statusCounts = ticketWidget.getTicketsCountByStatus().stream()
                .filter(statusType -> statusType.getTicketStatus().matches(STATUS_TYPES)).collect(Collectors.toList());
        statusCounts.add(assignedAndWip);
        ticketWidget.setTicketsCountByStatus(statusCounts);
    }

    private void ticketSeverityDataRestructure(TicketWidget ticketWidget) {
        List<TicketSeverityCount> severityCounts = ticketWidget.getTicketsCountBySeverity().stream()
                .filter(severity -> severity.getSeverityType().matches("S[1-3]")).collect(Collectors.toList());
        long sevFourToSevenSum = ticketWidget.getTicketsCountBySeverity().stream()
                .filter(severity -> severity.getSeverityType().matches("S[4-7]"))
                .collect(Collectors.summingLong(TicketSeverityCount::getTicketCount));
        TicketSeverityCount sevFourToSeven = new TicketSeverityCount();
        sevFourToSeven.setSeverityType(SEV_4_TO_7);
        sevFourToSeven.setTicketCount(sevFourToSevenSum);
        severityCounts.add(sevFourToSeven);
        ticketWidget.setTicketsCountBySeverity(severityCounts);
    }

    private TicketWidgetRequest getTicketRequestBody(Long mainCustomerId, Long associateCustomerId, Long projectId,
            FilteredTicketConfig filteredTicketConfig) {
        List<String> ticketTypes = filteredTicketConfig.getTicketTypes();
        List<String> severityTypes = filteredTicketConfig.getSeverityTypes();
        List<String> stateTypes = filteredTicketConfig.getStateTypes();
        TicketWidgetRequest requestBody = new TicketWidgetRequest();
        requestBody.setMainCustomerId(mainCustomerId);
        requestBody.setAssociateCustomerId(associateCustomerId);
        requestBody.setProjectId(projectId);
        requestBody.setTicketTypes(ticketTypes);
        requestBody.setTicketSeverityTypes(severityTypes);
        requestBody.setTicketStates(stateTypes);
        requestBody.setTicketTimeDelayUnits(timeDelayUnit);
        return requestBody;
    }

    private List<String> getTicketStateTypes(TicketConfig ticketConfig) {
        List<String> stateTypes = new ArrayList<String>();
        stateTypes = ticketConfig.getTicketStates().stream()
                .filter(state -> state.getStateName().matches(FILTER_STATE_TYPE)).map(TicketState::getStateName)
                .collect(Collectors.toList());
        return stateTypes;
    }

    private List<String> getTicketSeverityTypes(TicketConfig ticketConfig) {
        List<String> severityTypes = ticketConfig.getTicketSeverities().stream()
                .filter(ticketServerity -> ticketServerity.getSeverityName().matches("[S][1-7]"))
                .map(TicketSeverity::getSeverityName).collect(Collectors.toList());
        return severityTypes;
    }

    private List<String> getTicketTypes(TicketConfig ticketConfig) {
        List<String> ticketTypes = ticketConfig.getTicketCaseTypes().stream().map(TicketCaseType::getCaseType)
                .collect(Collectors.toList());
        return ticketTypes;
    }

    @Override
    public TicketDetail getTicketDetails(String ticketNumber, String orderBy, String pageNumber)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.ELEVEN.getParam(), apiBaseUrl, Tickets.DETAIL.getURL(), orderBy,
                    ACCESS_KEY_URL, accessKey, TICKET_NUMBER_URL, ticketNumber, PAGE_NUMBER_URL, pageNumber,
                    RECORD_PER_PAGE, ticketsPageSize);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), TicketDetail.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
        }
    }

    public List<TicketCaseType> getTicketCaseTypes() throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Tickets.CASE_TYPE.getURL(),
                    ACCESS_KEY_URL, accessKey);
            List<TicketCaseType> ticketCaseTypes = apiClient.performGet(queryString,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    new ParameterizedTypeReference<ArrayList<TicketCaseType>>() {
                    });
            ticketCaseTypes = ticketCaseTypes.stream()
                    .filter(caseType -> caseType.getCaseType().matches(FILTER_CASE_TYPE)).collect(Collectors.toList());
            return ticketCaseTypes;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
        }
    }

    @Override
    public List<TicketCaseSubType> getTicketSubCaseTypes(Long caseTypeId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Tickets.SUB_CASE_TYPE.getURL(),
                    accessKey, TICKET_SUB_TYPE, caseTypeId);
            TicketCaseSubTypeResponse caseSubTypeResponse = ticketCaseSubTypeCacheService
                    .get(new TicketCaseSubTypeResponse(Long.toString(caseTypeId)));
            if ( caseSubTypeResponse == null ) {
                TicketCaseSubTypeResponse ticketCaseSubTypeResponse = new TicketCaseSubTypeResponse();
                ticketCaseSubTypeResponse.setCaseTypeId(Long.toString(caseTypeId));
                ticketCaseSubTypeResponse
                        .setCaseSubTypes(apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                                Collections.emptyMap(), new ParameterizedTypeReference<ArrayList<TicketCaseSubType>>() {
                                }));
                ticketCaseSubTypeCacheService.save(ticketCaseSubTypeResponse);
                return ticketCaseSubTypeResponse.getCaseSubTypes();
            }
            return caseSubTypeResponse.getCaseSubTypes();

        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
        }
    }

    @Override
    public RaiseTicketResponse createTicket(RaiseTicketRequest raiseTicketRequest)
            throws RequestViolationException, ResourceAccessException {
        try {
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Tickets.CREATE.getURL(),
                    ACCESS_KEY_URL, accessKey);
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(raiseTicketRequest), Collections.emptyMap(), RaiseTicketResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error creating new Ticket", ex);
            throw new RequestViolationException("Error creating new ticket");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public Boolean updateTicket(UpdateTicketRequest updateTicketRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Tickets.UPDATE.getURL(),
                    ACCESS_KEY_URL, accessKey);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(updateTicketRequest), Collections.emptyMap(), Boolean.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error occurred while updating ticket status", ex);
            throw new RequestViolationException("Exception was raised in parsing JSON");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public FilteredTicketConfig fetchFilteredTicketConfig() throws ServiceUnavailableException {
        try {
            CachedFilteredTicketConfig cachedFilteredTicketConfig = filteredTicketConfigCacheService
                    .get(new CachedFilteredTicketConfig());
            if ( cachedFilteredTicketConfig == null ) {
                TicketConfig ticketConfig = getTicketConfig();
                FilteredTicketConfig filteredTicketConfig = new FilteredTicketConfig();
                CachedFilteredTicketConfig newFilteredTicketConfig = new CachedFilteredTicketConfig();
                filteredTicketConfig.setSeverityTypes(getTicketSeverityTypes(ticketConfig));
                filteredTicketConfig.setStateTypes(getTicketStateTypes(ticketConfig));
                filteredTicketConfig.setTicketTypes(getTicketTypes(ticketConfig));
                newFilteredTicketConfig.setFilteredTicketConfig(filteredTicketConfig);
                filteredTicketConfigCacheService.save(newFilteredTicketConfig);
                return newFilteredTicketConfig.getFilteredTicketConfig();
            }
            return cachedFilteredTicketConfig.getFilteredTicketConfig();

        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");

        }
    }

    @Override
    public void getFile(Long attachmentId, HttpServletResponse response)
            throws ServiceUnavailableException, IOException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, Tickets.DOWNLOAD_ATTACHMENT.getURL(),
                    attachmentId);
            AttachedFileInformation fileInformation = apiClient.performGet(queryString,
                    MediaType.APPLICATION_JSON_VALUE, Collections.emptyMap(), AttachedFileInformation.class);
            String fileContent = fileInformation.getReceivedfile();
            Gson gson = new Gson();
            byte[] fileContentInBytes = gson.fromJson(fileContent, byte[].class);
            String fullPath = String.format(Param.TWO.getParam(), downloadedFilePath, fileInformation.getFilename());
            response.setContentLength(fileContentInBytes.length);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            String headerKey = CONTENT_DISPOSITION;
            String headerValue = String.format(ATTACHMENT_FILENAME, fileInformation.getFilename());
            response.setHeader(headerKey, headerValue);
            File downloadFile = new File(fullPath);
            FileUtils.writeByteArrayToFile(downloadFile, fileContentInBytes);
            FileInputStream inputStream = new FileInputStream(downloadFile);
            OutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ( (bytesRead = inputStream.read(buffer)) != -1 ) {
                outStream.write(buffer, 0, bytesRead);
            }
            inputStream.close();
            outStream.close();
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Download URI", ex);
        } catch (IOException ex) {
            LOGGER.error("Error occurred while reading from or writing to file", ex);
            throw new ResourceAccessException("Exception occured in reading or writing in the file", ex);
        }
    }
}
