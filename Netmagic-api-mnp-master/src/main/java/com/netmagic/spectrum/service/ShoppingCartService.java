package com.netmagic.spectrum.service;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
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
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.DataNotFoundException;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.exception.UnProcessableException;

public interface ShoppingCartService {
    /**
     * This method fetches the location list from CMS portal
     * 
     * @return {@link LocationList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    LocationList getLocations() throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the sub categories list from CMS portal
     * 
     * @return {@link SubCategoryList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    SubCategoryList getSubCategories() throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the sub sub categories list from CMS portal
     * 
     * @param subCategoryId
     * @return {@link SubSubCategoryList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    SubSubCategoryList getSubSubCategories(String subCategoryId)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the product list from CMS portal
     * 
     * @param location
     * @param subCategoryId
     * @return {@link ProductList}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    ProductList getProducts(String location, String subCategoryId, String plan, String isCapped)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches single product details from CMS portal
     * 
     * @param productId
     * @return
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    ProductList getProduct(String productId) throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the pricing of the selected products in the cart from
     * Sugar CRM
     * 
     * @param purpose
     * 
     * @param productPriceRequest
     * @return {@link ProductPriceResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    ProductPriceResponse getProductPricing(String purpose, ProductPriceRequest productPriceRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method is for sending the request to create new SOF's
     * 
     * @param createSofRequest
     * @param sofType
     * @return {@link SofResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    SofResponse createSof(CreateSofRequest createSofRequest, String sofType)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method gets the contacts belonging to the given customer
     * 
     * @param customerId
     * @return {@link CustomerContactResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    CustomerContactResponse getContacts(String customerId)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * This method fetches the addresses belonging to a customer
     * 
     * @param customerAddressRequest
     * @return {@link CustomerAddressResponse}
     * @throws RequestViolationException
     * @throws ServiceUnavailableException
     */
    CustomerAddressResponse getCustomerAddress(CustomerAddressRequest customerAddressRequest)
            throws RequestViolationException, ServiceUnavailableException;

    /**
     * Adds line items into the shopping cart and returns values as per the
     * request source. For cloud it returns token and for mnp it returns
     * success.
     * 
     * @param userEmailId
     * @param source
     * @param shoppingCartRequest
     * @param mainCustomerId
     * @param associateCustomerId
     * @param projectId
     * @return {@link String}
     * @throws DataNotFoundException
     */
    String addLineItem(String userEmailId, String source, ProductPriceResponse shoppingCartRequest, Long mainCustomerId,
            Long associateCustomerId, String projectId) throws DataNotFoundException;

    /**
     * Updates line Items present on the shopping cart.It will update the line
     * items based on combination of unique token id and product id.
     * 
     * @param userEmailId
     * @param shoppingCartRequest
     * @param source
     * @return {@link Boolean}
     * @throws DataNotFoundException
     */
    Boolean updateLineItem(String userEmailId, String source, ProductPriceResponse shoppingCartRequest)
            throws DataNotFoundException;

    /**
     * Delete the line item which is associated with shopping cart id and the
     * token for that line item
     * 
     * @param deleteRequest
     * @return {@link Boolean}
     * @throws DataNotFoundException
     */
    Boolean deleteLineItem(DeleteLineItemRequest deleteRequest) throws DataNotFoundException;

    /**
     * This method fetches the existing product list present in the shopping
     * cart for a customer
     * 
     * @param userEmailId
     * @param mainCustomerId
     * @param associateCustomerId
     * @return {@link PriceResponseData}
     * @throws CacheServiceException
     */
    PriceResponseData getShoppingCartProducts(String userEmailId, String mainCustomerId, String associateCustomerId)
            throws CacheServiceException;

    /**
     * This method is used to split the create sof requests into new and
     * amendment lists and updating into master user table and sof table.
     * 
     * @param createSofRequest
     * @param mainCustomerId
     * @param transactionNumber
     * @param paymentSource
     * @return
     * 
     * @throws UnProcessableException
     * @throws ServiceUnavailableException
     * @throws JsonProcessingException,
     *             DuplicateDataException
     * @throws IOException
     */
    Boolean paidByCreditsOrEBS(CreateSofRequest createSofRequest, String mainCustomerId, String transactionNumber,
            String merchantRefNumber, String paymentSource) throws UnProcessableException, ServiceUnavailableException,
            JsonProcessingException, DuplicateDataException, IOException;

    /**
     * fetch the data from database with respected to transaction. who had paid
     * the amount
     * 
     * @param merchantRefNumber
     * @return
     * @throws DataNotFoundException
     * @throws ServiceUnavailableException
     * @throws IOException
     */
    List<SofTransactionDetails> getSofsByMerchantRefNumber(String merchantRefNumber)
            throws DataNotFoundException, ServiceUnavailableException, IOException;

    /**
     * 
     * @param emailId
     * @param shoppingCartRequest
     * @param mainCustomerId
     * @param associateCustomerId
     * @return
     * @throws ServiceUnavailableException
     */
    Boolean saveCart(String userEmailId, ProductPriceResponse shoppingCartRequest, Long mainCustomerId,
            Long associateCustomerId) throws ServiceUnavailableException;

    /**
     * check the line item token in the redis.
     * 
     * @param mainCustomerId
     * @param associastedCustomerId
     * @param lineItemToken
     * @return
     */
    Boolean isValidLineItemToken(String mainCustomerId, String associastedCustomerId, String lineItemToken);

    /**
     * check instance id in redis
     * 
     * @param mainCustomerId
     * @param associastedCustomerId
     * @param instanceId
     * @return
     */
    Boolean isValidInstanceId(String mainCustomerId, String associastedCustomerId, String instanceId);

    /**
     * This method updates the start date and end date of new and amendment line
     * items present in the cart
     * 
     * @param cachedShoppingCartDetails
     */
    void updateCartStartDate(CachedShoppingCartDetails cachedShoppingCartDetails);

    /**
     * This method searched for the specified combination of main customer,
     * associate customer and project in the cart for new project and returns
     * the product billing group if a variable product is present
     * 
     * @param mainCustomerId
     * @param associastedCustomerId
     * @param projectName
     * @return {@link BillingGroupResponse}
     */
    BillingGroupResponse getVariableUnitInCart(String mainCustomerId, String associastedCustomerId, String projectName);

    /**
     * 
     * @param merchantRefNumber
     * @return
     * @throws IOException
     * @throws JsonProcessingException
     * @throws JsonMappingException
     * @throws JsonParseException
     */
    Boolean createLineItemSof(String merchantRefNumber) throws ServiceUnavailableException, JsonParseException,
            JsonMappingException, JsonProcessingException, IOException;

}
