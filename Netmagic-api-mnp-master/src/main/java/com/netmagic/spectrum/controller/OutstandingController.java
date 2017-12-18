package com.netmagic.spectrum.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.dto.outstanding.request.CartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountRequestWrapper;
import com.netmagic.spectrum.dto.outstanding.request.OutstandingRequest;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequest;
import com.netmagic.spectrum.dto.outstanding.response.CartPaymentResponse;
import com.netmagic.spectrum.dto.outstanding.response.OnlineCreditResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingBusinessUnit;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingListResponse;
import com.netmagic.spectrum.dto.outstanding.response.OutstandingWidgetResponse;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.OutstandingService;

@RequestMapping(value = "/api/outstanding")
@RestController
public class OutstandingController {

    @Autowired
    private OutstandingService outstandingService;

    @RequestMapping(value = "/widget", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OutstandingWidgetResponse fetchWidgetData(@RequestBody final OutstandingRequest outstandingWidgetRequest) {
        return outstandingService.getWidgetData(outstandingWidgetRequest);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OutstandingListResponse fetchListData(@RequestBody final OutstandingRequest outstandingListRequest) {
        return outstandingService.getListData(outstandingListRequest);
    }

    @RequestMapping(value = "/payment/{paymentSource}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public boolean makePayment(@PathVariable("paymentSource") String paymentSource,
            @RequestParam("merchantRefNumber") String merchantRefNumber,
            @RequestBody final PaymentRequest invoicePaymentRequest) throws IOException {
        return outstandingService.sendPaymentRequest(invoicePaymentRequest, merchantRefNumber, paymentSource);
    }

    @RequestMapping(value = "/businessUnits", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OutstandingBusinessUnit fetchBusinessUnits() {
        return outstandingService.getBusinessUnits();
    }

    @RequestMapping(value = "/onlineCreditLimit/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public OnlineCreditResponse fetchCreditLimit(@PathVariable("customerId") Long customerId) {
        return outstandingService.getOnlineCreditLimit(customerId);
    }

    @RequestMapping(value = "/cart/payment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CartPaymentResponse sofMakePayment(@RequestBody final CartPaymentRequest cartPaymentRequest) {
        return outstandingService.cartMakePayment(cartPaymentRequest);
    }

    @RequestMapping(value = "/pay/onAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean payOnAccount(@RequestBody OnAccountRequestWrapper onAccountPaymentRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException {
        return outstandingService.payOnAccount(onAccountPaymentRequest);
    }

}
