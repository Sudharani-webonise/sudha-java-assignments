package com.netmagic.spectrum.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mockito.Mockito;

import com.netmagic.spectrum.dao.MasterUserTransactionDao;
import com.netmagic.spectrum.dao.SofTransactionPaymentConfigurationDao;
import com.netmagic.spectrum.dao.UserTransactionDetailsDao;
import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;
import com.netmagic.spectrum.entity.UserTransactionDetailsEntity;

public class MockDaoData {

    private static final String MASTER_USER_DATA = "entities/MasterUserTransactionEntity.json";

    private static final String SOF_TRANSACTION_DATA = "entities/SofTransactionPaymentConfigurationEntity.json";

    private static final String USER_TRANSACTION_DATA = "entities/UserTransactionDetailsEntity.json";

    static public MasterUserTransactionEntity findByMerchantRefNumberAndPaymentStatus(
            MasterUserTransactionDao masterUserTransactioDao) throws IOException {
        MasterUserTransactionEntity masterUser = getMasterUser();
        Mockito.when(masterUserTransactioDao.findByMerchantRefNumberAndPaymentStatus(masterUser.getMerchantRefNumber(),
                true)).thenReturn(masterUser);
        return masterUser;
    }

    public static List<SofTransactionPaymentConfigurationEntity> findAllBySofTransactionStatus(
            SofTransactionPaymentConfigurationDao sofTransactionPaymentConfigurationDao) throws IOException {
        List<SofTransactionPaymentConfigurationEntity> configurationEntities = geSofTransactionList();
        Mockito.when(sofTransactionPaymentConfigurationDao.findAllBySofTransactionStatus(false))
                .thenReturn(configurationEntities);
        return configurationEntities;
    }

    public static List<UserTransactionDetailsEntity> findByUserTransationStatus(
            UserTransactionDetailsDao userTransactionDao) throws IOException {
        List<UserTransactionDetailsEntity> detailsEntities = geUserTransactionList();
        Mockito.when(userTransactionDao.findByUserTransationStatus(false)).thenReturn(detailsEntities);
        return detailsEntities;

    }

    private static MasterUserTransactionEntity getMasterUser() throws IOException {
        MasterUserTransactionEntity masterUser = ResourceLoader.readAndGetObject(MASTER_USER_DATA,
                MasterUserTransactionEntity.class);
        masterUser.setSofTransactionPaymentConfigurations(geSofTransactionList());
        masterUser.setUserTransactionDetailsEntity(geUserTransaction());
        return masterUser;
    }

    private static UserTransactionDetailsEntity geUserTransaction() throws IOException {
        return ResourceLoader.readAndGetObject(USER_TRANSACTION_DATA, UserTransactionDetailsEntity.class);
    }

    private static List<SofTransactionPaymentConfigurationEntity> geSofTransactionList() throws IOException {
        SofTransactionPaymentConfigurationEntity configurationEntity = ResourceLoader
                .readAndGetObject(SOF_TRANSACTION_DATA, SofTransactionPaymentConfigurationEntity.class);

        List<SofTransactionPaymentConfigurationEntity> configurationEntities = new ArrayList<>();
        configurationEntities.add(configurationEntity);
        return configurationEntities;
    }

    private static List<UserTransactionDetailsEntity> geUserTransactionList() throws IOException {
        List<UserTransactionDetailsEntity> detailsEntities = new ArrayList<>();
        MasterUserTransactionEntity masterUser = getMasterUser();
        UserTransactionDetailsEntity detailsEntity = ResourceLoader.readAndGetObject(USER_TRANSACTION_DATA,
                UserTransactionDetailsEntity.class);
        detailsEntity.setMasterUserTransaction(masterUser);
        detailsEntities.add(detailsEntity);
        return detailsEntities;
    }
}
