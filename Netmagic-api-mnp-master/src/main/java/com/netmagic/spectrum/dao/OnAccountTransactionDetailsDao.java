package com.netmagic.spectrum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.OnAccountTransactionDetailsEntity;

/**
 * The following dao interface helps to fetch On Account transaction payment
 * information from the table.
 * 
 * @author Webonise
 *
 */
@Repository
public interface OnAccountTransactionDetailsDao extends JpaRepository<OnAccountTransactionDetailsEntity, Long> {

}
