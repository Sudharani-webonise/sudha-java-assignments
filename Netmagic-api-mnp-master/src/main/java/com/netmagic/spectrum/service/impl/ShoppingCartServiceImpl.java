package com.netmagic.spectrum.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.apiclient.ApiClient;
import com.netmagic.spectrum.apiclient.SugarCRMAuthorization;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.Param;
import com.netmagic.spectrum.commons.PaymentType;
import com.netmagic.spectrum.commons.SofType;
import com.netmagic.spectrum.commons.url.ShoppingCart;
import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dto.customer.BillingGroupResponse;
import com.netmagic.spectrum.dto.shoppingcart.request.BillingGroup;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.CustomerAddressRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.CustomerContactRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.DeleteLineItemRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductDetailRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductListRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductPriceRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.SubSubCategoryRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.BillingGroupPrice;
import com.netmagic.spectrum.dto.shoppingcart.response.CMSProducts;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerAddressResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.CustomerContactResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.LocationList;
import com.netmagic.spectrum.dto.shoppingcart.response.PriceResponseData;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductList;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPrice;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPriceResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofTransactionDetails;
import com.netmagic.spectrum.dto.shoppingcart.response.SubCategoryList;
import com.netmagic.spectrum.dto.shoppingcart.response.SubSubCategoryList;
import com.netmagic.spectrum.dto.sso.response.DeleteLineItemCloudResponse;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;
import com.netmagic.spectrum.exception.CacheServiceException;
import com.netmagic.spectrum.exception.DataNotFoundException;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.RequestViolationException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.exception.WrongBillGroupException;
import com.netmagic.spectrum.service.CustomerService;
import com.netmagic.spectrum.service.PaymentService;
import com.netmagic.spectrum.service.SSOService;
import com.netmagic.spectrum.service.ShoppingCartService;
import com.netmagic.spectrum.utils.DateUtils;
import com.netmagic.spectrum.utils.RandomString;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    private static final String INVALID_TOKEN = "Invalid Token/Session Expired";

    private static final String YES = "Yes";

    private static final String NO = "No";

    private static final String MBPS = "Mbps";

    private static final String GB = "GB";

    private static final String CLOUD = "cloud";

    private static final String MNP = "mnp";

    private static final String DISPLAY = "display";

    private static final String CAPPED = "capped";

    private static final String BURSTABLE = "burstable";

    private static final String EBS = "ebs";

    private static final String CREDITS = "credits";

    private static final String NEW = "new";

    private static final String AMENDMENT = "amendment";

    private static final String ERROR_MSG = "errMsg";

    private static final String NO_BILLING_GROUP = "No Billing Group exist";

    @Value("${cms-api-baseUrl}")
    private String cmsApiBaseUrl;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private ApiClient apiClient;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SugarCRMAuthorization sugarCRMAuthorization;

    @Autowired
    private CacheService<CachedShoppingCartDetails> cacheService;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MasterUserTransactionDao masterUserTransactionDao;

    @Override
    public LocationList getLocations() throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), cmsApiBaseUrl, ShoppingCart.LOCATION.getURL());
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), LocationList.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Shopping Cart Service URI");
        }
    }

    @Override
    public SubCategoryList getSubCategories() throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), cmsApiBaseUrl, ShoppingCart.SUB_CATEGORY.getURL());
            return apiClient.performGet(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    Collections.emptyMap(), SubCategoryList.class);
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing GET call", ex);
            throw new ServiceUnavailableException("I/O error on GET request for Shopping Cart Service URI");
        }
    }

    @Override
    public SubSubCategoryList getSubSubCategories(String subCategoryId)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), cmsApiBaseUrl,
                    ShoppingCart.SUB_SUB_CATEGORY.getURL());
            SubSubCategoryRequest subSubCategoryRequest = new SubSubCategoryRequest(subCategoryId);
            return apiClient.performPost(queryString, MediaType.APPLICATION_JSON_VALUE,
                    mapper.writeValueAsString(subSubCategoryRequest), Collections.emptyMap(), SubSubCategoryList.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Sub Sub Category List", ex);
            throw new RequestViolationException("Error in json processing while Getting Sub Sub Category List");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Sub Sub Category URI");
        }
    }

    @Override
    public ProductList getProducts(String location, String subSubCategoryId, String plan, String isCapped)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), cmsApiBaseUrl, ShoppingCart.PRODUCTS.getURL());
            ProductListRequest productListRequest = new ProductListRequest(location, subSubCategoryId);
            ProductList productList = apiClient.performPost(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    mapper.writeValueAsString(productListRequest), Collections.emptyMap(), ProductList.class);
            if ( productList.getProducts() == null || productList == null ) {
                throw new DataNotFoundException("No products available for the given location");
            }
            List<CMSProducts> products = new ArrayList<>();
            if ( GB.equalsIgnoreCase(plan) ) {
                products = getFilteredResultGBPSNonVariable(productList, MBPS);
                products.addAll(getFilteredResultGBPSVariable(productList, MBPS));
            } else if ( MBPS.equalsIgnoreCase(plan) ) {
                if ( YES.equalsIgnoreCase(isCapped) ) {
                    products = getFilteredResultMBPSNonVariable(productList, YES);
                    products.addAll(getFilteredResultMBPSVariable(productList, YES));
                } else {
                    products = getFilteredResultMBPSNonVariable(productList, NO);
                    products.addAll(getFilteredResultMBPSVariable(productList, NO));
                }
            } else {
                return productList;
            }
            productList.setProducts(products);
            return productList;
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Product List", ex);
            throw new RequestViolationException("Error in json processing while Getting Product List");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Product List URI");
        }
    }

    private List<CMSProducts> getFilteredResultMBPSVariable(ProductList productList, String isCapped) {
        List<CMSProducts> products = new ArrayList<>();
        if ( productList != null ) {
            products = productList.getProducts().stream().filter(cmsProduct -> cmsProduct.getCrmProducts() != null)
                    .filter(cmsProduct -> cmsProduct.getCrmProducts().stream()
                            .anyMatch(product -> (isCapped.equalsIgnoreCase(product.getIsCappedProduct())
                                    && !GB.equalsIgnoreCase(product.getVariableProductUOM())
                                    && product.getIsVariableProduct().equalsIgnoreCase(YES))))
                    .collect(Collectors.toList());
        }
        return products;
    }

    private List<CMSProducts> getFilteredResultMBPSNonVariable(ProductList productList, String isCapped) {
        List<CMSProducts> products = new ArrayList<>();
        if ( productList != null ) {
            for ( CMSProducts cmsProduct : productList.getProducts() ) {
                if ( cmsProduct.getCrmProducts() != null ) {
                    if ( NO.equalsIgnoreCase(cmsProduct.getCrmProducts().get(0).getIsVariableProduct()) ) {
                        products.add(cmsProduct);
                    }
                }
            }
        }
        return products;
    }

    private List<CMSProducts> getFilteredResultGBPSVariable(ProductList productList, String isCapped) {
        List<CMSProducts> products = new ArrayList<>();
        if ( productList != null )
            products = productList.getProducts().stream().filter(cmsProduct -> cmsProduct.getCrmProducts() != null)
                    .filter(cmsProduct -> cmsProduct.getCrmProducts().stream()
                            .anyMatch(product -> (!isCapped.equalsIgnoreCase(product.getVariableProductUOM())
                                    && product.getIsVariableProduct().equalsIgnoreCase(YES))))
                    .collect(Collectors.toList());
        return products;
    }

    private List<CMSProducts> getFilteredResultGBPSNonVariable(ProductList productList, String isCapped) {
        List<CMSProducts> products = new ArrayList<>();
        if ( productList != null )
            products = productList.getProducts().stream().filter(cmsProduct -> cmsProduct.getCrmProducts() != null)
                    .filter(cmsProduct -> cmsProduct.getCrmProducts().stream()
                            .anyMatch(product -> (!isCapped.equalsIgnoreCase(product.getVariableProductUOM())
                                    && product.getIsVariableProduct().equalsIgnoreCase(NO))))
                    .collect(Collectors.toList());
        return products;
    }

    @Override
    public ProductList getProduct(String productId) throws RequestViolationException, ServiceUnavailableException {
        try {
            String queryString = String.format(Param.TWO.getParam(), cmsApiBaseUrl, ShoppingCart.PRODUCT.getURL());
            ProductDetailRequest productDetailRequest = new ProductDetailRequest(productId);
            return apiClient.performPost(queryString, MediaType.APPLICATION_FORM_URLENCODED_VALUE,
                    mapper.writeValueAsString(productDetailRequest), Collections.emptyMap(), ProductList.class);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Error in json processing while Getting Product Detail", ex);
            throw new RequestViolationException("Error in json processing while Getting Product Deails");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Product Detail URI");
        }
    }

    @Override
    public ProductPriceResponse getProductPricing(String purpose, ProductPriceRequest productPriceRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            productPriceRequest.setToken(sugarCRMAuthorization.fetchServiceToken());
            String price;
            if ( purpose.equalsIgnoreCase("calculate") ) {
                price = sugarCRMAuthorization.getSugarServiceResponse(productPriceRequest,
                        ShoppingCart.CALCULATE_PRICE.getURL());
                if ( price.equalsIgnoreCase(INVALID_TOKEN) || price.isEmpty() ) {
                    productPriceRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                    price = sugarCRMAuthorization.getSugarServiceResponse(productPriceRequest,
                            ShoppingCart.CALCULATE_PRICE.getURL());
                }
            } else {
                price = sugarCRMAuthorization.getSugarServiceResponse(productPriceRequest,
                        ShoppingCart.DISPLAY_PRICE.getURL());
                if ( price.equalsIgnoreCase(INVALID_TOKEN) || price.isEmpty() ) {
                    productPriceRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                    price = sugarCRMAuthorization.getSugarServiceResponse(productPriceRequest,
                            ShoppingCart.DISPLAY_PRICE.getURL());
                }
            }
            return mapper.readValue(price, ProductPriceResponse.class);
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error fetching product price data from shopping cart ", ex);
            throw new RequestViolationException("Error fetching product Pricing Data");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Shopping Cart URI");
        }
    }

    @Override
    public SofResponse createSof(CreateSofRequest createSofRequest, String sofType)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            createSofRequest.setToken(sugarCRMAuthorization.fetchServiceToken());
            if ( sofType.equalsIgnoreCase(SofType.NEW.getSofType()) ) {
                String sofs = sugarCRMAuthorization.getSugarServiceResponse(createSofRequest,
                        ShoppingCart.CREATE_NEW_SOF.getURL());
                if ( sofs.equalsIgnoreCase(INVALID_TOKEN) || sofs.isEmpty() ) {
                    createSofRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                    sofs = sugarCRMAuthorization.getSugarServiceResponse(createSofRequest,
                            ShoppingCart.CREATE_NEW_SOF.getURL());
                }
                return mapper.readValue(sofs, SofResponse.class);
            } else if ( sofType.equalsIgnoreCase(SofType.AMENDMENT.getSofType()) ) {
                String sofs = sugarCRMAuthorization.getSugarServiceResponse(createSofRequest,
                        ShoppingCart.CREATE_AMENDMENT_SOF.getURL());
                if ( sofs.equalsIgnoreCase(INVALID_TOKEN) || sofs.isEmpty() ) {
                    createSofRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                    sofs = sugarCRMAuthorization.getSugarServiceResponse(createSofRequest,
                            ShoppingCart.CREATE_AMENDMENT_SOF.getURL());
                }
                return mapper.readValue(sofs, SofResponse.class);
            } else {
                throw new ServiceUnavailableException("no sof type difined");
            }

        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error in creation of sof from sugar crm ", ex);
            throw new RequestViolationException("Error in creation of sof from sugar crm");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for creating sof URI");
        }
    }

    @Override
    public CustomerContactResponse getContacts(String customerId)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            CustomerContactRequest customerContactRequest = new CustomerContactRequest(
                    sugarCRMAuthorization.fetchServiceToken(), customerId);
            String contacts = sugarCRMAuthorization.getSugarServiceResponse(customerContactRequest,
                    ShoppingCart.CUSTOMER_CONTACT.getURL());
            if ( contacts.equalsIgnoreCase(INVALID_TOKEN) || contacts.isEmpty() ) {
                customerContactRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                contacts = sugarCRMAuthorization.getSugarServiceResponse(customerContactRequest,
                        ShoppingCart.CUSTOMER_CONTACT.getURL());
            }
            return mapper.readValue(contacts, CustomerContactResponse.class);
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error in fetching customer contacts from sugar crm ", ex);
            throw new RequestViolationException("Error in fetching customer contacts from sugar crm");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for customer contact URI");
        }
    }

    @Override
    public CustomerAddressResponse getCustomerAddress(CustomerAddressRequest customerAddressRequest)
            throws RequestViolationException, ServiceUnavailableException {
        try {
            customerAddressRequest.setToken(sugarCRMAuthorization.fetchServiceToken());
            String address = sugarCRMAuthorization.getSugarServiceResponse(customerAddressRequest,
                    ShoppingCart.CUSTOMER_ADDRESS.getURL());
            if ( address.equalsIgnoreCase(INVALID_TOKEN) || address.isEmpty() ) {
                customerAddressRequest.setToken(sugarCRMAuthorization.generateServiceToken());
                address = sugarCRMAuthorization.getSugarServiceResponse(customerAddressRequest,
                        ShoppingCart.CUSTOMER_ADDRESS.getURL());
            }
            return mapper.readValue(address, CustomerAddressResponse.class);
        } catch (IOException | RequestViolationException ex) {
            LOGGER.error("Error in fetching customer address from sugar crm ", ex);
            throw new RequestViolationException("Error in fetching customer address from sugar crm");
        } catch (ResourceAccessException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException(ex.getMessage());

        }
    }

    @Override
    public String addLineItem(String userEmailId, String source, ProductPriceResponse shoppingCartRequest,
            Long mainCustomerId, Long associateCustomerId, String projectId) throws DataNotFoundException {
        try {
            BillingGroupPrice requestBillGroup = shoppingCartRequest.getPriceResponseData().getBillingGroupPrices()
                    .stream().findFirst().get();
            List<BillingGroupPrice> newBillingGroup = new ArrayList<BillingGroupPrice>();
            checkDataMissingInRequest(requestBillGroup);
            PriceResponseData priceResponse = shoppingCartRequest.getPriceResponseData();
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(userEmailId,
                    priceResponse.getMainCustomerId(), priceResponse.getAssociateCustomerId());
            CachedShoppingCartDetails cachedCartData = cacheService.get(cacheShoppingBean);
            if ( cachedCartData == null && source.equalsIgnoreCase(CLOUD) ) {
                String cloudToken = RandomString.generateCloudToken();
                requestBillGroup.getProducts().stream().findFirst().get().setLineItemToken(cloudToken);
                checkAndAddVariableProduct(requestBillGroup, cloudToken);
                newBillingGroup.add(requestBillGroup);
                priceResponse.setBillingGroupPrices(newBillingGroup);
                return saveCartItem(priceResponse, cacheShoppingBean, cloudToken);
            } else if ( cachedCartData == null && source.equalsIgnoreCase(MNP) ) {
                String mnpToken = RandomString.generateMnpToken();
                requestBillGroup.getProducts().stream().findFirst().get().setLineItemToken(mnpToken);
                checkAndAddVariableProduct(requestBillGroup, mnpToken);
                newBillingGroup.add(requestBillGroup);
                priceResponse.setBillingGroupPrices(newBillingGroup);
                return saveCartItem(priceResponse, cacheShoppingBean, mnpToken);
            }
            List<BillingGroupPrice> cachedBillingGroups = cachedCartData.getPriceResponseData().getBillingGroupPrices();
            Boolean isNewBillGroup = true;
            if ( source.equalsIgnoreCase(CLOUD) ) {
                String cloudToken = RandomString.generateCloudToken();
                requestBillGroup.getProducts().stream().findFirst().get().setLineItemToken(cloudToken);
                isNewBillGroup = addProductToCart(cachedCartData, requestBillGroup, cachedBillingGroups, isNewBillGroup,
                        mainCustomerId, associateCustomerId, projectId, cloudToken);
                if ( isNewBillGroup ) {
                    checkAndAddVariableProduct(requestBillGroup, cloudToken);
                    cachedBillingGroups.add(requestBillGroup);
                }
                updateCartStartDate(cachedCartData);
                return cloudToken;

            } else {
                String mnpToken = RandomString.generateMnpToken();
                requestBillGroup.getProducts().stream().findFirst().get().setLineItemToken(mnpToken);
                isNewBillGroup = addProductToCart(cachedCartData, requestBillGroup, cachedBillingGroups, isNewBillGroup,
                        mainCustomerId, associateCustomerId, projectId, mnpToken);
                if ( isNewBillGroup ) {
                    checkAndAddVariableProduct(requestBillGroup, mnpToken);
                    cachedBillingGroups.add(requestBillGroup);
                }
                updateCartStartDate(cachedCartData);
                return mnpToken;
            }
        } catch (Exception ex) {
            LOGGER.error("No data found to update token : ", ex);
            throw new DataNotFoundException(ex.getMessage());
        }
    }

    private void checkAndAddVariableProduct(BillingGroupPrice requestBillGroup, String mnpToken) {
        if ( requestBillGroup.getProducts().size() == 2 ) {
            if ( requestBillGroup.getProducts().get(0).getVarProdAddedOfType() == null ) {
                requestBillGroup.getProducts().get(0).setLineItemToken(mnpToken);
                requestBillGroup.getProducts().get(1).setLineItemToken(RandomString.generateMnpToken());
                requestBillGroup
                        .setVariableProductTypeUom(requestBillGroup.getProducts().get(1).getVarProdAddedOfType());
            } else {
                requestBillGroup.getProducts().get(1).setLineItemToken(mnpToken);
                requestBillGroup.getProducts().get(0).setLineItemToken(RandomString.generateMnpToken());
                requestBillGroup
                        .setVariableProductTypeUom(requestBillGroup.getProducts().get(0).getVarProdAddedOfType());
            }
        } else {
            requestBillGroup.getProducts().get(0).setLineItemToken(mnpToken);
        }
    }

    private Boolean addProductToCart(CachedShoppingCartDetails cachedCartData, BillingGroupPrice requestBillGroup,
            List<BillingGroupPrice> cachedBillingGroups, Boolean isNewBillGroup, Long mainCustomerId,
            Long associateCustomerId, String projectId, String token)
            throws JsonParseException, JsonMappingException, IOException {
        for ( BillingGroupPrice cachedBillingGroup : cachedBillingGroups ) {
            if ( cachedBillingGroup.getCntrperiod().equals(requestBillGroup.getCntrperiod())
                    && cachedBillingGroup.getProjectName().equals(requestBillGroup.getProjectName())
                    && cachedBillingGroup.getLocation().equals(requestBillGroup.getLocation()) ) {
                List<ProductPrice> products = cachedBillingGroup.getProducts();
                ProductPrice newProduct = requestBillGroup.getProducts().get(0);
                if ( mainCustomerId == null && associateCustomerId == null ) {
                    cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products, token);
                } else if ( newProduct.getIsVariableProduct().equalsIgnoreCase(YES) ) {
                    BillingGroupResponse billingGroupResponse = customerService.getBillToCutomerDetails(mainCustomerId,
                            associateCustomerId, projectId);
                    if ( billingGroupResponse.getUnitOfMeasurment() == null ) {
                        cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products,
                                token);
                    } else if ( newProduct.getVariableProductUOM() != null
                            && newProduct.getVariableProductUOM().equalsIgnoreCase(GB)
                            && newProduct.getVariableProductUOM()
                                    .equalsIgnoreCase(billingGroupResponse.getUnitOfMeasurment()) ) {
                        cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products,
                                token);
                    } else if ( newProduct.getVariableProductUOM() != null
                            && newProduct.getVariableProductUOM().equalsIgnoreCase(MBPS)
                            && newProduct.getIsCappedProduct().equalsIgnoreCase(NO)
                            && billingGroupResponse.getRateBasedType().equalsIgnoreCase(BURSTABLE) ) {
                        cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products,
                                token);
                    } else if ( newProduct.getVariableProductUOM() != null
                            && newProduct.getVariableProductUOM().equalsIgnoreCase(MBPS)
                            && newProduct.getIsCappedProduct().equalsIgnoreCase(YES)
                            && billingGroupResponse.getRateBasedType().equalsIgnoreCase(CAPPED) ) {
                        cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products,
                                token);
                    } else {
                        throw new WrongBillGroupException(
                                "The requested product does not belong to this billgroup. Please select another product");
                    }
                } else {
                    cachedBillingGroup = addProductToProductList(requestBillGroup, cachedBillingGroup, products, token);
                }
                BillingGroupPrice newBillingGroupPrice = updateBillingGroupPrice(cachedCartData, cachedBillingGroup);
                cachedBillingGroups.remove(cachedBillingGroup);
                cachedBillingGroups.add(newBillingGroupPrice);
                return false;
            }
        }
        return isNewBillGroup;
    }

    private BillingGroupPrice addProductToProductList(BillingGroupPrice requestBillGroup,
            BillingGroupPrice cachedBillingGroup, List<ProductPrice> products, String token) {
        if ( requestBillGroup.getProducts().size() == 1 ) {
            products.add(requestBillGroup.getProducts().get(0));
        } else if ( requestBillGroup.getProducts().size() == 2 ) {
            for ( ProductPrice productPrice : products ) {
                if ( productPrice.getVarProdAddedOfType() != null
                        && productPrice.getVarProdAddedOfType().equalsIgnoreCase(GB) ) {
                    if ( requestBillGroup.getProducts().get(0).getVarProdAddedOfType() == null ) {
                        requestBillGroup.getProducts().get(0).setLineItemToken(token);
                        products.add(requestBillGroup.getProducts().get(0));
                        cachedBillingGroup.setProducts(products);
                        return cachedBillingGroup;
                    } else {
                        requestBillGroup.getProducts().get(1).setLineItemToken(token);
                        products.add(requestBillGroup.getProducts().get(1));
                        cachedBillingGroup.setProducts(products);
                        return cachedBillingGroup;
                    }
                } else if ( productPrice.getVarProdAddedOfType() != null
                        && productPrice.getVarProdAddedOfType().equalsIgnoreCase(MBPS) ) {
                    if ( requestBillGroup.getProducts().get(0).getVarProdAddedOfType() == null ) {
                        requestBillGroup.getProducts().get(0).setLineItemToken(token);
                        products.add(requestBillGroup.getProducts().get(0));
                        cachedBillingGroup.setProducts(products);
                        return cachedBillingGroup;
                    } else {
                        requestBillGroup.getProducts().get(1).setLineItemToken(token);
                        products.add(requestBillGroup.getProducts().get(1));
                        cachedBillingGroup.setProducts(products);
                        return cachedBillingGroup;
                    }
                }
            }
            if ( requestBillGroup.getProducts().get(0).getVarProdAddedOfType() == null ) {
                requestBillGroup.getProducts().get(0).setLineItemToken(token);
                requestBillGroup.getProducts().get(1).setLineItemToken(RandomString.generateMnpToken());
                cachedBillingGroup
                        .setVariableProductTypeUom(requestBillGroup.getProducts().get(1).getVarProdAddedOfType());
            } else {
                requestBillGroup.getProducts().get(1).setLineItemToken(token);
                requestBillGroup.getProducts().get(0).setLineItemToken(RandomString.generateMnpToken());
                cachedBillingGroup
                        .setVariableProductTypeUom(requestBillGroup.getProducts().get(0).getVarProdAddedOfType());
            }
            products.add(requestBillGroup.getProducts().get(0));
            products.add(requestBillGroup.getProducts().get(1));
            cachedBillingGroup.setProducts(products);
            return cachedBillingGroup;
        }
        cachedBillingGroup.setProducts(products);
        return cachedBillingGroup;
    }

    private String saveCartItem(PriceResponseData priceResponse, CachedShoppingCartDetails cacheShoppingBean,
            String token) {
        cacheShoppingBean.setPriceResponseData(priceResponse);
        cacheService.save(cacheShoppingBean);
        return token;
    }

    @Override
    public Boolean updateLineItem(String userEmailId, String source, ProductPriceResponse shoppingCartRequest)
            throws DataNotFoundException {
        try {
            BillingGroupPrice requestBillGroup = shoppingCartRequest.getPriceResponseData().getBillingGroupPrices()
                    .stream().findFirst().get();
            checkDataMissingInRequest(requestBillGroup);
            PriceResponseData priceResponse = shoppingCartRequest.getPriceResponseData();
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(userEmailId,
                    priceResponse.getMainCustomerId(), priceResponse.getAssociateCustomerId());
            CachedShoppingCartDetails cachedCartData = cacheService.get(cacheShoppingBean);
            PriceResponseData cachedCartPrice = cachedCartData.getPriceResponseData();
            List<BillingGroupPrice> cachedBillingGroups = cachedCartPrice.getBillingGroupPrices();
            ProductPrice requestProduct = requestBillGroup.getProducts().stream().findFirst().get();
            for ( BillingGroupPrice cachedBillingGroup : cachedBillingGroups ) {
                if ( cachedBillingGroup.getCntrperiod().equals(requestBillGroup.getCntrperiod())
                        && cachedBillingGroup.getProjectName().equals(requestBillGroup.getProjectName())
                        && cachedBillingGroup.getLocation().equals(requestBillGroup.getLocation()) ) {
                    List<ProductPrice> products = cachedBillingGroup.getProducts();
                    for ( ProductPrice product : products ) {
                        if ( source.equalsIgnoreCase(CLOUD) && product.getLineItemToken() != null
                                && product.getLineItemToken().equals(requestProduct.getLineItemToken()) ) {
                            return replaceProduct(cachedCartData, requestProduct, cachedBillingGroup, products, product,
                                    cachedBillingGroups);
                        } else if ( source.equalsIgnoreCase(MNP)
                                && product.getProductId().equals(requestProduct.getProductId())
                                && product.getLineItemToken() != null
                                && product.getLineItemToken().equals(requestProduct.getLineItemToken()) ) {
                            return replaceProduct(cachedCartData, requestProduct, cachedBillingGroup, products, product,
                                    cachedBillingGroups);
                        }
                    }
                }
            }
            for ( BillingGroupPrice cachedBillingGroup : cachedBillingGroups ) {
                List<ProductPrice> products = cachedBillingGroup.getProducts();
                for ( ProductPrice product : products ) {
                    if ( source.equalsIgnoreCase(CLOUD) && product.getLineItemToken() != null
                            && product.getLineItemToken().equals(requestProduct.getLineItemToken()) ) {
                        return removeProductAddBillgroup(cachedCartData, cachedBillingGroup, products, product,
                                cachedBillingGroups, requestBillGroup);
                    } else if ( source.equalsIgnoreCase(MNP)
                            && product.getProductId().equals(requestProduct.getProductId())
                            && product.getLineItemToken() != null
                            && product.getLineItemToken().equals(requestProduct.getLineItemToken()) ) {
                        return removeProductAddBillgroup(cachedCartData, cachedBillingGroup, products, product,
                                cachedBillingGroups, requestBillGroup);
                    }
                }
            }
            return false;
        } catch (Exception ex) {
            LOGGER.error("invalid request, could not update : ", ex);
            throw new DataNotFoundException("invalid request, could not update");
        }
    }

    private void checkDataMissingInRequest(BillingGroupPrice requestBillGroup) {
        if ( requestBillGroup.getProjectName() == null || requestBillGroup.getLocation() == null
                || requestBillGroup.getCntrperiod() == null || requestBillGroup.getEndDate() == null
                || requestBillGroup.getStartDate() == null ) {
            throw new DataNotFoundException(
                    "missing ProjectName, Location, contract period start date or end date in bill group request");
        }
        if ( requestBillGroup.getProducts().stream().findFirst().get().getServiceType() == null ) {
            throw new DataNotFoundException("service type should be new or amendment");
        }
    }

    private BillingGroupPrice isExitingBillGroup(List<BillingGroupPrice> cachedBillingGroups,
            BillingGroupPrice requestBillGroup) {
        for ( BillingGroupPrice cachedBillingGroup : cachedBillingGroups ) {
            if ( cachedBillingGroup.getCntrperiod().equals(requestBillGroup.getCntrperiod())
                    && cachedBillingGroup.getProjectName().equals(requestBillGroup.getProjectName())
                    && cachedBillingGroup.getLocation().equals(requestBillGroup.getLocation()) ) {
                return cachedBillingGroup;
            }
        }
        return null;
    }

    private Boolean removeProductAddBillgroup(CachedShoppingCartDetails cachedCartData,
            BillingGroupPrice cachedBillingGroup, List<ProductPrice> products, ProductPrice product,
            List<BillingGroupPrice> cachedBillingGroups, BillingGroupPrice requestBillGroup)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        BillingGroupPrice exitingBillGroup = isExitingBillGroup(cachedBillingGroups, requestBillGroup);

        products.remove(product);
        cachedBillingGroup.setProducts(products);
        if ( cachedBillingGroup.getProducts().size() == 0 ) {
            cachedBillingGroups.remove(cachedBillingGroup);
        } else {
            BillingGroupPrice newBillingGroupPrice = updateBillingGroupPrice(cachedCartData, cachedBillingGroup);
            cachedBillingGroups.remove(cachedBillingGroup);
            cachedBillingGroups.add(newBillingGroupPrice);
        }
        if ( exitingBillGroup != null ) {
            List<ProductPrice> existingProductPrice = exitingBillGroup.getProducts();
            existingProductPrice.add(requestBillGroup.getProducts().stream().findFirst().get());
            exitingBillGroup.setProducts(existingProductPrice);
            BillingGroupPrice newBillingGroupPrice = updateBillingGroupPrice(cachedCartData, exitingBillGroup);
            cachedBillingGroups.remove(exitingBillGroup);
            cachedBillingGroups.add(newBillingGroupPrice);
            updateCartStartDate(cachedCartData);
            return true;
        }
        cachedBillingGroups.add(requestBillGroup);
        updateCartStartDate(cachedCartData);
        return true;
    }

    private Boolean replaceProduct(CachedShoppingCartDetails cachedCartData, ProductPrice requestProduct,
            BillingGroupPrice cachedBillingGroup, List<ProductPrice> products, ProductPrice product,
            List<BillingGroupPrice> cachedBillingGroups) throws IOException {
        products.remove(product);
        products.add(requestProduct);
        cachedBillingGroup.setProducts(products);
        BillingGroupPrice newBillingGroupPrice = updateBillingGroupPrice(cachedCartData, cachedBillingGroup);
        cachedBillingGroups.remove(cachedBillingGroup);
        cachedBillingGroups.add(newBillingGroupPrice);
        updateCartStartDate(cachedCartData);
        return true;
    }

    @Override
    public Boolean deleteLineItem(DeleteLineItemRequest deleteRequest) throws DataNotFoundException {
        try {
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(deleteRequest.getUserEmailId(),
                    deleteRequest.getMainCustomerId(), deleteRequest.getAssociateCustomerId());
            CachedShoppingCartDetails cachedCartData = cacheService.get(cacheShoppingBean);
            PriceResponseData cachedCart = cachedCartData.getPriceResponseData();
            List<BillingGroupPrice> cachedBillingGroups = cachedCart.getBillingGroupPrices();
            for ( BillingGroupPrice cachedBillingGroup : cachedBillingGroups ) {
                if ( cachedBillingGroup.getCntrperiod().equals(deleteRequest.getContractPeriod())
                        && cachedBillingGroup.getProjectName().equals(deleteRequest.getProjectName())
                        && cachedBillingGroup.getLocation().equals(deleteRequest.getLocation()) ) {
                    List<ProductPrice> products = cachedBillingGroup.getProducts();
                    for ( ProductPrice product : products ) {
                        if ( deleteRequest.getLineItemToken() != null && deleteRequest.getLineItemToken().contains(MNP)
                                && product.getLineItemToken().equals(deleteRequest.getLineItemToken()) ) {
                            if ( product.getProductId().equals(deleteRequest.getProductId()) ) {
                                return removeProduct(cachedCartData, cachedBillingGroup, products, product,
                                        cachedBillingGroups);
                            }
                        } else if ( product.getProductId().equals(deleteRequest.getProductId())
                                && product.getLineItemToken() != null
                                && product.getLineItemToken().equals(deleteRequest.getLineItemToken()) ) {
                            DeleteLineItemCloudResponse cloudResponse = ssoService
                                    .deleteLineItemFromCloud(deleteRequest.getLineItemToken());
                            if ( cloudResponse.getResponse().getStatus().getHttpCode() == 200 ) {
                                return removeProduct(cachedCartData, cachedBillingGroup, products, product,
                                        cachedBillingGroups);
                            } else {
                                throw new ServiceUnavailableException("Error occurred in deleting the product");
                            }
                        }
                    }
                }
            }
            return false;
        } catch (Exception ex) {
            LOGGER.error("No cart item associated with request line item : ", ex);
            throw new DataNotFoundException("unable to process : " + ex.getMessage());
        }
    }

    private Boolean removeProduct(CachedShoppingCartDetails cachedCartData, BillingGroupPrice cachedBillingGroup,
            List<ProductPrice> products, ProductPrice product, List<BillingGroupPrice> cachedBillingGroups)
            throws IOException {
        products.remove(product);
        cachedBillingGroup.setProducts(products);
        if ( cachedBillingGroup.getProducts().size() == 0 ) {
            cachedBillingGroups.remove(cachedBillingGroup);
        } else {
            BillingGroupPrice newBillingGroupPrice = updateBillingGroupPrice(cachedCartData, cachedBillingGroup);
            cachedBillingGroups.remove(cachedBillingGroup);
            cachedBillingGroups.add(newBillingGroupPrice);
        }
        updateCartStartDate(cachedCartData);
        return true;
    }

    private BillingGroupPrice updateBillingGroupPrice(CachedShoppingCartDetails cachedCartData,
            BillingGroupPrice cachedBillingGroup)
            throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
        String billingGroupPrice = mapper.writeValueAsString(cachedBillingGroup);
        BillingGroup billingGroup = mapper.readValue(billingGroupPrice, BillingGroup.class);
        List<BillingGroup> billingGroups = new ArrayList<BillingGroup>();
        billingGroups.add(billingGroup);
        ProductPriceRequest productPriceRequest = new ProductPriceRequest(cachedCartData.getBillToCustomer(),
                cachedCartData.getSupportToCustomer(), billingGroups);
        ProductPriceResponse priceResponse = getProductPricing(DISPLAY, productPriceRequest);
        return priceResponse.getPriceResponseData().getBillingGroupPrices().stream().findFirst().get();
    }

    public PriceResponseData getShoppingCartProducts(String userEmailId, String mainCustomerId,
            String associateCustomerId) throws CacheServiceException {
        try {
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(userEmailId, mainCustomerId,
                    associateCustomerId);
            CachedShoppingCartDetails cachedCartData = cacheService.get(cacheShoppingBean);
            if ( cachedCartData == null ) {
                throw new DataNotFoundException("Empty Cart for the requested combination");
            }
            return cachedCartData.getPriceResponseData();
        } catch (CacheServiceException ex) {
            LOGGER.error("Error in processing POST call", ex);
            throw new ServiceUnavailableException("I/O error on POST request for Cart Product List URI");
        }
    }

    @Override
    public Boolean paidByCreditsOrEBS(CreateSofRequest createSofRequest, String mainCustomerId,
            String transactionNumber, String merchantRefNumber, String paymentSource)
            throws ServiceUnavailableException, DuplicateDataException, IOException {
        if ( paymentSource.equalsIgnoreCase(EBS) ) {
            return paymentService.saveTransactionSofRequests(createSofRequest, mainCustomerId, transactionNumber,
                    merchantRefNumber, PaymentType.EBS.getPaymentId());
        } else if ( paymentSource.equalsIgnoreCase(CREDITS) ) {
            return paymentService.saveTransactionSofRequests(createSofRequest, mainCustomerId, transactionNumber,
                    merchantRefNumber, PaymentType.CREDIT.getPaymentId());
        } else {
            throw new ServiceUnavailableException("invalid payment source type : " + paymentSource);
        }
    }

    @Override
    public List<SofTransactionDetails> getSofsByMerchantRefNumber(String merchantRefNumber)
            throws DataNotFoundException, ServiceUnavailableException, IOException {
        try {
            List<SofTransactionDetails> transactionSofDetails = new ArrayList<>();
            MasterUserTransactionEntity masterUser = masterUserTransactionDao
                    .findByMerchantRefNumberAndPaymentStatus(merchantRefNumber, true);
            if ( masterUser == null ) {
                throw new DataNotFoundException("No data found with respect to this Transaction Id ");
            }
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations = masterUser
                    .getSofTransactionPaymentConfigurations();
            for ( SofTransactionPaymentConfigurationEntity sofPaymentConf : sofTransactionPaymentConfigurations ) {
                if ( sofPaymentConf.getSofTransactionStatus() ) {
                    SofTransactionDetails transactionSofDetail = getsofTransactionDetails(sofPaymentConf);
                    transactionSofDetails.add(transactionSofDetail);
                }
            }
            return transactionSofDetails;
        } catch (DataNotFoundException ex) {
            LOGGER.error("No data found with respect to this Transaction Id :", ex);
            throw new DataNotFoundException("No data found with respect to this Transaction Id ");
        } catch (Exception ex) {
            LOGGER.error("Error in mapping data  :", ex);
            throw new ServiceUnavailableException("Error in mapping data  ");
        }

    }

    private SofTransactionDetails getsofTransactionDetails(SofTransactionPaymentConfigurationEntity sofPaymentConf)
            throws JsonParseException, JsonMappingException, IOException {
        SofResponse transactionSofResponse = mapper.readValue(sofPaymentConf.getSofTransactionSofNumber(),
                SofResponse.class);
        CreateSofRequest sofRequest = mapper.readValue(sofPaymentConf.getSofTransactionrRquestBody(),
                CreateSofRequest.class);
        String sofNo = transactionSofResponse.getSofLists().stream().findFirst().get().getSofs().stream().findFirst()
                .get();
        String projectName = sofRequest.getBillingGroupPrices().stream().findFirst().get().getProjectName();
        String contractPeriod = sofRequest.getBillingGroupPrices().stream().findFirst().get().getCntrperiod();
        String serviceType = sofRequest.getBillingGroupPrices().stream().findFirst().get().getProducts().stream()
                .findFirst().get().getServiceType();
        return new SofTransactionDetails(sofNo, projectName, contractPeriod, serviceType);
    }

    @Override
    public Boolean saveCart(String userEmailId, ProductPriceResponse shoppingCartRequest, Long mainCustomerId,
            Long associateCustomerId) throws ServiceUnavailableException {
        try {
            BillingGroupPrice requestBillGroup = shoppingCartRequest.getPriceResponseData().getBillingGroupPrices()
                    .stream().findFirst().get();
            checkDataMissingInRequest(requestBillGroup);
            PriceResponseData priceResponse = shoppingCartRequest.getPriceResponseData();
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(userEmailId,
                    priceResponse.getMainCustomerId(), priceResponse.getAssociateCustomerId());
            cacheShoppingBean.setPriceResponseData(priceResponse);
            updateCartStartDate(cacheShoppingBean);
            return true;
        } catch (Exception ex) {
            LOGGER.error("Error in processing save cart : ", ex);
            throw new ServiceUnavailableException("Error in processing save cart");
        }
    }

    @Override
    public Boolean isValidLineItemToken(String mainCustomerId, String associastedCustomerId, String lineItemToken) {
        try {
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails();
            Map<Object, Object> shoppingBeanMap = cacheService.readAllHash(cacheShoppingBean.getObjectKey());
            for ( Object cacheKey : shoppingBeanMap.keySet() ) {
                CachedShoppingCartDetails responseData = cacheService
                        .get((CachedShoppingCartDetails) shoppingBeanMap.get(cacheKey));
                PriceResponseData cachedPriceData = responseData.getPriceResponseData();
                if ( cachedPriceData.getMainCustomerId().equals(mainCustomerId)
                        && cachedPriceData.getAssociateCustomerId().equals(associastedCustomerId) ) {
                    for ( BillingGroupPrice cachedBillgropPrice : cachedPriceData.getBillingGroupPrices() ) {
                        for ( ProductPrice cachedPrice : cachedBillgropPrice.getProducts() ) {
                            if ( cachedPrice.getLineItemToken() != null
                                    && cachedPrice.getLineItemToken().equals(lineItemToken) ) {
                                return true;
                            }
                        }
                    }

                }
            }
            return false;
        } catch (Exception ex) {
            LOGGER.error("No cart item associated with request line item : ", ex);
            throw new DataNotFoundException("No cart item associated with request bill group item");
        }
    }

    @Override
    public Boolean isValidInstanceId(String mainCustomerId, String associastedCustomerId, String instanceId) {
        try {
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails();
            Map<Object, Object> shoppingBeanMap = cacheService.readAllHash(cacheShoppingBean.getObjectKey());
            for ( Object cacheKey : shoppingBeanMap.keySet() ) {
                CachedShoppingCartDetails responseData = cacheService
                        .get((CachedShoppingCartDetails) shoppingBeanMap.get(cacheKey));
                PriceResponseData cachedPriceData = responseData.getPriceResponseData();
                if ( cachedPriceData.getMainCustomerId().equals(mainCustomerId)
                        && cachedPriceData.getAssociateCustomerId().equals(associastedCustomerId) ) {
                    for ( BillingGroupPrice cachedBillgropPrice : cachedPriceData.getBillingGroupPrices() ) {
                        for ( ProductPrice cachedPrice : cachedBillgropPrice.getProducts() ) {
                            if ( cachedPrice.getInstanceId() != null
                                    && cachedPrice.getInstanceId().equals(instanceId) ) {
                                return true;
                            }
                        }
                    }

                }
            }
            return false;
        } catch (Exception ex) {
            LOGGER.error("No cart item associated with request line item : ", ex);
            throw new DataNotFoundException("No cart item associated with request bill group item");
        }
    }

    @Override
    public void updateCartStartDate(CachedShoppingCartDetails cachedShoppingCartDetails) {
        for ( BillingGroupPrice billingGroupPrice : cachedShoppingCartDetails.getPriceResponseData()
                .getBillingGroupPrices() ) {
            billingGroupPrice.setStartDate(DateUtils.getStartDate());
            for ( ProductPrice productPrice : billingGroupPrice.getProducts() ) {
                if ( productPrice.getServiceType().equalsIgnoreCase(AMENDMENT) ) {
                    productPrice.setStartDate(DateUtils.getStartDate());
                } else if ( productPrice.getServiceType().equalsIgnoreCase(NEW) ) {
                    productPrice.setStartDate(DateUtils.getStartDate());
                    productPrice.setEndDate(DateUtils.getEndDate(billingGroupPrice.getCntrperiod()));
                }
            }
        }
        cacheService.save(cachedShoppingCartDetails, 30, TimeUnit.DAYS);
    }

    @Override
    public BillingGroupResponse getVariableUnitInCart(String mainCustomerId, String associateCustomerId,
            String projectName) {
        CachedShoppingCartDetails cachedShoppingCartDetails = new CachedShoppingCartDetails(
                authUser.getAuthentedEmailId(), mainCustomerId, associateCustomerId);
        CachedShoppingCartDetails cartDetails = cacheService.get(cachedShoppingCartDetails);
        BillingGroupResponse billingGroupResponse = new BillingGroupResponse();
        if ( cartDetails != null && cartDetails.getPriceResponseData() != null ) {
            for ( BillingGroupPrice billingGroupPrice : cartDetails.getPriceResponseData().getBillingGroupPrices() ) {
                if ( billingGroupPrice.getProjectName().equalsIgnoreCase(projectName) ) {
                    for ( ProductPrice productPrice : billingGroupPrice.getProducts() ) {
                        if ( productPrice.getIsVariableProduct().equalsIgnoreCase(YES) ) {
                            if ( productPrice.getVariableProductUOM().equalsIgnoreCase(GB) ) {
                                billingGroupResponse.setUnitOfMeasurment(GB);
                                return billingGroupResponse;
                            } else if ( productPrice.getVariableProductUOM().equalsIgnoreCase(MBPS) ) {
                                billingGroupResponse.setUnitOfMeasurment(MBPS);
                                if ( productPrice.getIsCappedProduct().equalsIgnoreCase(NO) ) {
                                    billingGroupResponse.setRateBasedType(BURSTABLE);
                                    return billingGroupResponse;
                                } else if ( productPrice.getIsCappedProduct().equalsIgnoreCase(YES) ) {
                                    billingGroupResponse.setRateBasedType(CAPPED);
                                    return billingGroupResponse;
                                }
                            }
                        }
                    }
                    billingGroupResponse.setAdditionalProperties(ERROR_MSG, NO_BILLING_GROUP);
                }
            }
        } else {
            billingGroupResponse.setAdditionalProperties(ERROR_MSG, NO_BILLING_GROUP);
        }
        return billingGroupResponse;
    }

    @Override
    public Boolean createLineItemSof(String merchantRefNumber) throws ServiceUnavailableException, JsonParseException,
            JsonMappingException, JsonProcessingException, IOException {
        return paymentService.createLineItemSof(merchantRefNumber);
    }

}
