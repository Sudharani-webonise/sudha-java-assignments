package com.netmagic.spectrum.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.netmagic.spectrum.entity.OtherSourceConfigurationEntity;

/**
 * The following dao interface helps to determine other source configurations
 * information table from sql and helps to determine their data.
 * 
 * @author Webonise
 *
 */
@Repository
public interface OtherSourceConfigurationDao extends JpaRepository<OtherSourceConfigurationEntity, Long> {

}
