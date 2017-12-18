package com.netmagic.spectrum.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netmagic.spectrum.commons.SofType;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.CustomerAddressRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.DeleteLineItemRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductPriceRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerAddressResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerContactResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.LocationList;
import com.netmagic.spectrum.dto.shoppingcart.response.PriceResponseData;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductList;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPriceResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofTransactionDetails;
import com.netmagic.spectrum.dto.shoppingcart.response.SubCategoryList;
import com.netmagic.spectrum.dto.shoppingcart.response.SubSubCategoryList;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.ShoppingCartService;

@RequestMapping(value = "/api/shoppingCart")
@RestController
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/locations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public LocationList fetchLocations() {
        return shoppingCartService.getLocations();
    }

    @RequestMapping(value = "/subCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubCategoryList fetchSubCategories() {
        return shoppingCartService.getSubCategories();
    }

    @RequestMapping(value = "/subSubCategories/{subCatId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public SubSubCategoryList fetchSubSubCategories(@PathVariable String subCatId) {
        return shoppingCartService.getSubSubCategories(subCatId);
    }

    @RequestMapping(value = "/products/{subCatId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductList fetchProducts(@PathVariable String subCatId, @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "") String plan, @RequestParam(required = false) String isCapped) {
        return shoppingCartService.getProducts(location, subCatId, plan, isCapped);
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductList fetchProduct(@PathVariable String productId) {
        return shoppingCartService.getProduct(productId);
    }

    @RequestMapping(value = "/products/price", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPriceResponse fetchProductPricing(@RequestParam(defaultValue = "display") String purpose,
            @RequestBody final ProductPriceRequest productPriceRequest) {
        return shoppingCartService.getProductPricing(purpose, productPriceRequest);
    }

    @RequestMapping(value = "/contract/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public SofResponse createContract(@RequestBody final CreateSofRequest createSofRequest) {
        return shoppingCartService.createSof(createSofRequest, SofType.NEW.getSofType());
    }

    @RequestMapping(value = "/customer/contacts/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerContactResponse fetchContacts(@PathVariable String customerId) {
        return shoppingCartService.getContacts(customerId);
    }

    @RequestMapping(value = "/customer/address", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerAddressResponse createContract(@RequestBody final CustomerAddressRequest customerAddressRequest) {
        return shoppingCartService.getCustomerAddress(customerAddressRequest);
    }

    @RequestMapping(value = "/lineItem/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addLineItems(@RequestParam String emailId, @RequestParam(defaultValue = "mnp") String source,
            @RequestParam(required = false) Long mainCustomerId,
            @RequestParam(required = false) Long associateCustomerId, @RequestParam(required = false) String projectId,
            @RequestBody final ProductPriceResponse shoppingCartRequest) {
        return shoppingCartService.addLineItem(emailId, source, shoppingCartRequest, mainCustomerId,
                associateCustomerId, projectId);
    }

    @RequestMapping(value = "/lineItem/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean updateLineItems(@RequestParam String emailId, @RequestParam(defaultValue = "mnp") String source,
            @RequestBody final ProductPriceResponse shoppingCartRequest) {
        return shoppingCartService.updateLineItem(emailId, source, shoppingCartRequest);
    }

    @RequestMapping(value = "/lineItem/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteLineItems(@RequestBody final DeleteLineItemRequest deleteRequest) {
        return shoppingCartService.deleteLineItem(deleteRequest);
    }

    @RequestMapping(value = "/cartProducts/customer/{mainCustomerId}/associated/{associateCustomerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PriceResponseData fetchProductFromShoppingCart(@RequestParam String emailId,
            @PathVariable String mainCustomerId, @PathVariable String associateCustomerId) {
        return shoppingCartService.getShoppingCartProducts(emailId, mainCustomerId, associateCustomerId);
    }

    @RequestMapping(value = "/pay/sof/{paymentSource}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean paidByCreditOrEBS(@PathVariable String paymentSource, @RequestParam String mainCustomerId,
            @RequestParam String transactionNumber, @RequestParam String merchantRefNumber,
            @RequestBody final CreateSofRequest createSofRequest)
            throws ServiceUnavailableException, DuplicateDataException, IOException {
        return shoppingCartService.paidByCreditsOrEBS(createSofRequest, mainCustomerId, transactionNumber,
                merchantRefNumber, paymentSource);
    }

    @RequestMapping(value = "/sofs/{merchantRefNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SofTransactionDetails> fetchProductFromShoppingCart(@PathVariable String merchantRefNumber)
            throws ServiceUnavailableException, IOException {
        return shoppingCartService.getSofsByMerchantRefNumber(merchantRefNumber);
    }

    @RequestMapping(value = "/save/products", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean saveCart(@RequestParam String emailId, @RequestParam(required = false) Long mainCustomerId,
            @RequestParam(required = false) Long associateCustomerId,
            @RequestBody final ProductPriceResponse shoppingCartRequest) {
        return shoppingCartService.saveCart(emailId, shoppingCartRequest, mainCustomerId, associateCustomerId);
    }

    @RequestMapping(value = "/lineItem/read/token", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isLineItemLineItems(@RequestParam String mainCustomerId, @RequestParam String associastedCustomerId,
            @RequestParam String lineItemToken) {
        return shoppingCartService.isValidLineItemToken(mainCustomerId, associastedCustomerId, lineItemToken);
    }

    @RequestMapping(value = "/lineItem/read/instanceId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean isLineItemInstanceId(@RequestParam String mainCustomerId, @RequestParam String associastedCustomerId,
            @RequestParam String instanceId) {
        return shoppingCartService.isValidInstanceId(mainCustomerId, associastedCustomerId, instanceId);
    }

    @RequestMapping(value = "/billGroup/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public BillingGroupResponse getVariableUnitForCombination(@RequestParam String billTo,
            @RequestParam String supportTo, @RequestParam(required = false) String projectName) {
        return shoppingCartService.getVariableUnitInCart(billTo, supportTo, projectName);
    }

    @RequestMapping(value = "/create/sofs/{merchantRefNumber}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean createSofsLineItem(@PathVariable String merchantRefNumber)
            throws ServiceUnavailableException, IOException {
        return shoppingCartService.createLineItemSof(merchantRefNumber);
    }

}
