package com.netmagic.spectrum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.MasterUserTransactionEntity;

/**
 * The following dao interface helps to determine master user transaction
 * information table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface MasterUserTransactionDao extends JpaRepository<MasterUserTransactionEntity, Long> {

    @Query("SELECT u FROM MasterUserTransactionEntity u WHERE u.merchantRefNumber = :merchantRefNumber")
    MasterUserTransactionEntity findByMerchantRefNumber(@Param("merchantRefNumber") String merchantRefNumber);

    @Query("SELECT u FROM MasterUserTransactionEntity u WHERE u.merchantRefNumber = :merchantRefNumber and u.transactionStatus=:transactionStatus")
    MasterUserTransactionEntity findByMerchantRefNumberAndPaymentStatus(
            @Param("merchantRefNumber") String merchantRefNumber,
            @Param("transactionStatus") boolean transactionStatus);

    @Query("SELECT u FROM MasterUserTransactionEntity u WHERE u.transactionStatus = :transactionStatus")
    List<MasterUserTransactionEntity> findByUserPaymentStatus(@Param("transactionStatus") boolean transactionStatus);

}
