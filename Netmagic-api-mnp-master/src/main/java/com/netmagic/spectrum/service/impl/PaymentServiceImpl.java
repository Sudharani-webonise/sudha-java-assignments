package com.netmagic.spectrum.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.cache.service.CacheService;
import com.netmagic.spectrum.commons.AuthUser;
import com.netmagic.spectrum.commons.BusinessUnit;
import com.netmagic.spectrum.commons.PaymentFor;
import com.netmagic.spectrum.commons.PaymentType;
import com.netmagic.spectrum.commons.ResourceHelper;
import com.netmagic.spectrum.commons.SofType;
import com.netmagic.spectrum.commons.SourceType;
import com.netmagic.spectrum.commons.Status;
import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dao.SofTransactionPaymentConfigurationDao;
import com.netmagic.spectrum.dto.outstanding.request.Invoice;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.OnAccountRequestWrapper;
import com.netmagic.spectrum.dto.outstanding.request.PaymentRequestDetails;
import com.netmagic.spectrum.dto.outstanding.response.OnAccountPaymentResponseWrapper;
import com.netmagic.spectrum.dto.outstanding.response.OnlineCreditResponse;
import com.netmagic.spectrum.dto.shoppingcart.request.BillingGroup;
import com.netmagic.spectrum.dto.shoppingcart.request.CachedShoppingCartDetails;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.request.ProductPriceRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.BillingGroupPrice;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPrice;
import com.netmagic.spectrum.dto.shoppingcart.response.ProductPriceResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SofResponse;
import com.netmagic.spectrum.dto.shoppingcart.response.SplitAccountPrice;
import com.netmagic.spectrum.entity.InvoiceTransactionDetailsEntity;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.entity.OnAccountTransactionDetailsEntity;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;
import com.netmagic.spectrum.entity.UserTransactionDetailsEntity;
import com.netmagic.spectrum.exception.DuplicateDataException;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.exception.UnProcessableException;
import com.netmagic.spectrum.service.OutstandingService;
import com.netmagic.spectrum.service.PaymentService;
import com.netmagic.spectrum.service.ShoppingCartService;
import com.netmagic.spectrum.utils.DateUtils;
import com.netmagic.spectrum.utils.RandomString;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private static final String CALCULATE = "calculate";

    private static final String SCF_ACTION = "lineitem_level_actions";

    private static final String EBS = "ebs";

    private static final String NEW = "new";

    private static final String AMENDMENT = "amendment";

    private static final String DISPLAY = "display";

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthUser authUser;

    @Autowired
    private OutstandingService outstandingService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private MasterUserTransactionDao masterUserTransactionDao;

    @Autowired
    private SofTransactionPaymentConfigurationDao sofTransactionPaymentConfigurationDao;

    @Autowired
    private CacheService<CachedShoppingCartDetails> cacheService;

    @Override
    public boolean saveTransactionSofRequests(CreateSofRequest createSofRequest, String mainCustomerId,
            String transactionNumber, String merchantRefNumber, long paymentId)
            throws ServiceUnavailableException, DuplicateDataException, IOException {
        try {
            checkBillGroupAndProductListEmptyOrNull(createSofRequest);
            checkDuplicateMerchantRefNumber(merchantRefNumber);
            double totalAmount = calculateTotalAmount(createSofRequest);
            if ( paymentId == PaymentType.CREDIT.getPaymentId() ) {
                verifyCreditLimit(createSofRequest, Long.parseLong(mainCustomerId), totalAmount);
            }
            MasterUserTransactionEntity masterUser = splitNewAndAmendmentSofRequest(createSofRequest, transactionNumber,
                    merchantRefNumber, paymentId, totalAmount);

            UserTransactionDetailsEntity userTransactionDetails = new UserTransactionDetailsEntity();
            userTransactionDetails.setOtherSourceId(SourceType.PEOPLESOFT.getSourceId());
            userTransactionDetails.setMasterUserTransaction(masterUser);
            masterUser.setUserTransactionDetailsEntity(userTransactionDetails);
            masterUserTransactionDao.save(masterUser);
            CachedShoppingCartDetails cacheShoppingBean = new CachedShoppingCartDetails(authUser.getAuthentedEmailId(),
                    createSofRequest.getBillToCustomerId(), createSofRequest.getSupportToCustomer());
            cacheService.delete(cacheShoppingBean);
            // createLineItemSof(merchantRefNumber);
            return true;
        } catch (DuplicateDataException ex) {
            LOGGER.error("Duplicate data : ", ex);
            throw new DuplicateDataException(ex.getMessage());
        } catch (UnProcessableException ex) {
            LOGGER.error("online credit limit is not sufficient", ex);
            throw new UnProcessableException("online credit limit is not sufficient ");
        } catch (ServiceUnavailableException ex) {
            LOGGER.error("Error in processing POST call : ", ex);
            throw new ServiceUnavailableException(ex.getMessage());
        } catch (Exception ex) {
            LOGGER.error("Error in json : ", ex);
            throw new ServiceUnavailableException(ex.getMessage());
        }
    }

    private MasterUserTransactionEntity splitNewAndAmendmentSofRequest(CreateSofRequest createSofRequest,
            String transactionNumber, String merchantRefNumber, long paymentId, double totalAmount)
            throws IOException, JsonProcessingException {
        MasterUserTransactionEntity masterUser = new MasterUserTransactionEntity();
        List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations = new ArrayList<>();
        CreateSofRequest tempCreateSofRequest = new CreateSofRequest(createSofRequest.getToken(),
                createSofRequest.getBillToCustomerId(), createSofRequest.getSupportToCustomer());
        for ( BillingGroupPrice billingGroupPrice : createSofRequest.getBillingGroupPrices() ) {
            List<ProductPrice> nmitProductPriceList = billingGroupPrice.getProducts().stream()
                    .filter(productPrice -> productPrice.getServiceType().equalsIgnoreCase(SofType.NEW.getSofType())
                            && productPrice.getType().equalsIgnoreCase(BusinessUnit.NMIT.getType()))
                    .collect(Collectors.toList());
            List<ProductPrice> nmsplProductPriceList = billingGroupPrice.getProducts().stream()
                    .filter(productPrice -> productPrice.getServiceType().equalsIgnoreCase(SofType.NEW.getSofType())
                            && productPrice.getType().equalsIgnoreCase(BusinessUnit.NSPL.getType()))
                    .collect(Collectors.toList());
            List<ProductPrice> amendmentProductPriceList1 = billingGroupPrice.getProducts().stream().filter(
                    productPrice -> productPrice.getServiceType().equalsIgnoreCase(SofType.AMENDMENT.getSofType()))
                    .collect(Collectors.toList());

            if ( !nmitProductPriceList.isEmpty() ) {
                addRequestForNmitOrNmsplProduct(createSofRequest, masterUser, sofTransactionPaymentConfigurations,
                        tempCreateSofRequest, billingGroupPrice, nmitProductPriceList);
            }

            if ( !nmsplProductPriceList.isEmpty() ) {
                addRequestForNmitOrNmsplProduct(createSofRequest, masterUser, sofTransactionPaymentConfigurations,
                        tempCreateSofRequest, billingGroupPrice, nmsplProductPriceList);
            }

            if ( !amendmentProductPriceList1.isEmpty() ) {
                for ( ProductPrice productPrice : amendmentProductPriceList1 ) {
                    List<ProductPrice> amendmentProductPriceList = new ArrayList<>();
                    billingGroupPrice.setProducts(amendmentProductPriceList);
                    amendmentProductPriceList.add(productPrice);
                    List<BillingGroupPrice> amendmentBillingGroupPriceList = new ArrayList<>();
                    amendmentBillingGroupPriceList
                            .add(getPriceForSofRequest(createSofRequest, billingGroupPrice, AMENDMENT));
                    tempCreateSofRequest.setBillingGroupPrices(amendmentBillingGroupPriceList);
                    tempCreateSofRequest.getBillingGroupPrices().get(0).setScfAction(SCF_ACTION);
                    addSofTransactionPaymentConfigurationEntityInList(masterUser, sofTransactionPaymentConfigurations,
                            tempCreateSofRequest, SofType.AMENDMENT.getSofType());
                }
            }
            // set basic details on user transaction table
            setAndSaveIntoDBForMasterSofUserTransactionTable(createSofRequest, transactionNumber, merchantRefNumber,
                    masterUser, sofTransactionPaymentConfigurations, totalAmount, paymentId);
        }
        return masterUser;
    }

    private void addRequestForNmitOrNmsplProduct(CreateSofRequest createSofRequest,
            MasterUserTransactionEntity masterUser,
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations,
            CreateSofRequest tempCreateSofRequest, BillingGroupPrice billingGroupPrice,
            List<ProductPrice> newProductPriceList) throws IOException, JsonProcessingException {
        billingGroupPrice.setProducts(newProductPriceList);
        List<BillingGroupPrice> newBillingGroupPriceList = new ArrayList<>();
        newBillingGroupPriceList.add(getPriceForSofRequest(createSofRequest, billingGroupPrice, NEW));
        tempCreateSofRequest.setBillingGroupPrices(newBillingGroupPriceList);
        addSofTransactionPaymentConfigurationEntityInList(masterUser, sofTransactionPaymentConfigurations,
                tempCreateSofRequest, SofType.NEW.getSofType());
        masterUser.setSofTransactionPaymentConfigurations(sofTransactionPaymentConfigurations);
    }

    private void checkBillGroupAndProductListEmptyOrNull(CreateSofRequest createSofRequest) {
        if ( createSofRequest.getBillingGroupPrices() == null || createSofRequest.getBillingGroupPrices().isEmpty() ) {
            throw new ServiceUnavailableException("Their is no billing group in request");
        }
        for ( BillingGroupPrice billingGroupPrice : createSofRequest.getBillingGroupPrices() ) {
            if ( billingGroupPrice.getProducts() == null || billingGroupPrice.getProducts().isEmpty() ) {
                throw new ServiceUnavailableException("Their is no products in billing group.");
            }
        }
    }

    private void checkDuplicateMerchantRefNumber(String merchantRefNumber) {
        if ( masterUserTransactionDao.findByMerchantRefNumber(merchantRefNumber) != null ) {
            throw new DuplicateDataException("Data Redundancy, Regerence number is already exists.");
        }
    }

    private double calculateTotalAmount(CreateSofRequest createSofRequest) {
        Double totalCostWithTax = 0.0;
        for ( BillingGroupPrice billingGroupPrice : createSofRequest.getBillingGroupPrices() ) {
            totalCostWithTax += billingGroupPrice.getTotalMonthlyRecurringCostWithTax()
                    + billingGroupPrice.getTotalOneTimeCostWithTax();
        }
        return totalCostWithTax;
    }

    private boolean verifyCreditLimit(CreateSofRequest createSofRequest, Long mainCustomerId, double totalCostWithTax)
            throws ServiceUnavailableException, UnProcessableException {
        try {
            OnlineCreditResponse onlineCreditResponse = outstandingService.getOnlineCreditLimit(mainCustomerId);
            Double onlineCreditLimit = onlineCreditResponse.getOnlineCreditMsgdata().getCreditLimitTrasaction()
                    .getOnlineCredit().getOnlineCreditLimit();
            if ( onlineCreditLimit < totalCostWithTax ) {
                throw new UnProcessableException("online credit limit is not sufficient ");
            }
            return true;
        } catch (ServiceUnavailableException ex) {
            LOGGER.error("Error in processing GET call : ", ex);
            throw new ServiceUnavailableException("unable to fetch online credit notes ");
        }
    }

    private BillingGroupPrice getPriceForSofRequest(CreateSofRequest createSofRequest,
            BillingGroupPrice sofRequestBillingGroupPrice, String serviceType) throws IOException {
        if ( serviceType.equalsIgnoreCase(NEW) ) {
            sofRequestBillingGroupPrice.setStartDate(DateUtils.getStartDate());
            sofRequestBillingGroupPrice.setEndDate(DateUtils.getEndDate(sofRequestBillingGroupPrice.getCntrperiod()));
        } else if ( serviceType.equalsIgnoreCase(AMENDMENT) ) {
            sofRequestBillingGroupPrice.setStartDate(DateUtils.getStartDate());
            sofRequestBillingGroupPrice.setEndDate(sofRequestBillingGroupPrice.getProducts().get(0).getEndDate());
        }
        String billingGroupPrice = mapper.writeValueAsString(sofRequestBillingGroupPrice);
        BillingGroup billingGroup = mapper.readValue(billingGroupPrice, BillingGroup.class);
        List<BillingGroup> billingGroups = new ArrayList<BillingGroup>();
        billingGroups.add(billingGroup);
        ProductPriceRequest productPriceRequest = new ProductPriceRequest(createSofRequest.getBillToCustomerId(),
                createSofRequest.getSupportToCustomer(), billingGroups);
        ProductPriceResponse priceResponse = shoppingCartService.getProductPricing(CALCULATE, productPriceRequest);
        return priceResponse.getPriceResponseData().getBillingGroupPrices().stream().findFirst().get();
    }

    // adding tempCreateSofRequest into sofTransactionPaymentConfigurations
    private void addSofTransactionPaymentConfigurationEntityInList(MasterUserTransactionEntity masterUser,
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations,
            CreateSofRequest tempCreateSofRequest, String sofType) throws JsonProcessingException {
        String sofTokenId = RandomString.generateSofToken();
        tempCreateSofRequest.setSofTokenId(sofTokenId);
        SofTransactionPaymentConfigurationEntity sofTransactionPayment = new SofTransactionPaymentConfigurationEntity();
        sofTransactionPayment.setSofMasterUserTransaction(masterUser);
        sofTransactionPayment.setSofTransactionTockenId(sofTokenId);
        sofTransactionPayment.setSofTransactionrRquestBody(ResourceHelper.objectAsJsonString(tempCreateSofRequest));
        sofTransactionPayment.setSofTransactionSofType(sofType);
        sofTransactionPaymentConfigurations.add(sofTransactionPayment);
    }

    private void setAndSaveIntoDBForMasterSofUserTransactionTable(CreateSofRequest createSofRequest,
            String transactionNumber, String merchantRefNumber, MasterUserTransactionEntity masterUser,
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations, double totalAmount,
            long paymentId) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        try {
            masterUser.setUserEmail(authUser.getAuthentedEmailId());
            masterUser.setTransactionNumber(transactionNumber);
            masterUser.setMerchantRefNumber(merchantRefNumber);
            masterUser.setTotalAmount(totalAmount);
            masterUser.setBillToCustomerId(createSofRequest.getBillToCustomerId());
            masterUser.setSupportCustomerId(createSofRequest.getSupportToCustomer());
            SplitAccountPrice splitAccountPrice = getInvoiceTotalAmount(authUser.getAuthentedEmailId(),
                    createSofRequest.getBillToCustomerId(), createSofRequest.getSupportToCustomer());
            masterUser.setNmitTotal(splitAccountPrice.getTotalNmit());
            masterUser.setUserTransactionNmitUpdatedTime(new Timestamp(System.currentTimeMillis()));
            masterUser.setNsplTotal(splitAccountPrice.getTotalNmspl());
            masterUser.setUserTransactionNsplUpdatedTime(new Timestamp(System.currentTimeMillis()));
            masterUser.setMnpUser(authUser.isExistingUser());
            masterUser.setPaymentTypeId(paymentId);
            masterUser.setPaymentForId(PaymentFor.SOF.getPaymentId());
            masterUser.setSofTransactionPaymentConfigurations(sofTransactionPaymentConfigurations);
            masterUser.setTransactionStatus(true);
            masterUserTransactionDao.saveAndFlush(masterUser);
        } catch (ServiceUnavailableException ex) {
            LOGGER.error("Error in processing GET call : ", ex);
            throw new ServiceUnavailableException("error in savind data into db request agian.");
        }

    }

    public Boolean createLineItemSof(String merchantRefNumber)
            throws JsonParseException, JsonMappingException, IOException {
        MasterUserTransactionEntity masterUser = masterUserTransactionDao
                .findByMerchantRefNumberAndPaymentStatus(merchantRefNumber, true);
        List<SofTransactionPaymentConfigurationEntity> sofPaymentConfigurations = masterUser
                .getSofTransactionPaymentConfigurations();
        for ( SofTransactionPaymentConfigurationEntity sofPaymentConf : sofPaymentConfigurations ) {
            if ( !sofPaymentConf.getSofTransactionStatus() ) {
                CreateSofRequest createSofRequest = new ObjectMapper()
                        .readValue(sofPaymentConf.getSofTransactionrRquestBody(), CreateSofRequest.class);
                SofResponse response = shoppingCartService.createSof(createSofRequest,
                        sofPaymentConf.getSofTransactionSofType());
                if ( response.getSofLists().stream().findFirst().get().getSofs().get(0) != null ) {
                    sofPaymentConf.setSofTransactionStatus(true);
                    response.setStatus(Status.SUCCESS.getStatus());
                }
                sofPaymentConf.setSofTransactionSofNumber(ResourceHelper.objectAsJsonString(response));
                sofTransactionPaymentConfigurationDao.save(sofPaymentConf);
            }
        }
        return true;
    }

    @Override
    public boolean saveMasterUserTransactionDetailsOnAccount(OnAccountPaymentRequest onAccountPaymentRequest)
            throws IOException {
        try {
            checkDuplicateMerchantRefNumber(onAccountPaymentRequest.getMerchantReferenceNumber());
            MasterUserTransactionEntity masterUser = new MasterUserTransactionEntity();
            masterUser.setUserEmail(authUser.getAuthentedEmailId());
            masterUser.setTransactionNumber(onAccountPaymentRequest.getTransactionNumber());
            masterUser.setMerchantRefNumber(onAccountPaymentRequest.getMerchantReferenceNumber());
            masterUser.setTotalAmount(onAccountPaymentRequest.getAmount());
            masterUser.setBillToCustomerId(onAccountPaymentRequest.getMainCustomerId());
            masterUser.setSupportCustomerId(onAccountPaymentRequest.getMainCustomerId());
            if ( onAccountPaymentRequest.getBusinessUnit().equalsIgnoreCase(BusinessUnit.NMIT.getType()) ) {
                masterUser.setNmitTotal(onAccountPaymentRequest.getAmount());
                masterUser.setUserTransactionNmitUpdatedTime(DateUtils.getTimestamp());
            } else if ( onAccountPaymentRequest.getBusinessUnit().equalsIgnoreCase(BusinessUnit.NSPL.getType()) ) {
                masterUser.setNsplTotal(onAccountPaymentRequest.getAmount());
                masterUser.setUserTransactionNsplUpdatedTime(DateUtils.getTimestamp());
            }
            masterUser.setMnpUser(authUser.isExistingUser());
            masterUser.setPaymentTypeId(PaymentType.EBS.getPaymentId());
            masterUser.setPaymentForId(PaymentFor.ON_ACCOUNT.getPaymentId());
            masterUser.setTransactionStatus(true);
            masterUserTransactionDao.save(masterUser);
            OnAccountPaymentResponseWrapper onAccountPaymentResponse = outstandingService
                    .sendOnAccountRequest(new OnAccountRequestWrapper(onAccountPaymentRequest));
            OnAccountTransactionDetailsEntity onAccountTransactionDetailsEntity = new OnAccountTransactionDetailsEntity();
            if ( onAccountPaymentResponse.getOnAccountPaymentResponse().getStatus()
                    .equalsIgnoreCase(Status.SUCCESS.getStatus()) ) {
                onAccountTransactionDetailsEntity.setOnAccountTransactionStatus(true);
                onAccountTransactionDetailsEntity.setOnAccountMasterUserTransaction(masterUser);
                masterUser.setOnAccountTransactionDetailsEntity(onAccountTransactionDetailsEntity);
                masterUserTransactionDao.saveAndFlush(masterUser);
                return true;
            } else {
                throw new DuplicateDataException("Paid for this transaction id");
            }
        } catch (DuplicateDataException ex) {
            LOGGER.error("Duplicate data : ", ex);
            throw new DuplicateDataException(ex.getMessage());
        } catch (ServiceUnavailableException ex) {
            LOGGER.error("Error in processing GET call : ", ex);
            throw new ServiceUnavailableException("error in savind data into db request agian.");
        }

    }

    @Override
    public boolean saveInvoiceDetails(PaymentRequestDetails invoicePaymentRequest, String merchantRefNumber,
            String paymentSource) throws DuplicateDataException, ServiceUnavailableException, IOException {
        try {
            checkDuplicateMerchantRefNumber(merchantRefNumber);

            double totalInvoiceAmount = getInvoiceTotalAmount(invoicePaymentRequest.getInvoiceList());
            MasterUserTransactionEntity masterUser = new MasterUserTransactionEntity();
            masterUser.setUserEmail(authUser.getAuthentedEmailId());
            masterUser.setTransactionNumber(invoicePaymentRequest.getTransactionId());
            masterUser.setMerchantRefNumber(merchantRefNumber);
            masterUser.setTotalAmount(totalInvoiceAmount);
            masterUser.setBillToCustomerId(invoicePaymentRequest.getBillToCustomerId().toString());
            masterUser.setSupportCustomerId(invoicePaymentRequest.getSupportToCustomerId().toString());
            if ( invoicePaymentRequest.getAccountType().equalsIgnoreCase(BusinessUnit.NMITS.getType()) ) {
                masterUser.setNmitTotal(totalInvoiceAmount);
                masterUser.setUserTransactionNmitUpdatedTime(DateUtils.getTimestamp());
            } else if ( invoicePaymentRequest.getAccountType().equalsIgnoreCase(BusinessUnit.NMSPL.getType()) ) {
                masterUser.setNsplTotal(totalInvoiceAmount);
                masterUser.setUserTransactionNsplUpdatedTime(DateUtils.getTimestamp());
            }
            masterUser.setMnpUser(authUser.isExistingUser());
            if ( paymentSource.equalsIgnoreCase(EBS) ) {
                masterUser.setPaymentTypeId(PaymentType.EBS.getPaymentId());
            } else {
                masterUser.setPaymentTypeId(PaymentType.CREDIT.getPaymentId());
            }
            masterUser.setPaymentForId(PaymentFor.INVOICE.getPaymentId());
            masterUser.setTransactionStatus(true);
            masterUserTransactionDao.save(masterUser);

            List<InvoiceTransactionDetailsEntity> invoiceEntityList = new ArrayList<>();
            for ( Invoice invoice : invoicePaymentRequest.getInvoiceList() ) {
                InvoiceTransactionDetailsEntity invoiceEntity = new InvoiceTransactionDetailsEntity();
                invoiceEntity.setInvoiceAmount(invoice.getAmountOutstanding());
                invoiceEntity.setInvoiceTransactionStatus(true);
                invoiceEntity.setInvoiceTdsAmount(invoice.getTdsAmount());
                invoiceEntity.setInvoiceTransactionNumber(ResourceHelper.objectAsJsonString(invoice));
                invoiceEntity.setInvoiceOutstandingAmount(invoice.getAmountOutstanding());
                invoiceEntity.setInvoiceMasterUserTransaction(masterUser);
                invoiceEntityList.add(invoiceEntity);
            }
            masterUser.setInvoiceTransactionDetailsEntity(invoiceEntityList);
            masterUserTransactionDao.saveAndFlush(masterUser);
            return true;
        } catch (

        DuplicateDataException ex) {
            LOGGER.error("Duplicate data : ", ex);
            throw new DuplicateDataException(ex.getMessage());
        } catch (ServiceUnavailableException ex) {
            LOGGER.error("Error in processing GET call : ", ex);
            throw new ServiceUnavailableException("error in savind data into db request agian.");
        }
    }

    private double getInvoiceTotalAmount(List<Invoice> invoiceList) {
        double totalAmount = 0.0;
        for ( Invoice invoice : invoiceList ) {
            totalAmount += invoice.getAmountOutstanding();
        }
        return totalAmount;
    }

    @Override
    public SplitAccountPrice getInvoiceTotalAmount(String userEmail, String billToCustomer, String supportToCustomer)
            throws JsonParseException, JsonMappingException, JsonProcessingException, IOException {
        CachedShoppingCartDetails cachedShoppingCartDetails = new CachedShoppingCartDetails(userEmail, billToCustomer,
                supportToCustomer);
        CachedShoppingCartDetails cartDetails = cacheService.get(cachedShoppingCartDetails);
        double totalNmit = 0;
        double totalNmspl = 0;
        if ( cartDetails.getPriceResponseData() != null ) {
            shoppingCartService.updateCartStartDate(cartDetails);
            for ( BillingGroupPrice billingGroupPrice : cartDetails.getPriceResponseData().getBillingGroupPrices() ) {
                List<ProductPrice> nmitProductPriceList = billingGroupPrice.getProducts().stream()
                        .filter(productPrice -> productPrice.getType().equalsIgnoreCase(BusinessUnit.NMIT.getType()))
                        .collect(Collectors.toList());
                List<ProductPrice> nmsplProductPriceList = billingGroupPrice.getProducts().stream()
                        .filter(productPrice -> productPrice.getType().equalsIgnoreCase(BusinessUnit.NSPL.getType()))
                        .collect(Collectors.toList());
                if ( !nmitProductPriceList.isEmpty() ) {
                    billingGroupPrice.setProducts(nmitProductPriceList);
                    totalNmit += updateBillingGroupPrice(cartDetails, billingGroupPrice);
                }
                if ( !nmsplProductPriceList.isEmpty() ) {
                    billingGroupPrice.setProducts(nmsplProductPriceList);
                    totalNmspl += updateBillingGroupPrice(cartDetails, billingGroupPrice);
                }
            }
        }
        SplitAccountPrice splitAccountPrice = new SplitAccountPrice(totalNmit, totalNmspl);
        return splitAccountPrice;

    }

    private double updateBillingGroupPrice(CachedShoppingCartDetails cachedCartData,
            BillingGroupPrice cachedBillingGroup)
            throws JsonProcessingException, IOException, JsonParseException, JsonMappingException {
        String billingGroupPrice = mapper.writeValueAsString(cachedBillingGroup);
        BillingGroup billingGroup = mapper.readValue(billingGroupPrice, BillingGroup.class);
        List<BillingGroup> billingGroups = new ArrayList<BillingGroup>();
        billingGroups.add(billingGroup);
        ProductPriceRequest productPriceRequest = new ProductPriceRequest(cachedCartData.getBillToCustomer(),
                cachedCartData.getSupportToCustomer(), billingGroups);
        ProductPriceResponse priceResponse = shoppingCartService.getProductPricing(DISPLAY, productPriceRequest);
        double amount = priceResponse.getPriceResponseData().getBillingGroupPrices().get(0)
                .getTotalMonthlyRecurringCostWithTax()
                + priceResponse.getPriceResponseData().getBillingGroupPrices().get(0).getTotalOneTimeCostWithTax();
        return amount;
    }

}
