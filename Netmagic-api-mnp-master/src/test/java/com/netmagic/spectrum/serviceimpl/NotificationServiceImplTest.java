package com.netmagic.spectrum.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.dto.notification.NotificationDetails;
import com.netmagic.spectrum.dto.notification.NotificationUpdateRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.impl.NotificationServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest({ NotificationServiceImpl.class })
public class NotificationServiceImplTest {

    private static final String NOTIFICATION_DETAILS_BY_CUSTOMER_ID = "responses/notification/NotificationDetailsByCustomerId.json";

    private static final String NOTIFICATION_UPDATE_REQUEST = "responses/notification/NotificationUpdateRequest.json";

    @InjectMocks
    private NotificationServiceImpl notificationSpy;

    @Mock
    private ApiClient apiClientMock;

    @Mock
    private ObjectMapper mapperMock;

    @Test
    public void testGetNotificationByValidCustomerId() throws Exception {
        NotificationDetails NotificationDetailsResponse = ResourceLoader
                .readAndGetObject(NOTIFICATION_DETAILS_BY_CUSTOMER_ID, NotificationDetails.class);
        Mockito.when(apiClientMock.performGet(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<NotificationDetails>> any()))
                .thenReturn(NotificationDetailsResponse);
        NotificationDetails NotificationDetailsResponseActual = Whitebox.invokeMethod(notificationSpy,
                "getNotificationDetailsByCustomerId", MockData.MAIN_CUSTOMER_ID.getLong());
        assertNotNull("response of getNotificationDetails should not be null", NotificationDetailsResponseActual);
        assertEquals("response of getNotificationDetails must be equal to mock data", NotificationDetailsResponse,
                NotificationDetailsResponseActual);
    }

    @Test
    public void testAddRoleDetailsWithValidRequest() throws Exception {
        NotificationUpdateRequest NotificationUpdateRequest = ResourceLoader
                .readAndGetObject(NOTIFICATION_UPDATE_REQUEST, NotificationUpdateRequest.class);
        String status = "success";
        Mockito.when(apiClientMock.performPut(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
                Mockito.anyMapOf(String.class, String.class), Mockito.<Class<String>> any())).thenReturn(status);
        String actulStatus = Whitebox.invokeMethod(notificationSpy, "modifyNotificationDetails",
                NotificationUpdateRequest);
        assertNotNull("response of modifyNotificationDetails should not be null", actulStatus);
        assertEquals("success response after updating the data", status, actulStatus);
    }
}
