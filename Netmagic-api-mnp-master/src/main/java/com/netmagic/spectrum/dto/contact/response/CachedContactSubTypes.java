package com.netmagic.spectrum.dto.contact.response;

import org.apache.commons.lang3.builder.EqualsBuilder;

import com.netmagic.spectrum.cache.Cacheable;
import com.netmagic.spectrum.commons.Param;

/**
 * This class stores the Contact Sub type List stored in Cache
 * 
 * @author webonise
 *
 */
public class CachedContactSubTypes implements Cacheable {

    private static final long serialVersionUID = -3317182948578281287L;

    private static final String OBJECT_KEY = "CONTACT";

    private static final String SUB_TYPE = "SUB_TYPE";

    private Long contactTypeId;

    private ContactSubTypeList contactSubTypes;

    public CachedContactSubTypes() {
        super();
    }

    public CachedContactSubTypes(Long contactTypeId) {
        super();
        this.contactTypeId = contactTypeId;
    }

    public Long getContactTypeId() {
        return contactTypeId;
    }

    public void setContactTypeId(Long contactTypeId) {
        this.contactTypeId = contactTypeId;
    }

    public ContactSubTypeList getContactSubTypes() {
        return contactSubTypes;
    }

    public void setContactSubTypes(ContactSubTypeList contactSubTypes) {
        this.contactSubTypes = contactSubTypes;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String getCacheKey() {
        return String.format(Param.TWO.getParam(), SUB_TYPE, contactTypeId.toString());
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }

}
