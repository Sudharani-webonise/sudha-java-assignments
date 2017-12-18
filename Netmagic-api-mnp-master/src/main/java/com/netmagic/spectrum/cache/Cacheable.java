package com.netmagic.spectrum.cache;

import java.io.Serializable;

public interface Cacheable extends Serializable {

    public String getCacheKey();

    public String getObjectKey();

}
