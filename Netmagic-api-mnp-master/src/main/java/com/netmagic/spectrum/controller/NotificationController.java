package com.netmagic.spectrum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.notification.NotificationDetails;
import com.netmagic.spectrum.dto.notification.NotificationUpdateRequest;
import com.netmagic.spectrum.service.NotificationService;

@RequestMapping(value = "/api/notification")
@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public NotificationDetails fetchNotificationByCustomerId(
            @PathVariable("customerId") Long customerId) {
        return notificationService.getNotificationDetailsByCustomerId(customerId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String modifyNotificationDetails(
            @RequestBody NotificationUpdateRequest notificationDetails) {
        return notificationService.modifyNotificationDetails(notificationDetails);
    }
}