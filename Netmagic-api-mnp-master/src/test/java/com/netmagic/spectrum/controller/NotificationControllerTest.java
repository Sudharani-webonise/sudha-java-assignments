package com.netmagic.spectrum.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.netmagic.spectrum.dto.notification.NotificationUpdateRequest;
import com.netmagic.spectrum.helpers.MockData;
import com.netmagic.spectrum.helpers.ResourceLoader;
import com.netmagic.spectrum.service.NotificationService;

@RunWith(MockitoJUnitRunner.class)
@PrepareForTest(NotificationController.class)
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private NotificationController notificationControllerSpy;

    @Mock
    private NotificationService notificationServiceSpy;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationControllerSpy).build();
    }

    @Test
    public void testFetchNotificationByCustomerId() throws Exception {
        mockMvc.perform(
                get("/api/notification/customer/{customerId}", MockData.MAIN_CUSTOMER_ID.getLong())
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyNotificationDetails() throws Exception {
        mockMvc.perform(put("/api/notification/update")
                .content(ResourceLoader.asJsonString(new NotificationUpdateRequest()))
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk());
    }

}
