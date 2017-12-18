package com.netmagic.spectrum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.TransactionPaymentConfigurationEntity;

/**
 * The following dao interface helps to user transaction payment configuration
 * information table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface TransactionPaymentConfigurationDao extends JpaRepository<TransactionPaymentConfigurationEntity, Long> {

}
