package com.netmagic.spectrum.dto.service.request;

import com.netmagic.spectrum.cache.Cacheable;

/**
 * This class represents the access token required to access data from service
 * legder APIs
 * 
 * @author webonise
 *
 */
public class Token implements Cacheable {

    private static final long serialVersionUID = 6967612498163714452L;

    private static final String CACHE_KEY = "TOKEN";

    private static final String OBJECT_KEY = "SERVICE_LEDGER";

    private String token;

    public Token() {
        super();
    }

    public Token(String token) {
        super();
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getCacheKey() {
        return CACHE_KEY;
    }

    @Override
    public String getObjectKey() {
        return OBJECT_KEY;
    }
}
