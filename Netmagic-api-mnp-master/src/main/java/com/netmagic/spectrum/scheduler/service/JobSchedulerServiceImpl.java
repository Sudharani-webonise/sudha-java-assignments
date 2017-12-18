package com.netmagic.spectrum.scheduler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netmagic.spectrum.commons.PaymentType;
import com.netmagic.spectrum.commons.ResourceHelper;
import com.netmagic.spectrum.commons.Status;
import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dao.SofTransactionPaymentConfigurationDao;
import com.netmagic.spectrum.dao.UserTransactionDetailsDao;
import com.netmagic.spectrum.dto.outstanding.request.CartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.ShoppingCartPaymentRequest;
import com.netmagic.spectrum.dto.outstanding.request.SofDetail;
import com.netmagic.spectrum.dto.outstanding.response.CartPaymentResponse;
import com.netmagic.spectrum.dto.shoppingcart.request.CreateSofRequest;
import com.netmagic.spectrum.dto.shoppingcart.response.BillingGroupPrice;
import com.netmagic.spectrum.dto.shoppingcart.response.SofResponse;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;
import com.netmagic.spectrum.entity.UserTransactionDetailsEntity;
import com.netmagic.spectrum.exception.ServiceUnavailableException;
import com.netmagic.spectrum.service.OutstandingService;
import com.netmagic.spectrum.service.ShoppingCartService;
import com.netmagic.spectrum.utils.DateUtils;

@Service
public class JobSchedulerServiceImpl implements JobSchedulerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobSchedulerServiceImpl.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private SofTransactionPaymentConfigurationDao sofTransactionPaymentConfigurationDao;

    @Autowired
    private UserTransactionDetailsDao userTransactionDao;

    @Autowired
    private MasterUserTransactionDao masterUserTransactionDao;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private OutstandingService outstandingService;

    @Override
    @Scheduled(cron = "0 0/2 * * * ?")
    public void creatLineItemSofs() throws JsonParseException, JsonMappingException, IOException {
        try {
            List<SofTransactionPaymentConfigurationEntity> sofTransactionPaymentConfigurations = sofTransactionPaymentConfigurationDao
                    .findAllBySofTransactionStatus(false);
            for ( SofTransactionPaymentConfigurationEntity sofTransactionPayment : sofTransactionPaymentConfigurations ) {
                if ( sofTransactionPayment.getSofTransactionLastRetriveTime() == null
                        && DateUtils.compareSofLastRetrivalTime(sofTransactionPayment.getSofTransactionCreateTime()) ) {
                    createSofs(sofTransactionPayment);
                } else if ( DateUtils
                        .compareSofLastRetrivalTime(sofTransactionPayment.getSofTransactionLastRetriveTime()) ) {
                    createSofs(sofTransactionPayment);
                }
            }
        } catch (Exception ex) {
            LOGGER.error("unable to create sof request again :", ex);
            throw new ServiceUnavailableException("unable to create sof request again");
        }
    }

    private void createSofs(SofTransactionPaymentConfigurationEntity sofTransactionPayment)
            throws IOException, JsonParseException, JsonMappingException, JsonProcessingException {
        CreateSofRequest createSofRequest = mapper.readValue(sofTransactionPayment.getSofTransactionrRquestBody(),
                CreateSofRequest.class);
        SofResponse response = shoppingCartService.createSof(createSofRequest,
                sofTransactionPayment.getSofTransactionSofType());
        if ( response != null && response.getSofLists().stream().findFirst().get().getSofs().get(0) != null ) {
            sofTransactionPayment.setSofTransactionStatus(true);
            response.setStatus(Status.SUCCESS.getStatus());
            response.setMessage("");
        }
        sofTransactionPayment.setSofTransactionRetriveCount(sofTransactionPayment.getSofTransactionRetriveCount() + 1);
        sofTransactionPayment.setSofTransactionLastRetriveTime(DateUtils.getTimestamp());
        sofTransactionPayment.setSofTransactionSofNumber(ResourceHelper.objectAsJsonString(response));
        sofTransactionPaymentConfigurationDao.save(sofTransactionPayment);
    }

    @Override
    @Scheduled(cron = "0 0/10 * * * ?")
    public void callToPeopleSoftAfterSofCreate() {
        try {
            List<UserTransactionDetailsEntity> userTransactions = userTransactionDao.findByUserTransationStatus(false);
            for ( UserTransactionDetailsEntity userTransaction : userTransactions ) {
                MasterUserTransactionEntity masterUser = userTransaction.getMasterUserTransaction();
                if ( masterUser.isTransactionStatus() ) {
                    List<SofTransactionPaymentConfigurationEntity> sofConfigurations = masterUser
                            .getSofTransactionPaymentConfigurations();
                    if ( isAllSofsGenerated(sofConfigurations) ) {
                        CartPaymentRequest finalCartPaymentRequest = new CartPaymentRequest();
                        ShoppingCartPaymentRequest cartRequest = setCartPaymentRequestBasedOnPaymentId(masterUser);
                        List<SofDetail> sofDetails = new ArrayList<>();
                        for ( SofTransactionPaymentConfigurationEntity sofPaymentConf : sofConfigurations ) {
                            if ( sofPaymentConf != null && sofPaymentConf.getSofTransactionStatus() ) {
                                SofDetail sofDetail = getsofDetail(sofPaymentConf);
                                sofDetails.add(sofDetail);
                            }
                        }
                        cartRequest.setSofDetail(sofDetails);
                        finalCartPaymentRequest.setShoppingCartPaymentRequest(cartRequest);
                        CartPaymentResponse cartPaymentResponse = outstandingService
                                .cartMakePayment(finalCartPaymentRequest);
                        if ( cartPaymentResponse != null && cartPaymentResponse.getShopCartPayResponse()
                                .getPaymentStatus().equalsIgnoreCase(Status.SUCCESS.getStatus()) ) {
                            userTransaction.setTransactionStatus(true);
                            userTransaction.setTransactionRetrivalTime(DateUtils.getTimestamp());
                            masterUser.setUserTransactionDetailsEntity(userTransaction);
                            masterUserTransactionDao.saveAndFlush(masterUser);
                        } else {
                            userTransaction.setTransactionRetrivalTime(DateUtils.getTimestamp());
                            userTransaction
                                    .setTransactionRetriveCount(userTransaction.getTransactionRetriveCount() + 1);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.error("unable to call people soft :", ex);
            throw new ServiceUnavailableException("unable to call people soft");
        }

    }

    private boolean isAllSofsGenerated(List<SofTransactionPaymentConfigurationEntity> sofConfigurations) {
        long numberOfSofsWithTrueStatus = sofConfigurations.stream()
                .filter(sofTransaction -> sofTransaction.getSofTransactionStatus() == true).count();
        long size = sofConfigurations.size();
        if ( numberOfSofsWithTrueStatus == size ) {
            return true;
        }
        return false;
    }

    private ShoppingCartPaymentRequest setCartPaymentRequestBasedOnPaymentId(MasterUserTransactionEntity masterUser) {
        ShoppingCartPaymentRequest cartPaymentRequest = new ShoppingCartPaymentRequest();
        if ( masterUser.getPaymentTypeId() == PaymentType.CREDIT.getPaymentId() ) {
            cartPaymentRequest.setCreditUsed(masterUser.getTotalAmount());
            cartPaymentRequest.setEbsPaymentAmount(0.00);
        } else if ( masterUser.getPaymentTypeId() == PaymentType.EBS.getPaymentId() ) {
            cartPaymentRequest.setEbsPaymentId(masterUser.getTransactionNumber());
            cartPaymentRequest.setEbsPaymentAmount(masterUser.getTotalAmount());
            cartPaymentRequest.setCreditUsed(0.00);
        }
        cartPaymentRequest.setUserEmailId(masterUser.getUserEmail());
        cartPaymentRequest.setTransactionId(masterUser.getMerchantRefNumber());
        cartPaymentRequest.setTransactionAmount(masterUser.getTotalAmount());
        cartPaymentRequest.setDateAndTime(DateUtils.getYYYYMMDDHHMMSSFormat());
        return cartPaymentRequest;
    }

    private SofDetail getsofDetail(SofTransactionPaymentConfigurationEntity sofPaymentConf)
            throws JsonParseException, JsonMappingException, IOException {
        SofResponse transactionSofResponse = mapper.readValue(sofPaymentConf.getSofTransactionSofNumber(),
                SofResponse.class);
        CreateSofRequest sofRequest = mapper.readValue(sofPaymentConf.getSofTransactionrRquestBody(),
                CreateSofRequest.class);
        String sofNumber = transactionSofResponse.getSofLists().stream().findFirst().get().getSofs().stream()
                .findFirst().get();
        double sofAmount = 0.0;
        for ( BillingGroupPrice sofBillGroupPrice : sofRequest.getBillingGroupPrices() ) {
            sofAmount += sofBillGroupPrice.getTotalMonthlyRecurringCostWithTax()
                    + sofBillGroupPrice.getTotalOneTimeCostWithTax();
        }
        return new SofDetail(sofNumber, sofAmount);
    }

}
