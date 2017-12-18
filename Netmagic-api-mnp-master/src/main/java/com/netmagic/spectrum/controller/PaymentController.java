package com.netmagic.spectrum.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.BusinessUnit;
import com.netmagic.spectrum.dto.PaymentForm;
import com.netmagic.spectrum.dto.customer.CustomerAddress;
import com.netmagic.spectrum.dto.shoppingcart.response.SplitAccountPrice;
import com.netmagic.spectrum.dto.user.response.UserResponseTemp;
import com.netmagic.spectrum.service.CustomerService;
import com.netmagic.spectrum.service.PaymentService;

@RequestMapping(value = "/payment")
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CacheService<UserResponseTemp> userCacheService;

    @Autowired
    private CustomerService customerService;

    @Value("${payment.sof.response.url.react}")
    private String sofResponseUrl;

    @Value("${payment.outstanding.response.url.react}")
    private String outstandingResponseUrl;

    @Value("${payment.onaccount.response.url.react}")
    private String onAccountResponseUrl;

    @Value("${payment.url}")
    private String paymentUrl;

    @Value("${payment.sof.return.url}")
    private String sofReturnUrl;

    @Value("${payment.outstanding.return.url}")
    private String outstandingReturnUrl;

    @Value("${payment.onaccount.return.url}")
    private String onAccountReturnUrl;

    @Value("${payment.submit.url}")
    private String paymentSubmitUrl;

    @Value("${payment.mode}")
    private String paymentMode;

    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView showPaymentForm() {
        ModelAndView model = new ModelAndView("payment");
        return model;
    }

    @RequestMapping(value = "/paynow/sof", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView payForSof(@RequestParam(required = false) String billToCustomer,
            @RequestParam(required = false) String supportToCustomer, @ModelAttribute PaymentForm paymentForm)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        SplitAccountPrice splitAccountPrice = paymentService.getInvoiceTotalAmount(paymentForm.getEmail(),
                billToCustomer, supportToCustomer);
        ModelAndView model = new ModelAndView("paymentDetails");
        model.addObject("amount", paymentForm.getAmount());
        model.addObject("email", paymentForm.getEmail());
        model.addObject("name", paymentForm.getName());
        model.addObject("shipName", paymentForm.getName());
        UserResponseTemp tempUser = new UserResponseTemp(paymentForm.getEmail());
        UserResponseTemp response = userCacheService.get(tempUser);
        if ( response != null ) {
            model.addObject("address", response.getAddress());
            model.addObject("city", response.getCity());
            model.addObject("state", response.getState());
            model.addObject("pinCode", response.getPincode());
            model.addObject("mobile", response.getMobile());
            model.addObject("shipAddress", response.getAddress());
            model.addObject("shipCity", response.getCity());
            model.addObject("shipState", response.getState());
            model.addObject("shipPinCode", response.getPincode());
            model.addObject("shipMobile", response.getMobile());
        } else {
            CustomerAddress customerAddress = customerService.getCustomerAddress(billToCustomer);
            if ( customerAddress.getPhoneNo() == null || customerAddress.getPhoneNo().size() == 0 ) {
                model.addObject("mobile", "1111111111");
                model.addObject("shipMobile", "11111111");
            } else {
                model.addObject("mobile", customerAddress.getPhoneNo().get(0));
                model.addObject("shipMobile", customerAddress.getPhoneNo().get(0));
            }
            if ( customerAddress.getCompanyAddress() == null ) {
                model.addObject("shipAddress", "NA");
                model.addObject("address", "NA");
            } else {
                model.addObject("address", customerAddress.getCompanyAddress());
                model.addObject("shipAddress", customerAddress.getCompanyAddress());
            }
            if ( customerAddress.getCity() == null ) {
                model.addObject("city", "NA");
                model.addObject("shipCity", "NA");
            } else {
                model.addObject("city", customerAddress.getCity());
                model.addObject("shipCity", customerAddress.getCity());
            }
            if ( customerAddress.getState() == null ) {
                model.addObject("state", "NA");
                model.addObject("shipState", "NA");
            } else {
                model.addObject("state", customerAddress.getState());
                model.addObject("shipState", customerAddress.getState());
            }
            if ( customerAddress.getPinCode() == null ) {
                model.addObject("pinCode", "NA");
                model.addObject("shipPinCode", "NA");
            } else {
                model.addObject("pinCode", customerAddress.getPinCode());
                model.addObject("shipPinCode", customerAddress.getPinCode());
            }
        }
        model.addObject("referenceid", paymentForm.getReference_no());
        model.addObject("returnurl", sofReturnUrl);
        model.addObject("nmitTotal", splitAccountPrice.getTotalNmit());
        model.addObject("nmsplTotal", splitAccountPrice.getTotalNmspl());
        model.addObject("paymenturl", paymentUrl);
        model.addObject("paymentsubmiturl", paymentSubmitUrl);
        return model;
    }

    @RequestMapping(value = "/paynow/invoice/{businessUnit}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView payForInvoice(@PathVariable String businessUnit, @RequestParam String billToCustomer,
            @ModelAttribute PaymentForm paymentForm)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        ModelAndView model = new ModelAndView("paymentDetails");
        model.addObject("amount", paymentForm.getAmount());
        model.addObject("name", paymentForm.getName());
        model.addObject("shipName", paymentForm.getName());
        model.addObject("email", paymentForm.getEmail());
        model.addObject("referenceid", paymentForm.getReference_no());
        if ( businessUnit.equalsIgnoreCase(BusinessUnit.NMITS.getType()) ) {
            model.addObject("nmitTotal", paymentForm.getAmount());
            model.addObject("nmsplTotal", 0);
        } else if ( businessUnit.equalsIgnoreCase(BusinessUnit.NMSPL.getType()) ) {
            model.addObject("nmitTotal", 0);
            model.addObject("nmsplTotal", paymentForm.getAmount());
        }
        CustomerAddress customerAddress = customerService.getCustomerAddress(billToCustomer);
        if ( customerAddress.getPhoneNo() == null || customerAddress.getPhoneNo().size() == 0 ) {
            model.addObject("mobile", "1111111111");
            model.addObject("shipMobile", "11111111");
        } else {
            model.addObject("mobile", customerAddress.getPhoneNo().get(0));
            model.addObject("shipMobile", customerAddress.getPhoneNo().get(0));
        }
        if ( customerAddress.getCompanyAddress() == null ) {
            model.addObject("shipAddress", "NA");
            model.addObject("address", "NA");
        } else {
            model.addObject("address", customerAddress.getCompanyAddress());
            model.addObject("shipAddress", customerAddress.getCompanyAddress());
        }
        if ( customerAddress.getCity() == null ) {
            model.addObject("city", "NA");
            model.addObject("shipCity", "NA");
        } else {
            model.addObject("city", customerAddress.getCity());
            model.addObject("shipCity", customerAddress.getCity());
        }
        if ( customerAddress.getState() == null ) {
            model.addObject("state", "NA");
            model.addObject("shipState", "NA");
        } else {
            model.addObject("state", customerAddress.getState());
            model.addObject("shipState", customerAddress.getState());
        }
        if ( customerAddress.getPinCode() == null ) {
            model.addObject("pinCode", "NA");
            model.addObject("shipPinCode", "NA");
        } else {
            model.addObject("pinCode", customerAddress.getPinCode());
            model.addObject("shipPinCode", customerAddress.getPinCode());
        }
        model.addObject("returnurl", outstandingReturnUrl);
        model.addObject("paymenturl", paymentUrl);
        model.addObject("paymentsubmiturl", paymentSubmitUrl);
        return model;
    }

    @RequestMapping(value = "/paynow/onAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView payForOnAccount(@RequestParam String billToCustomer, @ModelAttribute PaymentForm paymentForm)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        ModelAndView model = new ModelAndView("paymentDetails");
        model.addObject("amount", paymentForm.getAmount());
        model.addObject("name", paymentForm.getName());
        model.addObject("shipName", paymentForm.getName());
        model.addObject("email", paymentForm.getEmail());
        model.addObject("referenceid", paymentForm.getReference_no());
        model.addObject("paymentMode", paymentMode);
        CustomerAddress customerAddress = customerService.getCustomerAddress(billToCustomer);
        if ( customerAddress.getPhoneNo() == null || customerAddress.getPhoneNo().size() == 0 ) {
            model.addObject("mobile", "1111111111");
            model.addObject("shipMobile", "11111111");
        } else {
            model.addObject("mobile", customerAddress.getPhoneNo().get(0));
            model.addObject("shipMobile", customerAddress.getPhoneNo().get(0));
        }
        if ( customerAddress.getCompanyAddress() == null ) {
            model.addObject("shipAddress", "NA");
            model.addObject("address", "NA");
        } else {
            model.addObject("address", customerAddress.getCompanyAddress());
            model.addObject("shipAddress", customerAddress.getCompanyAddress());
        }
        if ( customerAddress.getCity() == null ) {
            model.addObject("city", "NA");
            model.addObject("shipCity", "NA");
        } else {
            model.addObject("city", customerAddress.getCity());
            model.addObject("shipCity", customerAddress.getCity());
        }
        if ( customerAddress.getState() == null ) {
            model.addObject("state", "NA");
            model.addObject("shipState", "NA");
        } else {
            model.addObject("state", customerAddress.getState());
            model.addObject("shipState", customerAddress.getState());
        }
        if ( customerAddress.getPinCode() == null ) {
            model.addObject("pinCode", "NA");
            model.addObject("shipPinCode", "NA");
        } else {
            model.addObject("pinCode", customerAddress.getPinCode());
            model.addObject("shipPinCode", customerAddress.getPinCode());
        }
        model.addObject("returnurl", onAccountReturnUrl);
        model.addObject("paymenturl", paymentUrl);
        model.addObject("paymentsubmiturl", paymentSubmitUrl);
        return model;
    }

    @RequestMapping(value = "/submitpayment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView submitPaymentForm(@ModelAttribute PaymentForm paymentForm) {
        ModelAndView model = new ModelAndView("pay");
        return model;
    }

    @RequestMapping(value = "/paymentResponse/sof", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getPaymentResponseSof(@ModelAttribute PaymentForm paymentForm) {
        ModelAndView model = new ModelAndView("paymentResponse");
        model.addObject("responseurl", sofResponseUrl);
        return model;
    }

    @RequestMapping(value = "/paymentResponse/outstanding", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getPaymentResponseOutstanding(@ModelAttribute PaymentForm paymentForm) {
        ModelAndView model = new ModelAndView("paymentResponse");
        model.addObject("responseurl", outstandingResponseUrl);
        return model;
    }

    @RequestMapping(value = "/paymentResponse/onAccount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getPaymentResponseOnAccount(@ModelAttribute PaymentForm paymentForm) {
        ModelAndView model = new ModelAndView("paymentResponse");
        model.addObject("responseurl", onAccountResponseUrl);
        return model;
    }

}
