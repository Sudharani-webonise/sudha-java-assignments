package com.netmagic.spectrum.service.impl;

import java.io.IOException;

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
import com.netmagic.spectrum.apiclient.OustandingApiAuthorization;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.Status;
import com.netmagic.spectrum.commons.url.Outstanding;
import com.netmagic.spectrum.dto.outstanding.request.CartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountRequestWrapper;
import com.netmagic.spectrum.dto.outstanding.request.OutstandingRequest;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequest;
import com.netmagic.spectrum.dto.outstanding.response.CartPaymentResponse;
import com.netmagic.spectrum.dto.outstanding.response.OnAccountPaymentResponseWrapper;
import com.netmagic.spectrum.dto.outstanding.response.OnlineCreditResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingBusinessUnit;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingListResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingWidgetResponse;
import com.netmagic.spectrum.dto.outstanding.response.PaymentResponse;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.OutstandingService;
import com.netmagic.spectrum.service.PaymentService;

@Service
public class OutstandingServiceImpl implements OutstandingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutstandingServiceImpl.class);

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private OustandingApiAuthorization outstandingApiAuthorization;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    PaymentService paymentService;

    @Value("${outstanding-api-base-url}")
    private String outstandingBaseUrl;

    @Override
    public OutstandingWidgetResponse getWidgetData(OutstandingRequest outstandingRequest)
            throws ServiceUnavailableException, RequestViolationException {
        try {
            String url = String.format(Param.TWO.getParam(), outstandingBaseUrl, Outstanding.WIDGET_API.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(outstandingRequest), outstandingApiAuthorization.getAuthorization(),
                    OutstandingWidgetResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Oustanding Widget Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Oustanding Widget Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Outstanding Service URI");
        }
    }

    @Override
    public OutstandingListResponse getListData(OutstandingRequest outstandingRequest)
            throws ServiceUnavailableException, RequestViolationException {
        try {
            String url = String.format(Param.TWO.getParam(), outstandingBaseUrl, Outstanding.LIST_API.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(outstandingRequest), outstandingApiAuthorization.getAuthorization(),
                    OutstandingListResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Oustanding List Data", ex);
            throw new RequestViolationException("Error in json processing while Getting Oustanding List Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public boolean sendPaymentRequest(PaymentRequest invoicePaymentRequest, String merchantRefNumber,
            String paymentSource) throws ServiceUnavailableException, DuplicateDataException, IOException {
        try {
            String url = String.format(Param.TWO.getParam(), outstandingBaseUrl,
                    Outstanding.INVOICE_PAYMENT_API.getURL());
            PaymentResponse paymentResponse = apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(invoicePaymentRequest), outstandingApiAuthorization.getAuthorization(),
                    PaymentResponse.class);
            if ( paymentResponse.getPaymentDetails().getPaymentStatus().equalsIgnoreCase(Status.SUCCESS.getStatus()) ) {
                return paymentService.saveInvoiceDetails(invoicePaymentRequest.getPaymentRequestDetails(),
                        merchantRefNumber, paymentSource);
            }
            return false;
        } catch (DuplicateDataException ex) {
            LOGGER.error("Error in json processing while getting Payment Response Data", ex);
            throw new DuplicateDataException(ex.getMessage());
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while getting Payment Response Data", ex);
            throw new RequestViolationException("Error in json processing while getting Payment Response Data");
        } catch (Exception ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public OutstandingBusinessUnit getBusinessUnits() throws ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), outstandingBaseUrl,
                    Outstanding.BUSINESS_UNIT_API.getURL());
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    outstandingApiAuthorization.getAuthorization(), OutstandingBusinessUnit.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public OnlineCreditResponse getOnlineCreditLimit(Long customerId) throws ServiceUnavailableException {
        try {
            String url = String.format(Param.THREE.getParam(), outstandingBaseUrl,
                    Outstanding.ONLLINE_CREDIT_LIMIT_API.getURL(), customerId);
            return apiClient.performGet(url, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    outstandingApiAuthorization.getAuthorization(), OnlineCreditResponse.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public CartPaymentResponse cartMakePayment(CartPaymentRequest cartPaymentRequest)
            throws ServiceUnavailableException {
        try {
            String url = String.format(Param.TWO.getParam(), outstandingBaseUrl,
                    Outstanding.SHOPPING_PAYMENT_REQUEST_API.getURL());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(cartPaymentRequest), outstandingApiAuthorization.getAuthorization(),
                    CartPaymentResponse.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while getting Payment Response Data", ex);
            throw new RequestViolationException("Error in json processing while getting Payment Response Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public OnAccountPaymentResponseWrapper sendOnAccountRequest(OnAccountRequestWrapper onAccountPaymentRequest)
            throws ServiceUnavailableException {
        try {
            String url = String.format(Param.THREE.getParam(), outstandingBaseUrl,
                    Outstanding.ON_ACCOUNT_PAYMENT_API.getURL(),
                    onAccountPaymentRequest.getOnAccountPaymentRequest().getMainCustomerId());
            return apiClient.performPost(url, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(onAccountPaymentRequest), outstandingApiAuthorization.getAuthorization(),
                    OnAccountPaymentResponseWrapper.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while getting Payment Response Data", ex);
            throw new RequestViolationException("Error in json processing while getting Payment Response Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Outstanding Service URI");
        }
    }

    @Override
    public Boolean payOnAccount(OnAccountRequestWrapper onAccountPaymentRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException {
        try {
            return paymentService
                    .saveMasterUserTransactionDetailsOnAccount(onAccountPaymentRequest.getOnAccountPaymentRequest());
        } catch (Exception ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("Unavle to save details in on acctount table");
        }
    }

}
