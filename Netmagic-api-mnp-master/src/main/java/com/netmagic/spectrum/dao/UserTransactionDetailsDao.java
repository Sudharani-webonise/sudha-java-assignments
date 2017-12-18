package com.netmagic.spectrum.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.UserTransactionDetailsEntity;

/**
 * The following dao interface helps to user transaction details information
 * table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface UserTransactionDetailsDao extends JpaRepository<UserTransactionDetailsEntity, Long> {

    @Query("SELECT u FROM UserTransactionDetailsEntity u WHERE u.transactionStatus = :transactionStatus")
    List<UserTransactionDetailsEntity> findByUserTransationStatus(
            @Param("transactionStatus") boolean transactionStatus);

}
