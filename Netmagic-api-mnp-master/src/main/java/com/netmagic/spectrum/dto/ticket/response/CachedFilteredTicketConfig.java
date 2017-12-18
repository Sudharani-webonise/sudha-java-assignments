package com.netmagic.spectrum.dto.ticket.response;

import com.netmagic.spectrum.cache.Cacheable;

/**
 * This class stores filtered ticket config information as required by MNP into
 * the cache
 * 
 * @author webonise
 *
 */
public class CachedFilteredTicketConfig implements Cacheable {

    private static final long serialVersionUID = -2865387954018491574L;

    private static final String CACHE_KEY = "CACHED_FILTERED_CONFIG";

    private static final String OBJECT_KEY = "TICKET";

    private FilteredTicketConfig filteredTicketConfig;

    public FilteredTicketConfig getFilteredTicketConfig() {
        return filteredTicketConfig;
    }

    public void setFilteredTicketConfig(FilteredTicketConfig filteredTicketConfig) {
        this.filteredTicketConfig = filteredTicketConfig;
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
