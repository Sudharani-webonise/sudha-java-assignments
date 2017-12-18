package com.netmagic.spectrum.mobile.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;

import java.util.List;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.cache.service.CacheService;
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
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.mobile.service.impl.MobileTicketServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ MobileTicketServiceImpl.class })
public class MobileTicketServiceImplTest {

    @InjectMocks
    private MobileTicketServiceImpl mobileticketServiceSpy;

    private static final String TICKET_LIST_REQUEST = "requests/tickets/TicketListRequest.json";

    private static final String TICKET_LIST_API_RESPONSE = "responses/tickets/mobileTicketListApiResponse.json";

    private static final String TICKET_DETAILS_RESPONSE = "responses/tickets/mobileTicketDetailsResponse.json";

    private static final String TICKET_DETAILS_RESPONSE_INVALID_TICKET_NO = "responses/tickets/ticketDetailsResponseForInvalidTicketNumber.json";

    private static final String CREATE_TICKET_VALID_REQUEST = "requests/tickets/createTicketValidRequest.json";

    private static final String CREATE_TICKET_VALID_RESPONSE = "responses/tickets/createTicketValidResponse.json";

    private static final String CREATE_TICKET_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/createTicketRequestMainCustomerNull.json";

    private static final String CASE_SUB_TYPE_VALID_RESPONSE = "responses/tickets/MobileCaseSubTypeResponse.json";

    private static final String TICKETS_CASE_TYPE = "responses/tickets/MobileCaseTypeValidResponse.json";

    private static final String UPDATE_WORKLOG_REQUEST = "requests/tickets/UpdateTicketWorklogRequest.json";

    private static final String UPDATE_WORKLOG_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/UpdateTicketWorklogRequestNullTicketNumber.json";

    private static final String UPDATE_TICKET_STATUS_REQUEST = "requests/tickets/ReopenTicketRequest.json";

    private static final String UPDATE_TICKET_STATUS_REQUEST_MAIN_CUSTOMER_NULL = "requests/tickets/ReopenTicketRequestTicketNumberNull.json";

    private static final String FILE_INFO_RESPONSE = "responses/tickets/FileInfoResponse.json";

    @Mock
    private CacheService<CachedFilteredTicketConfig> filteredTicketConfigCacheServiceMock;

    @Mock
    private CacheService<TicketCaseSubTypeResponse> ticketCaseSubTypeCacheServiceMock;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Mock
    private CacheService<TicketConfig> ticketConfigCacheServiceMock;

    @Mock
    private TicketConfig ticketConfigMock;

    @Before
    public void setUp() throws Exception {
        Whitebox.setInternalState(mobileticketServiceSpy, "ticketConfigCacheService", ticketConfigCacheServiceMock);
        Whitebox.setInternalState(mobileticketServiceSpy, "ticketCaseSubTypeCacheService",
                ticketCaseSubTypeCacheServiceMock);
        Whitebox.setInternalState(mobileticketServiceSpy, "filteredTicketConfigCacheService",
                filteredTicketConfigCacheServiceMock);
    }

    @Test
    public void testGetTicketList() throws Exception {
        TicketListRequest ticketListRequest = ResourceLoader.readAndGetObject(TICKET_LIST_REQUEST,
                TicketListRequest.class);
        MobileTicketListResponse ticketListResponse = ResourceLoader.readAndGetObject(TICKET_LIST_API_RESPONSE,
                MobileTicketListResponse.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<MobileTicketListResponse>> any()))
                .thenReturn(ticketListResponse);
        MobileTicketListResponse tickets = Whitebox.invokeMethod(mobileticketServiceSpy, "getMobileTicketList",
                ticketListRequest, MockData.ASCENDING.getString(), MockData.SORT_BY.getString());
        assertNotNull("response of getMobileTicketList should not be null", tickets);
        assertEquals("response of getMobileTicketList must be equal to mock data", ticketListResponse, tickets);
    }

    @Test
    public void testGetTicketDetails() throws Exception {
        MobileTicketDetail detail = ResourceLoader.readAndGetObject(TICKET_DETAILS_RESPONSE, MobileTicketDetail.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<MobileTicketDetail>> any()))
                .thenReturn(detail);
        MobileTicketDetail ticketDetail = Whitebox.invokeMethod(mobileticketServiceSpy, "getMobileTicketDetails",
                MockData.TICKET_NUMBER.getString(), MockData.ASCENDING.getString(), MockData.PAGE_NUMBER.getString());
        assertNotNull("response of getMobileTicketDetails should not be null", ticketDetail);
        assertEquals("response of getMobileTicketDetails must be equal to mock data", detail, ticketDetail);
    }

    @Test
    public void testGetTicketDetailsForInvalidTicketNumber() throws Exception {
        MobileTicketDetail detail = ResourceLoader.readAndGetObject(TICKET_DETAILS_RESPONSE_INVALID_TICKET_NO,
                MobileTicketDetail.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<MobileTicketDetail>> any()))
                .thenReturn(detail);
        MobileTicketDetail ticketDetail = Whitebox.invokeMethod(mobileticketServiceSpy, "getMobileTicketDetails",
                MockData.TICKET_NUMBER.getString(), MockData.ASCENDING.getString(), MockData.PAGE_NUMBER.getString());
        assertNull("response of getMobileTicketDetails should not null", ticketDetail.getTicket());
        assertNull("response of getMobileTicketDetails should be null", ticketDetail.getTicketWorklogs());
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
        RaiseTicketResponse newRaiseTicketResponse = Whitebox.invokeMethod(mobileticketServiceSpy, "createMobileTicket",
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
        RaiseTicketResponse newRaiseTicketResponse = Whitebox.invokeMethod(mobileticketServiceSpy, "createMobileTicket",
                raiseTicketRequest);
        assertNull("response of createTicket should be null", newRaiseTicketResponse);
    }

    @Test
    public void testGetTicketCaseTypes() throws Exception {
        MobileTicketCaseType ticketCaseTypes = ResourceLoader.readAndGetObject(TICKETS_CASE_TYPE,
                MobileTicketCaseType.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseType>>> any()))
                .thenReturn(ticketCaseTypes.getMobileTicketCaseType());
        MobileTicketCaseType caseTypes = Whitebox.invokeMethod(mobileticketServiceSpy, "getMobileTicketCaseTypes");
        assertNotNull("response of getTicketCaseTypes should not be null", caseTypes);
        assertEquals("response of getTicketCaseTypes must be equal to mock data",
                ticketCaseTypes.getMobileTicketCaseType(), caseTypes.getMobileTicketCaseType());
    }

    @Test
    public void testGetTicketSubCaseTypesInCache() throws Exception {
        TicketCaseSubTypeResponse caseSubTypes = ResourceLoader.readAndGetObject(CASE_SUB_TYPE_VALID_RESPONSE,
                TicketCaseSubTypeResponse.class);
        TicketCaseSubTypeResponse ticketCaseSubTypeResponse = new TicketCaseSubTypeResponse(
                MockData.CASE_SUB_TYPE.getString());
        ticketCaseSubTypeResponse.setCaseSubTypes(caseSubTypes.getCaseSubTypes());
        Mockito.when(ticketCaseSubTypeCacheServiceMock.get(Mockito.any())).thenReturn(ticketCaseSubTypeResponse);
        TicketCaseSubTypeResponse subTypeResponse = Whitebox.invokeMethod(ticketCaseSubTypeCacheServiceMock, "get",
                new TicketCaseSubTypeResponse(MockData.CASE_SUB_TYPE.getString()));
        Mockito.verify(apiClientMock, times(0)).performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseSubType>>> any());
        TicketCaseSubTypeResponse ticketCaseSubTypes = Whitebox.invokeMethod(mobileticketServiceSpy,
                "getMobileTicketSubCaseTypes", 2L);
        assertNotNull("response of getTicketSubCaseTypes should not be null", ticketCaseSubTypes);
        assertEquals("response of cached data must be equal to mock data", ticketCaseSubTypeResponse, subTypeResponse);
        assertEquals("response of getTicketSubCaseTypes must be equal to mock data", caseSubTypes.getCaseSubTypes(),
                ticketCaseSubTypes.getCaseSubTypes());
    }

    @Test
    public void testGetTicketSubCaseTypesInCacheNull() throws Exception {
        TicketCaseSubTypeResponse caseSubTypes = ResourceLoader.readAndGetObject(CASE_SUB_TYPE_VALID_RESPONSE,
                TicketCaseSubTypeResponse.class);
        Mockito.when(ticketCaseSubTypeCacheServiceMock.get(Mockito.any())).thenReturn(null);
        TicketCaseSubTypeResponse subTypeResponse = Whitebox.invokeMethod(ticketCaseSubTypeCacheServiceMock, "get",
                new TicketCaseSubTypeResponse(MockData.CASE_SUB_TYPE.getString()));
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class),
                Mockito.<ParameterizedTypeReference<List<TicketCaseSubType>>> any()))
                .thenReturn(caseSubTypes.getCaseSubTypes());
        TicketCaseSubTypeResponse ticketCaseSubTypes = Whitebox.invokeMethod(mobileticketServiceSpy,
                "getMobileTicketSubCaseTypes", 2L);
        assertNotNull("response of getTicketSubCaseTypes should not be null", ticketCaseSubTypes);
        assertEquals("response of cached data must be equal to mock data", null, subTypeResponse);
        assertEquals("response of getTicketSubCaseTypes must be equal to mock data", caseSubTypes.getCaseSubTypes(),
                ticketCaseSubTypes.getCaseSubTypes());
    }

    @Test
    public void testGetCachedTicketConfigWhenTicketConfigIsNotEmpty() throws Exception {
        Mockito.when(ticketConfigCacheServiceMock.get(Mockito.any(TicketConfig.class))).thenReturn(ticketConfigMock);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketConfig>> any()))
                .thenReturn(ticketConfigMock);
        TicketConfig ticketConfig = Whitebox.invokeMethod(mobileticketServiceSpy, "getTicketConfig");
        assertNotNull("response of getTicketConfig should not be null", ticketConfig);
    }

    @Test
    public void testUpdateTicketWorklog() throws Exception {
        UpdateWorklogRequest updateWorklogRequest = ResourceLoader.readAndGetObject(UPDATE_WORKLOG_REQUEST,
                UpdateWorklogRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any()))
                .thenReturn(MockData.SUCCESS.getString());
        String updateWorklogResponse = Whitebox.invokeMethod(mobileticketServiceSpy, "updateTicketWorklog",
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
        String updateWorklogResponse = Whitebox.invokeMethod(mobileticketServiceSpy, "updateTicketWorklog",
                updateWorklogRequest);
        assertNotNull("response of updateTicketWorklog should not be null", updateWorklogResponse);
        assertEquals("response of updateTicketWorklog must be equal to mock data", MockData.FAILURE.getString(),
                updateWorklogResponse);
    }

    @Test
    public void testUpdateTicketStatus() throws Exception {
        UpdateTicketRequest updateTicketRequest = ResourceLoader.readAndGetObject(UPDATE_TICKET_STATUS_REQUEST,
                UpdateTicketRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(true);
        Boolean status = Whitebox.invokeMethod(mobileticketServiceSpy, "updateTicket", updateTicketRequest);
        assertNotNull("response of updateTicket should not be null", status);
        assertEquals("response of updateTicket must be equal to mock data", true, status);
    }

    @Test
    public void testUpdateTicketStatusWithTicketNumberNull() throws Exception {
        UpdateTicketRequest updateTicketRequest = ResourceLoader
                .readAndGetObject(UPDATE_TICKET_STATUS_REQUEST_MAIN_CUSTOMER_NULL, UpdateTicketRequest.class);
        Mockito.when(apiClientMock.performPost(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<Boolean>> any())).thenReturn(false);
        Boolean status = Whitebox.invokeMethod(mobileticketServiceSpy, "updateTicket", updateTicketRequest);
        assertNotNull("response of updateTicket should not be null", status);
        assertEquals("response of updateTicket must be equal to mock data", false, status);
    }

    @Test
    public void testGetFileInformation() throws Exception {
        AttachedFileInformation fileInfo = ResourceLoader.readAndGetObject(FILE_INFO_RESPONSE,
                AttachedFileInformation.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<AttachedFileInformation>> any()))
                .thenReturn(fileInfo);
        AttachedFileInformation fileInfoActual = Whitebox.invokeMethod(mobileticketServiceSpy, "getFileInformation",
                MockData.ATTACHMENT_ID.getLong(), MockData.APPTYPE.getString());
        assertNotNull("response of getTicketDetails should not be null", fileInfoActual);
        assertEquals("response of getTicketDetails must be equal to mock data", fileInfo, fileInfoActual);
    }

    @Test
    public void testFetchFilteredTicketConfig() throws Exception {
        Mockito.when(ticketConfigCacheServiceMock.get(Mockito.any(TicketConfig.class))).thenReturn(ticketConfigMock);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<TicketConfig>> any()))
                .thenReturn(ticketConfigMock);
        FilteredTicketConfig filteredTicketConfig = Whitebox.invokeMethod(mobileticketServiceSpy,
                "fetchFilteredTicketConfig");
        assertNotNull("response of fetchFilteredTicketConfig should not be null", filteredTicketConfig);
    }
}
