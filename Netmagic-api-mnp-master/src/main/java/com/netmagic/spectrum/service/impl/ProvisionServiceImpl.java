package com.netmagic.spectrum.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.url.Provision;
import com.netmagic.spectrum.dto.provision.LineItemDetails;
import com.netmagic.spectrum.dto.provision.SofDetails;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.ProvisionService;

/**
 * This class contains methods to fetch data related to Provision APIs
 *
 * @author webonise
 */
@Service
public class ProvisionServiceImpl implements ProvisionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProvisionServiceImpl.class);

    private static final String BILL_TO_CUSTOMER = "?billToCustomer=";

    private static final String SUPPORT_TO_CUSTOMER = "&supportToCustomer=";

    private static final String PROJECT_NAME = "&projectName=";

    private static final String SOF_NUMBER = "?sofNumber=";

    private static final String LINE_ITEM_NUMBER = "&lineItemNumber=";

    @Value("${apiBaseUrl}")
    private String apiBaseUrl;

    @Autowired
    private ApiClient apiClient;

    @Override
    public List<SofDetails> getProvisionSofData(String billToCustomerId, String supportToCustomerId, String projectName)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.EIGHT.getParam(), apiBaseUrl, Provision.SOF.getURL(), BILL_TO_CUSTOMER,
                    billToCustomerId, SUPPORT_TO_CUSTOMER, supportToCustomerId, PROJECT_NAME, projectName);
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    new ParameterizedTypeReference<ArrayList<SofDetails>>() {
                    });
        } catch (RequestViolationException ex) {
            LOGGER.error("Error in fetching Provisioning SOF data for shopping Cart ", ex);
            throw new RequestViolationException("Error in fetching provisioning SOF data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for provisioning SOF data URI");
        }
    }

    @Override
    public LineItemDetails getProvisionLineItemData(Long sofNumber, Long lineItemNumber)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String url = String.format(Param.SIX.getParam(), apiBaseUrl, Provision.LINE_ITEM.getURL(), SOF_NUMBER,
                    sofNumber, LINE_ITEM_NUMBER, lineItemNumber);
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE, Collections.emptyMap(),
                    LineItemDetails.class);
        } catch (RequestViolationException ex) {
            LOGGER.error("Error in fetching Provisioning Line Item data for shopping Cart ", ex);
            throw new RequestViolationException("Error in fetching provisioning Line Item data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for provisioning Line Item data URI");
        }
    }
}
