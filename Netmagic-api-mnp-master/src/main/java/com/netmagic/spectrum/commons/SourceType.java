package com.netmagic.spectrum.commons;

import com.netmagic.spectrum.entity.OtherSourceConfigurationEntity;

/**
 * This enum is specifies the ids stored in database for table othr_sour_conf
 * {@link OtherSourceConfigurationEntity }
 * 
 * @author webonise
 *
 */
public enum SourceType {

    PEOPLESOFT("1"), SUGAR("2"), HELIOS("3"), CLOUD("4");

    private String sourceType;

    private SourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public int getSourceId() {
        return Integer.parseInt(sourceType);
    }

}
