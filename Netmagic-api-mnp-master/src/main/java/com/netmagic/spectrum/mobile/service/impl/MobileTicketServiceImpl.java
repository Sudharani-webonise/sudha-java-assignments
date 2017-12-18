package com.netmagic.spectrum.mobile.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketCaseType;
import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketDetail;
import com.netmagic.spectrum.dto.mobile.ticket.response.MobileTicketListResponse;
import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.UpdateWorklogRequest;
import com.netmagic.spectrum.dto.ticket.response.AttachedFileInformation;
import com.netmagic.spectrum.dto.ticket.response.CachedFilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.FilteredTicketConfig;
import com.netmagic.spectrum.dto.ticket.response.RaiseTicketResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubType;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseSubTypeResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketCaseType;
import com.netmagic.spectrum.dto.ticket.response.TicketConfig;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.mobile.service.MobileTicketService;

@Component
public class MobileTicketServiceImpl implements MobileTicketService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileTicketServiceImpl.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CacheService<TicketCaseSubTypeResponse> ticketCaseSubTypeCacheService;

    @Autowired
    private CacheService<CachedFilteredTicketConfig> filteredTicketConfigCacheService;

    @Autowired
    private CacheService<TicketConfig> ticketConfigCacheService;

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Value("${accessKey}")
    private String accessKey;

    @Value("${source}")
    private String source;

    private static final String ACCESS_KEY_URL = "?accesskey=";

    private static final String TICKET_NUMBER_URL = "&ticketNumber=";

    private static final String RECORD_PER_PAGE = "&recordsPerPage=";

    private static final String PAGE_NUMBER_URL = "&requestPage=";

    private static final String INCIDENT = "Incident";

    private static final String REQUEST = "Request";

    private static final String TICKET_SUB_TYPE = "&ticketCaseTypeId=";

    private static final String ASSIGNED = "Assigned";

    private static final String WORK_IN_PROGRESS = "Work in Progress";

    private static final String WAITING_ON_CUSTOMER = "Waiting on Customer";

    private static final String RESOLVED = "Resolved Pending Closure";

    private static final String REOPEN = "Reopen";

    private static final String ACCESS_SOURCE = "&source=";

    private static final String IOS = "ios";

    @Value("${page-size}")
    private String ticketsPageSize;

    @Override
    public MobileTicketListResponse getMobileTicketList(TicketListRequest ticketListRequest, String orderBy,
            String sortBy) throws RequestViolationException, IllegalArgumentException {
        try {
            String queryString = String.format(Param.FIVE.getParam(), apiBaseUrl, Tickets.LIST.getURL(), orderBy,
                    ACCESS_KEY_URL, accessKey);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
            ticketListRequest.setSortByFieldName(sortBy);
            MobileTicketListResponse ticketListResponse = apiClient.performPost(queryString,
                    MediaType.APPLICATION_JSON_VALUE, mapper.writeValueAsString(ticketListRequest),
                    Collections.emptyMap(), MobileTicketListResponse.class);
            if ( ticketListResponse.getTickets() == null ) {
                throw new IllegalArgumentException("no ticket list found ");
            }
            return ticketListResponse;
        } catch (IllegalArgumentException | JsonProcessingException ex) {
            LOGGER.error("Error occurred while fetching ticket list response", ex);
            throw new RequestViolationException(
                    "Exception was raised in parsing JSON or the passed arguments were illegal");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public MobileTicketDetail getMobileTicketDetails(String ticketNumber, String orderBy, String pageNumber)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.ELEVEN.getParam(), apiBaseUrl, Tickets.DETAIL.getURL(), orderBy,
                    ACCESS_KEY_URL, accessKey, TICKET_NUMBER_URL, ticketNumber, PAGE_NUMBER_URL, pageNumber,
                    RECORD_PER_PAGE, ticketsPageSize);
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), MobileTicketDetail.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
        }
    }

    @Override
    public RaiseTicketResponse createMobileTicket(RaiseTicketRequest raiseTicketRequest)
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
    public MobileTicketCaseType getMobileTicketCaseTypes() throws ServiceUnavailableException {
        try {
            MobileTicketCaseType mobileTicketCaseType = new MobileTicketCaseType();
            String queryString = String.format(Param.FOUR.getParam(), apiBaseUrl, Tickets.CASE_TYPE.getURL(),
                    ACCESS_KEY_URL, accessKey);
            List<TicketCaseType> ticketCaseTypes = apiClient.performGet(queryString,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    new ParameterizedTypeReference<ArrayList<TicketCaseType>>() {
                    });
            mobileTicketCaseType
                    .setMobileTicketCaseType(ticketCaseTypes.stream()
                            .filter(ticketCaseType -> ticketCaseType.getCaseType().equalsIgnoreCase(INCIDENT)
                                    || ticketCaseType.getCaseType().equalsIgnoreCase(REQUEST))
                            .collect(Collectors.toList()));
            return mobileTicketCaseType;

        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
        }
    }

    @Override
    public TicketCaseSubTypeResponse getMobileTicketSubCaseTypes(Long caseTypeId) throws ServiceUnavailableException {
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
                return ticketCaseSubTypeResponse;
            }
            return caseSubTypeResponse;

        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Service URI");
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

    private TicketConfig getTicketConfig() throws ServiceUnavailableException {
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

    private List<String> getTicketStateTypes(TicketConfig ticketConfig) {
        List<String> stateTypes = new ArrayList<String>();
        ticketConfig.getTicketStates().forEach(stateType -> {
            switch (stateType.getStateName()) {
            case ASSIGNED:
            case WORK_IN_PROGRESS:
            case WAITING_ON_CUSTOMER:
            case RESOLVED:
            case REOPEN:
                stateTypes.add(stateType.getStateName());
                break;
            default:
                break;
            }
        });
        return stateTypes;
    }

    private List<String> getTicketSeverityTypes(TicketConfig ticketConfig) {
        List<String> severityTypes = ticketConfig.getTicketSeverities().stream()
                .filter(ticketServerity -> ticketServerity.getSeverityName().matches("[S][1-7]"))
                .map(ticketServerity -> ticketServerity.getSeverityName()).collect(Collectors.toList());
        return severityTypes;
    }

    private List<String> getTicketTypes(TicketConfig ticketConfig) {
        List<String> ticketTypes = new ArrayList<String>();
        ticketConfig.getTicketCaseTypes().forEach(ticketType -> {
            ticketTypes.add(ticketType.getCaseType());
        });
        return ticketTypes;
    }

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
    public boolean updateTicket(UpdateTicketRequest updateTicketRequest)
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
            LOGGER.error("Error in processing POST call");
            throw new ServiceUnavailableException("I/O error on POST request for Ticket Service URI");
        }
    }

    @Override
    public AttachedFileInformation getFileInformation(Long attachmentId, String appType)
            throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl, Tickets.DOWNLOAD_ATTACHMENT.getURL(),
                    attachmentId);
            AttachedFileInformation fileInformation = apiClient.performGet(queryString,
                    MediaType.APPLICATION_JSON_VALUE, Collections.emptyMap(), AttachedFileInformation.class);
            if ( appType.equalsIgnoreCase(IOS) ) {
                Gson gson = new Gson();
                Encoder base64 = Base64.getEncoder();
                String fileContentInBase64 = new String(
                        base64.encode(gson.fromJson(fileInformation.getReceivedfile(), byte[].class)));
                fileInformation.setReceivedfile(fileContentInBase64);
            }
            return fileInformation;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Ticket Download URI", ex);
        }
    }
}
