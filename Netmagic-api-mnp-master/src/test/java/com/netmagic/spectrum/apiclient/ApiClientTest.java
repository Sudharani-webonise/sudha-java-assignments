package com.netmagic.spectrum.apiclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.netmagic.spectrum.dto.ticket.response.TicketListResponse;
import com.netmagic.spectrum.dto.ticket.response.TicketWorklog;

@RunWith(MockitoJUnitRunner.class)
public class ApiClientTest {

    @InjectMocks
    private ApiClient apiClient;

    @Mock
    private RestTemplate templateMock;

    @Mock
    private ResponseEntity<TicketListResponse> ticketListResponseEntityMock;

    @Mock
    private ResponseEntity<List<TicketWorklog>> worklogListResponseEntityMock;

    private static final String MOCK_STRING = "mock string";

    private Map<String, String> header;

    private TicketListResponse ticketListResponse;

    private List<TicketWorklog> worklogs;

    @Before
    public void setUp() {
        header = new HashMap<String, String>();
        ticketListResponse = new TicketListResponse();
        worklogs = new ArrayList<TicketWorklog>();
    }

    @Test
    public void testGetHttpRequestHeaderToPerformGet() {
        HttpEntity<String> httpEntity = apiClient.getHttpRequestHeader(RequestMethod.GET.name(),
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, MOCK_STRING, Collections.emptyMap());
        assertNotNull(httpEntity);
        assertEquals(httpEntity.getHeaders().size(), 0);
    }

    @Test
    public void testGetSSOHttpRequestHeaderToPerformGet() {
        HttpEntity<String> httpEntity = apiClient.getSSOHttpRequestHeader(RequestMethod.GET.name(),
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_JSON_VALUE, MOCK_STRING,
                Collections.emptyMap());
        assertNotNull(httpEntity);
        assertEquals(httpEntity.getHeaders().size(), 2);
    }

    @Test
    public void testGetHttpRequestHeaderToPerformPut() {
        HttpEntity<String> httpEntity = apiClient.getHttpRequestHeader(RequestMethod.PUT.name(),
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, MOCK_STRING, Collections.emptyMap());
        assertNotNull(httpEntity);
        assertEquals(httpEntity.getHeaders().size(), 1);
    }

    @Test
    public void testGetHttpRequestHeaderToPerformPutWithHeader() {
        header.put(MOCK_STRING, MOCK_STRING);
        HttpEntity<String> httpEntity = apiClient.getHttpRequestHeader(RequestMethod.PUT.name(),
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, MOCK_STRING, header);
        assertNotNull(httpEntity);
        assertEquals(httpEntity.getHeaders().size(), 2);
    }

    @Test
    public void testPerformGet() throws Exception {
        mockRestTemplateBody();
        TicketListResponse response = apiClient.performGet(MOCK_STRING, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                header, TicketListResponse.class);
        assertEquals(ticketListResponse, response);
        verify();
    }

    @Test
    public void testPerformGetAcceptHeadder() throws Exception {
        mockRestTemplateBody();
        TicketListResponse response = apiClient.performGet(MOCK_STRING, MediaType.APPLICATION_FORM_URLENCODED_VALUE, "",
                header, TicketListResponse.class);
        assertEquals(ticketListResponse, response);
        verify();
    }

    @Test
    public void testPerformGetWithParameterizedTypeRef() throws Exception {
        mockRestTemplateBodyParameterizedTypeRef();
        List<TicketWorklog> worklogsResponse = apiClient.performGet(MOCK_STRING,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                new ParameterizedTypeReference<List<TicketWorklog>>() {
                });
        assertEquals(worklogs, worklogsResponse);
        verifyWithParameterizedTypeRef();
    }

    @Test
    public void testPerformPost() throws Exception {
        mockRestTemplateBody();
        TicketListResponse response = apiClient.performPost(MOCK_STRING, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MOCK_STRING, header, TicketListResponse.class);
        assertEquals(ticketListResponse, response);
        verify();
    }

    @Test
    public void testPerformPostWithParameterizedTypeRef() throws Exception {
        mockRestTemplateBodyParameterizedTypeRef();
        List<TicketWorklog> worklogsResponse = apiClient.performPost(MOCK_STRING,
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, MOCK_STRING, Collections.emptyMap(),
                new ParameterizedTypeReference<List<TicketWorklog>>() {
                });
        assertEquals(worklogs, worklogsResponse);
        verifyWithParameterizedTypeRef();
    }

    @Test
    public void testPerformDelete() throws Exception {
        mockRestTemplateBody();
        TicketListResponse response = apiClient.performDelete(MOCK_STRING, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MOCK_STRING, header, TicketListResponse.class);
        assertEquals(ticketListResponse, response);
        verify();
    }

    @Test
    public void testPerformPut() throws Exception {
        mockRestTemplateBody();
        TicketListResponse response = apiClient.performPut(MOCK_STRING, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                MOCK_STRING, header, TicketListResponse.class);
        assertEquals(ticketListResponse, response);
        verify();
    }

    private void mockRestTemplateBody() {
        Mockito.when(templateMock.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(), Mockito.<Class<TicketListResponse>> any()))
                .thenReturn(ticketListResponseEntityMock);
        Mockito.when(ticketListResponseEntityMock.getBody()).thenReturn(ticketListResponse);
    }

    private void mockRestTemplateBodyParameterizedTypeRef() {
        Mockito.when(templateMock.exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(), Mockito.<ParameterizedTypeReference<List<TicketWorklog>>> any()))
                .thenReturn(worklogListResponseEntityMock);
        Mockito.when(worklogListResponseEntityMock.getBody()).thenReturn(worklogs);
    }

    private void verify() {
        Mockito.verify(ticketListResponseEntityMock, Mockito.times(1)).getBody();
        Mockito.verify(templateMock, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(), Mockito.<Class<TicketListResponse>> any());
    }

    private void verifyWithParameterizedTypeRef() {
        Mockito.verify(worklogListResponseEntityMock, Mockito.times(1)).getBody();
        Mockito.verify(templateMock, Mockito.times(1)).exchange(Mockito.anyString(), Mockito.<HttpMethod> any(),
                Mockito.<HttpEntity<?>> any(), Mockito.<ParameterizedTypeReference<List<TicketWorklog>>> any());
    }

}
