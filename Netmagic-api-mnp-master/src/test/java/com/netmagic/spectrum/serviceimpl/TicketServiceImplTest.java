package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.mock.web.MockHttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.dto.ticket.request.RaiseTicketRequest;
import com.netmagic.spectrum.dto.ticket.request.TicketListRequest;
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
import com.netmagic.spectrum.dto.ticket.response.TicketWidget;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.TicketServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ TicketServiceImpl.class })
public class TicketServiceImplTest {

    @InjectMocks
    private TicketServiceImpl ticketServiceSpy;

    private static final String ASCENDING = "ASC";

    private static final String SORT_BY = "ticket Number";

    private static final String TICKET_NUMBER = "TT220160616113618756";

    private static final String PAGE_NUMBER = "1";

    private static final String CASE_SUB_TYPE = "2";

    private static final String CREATE_TICKET_VALID_REQUEST = "requests/tickets/createTicketValidRequest.json";

    private static final String CREATE_TICKET_VALID_RESPONSE = "responses/tickets/createTicketValidResponse.json";

    private static final String CREATE_TICKET_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/createTicketRequestMainCustomerNull.json";

    private static final String CASE_SUB_TYPE_VALID_RESPONSE = "responses/tickets/CaseSubTypeResponse.json";

    private static final String TICKET_LIST_REQUEST = "requests/tickets/TicketListRequest.json";

    private static final String TICKET_LIST_API_RESPONSE = "responses/tickets/ticketListApiResponse.json";

    private static final String TICKET_DETAILS_RESPONSE = "responses/tickets/TicketDetailsResponse.json";

    private static final String TICKET_DETAILS_RESPONSE_INVALID_TICKET_NO = "responses/tickets/ticketDetailsResponseForInvalidTicketNumber.json";

    private static final String UPDATE_TICKET_STATUS_REQUEST = "requests/tickets/ReopenTicketRequest.json";

    private static final String UPDATE_TICKET_STATUS_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/ReopenTicketRequestTicketNumberNull.json";

    private static final String UPDATE_WORKLOG_REQUEST = "requests/tickets/UpdateTicketWorklogRequest.json";

    private static final String UPDATE_WORKLOG_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/UpdateTicketWorklogRequestNullTicketNumber.json";

    private static final String TICKETS_CASE_TYPE = "responses/tickets/CaseTypeValidResponse.json";

    private static final String TICKET_WIDGET_RESPONSE = "responses/tickets/ticketWidgetResponse.json";

    private static final String FILE_DATA_RESPONSE = "responses/tickets/getFileDetail.json";

    private static final String FILE_NAME = "nulltest.jpg";

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private TicketConfig ticketConfigMock;

    @Mock
    private CacheService<TicketConfig> ticketConfigCacheServiceMock;

    @Mock
    private CacheService<TicketCaseSubTypeResponse> ticketCaseSubTypeCacheServiceMock;

    @Mock
    private CacheService<CachedFilteredTicketConfig> filteredTicketConfigCacheServiceMock;

    @Mock
    private ApiClient apiClientMock;

    private MockHttpServletResponse response;

    @Before
    public void setUp() throws Exception {
        Whitebox.setInternalState(ticketServiceSpy, "ticketConfigCacheService", ticketConfigCacheServiceMock);
        Whitebox.setInternalState(ticketServiceSpy, "ticketCaseSubTypeCacheService", ticketCaseSubTypeCacheServiceMock);
        Whitebox.setInternalState(ticketServiceSpy, "filteredTicketConfigCacheService",
                filteredTicketConfigCacheServiceMock);
    }

    @Test
    public void testGetCachedTicketConfigWhenTicketConfigIsNotEmpty() throws Exception {
        Mockito.when(ticketConfigCacheServiceMock.get(Mockito.any(TicketConfig.class))).thenReturn(ticketConfigMock);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketConfig>> any()))
                .thenReturn(ticketConfigMock);
        TicketConfig ticketConfig = Whitebox.invokeMethod(ticketServiceSpy, "getTicketConfig");
        assertNotNull("response of getTicketConfig should not be null", ticketConfig);
    }

    @Test
    public void testGetTicketList() throws Exception {
        TicketListRequest ticketListRequest = ResourceLoader.readAndGetObject(TICKET_LIST_REQUEST,
                TicketListRequest.class);
        TicketListResponse ticketListResponse = ResourceLoader.readAndGetObject(TICKET_LIST_API_RESPONSE,
                TicketListResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketListResponse>> any()))
                .thenReturn(ticketListResponse);
        List<Ticket> tickets = Whitebox.invokeMethod(ticketServiceSpy, "getTicketList", ticketListRequest, ASCENDING,
                SORT_BY);
        assertNotNull("response of getTicketList should not be null", tickets);
        assertEquals("response of getTicketList must be equal to mock data", ticketListResponse.getTickets(), tickets);
    }

    @Test
    public void testGetTicketDetails() throws Exception {
        TicketDetail detail = ResourceLoader.readAndGetObject(TICKET_DETAILS_RESPONSE, TicketDetail.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketDetail>> any())).thenReturn(detail);
        TicketDetail ticketDetail = Whitebox.invokeMethod(ticketServiceSpy, "getTicketDetails", TICKET_NUMBER,
                ASCENDING, PAGE_NUMBER);
        assertNotNull("response of getTicketDetails should not be null", ticketDetail);
        assertEquals("response of getTicketDetails must be equal to mock data", detail, ticketDetail);
    }

    @Test
    public void testGetTicketWidgetData() throws Exception {
        Mockito.when(ticketConfigCacheServiceMock.get(Mockito.any(TicketConfig.class))).thenReturn(ticketConfigMock);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketConfig>> any()))
                .thenReturn(ticketConfigMock);
        FilteredTicketConfig filteredTicketConfig = Whitebox.invokeMethod(ticketServiceSpy,
                "fetchFilteredTicketConfig");
        TicketWidget widgetData = ResourceLoader.readAndGetObject(TICKET_WIDGET_RESPONSE, TicketWidget.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketWidget>> any()))
                .thenReturn(widgetData);
        TicketWidget ticketWidget = Whitebox.invokeMethod(ticketServiceSpy, "getTicketWidgetData", 100L, 100L, 100L,
                filteredTicketConfig);
        assertNotNull("response of getTicketWidgetData should not be null", ticketWidget);
        assertEquals("response of getTicketWidgetData must be equal to mock data", widgetData, ticketWidget);
    }

    @Test
    public void testGetTicketDetailsForInvalidTicketNumber() throws Exception {
        TicketDetail detail = ResourceLoader.readAndGetObject(TICKET_DETAILS_RESPONSE_INVALID_TICKET_NO,
                TicketDetail.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketDetail>> any())).thenReturn(detail);
        TicketDetail ticketDetail = Whitebox.invokeMethod(ticketServiceSpy, "getTicketDetails", TICKET_NUMBER,
                ASCENDING, PAGE_NUMBER);
        assertNull("response of getTicketDetails should not null", ticketDetail.getTicket());
        assertNull("response of getTicketDetails should be null", ticketDetail.getTicketWorklogs());

    }

    @Test
    public void testGetTicketCaseTypes() throws Exception {
        List<TicketCaseType> ticketCaseTypes = ResourceLoader.readAndGetObject(TICKETS_CASE_TYPE,
                new TypeReference<List<TicketCaseType>>() {
                });
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseType>>> any())).thenReturn(ticketCaseTypes);
        List<TicketCaseType> caseTypes = Whitebox.invokeMethod(ticketServiceSpy, "getTicketCaseTypes");
        assertNotNull("response of getTicketCaseTypes should not be null", caseTypes);
        assertEquals("response of getTicketCaseTypes must be equal to mock data", ticketCaseTypes, caseTypes);
    }

    @Test
    public void testGetTicketSubCaseTypesInCache() throws Exception {
        List<TicketCaseSubType> caseSubTypes = ResourceLoader.readAndGetObject(CASE_SUB_TYPE_VALID_RESPONSE,
                new TypeReference<List<TicketCaseSubType>>() {
                });
        TicketCaseSubTypeResponse ticketCaseSubTypeResponse = new TicketCaseSubTypeResponse(CASE_SUB_TYPE);
        ticketCaseSubTypeResponse.setCaseSubTypes(caseSubTypes);
        Mockito.when(ticketCaseSubTypeCacheServiceMock.get(Mockito.any())).thenReturn(ticketCaseSubTypeResponse);
        TicketCaseSubTypeResponse subTypeResponse = Whitebox.invokeMethod(ticketCaseSubTypeCacheServiceMock, "get",
                new TicketCaseSubTypeResponse(CASE_SUB_TYPE));
        Mockito.verify(apiClientMock, times(0)).performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseSubType>>> any());
        List<TicketCaseSubType> ticketCaseSubTypes = Whitebox.invokeMethod(ticketServiceSpy, "getTicketSubCaseTypes",
                2L);
        assertNotNull("response of getTicketSubCaseTypes should not be null", ticketCaseSubTypes);
        assertEquals("response of cached data must be equal to mock data", ticketCaseSubTypeResponse, subTypeResponse);
        assertEquals("response of getTicketSubCaseTypes must be equal to mock data", caseSubTypes, ticketCaseSubTypes);
    }

    @Test
    public void testGetTicketSubCaseTypesInCacheNull() throws Exception {
        List<TicketCaseSubType> caseSubTypes = ResourceLoader.readAndGetObject(CASE_SUB_TYPE_VALID_RESPONSE,
                new TypeReference<List<TicketCaseSubType>>() {
                });
        Mockito.when(ticketCaseSubTypeCacheServiceMock.get(Mockito.any())).thenReturn(null);
        TicketCaseSubTypeResponse subTypeResponse = Whitebox.invokeMethod(ticketCaseSubTypeCacheServiceMock, "get",
                new TicketCaseSubTypeResponse(CASE_SUB_TYPE));
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseSubType>>> any())).thenReturn(caseSubTypes);
        List<TicketCaseSubType> ticketCaseSubTypes = Whitebox.invokeMethod(ticketServiceSpy, "getTicketSubCaseTypes",
                2L);
        assertNotNull("response of getTicketSubCaseTypes should not be null", ticketCaseSubTypes);
        assertEquals("response of cached data must be equal to mock data", null, subTypeResponse);
        assertEquals("response of getTicketSubCaseTypes must be equal to mock data", caseSubTypes, ticketCaseSubTypes);
    }

    @Test
    public void testCreateTicketWithValidRequest() throws Exception {
        RaiseTicketRequest raiseTicketRequest = ResourceLoader.readAndGetObject(CREATE_TICKET_VALID_REQUEST,
                RaiseTicketRequest.class);
        RaiseTicketResponse raiseTicketResponse = ResourceLoader.readAndGetObject(CREATE_TICKET_VALID_RESPONSE,
                RaiseTicketResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<RaiseTicketResponse>> any()))
                .thenReturn(raiseTicketResponse);
        RaiseTicketResponse newRaiseTicketResponse = Whitebox.invokeMethod(ticketServiceSpy, "createTicket",
                raiseTicketRequest);
        assertNotNull("response of createTicket should not be null", newRaiseTicketResponse);
        assertEquals("response of createTicket must be equal to mock data", raiseTicketResponse,
                newRaiseTicketResponse);
    }

    @Test
    public void testCreateTicketWithCustomerIdNull() throws Exception {
        RaiseTicketRequest raiseTicketRequest = ResourceLoader
                .readAndGetObject(CREATE_TICKET_REQUEST_MAIN_CUSTOMER_NULL, RaiseTicketRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<RaiseTicketResponse>> any()))
                .thenReturn(null);
        RaiseTicketResponse newRaiseTicketResponse = Whitebox.invokeMethod(ticketServiceSpy, "createTicket",
                raiseTicketRequest);
        assertNull("response of createTicket should be null", newRaiseTicketResponse);
    }

    @Test
    public void testUpdateTicketStatus() throws Exception {
        UpdateTicketRequest updateTicketRequest = ResourceLoader.readAndGetObject(UPDATE_TICKET_STATUS_REQUEST,
                UpdateTicketRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        Boolean status = Whitebox.invokeMethod(ticketServiceSpy, "updateTicket", updateTicketRequest);
        assertNotNull("response of updateTicket should not be null", status);
        assertEquals("response of updateTicket must be equal to mock data", true, status);
    }

    @Test
    public void testUpdateTicketStatusWithTicketNumberNull() throws Exception {
        UpdateTicketRequest updateTicketRequest = ResourceLoader
                .readAndGetObject(UPDATE_TICKET_STATUS_REQUEST_MAIN_CUSTOMER_NULL, UpdateTicketRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(false);
        Boolean status = Whitebox.invokeMethod(ticketServiceSpy, "updateTicket", updateTicketRequest);
        assertNotNull("response of updateTicket should not be null", status);
        assertEquals("response of updateTicket must be equal to mock data", false, status);
    }

    @Test
    public void testUpdateTicketWorklog() throws Exception {
        UpdateWorklogRequest updateWorklogRequest = ResourceLoader.readAndGetObject(UPDATE_WORKLOG_REQUEST,
                UpdateWorklogRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.SUCCESS.getString());
        String updateWorklogResponse = Whitebox.invokeMethod(ticketServiceSpy, "updateTicketWorklog",
                updateWorklogRequest);
        assertNotNull("response of updateTicketWorklog should not be null", updateWorklogResponse);
        assertEquals("response of updateTicketWorklog must be equal to mock data", MockData.SUCCESS.getString(),
                updateWorklogResponse);
    }

    @Test
    public void testUpdateTicketWorklogWithTicketNumberNull() throws Exception {
        UpdateWorklogRequest updateWorklogRequest = ResourceLoader
                .readAndGetObject(UPDATE_WORKLOG_REQUEST_MAIN_CUSTOMER_NULL, UpdateWorklogRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.FAILURE.getString());
        String updateWorklogResponse = Whitebox.invokeMethod(ticketServiceSpy, "updateTicketWorklog",
                updateWorklogRequest);
        assertNotNull("response of updateTicketWorklog should not be null", updateWorklogResponse);
        assertEquals("response of updateTicketWorklog must be equal to mock data", MockData.FAILURE.getString(),
                updateWorklogResponse);
    }

    @Test
    public void testGetFile() throws Exception {
        response = new MockHttpServletResponse();
        AttachedFileInformation fileInformation = ResourceLoader.readAndGetObject(FILE_DATA_RESPONSE,
                AttachedFileInformation.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AttachedFileInformation>> any()))
                .thenReturn(fileInformation);
        ticketServiceSpy.getFile(MockData.ATTACHMENT_ID.getLong(), response);
    }

    @AfterClass
    public static void deleteFile() throws IOException {
        Path fileToDeletePath = Paths.get(FILE_NAME);
        Files.delete(fileToDeletePath);
    }
}
