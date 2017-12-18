package com.netmagic.spectrum.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequestDetails;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.SplitAccountPrice;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;

public interface PaymentService {

    /**
     * This method is used to split the create sof requests into new and
     * amendment lists and updating into master user table and sof table based
     * on payment source.
     * 
     * This method also requests the pricing for updated price and requests to
     * create the sofs and update the sof transaction details table on the
     * responses.
     * 
     * 
     * @param createSofRequest
     * @param mainCustomerId
     * @param transactionNumber
     * @param paymentId
     * @return
     * @throws ServiceUnavailableException
     * @throws DuplicateDataException
     * @throws IOException
     */
    boolean saveTransactionSofRequests(CreateSofRequest createSofRequest, String mainCustomerId,
            String transactionNumber, String merchantRefNumber, long paymentId)
            throws ServiceUnavailableException, DuplicateDataException, IOException;

    /**
     * This method is used to save the on account payment details in master user
     * table and on account payment table and people-soft.
     * 
     * @param onAccountPaymentRequest
     * @return {@link Boolean}
     * @throws ServiceUnavailableException
     * @throws DuplicateDataException
     * @throws IOException
     */
    boolean saveMasterUserTransactionDetailsOnAccount(OnAccountPaymentRequest onAccountPaymentRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException;

    /**
     * 
     * @param paymentRequestDetails
     * @param merchantRefNumber
     * @param paymentSource
     * @return
     * @throws DuplicateDataException
     * @throws ServiceUnavailableException
     * @throws IOException
     */
    boolean saveInvoiceDetails(PaymentRequestDetails paymentRequestDetails, String merchantRefNumber,
            String paymentSource) throws DuplicateDataException, ServiceUnavailableException, IOException;

    /**
     * Method gets the invoice total amount based on email id, main customer id
     * and associated customer id
     * 
     * @param userEmail
     * @param billToCustomer
     * @param supportToCustomer
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws IOException
     */
    SplitAccountPrice getInvoiceTotalAmount(String userEmail, String billToCustomer, String supportToCustomer)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException;

    /**
     * 
     * @param merchantRefNumber
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws JsonProcessingException
     * @throws IOException
     */
    Boolean createLineItemSof(String merchantRefNumber)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException;

}
