package com.netmagic.spectrum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.InvoiceTransactionDetailsEntity;

/**
 * The following dao interface helps to fetch Invoice transaction payment
 * details from the table.
 * 
 * @author Webonise
 *
 */
@Repository
public interface InvoiceTransactionDetailsDao extends JpaRepository<InvoiceTransactionDetailsEntity, Long> {

}
