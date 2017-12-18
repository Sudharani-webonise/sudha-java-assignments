package com.netmagic.spectrum.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

/**
 * The following Entity class helps to determine Other Source Configuration
 * data.
 * 
 * @author Webonise
 *
 */
@Entity
@Table(name = "othr_sour_conf")
public class OtherSourceConfigurationEntity implements Serializable {

    private static final long serialVersionUID = 3623666991671159921L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sour_id_pk")
    @Version
    private long sourceId;

    @Column(name = "sour_name")
    private String sourceName;

    @Column(name = "sour_status")
    private boolean sourceStatus;

    @Column(name = "sour_create_date_time")
    private Timestamp sourceCreatedTime;

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public boolean getSourceStatus() {
        return sourceStatus;
    }

    public void setSourceStatus(boolean sourceStatus) {
        this.sourceStatus = sourceStatus;
    }

    public Timestamp getSourceCreatedTime() {
        return sourceCreatedTime;
    }

    public void setSourceCreatedTime(Timestamp sourceCreatedTime) {
        this.sourceCreatedTime = sourceCreatedTime;
    }

}
