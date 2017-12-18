package com.netmagic.spectrum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.PaymentForConfigurationEntity;

/**
 * The following dao interface helps to user payment for configuration
 * information table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface PaymentForConfigurationDao extends JpaRepository<PaymentForConfigurationEntity, Long> {

}
