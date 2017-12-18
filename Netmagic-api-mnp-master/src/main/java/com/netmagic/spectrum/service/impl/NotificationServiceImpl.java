package com.netmagic.spectrum.service.impl;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Notification;
import com.netmagic.spectrum.dto.notification.NotificationDetails;
import com.netmagic.spectrum.dto.notification.NotificationUpdateRequest;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public NotificationDetails getNotificationDetailsByCustomerId(Long customerId) throws ServiceUnavailableException {
        try {
            String queryString = String.format(Param.THREE.getParam(), apiBaseUrl,
                    Notification.VIEW_NOTIFICATION.getURL(), customerId);
            NotificationDetails notificationDetails = apiClient.performGet(queryString,
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(), NotificationDetails.class);
            return notificationDetails;
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Notification Service URI");
        }
    }

    @Override
    public String modifyNotificationDetails(NotificationUpdateRequest notificationDetails)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), apiBaseUrl,
                    Notification.EDIT_NOTIFICATION.getURL());
            String status = apiClient.performPut(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(notificationDetails), Collections.emptyMap(), String.class);
            LOGGER.info("Notification update status {} ", status);
            return status;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Permission denied to add the role details ", ex);
            throw new RequestViolationException("Permission denied to add the role details ");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing PUT call", ex);
            throw new ServiceUnavailableException("I/O error on PUT request for Notification Service URI");
        }
    }
}