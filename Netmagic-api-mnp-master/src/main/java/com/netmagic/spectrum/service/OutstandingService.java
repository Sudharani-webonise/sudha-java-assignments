package com.netmagic.spectrum.service;

import java.io.IOException;

import org.springframework.security.access.prepost.PreAuthorize;

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

public interface OutstandingService {
    /**
     * This method fetches the widget data for Outstanding module
     * 
     * @param customerId
     * @param assoCustomerId
     * @param businessUnit
     * @return OutstandingWidgetResponse which holds the widget Data
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('OST_WDGT')")
    OutstandingWidgetResponse getWidgetData(OutstandingRequest outstandingRequest) throws ServiceUnavailableException;

    /**
     * This method fetches the list data for Outstanding module
     * 
     * @param customerId
     * @param assoCustomerId
     * @param businessUnit
     * @return OutstandingListResponse which holds the list of invoices
     * @throws ServiceUnavailableException
     */
    @PreAuthorize("hasAuthority('OST_LIST')")
    OutstandingListResponse getListData(OutstandingRequest outstandingRequest) throws ServiceUnavailableException;

    /**
     * This method sends the payment details to people-soft and gets success or
     * failure of payments made
     * 
     * @param invoicePaymentRequest
     * @param merchantRefNumber
     * @param paymentSource
     * @return {@link PaymentResponse}, This tells us whether the payment
     *         request was successful or not
     * @throws ServiceUnavailableException
     * @throws RequestViolationException
     * @throws IOException
     * @throws DuplicateDataException
     */
    @PreAuthorize("hasAuthority('OST_PAYT')")
    boolean sendPaymentRequest(PaymentRequest invoicePaymentRequest, String merchantRefNumber, String paymentSource)
            throws ServiceUnavailableException, RequestViolationException, DuplicateDataException, IOException;

    /**
     * This method fetches the business units available in the system
     * 
     * @return {@link OutstandingBusinessUnit}
     * @throws ServiceUnavailableException
     */
    OutstandingBusinessUnit getBusinessUnits() throws ServiceUnavailableException;

    /**
     * This method fetches the online credit limit available for a customer
     * 
     * @param customerId
     * @return {@link OnlineCreditResponse}
     * @throws ServiceUnavailableException
     */
    OnlineCreditResponse getOnlineCreditLimit(Long customerId) throws ServiceUnavailableException;

    /**
     * This method passes the detail of all sofs created with respect to
     * reference number, which will informs he people soft
     * 
     * @param cartPaymentRequest
     * @return
     * @throws ServiceUnavailableException
     */
    CartPaymentResponse cartMakePayment(CartPaymentRequest cartPaymentRequest) throws ServiceUnavailableException;

    /**
     * 
     * @param onAccountPaymentRequest
     * @return
     * @throws ServiceUnavailableException
     * @throws DuplicateDataException
     * @throws IOException
     */
    OnAccountPaymentResponseWrapper sendOnAccountRequest(OnAccountRequestWrapper onAccountPaymentRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException;

    /**
     * 
     * @param onAccountPaymentRequest
     * @return
     * @throws ServiceUnavailableException
     * @throws DuplicateDataException
     * @throws IOException
     */
    Boolean payOnAccount(OnAccountRequestWrapper onAccountPaymentRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException;
}
