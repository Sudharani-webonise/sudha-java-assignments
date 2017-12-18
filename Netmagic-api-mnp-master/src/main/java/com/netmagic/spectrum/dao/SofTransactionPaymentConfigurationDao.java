package com.netmagic.spectrum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.MasterUserTransactionEntity;
import com.netmagic.spectrum.entity.SofTransactionPaymentConfigurationEntity;

/**
 * The following dao interface helps to sof transaction payment configuration
 * information table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface SofTransactionPaymentConfigurationDao
        extends JpaRepository<SofTransactionPaymentConfigurationEntity, Long> {

    @Query("SELECT sofT FROM SofTransactionPaymentConfigurationEntity sofT WHERE sofT.sofTransactionStatus = :sofTransactionStatus")
    List<SofTransactionPaymentConfigurationEntity> findAllBySofTransactionStatus(
            @Param("sofTransactionStatus") boolean sofTransactionStatus);

    @Query("SELECT sofT FROM SofTransactionPaymentConfigurationEntity sofT WHERE sofT.sofMasterUserTransaction = :sofMasterUserTransaction")
    List<SofTransactionPaymentConfigurationEntity> findByMasterUser(
            MasterUserTransactionEntity sofMasterUserTransaction);

    @Query("SELECT sofT FROM SofTransactionPaymentConfigurationEntity sofT WHERE sofT.sofMasterUserTransaction = :userTransactionId")
    List<SofTransactionPaymentConfigurationEntity> findAll(long userTransactionId);
}
