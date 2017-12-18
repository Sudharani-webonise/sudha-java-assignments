package com.netmagic.spectrum.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.netmagic.spectrum.dto.service.request.LineItemsRestData;
import com.netmagic.spectrum.dto.service.request.SOFListRestData;
import com.netmagic.spectrum.dto.service.request.WidgetRestData;
import com.netmagic.spectrum.dto.service.response.SOF;
import com.netmagic.spectrum.dto.service.response.ServiceLineItem;
import com.netmagic.spectrum.dto.service.response.ServiceWidget;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface ServiceLedgerService {

    /**
     * This method returns the list of all available SOF services.
     *
     * @param restData
     * @return List of SOF
     * @throws ServiceUnavailableException
     *             , RequestViolationException
     */
    @PreAuthorize("hasAuthority('SER_LIST')")
    List<SOF> getSOFList(SOFListRestData restData) throws RequestViolationException, RequestViolationException;

    /**
     * This method returns the service line items for a particular SOF.
     *
     * @param restData
     * @return List of Service line items
     * @throws RequestViolationException
     *             , ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('SER_LIST')")
    List<ServiceLineItem> getLineItems(LineItemsRestData restData)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method gives the widget data for service ledger module in dashboard.
     *
     * @param restData
     * @return ServiceWidget
     * @throws RequestViolationException
     *             , ServiceUnavailableException, CacheServiceException
     */
    @PreAuthorize("hasAuthority('SER_WDGT')")
    ServiceWidget getWidgetData(WidgetRestData restData)
            throws RequestViolationException, CacheServiceException, ServiceUnavailableException;
}