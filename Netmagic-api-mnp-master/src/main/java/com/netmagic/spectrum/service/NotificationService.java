package com.netmagic.spectrum.service;

import com.netmagic.spectrum.dto.notification.NotificationDetails;
import com.netmagic.spectrum.dto.notification.NotificationUpdateRequest;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface NotificationService {

    /**
     * This method is used to get the notification details by customer id
     *
     * @param customerId
     * @return NotificationDetails
     * @throws ServiceUnavailableException
     */
    NotificationDetails getNotificationDetailsByCustomerId(Long customerId)
            throws ServiceUnavailableException;

    /**
     * This method is used to modify the notification details by
     * notificationDetails request
     *
     * @param notificationDetails
     * @return String
     * @throws ServiceUnavailableException
     *             , RequestViolationException
     */
    String modifyNotificationDetails(NotificationUpdateRequest notificationDetails)
            throws RequestViolationException, ServiceUnavailableException;
}
