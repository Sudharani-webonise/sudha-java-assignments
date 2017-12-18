package com.netmagic.spectrum.service.impl;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.commons.url.ServiceLedger;
import com.netmagic.spectrum.dto.service.request.LineItemsRestData;
import com.netmagic.spectrum.dto.service.request.SOFListRestData;
import com.netmagic.spectrum.dto.service.request.WidgetRestData;
import com.netmagic.spectrum.dto.service.response.SOF;
import com.netmagic.spectrum.dto.service.response.ServiceLineItem;
import com.netmagic.spectrum.dto.service.response.ServiceWidget;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.ServiceLedgerService;

/**
 * This class contains methods to fetch/send data related to service ledger APIs
 *
 * @author webonise
 */
@Service
public class ServiceLedgerServiceImpl implements ServiceLedgerService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SugarCRMAuthorization sugarCRMAuthorization;

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLedgerServiceImpl.class);

    private static final String TOKEN_EXPIRED = "Invalid Token/Session Expired";

    @Override
    public List<SOF> getSOFList(SOFListRestData restData) throws RequestViolationException {
        try {
            restData.setToken(sugarCRMAuthorization.fetchServiceToken());
            String SOFList = sugarCRMAuthorization.getSugarServiceResponse(restData, ServiceLedger.SOF_LIST.getURL());
            if ( SOFList.equalsIgnoreCase(TOKEN_EXPIRED) ) {
                restData.setToken(sugarCRMAuthorization.generateServiceToken());
                SOFList = sugarCRMAuthorization.getSugarServiceResponse(restData, ServiceLedger.SOF_LIST.getURL());
            }
            return mapper.readValue(SOFList, new TypeReference<List<SOF>>() {
            });
        } catch (JsonProcessingException | RequestViolationException ex) {
            LOGGER.error("Error parsing SOF LIST ", ex);
            throw new RequestViolationException("Error parsing SOF LIST");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ResourceAccessException("I/O error on GET request for Service Ledger URI");
        } catch (IOException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error while fetching SOF List");
        }
    }

    @Override
    public List<ServiceLineItem> getLineItems(LineItemsRestData restData)
            throws ServiceUnavailableException, RequestViolationException {
        try {
            restData.setToken(sugarCRMAuthorization.fetchServiceToken());
            String SOFList = sugarCRMAuthorization.getSugarServiceResponse(restData,
                    ServiceLedger.LINE_ITEM_LIST.getURL());
            if ( SOFList.equalsIgnoreCase(TOKEN_EXPIRED) ) {
                restData.setToken(sugarCRMAuthorization.generateServiceToken());
                SOFList = sugarCRMAuthorization.getSugarServiceResponse(restData,
                        ServiceLedger.LINE_ITEM_LIST.getURL());
            }
            return mapper.readValue(SOFList, new TypeReference<List<ServiceLineItem>>() {
            });
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error Getting SOF list from service ledger API ", ex);
            throw new RequestViolationException("Error fetching SOF list Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Service Ledger URI");
        }
    }

    @Override
    public ServiceWidget getWidgetData(WidgetRestData restData)
            throws RequestViolationException, CacheServiceException, ServiceUnavailableException {
        try {
            restData.setToken(sugarCRMAuthorization.fetchServiceToken());
            String widgetData = sugarCRMAuthorization.getSugarServiceResponse(restData, ServiceLedger.WIDGET.getURL());
            if ( widgetData.equalsIgnoreCase(TOKEN_EXPIRED) ) {
                restData.setToken(sugarCRMAuthorization.generateServiceToken());
                widgetData = sugarCRMAuthorization.getSugarServiceResponse(restData, ServiceLedger.WIDGET.getURL());
            }
            return mapper.readValue(widgetData, ServiceWidget.class);
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error Getting fetching service widget data from service ledger API ", ex);
            throw new RequestViolationException("Error fetching Service Widget Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Service Ledger URI");
        }
    }

}
